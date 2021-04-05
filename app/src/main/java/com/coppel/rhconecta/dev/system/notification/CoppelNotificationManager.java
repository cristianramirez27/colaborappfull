package com.coppel.rhconecta.dev.system.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.splash.SplashScreenActivity;

import java.util.Date;

/* */
public class CoppelNotificationManager {

    /* */
    private final Context context;

    /**
     *
     */
    public CoppelNotificationManager(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    /**
     *
     */
    private String getChannelId() {
        return context.getString(R.string.default_notification_channel_id);
    }

    /**
     *
     */
    private String getChannelName() {
        return context.getString(R.string.default_notification_channel_name);
    }

    /**
     *
     */
    private String getChannelDescription() {
        return context.getString(R.string.default_notification_channel_description);
    }

    /**
     *
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getChannelId(), getChannelName(), importance);
            channel.setDescription(getChannelDescription());
            NotificationManager notificationManager =
                    ContextCompat.getSystemService(context, NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     *
     */
    public void showNotification(NotificationType notificationType) {
        Notification notification = buildNotification(notificationType);
        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        int notificationId = (int) new Date().getTime();
        nmc.notify(notificationId, notification);
    }

    /**
     *
     */
    private Notification buildNotification(NotificationType notificationType) {
        Intent intent = new IntentBuilder(new Intent(context, SplashScreenActivity.class))
                .putSerializableExtra(NotificationType.NOTIFICATION_TYPE, notificationType)
                .build();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new NotificationCompat.Builder(context, getChannelId())
                .setSmallIcon(R.drawable.ic_colaborapp_notificacion)
                .setContentTitle(notificationType.getTitle())
                .setContentText(notificationType.getContent())
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationType.getContent()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();
    }

}
