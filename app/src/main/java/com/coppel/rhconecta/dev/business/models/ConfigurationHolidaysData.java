package com.coppel.rhconecta.dev.business.models;

import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import java.io.Serializable;
import java.util.HashMap;

public class ConfigurationHolidaysData implements Serializable {

    private  HolidaysPeriodsResponse holidaysPeriodsResponse;
    private HashMap<String, DaySelectedHoliday> daysConfiguration;

    public ConfigurationHolidaysData() {
    }

    public ConfigurationHolidaysData(HolidaysPeriodsResponse holidaysPeriodsResponse, HashMap<String, DaySelectedHoliday> daysConfiguration) {
        this.holidaysPeriodsResponse = holidaysPeriodsResponse;
        this.daysConfiguration = daysConfiguration;
    }

    public HolidaysPeriodsResponse getHolidaysPeriodsResponse() {
        return holidaysPeriodsResponse;
    }

    public void setHolidaysPeriodsResponse(HolidaysPeriodsResponse holidaysPeriodsResponse) {
        this.holidaysPeriodsResponse = holidaysPeriodsResponse;
    }

    public HashMap<String, DaySelectedHoliday> getDaysConfiguration() {
        return daysConfiguration;
    }

    public void setDaysConfiguration(HashMap<String, DaySelectedHoliday> daysConfiguration) {
        this.daysConfiguration = daysConfiguration;
    }
}
