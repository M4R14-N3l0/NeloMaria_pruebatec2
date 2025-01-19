    package com.example.servlets;

    import com.example.controllers.TurnoController;
    import com.example.entities.Ciudadano;
    import com.example.entities.Turno;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.List;

    @WebServlet(urlPatterns = "/turno")
    public class TurnoServlet extends HttpServlet {

        private TurnoController turnoController = new TurnoController();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            // Obtener parámetros de filtro (si existen)
            String fechaParam = req.getParameter("fecha");
            String estadoParam = req.getParameter("estado");

            // Si se han proporcionado parámetros de filtro
            if (fechaParam != null && estadoParam != null) {
                // Convertir la fecha
                LocalDate fecha = LocalDate.parse(fechaParam);
                estadoParam = estadoParam.toUpperCase();
                Turno.EstadoTurno estado = Turno.EstadoTurno.valueOf(estadoParam);

                // Filtrar los turnos según fecha y estado
                List<Turno> turnos = turnoController.findTurnosByFechaAndEstado(fecha, String.valueOf(estado));
                req.setAttribute("turno", turnos);
            } else if (fechaParam != null) {
                // Si solo se proporciona fecha
                LocalDate fecha = LocalDate.parse(fechaParam);
                List<Turno> turnos = turnoController.findTurnosByFecha(fecha);
                req.setAttribute("turno", turnos);
            } else {
                // Si no se proporciona ningún filtro, listar todos los turnos
                List<Turno> turnos = turnoController.findAll();
                req.setAttribute("turno", turnos);
            }

            // Obtener todos los ciudadanos para el formulario
            List<Ciudadano> ciudadanos = turnoController.findAllCiudadanos();
            req.setAttribute("ciudadanos", ciudadanos);

            // Redirigir al JSP para mostrar los turnos
            req.getRequestDispatcher("turno.jsp").forward(req, resp);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtener parámetros del formulario para crear un nuevo turno
        String codigo = req.getParameter("codigo");
        String estadoStr = req.getParameter("estado");
        String tramite = req.getParameter("tramite");
        LocalDate fechaParam = LocalDate.parse(req.getParameter("fecha"));
         // Formato recibido (ej.: 15/01/2025)
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //LocalDate fecha = LocalDate.parse(fechaParam, formatter);
        Long ciudadanoId = Long.parseLong(req.getParameter("ciudadano_id"));

        // Convertir el estado a enum

// Convertir a mayúsculas para asegurar que coincide con el enum
        estadoStr = estadoStr.toUpperCase();
        Turno.EstadoTurno estado = Turno.EstadoTurno.valueOf(estadoStr);




        // Crear un nuevo turno usando el controlador
        turnoController.create(codigo, String.valueOf(estado), tramite, fechaParam, ciudadanoId);

        // Redirigir a la lista de turnos después de la creación
        resp.sendRedirect("turno");
    }
}

