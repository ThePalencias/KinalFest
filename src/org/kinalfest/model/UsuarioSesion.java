package org.kinalfest.model;
/** Entidad UsuarioSesion. */
public class UsuarioSesion {
    private int idUsuario;
    private String usuario;
    private String rol;
    public UsuarioSesion() {}
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int value) { this.idUsuario=value; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String value) { this.usuario=value; }
    public String getRol() { return rol; }
    public void setRol(String value) { this.rol=value; }
    @Override public String toString() { return "UsuarioSesion #" + idUsuario; }
}
