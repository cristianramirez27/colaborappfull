package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class DatePrima {
    private String month;
    private List<DateCalendar> dates;

    public DatePrima(String month, List<DateCalendar> dates) {
        this.month = month;
        this.dates = dates;
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
}
