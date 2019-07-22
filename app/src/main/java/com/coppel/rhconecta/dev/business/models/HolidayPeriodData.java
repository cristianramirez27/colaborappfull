package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class HolidayPeriodData implements Serializable {

    private double num_dias;
    private String fec_ini;
    private String fec_fin;

    public HolidayPeriodData(double num_dias, String fec_ini, String fec_fin) {
        this.num_dias = num_dias;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
    }

    public double getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(double num_dias) {
        this.num_dias = num_dias;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }
}
