package com.coppel.rhconecta.dev.views.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.QrCodeResponse;
import com.coppel.rhconecta.dev.business.models.ValidateCodeRequest;
import com.coppel.rhconecta.dev.business.models.ValidateDeviceIdRequest;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;


public class QrCodeActivity extends AppCompatActivity implements IServicesContract.View, DialogFragmentWarning.OnOptionClick, ZXingScannerView.ResultHandler{
    private static final int RC_STATE = 100;
    private CoppelServicesPresenter coppelServicesPresenter;
    private DialogFragmentWarning dialogFragmentWarning;
    private DialogFragmentLoader dialogFragmentLoader;
    private boolean hideLoader;
    private TelephonyManager tMgr;
    private int numEmp;
    private String emailEmp;
    private String deviceId;

    private ZXingScannerView mScannerView;
    private RelativeLayout rl;
    private TextView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qr_scanner);

        this.coppelServicesPresenter = new CoppelServicesPresenter(this, this);
        this.dialogFragmentWarning = new DialogFragmentWarning();
        this.dialogFragmentLoader = new DialogFragmentLoader();
        this.hideLoader = false;
        this.numEmp = IntentExtension.getIntExtra(getIntent(), "numEmp");
        this.emailEmp = IntentExtension.getStringExtra(getIntent(), "emailEmp");

        closeBtn = (TextView) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        if (
            ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED
        ) {
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

        if ("".equals(this.deviceId) || this.deviceId == null) {
            this.deviceId = FirebaseInstanceId.getInstance().getId();
        }

        if (!("".equals(this.deviceId)) && this.deviceId != null){
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
        if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            showWarningDialog(getString(R.string.str_error_permisos));
            return;
        }

        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);

        mScannerView = new ZXingScannerView(this);
        rl = (RelativeLayout) findViewById(R.id.qrScanner);
        rl.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.setSoundEffectsEnabled(true);
        mScannerView.setAutoFocus(true);
        mScannerView.setBorderLineLength(150);
        mScannerView.setBorderStrokeWidth(20);
        mScannerView.setSquareViewFinder(true);
        mScannerView.setFormats(formats);
        mScannerView.startCamera();
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{READ_PHONE_STATE, CAMERA}, RC_STATE);
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

    @Override
    public void handleResult(Result result) {
        String cadenaQr = result.getText();

        if ("".equals(cadenaQr) || cadenaQr == null){
            Toast.makeText(this, getString(R.string.str_cancel_scan), Toast.LENGTH_LONG).show();
            finish();
        } else {
            this.hideLoader = true;

            ValidateCodeRequest validateCodeRequest = new ValidateCodeRequest();
            validateCodeRequest.setOpcion(1);
            validateCodeRequest.setQrcode(cadenaQr);
            validateCodeRequest.setUsuario(this.numEmp);
            validateCodeRequest.setEmailemp(this.emailEmp);
            validateCodeRequest.setDeviceid(this.deviceId);

            this.coppelServicesPresenter.validateCode(validateCodeRequest);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
            mScannerView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();
        }
    }
}
