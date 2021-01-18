package com.coppel.rhconecta.dev.analytics.time;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;

import java.util.Date;

/* */
public class AnalyticsTimeManager {

    /* */
    private static AnalyticsTimeManager instance;

    /* */
    private AnalyticsFlow analyticsFlow;

    /* */
    private long totalTimeInSeconds;

    /* */
    private long startRange;

    /**
     *
     */
    private AnalyticsTimeManager() {
        /* Empty constructor */
    }

    /**
     *
     */
    public static AnalyticsTimeManager getInstance() {
        if (instance == null)
            instance = new AnalyticsTimeManager();
        return instance;
    }

    /**
     *
     */
    public AnalyticsFlow getAnalyticsFlow() {
        return analyticsFlow;
    }

    /**
     *
     */
    public void start(AnalyticsFlow analyticsFlow) {
        this.analyticsFlow = analyticsFlow;
        totalTimeInSeconds = 0;
        startRange = now();
    }

    /**
     *
     */
    public void breakPoint() {
        totalTimeInSeconds += getRangeTimeInSeconds();
        startRange = -1;
    }

    /**
     *
     */
    public void resume() {
        startRange = now();
    }

    /**
     *
     */
    public long end() {
        breakPoint();
        return totalTimeInSeconds;
    }

    /**
     *
     */
    public void clear() {
        analyticsFlow = null;
        totalTimeInSeconds = 0;
        startRange = -1;
    }

    /**
     *
     */
    private long now() {
        return new Date().getTime();
    }

    /**
     *
     */
    private long getRangeTimeInSeconds() {
        if (startRange == -1)
            return 0;
        long endRange = now();
        return (endRange - startRange) / 1000;
    }
}
