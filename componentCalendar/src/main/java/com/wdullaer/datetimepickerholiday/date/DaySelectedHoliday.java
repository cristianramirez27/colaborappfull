package com.wdullaer.datetimepickerholiday.date;

import java.io.Serializable;

public class DaySelectedHoliday implements Serializable {

    private String id;
    private int day;
    private int month;
    private int year;
    private boolean isHalfDay;



    public DaySelectedHoliday(String id, int day, int month, int year, boolean isHalfDay) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.isHalfDay = isHalfDay;
    }

    public DaySelectedHoliday(int day, boolean isHalfDay) {
        this.day = day;
        this.isHalfDay = isHalfDay;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
