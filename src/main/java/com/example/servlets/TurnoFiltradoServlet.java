    package com.example.servlets;

    import com.example.controllers.TurnoController;
    import com.example.entities.Turno;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.List;

    @WebServlet(urlPatterns = "/turnosFiltrados")
    public class TurnoFiltradoServlet extends HttpServlet {

        private TurnoController turnoController = new TurnoController();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // Obtener parámetros de filtro (si existen)
            String fechaParam = req.getParameter("fecha");
            String estadoParam = req.getParameter("estado");

            // Verificamos si la fecha está presente
            if (fechaParam != null && !fechaParam.isEmpty()) {
                try {
                    // Convertir la fecha a LocalDate
                    LocalDate fecha = LocalDate.parse(fechaParam);

                    if (estadoParam != null && !estadoParam.isEmpty()) {
                        // Si el estado está presente, filtramos por fecha y estado
                        estadoParam = estadoParam.toUpperCase();  // Convertir a mayúsculas para comparar con el enum

                        // Convertir el estado recibido (String) a Turno.EstadoTurno (enum)
                        Turno.EstadoTurno estado = Turno.EstadoTurno.valueOf(estadoParam);

                        // Filtrar los turnos por fecha y estado
                        List<Turno> turnos = turnoController.findTurnosByFechaAndEstado(fecha, estado);
                        req.setAttribute("turnosFiltrados", turnos);
                    } else {
                        // Si solo se proporciona fecha, filtramos solo por fecha
                        List<Turno> turnos = turnoController.findTurnosByFecha(fecha);
                        req.setAttribute("turnosFiltrados", turnos);
                    }

                } catch (Exception e) {
                    // Si hay un error al convertir la fecha, enviar un error al cliente
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Fecha no válida");
                    return;
                }
            } else {
                // Si no se proporciona la fecha, obtener todos los turnos sin filtro
                List<Turno> turnos = turnoController.findAll();
                req.setAttribute("turnosFiltrados", turnos);
            }

            // Redirigir al JSP para mostrar los turnos filtrados
            req.getRequestDispatcher("turnosFiltrados.jsp").forward(req, resp);
        }
    }
