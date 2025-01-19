    package com.example.entities;

    import jakarta.persistence.*;
    import java.time.LocalDate;

    @Entity
    public class Turno {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String codigo;

        @Enumerated(EnumType.STRING)
        private EstadoTurno estado;

        @Column(nullable = false)
        private String tramite;

        @Column(nullable = false)
        private LocalDate fecha;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ciudadano_id")  // Establece la clave foránea en la tabla turno
        private Ciudadano ciudadano;

        public enum EstadoTurno {
            ESPERA,
            ATENDIDO;
        }

        // Constructor, getters y setters
        public Turno() {}

        public Turno(String codigo, EstadoTurno estado, String tramite, LocalDate fecha, Ciudadano ciudadano) {
            this.codigo = codigo;
            this.estado = estado;
            this.tramite = tramite;
            this.fecha = fecha;
            this.ciudadano = ciudadano;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public EstadoTurno getEstado() {
            return estado;
        }

        public void setEstado(EstadoTurno estado) {
            this.estado = estado;
        }

        public String getTramite() {
            return tramite;
        }

        public void setTramite(String tramite) {
            this.tramite = tramite;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public Ciudadano getCiudadano() {
            return ciudadano;
        }

        public void setCiudadano(Ciudadano ciudadano) {
            this.ciudadano = ciudadano;
        }
    }
