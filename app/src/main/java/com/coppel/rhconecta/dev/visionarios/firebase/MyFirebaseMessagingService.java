package com.coppel.rhconecta.dev.visionarios.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            try {
                //   sendNotification( remoteMessage.getNotification().getBody());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

   /* @SuppressLint("WrongConstant")
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.addFlags(Notification.FLAG_ONGOING_EVENT);
        intent.addFlags(Notification.FLAG_NO_CLEAR);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Picnic")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null)
            return;

        notificationManager.notify(0 , notificationBuilder.build());
    }  */


}
