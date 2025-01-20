        package com.example.entities;

        import jakarta.persistence.*;

        import java.util.List;

        @Entity
        public class Ciudadano {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
            @Column(nullable = false)
            private String nombre;
            @Column(nullable = false)
            private String apellido;

            @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
            private List<Turno> turnos;

            // Constructor, getters y setters
            public Ciudadano() {}

            public Ciudadano(String nombre, String apellido) {
                this.nombre = nombre;
                this.apellido = apellido;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            public String getApellido() {
                return apellido;
            }

            public void setApellido(String apellido) {
                this.apellido = apellido;
            }

            public List<Turno> getTurnos() {
                return turnos;
            }

            public void setTurnos(List<Turno> turnos) {
                this.turnos = turnos;
            }
        }
