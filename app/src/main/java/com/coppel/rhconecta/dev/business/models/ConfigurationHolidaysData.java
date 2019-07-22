package com.coppel.rhconecta.dev.business.models;

import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ConfigurationHolidaysData implements Serializable {

    private  HolidaysPeriodsResponse holidaysPeriodsResponse;
    private Map<String, List<DaySelectedHoliday>> daysConfiguration;
    private double totalDays;

    public ConfigurationHolidaysData() {
    }

    public ConfigurationHolidaysData(HolidaysPeriodsResponse holidaysPeriodsResponse, Map<String, List<DaySelectedHoliday>> daysConfiguration) {
        this.holidaysPeriodsResponse = holidaysPeriodsResponse;
        this.daysConfiguration = daysConfiguration;
    }

    public HolidaysPeriodsResponse getHolidaysPeriodsResponse() {
        return holidaysPeriodsResponse;
    }

    public void setHolidaysPeriodsResponse(HolidaysPeriodsResponse holidaysPeriodsResponse) {
        this.holidaysPeriodsResponse = holidaysPeriodsResponse;
    }

    public Map<String, List<DaySelectedHoliday>> getDaysConfiguration() {
        return daysConfiguration;
    }

    public void setDaysConfiguration(Map<String, List<DaySelectedHoliday>> daysConfiguration) {
        this.daysConfiguration = daysConfiguration;
    }

    public double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(double totalDays) {
        this.totalDays = totalDays;
    }
}
