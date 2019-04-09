package com.coppel.rhconecta.dev.business.utils;

import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_BENEFICIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_CARTASCONFIG;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_CARTASGENERAR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_LOGIN;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PASSWORD_RECOVER;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PAYSHEET;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PREVIEW;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PROFILE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_SAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_VALIDARFIRMA;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_WITHDRAWSAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.URL_MAIN;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ServicesConstants {

    public static final String REGEX_EMAIL = "[a-zA-Z0-9._+-]+@[a-zA-Z]+\\.+[a-z]+";
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    /**TESTING ENVIROMENT*/
   /* public static final String URL_BASE = "http://qa-apipos.coppel.com:9000/";
    public static final String GET_LOGIN = "rhconecta/api/v1/inicioSesion";
    public static final String GET_PROFILE = "rhconecta/api/v2/perfilColaboradorConsulta";
    public static final String GET_LETTERS_VALIDATE_SIGNATURE = "rhconecta/api/v2/cartasValidarFirma";
    public static final String GET_CONFIG = "rhconecta/api/v2/cartasConfig";
    public static final String GET_LETTER_PREVIEW= "rhconecta/api/v2/cartasVistaPrevia";
    public static final String GET_LETTER_GENERATE= "rhconecta/api/v2/cartasGenerar";
    public static final String GET_VOUCHER = "rhconecta/api/v2/comprobantesNomina";
    public static final String GET_LOANSAVINGFUND = "rhconecta/api/v2/fondoAhorroConsulta";
    public static final String GET_RECOVERY_PASSWORD = "rhconecta/api/v2/recuperacontra";*/

    public static  String URL_BASE = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), URL_MAIN);
    public static  String GET_LOGIN = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_LOGIN);
    public static  String GET_PROFILE = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_PROFILE);
    public static  String GET_LETTERS_VALIDATE_SIGNATURE = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_VALIDARFIRMA);
    public static  String GET_CONFIG = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_CARTASCONFIG);
    public static  String GET_LETTER_PREVIEW= AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_PREVIEW);
    public static  String GET_LETTER_GENERATE= AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_CARTASGENERAR);
    public static  String GET_VOUCHER = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_PAYSHEET);
    public static  String GET_LOANSAVINGFUND = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_SAVINGS);
    public static  String GET_RECOVERY_PASSWORD = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_PASSWORD_RECOVER);
    public static  String GET_BENEFITS = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_BENEFICIOS);
    public static  String GET_WITHDRAWSAVINGS= AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_WITHDRAWSAVINGS);

    public static  String WS_LOGOUT = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_PROFILE);



    // AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_BENEFICIOS,beneficios);




    /**PROD ENVIROMENT*/
   /* public static final String URL_BASE = "https://apipos.coppel.cloud/";
    public static final String GET_LOGIN = "appcoppel/api/v1/inicioSesion";
    public static final String GET_PROFILE = "rhconecta/api/v2/perfilColaboradorConsulta";
    public static final String GET_LETTERS_VALIDATE_SIGNATURE = "rhconecta/api/v2/cartasValidarFirma";
    public static final String GET_CONFIG = "rhconecta/api/v2/cartasConfig";
    public static final String GET_VOUCHER = "rhconecta/api/v2/comprobantesNomina";
    public static final String GET_LOANSAVINGFUND = "rhconecta/api/v2/fondoAhorroConsulta";
    public static final String GET_RECOVERY_PASSWORD = "rhconecta/api/v2/recuperacontra";*/

    public static final String SUCCESS = "SUCCESS";

    public static final int PETITION_PAYROLL_VOUCHER_LIST = 1;
    public static final int CONSTANCE_TYPE_PAYROLL = 1;
    public static final int CONSTANCE_TYPE_SAVING_FUND = 2;
    public static final int CONSTANCE_TYPE_GAS = 3;
    public static final int CONSTANCE_TYPE_PAYROLL_PTU = 4;
    public static final int CONSTANCE_TYPE_ALIMONY = 5;
    public static final int CONSTANCE_TYPE_BONUS = 6;

    public static final int SHIPPING_OPTION_GET = 0;
    public static final int SHIPPING_OPTION_DOWNLOAD_PDF = 1;
    public static final int SHIPPING_OPTION_SEND_EMAIL = 2;
}
