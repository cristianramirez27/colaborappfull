package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class HolidayPeriodFolio implements Serializable {

    private int idu_folio;

    public HolidayPeriodFolio(int idu_folio) {
        this.idu_folio = idu_folio;
    }

    public int getIdu_folio() {
        return idu_folio;
    }

    public void setIdu_folio(int idu_folio) {
        this.idu_folio = idu_folio;
    }
}
