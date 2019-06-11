package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class ReasonTravel implements Serializable {

        private int idu_motivo;
        private String des_motivo;
        private String des_especificacion;
        private int clv_subcuenta;
        private int num_subcuenta;
        private String des_subcuenta;


    public int getIdu_motivo() {
        return idu_motivo;
    }

    public void setIdu_motivo(int idu_motivo) {
        this.idu_motivo = idu_motivo;
    }

    public String getDes_motivo() {
        return des_motivo;
    }

    public void setDes_motivo(String des_motivo) {
        this.des_motivo = des_motivo;
    }

    public String getDes_especificacion() {
        return des_especificacion;
    }

    public void setDes_especificacion(String des_especificacion) {
        this.des_especificacion = des_especificacion;
    }

    public int getClv_subcuenta() {
        return clv_subcuenta;
    }

    public void setClv_subcuenta(int clv_subcuenta) {
        this.clv_subcuenta = clv_subcuenta;
    }

    public int getNum_subcuenta() {
        return num_subcuenta;
    }

    public void setNum_subcuenta(int num_subcuenta) {
        this.num_subcuenta = num_subcuenta;
    }

    public String getDes_subcuenta() {
        return des_subcuenta;
    }

    public void setDes_subcuenta(String des_subcuenta) {
        this.des_subcuenta = des_subcuenta;
    }
}
