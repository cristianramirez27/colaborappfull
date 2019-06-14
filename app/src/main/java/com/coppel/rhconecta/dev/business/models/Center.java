package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Center implements Serializable {
    private int num_centro;
    private String nom_centro;

    private transient  boolean selected;

    public int getNum_centro() {
        return num_centro;
    }

    public void setNum_centro(int num_centro) {
        this.num_centro = num_centro;
    }

    public String getNom_centro() {
        return nom_centro;
    }

    public void setNom_centro(String nom_centro) {
        this.nom_centro = nom_centro;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}