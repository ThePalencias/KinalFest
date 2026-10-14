package org.kinalfest.model;
/** Entidad Asistente. */
public class Asistente {
    private int idAsistente;
    private String nit;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    public Asistente() {}
    public int getIdAsistente() { return idAsistente; }
    public void setIdAsistente(int value) { this.idAsistente=value; }
    public String getNit() { return nit; }
    public void setNit(String value) { this.nit=value; }
    public String getNombres() { return nombres; }
    public void setNombres(String value) { this.nombres=value; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String value) { this.apellidos=value; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String value) { this.telefono=value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { this.email=value; }
    @Override public String toString() { return "Asistente #" + idAsistente; }
}
