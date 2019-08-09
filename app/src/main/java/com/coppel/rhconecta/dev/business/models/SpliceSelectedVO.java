package com.coppel.rhconecta.dev.business.models;

import com.shrikanthravi.collapsiblecalendarview.data.Day;

import java.io.Serializable;
import java.util.List;

public class SpliceSelectedVO implements Serializable {

    private List<HolidayPeriod> periodos;
    private Day daySelected;

    public SpliceSelectedVO(List<HolidayPeriod> periodos, Day daySelected) {
        this.periodos = periodos;
        this.daySelected = daySelected;
    }

    public List<HolidayPeriod> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<HolidayPeriod> periodos) {
        this.periodos = periodos;
    }

    public Day getDaySelected() {
        return daySelected;
    }

    public void setDaySelected(Day daySelected) {
        this.daySelected = daySelected;
    }
}
