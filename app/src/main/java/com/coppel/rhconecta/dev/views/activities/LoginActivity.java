package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.RecoveryPasswordResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.customviews.EditTextEmail;
import com.coppel.rhconecta.dev.views.customviews.EditTextPassword;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.EnrollmentFragment;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.gson.Gson;

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

    @BindView(R.id.flLoginFragmentContainer)
    FrameLayout flLoginFragmentContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    private void login() {
        if (validateFields(cedtEmail.getText(), cedtPassword.getText())) {
            coppelServicesPresenter.requestLogin(cedtEmail.getText(), cedtPassword.getText(),false);
        }
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
                coppelServicesPresenter.requestProfile(loginResponse.getData().getResponse().getCliente(), cedtEmail.getText(), loginResponse.getData().getResponse().getToken());
                break;
            case ServicesRequestType.PROFILE:
                ProfileResponse profileResponse = (ProfileResponse) response.getResponse();
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra(AppConstants.BUNDLE_LOGIN_RESPONSE, gson.toJson(loginResponse));
                intent.putExtra(AppConstants.BUNLDE_PROFILE_RESPONSE, gson.toJson(profileResponse));
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL, cedtEmail.getText());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PASS, cedtPassword.getText());
                AppUtilities.saveBooleanInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN, true);
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN, loginResponse.getData().getResponse().getToken());
                AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR, profileResponse.getData().getResponse()[0].getColaborador());

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
        else
            flLoginFragmentContainer.setVisibility(View.GONE);


    }
}
