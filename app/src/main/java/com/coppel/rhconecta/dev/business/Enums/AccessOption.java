package com.coppel.rhconecta.dev.business.Enums;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public enum AccessOption implements Serializable {

    /* If the access option is the main menu icon */
    ICON,

    /* If the access option is by banner action */
    BANNER,

    /* If the access option is by lateral menu icon */
    MENU;

    /**
     * Obtains the integer representation of the value.
     */
    public Integer toInteger() {
        switch (this) {
            case ICON: return 1;
            case BANNER: return 2;
            case MENU: return 3;
            default: return null;
        }
    }

    /**
     * Obtains the access option from integer value.
     */
    public static AccessOption fromInt(int value) {
        switch (value) {
            case 1: return ICON;
            case 2: return BANNER;
            case 3: return MENU;
            default: return null;
        }
    }

}
