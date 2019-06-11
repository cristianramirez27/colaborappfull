package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class ItinerayMulti  implements Serializable {

        private int clv_multiciudad;
        private String des_multiciudad;

    public int getClv_multiciudad() {
        return clv_multiciudad;
    }

    public void setClv_multiciudad(int clv_multiciudad) {
        this.clv_multiciudad = clv_multiciudad;
    }

    public String getDes_multiciudad() {
        return des_multiciudad;
    }

    public void setDes_multiciudad(String des_multiciudad) {
        this.des_multiciudad = des_multiciudad;
    }
}
