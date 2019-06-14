package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Itinerary implements Serializable {

        private int id_itinerario;
        private String fec_salida;
        private String fec_cambio;
        private String des_ciudadO;
        private String des_ciudadD;
        private int num_noches;
        private int clv_transporte;
        private int num_ciudadO;
        private int num_ciudadD;


    public int getId_itinerario() {
        return id_itinerario;
    }

    public void setId_itinerario(int id_itinerario) {
        this.id_itinerario = id_itinerario;
    }

    public String getFec_salida() {
        return fec_salida;
    }

    public void setFec_salida(String fec_salida) {
        this.fec_salida = fec_salida;
    }

    public String getFec_cambio() {
        return fec_cambio;
    }

    public void setFec_cambio(String fec_cambio) {
        this.fec_cambio = fec_cambio;
    }

    public String getDes_ciudadO() {
        return des_ciudadO;
    }

    public void setDes_ciudadO(String des_ciudadO) {
        this.des_ciudadO = des_ciudadO;
    }

    public String getDes_ciudadD() {
        return des_ciudadD;
    }

    public void setDes_ciudadD(String des_ciudadD) {
        this.des_ciudadD = des_ciudadD;
    }

    public int getNum_noches() {
        return num_noches;
    }

    public void setNum_noches(int num_noches) {
        this.num_noches = num_noches;
    }

    public int getClv_transporte() {
        return clv_transporte;
    }

    public void setClv_transporte(int clv_transporte) {
        this.clv_transporte = clv_transporte;
    }

    public int getNum_ciudadO() {
        return num_ciudadO;
    }

    public void setNum_ciudadO(int num_ciudadO) {
        this.num_ciudadO = num_ciudadO;
    }

    public int getNum_ciudadD() {
        return num_ciudadD;
    }

    public void setNum_ciudadD(int num_ciudadD) {
        this.num_ciudadD = num_ciudadD;
    }
}
