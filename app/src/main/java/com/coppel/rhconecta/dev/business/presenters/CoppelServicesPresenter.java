package com.coppel.rhconecta.dev.business.presenters;

import android.content.Context;

import com.coppel.rhconecta.dev.business.interactors.ServicesInteractor;
import com.coppel.rhconecta.dev.business.interfaces.IServiceListener;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;

public class CoppelServicesPresenter implements IServiceListener {

    private ServicesInteractor servicesInteractor;
    private IServicesContract.View view;

    public CoppelServicesPresenter(IServicesContract.View view, Context context) {
        this.servicesInteractor = new ServicesInteractor(context);
        this.servicesInteractor.setOnServiceListener(this);
        this.view = view;
    }

    public void requestLogin(String email, String password,boolean executeInBackground) {
        view.showProgress();
        servicesInteractor.getLoginValidation(email, password,executeInBackground);
    }

    public void requestProfile(String employeeNumber, String employeeEmail, String token) {
        view.showProgress();
        servicesInteractor.getProfileValidation(employeeNumber, employeeEmail, token);
    }

    public void requestPayrollVoucher(String employeeNumber, int typePetition, String token) {
        view.showProgress();
        servicesInteractor.getPayrollVoucherValidation(employeeNumber, typePetition, token);
    }

    public void requestPayrollVoucherDetail(String employeeNumber, String email, int typeConstancy, int request, int shippingOption, String date, CoppelServicesPayrollVoucherDetailRequest.PayrollVoucherDetailGenericData data, String token) {
        view.showProgress();
        servicesInteractor.getPayrollVoucherDetailValidation(employeeNumber, email, typeConstancy, request, shippingOption, date, data, token);
    }

    public void requestLoansSavingFund(String employeeNumber, String token) {
        view.showProgress();
        servicesInteractor.getLoansSavingFundValidation(employeeNumber, token);
    }

    public void requestRecoveryPassword()
    {
        view.showProgress();
        servicesInteractor.getRecoverPassword();
    }

    public void requestLettersValidationSignature(int numEmpleado) {
        view.showProgress();
        servicesInteractor.getLettersValidateSignatureValidation(numEmpleado);
    }

    public void requestLettersConfig(int numEmpleado, int tipoCarta) {
        view.showProgress();
        servicesInteractor.getLettersConfigValidation(numEmpleado, tipoCarta);
    }

    @Override
    public void onResponse(ServicesResponse response) {
        view.hideProgress();
        view.showResponse(response);
    }

    @Override
    public void onError(ServicesError coppelServicesError) {
        view.hideProgress();
        view.showError(coppelServicesError);
    }
}
