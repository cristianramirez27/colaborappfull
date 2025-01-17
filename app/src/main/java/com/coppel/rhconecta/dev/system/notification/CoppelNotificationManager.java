package com.coppel.rhconecta.dev.system.notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.splash.SplashScreenActivity;
import com.coppel.rhconecta.dev.resources.db.RealmTransactions;
import com.coppel.rhconecta.dev.resources.db.models.NotificationsUser;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.coppel.rhconecta.dev.system.notification.NotificationKeyKt.IDU_DESTINATION;
import static com.coppel.rhconecta.dev.system.notification.NotificationKeyKt.IDU_SYSTEM;
import static com.coppel.rhconecta.dev.system.notification.NotificationKeyKt.ID_DESTINATION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;

/** */
public class CoppelNotificationManager {

    /* */
    private final Context context;

    /** */
    public CoppelNotificationManager(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    /** */
    private String getChannelId() {
        return context.getString(R.string.default_notification_channel_id);
    }

    /** */
    private String getChannelName() {
        return context.getString(R.string.default_notification_channel_name);
    }

    /** */
    private String getChannelDescription() {
        return context.getString(R.string.default_notification_channel_description);
    }

    /** */
    public NotificationType fromBundle(Bundle bundle) {
        Map<String, String> data = new HashMap<>();
        try {
            String value = bundle.getString(IDU_SYSTEM);
            data.put(IDU_SYSTEM, value);
        } catch (Exception ignore) { /* PASS */ }
        return notificationTypeFromData(data, null, null);
    }

    /** */
    public NotificationType notificationTypeFromData(
            Map<String, String> data,
            String title,
            String body
    ) {
        String iduSystemValue = data.get(IDU_SYSTEM);
        int iduSystem = iduSystemValue == null ? -1 : Integer.parseInt(iduSystemValue);
        String iduDestinationValue = data.get(IDU_DESTINATION);
        String idDestinationValue = data.get(ID_DESTINATION);
        int iduDestination = iduDestinationValue == null ?
                (idDestinationValue == null ? -1 : Integer.parseInt(idDestinationValue)) :
                Integer.parseInt(iduDestinationValue);
        processNotification(iduSystem);
        NotificationDestination destination = NotificationDestination.fromInt(iduSystem, iduDestination);
        return new NotificationType(title, body, destination);
    }

    private void processNotification(int iduSystem) {
        if (iduSystem >= 9 && iduSystem < 12) {
            String numgColaborator = AppUtilities.getStringFromSharedPreferences(
                    CoppelApp.getContext(),
                    SHARED_PREFERENCES_NUM_COLABORADOR
            );
            RealmTransactions.insertInto(new NotificationsUser(UUID.randomUUID().toString(), numgColaborator, iduSystem),
                    params -> LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(AppConstants.INTENT_NOTIFICATION_ACTON)));
        }
    }

    /** */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getChannelId(), getChannelName(), importance);
            channel.setDescription(getChannelDescription());
            NotificationManager notificationManager =
                    ContextCompat.getSystemService(context, NotificationManager.class);
            if (notificationManager == null) return;
            notificationManager.createNotificationChannel(channel);
        }
    }

    /** */
    public void showNotification(NotificationType notificationType) {
        Notification notification = buildNotification(notificationType);
        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        int notificationId = (int) new Date().getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        nmc.notify(notificationId, notification);
    }

    /** */
    private Notification buildNotification(NotificationType notificationType) {
        Intent intent = new IntentBuilder(new Intent(context, SplashScreenActivity.class))
                .putSerializableExtra(NotificationType.NOTIFICATION_TYPE, notificationType)
                .build();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        /*PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/

        PendingIntent pendingIntent;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new NotificationCompat.Builder(context, getChannelId())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(notificationType.getTitle())
                .setContentText(notificationType.getContent())
                .setSound(defaultSoundUri)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryCoppelAzul2))
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationType.getContent()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();
    }

}
