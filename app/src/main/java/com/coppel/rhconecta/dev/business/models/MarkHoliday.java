package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class MarkHoliday implements Serializable {

    private String fotoperfil;
    private int num_empmarca;
    private String nom_empmarca;


    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public int getNum_empmarca() {
        return num_empmarca;
    }

    public void setNum_empmarca(int num_empmarca) {
        this.num_empmarca = num_empmarca;
    }

    public String getNom_empmarca() {
        return nom_empmarca;
    }

    public void setNom_empmarca(String nom_empmarca) {
        this.nom_empmarca = nom_empmarca;
    }
}
