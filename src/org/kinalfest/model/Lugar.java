package org.kinalfest.model;
/** Entidad Lugar. */
public class Lugar {
    private int idLugar;
    private String nombreLugar;
    private String edificio;
    private int capacidad;
    private String codigoLugar;
    public Lugar() {}
    public int getIdLugar() { return idLugar; }
    public void setIdLugar(int value) { this.idLugar=value; }
    public String getNombreLugar() { return nombreLugar; }
    public void setNombreLugar(String value) { this.nombreLugar=value; }
    public String getEdificio() { return edificio; }
    public void setEdificio(String value) { this.edificio=value; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int value) { this.capacidad=value; }
    public String getCodigoLugar() { return codigoLugar; }
    public void setCodigoLugar(String value) { this.codigoLugar=value; }
    @Override public String toString() { return "Lugar #" + idLugar; }
}
