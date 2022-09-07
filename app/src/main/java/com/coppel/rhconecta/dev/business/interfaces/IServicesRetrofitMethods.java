package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.models.BenefitCodeRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesAuthorizedRequest;
import com.coppel.rhconecta.dev.business.models.AuthCodeRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesAuthorizedV2Request;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsAdvertisingRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCategoriesLocationRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCategoriesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCityRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCompanyRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsDiscountsRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsSearchRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsStatesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesCalendarHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesCancelPeriodsHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesChangeStatusHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesCollageUrlRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaAbonoRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaAhorroAdicionalRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaMetodoPagoRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesConsultaRetiroRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesDetailControlExpensesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesDetailRequestExpensesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetCentersHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetCentersRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetColaboratorRequestExpensesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetColaboratorsHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetColaboratorsPeriodsHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetControlsGteRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetDetailPeriodHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetFiltersControlRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetMonthsExpensesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetPeriodsHolidaysColaboratorsRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetRequestToAuthorizeRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetRolExpensesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesGetRolHolidaysRequest;
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
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherSelectedRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesProfileRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesReasonAditionalDaysHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesRecoveryPasswordRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesRefuseRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesSchedulePeriodsHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesSendAditionalDaysHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesSendPeriodsHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesValidateAditionalDaysHolidaysRequest;
import com.coppel.rhconecta.dev.business.models.ExternalUrlRequest;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterGenerateResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.LetterSignatureResponse;
import com.coppel.rhconecta.dev.business.models.TokenSSORequest;
import com.coppel.rhconecta.dev.business.models.ValidateCodeRequest;
import com.coppel.rhconecta.dev.business.models.ValidateDeviceIdRequest;
import com.coppel.rhconecta.dev.business.models.HolidayBonusRequestData;
import com.coppel.rhconecta.dev.business.models.CoCreaRequest;
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationRequest;
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

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getPayrollVoucher(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesPayrollVoucherSelectedRequest servicesRequest);

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
    Call<JsonObject> getBenefitsCategoriesLocation(@Url String url,@Header("Authorization") String token, @Body CoppelServicesBenefitsCategoriesLocationRequest servicesRequest);


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


    //************************** Fondo ahorro **************************
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

    //************************** logout **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> logout(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesProfileRequest servicesRequest);

    //************************** zendesk **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> zendesk(@Url String url,@Header("Authorization") String toke, @Body CoppelServicesProfileRequest servicesRequest);


    //************************** Gastos de Viaje **************************
    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getRolExpensesTravel(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetRolExpensesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getRequestColaboratorExpensesTravel(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetColaboratorRequestExpensesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getMonthsExpensesTravel(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetMonthsExpensesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getDetailRequestExpensesTravel(@Url String url,@Header("Authorization") String token, @Body CoppelServicesDetailRequestExpensesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getDetailControlExpensesTravel(@Url String url,@Header("Authorization") String token, @Body CoppelServicesDetailControlExpensesRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getCentersExpensesTravel(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetCentersRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getRequestExpensesToAuthorize(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetRequestToAuthorizeRequest servicesRequest);


    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getFilterControlsExpenses(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetFiltersControlRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getControlsGteExpenses(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetControlsGteRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getMonthsGteExpenses(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetControlsGteRequest servicesRequest);


    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getAutorizedExpenses(@Url String url,@Header("Authorization") String token, @Body CoppelServicesAuthorizedV2Request servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getRefuseExpenses(@Url String url,@Header("Authorization") String token, @Body CoppelServicesRefuseRequest servicesRequest);


    //**************************Vacaciones *************************

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getRolHolidays(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetRolHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getHolidaysPeriods(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> sendHolidaysPeriods(@Url String url,@Header("Authorization") String token, @Body CoppelServicesSendPeriodsHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getDetailHolidayPeriod(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetDetailPeriodHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> cancelHolidayPeriod(@Url String url,@Header("Authorization") String token, @Body CoppelServicesCancelPeriodsHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getCentersHoliday(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetCentersHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getColaboratosHoliday(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetColaboratorsHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getColaboratosPeriodsHoliday(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetColaboratorsPeriodsHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> validateAditionalDays(@Url String url,@Header("Authorization") String token, @Body CoppelServicesValidateAditionalDaysHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getReasonsAditionalDays(@Url String url,@Header("Authorization") String token, @Body CoppelServicesReasonAditionalDaysHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> sendAditionalDays(@Url String url,@Header("Authorization") String token, @Body CoppelServicesSendAditionalDaysHolidaysRequest servicesRequest);


    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getPeriodsOtherColaborators(@Url String url,@Header("Authorization") String token, @Body CoppelServicesGetPeriodsHolidaysColaboratorsRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> scheduleHolidaysPeriods(@Url String url,@Header("Authorization") String token, @Body CoppelServicesSchedulePeriodsHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getCalendarHolidays(@Url String url,@Header("Authorization") String token, @Body CoppelServicesCalendarHolidaysRequest servicesRequest);


    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> changeStatus(@Url String url,@Header("Authorization") String token, @Body CoppelServicesChangeStatusHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> editHolidays(@Url String url,@Header("Authorization") String token, @Body CoppelServicesSchedulePeriodsHolidaysRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getCollageURL(@Url String url,@Header("Authorization") String token, @Body CoppelServicesCollageUrlRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> validateCode(@Url String url, @Body ValidateCodeRequest validateCodeRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> authCode(@Url String url, @Body AuthCodeRequest authCodeRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> validateDeviceId(@Url String url, @Body ValidateDeviceIdRequest validateDeviceIdRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getExternalURL(@Url String url,@Header("Authorization") String token, @Body ExternalUrlRequest servicesRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getBenefitCode(@Url String url, @Header("Authorization") String token, @Body BenefitCodeRequest benefitCodeRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getInfoCompany(@Url String url, @Header("Authorization") String token, @Body BenefitCodeRequest infoCompanyRequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getHolidayBonus(@Url String url,@Header("Authorization") String token, @Body HolidayBonusRequestData holidayBonusRequestData);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getTokenSSO(@Url String url,@Header("Authorization") String token, @Body TokenSSORequest tokenSSORequest);

    @Headers({"Content-Type: application/json"})
    @POST
    Call<JsonObject> getCoCre(@Url String url,@Header("Authorization") String token, @Body CoCreaRequest coCreaRequest);
}
