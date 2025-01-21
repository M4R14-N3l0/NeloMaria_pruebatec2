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
    import java.util.List;

    @WebServlet(urlPatterns = "/turnosAsignados")
    public class TurnosAsignadosServlet extends HttpServlet {

        private TurnoController turnoController = new TurnoController();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // Obtener los turnos asignados
            List<Turno> turnosAsignados = turnoController.findAll();
            req.setAttribute("turnosAsignados", turnosAsignados);  // Cambiado de "turno" a "turnosAsignados"

            // Obtener todos los ciudadanos para el formulario
            List<Ciudadano> ciudadanos = turnoController.findAllCiudadanos();
            req.setAttribute("ciudadanos", ciudadanos);

            req.getRequestDispatcher("turnosAsignados.jsp").forward(req, resp);  // Se redirige a "turnosAsignados.jsp"
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // Obtener parámetros del formulario para crear un nuevo turno
            String codigo = req.getParameter("codigo");
            String estadoStr = req.getParameter("estado");
            String tramite = req.getParameter("tramite");
            LocalDate fechaParam = LocalDate.parse(req.getParameter("fecha"));
            Long ciudadanoId = Long.parseLong(req.getParameter("ciudadano_id"));

            // Convertir el estado a enum
            estadoStr = estadoStr.toUpperCase();
            Turno.EstadoTurno estado = Turno.EstadoTurno.valueOf(estadoStr);

            // Crear un nuevo turno usando el controlador
            turnoController.create(codigo, String.valueOf(estado), tramite, fechaParam, ciudadanoId);

            // Redirigir a la lista de turnos asignados después de la creación
            resp.sendRedirect("turnosAsignados");  // Cambiado a la URL de turnos asignados
        }
    }
