package com.coppel.rhconecta.dev.analytics.time;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.presentation.poll.PollActivity;
import com.coppel.rhconecta.dev.presentation.profile_actions.ProfileActionsActivity;
import com.coppel.rhconecta.dev.presentation.profile_actions.profile_details.ProfileDetailsActivity;
import com.coppel.rhconecta.dev.presentation.releases.ReleasesActivity;
import com.coppel.rhconecta.dev.presentation.splash.SplashScreenActivity;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionariesActivity;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.ZendeskStatusCallBack;

public class AnalyticsTimeAppCompatActivity extends AppCompatActivity {

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isInstancePreviousActivity()) {
            getAnalyticsTimeManager().resume();
        }
        if (isInstanceWithZendeskImplementation()) {
            if (CoppelApp.getZendesk() != null) {
                CoppelApp.getZendesk().startMonitorChatStatus(0.30F);
            }
        }
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (isInstancePreviousActivity()) {
            getAnalyticsTimeManager().breakPoint();
        }

        if (isInstanceWithZendeskImplementation()) {
            if (CoppelApp.getZendesk() != null) {
                CoppelApp.getZendesk().stopMonitorChatStatus();
            }
        }
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

    private boolean isInstancePreviousActivity() {
        return this instanceof SplashScreenActivity || this instanceof HomeActivity
                || this instanceof VacacionesActivity || this instanceof GastosViajeActivity
                || this instanceof VisionariesActivity || this instanceof PollActivity
                || this instanceof ReleasesActivity;
    }

    /**
     * HomeActivity also implements zendesk, but it starts the events individually
     */
    private boolean isInstanceWithZendeskImplementation() {
        return this instanceof PollActivity
                || this instanceof ProfileActionsActivity// Use the fragment ToolbarFragment as toolbar
                || this instanceof ReleasesActivity// Use the fragment ToolbarFragment as toolbar
                || this instanceof VisionariesActivity// Use the fragment ToolbarFragment as toolbar
                || this instanceof ProfileDetailsActivity
                || this instanceof ConfigLetterActivity
                || this instanceof FondoAhorroActivity
                || this instanceof GastosViajeActivity
                || this instanceof VacacionesActivity;
    }

    public void showWarningDialog(String message) {
        DialogFragmentWarning dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
        dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
            @Override
            public void onLeftOptionClick() {
                dialogFragmentWarning.close();
            }

            @Override
            public void onRightOptionClick() {
                dialogFragmentWarning.close();
            }
        });
    }

    public void clickZendesk() {
        CoppelApp.getZendesk().clickFeature();
    }

    public void setCallBackAndRefreshStatus(ZendeskStatusCallBack callback) {
        CoppelApp.getZendesk().setCallBackAndRefreshStatus(callback);
    }
}
