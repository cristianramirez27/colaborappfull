package com.coppel.rhconecta.dev.business.models;

import com.shrikanthravi.collapsiblecalendarview.data.Day;

import java.io.Serializable;
import java.util.List;

public class CalendarProposedData implements Serializable {

    private HolidayPeriod period;
    private ColaboratorHoliday colaborator;
    private List<Day> listDaySelected;

    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    private int num_mes;
    private int num_anio;
    private boolean showLabel;

    public CalendarProposedData(HolidayPeriod period) {
        this.period = period;
    }

    public CalendarProposedData(ColaboratorHoliday colaborator) {
        this.colaborator = colaborator;
    }

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

    public boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
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

    public int getNum_mes() {
        return num_mes;
    }

    public void setNum_mes(int num_mes) {
        this.num_mes = num_mes;
    }

    public int getNum_anio() {
        return num_anio;
    }

    public void setNum_anio(int num_anio) {
        this.num_anio = num_anio;
    }
}
