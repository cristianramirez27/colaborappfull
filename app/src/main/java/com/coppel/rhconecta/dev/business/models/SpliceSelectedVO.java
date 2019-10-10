package com.coppel.rhconecta.dev.business.models;

import com.shrikanthravi.collapsiblecalendarview.data.Day;

import java.io.Serializable;
import java.util.List;

public class SpliceSelectedVO implements Serializable {

    private List<HolidayPeriod> periodos;
    private Day daySelected;
    private boolean addSpliceMarks;

    private List<Day>  listDaySelectedCurrent;
    private boolean showDetail = false;

    public SpliceSelectedVO(List<HolidayPeriod> periodos, Day daySelected) {
        this.periodos = periodos;
        this.daySelected = daySelected;
    }


    public List<Day> getListDaySelectedCurrent() {
        return listDaySelectedCurrent;
    }

    public void setListDaySelectedCurrent(List<Day> listDaySelectedCurrent) {
        this.listDaySelectedCurrent = listDaySelectedCurrent;
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

    public boolean isAddSpliceMarks() {
        return addSpliceMarks;
    }

    public void setAddSpliceMarks(boolean addSpliceMarks) {
        this.addSpliceMarks = addSpliceMarks;
    }

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }
}
