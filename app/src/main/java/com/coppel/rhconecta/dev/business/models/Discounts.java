package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Discounts implements Serializable {

    private int empresa;
    private String descuento;
    private String descripciondes;
    private String ruta;

    public Discounts() {
    }

    public Discounts(int empresa, String descuento, String descripciondes, String ruta) {
        this.empresa = empresa;
        this.descuento = descuento;
        this.descripciondes = descripciondes;
        this.ruta = ruta;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getDescripciondes() {
        return descripciondes;
    }

    public void setDescripciondes(String descripciondes) {
        this.descripciondes = descripciondes;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
