    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%-- Importar las clases --%>
    <%@ page import="java.util.List, com.example.entities.Ciudadano" %>

    <!DOCTYPE html>
    <html lang="es">

        <%@ include file="partials/head.jsp" %>  <!-- Incluir encabezado común -->

    <body>
        <%@ include file="partials/header.jsp" %>  <!-- Incluir cabecera común -->

        <div class="container mt-5">
            <!-- Título de la página -->
            <div class="row mb-4">
                <div class="col-12 text-center">
                    <h2 class="display-4">Lista de Ciudadanos</h2>
                    <p class="lead">Visualiza y agrega ciudadanos a la lista.</p>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-12">
                    <!-- Formulario para agregar un nuevo ciudadano -->
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Agregar Nuevo Ciudadano</h5>
                            <form action="ciudadano" method="post">
                                <div class="mb-3">
                                    <label for="nombre" class="form-label">Nombre</label>
                                    <input type="text" id="nombre" name="nombre" class="form-control" placeholder="Ingresa el nombre" required>
                                </div>
                                <div class="mb-3">
                                    <label for="apellido" class="form-label">Apellido</label>
                                    <input type="text" id="apellido" name="apellido" class="form-control" placeholder="Ingresa el apellido" required>
                                </div>
                                <button type="submit" class="btn btn-success">Agregar Ciudadano</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Mostrar mensaje si existe -->
            <div class="row mb-4">
                <div class="col-12">
                    <p class="text-info">
                        Mensaje: <%= request.getAttribute("nombre") %>
                    </p>
                </div>
            </div>

            <!-- Tabla de ciudadanos -->
            <div class="row">
                <div class="col-12">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Lista de Ciudadanos</h5>
                            <ul class="list-group">
                                <% List<Ciudadano> listado = (List<Ciudadano>) request.getAttribute("listado");

                                for (Ciudadano ciudadano : listado) { %>
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        <span><b>Nombre:</b> <%= ciudadano.getNombre() %> <%= ciudadano.getApellido() %></span>
                                        <span class="badge bg-primary rounded-pill">ID: <%= ciudadano.getId() %></span>
                                    </li>
                                <% } %>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div> <!-- Fin del contenedor -->

    </body>
    </html>

