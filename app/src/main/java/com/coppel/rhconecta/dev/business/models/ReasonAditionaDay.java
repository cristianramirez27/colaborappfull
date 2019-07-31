package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class ReasonAditionaDay implements Serializable {

    private int idu_motivo;
    private String nom_motivo;

    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIdu_motivo() {
        return idu_motivo;
    }

    public void setIdu_motivo(int idu_motivo) {
        this.idu_motivo = idu_motivo;
    }

    public String getNom_motivo() {
        return nom_motivo;
    }

    public void setNom_motivo(String nom_motivo) {
        this.nom_motivo = nom_motivo;
    }
}
