package com.coppel.rhconecta.dev.views.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.coppel.rhconecta.dev.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class TextUtilities {

    private static final Locale localeMX = new Locale("es", "MX");
    private static final String[] months = new String[]{
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    public static SpannableString underlineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    public static String capitalizeText(Context context, String text) {
        try {
            String[] words = text.trim().split(" ");
            StringBuilder capitalizedText = new StringBuilder();
            for (String word : words) {
                capitalizedText.append(capitalizeWord(context, word));
                capitalizedText.append(" ");
            }
            return capitalizedText.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String capitalizeWord(Context context, String word) {
        try {
            String[] uppercaseWords = context.getResources().getStringArray(R.array.uppercase_words);
            if (Arrays.asList(uppercaseWords).contains(word)) {
                return word.toUpperCase();
            } else {
                return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    public static String insertDecimalPoint(String number) {
        try {
            /**Validamos si es negativo*/
            boolean isNegative = false;

            if(number.contains("-")){
                isNegative = true;
                number = number.replace("-","");
            }
            /**********************/
            if(number.length() == 1){
                number="0"+number;
            }

            StringBuilder stringBuilder = new StringBuilder(number);
            stringBuilder.insert(number.length() - 2, '.');

            String s = stringBuilder.toString();
            if(isNegative){
                s = "-"+s;
            }
            return s;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return number;
        }
    }

    public static String insertDecimalPointV2(String number) {
        try {
            StringBuilder stringBuilder = new StringBuilder(number);
            stringBuilder.insert(number.length() - 2, '.');
            return stringBuilder.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return number;
        }
    }

    public static String getNumberInCurrencyFormat(double value) {
        String currency;
        Locale locale = new Locale("es", "MX");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setMaximumFractionDigits(10);//Avoid round
        currency = currencyFormat.format(value);
        return currency;
    }

    public static String getNumberInCurrencyFormaNoDecimal(double value) {
        String currency;
        Locale locale = new Locale("es", "MX");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setMaximumFractionDigits(10);//Avoid round
        currencyFormat.setMinimumFractionDigits(0);
        currency = currencyFormat.format(value);
        return currency;
    }

    public static String dateFormatter(Date date, String outFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outFormat, localeMX);
        return simpleDateFormat.format(date);
    }

    public static String dateFormatter(String date, String inFormat, String outFormat) {
        SimpleDateFormat inSimpleDateFormat = new SimpleDateFormat(inFormat, localeMX);
        SimpleDateFormat outSimpleDateFormat = new SimpleDateFormat(outFormat, localeMX);
        try {
            Date tempDate = inSimpleDateFormat.parse(date);
            return outSimpleDateFormat.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String getDateFormatText(String date) {
        try {

            String[] dateParts= date.split("-");
           String day = dateParts[2].startsWith("0") ? dateParts[2].substring(1) : dateParts[2];

            return String.format("%s de %s",day,months[Integer.parseInt(dateParts[1])-1]);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void adjustFontScale(Context context, Configuration configuration) {
        if (configuration.fontScale != 1) {
            configuration.fontScale = 1;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            context.getResources().updateConfiguration(configuration, metrics);
        }
    }


}
