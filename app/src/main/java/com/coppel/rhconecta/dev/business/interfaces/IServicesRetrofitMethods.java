package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsAdvertisingRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsBaseRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCategoriesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCityRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCompanyRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsDiscountsRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsSearchRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsStatesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaAbonoRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaAhorroAdicionalRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaMetodoPagoRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaRetiroRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGuardarAbonoRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGuardarAhorroRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGuardarRetiroRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersConfigRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersGenerateRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersPreviewRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersSignatureRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLoanSavingFundRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLoginRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesProfileRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesRecoveryPasswordRequest;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterGenerateResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.LetterSignatureResponse;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface IServicesRetrofitMethods  <T>{

    //************************** getLogin **************************
    @Headers("Content-Type: application/json")
    @POST
    Call<JsonObject> getLogin(@Url String url, @Body CoppelServicesLoginRequest servicesRequest);

    //************************** getProfile **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getProfile(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesProfileRequest servicesRequest);

    //************************** getVoucher **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getPayrollVoucher(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesPayrollVoucherRequest servicesRequest);

    //************************** getVoucherDetail **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getPayrollVoucherDetail(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesPayrollVoucherDetailRequest servicesRequest);

    //************************** getLoans/SavingFund **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getLoansSavingFund(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesLoanSavingFundRequest servicesRequest);

    //************************** getRecoveryPassword **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getRecoveryPassword(@Url String url,@Body CoppelServicesRecoveryPasswordRequest servicesRequest);

    //************************** getLettersValidateSignature **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<LetterSignatureResponse> getLettersValidationSignature(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesLettersSignatureRequest servicesRequest);

    //************************** getLettersConfig **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<LetterConfigResponse> getLettersConfig(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesLettersConfigRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<LetterPreviewResponse> getLettersPreview(@Url String url,@Header("Authorization") String token, @Body CoppelServicesLettersPreviewRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<LetterGenerateResponse> getLettersGenerate(@Url String url,@Header("Authorization") String token, @Body CoppelServicesLettersGenerateRequest servicesRequest);


    //************************** Benefits **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsStates(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsStatesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsCity(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsCityRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsCategories(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsCategoriesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsDiscounts(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsDiscountsRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsCompany(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsCompanyRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsAdvertising(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsAdvertisingRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitsSearch(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsSearchRequest servicesRequest);


    //************************** Benefits **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getConsultaRetiro(@Url String url,@Header("Authorization") String token, @Body CoppelServicesConsultaRetiroRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getGuardarRetiro(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGuardarRetiroRequest servicesRequest);


    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getConsultarAhorroAdicional(@Url String url,@Header("Authorization") String token, @Body CoppelServicesConsultaAhorroAdicionalRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getGuardarAhorro(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGuardarAhorroRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getConsultarAbono(@Url String url,@Header("Authorization") String token, @Body CoppelServicesConsultaAbonoRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getGuardarAbono(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGuardarAbonoRequest servicesRequest);


    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getConsultarMetodo(@Url String url,@Header("Authorization") String token, @Body CoppelServicesConsultaMetodoPagoRequest servicesRequest);



}
