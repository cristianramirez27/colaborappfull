package com.coppel.rhconecta.dev.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.QrCodeResponse;
import com.coppel.rhconecta.dev.business.models.ValidateCodeRequest;
import com.coppel.rhconecta.dev.business.models.ValidateDeviceIdRequest;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.Manifest.permission.READ_PHONE_STATE;


public class QrCodeActivity extends AppCompatActivity implements IServicesContract.View, DialogFragmentWarning.OnOptionClick{
    private static final int RC_STATE = 100;
    private CoppelServicesPresenter coppelServicesPresenter;
    private IntentIntegrator intent;
    private DialogFragmentWarning dialogFragmentWarning;
    private DialogFragmentLoader dialogFragmentLoader;
    private boolean hideLoader;
    private TelephonyManager tMgr;
    private int numEmp;
    private String emailEmp;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.coppelServicesPresenter = new CoppelServicesPresenter(this, this);
        this.dialogFragmentWarning = new DialogFragmentWarning();
        this.dialogFragmentLoader = new DialogFragmentLoader();
        this.hideLoader = false;
        this.numEmp = Integer.parseInt(getIntent().getStringExtra("numEmp"));
        this.emailEmp = getIntent().getStringExtra("emailEmp");

        if (ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            getDeviceId();
        } else {
            requestPermission();
        }
    }

    public void getDeviceId(){
        if (ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            showWarningDialog(getString(R.string.str_error_permisos));
            return;
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            this.deviceId = tMgr.getDeviceId();
        } else {
            this.deviceId = Settings.Secure.getString(
                    getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
        }

        if (this.deviceId != "" && this.deviceId != null){
            this.hideLoader = true;
            validateDeviceId();
        } else {
            showWarningDialog(getString(R.string.str_error_unknown_device));
        }
    }

    public void validateDeviceId(){
        ValidateDeviceIdRequest validateDeviceIdRequest =  new ValidateDeviceIdRequest();
        validateDeviceIdRequest.setOpcion(3);
        validateDeviceIdRequest.setEmpleado(this.numEmp);
        validateDeviceIdRequest.setId(this.deviceId);
        this.coppelServicesPresenter.validateDeviceId(validateDeviceIdRequest);

    }

    public void initScan() {
        intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intent.setPrompt(getString(R.string.str_scan_title));
        intent.setCameraId(0);
        intent.setOrientationLocked(false);
        intent.setBeepEnabled(true);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{READ_PHONE_STATE}, RC_STATE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                getDeviceId();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null){
                Toast.makeText(this, getString(R.string.str_cancel_scan), Toast.LENGTH_LONG).show();
                finish();
            } else {
                this.intent = null;
                this.hideLoader = true;
                String cadenaQr =  result.getContents();

                ValidateCodeRequest validateCodeRequest = new ValidateCodeRequest();
                validateCodeRequest.setOpcion(1);
                validateCodeRequest.setQrcode(cadenaQr);
                validateCodeRequest.setUsuario(this.numEmp);
                validateCodeRequest.setEmailemp(this.emailEmp);
                validateCodeRequest.setDeviceid(this.deviceId);

                this.coppelServicesPresenter.validateCode(validateCodeRequest);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()){
            case 1:
                QrCodeResponse resQr = ((QrCodeResponse) response.getResponse());
                if (resQr != null){
                    showWarningDialog(resQr.getMensaje());
                } else {
                    showWarningDialog(getString(R.string.str_error_conexion));
                }
                break;
            case 2:
                QrCodeResponse validateIdRes = ((QrCodeResponse) response.getResponse());
                if (validateIdRes != null) {
                   if (validateIdRes.getEstado() == 1 || validateIdRes.getEstado() == 0){
                       initScan();
                   } else {
                       showWarningDialog(validateIdRes.getMensaje());
                   }
                } else {
                    showWarningDialog(getString(R.string.str_error_conexion));
                }
                break;
        }

    }

    @Override
    public void showError(ServicesError error) {
        if (error != null) {
            showWarningDialog(error.getMessage());
        } else {
            showWarningDialog(getString(R.string.str_error_conexion));
        }
    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void showProgress() {
        if(hideLoader){
            dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
        }
    }

    @Override
    public void hideProgress() {
        if(dialogFragmentLoader != null && dialogFragmentLoader.isAdded()) {
            dialogFragmentLoader.dismissAllowingStateLoss();
            dialogFragmentLoader.close();
        }
    }

    @Override
    public void onLeftOptionClick() {

    }

    @Override
    public void onRightOptionClick() {
        finish();
    }
}
