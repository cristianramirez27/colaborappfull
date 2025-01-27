package com.wdullaer.datetimepickerholiday.date;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

public class DatePickerDialogTest {
    // isHighlighted
    @Test
    public void isHighlightedShouldReturnFalseIfNoHighlightedDaysAreSet() {
        DatePickerHolidayDialog dpd = DatePickerHolidayDialog.newInstance(new DatePickerHolidayDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerHolidayDialog view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        Assert.assertFalse(dpd.isHighlighted(1990, 1, 1));
    }

    @Test
    public void isHighlightedShouldReturnFalseIfHighlightedDoesNotContainSelection() {
        DatePickerHolidayDialog dpd = DatePickerHolidayDialog.newInstance(new DatePickerHolidayDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerHolidayDialog view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        Calendar highlighted = Calendar.getInstance();
        highlighted.set(Calendar.YEAR, 1990);
        highlighted.set(Calendar.MONTH, 1);
        highlighted.set(Calendar.DAY_OF_MONTH, 1);

        Calendar[] highlightedDays = {highlighted};

        dpd.setHighlightedDays(highlightedDays);

        Assert.assertFalse(dpd.isHighlighted(1990, 2, 1));
    }

    @Test
    public void isHighlightedShouldReturnTrueIfHighlightedDoesContainSelection() {
        DatePickerHolidayDialog dpd = DatePickerHolidayDialog.newInstance(new DatePickerHolidayDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerHolidayDialog view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        int year = 1990;
        int month = 1;
        int day = 1;

        Calendar highlighted = Calendar.getInstance();
        highlighted.set(Calendar.YEAR, year);
        highlighted.set(Calendar.MONTH, month);
        highlighted.set(Calendar.DAY_OF_MONTH, day);

        Calendar[] highlightedDays = {highlighted};

        dpd.setHighlightedDays(highlightedDays);

        Assert.assertTrue(dpd.isHighlighted(year, month, day));
    }

    @Test
    public void isHighlightedShouldBehaveCorrectlyInCustomTimezones() {
        String timeZoneString = "Americas/Los_Angeles";
        Calendar initial = Calendar.getInstance(TimeZone.getTimeZone(timeZoneString));
        DatePickerHolidayDialog dpd = DatePickerHolidayDialog.newInstance(new DatePickerHolidayDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerHolidayDialog view, int year, int monthOfYear, int dayOfMonth) {

            }
        }, initial);
        int year = 1990;
        int month = 1;
        int day = 1;

        Calendar highlighted = Calendar.getInstance(TimeZone.getTimeZone(timeZoneString));
        highlighted.set(Calendar.YEAR, year);
        highlighted.set(Calendar.MONTH, month);
        highlighted.set(Calendar.DAY_OF_MONTH, day);

        Calendar[] highlightedDays = {highlighted};

        dpd.setHighlightedDays(highlightedDays);

        Assert.assertTrue(dpd.isHighlighted(year, month, day));
    }
}
