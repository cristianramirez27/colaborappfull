package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.BenefitsAdvertisingResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsDiscountsResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.PushData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCompany;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.List;

import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_ADVERTISING;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_DISCOUNTS;
import static com.coppel.rhconecta.dev.views.customviews.PushInvasiveDialog.KEY_PUSH_DATA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;


/**
 * Created by flima on 18/01/2018.
 */

public class DialogAlertActivity extends AppCompatActivity implements IServicesContract.View , DialogFragmentCompany.OnBenefitsAdvertisingClickListener  {
    private String TAG = getClass().getSimpleName();
    public static String KEY_COMPANY = "KEY_COMPANY";

    private DialogFragmentCompany dialogFragmentCompany;
    private CoppelServicesPresenter coppelServicesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alert_container);
        initViews();
        BenefitsCompaniesResponse.Company company = (BenefitsCompaniesResponse.Company)getIntent().getSerializableExtra(KEY_COMPANY);
        coppelServicesPresenter = new CoppelServicesPresenter(this, DialogAlertActivity.this);
        showCompanyDialog(company);



    }

    public void initViews() {
        ButterKnife.bind(this);


    }

    private void showCompanyDialog(BenefitsCompaniesResponse.Company company){
        dialogFragmentCompany = DialogFragmentCompany.getInstance(company);
        dialogFragmentCompany.setOnBenefitsAdvertisingClickListener(this);
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
        BenefitsRequestData requestData = new BenefitsRequestData (BENEFITS_ADVERTISING,7);
        requestData.setIdempresa(company.getServicios());
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
        }
    }

    private void showAdvertising(BenefitsAdvertisingResponse.Advertising advertising){
        dialogFragmentCompany.setAdvertising(advertising);
    }


    @Override
    public void showError(ServicesError coppelServicesError) {

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
}
