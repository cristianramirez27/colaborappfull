package com.coppel.rhconecta.dev.analytics.time;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.coppel.rhconecta.dev.views.utils.AppUtilities;

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
        return AnalyticsTimeManager.getInstance(getSharedPreferences());
    }

    /**
     *
     */
    protected SharedPreferences getSharedPreferences() {
        return AppUtilities.getSharedPreferences(this);
    }

}
