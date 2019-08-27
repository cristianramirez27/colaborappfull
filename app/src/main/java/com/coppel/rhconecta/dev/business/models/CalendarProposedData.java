package com.coppel.rhconecta.dev.business.models;

import com.shrikanthravi.collapsiblecalendarview.data.Day;

import java.io.Serializable;
import java.util.List;

public class CalendarProposedData implements Serializable {

    private HolidayPeriod period;
    private ColaboratorHoliday colaborator;
    private List<Day> listDaySelected;

    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    public CalendarProposedData(ColaboratorHoliday colaborator, List<Day> listDaySelected) {
        this.colaborator = colaborator;
        this.listDaySelected = listDaySelected;
    }

    public CalendarProposedData(ColaboratorHoliday colaborator, HolidayPeriod period) {
        this.period = period;
        this.colaborator = colaborator;
    }

    public CalendarProposedData(HolidaysPeriodsResponse holidaysPeriodsResponse,HolidayPeriod period) {
        this.period = period;
        this.holidaysPeriodsResponse = holidaysPeriodsResponse;
    }


    public HolidayPeriod getPeriod() {
        return period;
    }

    public void setPeriod(HolidayPeriod period) {
        this.period = period;
    }

    public ColaboratorHoliday getColaborator() {
        return colaborator;
    }

    public void setColaborator(ColaboratorHoliday colaborator) {
        this.colaborator = colaborator;
    }

    public HolidaysPeriodsResponse getHolidaysPeriodsResponse() {
        return holidaysPeriodsResponse;
    }

    public void setHolidaysPeriodsResponse(HolidaysPeriodsResponse holidaysPeriodsResponse) {
        this.holidaysPeriodsResponse = holidaysPeriodsResponse;
    }


    public List<Day> getListDaySelected() {
        return listDaySelected;
    }

    public void setListDaySelected(List<Day> listDaySelected) {
        this.listDaySelected = listDaySelected;
    }
}
