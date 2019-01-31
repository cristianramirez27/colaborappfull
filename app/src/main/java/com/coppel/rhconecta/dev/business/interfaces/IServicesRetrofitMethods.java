package com.coppel.rhconecta.dev.business.interfaces;

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

public interface IServicesRetrofitMethods {

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


}
