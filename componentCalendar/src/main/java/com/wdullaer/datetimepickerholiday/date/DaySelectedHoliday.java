package com.wdullaer.datetimepickerholiday.date;

import java.io.Serializable;

public class DaySelectedHoliday implements Serializable {

    private int day;
    private boolean isHalfDay;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isHalfDay() {
        return isHalfDay;
    }

    public void setHalfDay(boolean halfDay) {
        isHalfDay = halfDay;
    }
}
