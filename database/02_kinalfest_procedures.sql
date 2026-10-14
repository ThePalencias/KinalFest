-- KinalFest: Procedimientos Almacenados
USE db_kinalfest;
DELIMITER $$

-- Login
DROP PROCEDURE IF EXISTS sp_login$$
CREATE PROCEDURE sp_login(IN p_usuario VARCHAR(50), IN p_password VARCHAR(255))
BEGIN
  SELECT u.idUsuario, u.usuario, r.nombreRol AS rol 
  FROM usuario u 
  JOIN rol r ON r.idRol=u.idRol 
  WHERE u.usuario=p_usuario AND u.contrasenia=p_password 
  LIMIT 1;
END$$

-- CRUD Asistentes
DROP PROCEDURE IF EXISTS sp_AgregarAsistente$$
CREATE PROCEDURE sp_AgregarAsistente(IN p_nit VARCHAR(30), IN p_nombres VARCHAR(100), IN p_apellidos VARCHAR(100), IN p_telefono VARCHAR(20), IN p_email VARCHAR(150))
BEGIN
  INSERT INTO asistente(nit, nombres, apellidos, telefono, email) VALUES (p_nit, p_nombres, p_apellidos, p_telefono, p_email);
END$$

DROP PROCEDURE IF EXISTS sp_ListarAsistentes$$
CREATE PROCEDURE sp_ListarAsistentes()
BEGIN
  SELECT * FROM asistente;
END$$

DROP PROCEDURE IF EXISTS sp_ActualizarAsistente$$
CREATE PROCEDURE sp_ActualizarAsistente(IN p_idAsistente INT, IN p_nit VARCHAR(30), IN p_nombres VARCHAR(100), IN p_apellidos VARCHAR(100), IN p_telefono VARCHAR(20), IN p_email VARCHAR(150))
BEGIN
  UPDATE asistente SET nit=p_nit, nombres=p_nombres, apellidos=p_apellidos, telefono=p_telefono, email=p_email WHERE idAsistente=p_idAsistente;
END$$

DROP PROCEDURE IF EXISTS sp_EliminarAsistente$$
CREATE PROCEDURE sp_EliminarAsistente(IN p_idAsistente INT)
BEGIN
  DELETE FROM asistente WHERE idAsistente=p_idAsistente;
END$$

DROP PROCEDURE IF EXISTS sp_BuscarAsistentePorId$$
CREATE PROCEDURE sp_BuscarAsistentePorId(IN p_idAsistente INT)
BEGIN
  SELECT * FROM asistente WHERE idAsistente=p_idAsistente;
END$$

-- CRUD Lugares
DROP PROCEDURE IF EXISTS sp_AgregarLugar$$
CREATE PROCEDURE sp_AgregarLugar(IN p_nombreLugar VARCHAR(100), IN p_edificio VARCHAR(60), IN p_capacidad INT, IN p_codigoLugar VARCHAR(20))
BEGIN
  INSERT INTO lugar(nombreLugar, edificio, capacidad, codigoLugar) VALUES (p_nombreLugar, p_edificio, p_capacidad, p_codigoLugar);
END$$

DROP PROCEDURE IF EXISTS sp_ListarLugares$$
CREATE PROCEDURE sp_ListarLugares()
BEGIN
  SELECT * FROM lugar;
END$$

DROP PROCEDURE IF EXISTS sp_ActualizarLugar$$
CREATE PROCEDURE sp_ActualizarLugar(IN p_idLugar INT, IN p_nombreLugar VARCHAR(100), IN p_edificio VARCHAR(60), IN p_capacidad INT, IN p_codigoLugar VARCHAR(20))
BEGIN
  UPDATE lugar SET nombreLugar=p_nombreLugar, edificio=p_edificio, capacidad=p_capacidad, codigoLugar=p_codigoLugar WHERE idLugar=p_idLugar;
END$$

DROP PROCEDURE IF EXISTS sp_EliminarLugar$$
CREATE PROCEDURE sp_EliminarLugar(IN p_idLugar INT)
BEGIN
  DELETE FROM lugar WHERE idLugar=p_idLugar;
END$$

