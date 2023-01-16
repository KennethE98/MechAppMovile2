package com.example.mechappmovile.Modelo;

public class mecanico {
    private String full_name;
    private String distancia;
    private String direccion;

    public mecanico() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String nombreMec) {
        full_name = nombreMec;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public mecanico(String nombreMec, String distancia, String direccion) {
        this.full_name = nombreMec;
        this.distancia = distancia;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "mecanico{" +
                "NombreMec='" + full_name + '\'' +
                ", distancia='" + distancia + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
