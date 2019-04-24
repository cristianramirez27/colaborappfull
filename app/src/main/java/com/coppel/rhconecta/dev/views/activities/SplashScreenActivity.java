package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.URL_MAIN;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.getVersionApp;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.setEndpointConfig;

public class SplashScreenActivity extends AppCompatActivity implements IServicesContract.View, DialogFragmentWarning.OnOptionClick {

    private CoppelServicesPresenter coppelServicesPresenter;
    private LoginResponse loginResponse;
    private ProfileResponse profileResponse;
    private Gson gson;
    private DialogFragmentWarning dialogFragmentWarning;
    private  boolean finishApp = false;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView versionTxt = (TextView)findViewById(R.id.versionTxt);

        versionTxt.setText(String.format("V. %s",getVersionApp()));

        getWindow().setBackgroundDrawable(null);
        gson = new Gson();
        init();
    }

    private void startApp(){
        if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN)) {
            coppelServicesPresenter = new CoppelServicesPresenter(this, this);
            String email = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL);
            String password = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PASS);
            coppelServicesPresenter.requestLogin(email, password, true);
        } else {
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }.start();
        }
    }


    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LOGIN:
                loginResponse = (LoginResponse) response.getResponse();

                if(loginResponse.getData().getResponse().getErrorCode() == -10){
                    finishApp = true;
                    showMessageUser(loginResponse.getData().getResponse().getUserMessage());
                }else {
                    coppelServicesPresenter.requestProfile(loginResponse.getData().getResponse().getCliente(), loginResponse.getData().getResponse().getEmail(), loginResponse.getData().getResponse().getToken());
                }

                break;
            case ServicesRequestType.PROFILE:
                profileResponse = (ProfileResponse) response.getResponse();
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN, loginResponse.getData().getResponse().getToken());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR, profileResponse.getData().getResponse()[0].getColaborador());

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("LOGIN_RESPONSE", gson.toJson(loginResponse));
                intent.putExtra("PROFILE_RESPONSE", gson.toJson(profileResponse));
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        /**Se agrega modificación para mostrar Login cuando las credenciales almacenadas
         * no correspoden a las del usuario previamente autenticado.*/
        if (coppelServicesError.isExecuteInBackground()) {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
            return;
        }

        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onLeftOptionClick() {

    }

    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();
        finish();

    }


    private void init(){

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        fetchEndpoints();
    }


    private void fetchEndpoints() {
        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(SplashScreenActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                           Log.d("RemoteConfig","Fetch Failed");
                        }
                        setEndpoints();
                    }
                });
        // [END fetch_config_with_callback]
    }

    private void setEndpoints(){
        setEndpointConfig(mFirebaseRemoteConfig);
        startApp();
    }


    private void showMessageUser(String msg){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), msg, getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(SplashScreenActivity.this);
                dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);

            }
        }, 1500);
    }
}
