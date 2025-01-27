package com.coppel.rhconecta.dev.business.models;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterJobScheduleData {

    private String hra_entrada;
    private String hra_salida;

    public LetterJobScheduleData(String hra_entrada, String hra_salida) {
        this.hra_entrada = hra_entrada;
        this.hra_salida = hra_salida;
    }

    public String getHra_entrada() {
        return hra_entrada;
    }

    public void setHra_entrada(String hra_entrada) {
        this.hra_entrada = hra_entrada;
    }

    public String getHra_salida() {
        return hra_salida;
    }

    public void setHra_salida(String hra_salida) {
        this.hra_salida = hra_salida;
    }
}
