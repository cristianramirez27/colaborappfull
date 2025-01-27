package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Acomodation implements Serializable {

        private int idu_tipoHospedaje;
        private String des_tipoHospedaje;


    public int getIdu_tipoHospedaje() {
        return idu_tipoHospedaje;
    }

    public void setIdu_tipoHospedaje(int idu_tipoHospedaje) {
        this.idu_tipoHospedaje = idu_tipoHospedaje;
    }

    public String getDes_tipoHospedaje() {
        return des_tipoHospedaje;
    }

    public void setDes_tipoHospedaje(String des_tipoHospedaje) {
        this.des_tipoHospedaje = des_tipoHospedaje;
    }
}
