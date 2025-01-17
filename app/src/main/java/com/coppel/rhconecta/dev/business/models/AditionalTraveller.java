package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class AditionalTraveller implements Serializable {

        private int total_viajeros;
        private int num_colaborador;
        private String nom_colaborador;
        private int num_centro;
        private String nom_centro;
        private int num_puesto;
        private String nom_puesto;
        private String empalme = "";

    public int getTotal_viajeros() {
        return total_viajeros;
    }

    public void setTotal_viajeros(int total_viajeros) {
        this.total_viajeros = total_viajeros;
    }

    public int getNum_colaborador() {
        return num_colaborador;
    }

    public void setNum_colaborador(int num_colaborador) {
        this.num_colaborador = num_colaborador;
    }

    public String getNom_colaborador() {
        return nom_colaborador;
    }

    public void setNom_colaborador(String nom_colaborador) {
        this.nom_colaborador = nom_colaborador;
    }

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

    public int getNum_puesto() {
        return num_puesto;
    }

    public void setNum_puesto(int num_puesto) {
        this.num_puesto = num_puesto;
    }

    public String getNom_puesto() {
        return nom_puesto;
    }

    public void setNom_puesto(String nom_puesto) {
        this.nom_puesto = nom_puesto;
    }

    public String getEmpalme() {
        return empalme;
    }

    public void setEmpalme(String empalme) {
        this.empalme = empalme;
    }
}
