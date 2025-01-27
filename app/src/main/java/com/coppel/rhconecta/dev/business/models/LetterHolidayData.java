package com.coppel.rhconecta.dev.business.models;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterHolidayData {

    private String FechaIni;
    private String FechaFin;

    public LetterHolidayData(String fechaIni, String fechaFin) {
        FechaIni = fechaIni;
        FechaFin = fechaFin;
    }

    public String getFechaIni() {
        return FechaIni;
    }

    public void setFechaIni(String fechaIni) {
        FechaIni = fechaIni;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }
}
