package com.coppel.rhconecta.dev.business.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.PushData;


/**
 * Created by faust on 1/26/18.
 */

public class NotificationCreator {

    public final static int REQUEST_CODE_PUSH = 777;
    public final static String KEY_PUSH_DATA = "_KEY_PUSH_DATA";

    public static PendingIntent getPendindIntent(Context context, Class clazz, PushData pushData){
        Intent resultIntent = new Intent(context, clazz);
        if(pushData.getLoginRequired().equals("0")){
          //  resultIntent.putExtra(KEY_SECTION,EVENT_GO_HOME);
        }
        resultIntent.putExtra(KEY_PUSH_DATA,pushData);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        REQUEST_CODE_PUSH,
                        resultIntent,
                        PendingIntent.FLAG_ONE_SHOT);//FLAG_UPDATE_CURRENT

        return resultPendingIntent;
    }



    public static NotificationCompat.Builder buildLocalNotification(Context context, String title, String content, PendingIntent pendingIntent) {

        Uri uriSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(content)
                        //.setSubText(content)
                        .setSmallIcon(getSmallIconId())
                        .setSound(uriSound)
                        .setLargeIcon(BitmapFactory.decodeResource(CoppelApp.getContext().getResources(), R.mipmap.ic_launcher))
                        .setColor(Color.parseColor("#bb162b"))
                        .setAutoCancel(true);

        return builder;
    }


    private static int getSmallIconId() {

        boolean whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;

    }

}
