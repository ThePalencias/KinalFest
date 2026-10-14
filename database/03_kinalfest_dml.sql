-- KinalFest: Datos de Prueba
USE db_kinalfest;

-- Roles
INSERT INTO rol(nombreRol) VALUES 
('Director de Eventos'), ('Organizador'), ('Taquillero'), ('Logistica'), ('Coordinador');

-- Usuarios
INSERT INTO usuario(nombre, usuario, contrasenia, idRol) VALUES
('Administrador KinalFest', 'admin', '123456', 1),
('Carlos Lopez', 'clopez', '123456', 2),
('Ana Taquillera', 'ataquilla', '123456', 3),
('Rosa Logistica', 'rlogistica', '123456', 4),
('Luis Coordinador', 'lcoordinador', '123456', 5);

-- Asistentes (Mínimo 5 registros)
INSERT INTO asistente(nit, nombres, apellidos, telefono, email) VALUES
('CF', 'Juan', 'Perez', '5555-1234', 'juan.perez@gmail.com'),
('1002003', 'Maria', 'Gonzalez', '4444-5678', 'maria.gonzalez@gmail.com'),
('1002004', 'Pedro', 'Ramirez', '3333-9012', 'pedro.ramirez@gmail.com'),
('1002005', 'Sofia', 'Martinez', '2222-3456', 'sofia.martinez@gmail.com'),
('1002006', 'Diego', 'Hernandez', '1111-7890', 'diego.hernandez@gmail.com');

-- Lugares / Auditórium
INSERT INTO lugar(nombreLugar, edificio, capacidad, codigoLugar) VALUES
('Auditorio Central', 'Edificio A', 500, 'AUD-A1'),
('Laboratorio de Computo 1', 'Edificio B', 40, 'LAB-B1'),
('Cancha Polideportiva', 'Exterior', 1000, 'EXT-C1'),
('Salón de Usos Múltiples', 'Edificio C', 150, 'SUM-C2'),
('Plaza Central', 'Exterior', 800, 'EXT-P1');

-- Organizadores
INSERT INTO organizador(nombres, apellidos, telefono, especialidad) VALUES
('Mario', 'Gomez', '5550-1001', 'Tecnologia'),
('Lucia', 'Morales', '5550-1002', 'Robotica'),
('Fernando', 'Rios', '5550-1003', 'Diseno Digital'),
('Elena', 'Castillo', '5550-1004', 'Redes'),
('Gustavo', 'Mendoza', '5550-1005', 'Emprendimiento');

-- Recursos / Materiales
INSERT INTO recurso(nombreRecurso, precioAlquiler, stock, proveedor) VALUES
('Proyector 4K', 150.00, 10, 'Sistemas AV'),
('Microfono Inalambrico', 50.00, 20, 'Audio Pro'),
('Stand Promocional', 200.00, 15, 'Estructuras Kinal'),
('Set de Sillas (x10)', 30.00, 50, 'Mobiliario Eventos'),
('Caja de Sonido 1000W', 300.00, 8, 'Audio Pro');

-- Eventos Programados
INSERT INTO evento(idLugar, idOrganizador, fechaEvento, descripcion, costoEntrada, aforoEsperado, estado) VALUES
(1, 1, '2026-11-10', 'Conferencia de Inteligencia Artificial', 50.00, 300, 'PROGRAMADO'),
(2, 2, '2026-11-11', 'Taller de Mecatrónica y Arduino', 75.00, 35, 'EN_REVISION'),
(3, 3, '2026-11-12', 'Exposición de Arte Digital', 25.00, 500, 'PROGRAMADO'),
(4, 4, '2026-11-13', 'Seminario de Ciberseguridad', 100.00, 120, 'PROGRAMADO'),
(5, 5, '2026-11-14', 'Feria de Emprendimiento Tecnológico', 10.00, 600, 'PROGRAMADO');

-- Asignación de Recursos a Eventos
INSERT INTO detalle_recurso(idEvento, idRecurso, cantidad, subtotal) VALUES
(1, 1, 1, 150.00),
(2, 2, 2, 100.00),
(3, 3, 3, 600.00),
(4, 4, 5, 150.00),
(5, 5, 2, 600.00);

-- Eventos previas aprobados para la emisión de tickets
INSERT INTO evento(idLugar, idOrganizador, fechaEvento, descripcion, costoEntrada, aforoEsperado, estado, aprobadoPor, fechaAprobacion) VALUES
(1, 1, '2026-10-01', 'Inauguración KinalFest', 20.00, 450, 'CONFIRMADO', 1, '2026-10-02'),
(2, 2, '2026-10-02', 'Hackathon 2026', 30.00, 30, 'CONFIRMADO', 1, '2026-10-03'),
(3, 3, '2026-10-03', 'Torneo eSports', 15.00, 400, 'CONFIRMADO', 1, '2026-10-04'),
(4, 4, '2026-10-04', 'Taller de Desarrollo Web', 40.00, 100, 'CONFIRMADO', 1, '2026-10-05'),
(5, 5, '2026-10-05', 'Cierre Cultural', 25.00, 500, 'CONFIRMADO', 1, '2026-10-06');

-- Emisión de Tickets de Prueba
INSERT INTO ticket(idEvento, idCajero, total) VALUES 
(6, 3, 170.00),
(7, 3, 130.00),
(8, 3, 615.00),
(9, 3, 190.00),
(10, 3, 625.00);