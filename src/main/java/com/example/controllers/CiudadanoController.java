        package com.example.controllers;

        import com.example.entities.Ciudadano;
        import com.example.persistence.GenericoJPA;
        import java.util.List;

        public class CiudadanoController {

            private final GenericoJPA<Ciudadano, Long> ciudadanoJPA;

            public CiudadanoController() {
                this.ciudadanoJPA = new GenericoJPA<>(Ciudadano.class);
            }

            // Crear un nuevo ciudadano
            public void create(String nombre, String apellido) {
                Ciudadano ciudadano = new Ciudadano();
                ciudadano.setNombre(nombre);
                ciudadano.setApellido(apellido);
                ciudadanoJPA.create(ciudadano);
            }

            // Listar todos los ciudadanos
            public List<Ciudadano> findAll() {
                return ciudadanoJPA.findAll();
            }

            // Buscar un ciudadano por su ID
            public Ciudadano findOne(Long id) {
                return ciudadanoJPA.findOne(id);
            }

        }

