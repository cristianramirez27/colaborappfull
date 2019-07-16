package com.coppel.rhconecta.dev.business.utils;


import android.util.Log;

import com.wdullaer.materialdatepicker.date.DatePickerDialog;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;

//import com.wdullaer.materialdatepicker.date.DatePickerHolidaysDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fausto on 6/29/16.
 */
public class DateTimeUtil {

    private static String[] months = new String[]{
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Novimebre",
            "Diciembre",
    };

    private static String[] days = new String[]{
            "Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado",
            "Domingo"
    };

    public static DatePickerDialog getMaterialDatePicker(DatePickerDialog.OnDateSetListener callback){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //dpd.setVersion(DatePickerHolidayDialog.Version.VERSION_2);

        //dpd.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);

        return dpd;
    }


    public static DatePickerHolidayDialog getMaterialDatePicker(DatePickerHolidayDialog.OnDateSetListener callback){
        Calendar now = Calendar.getInstance();
        DatePickerHolidayDialog dpd = DatePickerHolidayDialog.newInstance(
                callback,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //dpd.setVersion(DatePickerHolidayDialog.Version.VERSION_2);

        //dpd.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);

        return dpd;
    }

    public static String formatDate(int dayOfMonth, int monthOfYear, int year){

        String day ="";
        String month ="";
        int iMonth = ++monthOfYear;
        if(dayOfMonth < 10){
            day = String.format("0%s",dayOfMonth);
        }
        else{
            day= String.format("%s",dayOfMonth);
        }

        if( iMonth < 10){
            month = String.format("0%s",iMonth);
        }else{
            month= String.format("%s",iMonth);
        }

        String date = String.format("%s/%s/%s",day,month, year);

        return date;

    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static int getYearsDiffFromNow(Date last) {
        Calendar a = getCalendar(last);
        Calendar b = getCalendar(new Date());
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Date getDateFromString(String dateAsString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {

            date = sdf.parse(dateAsString);

        }catch (Exception e){
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringTimeToDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa", Locale.US);
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = format.parse(time);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String curTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        return curTime;
    }


    public static String getDateAsSttring(int day, int month, int year) {

        String dateAsString = String.format("%s de %s %s",
                String.valueOf(day),
                months[month],
                String.valueOf(year));


        return dateAsString;
    }

    public static int getNumberDays(String startDay,String endDay){
        int dayStart = 0;
        int dayEnd = 0;
        for(int i = 0 ; i <  days.length ; i++){
            if(days[i].compareToIgnoreCase(startDay) == 0){
                dayStart = i;
            }

            if(days[i].compareToIgnoreCase(endDay) == 0){
                dayEnd = i;
            }

        }


        return  (dayEnd - dayStart)+ 1;

    }

    public static int getHoursCount(String startTime,String endTime){

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
            Date date1 = simpleDateFormat.parse(startTime);
            Date date2 = simpleDateFormat.parse(endTime);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

            hours = (hours < 0 ? -hours : hours);
            Log.i("======= Hours", " :: " + hours);

            return hours;

        }catch (Exception e){
            return 0;
        }
    }

}
