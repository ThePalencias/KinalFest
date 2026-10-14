package org.kinalfest.exceptions;
/** Error de conexión o ejecución en MySQL. @author Equipo KinalFest @version 1.0.0 */
public class DBException extends AppException {
    public DBException(String message, Throwable cause) { super(message, cause); }
}
