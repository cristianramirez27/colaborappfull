package com.coppel.rhconecta.dev.business.models;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterScheduleData {

    private String hrs_comida;


    public LetterScheduleData(String hrs_comida) {
        this.hrs_comida = hrs_comida;
    }

    public String getHrs_comida() {
        return hrs_comida;
    }

    public void setHrs_comida(String hrs_comida) {
        this.hrs_comida = hrs_comida;
    }
}
