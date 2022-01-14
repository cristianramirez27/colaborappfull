package com.coppel.rhconecta.dev.presentation.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeManager;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.di.analytics.DaggerAnalyticsComponent;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.system.notification.CoppelNotificationManager;
import com.coppel.rhconecta.dev.system.notification.NotificationDestination;
import com.coppel.rhconecta.dev.system.notification.NotificationType;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import javax.inject.Inject;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.getVersionApp;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.setEndpointConfig;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_GOTO_SECTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTIFICATION_EXPENSES_AUTHORIZE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;

/** */
public class SplashScreenActivity
        extends AnalyticsTimeAppCompatActivity
        implements IServicesContract.View, DialogFragmentWarning.OnOptionClick {

    /* */
    @Inject
    public SplashViewModel splashViewModel;

    /* */
    private CoppelServicesPresenter coppelServicesPresenter;
    /* */
    private LoginResponse loginResponse;
    /* */
    private ProfileResponse profileResponse;
    /* */
    private DialogFragmentWarning dialogFragmentWarning;
    /* */
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    /* */
    private NotificationDestination notificationDestination;
    /* */
    private String goTosection = "";
    /* */
    private boolean EXPIRED_SESSION = false;

    /** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        DaggerAnalyticsComponent.create().injectSplash(this);
        setupFirebaseInstanceId();
        setupViews();
        init();
        observeViewModel();
        checkAndSendAnalyticsIfExists();
    }

    /** */
    private void setupFirebaseInstanceId() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                SplashScreenActivity.this,
                instanceIdResult -> {
                    String newToken = instanceIdResult.getToken();
                    if (newToken != null && !newToken.isEmpty())
                        AppUtilities.saveStringInSharedPreferences(
                                getApplicationContext(),
                                AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN,
                                newToken
                        );
                }
        );
    }

    /** */
    private void initValues() {
        NotificationType notificationType = (NotificationType) IntentExtension
                .getSerializableExtra(getIntent(), NotificationType.NOTIFICATION_TYPE);

        if (notificationType == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                CoppelNotificationManager cnm = new CoppelNotificationManager(this);
                notificationType = cnm.fromBundle(bundle);
            }
        }

        if (notificationType != null) {
            notificationDestination = notificationType.getNotificationDestination();
            setupGoto();
        }
            startApp();

    }

    /** */
    private void checkAndSendAnalyticsIfExists() {
        AnalyticsTimeManager atm = getAnalyticsTimeManager();
        if (atm.existsFlow()) {
            AnalyticsFlow analyticsFlow = atm.getAnalyticsFlow();
            long totalTimeInSeconds = atm.end();
            splashViewModel.sendTimeByAnalyticsFlow(analyticsFlow, totalTimeInSeconds);
        }
    }

    /** */
    private void observeViewModel() {
        splashViewModel.getSendTimeByAnalyticsFlowStatus()
                .observe(this, this::sendTimeByAnalyticsFlowStatusObserver);
    }

    /** */
    private void sendTimeByAnalyticsFlowStatusObserver(ProcessStatus processStatus) {
        switch (processStatus) {
            case LOADING:
                break;
            case FAILURE:
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                getAnalyticsTimeManager().clear();
                break;
            case COMPLETED:
                getAnalyticsTimeManager().clear();
                break;
        }
    }

    /* */
    private void setupGoto() {
        if (notificationDestination == NotificationDestination.SAVING_FOUND)
            goTosection = OPTION_SAVING_FUND;
        if (notificationDestination == NotificationDestination.HOLIDAYS)
            goTosection = OPTION_HOLIDAYS;
        if (notificationDestination == NotificationDestination.EXPENSES)
            goTosection = OPTION_EXPENSES;
        if (notificationDestination == NotificationDestination.EXPENSES_AUTHORIZE)
            goTosection = OPTION_NOTIFICATION_EXPENSES_AUTHORIZE;
    }

    /** */
    private void setupViews() {
        TextView versionTxt = (TextView) findViewById(R.id.versionTxt);
        versionTxt.setText(String.format("V. %s", getVersionApp()));
        getWindow().setBackgroundDrawable(null);
    }

    /** */
    private void startApp() {
        if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN)) {
            initHome();
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }, 1000);
        }
    }

    private void initHome() {
        coppelServicesPresenter = new CoppelServicesPresenter(this, this);

        String strLoginResponse = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_LOGIN_RESPONSE);
        loginResponse = new Gson().fromJson(strLoginResponse, LoginResponse.class);

        String email = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL);
        coppelServicesPresenter.requestProfile(
                loginResponse.getData().getResponse().getCliente(),
                email,
                loginResponse.getData().getResponse().getToken()
        );
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LOGIN:
                loginResponse = (LoginResponse) response.getResponse();
                manageLoginResponse(loginResponse);
                break;
            case ServicesRequestType.PROFILE:
                profileResponse = (ProfileResponse) response.getResponse();
                manageProfileResponse(profileResponse);
                break;
        }
    }

    /** */
    private void manageLoginResponse(LoginResponse loginResponse) {
        if (loginResponse.getData().getResponse().getErrorCode() == -10) {
            showMessageUser(loginResponse.getData().getResponse().getUserMessage());
        } else {
            String email = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL);
            coppelServicesPresenter.requestProfile(
                    loginResponse.getData().getResponse().getCliente(),
                    email,
                    loginResponse.getData().getResponse().getToken()
            );
        }
    }

    /** */
    private void manageProfileResponse(ProfileResponse profileResponse) {
        ProfileResponse.Response profileInternalResponse = profileResponse.getData().getResponse()[0];
        saveProfileInternalResponse(profileInternalResponse);

        Intent intent = new IntentBuilder(new Intent(this, HomeActivity.class))
                .putSerializableExtra(AppConstants.BUNDLE_LOGIN_RESPONSE, loginResponse)
                .putSerializableExtra(AppConstants.BUNLDE_PROFILE_RESPONSE, profileResponse)
                .build();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (notificationDestination != null) {
            IntentExtension.putSerializableExtra(
                    intent,
                    NotificationDestination.NOTIFICATION_DESTINATION,
                    notificationDestination
            );
        }

        if (!goTosection.isEmpty())
            IntentExtension.putStringExtra(intent, BUNDLE_GOTO_SECTION, goTosection);


        startActivity(intent);
        finish();
    }

    /** */
    private void saveLoginInternalResponse(LoginResponse.Response loginInternalResponse) {
        saveString(AppConstants.SHARED_PREFERENCES_TOKEN, loginInternalResponse.getToken());
        saveString(AppConstants.SHARED_PREFERENCES_TOKEN_USER, loginInternalResponse.getToken_user());
        saveString(AppConstants.SHARED_PREFERENCES_LOGIN_RESPONSE, new Gson().toJson(loginResponse));
    }

    /** */
    private void saveProfileInternalResponse(ProfileResponse.Response profileInternalResponse) {
        saveString(AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR, profileInternalResponse.getColaborador());
        saveString(AppConstants.SHARED_PREFERENCES_STATE_COLABORADOR, String.valueOf(profileInternalResponse.getEstado()));
        saveString(AppConstants.SHARED_PREFERENCES_CITY_COLABORADOR, String.valueOf(profileInternalResponse.getCiudad()));
        saveBoolean(AppConstants.SHARED_PREFERENCES_IS_GTE, profileInternalResponse.getEsGte() == 1);
        saveBoolean(AppConstants.SHARED_PREFERENCES_IS_SUPLENTE, profileInternalResponse.getEsSuplente() == 1);
        saveString(AppConstants.SHARED_PREFERENCES_NUM_GTE, String.valueOf(profileInternalResponse.getGte()));
        saveString(AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE, String.valueOf(profileInternalResponse.getSuplente()));
        saveString(AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE, new Gson().toJson(profileResponse));
    }

    /** */
    private void saveString(String key, String value) {
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), key, value);
    }

    /** */
    private void saveBoolean(String key, boolean value) {
        AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), key, value);
    }

    /** */
    @Override
    public void showError(ServicesError coppelServicesError) {
        if (coppelServicesError.isExecuteInBackground()) {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
            return;
        }

        if (coppelServicesError.getType() == ServicesRequestType.INVALID_TOKEN) {
            EXPIRED_SESSION = true;
        }
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    /** */
    @Override
    public void showProgress() { /* USELESS IMPLEMENTATION */ }

    /** */
    @Override
    public void hideProgress() { /* USELESS IMPLEMENTATION */ }

    /** */
    @Override
    public void onLeftOptionClick() { /* USELESS IMPLEMENTATION */ }

    /** */
    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();

        if (EXPIRED_SESSION) {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN, false);
            EXPIRED_SESSION = false;
        }

        finish();
    }

    /** */
    private void init() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        fetchEndpoints();
    }

    /** */
    private void fetchEndpoints() {
        long cacheExpiration = 0;
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(
                        SplashScreenActivity.this,
                        task -> {
                            if (task.isSuccessful())
                                mFirebaseRemoteConfig.activateFetched();
                            setEndpoints();
                        }
                );
    }

    /** */
    private void setEndpoints() {
        setEndpointConfig(mFirebaseRemoteConfig);
        initValues();
    }

    /** */
    private void showMessageUser(String msg) {
        new Handler().postDelayed(() -> {
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), msg, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(SplashScreenActivity.this);
            dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);

        }, 1500);
    }

}
