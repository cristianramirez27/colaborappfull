package com.coppel.rhconecta.dev.system.notification;

import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import zendesk.chat.Chat;
import zendesk.chat.PushData;
import zendesk.chat.PushNotificationsProvider;

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

                    /**
                     * Implementation for push zendesk
                     */
                    Chat.INSTANCE.init(
                            this,
                            BuildConfig.ZENDESK_ACCOUNT,
                            BuildConfig.ZENDESK_APPLICATION
                    );

                    PushNotificationsProvider pushProvider = Chat.INSTANCE.providers().pushNotificationsProvider();
                    PushData pushData = null;
                    String tokenFirebase = AppUtilities.getStringFromSharedPreferences(
                            CoppelApp.getContext(),
                            AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN
                    );

                    if (pushProvider != null) {
                        pushProvider.registerPushToken(tokenFirebase);
                        pushData = pushProvider.processPushNotification(remoteMessage.getData());
                    }

                    NotificationType notificationType;
                    if (pushData != null) {
                        String message = pushData.getMessage();
                        String author = pushData.getAuthor();
                        notificationType = cnm.notificationTypeFromData(remoteMessage.getData(), author, message);
                        AppUtilities.saveBooleanInSharedPreferences(
                                CoppelApp.getContext(),
                                AppConstants.ZENDESK_NOTIFICATION, true
                        );
                    } else {
                        notificationType = cnm.notificationTypeFromData(remoteMessage.getData(), title, body);
                    }

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
    private NotificationType notificationTypeFromData(
            Map<String, String> data,
            String title,
            String body
    ) {
        String IDU_SYSTEM = "idu_sistema";
        String iduSystemValue = data.get(IDU_SYSTEM);
        int iduSystem = iduSystemValue == null ? -1 : Integer.parseInt(iduSystemValue);
        String IDU_DESTINATION = "idu_pantalla";
        String ID_DESTINATION = "id_pantalla";
        String iduDestinationValue = data.get(IDU_DESTINATION);
        String idDestinationValue = data.get(ID_DESTINATION);
        int iduDestination = iduDestinationValue == null ?
                idDestinationValue == null ? -1 : Integer.parseInt(idDestinationValue)
                : Integer.parseInt(iduDestinationValue);
        NotificationDestination destination = NotificationDestination.fromInt(iduSystem, iduDestination);
        return new NotificationType(title, body, destination);
    }

    /** */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

}
