package com.coppel.rhconecta.dev.system.notification;

import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/* */
public class CoppelFirebaseMessagingService extends FirebaseMessagingService {

    /**
     *
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (isUserLogged()) {
            NotificationType notificationType =
                    notificationTypeFromData(remoteMessage.getData(), remoteMessage.getNotification());
            CoppelNotificationManager coppelNotificationManager = new CoppelNotificationManager(this);
            coppelNotificationManager.showNotification(notificationType);
        }
    }

    /* */
    private boolean isUserLogged() {
        return AppUtilities.getBooleanFromSharedPreferences(
                getApplicationContext(),
                AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN
        );
    }

    /**
     *
     */
    private NotificationType notificationTypeFromData(
            Map<String, String> data,
            RemoteMessage.Notification notification
    ) {
        String title = notification.getTitle();
        String content = notification.getBody();
        return notificationTypeFromData(title, content, data);
    }

    /**
     *
     */
    private NotificationType notificationTypeFromData(
            String title,
            String content,
            Map<String, String> data
    ) {
        String IDU_SYSTEM = "idu_sistema";
        String IDU_DESTINATION = "idu_pantalla";
        int iduSystem = Integer.parseInt(data.get(IDU_SYSTEM));
        int iduDestination = Integer.parseInt(data.get(IDU_DESTINATION));
        NotificationDestination destination = NotificationDestination.fromInt(iduSystem, iduDestination);
        return new NotificationType(title, content, destination);
    }

    /**
     *
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

}
