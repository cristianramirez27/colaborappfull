package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

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
import com.google.gson.Gson;

public class SplashScreenActivity extends AppCompatActivity implements IServicesContract.View, DialogFragmentWarning.OnOptionClick {

    private CoppelServicesPresenter coppelServicesPresenter;
    private LoginResponse loginResponse;
    private ProfileResponse profileResponse;
    private Gson gson;
    private DialogFragmentWarning dialogFragmentWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setBackgroundDrawable(null);
        gson = new Gson();
        if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN)) {
            coppelServicesPresenter = new CoppelServicesPresenter(this, this);
            String email = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL);
            String password = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PASS);
            coppelServicesPresenter.requestLogin(email, password,true);
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
                coppelServicesPresenter.requestProfile(loginResponse.getData().getResponse().getCliente(), loginResponse.getData().getResponse().getEmail(), loginResponse.getData().getResponse().getToken());
                break;
            case ServicesRequestType.PROFILE:
                profileResponse = (ProfileResponse) response.getResponse();
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
        if(coppelServicesError.isExecuteInBackground()){
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
}
