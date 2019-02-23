package com.coppel.rhconecta.dev.business.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by noe_3 on 16/11/2017.
 */

public class NotificationHelper {
    public static int ALARM_TYPE_RTC = 100;
    private static AlarmManager alarmManagerRTC;
    private static PendingIntent alarmIntentRTC;

    public static int ALARM_TYPE_ELAPSED = 101;
    private static AlarmManager alarmManagerElapsed;
    private static PendingIntent alarmIntentElapsed;

    /**
     * This is the real time /wall clock time
     * @param context
     */
    public static void scheduleRepeatingRTCNotification(Context context, Calendar calendar, Serializable object, Command actionPostSetAlarm) {
        //get calendar instance to be able to select what time notification should be scheduled
        //Setting intent to class where Alarm broadcast message will be handled
       /* Intent intent = new Intent(context, AlarmReceiver.class);
        String quoteID = "";
        int idNotification = getRandomIdentifier();
        if(object instanceof DateCviS){
            DateCviS dateCviS = (DateCviS)object;
            intent.putExtra("IS_DEFAULT_DATE",true);
            intent.putExtra("CONTENT",MyKiaApp.getContext().getString(R.string.content_calendar_reminder,
                    DateTimeUtil.convertStringTimeToDate(Formatters.getHourFormatDateCEViS(dateCviS.getHour())),
                    dateCviS.getDealerName()));
            quoteID = dateCviS.getQuoteID();
        }else if(object instanceof ReminderDate){
            ReminderDate reminderDate = (ReminderDate)object;
            intent.putExtra("IS_DEFAULT_DATE",false);
            intent.putExtra("CONTENT",MyKiaApp.getContext().getString(R.string.content_local_reminder, Formatters.getDateFormatDateWithOutDay(reminderDate.getBookingData().getDate()),
                    DateTimeUtil.convertStringTimeToDate(Formatters.getHourFormatDateCEViS(String.valueOf(reminderDate.getTimeSchedule().getTime()))),
                    reminderDate.getDateCviS().getDealerName()));
            quoteID = reminderDate.getDateCviS().getQuoteID();
        }

        intent.putExtra("ID",idNotification);

        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        actionPostSetAlarm.execute(quoteID,idNotification);//Regresamos el idNotification
        //getting instance of AlarmManager service
        alarmManagerRTC = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Setting alarm to wake up device every day for clock time.
        //AlarmManager.RTC_WAKEUP is responsible to wake up device for sure, which may not be good practice all the time.
        // Use this when you know what you're doing.
        //Use RTC when you don't need to wake up device, but want to deliver the notification whenever device is woke-up
        //We'll be using RTC.WAKEUP for demo purpose only
        alarmManagerRTC.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentRTC);*/
    }


    public static void cancelAlarmRTC() {
        if (alarmManagerRTC!= null) {
            alarmManagerRTC.cancel(alarmIntentRTC);
        }
    }

    public static void cancelAlarmElapsed() {
        if (alarmManagerElapsed!= null) {
            alarmManagerElapsed.cancel(alarmIntentElapsed);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "channel_general";// The id of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "General", importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        return notificationManager;
    }


    public static int getRandomIdentifier() {
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        return m;
    }
}