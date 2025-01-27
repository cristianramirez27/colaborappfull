package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.BenefitCodeResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsAdvertisingResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.InfoCompanyResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCompany;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_ADVERTISING;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;


/**
 * Created by flima on 18/01/2018.
 */

public class DialogAlertActivity extends AppCompatActivity implements IServicesContract.View , DialogFragmentCompany.OnBenefitsAdvertisingClickListener, DialogFragmentWarning.OnOptionClick  {
    private String TAG = getClass().getSimpleName();
    public static String KEY_COMPANY = "KEY_COMPANY";

    private DialogFragmentCompany dialogFragmentCompany;
    private CoppelServicesPresenter coppelServicesPresenter;
    private DialogFragmentWarning dialogFragmentWarning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alert_container);
        initViews();
        this.dialogFragmentWarning = new DialogFragmentWarning();
        Serializable temp = IntentExtension.getSerializableExtra(getIntent(), KEY_COMPANY);
        BenefitsCompaniesResponse.Company company = (BenefitsCompaniesResponse.Company) temp;
        coppelServicesPresenter = new CoppelServicesPresenter(this, DialogAlertActivity.this);
        showCompanyDialog(company);
    }

    public void initViews() {
        ButterKnife.bind(this);


    }

    private void showCompanyDialog(BenefitsCompaniesResponse.Company company){
        dialogFragmentCompany = DialogFragmentCompany.getInstance(company);
        dialogFragmentCompany.setOnBenefitsAdvertisingClickListener(this);
        dialogFragmentCompany.setCoppelServicesPresenter(this.coppelServicesPresenter);
        dialogFragmentCompany.show(getSupportFragmentManager(), DialogFragmentCompany.TAG);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onCategoryClick(BenefitsCompaniesResponse.Company company) {
        requestAdvertising(company);
    }

    private void requestAdvertising(BenefitsCompaniesResponse.Company company){
        String token = AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_TOKEN);
        BenefitsRequestData requestData = new BenefitsRequestData(BENEFITS_ADVERTISING,7);
        requestData.setIdempresa(String.valueOf(company.getServicios()));
        coppelServicesPresenter.getBenefits(requestData, token);
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.BENEFITS:
                if (response.getResponse() instanceof BenefitsAdvertisingResponse){
                    List<BenefitsAdvertisingResponse.Advertising> advertisingList = ((BenefitsAdvertisingResponse)response.getResponse() ).getData().getResponse();
                    if(!advertisingList.isEmpty())
                        showAdvertising(advertisingList.get(0));
                }
                break;
            case ServicesRequestType.BENEFIT_CODE:
                if (response.getResponse() instanceof BenefitCodeResponse) {
                    dialogFragmentCompany.setCodeView(((BenefitCodeResponse) response.getResponse()).getDes_codigo());
                }
                break;
            case ServicesRequestType.BENEFIT_COMPANY:
                if (response.getResponse() instanceof InfoCompanyResponse) {
                    dialogFragmentCompany.setInfoCompany(((InfoCompanyResponse) response.getResponse()));
                }
                break;
        }
    }

    private void showAdvertising(BenefitsAdvertisingResponse.Advertising advertising){
        dialogFragmentCompany.setAdvertising(advertising);
    }


    @Override
    public void showError(ServicesError coppelServicesError) {
        if (coppelServicesError != null) {
            if (coppelServicesError.getType() == ServicesRequestType.BENEFIT_CODE)
                return;
            showWarningDialog(coppelServicesError.getMessage());
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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void closeCategoryDialog() {
        finish();
    }

    @Override
    public void onLeftOptionClick() {

    }

    @Override
    public void onRightOptionClick() {
        finish();
    }
}
