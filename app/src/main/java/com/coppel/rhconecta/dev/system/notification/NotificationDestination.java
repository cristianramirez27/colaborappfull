package com.coppel.rhconecta.dev.system.notification;

import java.io.Serializable;

/* */
public enum NotificationDestination implements Serializable {

    SAVING_FOUND,

    HOLIDAYS,

    EXPENSES,

    EXPENSES_AUTHORIZE,

    VIDEOS,

    VISIONARIES,

    RELEASES,

    POLL,

    MAIN;

    /* */
    public static final  String NOTIFICATION_DESTINATION = "NOTIFICATION_DESTINATION";

    /**
     *
     */
    public static NotificationDestination fromInt(int value, int aux) {
        switch (value) {
            case -1:
                switch (aux) {
                    case 39:
                    case 40:
                        return HOLIDAYS;
                    default: return MAIN;
                }
            case 9:
                return SAVING_FOUND;
            case 10:
                return HOLIDAYS;
            case 11:
                return aux == 2 ? EXPENSES_AUTHORIZE : EXPENSES;
            case 17:
                return VIDEOS;
            case 18:
                return VISIONARIES;
            case 19:
                return RELEASES;
            case 20:
                return POLL;
            default:
                return MAIN;
        }
    }

}
