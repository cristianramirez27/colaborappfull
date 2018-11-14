package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersConfigRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersSignatureRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLoanSavingFundRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLoginRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesProfileRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesRecoveryPasswordRequest;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterSignatureResponse;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IServicesRetrofitMethods {

    //************************** getLogin **************************
    @Headers("Content-Type: application/json")
    @POST(ServicesConstants.GET_LOGIN)
    Call<JsonObject> getLogin(@Body CoppelServicesLoginRequest servicesRequest);

    //************************** getProfile **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_PROFILE)
    Call<JsonObject> getProfile(@Header("Authorization") String toke, @Body CoppelServicesProfileRequest servicesRequest);

    //************************** getVoucher **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_VOUCHER)
    Call<JsonObject> getPayrollVoucher(@Header("Authorization") String toke, @Body CoppelServicesPayrollVoucherRequest servicesRequest);

    //************************** getVoucherDetail **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_VOUCHER)
    Call<JsonObject> getPayrollVoucherDetail(@Header("Authorization") String toke, @Body CoppelServicesPayrollVoucherDetailRequest servicesRequest);

    //************************** getLoans/SavingFund **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_LOANSAVINGFUND)
    Call<JsonObject> getLoansSavingFund(@Header("Authorization") String toke, @Body CoppelServicesLoanSavingFundRequest servicesRequest);

    //************************** getRecoveryPassword **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_RECOVERY_PASSWORD)
    Call<JsonObject> getRecoveryPassword(@Body CoppelServicesRecoveryPasswordRequest servicesRequest);

    //************************** getLettersValidateSignature **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_LETTERS_VALIDATE_SIGNATURE)
    Call<LetterSignatureResponse> getLettersValidationSignature(@Header("Authorization") String toke, @Body CoppelServicesLettersSignatureRequest servicesRequest);

    //************************** getLettersConfig **************************
    @Headers({"Content-Type: application/json"})
    @POST(ServicesConstants.GET_CONFIG)
    Call<LetterConfigResponse> getLettersConfig(@Header("Authorization") String toke, @Body CoppelServicesLettersConfigRequest servicesRequest);
}
