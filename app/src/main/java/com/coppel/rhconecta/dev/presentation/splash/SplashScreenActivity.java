package com.coppel.rhconecta.dev.presentation.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeManager;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.CustomCallBack;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.business.utils.ShareUtil;
import com.coppel.rhconecta.dev.di.analytics.DaggerAnalyticsComponent;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.system.notification.CoppelNotificationManager;
import com.coppel.rhconecta.dev.system.notification.NotificationDestination;
import com.coppel.rhconecta.dev.system.notification.NotificationType;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;
import com.coppel.rhconecta.dev.views.activities.LoginMicrosoftActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_GENERAL_CONFIGURATION;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_LINKS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.getVersionApp;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.setEndpointConfig;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.updateEndpoints;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_GOTO_SECTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTIFICATION_EXPENSES_AUTHORIZE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 *
 */
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

    private JsonObject generalConfiguration;
    Integer validateVersionCode;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        DaggerAnalyticsComponent.create().injectSplash(this);
        setupFirebaseInstanceId();
        setupViews();
        //validateVersionMessage();
        //init();
        validateRoot();
        observeViewModel();
        checkAndSendAnalyticsIfExists();


    }

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
    private void checkAndSendAnalyticsIfExists() {
        AnalyticsTimeManager atm = getAnalyticsTimeManager();
        if (atm.existsFlow()) {
            AnalyticsFlow analyticsFlow = atm.getAnalyticsFlow();
            long totalTimeInSeconds = atm.end();
            splashViewModel.sendTimeByAnalyticsFlow(analyticsFlow, totalTimeInSeconds);
        }
    }

    /**
     *
     */
    private void observeViewModel() {
        splashViewModel.getSendTimeByAnalyticsFlowStatus()
                .observe(this, this::sendTimeByAnalyticsFlowStatusObserver);
    }

    /**
     *
     */
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

    /**
     *
     */
    private void setupViews() {
        TextView versionTxt = (TextView) findViewById(R.id.versionTxt);
        versionTxt.setText(String.format("V. %s", getVersionApp()));
        getWindow().setBackgroundDrawable(null);
    }

    /**
     *
     */
    private void startApp() {
        if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN)) {
            initHome();
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, LoginMicrosoftActivity.class));
                //startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
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
        String strProfileResponse = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE);
        profileResponse = new Gson().fromJson(strProfileResponse, ProfileResponse.class);

        String email2 = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL);
        String employee = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.requestProfile(
                employee,
                email2,
                token
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

    /**
     *
     */
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

    /**
     *
     */
    private void manageProfileResponse(ProfileResponse profileResponse) {
        ProfileResponse.Response profileInternalResponse = profileResponse.getData().getResponse()[0];
        saveProfileInternalResponse(profileInternalResponse);
        saveBoolean(AppConstants.SHARED_PREFERENCES_FILIAL, profileInternalResponse.getEsFilial() == 1);
        ShareUtil.toSaveMainSection(profileInternalResponse.getSeccionesApp());

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

    /**
     *
     */
    private void saveLoginInternalResponse(LoginResponse.Response loginInternalResponse) {
        saveString(AppConstants.SHARED_PREFERENCES_TOKEN, loginInternalResponse.getToken());
        saveString(AppConstants.SHARED_PREFERENCES_TOKEN_USER, loginInternalResponse.getToken_user());
        saveString(AppConstants.SHARED_PREFERENCES_LOGIN_RESPONSE, new Gson().toJson(loginResponse));
    }

    /**
     *
     */
    private void saveProfileInternalResponse(ProfileResponse.Response profileInternalResponse) {
        saveString(AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR, profileInternalResponse.getColaborador());
        saveString(AppConstants.SHARED_PREFERENCES_STATE_COLABORADOR, String.valueOf(profileInternalResponse.getEstado()));
        saveString(AppConstants.SHARED_PREFERENCES_CITY_COLABORADOR, String.valueOf(profileInternalResponse.getCiudad()));
        saveBoolean(AppConstants.SHARED_PREFERENCES_IS_GTE, profileInternalResponse.getEsGte() == 1);
        saveBoolean(AppConstants.SHARED_PREFERENCES_IS_SUPLENTE, profileInternalResponse.getEsSuplente() == 1);
        saveString(AppConstants.SHARED_PREFERENCES_NUM_GTE, String.valueOf(profileInternalResponse.getGte()));
        saveString(AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE, String.valueOf(profileInternalResponse.getSuplente()));
        saveString(AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE, new Gson().toJson(profileResponse.getData().getResponse()[0]));
    }

    /**
     *
     */
    private void saveString(String key, String value) {
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), key, value);
    }

    /**
     *
     */
    private void saveBoolean(String key, boolean value) {
        AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), key, value);
    }

    /**
     *
     */
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
        try {
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(this);
            dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), coppelServicesError.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *
     */
    @Override
    public void showProgress() { /* USELESS IMPLEMENTATION */ }

    /**
     *
     */
    @Override
    public void hideProgress() { /* USELESS IMPLEMENTATION */ }

    /**
     *
     */
    @Override
    public void onLeftOptionClick() {
        /* USELESS IMPLEMENTATION */
        Log.i("prueba", "onLeftOptionClick");

    }

    /**
     *
     */
    @Override
    public void onRightOptionClick() {
        Log.i("prueba", "onRightOptionClick");
        dialogFragmentWarning.close();

        if (EXPIRED_SESSION) {
            //startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            startActivity(new Intent(SplashScreenActivity.this, LoginMicrosoftActivity.class));
            AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN, false);
            EXPIRED_SESSION = false;
        }

        finish();
    }

    /**
     *
     */
    private void init() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(TimeUnit.HOURS.toSeconds(12))
                //.setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings).addOnCompleteListener(SplashScreenActivity.this, task -> fetchEndpoints());
    }

    /**
     *
     */
    private void fetchEndpoints() {
        long cacheExpiration = 0;
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(SplashScreenActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(SplashScreenActivity.this, new OnCompleteListener<Boolean>() {
                                @Override
                                public void onComplete(@NonNull Task<Boolean> task) {
                                    if (task.isSuccessful()) {
                                        setEndpoints();
                                    }
                                }
                            });
                        }
                    }
                });
        mFirebaseRemoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
            @Override
            public void onUpdate(@NonNull ConfigUpdate configUpdate) {
                Log.d("TAG", "Updated keys: " + configUpdate.getUpdatedKeys());
                Log.d("TAG", "Updated keys: " + configUpdate);

                mFirebaseRemoteConfig.activate().addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d("TAG", "onComplete task: " + task);
                        for (String key : configUpdate.getUpdatedKeys()) {
                            updateEndpoints(mFirebaseRemoteConfig, key);
                        }
                    }
                });
            }

            @Override
            public void onError(@NonNull FirebaseRemoteConfigException error) {
                Log.w("TAG", "Config update error with code: " + error.getCode(), error);
            }

        });


    }

    /**
     *
     */
    private void setEndpoints() {
        setEndpointConfig(mFirebaseRemoteConfig, new CustomCallBack() {

            @Override
            public void onComplete(String result) {
                validateVersionMessage();
                //initValues();
            }

            @Override
            public void onFail(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                fetchEndpoints();
            }
        });
    }

    /**
     *
     */
    private void showMessageUser(String msg) {
        new Handler().postDelayed(() -> {
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), msg, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(SplashScreenActivity.this);
            dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);

        }, 1500);
    }

    private static boolean checkRootMethod() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private void validateRoot() {
        new Handler().postDelayed(() -> {
            if (checkRootMethod()) {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.root_message), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(this);
                dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
            } else {
                init();
            }
        }, 1500);
    }

    private Integer validateVersion() {
        generalConfiguration = AppUtilities.getJsonObjectFromSharedPreferences(this, ENDPOINT_GENERAL_CONFIGURATION);
        JsonObject versionAndroid = generalConfiguration.get("AppVersion_Android").getAsJsonObject();
        String min = versionAndroid.get("min").getAsString();
        String max = versionAndroid.get("max").getAsString();
        String versionApp = getVersionApp();

        /*int comparisonMin = min.compareTo(versionApp);
        int comparisonMax = max.compareTo(versionApp);

        if (comparisonMin >= 0 && comparisonMax <= 0) {
            System.out.println("La versión app es válida.");
        } else {
            System.out.println("La versión app no es válida.");
        }*/
        String[] minParts = min.split("\\.");
        String[] maxParts = max.split("\\.");
        String[] appParts = versionApp.split("\\.");

        for (int i = 0; i < 3; i++) {
            int minPart = Integer.parseInt(minParts[i]);
            int maxPart = Integer.parseInt(maxParts[i]);
            int appPart = Integer.parseInt(appParts[i]);

            if (appPart < minPart) {
                Log.i("prueba", "-1");
                // "te obliga"
                return -1;
            } else if (appPart > maxPart) {
                Log.i("prueba", "1");
                //no te molesta
                return 1;
            }
        }
        //si es 0 te avisa que hay una actualizacion pero no es necesario actualizar
        Log.i("prueba", "0");
        return 0;
    }

    public void validateVersionMessage(){
        validateVersionCode = validateVersion();

        switch (validateVersionCode) {
            case -1:
                //obligar a actualizar
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Actualización Disponible")
                        .setMessage("Una nueva version disponible. Actualiza la app para continuar.")
                        .setPositiveButton("Actualizar", (dialog, which) -> {
                            // Acción al presionar Aceptar
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                            finish();
                        })
                        .setCancelable(false)
                        .show();
                break;
            case 0:
                //levantar modal para avisar que hay una nueva pero no es necesario descargar aun
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Actualización Disponible")
                        .setMessage("Una nueva version disponible. Actualiza la app.")
                        .setPositiveButton("Actualizar", (dialog, which) -> {
                            // Acción al presionar Aceptar
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                            finish();
                        })
                        .setNegativeButton("Despues", (dialog, which) -> {
                            dialog.dismiss();
                            initValues();
                        })
                        .setCancelable(false)
                        .show();
                break;
            case 1:
                //dejar pasar normal
                initValues();

                break;
            default:
                System.out.println("El resultado es desconocido.");
        }
    }

    private  void showDialogAlt(String message){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.attention))
                .setContentText(message)
                .setConfirmText(getString(R.string.accept))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                    }
                })
                .show();
    }

}
