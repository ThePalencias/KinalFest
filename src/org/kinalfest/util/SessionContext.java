package org.kinalfest.util;
import java.time.LocalDateTime;
/** Usuario autenticado y rol de la sesión actual. */
public final class SessionContext {
    private static SessionContext instancia;
    private final int id;
    private final String usuario, rol;
    private final LocalDateTime inicio;
    private SessionContext(int id, String usuario, String rol) {
        this.id=id; this.usuario=usuario; this.rol=rol; this.inicio=LocalDateTime.now();
    }
    public static synchronized void iniciar(int id, String usuario, String rol) {
        instancia=new SessionContext(id, usuario, rol);
    }
    public static synchronized SessionContext actual() { return instancia; }
    public static synchronized void cerrar() { instancia=null; }
    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getRol() { return rol; }
    public LocalDateTime getInicio() { return inicio; }
    public boolean tieneRol(String esperado) { return rol.equalsIgnoreCase(esperado); }
}
