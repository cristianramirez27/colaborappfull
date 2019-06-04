package com.coppel.rhconecta.dev.business.interactors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.BenefitsType;
import com.coppel.rhconecta.dev.business.interfaces.IServiceListener;
import com.coppel.rhconecta.dev.business.interfaces.IServicesRetrofitMethods;
import com.coppel.rhconecta.dev.business.models.BenefitsAdvertisingResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsBaseResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCitiesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsDiscountsResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsEmptyResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.BenefitsSearchResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;
import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsAdvertisingRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsBaseRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCategoriesRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCityRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsCompanyRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsDiscountsRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsSearchRequest;
import com.coppel.rhconecta.dev.business.models.CoppelServicesBenefitsStatesRequest;
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
import com.coppel.rhconecta.dev.business.models.GeneralErrorResponse;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterGenerateResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.LetterSignatureResponse;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.LogoutResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.RecoveryPasswordResponse;
import com.coppel.rhconecta.dev.business.models.VoucherAlimonyResponse;
import com.coppel.rhconecta.dev.business.models.VoucherBonusResponse;
import com.coppel.rhconecta.dev.business.models.VoucherDownloadResponse;
import com.coppel.rhconecta.dev.business.models.VoucherGasResponse;
import com.coppel.rhconecta.dev.business.models.VoucherPTUResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.models.VoucherRosterResponse;
import com.coppel.rhconecta.dev.business.models.VoucherSavingFundResponse;
import com.coppel.rhconecta.dev.business.models.VoucherSendMailResponse;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesGeneralValidations;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.business.utils.ServicesUtilities;
import com.google.gson.Gson;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.getVersionApp;


public class ServicesInteractor {

    private Context context;
    private Retrofit retrofit;
    private IServiceListener iServiceListener;
    private IServicesRetrofitMethods iServicesRetrofitMethods;
    private ServicesGeneralValidations servicesGeneralValidations;
    private ServicesUtilities servicesUtilities;
    private String token;

    public ServicesInteractor(Context context) {
        this.context = context;
        servicesUtilities = new ServicesUtilities();
        retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        iServicesRetrofitMethods = retrofit.create(IServicesRetrofitMethods.class);
        servicesGeneralValidations = new ServicesGeneralValidations();
    }

    /* *******************************************************************************************************************************************************
     *****************************************************               Login            ********************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param email    User email
     * @param password User password
     *
     * Update 2 Noviembre 2018
     * @param executeInBackground flag to indicate if login is execute in background
     */
    public void getLoginValidation(String email, String password,boolean executeInBackground) {
        getLogin(email, password,executeInBackground);
    }

