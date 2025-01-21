<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.entities.Turno, com.example.entities.Ciudadano" %>

<!DOCTYPE html>
<html lang="es">

    <%@ include file="partials/head.jsp" %>  <!-- Incluir encabezado común -->

<body>
    <%@ include file="partials/header.jsp" %>  <!-- Incluir cabecera común -->

    <div class="container mt-5">
        <h2 class="display-4">Turnos Filtrados</h2>

        <!-- Filtro de turnos -->
        <form action="turnosFiltrados" method="get">
            <div class="mb-3">
                <label for="fecha" class="form-label">Fecha</label>
                <input type="date" id="fecha" name="fecha" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="estado" class="form-label">Estado</label>
                <select id="estado" name="estado" class="form-control">
                    <option value="">Todos</option>
                    <option value="ESPERA">Espera</option>
                    <option value="ATENDIDO">Atendido</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Filtrar</button>
        </form>

        <!-- Tabla de turnos filtrados -->
        <table class="table mt-3">
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
                <% List<Turno> turnosFiltrados = (List<Turno>) request.getAttribute("turnosFiltrados");
                for (Turno turno : turnosFiltrados) { %>
                    <tr>
                        <td><%= turno.getCodigo() %></td>
                        <td><%= turno.getTramite() %></td>
                        <td><%= turno.getEstado() %></td>
                        <td><%= turno.getFecha() %></td>
                        <td><%= turno.getCiudadano().getNombre() %> <%= turno.getCiudadano().getApellido() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

</body>
</html>
