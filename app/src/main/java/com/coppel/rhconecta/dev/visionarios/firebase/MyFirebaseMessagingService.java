package com.coppel.rhconecta.dev.visionarios.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.NotificationCreator;
import com.coppel.rhconecta.dev.business.utils.NotificationHelper;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
            if (remoteMessage.getNotification() != null) {
                try {

                    if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN)){

                        Random rand = new Random();
                        int idNotification = rand.nextInt(999);
                        Notification notification = NotificationCreator.buildLocalNotification(CoppelApp.getContext(),
                                remoteMessage.getNotification().getTitle(),
                                remoteMessage.getNotification().getBody(),
                                NotificationCreator.getPendindIntent(CoppelApp.getContext(),
                                        MainActivity.class )).build();
                        NotificationHelper.getNotificationManager(CoppelApp.getContext()).notify(idNotification, notification);

                   }

                   // sendNotification( remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    @SuppressLint("WrongConstant")
    private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.addFlags(Notification.FLAG_ONGOING_EVENT);
        intent.addFlags(Notification.FLAG_NO_CLEAR);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icn_notificaciones_blanco)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null)
            return;

        Notification notification = notificationBuilder.build();

        notificationManager.notify(0 ,notification);
    }

}


