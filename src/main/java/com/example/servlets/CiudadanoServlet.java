        package com.example.servlets;

        import com.example.controllers.CiudadanoController;
        import com.example.entities.Ciudadano;
        import jakarta.servlet.ServletException;
        import jakarta.servlet.annotation.WebServlet;
        import jakarta.servlet.http.HttpServlet;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;

        import java.io.IOException;
        import java.util.List;

        /*localhost:8080/app/ciudadano*/

        @WebServlet(urlPatterns = "/ciudadano")
        public class CiudadanoServlet extends HttpServlet {

            private CiudadanoController ciudadanoController = new CiudadanoController();

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                // Listar todos los ciudadanos
                List<Ciudadano> listado = ciudadanoController.findAll();

                // Enviar listado al JSP para visualización
                req.setAttribute("listado", listado);

                // Redirigir a la página JSP
                req.getRequestDispatcher("ciudadano.jsp").forward(req, resp);
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                // Obtener parámetros del formulario para crear un ciudadano
                String nombre = req.getParameter("nombre");
                String apellido = req.getParameter("apellido");

                // Validar que los parámetros no sean nulos o vacíos
                if (nombre != null && !nombre.isEmpty() && apellido != null && !apellido.isEmpty()) {
                    // Crear un nuevo ciudadano utilizando el controlador
                    ciudadanoController.create(nombre, apellido);

                    // Redirigir al listado de ciudadanos después de la creación
                    resp.sendRedirect("ciudadano");
                } else {
                    // Si los parámetros son inválidos, redirigir al formulario de creación con un mensaje de error
                    req.setAttribute("error", "Los campos no pueden estar vacíos.");
                    req.getRequestDispatcher("ciudadano.jsp").forward(req, resp);
                }
            }
        }
