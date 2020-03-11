package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.QrCodeResponse;
import com.coppel.rhconecta.dev.business.models.ValidateCodeRequest;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

public class QrCodeActivity extends AppCompatActivity implements IServicesContract.View, DialogFragmentWarning.OnOptionClick{
    private CoppelServicesPresenter coppelServicesPresenter;
    private IntentIntegrator intent;
    private ProfileResponse.Response profileResponse;
    private DialogFragmentWarning dialogFragmentWarning;
    private DialogFragmentLoader dialogFragmentLoader;
    private boolean hideLoader;

    private int numEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.coppelServicesPresenter = new CoppelServicesPresenter(this, this);
        this.dialogFragmentWarning = new DialogFragmentWarning();
        this.dialogFragmentLoader = new DialogFragmentLoader();
        this.hideLoader = false;

        this.numEmpleado = Integer.parseInt(getIntent().getStringExtra("numEmpleado"));

        intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intent.setPrompt(getString(R.string.str_scan_title));
        intent.setCameraId(0);
        intent.setOrientationLocked(false);
        intent.setBeepEnabled(true);
        intent.setBarcodeImageEnabled(false);

        intent.initiateScan();
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

                ValidateCodeRequest validateCodeRequest = new ValidateCodeRequest(
                    cadenaQr,
                    this.numEmpleado,
            1
                );

                this.coppelServicesPresenter.validateCode(validateCodeRequest);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        QrCodeResponse res = ((QrCodeResponse) response.getResponse());
        if (res != null){
            showWarningDialog(res.getMensaje());
        } else {
            showWarningDialog(getString(R.string.str_error_conexion));
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
