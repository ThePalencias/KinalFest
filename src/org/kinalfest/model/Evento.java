package org.kinalfest.model;
/** Entidad Evento. */
public class Evento {
    private int idEvento;
    private int idLugar;
    private int idOrganizador;
    private java.time.LocalDate fechaEvento;
    private String descripcion;
    private double costoEntrada;
    private String estado;
    public Evento() {}
    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int value) { this.idEvento=value; }
    public int getIdLugar() { return idLugar; }
    public void setIdLugar(int value) { this.idLugar=value; }
    public int getIdOrganizador() { return idOrganizador; }
    public void setIdOrganizador(int value) { this.idOrganizador=value; }
    public java.time.LocalDate getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(java.time.LocalDate value) { this.fechaEvento=value; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion=value; }
    public double getCostoEntrada() { return costoEntrada; }
    public void setCostoEntrada(double value) { this.costoEntrada=value; }
    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado=value; }
    @Override public String toString() { return "Evento #" + idEvento; }
}
