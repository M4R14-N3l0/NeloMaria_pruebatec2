#Segunda evaluación técnica del Bootcamp JAVA en HACK A BOSS#

##Aplicación Web con Servlets y JPA##

Descripción del Proyecto

Esta aplicación web gestiona turnos mediante servlets y utiliza JPA para la interacción con una base de datos. Incluye funcionalidades como creación de ciudadanos, asignación de turnos, y filtrado de turnos según criterios específicos.

Requisitos

Java: Versión 17 o superior

Servidor de aplicaciones: Apache Tomcat (9.0 o superior)

Base de datos: MySQL

Herramienta de construcción: Maven

Instalación

Clona este repositorio.

Despliega el proyecto desde tu IDE. Yo he usado IntelliJ IDEA Community Edition 2024.3.1.1

Crea la base de datos turnos_db en MySQL y configura las credenciales en persistence.xml.

Ejecuta el proyecto.

Configuración de la Base de Datos

La conexión a la base de datos se configura a través de JPA con el archivo persistence.xml:

<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1">
    <persistence-unit name="examplePU">
        <class>com.ejemplo.Pelicula</class>
        <properties>
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/turnos_db?serverTimezone=UTC" />
            <property name="javax.persistence.jdbc.user" value="root" />
            <property name="javax.persistence.jdbc.password" value="root" />

            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="update" />
        </properties>
    </persistence-unit>
</persistence>

Asegúrate de ajustar los datos según tu configuración local. Es decir, colocar tu usuario y contraseña en la base de datos.

Estructura de Rutas y Servlets

1. /ciudadano

Métodos:

GET: Muestra una lista de todos los ciudadanos en la base de datos. Los datos se envían al archivo JSP ciudadano.jsp para su visualización.

POST: Registra un nuevo ciudadano basado en los parámetros enviados desde un formulario.

Parámetros:

nombre (String): Nombre del ciudadano (POST).

apellido (String): Apellido del ciudadano (POST).

Ejemplo de Código:

@WebServlet(urlPatterns = "/ciudadano")
public class CiudadanoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ciudadano> listado = ciudadanoController.findAll();
        req.setAttribute("listado", listado);
        req.getRequestDispatcher("ciudadano.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");

        if (nombre != null && !nombre.isEmpty() && apellido != null && !apellido.isEmpty()) {
            ciudadanoController.create(nombre, apellido);
            resp.sendRedirect("ciudadano");
        } else {
            req.setAttribute("error", "Los campos no pueden estar vacíos.");
            req.getRequestDispatcher("ciudadano.jsp").forward(req, resp);
        }
    }
}

2. /turnosFiltrados

Método: GET

Descripción: Filtra los turnos según fecha y/o estado. Si no se proporcionan filtros, devuelve todos los turnos.

Parámetros:

fecha (String): Fecha en formato YYYY-MM-DD.

estado (String): Estado del turno (ESPERA, ATENDIDO)

Ejemplo de Código:

@WebServlet(urlPatterns = "/turnosFiltrados")
public class TurnoFiltradoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fechaParam = req.getParameter("fecha");
        String estadoParam = req.getParameter("estado");

        if (fechaParam != null && !fechaParam.isEmpty()) {
            try {
                LocalDate fecha = LocalDate.parse(fechaParam);
                if (estadoParam != null && !estadoParam.isEmpty()) {
                    Turno.EstadoTurno estado = Turno.EstadoTurno.valueOf(estadoParam.toUpperCase());
                    List<Turno> turnos = turnoController.findTurnosByFechaAndEstado(fecha, estado);
                    req.setAttribute("turnosFiltrados", turnos);
                } else {
                    List<Turno> turnos = turnoController.findTurnosByFecha(fecha);
                    req.setAttribute("turnosFiltrados", turnos);
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Fecha no válida");
                return;
            }
        } else {
            List<Turno> turnos = turnoController.findAll();
            req.setAttribute("turnosFiltrados", turnos);
        }

        req.getRequestDispatcher("turnosFiltrados.jsp").forward(req, resp);
    }
}

3. /turnosAsignados

Métodos:

GET: Devuelve una lista de turnos asignados y ciudadanos para mostrarlos en turnosAsignados.jsp.

POST: Crea un nuevo turno asignado a un ciudadano.

Parámetros:

codigo (String): Código del turno (POST).

estado (String): Estado del turno (POST).

tramite (String): Descripción del trámite asociado al turno (POST).

fecha (String): Fecha del turno en formato YYYY-MM-DD (POST).

ciudadano_id (Long): ID del ciudadano al que se asigna el turno (POST).

Ejemplo de Código:

@WebServlet(urlPatterns = "/turnosAsignados")
public class TurnosAsignadosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Turno> turnosAsignados = turnoController.findAll();
        List<Ciudadano> ciudadanos = turnoController.findAllCiudadanos();

        req.setAttribute("turnosAsignados", turnosAsignados);
        req.setAttribute("ciudadanos", ciudadanos);

        req.getRequestDispatcher("turnosAsignados.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codigo = req.getParameter("codigo");
        String estadoStr = req.getParameter("estado").toUpperCase();
        Turno.EstadoTurno estado = Turno.EstadoTurno.valueOf(estadoStr);
        String tramite = req.getParameter("tramite");
        LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
        Long ciudadanoId = Long.parseLong(req.getParameter("ciudadano_id"));

        turnoController.create(codigo, String.valueOf(estado), tramite, fecha, ciudadanoId);
        resp.sendRedirect("turnosAsignados");
    }
}