DROP PROCEDURE IF EXISTS sp_BuscarLugarPorId$$
CREATE PROCEDURE sp_BuscarLugarPorId(IN p_idLugar INT)
BEGIN
  SELECT * FROM lugar WHERE idLugar=p_idLugar;
END$$

-- CRUD Recursos
DROP PROCEDURE IF EXISTS sp_AgregarRecurso$$
CREATE PROCEDURE sp_AgregarRecurso(IN p_nombreRecurso VARCHAR(100), IN p_precioAlquiler DECIMAL(12,2), IN p_stock INT, IN p_proveedor VARCHAR(100))
BEGIN
  INSERT INTO recurso(nombreRecurso, precioAlquiler, stock, proveedor) VALUES (p_nombreRecurso, p_precioAlquiler, p_stock, p_proveedor);
END$$

DROP PROCEDURE IF EXISTS sp_ListarRecursos$$
CREATE PROCEDURE sp_ListarRecursos()
BEGIN
  SELECT * FROM recurso;
END$$

DROP PROCEDURE IF EXISTS sp_ActualizarRecurso$$
CREATE PROCEDURE sp_ActualizarRecurso(IN p_idRecurso INT, IN p_nombreRecurso VARCHAR(100), IN p_precioAlquiler DECIMAL(12,2), IN p_stock INT, IN p_proveedor VARCHAR(100))
BEGIN
  UPDATE recurso SET nombreRecurso=p_nombreRecurso, precioAlquiler=p_precioAlquiler, stock=p_stock, proveedor=p_proveedor WHERE idRecurso=p_idRecurso;
END$$

DROP PROCEDURE IF EXISTS sp_EliminarRecurso$$
CREATE PROCEDURE sp_EliminarRecurso(IN p_idRecurso INT)
BEGIN
  DELETE FROM recurso WHERE idRecurso=p_idRecurso;
END$$

DROP PROCEDURE IF EXISTS sp_BuscarRecursoPorId$$
CREATE PROCEDURE sp_BuscarRecursoPorId(IN p_idRecurso INT)
BEGIN
  SELECT * FROM recurso WHERE idRecurso=p_idRecurso;
END$$

-- CRUD Organizadores
DROP PROCEDURE IF EXISTS sp_AgregarOrganizador$$
CREATE PROCEDURE sp_AgregarOrganizador(IN p_nombres VARCHAR(100), IN p_apellidos VARCHAR(100), IN p_telefono VARCHAR(20), IN p_especialidad VARCHAR(100))
BEGIN
  INSERT INTO organizador(nombres, apellidos, telefono, especialidad) VALUES (p_nombres, p_apellidos, p_telefono, p_especialidad);
END$$

DROP PROCEDURE IF EXISTS sp_ListarOrganizadores$$
CREATE PROCEDURE sp_ListarOrganizadores()
BEGIN
  SELECT * FROM organizador;
END$$

DROP PROCEDURE IF EXISTS sp_ActualizarOrganizador$$
CREATE PROCEDURE sp_ActualizarOrganizador(IN p_idOrganizador INT, IN p_nombres VARCHAR(100), IN p_apellidos VARCHAR(100), IN p_telefono VARCHAR(20), IN p_especialidad VARCHAR(100))
BEGIN
  UPDATE organizador SET nombres=p_nombres, apellidos=p_apellidos, telefono=p_telefono, especialidad=p_especialidad WHERE idOrganizador=p_idOrganizador;
END$$

DROP PROCEDURE IF EXISTS sp_EliminarOrganizador$$
CREATE PROCEDURE sp_EliminarOrganizador(IN p_idOrganizador INT)
BEGIN
  DELETE FROM organizador WHERE idOrganizador=p_idOrganizador;
END$$

DROP PROCEDURE IF EXISTS sp_BuscarOrganizadorPorId$$
CREATE PROCEDURE sp_BuscarOrganizadorPorId(IN p_idOrganizador INT)
BEGIN
  SELECT * FROM organizador WHERE idOrganizador=p_idOrganizador;
END$$

