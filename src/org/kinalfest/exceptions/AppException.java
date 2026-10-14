package org.kinalfest.exceptions;
/** Error de negocio o de aplicación. @author Equipo KinalFest @version 1.0.0 */
public class AppException extends RuntimeException {
    public AppException(String message) { super(message); }
    public AppException(String message, Throwable cause) { super(message, cause); }
}
