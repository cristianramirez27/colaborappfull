package com.coppel.rhconecta.dev.views.activities;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.setEndpointConfig;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.RecoveryPasswordResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.CustomCallBack;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.business.utils.ShareUtil;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.views.customviews.EditTextEmail;
import com.coppel.rhconecta.dev.views.customviews.EditTextPassword;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.EnrollmentFragment;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.recaptcha.Recaptcha;
import com.google.android.gms.recaptcha.RecaptchaAction;
import com.google.android.gms.recaptcha.RecaptchaActionType;
import com.google.android.gms.recaptcha.RecaptchaHandle;
import com.google.android.gms.recaptcha.RecaptchaResultData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick, EditTextPassword.OnEditorActionListener {

    private CoppelServicesPresenter coppelServicesPresenter;
    private LoginResponse loginResponse;
    private Gson gson;
    private DialogFragmentWarning dialogFragmentWarning;
    private DialogFragmentLoader dialogFragmentLoader;
    private FragmentManager fragmentManager;
    @BindView(R.id.cedtEmail)
    EditTextEmail cedtEmail;
    @BindView(R.id.cedtPassword)
    EditTextPassword cedtPassword;
    @BindView(R.id.txvForgotPassword)
    TextView txvForgotPassword;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;
    @BindView(R.id.txvSignIn)
    TextView txvSignIn;
    @BindView(R.id.txvJoin)
    TextView txvJoin;


    @BindView(R.id.mainContainer)
    RelativeLayout mainContainer;

    @BindView(R.id.flLoginFragmentContainer)
    FrameLayout flLoginFragmentContainer;

    //VISIONARIOS
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private  boolean finishApp = false;
    private RecaptchaHandle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initRecapcha();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(null);
        fragmentManager = getSupportFragmentManager();
        coppelServicesPresenter = new CoppelServicesPresenter(this, this);
        gson = new Gson();
        cedtPassword.setOnEditorActionListener(this);
        btnLogIn.setOnClickListener(this);
        txvSignIn.setOnClickListener(this);
        txvJoin.setOnClickListener(this);
        txvForgotPassword.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (BuildConfig.DEBUG) {
            cedtEmail.setText(BuildConfig.DEBUG_USR);
            cedtPassword.setText(BuildConfig.DEBUG_PS);
        }

        //VISIONARIOS
        initRemoteConfig();
    }

    private void initRecapcha(){
        Log.i("CAPCHA", "initRecapcha  ");
        Recaptcha.getClient(this)
                .init(AppConstants.KEY_CAPTCHA)
                .addOnSuccessListener(
                        this,
                        new OnSuccessListener<RecaptchaHandle>() {
                            @Override
                            public void onSuccess(RecaptchaHandle handle) {
                                // Handle success ...
                                Log.i("CAPCHA", "Handle success ... ");
                                LoginActivity.this.handle = handle;
                            }
                        })
                .addOnFailureListener(
                        this,
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("CAPCHA", e.getMessage());
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    // Status apiErrorStatus = apiException.getStatusCode();
                                    // Handle api errors ...
                                } else {
                                    // Handle other failures ...
                                }
                            }
                        });
    }
    private void login() {
        this.callCapcha(new View(this));

        //this.onDestroyCap();
    }
    public void callCapcha(View v) {
        this.initRecapcha();
        // Step 2: call execute() when there is an action to protect.
        Recaptcha.getClient(this)
                .execute(this.handle, new RecaptchaAction(new RecaptchaActionType("createAccount")))
                .addOnSuccessListener(
                        this,
                        new OnSuccessListener<RecaptchaResultData>() {
                            @Override
                            public void onSuccess(RecaptchaResultData response) {
                                String token = response.getTokenResult();
                                // Handle success ...
                                if (!token.isEmpty()) {
                                    Log.i("CAPCHA", "reCAPTCHA response token: " + token);
                                    // Validate the response token by following the instructions
                                    if (validateFields(cedtEmail.getText(), cedtPassword.getText())) {
                                        coppelServicesPresenter.requestLogin(cedtEmail.getText(), cedtPassword.getText(),false,token);
                                    }
                                }
                            }
                        })
                .addOnFailureListener(
                        this,
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("CAPCHA", e.getMessage());
                                showMessageUser("Servicio no Disponible!.");
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    //Status apiErrorStatus = apiException.getStatusCode();
                                    // Handle api errors ...
                                } else {
                                    // Handle other failures ...
                                }
                            }
                        });
    }

    private boolean validateFields(String email, String password) {
        boolean validEmail;
        boolean validPassword;
        if (email.isEmpty()) {
            cedtEmail.setWarningMessage(getString(R.string.error_email));
            validEmail = false;
        } else {
            cedtEmail.clearWarning();
            validEmail = true;
        }
        if (password.isEmpty()) {
            cedtPassword.setWarningMessage(getString(R.string.error_password));
            validPassword = false;
        } else {
            cedtPassword.clearWarning();
            validPassword = true;
        }
        return validEmail && validPassword;
    }

    private void addFragment(Fragment fragment, String TAG) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.add(R.id.flLoginFragmentContainer, fragment, TAG).commit();
        flLoginFragmentContainer.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                login();
                break;
            case R.id.txvSignIn:
                addFragment(new EnrollmentFragment(), EnrollmentFragment.TAG);
                break;
            case R.id.txvForgotPassword:
                coppelServicesPresenter.requestRecoveryPassword(18);
                break;

            case R.id.txvJoin:
                coppelServicesPresenter.requestRecoveryPassword(24);
                break;

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
                    coppelServicesPresenter.requestProfile(loginResponse.getData().getResponse().getCliente(), cedtEmail.getText(), loginResponse.getData().getResponse().getToken());
                }
                 break;
            case ServicesRequestType.PROFILE:
                ProfileResponse profileResponse = (ProfileResponse) response.getResponse();
                Intent intent = new IntentBuilder(new Intent(this, HomeActivity.class))
                        .putSerializableExtra(AppConstants.BUNDLE_LOGIN_RESPONSE, loginResponse)
                        .putSerializableExtra(AppConstants.BUNLDE_PROFILE_RESPONSE, profileResponse)
                        .build();

                ProfileResponse.Response profile = profileResponse.getData().getResponse()[0];
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(),AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE, new Gson().toJson(profile));
                AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_FILIAL, profile.getEsFilial() == 1);
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL, cedtEmail.getText());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PASS, cedtPassword.getText());
                AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN, true);
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN, loginResponse.getData().getResponse().getToken());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER, loginResponse.getData().getResponse().getToken_user());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_LOGIN_RESPONSE, new Gson().toJson(loginResponse));
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR, profile.getColaborador());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_STATE_COLABORADOR,String.valueOf( profile.getEstado()));
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_CITY_COLABORADOR, String.valueOf(profile.getCiudad()));
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_GTE, String.valueOf(profile.getGte()));
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE, String.valueOf(profile.getSuplente()));

                /* Almacenamos la fecha en la que se inicio sesion */
                Date currentTime = Calendar.getInstance().getTime();
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_LAST_SSO_LOGIN, new Gson().toJson(currentTime));

                /*Almacenamos si es Gerente*/
                AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_GTE, profile.getEsGte() == 1);
                AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_SUPLENTE, profile.getEsSuplente() == 1);
                ShareUtil.toSaveMainSection(profile.getSeccionesApp());

                cedtEmail.setText("");
                cedtPassword.setText("");
                dialogFragmentLoader.close();
                startActivity(intent);
                finish();
                break;
            case ServicesRequestType.RECOVERY_PASSWORD:
                RecoveryPasswordResponse recoveryPasswordResponse = (RecoveryPasswordResponse) response.getResponse();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recoveryPasswordResponse.getData().getResponse().getClv_urlservicio()));
                dialogFragmentLoader.close();
                startActivity(webIntent);
                break;
        }
    }

    @Override
    public void showError(final ServicesError coppelServicesError) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(LoginActivity.this);
                dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
                dialogFragmentLoader.close();

                hideProgress();
            }
        }, 1500);
    }

    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
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

        if(finishApp){
            finishApp = false;
            finish();
        }
    }

    @Override
    public void onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            login();
        }
    }

    @Override
    public void onBackPressed() {

        if(flLoginFragmentContainer.getVisibility() == View.GONE)
            finish();
        else {
            flLoginFragmentContainer.setVisibility(View.GONE);
            mainContainer.setVisibility(View.VISIBLE);
        }
    }


    private void showMessageUser(String msg){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), msg, getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(LoginActivity.this);
                dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
                dialogFragmentLoader.close();
            }
        }, 1500);
    }

    //VISIONARIOS SE AGREGO LA OBTENCION EN LOGIN PARA LA OBTENCIONB DEL ENDPOINT DE LOGIN DE VISIONARIOS
    private void initRemoteConfig(){

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                //.setDeveloperModeEnabled(BuildConfig.DEBUG)
                .setMinimumFetchIntervalInSeconds(TimeUnit.HOURS.toSeconds(12))
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fetchEndpoints();
            }
        });

    }


    private void fetchEndpoints() {
        long cacheExpiration = 0;
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Boolean>() {
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
        // [END fetch_config_with_callback]
    }

    private void setEndpoints(){
        setEndpointConfig(mFirebaseRemoteConfig, new CustomCallBack() {
            @Override
            public void onComplete(String result) {
            }

            @Override
            public void onFail(String result) {
            }
        });
    }

    //VISIONARIOS SE AGREGO LA OBTENCION EN LOGIN PARA LA OBTENCIONB DEL ENDPOINT DE LOGIN DE VISIONARIOS

}