-- Transacciones e Integración de Eventos
DROP PROCEDURE IF EXISTS sp_RegistrarEvento$$
CREATE PROCEDURE sp_RegistrarEvento(IN p_idLugar INT, IN p_idOrganizador INT, IN p_fecha DATE, IN p_descripcion TEXT, IN p_costo DECIMAL(12,2), IN p_aforo INT, IN p_afiche VARCHAR(500))
BEGIN
  IF p_aforo < 0 THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Aforo invalido'; END IF;
  INSERT INTO evento(idLugar, idOrganizador, fechaEvento, descripcion, costoEntrada, aforoEsperado, aficheReferencia, estado) 
  VALUES(p_idLugar, p_idOrganizador, p_fecha, p_descripcion, p_costo, p_aforo, p_afiche, 'PROGRAMADO');
END$$

DROP PROCEDURE IF EXISTS sp_ListarEventos$$
CREATE PROCEDURE sp_ListarEventos()
BEGIN
  SELECT idEvento, idLugar, idOrganizador, fechaEvento, descripcion, costoEntrada, estado FROM evento;
END$$

DROP PROCEDURE IF EXISTS sp_AgregarDetalleRecurso$$
CREATE PROCEDURE sp_AgregarDetalleRecurso(IN p_idEvento INT, IN p_idRecurso INT, IN p_cantidad INT)
BEGIN
  DECLARE v_precio DECIMAL(12,2);
  DECLARE v_stock INT;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN ROLLBACK; RESIGNAL; END;
  START TRANSACTION;
  SELECT precioAlquiler, stock INTO v_precio, v_stock FROM recurso WHERE idRecurso=p_idRecurso FOR UPDATE;
  IF v_precio IS NULL OR p_cantidad<=0 OR v_stock<p_cantidad THEN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Recurso inexistente, cantidad invalida o stock insuficiente'; 
  END IF;
  INSERT INTO detalle_recurso(idEvento, idRecurso, cantidad, subtotal) VALUES(p_idEvento, p_idRecurso, p_cantidad, v_precio*p_cantidad);
  UPDATE recurso SET stock=stock-p_cantidad WHERE idRecurso=p_idRecurso;
  COMMIT;
END$$

DROP PROCEDURE IF EXISTS sp_AprobarEvento$$
CREATE PROCEDURE sp_AprobarEvento(IN p_idEvento INT, IN p_idDirector INT)
BEGIN
  IF NOT EXISTS(SELECT 1 FROM usuario u JOIN rol r ON r.idRol=u.idRol WHERE u.idUsuario=p_idDirector AND r.nombreRol='Director de Eventos') THEN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Solo el Director de Eventos puede aprobar'; 
  END IF;
  UPDATE evento SET estado='APROBADO', aprobadoPor=p_idDirector, fechaAprobacion=NOW() 
  WHERE idEvento=p_idEvento AND estado IN ('PROGRAMADO','EN_REVISION');
  IF ROW_COUNT()=0 THEN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Evento inexistente o estado no permite aprobacion'; 
  END IF;
END$$

DROP PROCEDURE IF EXISTS sp_EmitirTicket$$
CREATE PROCEDURE sp_EmitirTicket(IN p_idEvento INT, IN p_idCajero INT)
BEGIN
  DECLARE v_total DECIMAL(12,2);
  DECLARE v_estado VARCHAR(30);
  DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN ROLLBACK; RESIGNAL; END;
  START TRANSACTION;
  IF NOT EXISTS(SELECT 1 FROM usuario u JOIN rol r ON r.idRol=u.idRol WHERE u.idUsuario=p_idCajero AND r.nombreRol='Taquillero') THEN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Solo el Taquillero puede emitir tickets'; 
  END IF;
  SELECT estado, costoEntrada+(SELECT COALESCE(SUM(subtotal),0) FROM detalle_recurso WHERE idEvento=p_idEvento) 
  INTO v_estado, v_total FROM evento WHERE idEvento=p_idEvento FOR UPDATE;
  IF v_estado IS NULL OR v_estado<>'APROBADO' THEN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='El evento requiere aprobacion antes de emitir tickets'; 
  END IF;
  INSERT INTO ticket(idEvento, idCajero, total) VALUES(p_idEvento, p_idCajero, v_total);
  UPDATE evento SET estado='CONFIRMADO' WHERE idEvento=p_idEvento;
  COMMIT;
END$$

DELIMITER ;