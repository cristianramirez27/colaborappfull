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
import android.widget.RemoteViews;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;

import java.util.Map;


/**
 * Created by faust on 1/26/18.
 */

public class NotificationCreator {

    public final static int REQUEST_CODE_PUSH = 777;
    public final static String KEY_PUSH_DATA = "_KEY_PUSH_DATA";

    public static PendingIntent getPendindIntent(Context context, Class clazz){
        Intent resultIntent = new Intent(context, clazz);
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

        RemoteViews contentView = new RemoteViews(CoppelApp.getContext().getPackageName(), R.layout.custom_push_notification);
        contentView.setImageViewResource(R.id.image,  R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, content);

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)

                        //.setStyle(new NotificationCompat.BigTextStyle() .bigText(title))
                       // .setStyle(new NotificationCompat.MessagingStyle(title))
                        .setContentTitle(title)

                        //.setStyle(new NotificationCompat.BigTextStyle() .bigText(content))
                        .setContentText(content)
                        .setCustomBigContentView(contentView)
                        //.setSubText(content)
                        .setSmallIcon(getSmallIconId())
                        .setSound(uriSound)
                        .setLargeIcon(BitmapFactory.decodeResource(CoppelApp.getContext().getResources(), R.drawable.icn_notificaciones_azul))
                        .setColor(Color.parseColor("#004695"))
                        .setChannelId("channel_general")
                        .setAutoCancel(true);

        return builder;
    }


    public static PendingIntent getPendindIntentSection(Context context, Class clazz, Map<String,String> params){
        Intent resultIntent = new Intent(context, clazz);

        //resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        for(String key : params.keySet()){
            resultIntent.putExtra(key,params.get(key));
        }


        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        REQUEST_CODE_PUSH,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT

        return resultPendingIntent;
    }





    private static int getSmallIconId() {

        boolean whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        return whiteIcon ? R.drawable.icn_notificaciones_azul : R.drawable.icn_notificaciones_azul;

    }

}
