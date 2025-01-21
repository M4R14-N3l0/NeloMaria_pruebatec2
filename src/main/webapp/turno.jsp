<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.entities.Ciudadano, com.example.entities.Turno" %>

<!DOCTYPE html>
<html lang="es">

<%@ include file="partials/head.jsp" %>  <!-- Incluir encabezado común -->

<body>
    <%@ include file="partials/header.jsp" %>  <!-- Incluir cabecera común -->

    <div class="container mt-5">
        <!-- Título de la página -->
        <div class="row mb-4">
            <div class="col-12 text-center">
                <h2 class="display-4">Gestión de Turnos</h2>
                <p class="lead">Administra y agrega nuevos turnos fácilmente.</p>
            </div>
        </div>

        <!-- Formulario para agregar un nuevo turno -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Agregar Nuevo Turno</h5>
                        <form action="turno" method="post">
                            <div class="mb-3">
                                <label for="codigo" class="form-label">Código del Turno</label>
                                <input type="text" id="codigo" name="codigo" class="form-control" placeholder="Ingresa el código del turno" required>
                            </div>
                            <div class="mb-3">
                                <label for="estado" class="form-label">Estado</label>
                                <select id="estado" name="estado" class="form-control" required>
                                    <option value="ESPERA">Espera</option>
                                    <option value="ATENDIDO">Atendido</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="tramite" class="form-label">Trámite</label>
                                <input type="text" id="tramite" name="tramite" class="form-control" placeholder="Describe el trámite" required>
                            </div>
                            <div class="mb-3">
                                <label for="fecha" class="form-label">Fecha</label>
                                <input type="date" id="fecha" name="fecha" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label for="ciudadano_id" class="form-label">Ciudadano</label>
                                <select id="ciudadano_id" name="ciudadano_id" class="form-control" required>
                                    <% List<Ciudadano> ciudadanos = (List<Ciudadano>) request.getAttribute("ciudadanos");
                                    for (Ciudadano ciudadano : ciudadanos) { %>
                                        <option value="<%= ciudadano.getId() %>"><%= ciudadano.getNombre() %> <%= ciudadano.getApellido() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success">Agregar Turno</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Filtro de turnos -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Filtrar Turnos</h5>
                        <form action="turno" method="get">
                            <div class="mb-3">
                                <label for="fecha" class="form-label">Fecha</label>
                                <input type="date" id="fecha" name="fecha" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label for="estado" class="form-label">Estado</label>
                                <select id="estado" name="estado" class="form-control">
                                    <option value="">Todos</option> <!-- Opción para no filtrar por estado -->
                                    <option value="ESPERA">Espera</option>
                                    <option value="ATENDIDO">Atendido</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success">Filtrar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Tabla de turnos filtrados -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Turnos Filtrados</h5>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Trámite</th>
                                    <th>Estado</th>
                                    <th>Fecha</th>
                                    <th>Ciudadano</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% List<Turno> turnosFiltrados = (List<Turno>) request.getAttribute("turno");
                                if (turnosFiltrados != null && !turnosFiltrados.isEmpty()) {
                                    for (Turno turno : turnosFiltrados) { %>
                                        <tr>
                                            <td><%= turno.getCodigo() %></td>
                                            <td><%= turno.getTramite() %></td>
                                            <td><%= turno.getEstado() %></td>
                                            <td><%= turno.getFecha() %></td>
                                            <td><%= turno.getCiudadano().getNombre() %> <%= turno.getCiudadano().getApellido() %></td>
                                        </tr>
                                    <% }
                                } else { %>
                                    <tr>
                                        <td colspan="5" class="text-center">No se encontraron turnos filtrados.</td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Tabla de todos los turnos asignados -->
        <div class="row">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Todos los Turnos Asignados</h5>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Trámite</th>
                                    <th>Estado</th>
                                    <th>Fecha</th>
                                    <th>Ciudadano</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% List<Turno> turnosTodos = (List<Turno>) request.getAttribute("turno");
                                if (turnosTodos != null && !turnosTodos.isEmpty()) {
                                    for (Turno turno : turnosTodos) { %>
                                        <tr>
                                            <td><%= turno.getCodigo() %></td>
                                            <td><%= turno.getTramite() %></td>
                                            <td><%= turno.getEstado() %></td>
                                            <td><%= turno.getFecha() %></td>
                                            <td><%= turno.getCiudadano().getNombre() %> <%= turno.getCiudadano().getApellido() %></td>
                                        </tr>
                                    <% }
                                } else { %>
                                    <tr>
                                        <td colspan="5" class="text-center">No se encontraron turnos asignados.</td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div> <!-- Fin del contenedor -->

</body>
</html>