    /**
     * Make a request to get login
     *
     * @param email    User email
     * @param password User password
     *
     */
    private void getLogin(String email, String password, final boolean executeInBackground) {
        final int type = ServicesRequestType.LOGIN;
        iServicesRetrofitMethods.getLogin(ServicesConstants.GET_LOGIN,buildLoginRequest(email, password)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {
                    LoginResponse loginResponse = (LoginResponse) servicesUtilities.parseToObjectClass(response.body().toString(), LoginResponse.class);

                    if (loginResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getLoginResponse(loginResponse, response.code(), type);
                    } else {
                        sendGenericError(type, response,executeInBackground);
                    }
                } catch (Exception e) {
                    sendGenericError(type, response,executeInBackground);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, type));
            }
        });
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getLoginResponse(LoginResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getLoginSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_login), servicesError));
        }
    }

    /**
     * Handles a successful response of the Login method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getLoginSuccess(LoginResponse response, int type) {
        ServicesResponse<LoginResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Constructs the model to be sent for the login request
     *
     * @param email    User email
     * @param password User password
     * @return CoppelServicesLoginRequest Request model
     */
    public CoppelServicesLoginRequest buildLoginRequest(String email, String password) {
        CoppelServicesLoginRequest coppelServicesLoginRequest = new CoppelServicesLoginRequest();

        coppelServicesLoginRequest.setEmail(email);
        coppelServicesLoginRequest.setPassword(password);
        coppelServicesLoginRequest.setApp("rhconecta");
        coppelServicesLoginRequest.setVersion(getVersionApp());

        return coppelServicesLoginRequest;
    }

    /* *******************************************************************************************************************************************************
     *****************************************************          Profile          *************************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param employeeNumber User Number
     * @param employeeEmail  User email
     * @param token          User token
     */
    public void getProfileValidation(String employeeNumber, String employeeEmail, String token) {
        this.token = token;
        getProfile(employeeNumber, employeeEmail,1);
    }

    /**
     * Make a request to get profile
     *
     * @param employeeNumber User Number
     * @param employeeEmail  User email
     */
    private void getProfile(String employeeNumber, String employeeEmail,int option) {
        final int type = ServicesRequestType.PROFILE;
        iServicesRetrofitMethods.getProfile(ServicesConstants.GET_PROFILE,token, buildProfileRequest(employeeNumber, employeeEmail,option)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    ProfileResponse profileResponse = (ProfileResponse) servicesUtilities.parseToObjectClass(response.body().toString(), ProfileResponse.class);
                    if (profileResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getProfileResponse(profileResponse, response.code(), type);
                    } else {
                        sendGenericError(type, response);
                    }

                } catch (Exception e) {
                    sendGenericError(type, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, type));
            }
        });
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getProfileResponse(ProfileResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getProfileSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_profile), servicesError));
        }
    }

    /**
     * Handles a successful response of the Profile method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getProfileSuccess(ProfileResponse response, int type) {
        ServicesResponse<ProfileResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Constructs the model to be sent for the profile request
     *
     * @param employeeNumber User Number
     * @param employeeEmail  User emai
     * @return CoppelServicesProfileRequest Request model
     */
    public CoppelServicesProfileRequest buildProfileRequest(String employeeNumber, String employeeEmail,int option) {
        CoppelServicesProfileRequest coppelServicesProfileRequest = new CoppelServicesProfileRequest();
        coppelServicesProfileRequest.setNum_empleado(employeeNumber);
        coppelServicesProfileRequest.setCorreo(employeeEmail);
        //Se agrega parámetro de opcion 09/04/2019
        coppelServicesProfileRequest.setOpcion(option);
        String tokenFirebase = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN);
        if(tokenFirebase!= null && !tokenFirebase.isEmpty()){
            coppelServicesProfileRequest.setId_firebase(tokenFirebase);
        }

        return coppelServicesProfileRequest;
    }

    /* *******************************************************************************************************************************************************
     *****************************************************          Cerrar sesión          *************************************************************************
     *********************************************************************************************************************************************************/
    /**
     * Validates if the data are correct
     *
     * @param employeeNumber User Number
     * @param employeeEmail  User email
     * @param token          User token
     */
    public void getLogoutValidation(String employeeNumber, String employeeEmail, String token) {
        this.token = token;
        logOut(employeeNumber, employeeEmail,2);
    }

    /**
     * Make a request to get profile
     *
     * @param employeeNumber User Number
     * @param employeeEmail  User email
     */
    private void logOut(String employeeNumber, String employeeEmail,int option) {
        final int type = ServicesRequestType.LOGOUT;
        iServicesRetrofitMethods.logout(ServicesConstants.WS_LOGOUT,token, buildProfileRequest(employeeNumber, employeeEmail,option)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    LogoutResponse logoutResponse = (LogoutResponse) servicesUtilities.parseToObjectClass(response.body().toString(), LogoutResponse.class);
                    if (logoutResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getLogoutResponse(logoutResponse, response.code(), type);
                    } else {
                        sendGenericError(type, response);
                    }

                } catch (Exception e) {
                    sendGenericError(type, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, type));
            }
        });
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getLogoutResponse(LogoutResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getLogoutSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_profile), servicesError));
        }
    }

    /**
     * Handles a successful response of the Profile method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getLogoutSuccess(LogoutResponse response, int type) {
        ServicesResponse<LogoutResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /* *******************************************************************************************************************************************************
     ***************************************************          Payroll Voucher      ***********************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param employeeNumber User Number
     * @param typePetition   Type Petition
     * @param token          User token
     */
    public void getPayrollVoucherValidation(String employeeNumber, int typePetition, String token) {
        this.token = token;
        getPayrollVoucher(employeeNumber, typePetition, token);
    }

    /**
     * Make a request to get voucher
     *
     * @param employeeNumber User Number
     * @param typePetition   Type Petition
     * @param token          User token
     */
    private void getPayrollVoucher(String employeeNumber, int typePetition, final String token) {

        final int type = ServicesRequestType.PAYROLL_VOUCHER;
        iServicesRetrofitMethods.getPayrollVoucher(ServicesConstants.GET_VOUCHER,token, buildPayrollVoucherRequest(employeeNumber, typePetition)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    VoucherResponse voucherResponse = (VoucherResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherResponse.class);
                    if (voucherResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getPayrollVoucherResponse(voucherResponse, response.code(), type);
                    } else {
                        sendGenericError(type, response);
                    }

                } catch (Exception e) {
                    sendGenericError(type, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, type));
            }
        });
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherResponse(VoucherResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.PAYROLL_VOUCHER);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Handles a successful response of the Voucher method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherSuccess(VoucherResponse response, int type) {
        ServicesResponse<VoucherResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Constructs the model to be sent for the voucher request
     *
     * @param employeeNumber User Number
     * @param typePetition   Type Petition
     * @return CoppelServicesPayrollVoucherRequest Request model
     */
    public CoppelServicesPayrollVoucherRequest buildPayrollVoucherRequest(String employeeNumber, int typePetition) {
        CoppelServicesPayrollVoucherRequest coppelServicesPayrollVoucherRequest = new CoppelServicesPayrollVoucherRequest();

        coppelServicesPayrollVoucherRequest.setNum_empleado(employeeNumber);
        coppelServicesPayrollVoucherRequest.setSolicitud(typePetition);

        return coppelServicesPayrollVoucherRequest;
    }

    /* *******************************************************************************************************************************************************
     **************************************************         Payroll Voucher Detail       *****************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param employeeNumber User Number
     * @param email          User email
     * @param typeConstancy  Type Constancy
     * @param request        Type Request
     * @param shippingOption Type ShippingOption
     * @param date           Date
     * @param data           Extra Data
     * @param token          User token
     */
    public void getPayrollVoucherDetailValidation(String employeeNumber, String email, int typeConstancy, int request, int shippingOption, String date, CoppelServicesPayrollVoucherDetailRequest.PayrollVoucherDetailGenericData data, String token) {
        this.token = token;
        getPayrollVoucherDetail(employeeNumber, email, typeConstancy, request, shippingOption, date, data, token);
    }

    /**
     * Make a request to get voucher detail
     *
     * @param employeeNumber User Number
     * @param email          User email
     * @param typeConstancy  Type Constancy
     * @param request        Type Request
     * @param shippingOption Type ShippingOption
     * @param date           Date
     * @param data           Extra Data
     * @param token          User token
     */
    private void getPayrollVoucherDetail(String employeeNumber, String email, final int typeConstancy, int request, final int shippingOption, String date, CoppelServicesPayrollVoucherDetailRequest.PayrollVoucherDetailGenericData data, String token) {

        iServicesRetrofitMethods.getPayrollVoucherDetail(ServicesConstants.GET_VOUCHER,token, buildPayrollVoucherDetailRequest(employeeNumber, email, typeConstancy, request, shippingOption, date, data)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (typeConstancy == 1) {
                    if (shippingOption == 0) {
                        try {
                            VoucherRosterResponse voucherRosterResponse = (VoucherRosterResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherRosterResponse.class);
                            if (voucherRosterResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                                getPayrollVoucherRosterDetailResponse(voucherRosterResponse, response.code(), ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DETAIL);
                            } else {
                                sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DETAIL, response);
                            }
                        } catch (Exception e) {
                            sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DETAIL, response);
                        }
                    } else if (shippingOption == 1) {
                        getDownloadVoucher(response, ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DOWNLOAD_DETAIL);
                    } else if (shippingOption == 2) {
                        getSendDetail(response, ServicesRequestType.PAYROLL_VOUCHER_ROSTER_SENDMAIL_DETAIL);
                    }

                } else if (typeConstancy == 2) {
                    if (shippingOption == 0) {
                        try {
                            VoucherSavingFundResponse voucherSavingFundResponse = (VoucherSavingFundResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherSavingFundResponse.class);
                            if (voucherSavingFundResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                                getPayrollVoucherSavingFundDetailResponse(voucherSavingFundResponse, response.code(), ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DETAIL);
                            } else {
                                sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DETAIL, response);
                            }
                        } catch (Exception e) {
                            sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DETAIL, response);
                        }
                    } else if (shippingOption == 1) {
                        getDownloadVoucher(response, ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DOWNLOAD_DETAIL);
                    } else if (shippingOption == 2) {
                        getSendDetail(response, ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_SENDMAIL_DETAIL);
                    }

                } else if (typeConstancy == 3) {
                    if (shippingOption == 0) {
                        try {
                            VoucherGasResponse voucherGasResponse = (VoucherGasResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherGasResponse.class);
                            if (voucherGasResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                                getPayrollVoucherGasDetailResponse(voucherGasResponse, response.code(), ServicesRequestType.PAYROLL_VOUCHER_GAS_DETAIL);
                            } else {
                                sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_GAS_DETAIL, response);
                            }
                        } catch (Exception e) {
                            sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_GAS_DETAIL, response);
                        }
                    } else if (shippingOption == 1) {
                        getDownloadVoucher(response, ServicesRequestType.PAYROLL_VOUCHER_GAS_DOWNLOAD_DETAIL);
                    } else if (shippingOption == 2) {
                        getSendDetail(response, ServicesRequestType.PAYROLL_VOUCHER_GAS_SENDMAIL_DETAIL);
                    }

                } else if (typeConstancy == 4) {
                    if (shippingOption == 0) {
                        try {
                            VoucherPTUResponse voucherPTUResponse = (VoucherPTUResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherPTUResponse.class);
                            if (voucherPTUResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                                getPayrollVoucherPTUDetailResponse(voucherPTUResponse, response.code(), ServicesRequestType.PAYROLL_VOUCHER_PTU_DETAIL);
                            } else {
                                sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_PTU_DETAIL, response);
                            }
                        } catch (Exception e) {
                            sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_PTU_DETAIL, response);
                        }
                    } else if (shippingOption == 1) {
                        getDownloadVoucher(response, ServicesRequestType.PAYROLL_VOUCHER_PTU_DOWNLOAD_DETAIL);
                    } else if (shippingOption == 2) {
                        getSendDetail(response, ServicesRequestType.PAYROLL_VOUCHER_PTU_SENDMAIL_DETAIL);
                    }

                } else if (typeConstancy == 5) {
                    if (shippingOption == 0) {
                        try {
                            VoucherAlimonyResponse voucherAlimonyResponse = (VoucherAlimonyResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherAlimonyResponse.class);
                            if (voucherAlimonyResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                                getPayrollVoucherAlimonyDetailResponse(voucherAlimonyResponse, response.code(), ServicesRequestType.PAYROLL_VOUCHER_ALIMONY_DETAIL);
                            } else {
                                sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_ALIMONY_DETAIL, response);
                            }
                        } catch (Exception e) {
                            sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_ALIMONY_DETAIL, response);
                        }
                    } else if (shippingOption == 1) {
                        getDownloadVoucher(response, ServicesRequestType.PAYROLL_VOUCHER_ALIMONY_DOWNLOAD_DETAIL);
                    } else if (shippingOption == 2) {
                        getSendDetail(response, ServicesRequestType.PAYROLL_VOUCHER_ALIMONY_SENDMAIL_DETAIL);
                    }

                } else if (typeConstancy == 6) {
                    if (shippingOption == 0) {
                        try {
                            VoucherBonusResponse voucherBonusResponse = (VoucherBonusResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherBonusResponse.class);
                            if (voucherBonusResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                                getPayrollVoucherBonusDetailResponse(voucherBonusResponse, response.code(), ServicesRequestType.PAYROLL_VOUCHER_BONUS_DETAIL);
                            } else {
                                sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_BONUS_DETAIL, response);
                            }
                        } catch (Exception e) {
                            sendGenericError(ServicesRequestType.PAYROLL_VOUCHER_BONUS_DETAIL, response);
                        }
                    } else if (shippingOption == 1) {
                        getDownloadVoucher(response, ServicesRequestType.PAYROLL_VOUCHER_BONUS_DOWNLOAD_DETAIL);
                    } else if (shippingOption == 2) {
                        getSendDetail(response, ServicesRequestType.PAYROLL_VOUCHER_BONUS_SENDMAIL_DETAIL);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.PAYROLL_VOUCHER_DETAIL));
            }
        });
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherRosterDetailResponse(VoucherRosterResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherRosterDetailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherSavingFundDetailResponse(VoucherSavingFundResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherSavingFundDetailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherGasDetailResponse(VoucherGasResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherGasDetailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherPTUDetailResponse(VoucherPTUResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherPTUDetailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherAlimonyDetailResponse(VoucherAlimonyResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.PAYROLL_VOUCHER_ALIMONY_DETAIL);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherAlimonyDetailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherBonusDetailResponse(VoucherBonusResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherBonusDetailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher), servicesError));
        }
    }

    /**
     * Handles a successful response of the Voucher Roster Detail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherRosterDetailSuccess(VoucherRosterResponse response, int type) {
        ServicesResponse<VoucherRosterResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Handles a successful response of the Voucher Saving Fund Detail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherSavingFundDetailSuccess(VoucherSavingFundResponse response, int type) {
        ServicesResponse<VoucherSavingFundResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Handles a successful response of the Voucher Gas Detail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherGasDetailSuccess(VoucherGasResponse response, int type) {
        ServicesResponse<VoucherGasResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);

    }

    /**
     * Handles a successful response of the Voucher PTU Detail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherPTUDetailSuccess(VoucherPTUResponse response, int type) {
        ServicesResponse<VoucherPTUResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Handles a successful response of the Voucher Alimony Detail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherAlimonyDetailSuccess(VoucherAlimonyResponse response, int type) {
        ServicesResponse<VoucherAlimonyResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Handles a successful response of the Voucher Bonus Detail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherBonusDetailSuccess(VoucherBonusResponse response, int type) {
        ServicesResponse<VoucherBonusResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Constructs the model to be sent for the Payroll Voucher Detail request
     *
     * @param employeeNumber User Number
     * @param email          User email
     * @param typeConstancy  Type Constancy
     * @param request        Type Request
     * @param shippingOption Type ShippingOption
     * @param date           Date
     * @param data           Extra Data
     * @return General Request model
     */
    public CoppelServicesPayrollVoucherDetailRequest buildPayrollVoucherDetailRequest(String employeeNumber, String email, int typeConstancy, int request, int shippingOption, String date, CoppelServicesPayrollVoucherDetailRequest.PayrollVoucherDetailGenericData data) {
        CoppelServicesPayrollVoucherDetailRequest coppelServicesPayrollVoucherDetailRequest = new CoppelServicesPayrollVoucherDetailRequest();

        coppelServicesPayrollVoucherDetailRequest.setNum_empleado(employeeNumber);
        coppelServicesPayrollVoucherDetailRequest.setCorreo(email);
        coppelServicesPayrollVoucherDetailRequest.setTipo_Constancia(typeConstancy);
        coppelServicesPayrollVoucherDetailRequest.setSolicitud(request);
        coppelServicesPayrollVoucherDetailRequest.setOpcionEnvio(shippingOption);
        coppelServicesPayrollVoucherDetailRequest.setFecha(date);
        coppelServicesPayrollVoucherDetailRequest.setDatos(data);

        return coppelServicesPayrollVoucherDetailRequest;
    }

    /* *******************************************************************************************************************************************************
     ***************************************************        Send Voucher Mail          *******************************************************************
     *********************************************************************************************************************************************************/

    public void getSendDetail(Response<JsonObject> response, int servicesRequestType) {

        try {
            CoppelGeneralParameterResponse coppelGeneralParameterResponse = (CoppelGeneralParameterResponse) servicesUtilities.parseToObjectClass(response.body().toString(), CoppelGeneralParameterResponse.class);
            if (coppelGeneralParameterResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                VoucherSendMailResponse voucherSendMailResponse = (VoucherSendMailResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherSendMailResponse.class);
                setPayrollVoucherToEmailResponse(voucherSendMailResponse, response.code(), servicesRequestType);
            } else {
                sendGenericError(servicesRequestType, response);
            }

        } catch (Exception e) {
            sendGenericError(servicesRequestType, response);
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    private void setPayrollVoucherToEmailResponse(VoucherSendMailResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherSendMailSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher_send_mail), servicesError));
        }
    }

    /**
     * Handles a successful response of the Send Mail method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherSendMailSuccess(VoucherSendMailResponse response, int type) {
        ServicesResponse<VoucherSendMailResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /* *******************************************************************************************************************************************************
     ***************************************************        Download Voucher           *******************************************************************
     *********************************************************************************************************************************************************/

    public void getDownloadVoucher(Response<JsonObject> response, int servicesRequestType) {
        try {
            CoppelGeneralParameterResponse coppelGeneralParameterResponse = (CoppelGeneralParameterResponse) servicesUtilities.parseToObjectClass(response.body().toString(), CoppelGeneralParameterResponse.class);
            if (coppelGeneralParameterResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                VoucherDownloadResponse voucherDownloadResponse = (VoucherDownloadResponse) servicesUtilities.parseToObjectClass(response.body().toString(), VoucherDownloadResponse.class);
                setPayrollVoucherDownloadResponse(voucherDownloadResponse, response.code(), servicesRequestType);
            } else {
                sendGenericError(servicesRequestType, response);
            }

        } catch (Exception e) {
            sendGenericError(servicesRequestType, response);
        }
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    private void setPayrollVoucherDownloadResponse(VoucherDownloadResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getPayrollVoucherDownloadSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_voucher_download), servicesError));
        }
    }

    /**
     * Handles a successful response of the Download Voucher method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getPayrollVoucherDownloadSuccess(VoucherDownloadResponse response, int type) {
        ServicesResponse<VoucherDownloadResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /* *******************************************************************************************************************************************************
     ***************************************************        Loans / Saving Fund          *****************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param employeeNumber User Number
     * @param token          User token
     */
    public void getLoansSavingFundValidation(String employeeNumber, String token) {
        this.token = token;
        getLoansSavingFund(employeeNumber, token);
    }

    /**
     * Make a request to get Loans/SavingFund
     *
     * @param employeeNumber User Number
     * @param token          User token
     */
    private void getLoansSavingFund(String employeeNumber, String token) {

        iServicesRetrofitMethods.getLoansSavingFund(ServicesConstants.GET_LOANSAVINGFUND,token, buildPayrollVoucherRequest(employeeNumber)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    LoanSavingFundResponse loanSavingFundResponse = (LoanSavingFundResponse) servicesUtilities.parseToObjectClass(response.body().toString(), LoanSavingFundResponse.class);
                    if (loanSavingFundResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getLoanSavingFundResponse(loanSavingFundResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.LOAN_SAVINGFUND, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.LOAN_SAVINGFUND, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.LOAN_SAVINGFUND));
            }
        });
    }

    /**
     * Checks whether the server response is successful or error
     *
     * @param response Server response
     */
    public void getLoanSavingFundResponse(LoanSavingFundResponse response, int code) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LOAN_SAVINGFUND);

        if (servicesGeneralValidations.verifySuccessCode(code)) {
            getLoanSavingFundSuccess(response);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_profile), servicesError));
        }
    }

    /**
     * Handles a successful response of the Loans/SavingFund method
     *
     * @param response Server response
     */
    public void getLoanSavingFundSuccess(LoanSavingFundResponse response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LOAN_SAVINGFUND);

        if (response != null && response != null) {

            LoanSavingFundResponse voucherResponse = response;

            if (voucherResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                ServicesResponse<LoanSavingFundResponse> servicesResponse = new ServicesResponse<>();
                servicesResponse.setResponse(voucherResponse);
                servicesResponse.setType(ServicesRequestType.LOAN_SAVINGFUND);
                iServiceListener.onResponse(servicesResponse);
            } else {
                servicesError.setMessage(voucherResponse.getData().getResponse().getUserMessage());
                iServiceListener.onError(servicesError);
            }

        } else {
            servicesError.setMessage(context.getString(R.string.error_voucher));
            iServiceListener.onError(servicesError);
        }
    }

    /**
     * Constructs the model to be sent for the Loans/SavingFund request
     *
     * @param employeeNumber User Number
     * @return General Request model
     */
    public CoppelServicesLoanSavingFundRequest buildPayrollVoucherRequest(String employeeNumber) {
        CoppelServicesLoanSavingFundRequest coppelServicesLoanSavingFundRequest = new CoppelServicesLoanSavingFundRequest();

        coppelServicesLoanSavingFundRequest.setNum_empleado(employeeNumber);

        return coppelServicesLoanSavingFundRequest;
    }

    /* *******************************************************************************************************************************************************
     ***************************************************        Recovery Password          *******************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Make a request to get url recover password
     */
    public void getRecoverPassword(int clave) {

        iServicesRetrofitMethods.getRecoveryPassword(ServicesConstants.GET_RECOVERY_PASSWORD,buildRecoveryPasswordRequest(clave)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                int type = ServicesRequestType.RECOVERY_PASSWORD;
                try {
                    RecoveryPasswordResponse recoveryPasswordResponse = (RecoveryPasswordResponse) servicesUtilities.parseToObjectClass(response.body().toString(), RecoveryPasswordResponse.class);

                    if (recoveryPasswordResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {

                        getRecoveryPasswordResponse(recoveryPasswordResponse, response.code(), type);

                    } else {
                        sendGenericError(type, response);
                    }

                } catch (Exception e) {
                    sendGenericError(type, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.RECOVERY_PASSWORD));
            }
        });
    }

    /**
     * Checks that the response code is equal to 200
     *
     * @param response Server response
     * @param code     Code response
     * @param type     Services Request Type
     */
    public void getRecoveryPasswordResponse(RecoveryPasswordResponse response, int code, int type) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(type);

        if (response != null && servicesGeneralValidations.verifySuccessCode(code)) {
            getRecoveryPasswordSuccess(response, type);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_recover_password), servicesError));
        }
    }

    /**
     * Handles a successful response of the Recovery Password method
     *
     * @param response Server response
     * @param type     Services Request Type
     */
    public void getRecoveryPasswordSuccess(RecoveryPasswordResponse response, int type) {
        ServicesResponse<RecoveryPasswordResponse> servicesResponse = new ServicesResponse<>();
        servicesResponse.setResponse(response);
        servicesResponse.setType(type);
        iServiceListener.onResponse(servicesResponse);
    }

    /**
     * Constructs the model to be sent for the recovery password
     *
     * @return General Request model
     */
    public CoppelServicesRecoveryPasswordRequest buildRecoveryPasswordRequest(int clave) {
        CoppelServicesRecoveryPasswordRequest coppelServicesRecoveryPasswordRequest = new CoppelServicesRecoveryPasswordRequest();
        coppelServicesRecoveryPasswordRequest.setSolicitud(2);
        coppelServicesRecoveryPasswordRequest.setClave(clave);
        return coppelServicesRecoveryPasswordRequest;
    }

    /* *******************************************************************************************************************************************************
     ***************************************************          Letters Validate Signature          ********************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param employeeNumber User Number
     */
    /*public void getLettersValidateSignatureValidation(String employeeNumber,String token) {
        this.token = token;
        final ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERSVALIDATIONSIGNATURE);
        //String validation = servicesGeneralValidations.validatePassword(password, context);
        String validation = ServicesConstants.SUCCESS;
        if (validation.equals(ServicesConstants.SUCCESS)) {
            getLettersValidateSignature(employeeNumber);
        } else {
            servicesError.setMessage(validation);
            iServiceListener.onError(servicesError);
        }
    }*/

    public void getLettersValidateSignatureValidation(String employeeNumber,String token) {
        this.token = token;
        getLettersValidateSignature(employeeNumber);
    }

    /**
     * Make a request to get Letters Validate Signature
     *
     * @param employeeNumber User Number
     */
    private void getLettersValidateSignature(String employeeNumber) {

        iServicesRetrofitMethods.getLettersValidationSignature(ServicesConstants.GET_LETTERS_VALIDATE_SIGNATURE,token, buildLettersSignatureRequest(employeeNumber)).enqueue(new Callback<LetterSignatureResponse>() {
            @Override
            public void onResponse(Call<LetterSignatureResponse> call, Response<LetterSignatureResponse> response) {

                getLettersValidationSignatureResponse(response);
            }

            @Override
            public void onFailure(Call<LetterSignatureResponse> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.LETTERSVALIDATIONSIGNATURE));
            }
        });
    }

    /**
     * Checks whether the server response is successful or error
     *
     * @param response Server response
     */
    public void getLettersValidationSignatureResponse(Response<LetterSignatureResponse> response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERSVALIDATIONSIGNATURE);

        if (servicesGeneralValidations.verifySuccessCode(response.code())) {
            getLettersSignatureValidationSuccess(response);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, response.code(), context.getString(R.string.error_letters), servicesError));
        }
    }

    /**
     * Handles a successful response of the Letters Signature Validation method
     *
     * @param response Server response
     */
    public void getLettersSignatureValidationSuccess(Response<LetterSignatureResponse> response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERSVALIDATIONSIGNATURE);

        if (response != null && response.body() != null) {

            LetterSignatureResponse letterSignatureResponse = response.body();

            if (letterSignatureResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {

                ServicesResponse<LetterSignatureResponse> servicesResponse = new ServicesResponse<>();
                servicesResponse.setResponse(letterSignatureResponse);
                servicesResponse.setType(ServicesRequestType.LETTERSVALIDATIONSIGNATURE);
                iServiceListener.onResponse(servicesResponse);

            } else {
                servicesError.setMessage(letterSignatureResponse.getData().getResponse().getUserMessage());
                iServiceListener.onError(servicesError);
            }

        } else {
            servicesError.setMessage(context.getString(R.string.error_profile));
            iServiceListener.onError(servicesError);
        }
    }

    /**
     * Constructs the model to be sent for the letters validation signature request
     *
     * @param employeeNumber User Number
     * @return General Request model
     */
    public CoppelServicesLettersSignatureRequest buildLettersSignatureRequest(String employeeNumber) {
        CoppelServicesLettersSignatureRequest coppelServicesLettersSignatureRequest = new CoppelServicesLettersSignatureRequest();
        coppelServicesLettersSignatureRequest.setNum_empleado(employeeNumber);
        return coppelServicesLettersSignatureRequest;
    }

    /* *******************************************************************************************************************************************************
     ***********************************************          Letters Config          ************************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param numEmpleado User Number
     * @param tipoCarta   Type letter
     */
   /* public void getLettersConfigValidation(String numEmpleado, int tipoCarta) {
        final ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERSCONFIG);
        //String validation = servicesGeneralValidations.validatePassword(password, context);
        String validation = ServicesConstants.SUCCESS;
        if (validation.equals(ServicesConstants.SUCCESS)) {
            getLettersConfig(numEmpleado, tipoCarta);
        } else {
            servicesError.setMessage(validation);
            iServiceListener.onError(servicesError);
        }
    }*/

    public void getLettersConfigValidation(String numEmpleado, int tipoCarta,String token) {
        this.token = token;
        getLettersConfig(numEmpleado,tipoCarta);
    }


    /**
     * Make a request to get Letters Validate Signature
     *
     * @param numEmpleado User Number
     */
    private void getLettersConfig(String numEmpleado, int tipoCarta) {

        iServicesRetrofitMethods.getLettersConfig(ServicesConstants.GET_CONFIG,token, buildLettersConfigRequest(numEmpleado, tipoCarta)).enqueue(new Callback<LetterConfigResponse>() {
            @Override
            public void onResponse(Call<LetterConfigResponse> call, Response<LetterConfigResponse> response) {
                getLettersConfigResponse(response);
            }

            @Override
            public void onFailure(Call<LetterConfigResponse> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.LETTERSCONFIG));
            }
        });
    }

    /**
     * Checks whether the server response is successful or error
     *
     * @param response Server response
     */
    public void getLettersConfigResponse(Response<LetterConfigResponse> response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERSCONFIG);

        if (servicesGeneralValidations.verifySuccessCode(response.code())) {
            getLettersConfigSuccess(response);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, response.code(), context.getString(R.string.error_letters), servicesError));
        }
    }

    /**
     * Handles a successful response of the Letters Config method
     *
     * @param response Server response
     */
    public void getLettersConfigSuccess(Response<LetterConfigResponse> response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERSCONFIG);

        if (response != null && response.body() != null) {

            LetterConfigResponse letterConfigResponse = response.body();

            if (letterConfigResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {

                ServicesResponse<LetterConfigResponse> servicesResponse = new ServicesResponse<>();
                servicesResponse.setResponse(letterConfigResponse);
                servicesResponse.setType(ServicesRequestType.LETTERSCONFIG);
                iServiceListener.onResponse(servicesResponse);

            } else {
                servicesError.setMessage(letterConfigResponse.getData().getResponse().getUserMessage());
                iServiceListener.onError(servicesError);
            }

        } else {
            servicesError.setMessage(context.getString(R.string.error_profile));
            iServiceListener.onError(servicesError);
        }
    }

    /**
     * Constructs the model to be sent for the letters validation signature request
     *
     * @param employeeNumber User Number
     * @param tipoCarta      Type letter
     * @return General Request model
     */
    public CoppelServicesLettersConfigRequest buildLettersConfigRequest(String employeeNumber, int tipoCarta) {
        CoppelServicesLettersConfigRequest coppelServicesLettersConfigRequest = new CoppelServicesLettersConfigRequest();
        coppelServicesLettersConfigRequest.setNum_empleado(employeeNumber);
        coppelServicesLettersConfigRequest.setTipo_carta(tipoCarta);
        return coppelServicesLettersConfigRequest;
    }


    /* *******************************************************************************************************************************************************
     ***********************************************          Letters Preview          ************************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param numEmpleado User Number
     * @param tipoCarta   Type letter
     * @param fields   fields to letter
     * @param token   user token
     */
    public void getLettersPreview(String numEmpleado, int tipoCarta, Map<String, Object> fields, String token) {
        this.token = token;
        getLetterPreview(numEmpleado,tipoCarta,fields);
    }


    /**
     * Make a request to get Letters Validate Signature
     *
     * @param numEmpleado User Number
     */
    private void getLetterPreview(String numEmpleado, int tipoCarta, Map<String, Object> fields) {

        iServicesRetrofitMethods.getLettersPreview(ServicesConstants.GET_LETTER_PREVIEW,token, buildLetterPreviewRequest(numEmpleado, tipoCarta,fields)).enqueue(new Callback<LetterPreviewResponse>() {
            @Override
            public void onResponse(Call<LetterPreviewResponse> call, Response<LetterPreviewResponse> response) {
                getLetterPreviewResponse(response);
            }

            @Override
            public void onFailure(Call<LetterPreviewResponse> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.LETTERSCONFIG));
            }
        });
    }


    /**
     * Checks whether the server response is successful or error
     *
     * @param response Server response
     */
    public void getLetterPreviewResponse(Response<LetterPreviewResponse> response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERPREVIEW);

        if (servicesGeneralValidations.verifySuccessCode(response.code())) {
            getLetterPreviewSuccess(response);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, response.code(), context.getString(R.string.error_letters), servicesError));
        }
    }


    /**
     * Handles a successful response of the Letters Config method
     *
     * @param response Server response
     */
    public void getLetterPreviewSuccess(Response<LetterPreviewResponse> response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.LETTERPREVIEW);

        if (response != null && response.body() != null) {

            LetterPreviewResponse letterPreviewResponse = response.body();

            if (letterPreviewResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {

                ServicesResponse<LetterPreviewResponse> servicesResponse = new ServicesResponse<>();
                servicesResponse.setResponse(letterPreviewResponse);
                servicesResponse.setType(ServicesRequestType.LETTERPREVIEW);
                iServiceListener.onResponse(servicesResponse);

            } else {
                servicesError.setMessage(letterPreviewResponse.getData().getResponse().getUserMessage());
                iServiceListener.onError(servicesError);
            }

        } else {
            servicesError.setMessage(context.getString(R.string.error_profile));
            iServiceListener.onError(servicesError);
        }
    }

    /**
     * Constructs the model to be sent for the letters validation signature request
     *
     * @param employeeNumber User Number
     * @param tipoCarta      Type letter
     * @param fields      fields to letter
     * @return General Request model
     */
    public CoppelServicesLettersPreviewRequest buildLetterPreviewRequest(String employeeNumber, int tipoCarta, Map<String, Object>  fields) {
        CoppelServicesLettersPreviewRequest coppelServicesLettersPreviewRequest = new CoppelServicesLettersPreviewRequest();
        coppelServicesLettersPreviewRequest.setNum_empleado(employeeNumber);
        coppelServicesLettersPreviewRequest.setTipo_carta(tipoCarta);
        coppelServicesLettersPreviewRequest.setDatos(fields);

        Gson gson = new Gson();
        String json = gson.toJson(coppelServicesLettersPreviewRequest);
        Log.d("PREVIEW",json.toString());

        return coppelServicesLettersPreviewRequest;
    }



    /* *******************************************************************************************************************************************************
     ***********************************************          Letters Generate          ************************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param numEmpleado User Number
     * @param tipoCarta   Type letter
     * @param fields   fields to letter
     * @param token   user token
     */
    public <T>  void getLettersGenerate(String numEmpleado, int tipoCarta,int opcionEnvio, String correo, Map<String, T > fields, String token) {
        this.token = token;
        getLetterGenerate(numEmpleado,tipoCarta,opcionEnvio,correo,fields);
    }

    /**
     * Make a request to get Letters Validate Signature
     *
     * @param numEmpleado User Number
     */
    private <T> void getLetterGenerate(String numEmpleado, int tipoCarta,final int opcionEnvio, String correo, Map<String, T >  data) {
        iServicesRetrofitMethods.getLettersGenerate(ServicesConstants.GET_LETTER_GENERATE,token, buildLetterGenerateRequest(numEmpleado, tipoCarta, opcionEnvio, correo,data)).enqueue(new Callback<LetterGenerateResponse>() {
            @Override
            public void onResponse(Call<LetterGenerateResponse> call, Response<LetterGenerateResponse> response) {
                getLetterGenerateResponse(response,opcionEnvio);
            }
            @Override
            public void onFailure(Call<LetterGenerateResponse> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t,
                        opcionEnvio == ServicesConstants.SHIPPING_OPTION_SEND_EMAIL ?
                                ServicesRequestType.LETTERGENERATE_SENDMAIL : ServicesRequestType.LETTERGENERATE_DOWNLOADMAIL));
            }
        });
    }

    /**
     * Checks whether the server response is successful or error
     *
     * @param response Server response
     */
    public void getLetterGenerateResponse(Response<LetterGenerateResponse> response, int opcionEnvio) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType( opcionEnvio == ServicesConstants.SHIPPING_OPTION_SEND_EMAIL ?
                ServicesRequestType.LETTERGENERATE_SENDMAIL : ServicesRequestType.LETTERGENERATE_DOWNLOADMAIL);

        if (servicesGeneralValidations.verifySuccessCode(response.code())) {
            getLetterGenerateSuccess(response,opcionEnvio);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, response.code(), context.getString(R.string.error_letters), servicesError));
        }
    }

    /**
     * Handles a successful response of the Letters Config method
     *
     * @param response Server response
     */
    public void getLetterGenerateSuccess(Response<LetterGenerateResponse> response , int opcionEnvio) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType( opcionEnvio == ServicesConstants.SHIPPING_OPTION_SEND_EMAIL ?
                ServicesRequestType.LETTERGENERATE_SENDMAIL : ServicesRequestType.LETTERGENERATE_DOWNLOADMAIL);

        if (response != null && response.body() != null) {

            LetterGenerateResponse letterGenerateResponse = response.body();
            if (letterGenerateResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                ServicesResponse<LetterGenerateResponse> servicesResponse = new ServicesResponse<>();
                servicesResponse.setResponse(letterGenerateResponse);
                servicesResponse.setType( opcionEnvio == ServicesConstants.SHIPPING_OPTION_SEND_EMAIL ?
                        ServicesRequestType.LETTERGENERATE_SENDMAIL : ServicesRequestType.LETTERGENERATE_DOWNLOADMAIL);
                iServiceListener.onResponse(servicesResponse);
            } else {
                servicesError.setMessage(letterGenerateResponse.getData().getResponse().getUserMessage());
                iServiceListener.onError(servicesError);
            }
        } else {
            servicesError.setMessage(context.getString(R.string.error_profile));
            iServiceListener.onError(servicesError);
        }
    }
    /**
     * Constructs the model to be sent for the letters validation signature request
     *
     * @param employeeNumber User Number
     * @param tipoCarta      Type letter
     * @param data      fields to letter
     * @return General Request model
     */
    public <T> CoppelServicesLettersGenerateRequest buildLetterGenerateRequest(String employeeNumber, int tipoCarta, int opcionEnvio, String correo, Map<String, T>  data) {
        CoppelServicesLettersGenerateRequest coppelServicesLettersGenerateRequest = new CoppelServicesLettersGenerateRequest();
        coppelServicesLettersGenerateRequest.setNum_empleado(employeeNumber);
        coppelServicesLettersGenerateRequest.setTipo_carta(tipoCarta);
        coppelServicesLettersGenerateRequest.setCorreo(correo);
        coppelServicesLettersGenerateRequest.setOpcionEnvio(opcionEnvio);
        coppelServicesLettersGenerateRequest.setDatos(data);


        Gson gson = new Gson();
        String json = gson.toJson(coppelServicesLettersGenerateRequest);
        Log.d("PREVIEW",json.toString());

        return coppelServicesLettersGenerateRequest;
    }

    /******************************************************/
    /* *******************************************************************************************************************************************************
     ***************************************************        Benefits          *****************************************************************
     *********************************************************************************************************************************************************/

    /**
     * Validates if the data are correct
     *
     * @param benefitsRequestData BenefitsRequestData Number
     * @param token          User token
     */
    public void getBenefits(BenefitsRequestData benefitsRequestData, String token) {
        this.token = token;
        switch (benefitsRequestData.getSolicitud()){
            case  1:
                getBenefitsStatesRequest(benefitsRequestData, token);
                break;
            case  2:
                getBenefitsCitiesRequest(benefitsRequestData, token);
                break;
            case  3:
                getBenefitsCategoriesRequest(benefitsRequestData, token);
                break;
            case  4:
                getBenefitsDiscountsRequest(benefitsRequestData, token);
                break;
            case  5:
                getBenefitsCompanyRequest(benefitsRequestData, token);
                break;

            case  6:
                getBenefitsSearchRequest(benefitsRequestData, token);
                break;

            case  7:
                getBenefitsAdvertisingRequest(benefitsRequestData, token);
                break;
        }

    }

    private void getBenefitsAdvertisingRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsAdvertising(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsAdvertisingRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    BenefitsBaseResponse benefitsBaseResponse =   (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }
                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }

    private void getBenefitsStatesRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsStates(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsStatesRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {

                    BenefitsBaseResponse benefitsBaseResponse =   (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    //getBenefitsResponse(benefitsRequestData.getBenefits_type());
                    if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }

    private void getBenefitsCitiesRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsCity(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsCityRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {

                    BenefitsBaseResponse benefitsBaseResponse =   (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    //getBenefitsResponse(benefitsRequestData.getBenefits_type());
                    if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }

    /**
     * Make a request to get Loans/SavingFund
     *
     * @param benefitsRequestData BenefitsRequestData Number
     * @param token          User token
     */
    private void getBenefitsCategoriesRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsCategories(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsCategoriesRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {

                    BenefitsBaseResponse benefitsBaseResponse = (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    //getBenefitsResponse(benefitsRequestData.getBenefits_type());
                    if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }

    private void getBenefitsDiscountsRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsDiscounts(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsDiscountsRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {

                    BenefitsBaseResponse benefitsBaseResponse =   (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    //getBenefitsResponse(benefitsRequestData.getBenefits_type());

                    if(benefitsBaseResponse == null){
                        BenefitsEmptyResponse benefitsEmptyResponse =  (BenefitsEmptyResponse) servicesUtilities.parseToObjectClass(response.body().toString(),BenefitsEmptyResponse.class);


                        getBenefitsResponse(benefitsEmptyResponse, response.code());
                    }else if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }


    private void getBenefitsCompanyRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsCompany(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsCompanyRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {

                    BenefitsBaseResponse benefitsBaseResponse =   (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    //getBenefitsResponse(benefitsRequestData.getBenefits_type());
                    if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }

    private void getBenefitsSearchRequest(BenefitsRequestData benefitsRequestData, String token) {
        iServicesRetrofitMethods.getBenefitsSearch(ServicesConstants.GET_BENEFITS,token,(CoppelServicesBenefitsSearchRequest) buildBenefitsRequest(benefitsRequestData)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {

                    BenefitsBaseResponse benefitsBaseResponse =   (BenefitsBaseResponse) servicesUtilities.parseToObjectClass(response.body().toString(), getBenefitsResponse(benefitsRequestData.getBenefits_type()));
                    //getBenefitsResponse(benefitsRequestData.getBenefits_type());
                    if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                        getBenefitsResponse(benefitsBaseResponse, response.code());
                    } else {
                        sendGenericError(ServicesRequestType.BENEFITS, response);
                    }

                } catch (Exception e) {
                    sendGenericError(ServicesRequestType.BENEFITS, response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                iServiceListener.onError(servicesUtilities.getOnFailureResponse(context, t, ServicesRequestType.BENEFITS));
            }
        });
    }

    /**
     * Checks whether the server response is successful or error
     *
     * @param response Server response
     */
    public void getBenefitsResponse(BenefitsBaseResponse response, int code) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.BENEFITS);

        if (servicesGeneralValidations.verifySuccessCode(code)) {
            getBenefitsSuccess(response);
        } else {
            iServiceListener.onError(servicesUtilities.getErrorByStatusCode(context, code, context.getString(R.string.error_profile), servicesError));
        }
    }

    /**
     * Handles a successful response of the Loans/SavingFund method
     *
     * @param response Server response
     */
    public void getBenefitsSuccess(BenefitsBaseResponse response) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(ServicesRequestType.BENEFITS);

        if (response != null && response != null) {

            BenefitsBaseResponse benefitsBaseResponse = response;
            if (benefitsBaseResponse.getMeta().getStatus().equals(ServicesConstants.SUCCESS)) {
                ServicesResponse<BenefitsBaseResponse> servicesResponse = new ServicesResponse<>();
                servicesResponse.setResponse(benefitsBaseResponse);
                servicesResponse.setType(ServicesRequestType.BENEFITS);
                iServiceListener.onResponse(servicesResponse);
            } else {
                servicesError.setMessage(""/*benefitsBaseResponse.getData().getResponse().getUserMessage()*/);
                iServiceListener.onError(servicesError);
            }

        } else {
            servicesError.setMessage(context.getString(R.string.error_voucher));
            iServiceListener.onError(servicesError);
        }
    }

    /**
     * Constructs the model to be sent for the Loans/SavingFund request
     *
     * @param benefitsRequestData {BenefitsRequestData} request
     * @return General Request model
     */
    public CoppelServicesBenefitsBaseRequest buildBenefitsRequest(BenefitsRequestData benefitsRequestData) {
        CoppelServicesBenefitsBaseRequest coppelServicesBenefitsRequest = null;

        int solicitud = benefitsRequestData.getSolicitud();

        switch (benefitsRequestData.getBenefits_type()){

            case BENEFITS_STATES:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsStatesRequest(solicitud);
                break;
            case BENEFITS_CITY:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsCityRequest(solicitud,benefitsRequestData.getNum_estado());
                break;
            case BENEFITS_CATEGORIES:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsCategoriesRequest(solicitud,
                        benefitsRequestData.getNum_estado(),
                        benefitsRequestData.getNum_ciudad());
                break;
            case BENEFITS_DISCOUNTS:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsDiscountsRequest(solicitud,
                        benefitsRequestData.getNum_estado(),
                        benefitsRequestData.getNum_ciudad(),
                        benefitsRequestData.getClave_servicio());
                break;
            case BENEFITS_COMPANY:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsCompanyRequest(solicitud,
                        benefitsRequestData.getNum_estado(),
                        benefitsRequestData.getNum_ciudad(),
                        benefitsRequestData.getClave_servicio(),
                        String.valueOf(benefitsRequestData.getIdempresa()));
                break;
            case BENEFITS_ADVERTISING:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsAdvertisingRequest(solicitud,
                        benefitsRequestData.getIdempresa());
                break;

            case BENEFITS_SEARCH:
                coppelServicesBenefitsRequest = new CoppelServicesBenefitsSearchRequest(solicitud,
                        benefitsRequestData.getNum_estado(),
                        benefitsRequestData.getNum_ciudad(),
                        benefitsRequestData.getDes_busqueda());
                break;
        }

        return coppelServicesBenefitsRequest;
    }

    public Class getBenefitsResponse(BenefitsType benefitsType) {
        BenefitsBaseResponse benefitsBaseResponse = null;

        Class clazz = null;
        switch (benefitsType){

            case BENEFITS_STATES:
                benefitsBaseResponse = new BenefitsStatesResponse();
                clazz = BenefitsStatesResponse.class;
                break;
            case BENEFITS_CITY:
                benefitsBaseResponse = new BenefitsCitiesResponse();
                clazz = BenefitsCitiesResponse.class;

                break;
            case BENEFITS_CATEGORIES:
                benefitsBaseResponse = new BenefitsCategoriesResponse();
                clazz = BenefitsCategoriesResponse.class;

                break;
            case BENEFITS_DISCOUNTS:
                benefitsBaseResponse = new BenefitsDiscountsResponse();
                clazz = BenefitsDiscountsResponse.class;

                break;
            case BENEFITS_COMPANY:
                benefitsBaseResponse = new BenefitsCompaniesResponse();
                clazz = BenefitsCompaniesResponse.class;

                break;
            case BENEFITS_ADVERTISING:
                benefitsBaseResponse = new BenefitsAdvertisingResponse();
                clazz = BenefitsAdvertisingResponse.class;

                break;

            case BENEFITS_SEARCH:
                benefitsBaseResponse = new BenefitsSearchResponse();
                clazz = BenefitsSearchResponse.class;
                break;
        }

        return clazz;
    }


    /******************************************************/

    /* *******************************************************************************************************************************************************
     ***********************************************          General Methods          ************************************************************************
     *********************************************************************************************************************************************************/

    /* ServiceListener */
    public void setOnServiceListener(IServiceListener iServiceListener) {
        this.iServiceListener = iServiceListener;
    }

    /* Generic Error Service */
    public void sendGenericError(int type, @NonNull Response<JsonObject> response, boolean ... params) {
        boolean executeInBackground = false;
        if(params != null && params.length > 0){
            executeInBackground = params[0];
        }

        ServicesError servicesError = new ServicesError();
        servicesError.setExecuteInBackground(executeInBackground);
        servicesError.setType(type);

        try {
            GeneralErrorResponse generalErrorResponse = (GeneralErrorResponse) servicesUtilities.parseToObjectClass(response.body().toString(), GeneralErrorResponse.class);
            servicesError.setMessage(sendMessageFromCode(generalErrorResponse.getData().getResponse().getErrorCode(), generalErrorResponse.getData().getResponse().getUserMessage()));
            servicesError.setType(sendTypeTokenResponse(generalErrorResponse.getData().getResponse().getErrorCode(), type));
            iServiceListener.onError(servicesError);
        } catch (Exception e) {
            servicesError.setMessage(context.getString(R.string.error_generic_service));
            iServiceListener.onError(servicesError);
        }
    }

    /* Parse code response service to message*/
    public String sendMessageFromCode(int errorCode, String userMessage) {
        String message = "";

        if (errorCode == -33 || errorCode == -99 || errorCode == -5 || errorCode == -1) {
            message = context.getString(R.string.error_generic_service);
        } else {
            message = userMessage;
        }
        return message;
    }

    /* Parse code response service to validate token*/
    public int sendTypeTokenResponse(int errorCode, int type) {
        if (errorCode == -6) {
            return type = ServicesRequestType.INVALID_TOKEN;
        } else {
            return type;
        }
    }

}