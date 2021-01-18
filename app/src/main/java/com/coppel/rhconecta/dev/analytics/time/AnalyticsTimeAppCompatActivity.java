package com.coppel.rhconecta.dev.analytics.time;

import androidx.appcompat.app.AppCompatActivity;

/* */
public class AnalyticsTimeAppCompatActivity extends AppCompatActivity {

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        getAnalyticsTimeManager().resume();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        getAnalyticsTimeManager().breakPoint();
    }

    /**
     *
     */
    protected AnalyticsTimeManager getAnalyticsTimeManager() {
        return AnalyticsTimeManager.getInstance();
    }

}
