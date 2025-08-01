CREATE DATABASE IF NOT EXISTS bd_simple;
USE bd_simple;
CREATE TABLE estudiante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    edad INT
);
INSERT INTO estudiante (nombre, email, edad) VALUES
('Juan Perez', 'juan@email.com', 20),
('Maria Lopez', 'maria@email.com', 22),
('Carlos Ruiz', 'carlos@email.com', 19),
('Ana Torres', 'ana@email.com', 21),
('Luis Garcia', 'luis@email.com', 23);
SELECT * FROM estudiante;