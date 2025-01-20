-- Crear la base de datos
CREATE DATABASE turnos_db;
USE turnos_db;

DROP database turnos_db;

select * from ciudadano;

-- Crear la tabla persona
CREATE TABLE ciudadano (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL
);

-- Crear la tabla tarjetas para la relación uno a muchos
CREATE TABLE turno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL,
    estado ENUM('espera', 'atendido') NOT NULL,
    tramite VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    ciudadano_id BIGINT NOT NULL,
    FOREIGN KEY (ciudadano_id) REFERENCES ciudadano(id) ON DELETE CASCADE
);

-- Insertar registros en la tabla persona
INSERT INTO ciudadano (nombre, apellido) VALUES
('Juan', 'Perez'),
('Maria', 'Gomez'),
('Carlos', 'Lopez'),
('Ana', 'Martinez'),
('Luis', 'Garcia');

-- Insertar registros en la tabla tarjetas
INSERT INTO turno (codigo, estado, tramite, fecha, ciudadano_id) VALUES
('1111111111', 'atendido', 'alta', '2020-01-01', 1),
('2222222222','espera', 'alta', '2020-02-01', 1),
('3333333333', 'atendido', 'alta', '2021-03-01', 2),
('4444444444','espera', 'alta', '2021-04-01', 2),
('5555555555', 'atendido', 'alta', '2022-05-01', 3),
('6666666666','espera', 'alta', '2022-06-01', 3),
('7777777777', 'atendido', 'alta', '2023-07-01', 4),
('8888888888','espera', 'alta', '2023-08-01', 4),
('9999999999', 'atendido', 'alta', '2024-09-015', 5),
('0000000000','espera', 'alta', '2024-10-01', 5);

SELECT DISTINCT estado FROM turno;



