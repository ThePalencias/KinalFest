package org.kinalfest.model;
/** Entidad DetalleRecurso. */
public class DetalleRecurso {
    private int idDetalle;
    private int idEvento;
    private int idRecurso;
    private int cantidad;
    private double subtotal;
    public DetalleRecurso() {}
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int value) { this.idDetalle=value; }
    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int value) { this.idEvento=value; }
    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int value) { this.idRecurso=value; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int value) { this.cantidad=value; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double value) { this.subtotal=value; }
    @Override public String toString() { return "DetalleRecurso #" + idDetalle; }
}
