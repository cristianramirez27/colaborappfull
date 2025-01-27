package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class DetailRequest implements Serializable {

    private int idu_tipoGasto;
    private String des_tipoGasto;
    private String imp_total;

    private int clv_editar;

    private transient double newAmount;

    public DetailRequest(int idu_tipoGasto, String des_tipoGasto, String imp_total) {
        this.idu_tipoGasto = idu_tipoGasto;
        this.des_tipoGasto = des_tipoGasto;
        this.imp_total = imp_total;
    }

    public int getIdu_tipoGasto() {
        return idu_tipoGasto;
    }

    public void setIdu_tipoGasto(int idu_tipoGasto) {
        this.idu_tipoGasto = idu_tipoGasto;
    }

    public String getDes_tipoGasto() {
        return des_tipoGasto;
    }

    public void setDes_tipoGasto(String des_tipoGasto) {
        this.des_tipoGasto = des_tipoGasto;
    }

    public String getImp_total() {
        return imp_total;
    }

    public void setImp_total(String imp_total) {
        this.imp_total = imp_total;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }


    public int getClv_editar() {
        return clv_editar;
    }

    public void setClv_editar(int clv_editar) {
        this.clv_editar = clv_editar;
    }
}
