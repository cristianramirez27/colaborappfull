package com.coppel.rhconecta.dev.system.notification;

import java.io.Serializable;

/* */
public class NotificationType implements Serializable {

    /* */
    public static String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";

    /* */
    private final String title;
    /* */
    private final String content;
    /* */
    private final NotificationDestination notificationDestination;

    /**
     *
     */
    public NotificationType(String title, String content, NotificationDestination notificationDestination) {
        this.title = title;
        this.content = content;
        this.notificationDestination = notificationDestination;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NotificationDestination getNotificationDestination() {
        return notificationDestination;
    }

}
