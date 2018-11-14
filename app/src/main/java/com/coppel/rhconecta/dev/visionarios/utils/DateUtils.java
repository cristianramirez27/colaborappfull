package com.coppel.rhconecta.dev.visionarios.utils;

import java.util.Calendar;

public class DateUtils {

    public static String dateToText(String date) {
        String dateText = "";


        String day = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH));
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        try {
            int montidx = Integer.parseInt(month);
            String[] meses = new String[12];
            meses[0] = "Enero";
            meses[1] = "Febrero";
            meses[2] = "Marzo";
            meses[3] = "Abril";
            meses[4] = "Mayo";
            meses[5] = "Junio";
            meses[6] = "Julio";
            meses[7] = "Agosto";
            meses[8] = "Septiembre";
            meses[9] = "Octubre";
            meses[10] = "Noviembre";
            meses[11] = "Diciembre";

            month = meses[montidx];
            dateText = day + " de " + month + " " + year;


        } catch (Exception e) {
            dateText = "";
        }

        return dateText;
    }

}
