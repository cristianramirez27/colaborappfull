package com.coppel.rhconecta.dev.business.presenters;

import android.content.Context;

import com.coppel.rhconecta.dev.business.interactors.ServicesInteractor;
import com.coppel.rhconecta.dev.business.interfaces.IServiceListener;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersGenerateRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.KEY_CHILDREN_NAMES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.KEY_HOLIDAY_PERIOD;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.KEY_JOB_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.KEY_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.KEY_SECTION_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.KEY_STAMP;

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

    public void requestLogOut(String employeeNumber, String employeeEmail, String token) {
        view.showProgress();
        servicesInteractor.getLogoutValidation(employeeNumber, employeeEmail, token);
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

    public void requestRecoveryPassword(int clave)
    {
        view.showProgress();
        servicesInteractor.getRecoverPassword(clave);
    }

    public void requestLettersValidationSignature(String employeeNumber, String token) {
        view.showProgress();
        servicesInteractor.getLettersValidateSignatureValidation(employeeNumber,token);
    }

    public void requestLettersConfig(String employeeNumber, int tipoCarta, String token) {
        view.showProgress();
        servicesInteractor.getLettersConfigValidation(employeeNumber, tipoCarta,token);
    }

    public void requestLettersPreviewView(String employeeNumber, int tipoCarta, List<LetterConfigResponse.Datos> fieldsLetter,
                                          CoppelServicesLettersGenerateRequest.Data dataOptional , boolean hasStamp, String token) {
        view.showProgress();
        // ConfigLetterFields configLetterFields = new ConfigLetterFields();
        Map<String,Object> data = new HashMap<>();
        for(LetterConfigResponse.Datos dato: fieldsLetter){
            data.put(String.valueOf(dato.getIdu_datoscartas()),dato.isSelected() ? 1 : 0);
        }

        data.put(KEY_STAMP,hasStamp? 1 : 0);

        /*Agregamos los datos opcionales*/
        if(dataOptional != null){

            data.put(KEY_CHILDREN_NAMES,dataOptional.getChildrenData());
            data.put(KEY_SCHEDULE,dataOptional.getScheduleData());
            data.put(KEY_JOB_SCHEDULE,dataOptional.getJobScheduleData());
            data.put(KEY_SECTION_SCHEDULE,dataOptional.getSectionScheduleData());
            data.put(KEY_HOLIDAY_PERIOD,dataOptional.getLetterHolidayData());


        }


        servicesInteractor.getLettersPreview(employeeNumber, tipoCarta,data,token);

    }

    public void requestLetterGenerateView(String employeeNumber, int tipoCarta, int opcionEnvio,
                                          String correo, List<LetterConfigResponse.Datos> fieldsLetter,
                                          CoppelServicesLettersGenerateRequest.Data dataOptional ,
                                          boolean hasStamp, String token) {
        view.showProgress();
        // ConfigLetterFields configLetterFields = new ConfigLetterFields();
        Map<String,Object> data = new HashMap<>();
        for(LetterConfigResponse.Datos dato: fieldsLetter){
            data.put(String.valueOf(dato.getIdu_datoscartas()),dato.isSelected() ? 1 : 0);
        }
        /*Agregamos valor del sello*/
        data.put(KEY_STAMP,hasStamp? 1 : 0);

        /*Agregamos los datos opcionales*/
        if(dataOptional != null){

            data.put(KEY_CHILDREN_NAMES,dataOptional.getChildrenData());
            data.put(KEY_SCHEDULE,dataOptional.getScheduleData());
            data.put(KEY_JOB_SCHEDULE,dataOptional.getJobScheduleData());
            data.put(KEY_SECTION_SCHEDULE,dataOptional.getSectionScheduleData());
            data.put(KEY_HOLIDAY_PERIOD,dataOptional.getLetterHolidayData());


        }

        servicesInteractor.getLettersGenerate(employeeNumber, tipoCarta,opcionEnvio, correo,data,token);

    }


    public void getBenefits(BenefitsRequestData data,String token){
        view.showProgress();
        servicesInteractor.getBenefits(data,token);
    }

    public void getWithDrawSaving(WithDrawSavingRequestData data, String token){
        view.showProgress();
        servicesInteractor.getFondoAhorro(data,token);
    }

    public void getExpensesTravel(ExpensesTravelRequestData data, String token){
        view.showProgress();
        servicesInteractor.getExpensesTravel(data,token);
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