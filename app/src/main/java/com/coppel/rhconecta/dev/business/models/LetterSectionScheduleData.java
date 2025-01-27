package com.coppel.rhconecta.dev.business.models;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterSectionScheduleData {


    private String DiaInicio;
    private String DiaFin;

    public LetterSectionScheduleData(String diaInicio, String diaFin) {
        DiaInicio = diaInicio;
        DiaFin = diaFin;
    }

    public String getDiaInicio() {
        return DiaInicio;
    }

    public void setDiaInicio(String diaInicio) {
        DiaInicio = diaInicio;
    }

    public String getDiaFin() {
        return DiaFin;
    }

    public void setDiaFin(String diaFin) {
        DiaFin = diaFin;
    }
}
