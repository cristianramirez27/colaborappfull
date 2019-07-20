package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class HolidayPeriod implements Serializable {
    private int idu_estatus;
    private String nom_estatus;
    private String color;
    private String num_dias;
    private String fec_ini;
    private String fec_fin;
    private int idu_folio;

    public HolidayPeriod(int idu_estatus, String nom_estatus, String color, String num_dias, String fec_ini, String fec_fin, int idu_folio) {
        this.idu_estatus = idu_estatus;
        this.nom_estatus = nom_estatus;
        this.color = color;
        this.num_dias = num_dias;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
        this.idu_folio = idu_folio;
    }

    public HolidayPeriod(int idu_estatus, String num_dias, String fec_ini, String fec_fin) {
        this.idu_estatus = idu_estatus;
        this.num_dias = num_dias;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
    }

    public int getIdu_estatus() {
        return idu_estatus;
    }

    public void setIdu_estatus(int idu_estatus) {
        this.idu_estatus = idu_estatus;
    }

    public String getNom_estatus() {
        return nom_estatus;
    }

    public void setNom_estatus(String nom_estatus) {
        this.nom_estatus = nom_estatus;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(String num_dias) {
        this.num_dias = num_dias;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public int getIdu_folio() {
        return idu_folio;
    }

    public void setIdu_folio(int idu_folio) {
        this.idu_folio = idu_folio;
    }
}