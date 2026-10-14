package org.kinalfest.model;
/** Entidad Recurso. */
public class Recurso {
    private int idRecurso;
    private String nombreRecurso;
    private double precioAlquiler;
    private int stock;
    private String proveedor;
    public Recurso() {}
    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int value) { this.idRecurso=value; }
    public String getNombreRecurso() { return nombreRecurso; }
    public void setNombreRecurso(String value) { this.nombreRecurso=value; }
    public double getPrecioAlquiler() { return precioAlquiler; }
    public void setPrecioAlquiler(double value) { this.precioAlquiler=value; }
    public int getStock() { return stock; }
    public void setStock(int value) { this.stock=value; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String value) { this.proveedor=value; }
    @Override public String toString() { return "Recurso #" + idRecurso; }
}
