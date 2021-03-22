package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class DatePrima {
    private String month;
    private List<DateCalendar> dates;
    private String descriptionPeriod = null;

    public DatePrima(String month, String descriptionPeriod, List<DateCalendar> dates) {
        this.month = month;
        this.dates = dates;
        this.descriptionPeriod = descriptionPeriod;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<DateCalendar> getDates() {
        return dates;
    }

    public void setDates(List<DateCalendar> dates) {
        this.dates = dates;
    }

    public String getDescriptionPeriod() {
        return descriptionPeriod;
    }

    public void setDescriptionPeriod(String descriptionPeriod) {
        this.descriptionPeriod = descriptionPeriod;
    }
}
