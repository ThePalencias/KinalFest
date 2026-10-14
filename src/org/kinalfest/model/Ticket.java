package org.kinalfest.model;
/** Entidad Ticket. */
public class Ticket {
    private int idTicket;
    private int idEvento;
    private int idCajero;
    private java.time.LocalDateTime fechaEmision;
    private double total;
    public Ticket() {}
    public int getIdTicket() { return idTicket; }
    public void setIdTicket(int value) { this.idTicket=value; }
    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int value) { this.idEvento=value; }
    public int getIdCajero() { return idCajero; }
    public void setIdCajero(int value) { this.idCajero=value; }
    public java.time.LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(java.time.LocalDateTime value) { this.fechaEmision=value; }
    public double getTotal() { return total; }
    public void setTotal(double value) { this.total=value; }
    @Override public String toString() { return "Ticket #" + idTicket; }
}
