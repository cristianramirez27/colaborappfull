package com.coppel.rhconecta.dev.views.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import com.coppel.rhconecta.dev.R;

import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

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

    public static String getFormatedERH(String text){
        if(text.contains("Erh") ){
            text = text.replace("Erh","ERH");
        }

        return text;
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

    public static String getDateFormatToHolidays(String date,boolean adjustMonth){
        String[] dateFormat = date.split("-");
        int month = adjustMonth ? Integer.parseInt(dateFormat[1]) + 1 : Integer.parseInt(dateFormat[1]);
        String dateFormatted = String.format("%s%s%s",dateFormat[0],month > 9 ? String.valueOf(month) : "0" +month,dateFormat[2]);
        return dateFormatted.trim();
    }

    public static String getDateFormatToHolidaysSchedule(String date,boolean adjustMonth){
        String[] dateFormat = date.split("-");
        int month = adjustMonth ? Integer.parseInt(dateFormat[1]) + 1 : Integer.parseInt(dateFormat[1]);
        String dateFormatted = String.format("%s%s%s",dateFormat[2],month > 9 ? String.valueOf(month) : "0" +month,dateFormat[0]);
        return dateFormatted.trim();
    }


    public static String getDateFormatToHolidaysInverse(String date,boolean adjustMonth){
        date = date.split(",")[1].trim();
        String[] dateFormat = date.split("-");
        int month = adjustMonth ? Integer.parseInt(dateFormat[1]) + 1 : Integer.parseInt(dateFormat[1]);
        String dateFormatted = String.format("%s%s%s",dateFormat[2],month > 9 ? String.valueOf(month) : "0" +month,dateFormat[0]);
        return dateFormatted.trim();
    }


    public static String getDayNameFromDate(String date ){//Sample "29-08-2016"
        /*Obtenemos nombre del día*/
        LocalDate localDate = DateTimeFormat.forPattern("dd-MM-yyyy").parseLocalDate(date);
       return DateTimeFormat.forPattern("EEEE").print(localDate);
    }

    public static String getDayNameFromDate(DateTime date ){//Sample "29-08-2016"
        /*Obtenemos nombre del día*/
        return DateTimeFormat.forPattern("EEEE").print(date);
    }

    public static void formatMonthNameFormat(String titleMonth, TextView mTxtTitle){

        try {

            String[] titleParts = titleMonth.split(" ");
            titleParts[0] = titleParts[0].substring(0, 1).toUpperCase() + titleParts[0].substring(1).toLowerCase() + " ";

            Spannable word = new SpannableString( titleParts[0]);
            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTxtTitle.setText(word);
            Spannable wordTwo = new SpannableString( titleParts[1]);
            wordTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#5f6062")), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTxtTitle.append(wordTwo);
            // mTxtTitle.setText(sbuilder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String[] getMonths() {
        return months;
    }

    public static Balloon getBallon (Context context, int label) {
             return new Balloon.Builder(context)
                    .setTextResource(label)
                    .setArrowSize(10)
                    .setWidthRatio(1.0f)
                     .setTextIsHtml(true)
                     .setTextGravity(Gravity.START)
                    .setHeight(BalloonSizeSpec.WRAP)
                    .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                    .setArrowPosition(0.5f)
                    .setPadding(8)
                    .setMarginRight(24)
                    .setMarginLeft(24)
                    .setTextSize(16f)
                    .setCornerRadius(8f)
                    .setTextTypeface(R.font.lineto_circular_pro_medium)
                    .setTextColorResource(R.color.mdtp_white)
                    .setBackgroundColorResource(R.color.colorBlueLight)
                    .setBalloonAnimation(BalloonAnimation.ELASTIC)
                    .build();
    }
    public static Balloon getBallon (Context context, int label, LifecycleOwner  lifecycleOwner) {
        return new Balloon.Builder(context)
                .setTextResource(label)
                .setArrowSize(10)
                .setWidthRatio(1.0f)
                .setTextIsHtml(true)
                .setTextGravity(Gravity.START)
                .setHeight(BalloonSizeSpec.WRAP)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setArrowPosition(0.5f)
                .setPadding(8)
                .setMarginRight(24)
                .setMarginLeft(24)
                .setTextSize(16f)
                .setLifecycleOwner(lifecycleOwner)
                .setCornerRadius(8f)
                .setTextTypeface(R.font.lineto_circular_pro_medium)
                .setTextColorResource(R.color.mdtp_white)
                .setBackgroundColorResource(R.color.colorBlueLight)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .build();
    }
}
