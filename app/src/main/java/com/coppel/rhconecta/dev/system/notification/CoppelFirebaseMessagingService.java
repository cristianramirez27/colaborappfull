package com.coppel.rhconecta.dev.system.notification;

import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/** */
public class CoppelFirebaseMessagingService extends FirebaseMessagingService {

    /** */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (isUserLogged()) {
            if (remoteMessage != null) {
                if (remoteMessage.getData() != null) {
                    String title = remoteMessage.getData().get("title");
                    String body = remoteMessage.getData().get("body");
                    RemoteMessage.Notification notification = remoteMessage.getNotification();
                    if (notification != null) {
                        title = notification.getTitle() == null ? title : notification.getTitle();
                        body = notification.getBody() == null ? body : notification.getBody();
                    }
                    CoppelNotificationManager cnm = new CoppelNotificationManager(this);
                    NotificationType notificationType =
                            cnm.notificationTypeFromData(remoteMessage.getData(), title, body);
                    cnm.showNotification(notificationType);
                }
            }
        }
    }

    /** */
    private boolean isUserLogged() {
        return AppUtilities.getBooleanFromSharedPreferences(
                getApplicationContext(),
                AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN
        );
    }

    /** */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

}
