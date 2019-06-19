package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class DetailRequestAuthorize implements Serializable {

    private int idu_tipoGasto;
    private double imp_total;


    public DetailRequestAuthorize(int idu_tipoGasto, double imp_total) {
        this.idu_tipoGasto = idu_tipoGasto;
        this.imp_total = imp_total;
    }

    public int getIdu_tipoGasto() {
        return idu_tipoGasto;
    }

    public void setIdu_tipoGasto(int idu_tipoGasto) {
        this.idu_tipoGasto = idu_tipoGasto;
    }

    public double getImp_total() {
        return imp_total;
    }

    public void setImp_total(double imp_total) {
        this.imp_total = imp_total;
    }
}
