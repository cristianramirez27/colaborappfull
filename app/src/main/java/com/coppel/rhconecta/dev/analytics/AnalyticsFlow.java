package com.coppel.rhconecta.dev.analytics;

import androidx.annotation.Keep;

import java.io.Serializable;

/* */
@Keep
public enum AnalyticsFlow implements Serializable {

    SAVING_FUND,

    HOLIDAYS,

    TRAVEL_EXPENSES,

    RELEASES,

    VISIONARIES,

    VIDEOS,

    POLL,

    PAYROLL_VOUCHER,

    LETTERS,

    BENEFITS,

    COLLAGE,

    COVID_SURVEY;

    /**
     *
     */
    public boolean availableRunOnBackgroundThreat() {
        switch (this) {
            case COLLAGE:
            case COVID_SURVEY: return true;
            default: return false;
        }
    }

}
