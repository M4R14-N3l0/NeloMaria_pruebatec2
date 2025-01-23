                package com.example.controllers;

                import com.example.entities.Ciudadano;
                import com.example.entities.Turno;
                import com.example.persistence.GenericoJPA;
                import java.time.LocalDate;
                import java.util.List;
                import java.util.UUID;
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
                        // Buscar al ciudadano en la base de datos
                        Ciudadano ciudadano = ciudadanoJPA.findOne(ciudadanoId);

                        if (ciudadano != null) {
                            // Normalizar el estado
                            Turno.EstadoTurno estadoNormalizado = normalizeEstado(estado);
                            if (estadoNormalizado == null) return; // Detenemos la creación si el estado no es válido

                            // Verificar si el código ya existe en la base de datos
                            List<Turno> turnosExistentes = turnoJPA.findAll();
                            boolean codigoExiste = turnosExistentes.stream().anyMatch(turno -> turno.getCodigo().equals(codigo));

                            // Si el código ya existe, generamos uno nuevo
                            final String codigoFinal = codigoExiste ? generarCodigoUnico() : codigo;

                            // Crear el nuevo turno
                            Turno turno = new Turno();
                            turno.setCodigo(codigoFinal); // Usamos el código final
                            turno.setEstado(estadoNormalizado);
                            turno.setTramite(tramite);
                            turno.setFecha(fecha);
                            turno.setCiudadano(ciudadano);

                            // Guardar el turno en la base de datos
                            turnoJPA.create(turno);
                            System.out.println("Turno creado con éxito con el código: " + turno.getCodigo());
                        } else {
                            System.out.println("Ciudadano no encontrado.");
                        }
                    }

                    private String generarCodigoUnico() {
                        // Generamos un número aleatorio de 10 dígitos
                        long numero = (long) (Math.random() * 9000000000L) + 1000000000L; // Genera un número de 10 dígitos
                        return String.valueOf(numero); // Lo convertimos a String
                    }



                    // Listar todos los turnos
                    public List<Turno> findAll() {
                        return turnoJPA.findAll();
                    }


                    public List<Turno> findTurnosByFechaAndEstado(LocalDate fecha, Turno.EstadoTurno estado) {
                        LocalDate today = LocalDate.now();  // Obtener la fecha actual

                        return turnoJPA.findAll().stream()
                                .filter(turno -> !turno.getFecha().isBefore(fecha) && !turno.getFecha().isAfter(today) && turno.getEstado().equals(estado))
                                .collect(Collectors.toList());
                    }

                    // Filtrar turnos solo por fecha (mayor o igual que la fecha indicada y menor o igual que la fecha actual)
                    public List<Turno> findTurnosByFecha(LocalDate fecha) {
                        List<Turno> turnos = turnoJPA.findAll();
                        LocalDate today = LocalDate.now();  // Obtener la fecha actual

                        return turnos.stream()
                                .filter(turno -> !turno.getFecha().isBefore(fecha) && !turno.getFecha().isAfter(today))  // Fecha entre la fecha indicada y la fecha actual
                                .collect(Collectors.toList());
                    }

                    // Obtener todos los ciudadanos
                    public List<Ciudadano> findAllCiudadanos() {
                        return ciudadanoJPA.findAll();
                    }
                }



