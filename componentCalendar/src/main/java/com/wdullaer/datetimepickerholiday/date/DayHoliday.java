package com.wdullaer.datetimepickerholiday.date;

public class DayHoliday {

    private  int dayNumber;
    private boolean isHalf;

    public DayHoliday(int dayNumber, boolean isHalf) {
        this.dayNumber = dayNumber;
        this.isHalf = isHalf;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public boolean isHalf() {
        return isHalf;
    }

    public void setHalf(boolean half) {
        isHalf = half;
    }
}
