package com.coppel.rhconecta.dev.analytics.time;

import android.content.SharedPreferences;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.presentation.common.extension.SharedPreferencesExtension;

import java.util.Date;

/* */
public class AnalyticsTimeManager {

    /* */
    private static AnalyticsTimeManager instance;

    /* */
    private final String ANALYTICS_FLOW = "ANALYTICS_FLOW";
    private final String TOTAL_TIME_SECONDS = "TOTAL_TIME_SECONDS";
    private final String START_RANGE = "START_RANGE";

    /* */
    private final SharedPreferences sharedPreferences;

    /* */
    private AnalyticsFlow analyticsFlow;

    /* */
    private long totalTimeInSeconds;

    /* */
    private long startRange;

    /**
     *
     */
    private AnalyticsTimeManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        analyticsFlow = (AnalyticsFlow) SharedPreferencesExtension
                .getSerializable(sharedPreferences, ANALYTICS_FLOW);
        totalTimeInSeconds = SharedPreferencesExtension
                .getLong(sharedPreferences, TOTAL_TIME_SECONDS, 0L);
        startRange = SharedPreferencesExtension
                .getLong(sharedPreferences, START_RANGE, -1);
    }

    /**
     *
     */
    public static AnalyticsTimeManager getInstance(SharedPreferences sharedPreferences) {
        if (instance == null)
            instance = new AnalyticsTimeManager(sharedPreferences);
        return instance;
    }

    /**
     *
     */
    public boolean existsFlow() {
        return analyticsFlow != null;
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
        saveValuesIntoSharedPreferences();
    }

    /**
     *
     */
    public void breakPoint() {
        if (analyticsFlow == null) return;
        if (analyticsFlow.availableRunOnBackgroundThreat())
            return;
        totalTimeInSeconds += getRangeTimeInSeconds();
        startRange = -1;
        saveValuesIntoSharedPreferences();
    }

    /**
     *
     */
    private void forceBreakPoint() {
        totalTimeInSeconds += getRangeTimeInSeconds();
        startRange = -1;
        saveValuesIntoSharedPreferences();
    }

    /**
     *
     */
    public void resume() {
        startRange = now();
        saveValuesIntoSharedPreferences();
    }

    /**
     *
     */
    public long end() {
        forceBreakPoint();
        long aux = totalTimeInSeconds;
        clear();
        saveValuesIntoSharedPreferences();
        return aux;
    }

    /**
     *
     */
    public void clear() {
        analyticsFlow = null;
        totalTimeInSeconds = 0;
        startRange = -1;
        saveValuesIntoSharedPreferences();
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

    /**
     *
     */
    private void saveValuesIntoSharedPreferences() {
        SharedPreferencesExtension
                .putSerializable(sharedPreferences, ANALYTICS_FLOW, analyticsFlow);
        SharedPreferencesExtension
                .putLong(sharedPreferences, TOTAL_TIME_SECONDS, totalTimeInSeconds);
        SharedPreferencesExtension
                .putLong(sharedPreferences, START_RANGE, startRange);
    }

}
