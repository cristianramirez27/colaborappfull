package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class DatosAbonoOpcion implements Serializable {

    private Float importe = 0f;
    private String des_proceso;
    private String des_cambiar;
    private int clv_retiro;

    public DatosAbonoOpcion(Float importe, String des_proceso, String des_cambiar, int clv_retiro) {
        this.importe = importe;
        this.des_proceso = des_proceso;
        this.des_cambiar = des_cambiar;
        this.clv_retiro = clv_retiro;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public String getDes_proceso() {
        return des_proceso;
    }

    public void setDes_proceso(String des_proceso) {
        this.des_proceso = des_proceso;
    }

    public String getDes_cambiar() {
        return des_cambiar;
    }

    public void setDes_cambiar(String des_cambiar) {
        this.des_cambiar = des_cambiar;
    }

    public int getClv_retiro() {
        return clv_retiro;
    }

    public void setClv_retiro(int clv_retiro) {
        this.clv_retiro = clv_retiro;
    }
}
