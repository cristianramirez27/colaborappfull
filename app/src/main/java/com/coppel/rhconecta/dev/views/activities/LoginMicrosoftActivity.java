package com.coppel.rhconecta.dev.views.activities;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.setEndpointConfig;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.CustomCallBack;
import com.coppel.rhconecta.dev.business.utils.MSGraphRequestWrapper;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.business.utils.ShareUtil;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LoginMicrosoftActivity extends AppCompatActivity implements IServicesContract.View, DialogFragmentWarning.OnOptionClick {
    private static final String TAG = LoginMicrosoftActivity.class.getSimpleName();


    private ISingleAccountPublicClientApplication mSingleAccountApp;
    private IAccount mAccount;
    private Button btnLogIn;
    private Button btnLogOut;
    //private ImageView btnLogIn;
    private CoppelServicesPresenter coppelServicesPresenter;
    private DialogFragmentLoader dialogFragmentLoader;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private DialogFragmentWarning dialogFragmentWarning;
    private boolean finishApp = false;
    private IServicesContract.View view;

    TextView txvJoin;

    String[] scopes = {"user.read"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_microsoft);
        btnLogIn = findViewById(R.id.btnLogInMicrosoft);
        txvJoin = findViewById(R.id.txvJoin);
        //View includedLayout = findViewById(R.id.iLogin);
        //includedLayout.setElevation(30f);

        coppelServicesPresenter = new CoppelServicesPresenter(this, this);

        initializeUI();
        PublicClientApplication.createSingleAccountPublicClientApplication(getContext(),
                R.raw.auth_config_single_account,
                new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {

                        //mSingleAccountApp.signIn(this, null, getScopes(), getAuthInteractiveCallback());
                        mSingleAccountApp = application;
                        Log.i("prueba", "onCreated: " + mSingleAccountApp);
                      //  Toast.makeText(LoginMicrosoftActivity.this, "onCreated: " + mSingleAccountApp, Toast.LENGTH_SHORT).show();
                        loadAccount();
                    }

                    @Override
                    public void onError(MsalException exception) {
                        //Toast.makeText(LoginMicrosoftActivity.this, "onError " + exception, Toast.LENGTH_SHORT).show();
                        Log.i("prueba", "exception:  " + exception);
                    }
                });

        txvJoin.setOnClickListener(v -> coppelServicesPresenter.requestRecoveryPassword(24));
        initRemoteConfig();

    }

    private void initializeUI() {

        //btnLogIn = view.findViewById(R.id.btnLogIn);
        Log.i("prueba", "CLICK CLICK");
        final String defaultGraphResourceUrl = MSGraphRequestWrapper.MS_GRAPH_ROOT_ENDPOINT + "v1.0/me";
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("prueba", "CLick btn login");
                if (mSingleAccountApp == null) {
                    Log.i("prueba", "onClick");
                    return;
                }

                mSingleAccountApp.signIn(LoginMicrosoftActivity.this, null, getScopes(), getAuthInteractiveCallback());
            }
        });

        /*btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("prueba", "CLICK cerrar sesion");
                mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
                    @Override
                    public void onSignOut() {
                        Toast.makeText(getContext(), "Signed Out.", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(@NonNull MsalException exception) {
                        Toast.makeText(getContext(), exception.toString(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });*/
    }

    private String[] getScopes() {
        return "user.read".toLowerCase().split(" ");
    }

    private void loadAccount() {
        if (mSingleAccountApp == null) {
            return;
        }

        mSingleAccountApp.getCurrentAccountAsync(new ISingleAccountPublicClientApplication.CurrentAccountCallback() {
            @Override
            public void onAccountLoaded(@Nullable IAccount activeAccount) {
                // You can use the account data to update your UI or your app database.
                mAccount = activeAccount;
                //updateUI();
            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    //showToastOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {

                //displayError(exception);
            }
        });
    }

    private AuthenticationCallback getAuthInteractiveCallback() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
        return new AuthenticationCallback() {

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d("SingleAccountModeFrag", "Successfully authenticated");
                Log.d("SingleAccountModeFrag", "ID Token: " + authenticationResult.getAccount().getClaims().get("id_token"));
                Log.i("prueba", "ID Token: " + authenticationResult.getAccount().getClaims().get("id_token"));
                Log.i("prueba", "GET getAccessToken: " + authenticationResult.getAccessToken());
                //Toast.makeText(LoginMicrosoftActivity.this, "Authentication success" + authenticationResult.getAccessToken(), Toast.LENGTH_SHORT).show();
                /* Update account */
                mAccount = authenticationResult.getAccount();
                //updateUI();

                /* call graph */
                callGraphAPI(authenticationResult);
            }

            @Override
            public void onError(MsalException exception) {
                dialogFragmentLoader.close();
                /* Failed to acquireToken */
                //Toast.makeText(LoginMicrosoftActivity.this, "Authentication failed: " + exception.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Authentication failed: " + exception.toString());
                mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
                    @Override
                    public void onSignOut() {
                        //Toast.makeText(getContext(), "Signed Out.", Toast.LENGTH_SHORT).show();
                        if (mSingleAccountApp == null) {
                            Log.i("prueba", "onClick");
                            return;
                        }

                        mSingleAccountApp.signIn(LoginMicrosoftActivity.this, null, getScopes(), getAuthInteractiveCallback());
                    }

                    @Override
                    public void onError(@NonNull MsalException exception) {
                        //Toast.makeText(getContext(), exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                //displayError(exception);

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            @Override
            public void onCancel() {
                /* User canceled the authentication */
                dialogFragmentLoader.dismissAllowingStateLoss();
                dialogFragmentLoader.close();
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private void callGraphAPI(final IAuthenticationResult authenticationResult) {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                getContext(),
                "https://graph.microsoft.com/v1.0/me?$select=employeeId,mail",
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        /* Successfully called graph, process data and send to UI */
                        Log.i("prueba", "Response json : " + response.toString());

                        Log.i("prueba", "Numero de empleado : " + response.optString("employeeId", ""));
                        String employeeId = response.optString("employeeId", "");
                        String email = response.optString("mail", "");

                        //AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL, email);
                        //AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN,  authenticationResult.getAccessToken());
                        //AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER, authenticationResult.getAccessToken());
                        coppelServicesPresenter.requestProfileLogin(employeeId, email, authenticationResult.getAccessToken());
                        //displayGraphResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        //displayError(error);
                    }
                });
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ProfileResponse profileResponse = (ProfileResponse) response.getResponse();
        LoginResponse loginResponse = new LoginResponse();

        ProfileResponse.Response profile = profileResponse.getData().getResponse()[0];
        loginResponse.getData().getResponse().setToken(profile.getToken());
        loginResponse.getData().getResponse().setToken_user(profile.getToken());
        loginResponse.getData().getResponse().setCliente(profile.getColaborador());
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE, new Gson().toJson(profile));
        AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_FILIAL, profile.getEsFilial() == 1);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL, profile.getCorreo());
        //AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PASS, cedtPassword.getText());
        AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN, true);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN, profile.getToken());
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER, profile.getToken());
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_LOGIN_RESPONSE, new Gson().toJson(loginResponse));
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR, profile.getColaborador());
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_STATE_COLABORADOR, String.valueOf(profile.getEstado()));
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

        Intent intent = new IntentBuilder(new Intent(this, HomeActivity.class))
                .putSerializableExtra(AppConstants.BUNDLE_LOGIN_RESPONSE, loginResponse)
                .putSerializableExtra(AppConstants.BUNLDE_PROFILE_RESPONSE, profileResponse)
                .build();

        dialogFragmentLoader.close();
        startActivity(intent);
        finish();

    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(LoginMicrosoftActivity.this);
                dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
                dialogFragmentLoader.close();

                hideProgress();
            }
        }, 1500);

    }

    @Override
    public void showProgress() {
        //dialogFragmentLoader = new DialogFragmentLoader();
        //dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {

    }

    private void initRemoteConfig() {

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                //.setDeveloperModeEnabled(BuildConfig.DEBUG)
                .setMinimumFetchIntervalInSeconds(TimeUnit.HOURS.toSeconds(12))
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings).addOnCompleteListener(LoginMicrosoftActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fetchEndpoints();
            }
        });
    }

    private void fetchEndpoints() {
        long cacheExpiration = 0;
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(LoginMicrosoftActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(LoginMicrosoftActivity.this, new OnCompleteListener<Boolean>() {
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

    private void setEndpoints() {
        setEndpointConfig(mFirebaseRemoteConfig, new CustomCallBack() {
            @Override
            public void onComplete(String result) {
            }

            @Override
            public void onFail(String result) {
            }
        });
    }

    @Override
    public void onLeftOptionClick() {

    }

    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();
        //dialogFragmentLoader.dismissAllowingStateLoss();
        //dialogFragmentLoader.close();
        if (finishApp) {
            finishApp = false;
            finish();
        }
    }
}