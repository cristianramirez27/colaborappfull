package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class ColaboratorHoliday implements Serializable {

    private String nom_empleado;
    private String fotoperfil;
    private String num_empleado;
    private int num_centro;
    private boolean hasSplice;

    public ColaboratorHoliday(String nom_empleado, String fotoperfil, String num_empleado, int num_centro, boolean hasSplice) {
        this.nom_empleado = nom_empleado;
        this.fotoperfil = fotoperfil;
        this.num_empleado = num_empleado;
        this.num_centro = num_centro;
        this.hasSplice = hasSplice;
    }

    public ColaboratorHoliday(String nom_empleado, String fotoperfil, String num_empleado) {
        this.nom_empleado = nom_empleado;
        this.fotoperfil = fotoperfil;
        this.num_empleado = num_empleado;
    }

    public String getNom_empleado() {
        return nom_empleado;
    }

    public void setNom_empleado(String nom_empleado) {
        this.nom_empleado = nom_empleado;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public int getNum_centro() {
        return num_centro;
    }

    public void setNum_centro(int num_centro) {
        this.num_centro = num_centro;
    }


    public boolean isHasSplice() {
        return hasSplice;
    }

    public void setHasSplice(boolean hasSplice) {
        this.hasSplice = hasSplice;
    }
}
