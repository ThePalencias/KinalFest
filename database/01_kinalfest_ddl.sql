-- KinalFest - Esquema DDL para Gestión de Eventos
CREATE DATABASE IF NOT EXISTS db_kinalfest CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_kinalfest;

-- 1. Tabla de Roles para acceso al sistema
CREATE TABLE IF NOT EXISTS rol (
    idRol INT AUTO_INCREMENT PRIMARY KEY,
    nombreRol VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- 2. Tabla de Usuarios
CREATE TABLE IF NOT EXISTS usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    idRol INT NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY(idRol) REFERENCES rol(idRol) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 3. Tabla de Asistentes / Participantes (Equivalente a Cliente)
CREATE TABLE IF NOT EXISTS asistente (
    idAsistente INT AUTO_INCREMENT PRIMARY KEY,
    nit VARCHAR(30) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- 4. Tabla de Salones / Ubicaciones (Equivalente a Vehículo)
CREATE TABLE IF NOT EXISTS lugar (
    idLugar INT AUTO_INCREMENT PRIMARY KEY,
    nombreLugar VARCHAR(100) NOT NULL,
    edificio VARCHAR(60) NOT NULL,
    capacidad INT NOT NULL,
    codigoLugar VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT chk_lugar_capacidad CHECK(capacidad > 0)
) ENGINE=InnoDB;

-- 5. Tabla de Organizadores / Conferencistas (Equivalente a Mecánico)
CREATE TABLE IF NOT EXISTS organizador (
    idOrganizador INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    especialidad VARCHAR(100) NOT NULL DEFAULT 'General'
) ENGINE=InnoDB;

-- 6. Tabla de Recursos / Materiales de Eventos (Equivalente a Repuesto)
CREATE TABLE IF NOT EXISTS recurso (
    idRecurso INT AUTO_INCREMENT PRIMARY KEY,
    nombreRecurso VARCHAR(100) NOT NULL,
    precioAlquiler DECIMAL(12,2) NOT NULL,
    stock INT NOT NULL,
    proveedor VARCHAR(100) NOT NULL,
    CONSTRAINT chk_recurso_precio CHECK(precioAlquiler >= 0),
    CONSTRAINT chk_recurso_stock CHECK(stock >= 0)
) ENGINE=InnoDB;

-- 7. Tabla Principal de Eventos (Equivalente a Orden de Trabajo)
CREATE TABLE IF NOT EXISTS evento (
    idEvento INT AUTO_INCREMENT PRIMARY KEY,
    idLugar INT NOT NULL,
    idOrganizador INT NOT NULL,
    fechaEvento DATE NOT NULL,
    descripcion TEXT NOT NULL,
    costoEntrada DECIMAL(12,2) NOT NULL DEFAULT 0,
    aforoEsperado INT NOT NULL DEFAULT 0,
    estado VARCHAR(30) NOT NULL DEFAULT 'PROGRAMADO',
    aficheReferencia VARCHAR(500) NULL,
    aprobadoPor INT NULL,
    fechaAprobacion DATETIME NULL,
    CONSTRAINT fk_evento_lugar FOREIGN KEY(idLugar) REFERENCES lugar(idLugar) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_evento_organizador FOREIGN KEY(idOrganizador) REFERENCES organizador(idOrganizador) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_evento_aprobador FOREIGN KEY(aprobadoPor) REFERENCES usuario(idUsuario) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT chk_evento_aforo CHECK(aforoEsperado >= 0),
    CONSTRAINT chk_evento_costo CHECK(costoEntrada >= 0)
) ENGINE=InnoDB;

-- 8. Tabla Detalle de Recursos Asignados (Equivalente a Detalle Repuesto)
CREATE TABLE IF NOT EXISTS detalle_recurso (
    idDetalle INT AUTO_INCREMENT PRIMARY KEY,
    idEvento INT NOT NULL,
    idRecurso INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_detalle_evento FOREIGN KEY(idEvento) REFERENCES evento(idEvento) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detalle_recurso FOREIGN KEY(idRecurso) REFERENCES recurso(idRecurso) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_detalle_cantidad CHECK(cantidad > 0)
) ENGINE=InnoDB;

-- 9. Tabla de Entradas / Tickets Emitidos (Equivalente a Factura)
CREATE TABLE IF NOT EXISTS ticket (
    idTicket INT AUTO_INCREMENT PRIMARY KEY,
    idEvento INT NOT NULL,
    idCajero INT NOT NULL,
    fechaEmision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_ticket_evento FOREIGN KEY(idEvento) REFERENCES evento(idEvento) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_ticket_cajero FOREIGN KEY(idCajero) REFERENCES usuario(idUsuario) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;