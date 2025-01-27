package com.coppel.rhconecta.dev.business.models;

public class DateCalendar {
    private String numberDate;
    private String descriptionDate;
    private String date;

    public DateCalendar(String numberDate, String descriptionDate, String date) {
        this.numberDate = numberDate;
        this.descriptionDate = descriptionDate;
        this.date = date;
    }

    public String getNumberDate() {
        return numberDate;
    }

    public void setNumberDate(String numberDate) {
        this.numberDate = numberDate;
    }

    public String getDescriptionDate() {
        return descriptionDate;
    }

    public void setDescriptionDate(String descriptionDate) {
        this.descriptionDate = descriptionDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
