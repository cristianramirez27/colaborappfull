package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Estatus implements Serializable {
    private int idu_estatus_factura;
    private String nom_estatus_liq;

    private transient  boolean selected;

    public int getIdu_estatus_factura() {
        return idu_estatus_factura;
    }

    public void setIdu_estatus_factura(int idu_estatus_factura) {
        this.idu_estatus_factura = idu_estatus_factura;
    }

    public String getNom_estatus_liq() {
        return nom_estatus_liq;
    }

    public void setNom_estatus_liq(String nom_estatus_liq) {
        this.nom_estatus_liq = nom_estatus_liq;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}