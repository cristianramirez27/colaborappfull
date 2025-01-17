package com.coppel.rhconecta.dev.business.utils;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_BENEFICIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_BENEFIT_CODE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_BONUS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_CARTASCONFIG;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_CARTASGENERAR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_COLLAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_COMUNICADOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_EXPENSES_TRAVEL;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_GAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_HOLIDAY_BONUS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_HOME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_ADDITIONALS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_LOGIN;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_MYMOVEMENTS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_NOMINA;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PASSWORD_RECOVER;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PAYSHEET;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PENSION;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PREVIEW;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_PROFILE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_QR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_SAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_SAVING_FUND;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_SECTION_TIME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_STAYHOME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_TRAVEL_EXPENSES_COLABORATOR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_TRAVEL_EXPENSES_GTE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_UTILITIES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_VACANCIES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_VALIDARFIRMA;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_VISIONARIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_VOUCHERS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_WITHDRAWSAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_LOGIN_APPS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.URL_MAIN;

public class ServicesConstants {

    public static final String REGEX_EMAIL = "[a-zA-Z0-9._+-]+@[a-zA-Z]+\\.+[a-z]+";
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    /**
     * TESTING ENVIROMENT
     */
    public static final String AUTHORIZATION = "https://apisp.coppel.com:9000/";

    public static final String GET_PROFILE_LOCAL = "v1/visionarios/perfil";
    public static final String GET_HOME_LOCAL = "v1/visionarios/home";
    public static final String GET_COMUNICADOS_LOCAL = "v1/visionarios/comunicados";
    public static final String GET_SAVINGS_LOCAL = "v1/fondo/consultasyretiros";
    public static final String GET_VOUCHERS_LOCAL = "v1/comprobantes/iconos";
    public static final String GET_NOMINA_LOCAL = "v1/comprobantes/nomina";
    public static final String GET_VOUCHER_SAVINGFUND_LOCAL = "v1/comprobantes/fondoAhorro";
    public static final String GET_MYMOVEMENTS_LOCAL = "v1/fondo/movimientos";
    public static final String GET_LETTER_LOCAL = "v1/cartaslaborales/consultas";
    public static final String GET_BONUS_LOCAL = "v1/comprobantes/aguinaldo";
    public static final String GET_VIDEOS_LOCAL = "v1/visionarios/videos";
    public static final String GET_STAYHOME_LOCAL = "v1/visionarios/stayhome";
    public static final String GET_BENEFITS_LOCAL = "v1/beneficios/consulta";
    public static final String GET_HOLIDAYS_LOCAL = "v1/vacaciones/consulta";
    public static final String GET_HOLIDAY_BONUS_LOCAL = "v1/primavacacional/consulta";
    public static final String GET_TRAVEL_EXPENSES_COLABORATOR_LOCAL = "v1/gastosdeviaje/colaborador";
    public static final String GET_TRAVEL_EXPENSES_GTE_LOCAL = "v1/gastosdeviaje/gerente";


    public static String URL_BASE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), URL_MAIN);
    public static String GET_LOGIN = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_LOGIN);
    public static String GET_PROFILE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_PROFILE);
    public static String GET_HOME = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_HOME);
    public static String GET_HELP_DESK_SERVICE_AVAILABILITY = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_COLLAGE);
    public static String GET_COMUNICADOS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_COMUNICADOS);
    public static String GET_VISIONARIOS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_VISIONARIOS);
    public static String GET_VISIONARIOS_STAY_HOME = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_STAYHOME);
    public static String GET_ENCUESTAS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_ENCUESTAS);
    public static String GET_LETTERS_VALIDATE_SIGNATURE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_VALIDARFIRMA);
    public static String GET_CONFIG = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_CARTASCONFIG);
    public static String GET_LETTER_PREVIEW = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_PREVIEW);
    public static String GET_LETTER_GENERATE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_CARTASGENERAR);
    public static String GET_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_PAYSHEET);
    public static String GET_LOANSAVINGFUND = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_SAVINGS);
    public static String GET_RECOVERY_PASSWORD = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_PASSWORD_RECOVER);
    public static String GET_BENEFITS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_BENEFICIOS);
    public static String GET_WITHDRAWSAVINGS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_WITHDRAWSAVINGS);
    public static String WS_LOGOUT = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_PROFILE);

    public static String GET_HUELLAS_ADICIONALES = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_ADDITIONALS);

    public static String GET_EXPENSES_TRAVEL = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_EXPENSES_TRAVEL);
    public static String GET_EXPENSES_TRAVEL_COLABORATOR = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_TRAVEL_EXPENSES_COLABORATOR);
    public static String GET_EXPENSES_TRAVEL_GTE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_TRAVEL_EXPENSES_GTE);
    public static String GET_ENDPOINT_HOLIDAYS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_HOLIDAYS);
    public static String GET_ENDPOINT_HOLIDAY_BONUS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_HOLIDAY_BONUS);
    public static String GET_ENDPOINT_COLLAGES = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_COLLAGE);
    public static String GET_ENDPOINT_VACANCIES = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_VACANCIES);
    public static String GET_ENDPOINT_BENEFIT_CODE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_BENEFIT_CODE);
    public static String GET_ENDPOINT_QR = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_QR);
    public static String GET_ENDPOINT_SECTION_TIME = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_SECTION_TIME);
    public static String GET_ENDPOINT_LOGIN_APPS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_LOGIN_APPS);
    // AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_BENEFICIOS,beneficios);
    public static String GET_MY_MOVEMENTS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_MYMOVEMENTS);

    //Comprobantes
    public static String GET_PAYROLL_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_NOMINA);
    public static String GET_SAVING_FUND_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_SAVING_FUND);
    public static String GET_GAS_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_GAS);
    public static String GET_PENSION_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_PENSION);
    public static String GET_UTILITIES_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_UTILITIES);
    public static String GET_BONUS_VOUCHER = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_BONUS);
    public static String GET_VOUCHERS = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), ENDPOINT_VOUCHERS);


    /**
     * PROD ENVIROMENT
     */
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
    public static final int PETITION_PAYROLL_VOUCHER_SELECTED = 3;

    public static final int PETITION_PAYROLL_VOUCHER_LIST_V2 = 4;

    public static final int CONSTANCE_TYPE_PAYROLL = 1;
    public static final int CONSTANCE_TYPE_SAVING_FUND = 2;
    public static final int CONSTANCE_TYPE_GAS = 3;
    public static final int CONSTANCE_TYPE_PAYROLL_PTU = 4;
    public static final int CONSTANCE_TYPE_ALIMONY = 5;
    public static final int CONSTANCE_TYPE_BONUS = 6;

    public static final int SHIPPING_OPTION_GET = 0;
    public static final int SHIPPING_OPTION_DOWNLOAD_PDF = 1;
    public static final int SHIPPING_OPTION_SEND_EMAIL = 2;
    public static final int TIME_OUT = 60;
}