    package com.example.controllers;

    import com.example.entities.Ciudadano;
    import com.example.entities.Turno;
    import com.example.persistence.GenericoJPA;
    import java.time.LocalDate;
    import java.util.Collections;
    import java.util.List;
    import java.util.stream.Collectors;

    public class TurnoController {

        private final GenericoJPA<Turno, Long> turnoJPA;
        private final GenericoJPA<Ciudadano, Long> ciudadanoJPA;

        public TurnoController() {
            this.turnoJPA = new GenericoJPA<>(Turno.class);
            this.ciudadanoJPA = new GenericoJPA<>(Ciudadano.class);
        }

        // Metodo para normalizar y validar el estado
        private Turno.EstadoTurno normalizeEstado(String estado) {
            try {
                return Turno.EstadoTurno.valueOf(estado.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido: " + estado);
                return null; //
            }
        }

        // Crear un nuevo turno
        public void create(String codigo, String estado, String tramite, LocalDate fecha, Long ciudadanoId) {
            Ciudadano ciudadano = ciudadanoJPA.findOne(ciudadanoId);

            if (ciudadano != null) {
                Turno.EstadoTurno estadoNormalizado = normalizeEstado(estado);
                if (estadoNormalizado == null) return; // Detenemos la creación si el estado no es válido

                Turno turno = new Turno();
                turno.setCodigo(codigo);
                turno.setEstado(estadoNormalizado);
                turno.setTramite(tramite);
                turno.setFecha(fecha);
                turno.setCiudadano(ciudadano);
                turnoJPA.create(turno);
            } else {
                System.out.println("Ciudadano no encontrado.");
            }
        }

        // Listar todos los turnos
        public List<Turno> findAll() {
            return turnoJPA.findAll();
        }

            // Filtrar turnos por fecha y estado
        public List<Turno> findTurnosByFechaAndEstado(LocalDate fecha, String estado) {
            Turno.EstadoTurno estadoNormalizado = normalizeEstado(estado);
            if (estadoNormalizado == null) return Collections.emptyList(); // Retornamos lista vacía si el estado es inválido

            List<Turno> turnos = turnoJPA.findAll();
            return turnos.stream()
                    .filter(turno -> turno.getFecha().equals(fecha) && turno.getEstado().equals(estadoNormalizado))
                    .collect(Collectors.toList());
        }

        // Filtrar turnos por fecha (independientemente del estado)
        public List<Turno> findTurnosByFecha(LocalDate fecha) {
            List<Turno> turnos = turnoJPA.findAll();
            return turnos.stream()
                    .filter(turno -> turno.getFecha().equals(fecha))
                    .collect(Collectors.toList());
        }

        // Obtener todos los ciudadanos
        public List<Ciudadano> findAllCiudadanos() {
            return ciudadanoJPA.findAll();
        }
    }



