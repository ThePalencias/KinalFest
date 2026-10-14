package org.kinalfest.model;
/** Entidad Organizador. */
public class Organizador {
    private int idOrganizador;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String especialidad;
    public Organizador() {}
    public int getIdOrganizador() { return idOrganizador; }
    public void setIdOrganizador(int value) { this.idOrganizador=value; }
    public String getNombres() { return nombres; }
    public void setNombres(String value) { this.nombres=value; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String value) { this.apellidos=value; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String value) { this.telefono=value; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String value) { this.especialidad=value; }
    @Override public String toString() { return "Organizador #" + idOrganizador; }
}
