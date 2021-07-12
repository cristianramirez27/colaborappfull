package com.coppel.rhconecta.dev.views.utils;

import com.coppel.rhconecta.dev.BuildConfig;

public class AppConstants {

    public static final String URL_COPPEL = "https://www.coppel.com/";

    public static final String SHARED_PREFERENCES_NAME = "COPPEL_PREFERENCES";
    public static final String SHARED_PREFERENCES_IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String SHARED_PREFERENCES_EMAIL = "EMAIL";
    public static final String SHARED_PREFERENCES_PASS = "PASSWORD";
    public static final String SHARED_PREFERENCES_TOKEN = "TOKEN";

    public static final String SHARED_PREFERENCES_TOKEN_USER = "TOKEN_USER";
    public static final String SHARED_PREFERENCES_NUM_COLABORADOR = "NUM_COLABORADOR";
    public static final String SHARED_PREFERENCES_STATE_COLABORADOR = "STATE_COLABORADOR ";
    public static final String SHARED_PREFERENCES_CITY_COLABORADOR = "CITY_COLABORADOR";
    public static final String SHARED_PREFERENCES_FIREBASE_TOKEN = "FIREBASE_TOKEN";

    public static final String SHARED_PREFERENCES_IS_GTE = "IS_GTE";
    public static final String SHARED_PREFERENCES_IS_SUPLENTE = "IS_SUPLENTE";
    public static final String SHARED_PREFERENCES_NUM_GTE = "NUM_GTE";
    public static final String SHARED_PREFERENCES_NUM_SUPLENTE = "NUM_SUPLENTE";

    public static final String SHARED_PREFERENCES_LOGIN_RESPONSE = "LOGIN_RESPONSE";
    public static final String SHARED_PREFERENCES_PROFILE_RESPONSE = "PROFILE_RESPONSE";

    public static final String APP_FOLDER = "ColaborApp";
    public static final String FILEPROVIDER = BuildConfig.APPLICATION_ID + ".fileprovider";
    public static final String CONTENT_TYPE_PDF = "application/pdf";

    public static final String OPTION_HOME = "HOME";
    public static final String OPTION_NOTICE = "NOTICE";
    public static final String OPTION_PAYROLL_VOUCHER = "PAYROLL_VOUCHER";
    public static final String OPTION_BENEFITS = "BENEFIT";
    public static final String OPTION_SAVING_FUND = "SAVING_FUND";
    public static final String OPTION_VISIONARIES = "VISIONARIES";
    public static final String OPTION_COLLABORATOR_AT_HOME = "COLLABORATOR_AT_HOME";
    public static final String OPTION_LETTERS = "LETTERS";
    public static final String OPTION_EXPENSES = "EXPENSE";
    public static final String OPTION_POLL = "POLL";
    public static final String OPTION_HOLIDAYS = "HOLIDAYS";
    public static final String OPTION_QR_CODE = "QR_CODE";
    public static final String OPTION_COLLAGE = "COLLAGE";
    public static final String OPTION_COVID_SURVEY = "COVID_SURVEY";
    public static final String OPTION_COCREA = "COCREA";
    public static final String URL_DEFAULT_COCREA  = "https://cocrea.coppel.com:58443/#/login";


    public static final String OPTION_NOTIFICATION_EXPENSES_AUTHORIZE = "_NOTIFICATION_EXPENSES_AUTHORIZE";

    public static final String OPTION_BONUS = "BONUS";
    public static final String OPTION_GAS = "GAS";
    public static final String OPTION_PTU = "PTU";
    public static final String OPTION_ALIMONY = "ALIMONY";

    /*Employment Letters*/
    public static final String OPTION_WORK_RECORD = "WORK_RECORD";
    public static final String OPTION_VISA_PASSPORT = "VISA_PASSPORT";
    public static final String OPTION_BANK_CREDIT = "BANK_CREDIT";
    public static final String OPTION_IMSS= "IMSS";
    public static final String OPTION_INFONAVIT= "INFONAVIT";
    public static final String OPTION_KINDERGARTEN = "KINDERGARTEN";



    /*Menus Iconos*/
    public static final String OPTION_MENU_COLABORATOR = "COLABORATOR";
    public static final String OPTION_MENU_GTE = "GTE";
    public static final String OPTION_HOLIDAY_REQUESTS = "HOLIDAY_REQUESTS";
    public static final String OPTION_CALENDAR_GRAL = "CALENDAR_GRAL";
    public static final String OPTION_ADITIONAL_DAYS = "ADITIONAL_DAYS";

    public static final String OPTION_AUTHORIZE_REQUESTS = "AUTHORIZE_REQUESTS";
    public static final String OPTION_CONTROLS_LIQ = "CONTROLS_LIQ";
    public static final String OPTION_REMOVE = "REMOVE";
    public static final String OPTION_ADITIONAL_SAVED = "ADITIONAL_SAVED";
    public static final String OPTION_PAY = "PAY";


    public static final int TYPE_WORK_RECORD = 1;
    public static final int TYPE_VISA_PASSPORT = 2;
    public static final int TYPE_BANK_CREDIT = 3;
    public static final int TYPE_INFONAVIT=4;
    public static final int TYPE_IMSS= 5;
    public static final int TYPE_KINDERGARTEN = 6;

    public static final String DATE_FORMAT_YYYY_MM_DD_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DD_MM_YYYY_SLASH = "dd/MM/yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD_MIDDLE = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DD_MM_YYYY_MIDDLE = "dd-MM-yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String BUNDLE_GOTO_SECTION = "GOTO_SECTION";
    public static final String BUNDLE_LOGIN_RESPONSE = "LOGIN_RESPONSE";
    public static final String BUNLDE_PROFILE_RESPONSE = "PROFILE_RESPONSE";
    public static final String BUNDLE_PAYROLL_DATES = "PAYROLL_DATES";
    public static final String BUNDLE_SAVING_FUND_DATES = "SAVING_FUND_DATES";
    public static final String BUNDLE_GAS_DATES = "GAS_DATES";
    public static final String BUNDLE_PAYROLL_DATES_PTU = "PAYROLL_DATES_PTU";
    public static final String BUNDLE_PAYROLL_DATES_BONUS = "PAYROLL_DATES_BONUS";
    public static final String BUNDLE_PAYROLL_BENEFICIARIES_ALIMONY = "PAYROLL_BENEFICIARIES_ALIMONY";
    public static final String BUNDLE_BENEFICIARY_DATES = "BENEFICIARY_DATES";
    public static final String BUNDLE_BENEFICIARY = "BENEFICIARY";
    public static final String BUNDLE_SELECTED_GAS_DATE = "SELECTED_GAS_DATE";
    public static final String BUNDLE_SELECTED_CATEGORY_BENEFITS = "BUNDLE_SELECTED_CATEGORY_BENEFITS";
    public static final String BUNDLE_SELECTED_BENEFIT_DATA= "BUNDLE_SELECTED_BENEFIT_DATA";

    public static final String BUNDLE_LETTER = "BUNDLE_LETTER";
    public static final String BUNDLE_FIELDS_LETTER = "BUNDLE_FIELDS_LETTER";

    public static final String BUNDLE_RESPONSE_CONFIG_LETTER = "BUNDLE_RESPONSE_CONFIG_LETTER";
    public static final String BUNDLE_TYPE_SAVING_OPTION = "BUNDLE_TYPE_SAVING_OPTION";

    public static final String KEY_STAMP = "sello";
    public static final String KEY_CHILDREN_NAMES = "Nombre_hijos";
    public static final String KEY_SCHEDULE = "datoshorario";
    public static final String KEY_JOB_SCHEDULE= "horario_Laboral";
    public static final String KEY_SECTION_SCHEDULE = "horario_Seccion";
    public static final String KEY_HOLIDAY_PERIOD = "periodo_vacacional";


    public static final String BUNDLE_CLV_ABONAR = "BUNDLE_CLV_ABONAR";
    public static final String BUNDLE_DATOS_OPT_ABONAR = "BUNDLE_DATOS_OPT_ABONAR";
    public static final String BUNDLE_SAVINFOUND = "BUNDLE_SAVINFOUND";


    /*Gastos de Viaje*/
    public static final String BUNDLE_OPTION_TRAVEL_EXPENSES = "BUNDLE_OPTION_TRAVEL_EXPENSES";
    public static final String BUNDLE_OPTION_DATA_TRAVEL_EXPENSES = "BUNDLE_OPTION_DATA_TRAVEL_EXPENSES";

    public static final String OPTION_MANAGER = "OPTION_MANAGER";
    public static final String OPTION_COLABORATOR = "OPTION_COLABORATOR";

    public static final String OPTION_DETAIL_REQUETS_CONTROLS = "OPTION_DETAIL_REQUETS_CONTROLS";


    public static final String OPTION_AUTHORIZE_REQUEST = "OPTION_AUTHORIZE_REQUEST";
    public static final String OPTION_CONSULT_CONTROLS = "OPTION_CONSULT_CONTROLS";

    public static final String OPTION_MORE_DETAIL_CONTROL= "OPTION_MORE_DETAIL_CONTROL";
    public static final String OPTION_REFUSE_REQUEST= "OPTION_REFUSE_REQUEST";
    public static final String OPTION_EDIT_AMOUNTS= "OPTION_EDIT_AMOUNTS";

    public static final String OPTION_MORE_DETAIL_REQUEST= "OPTION_MORE_DETAIL_REQUEST";
    public static final String OPTION_MORE_DETAIL_COMPLEMENT= "OPTION_MORE_DETAIL_COMPLEMENT";


    public static final String BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL= "BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL";

    /*Holidays*/
    public static final String BUNDLE_OPTION_HOLIDAYS = "BUNDLE_OPTION_HOLIDAYS";
    public static final String BUNDLE_OPTION_COLABORATOR_SCHEDULE = "BUNDLE_OPTION_COLABORATOR_SCHEDULE";
    public static final String BUNDLE_OPTION_HOLIDAYREQUESTS = "BUNDLE_OPTION_HOLIDAYREQUESTS";
    public static final String BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL = "BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL";
    public static final String BUNDLE_OPTION_HOLIDAY_MENU_GTE = "BUNDLE_OPTION_HOLIDAY_MENU_GTE";
    public static final String BUNDLE_OPTION_HOLIDAY_REQUESTS = "BUNDLE_OPTION_HOLIDAY_REQUESTS";
    public static final String BUNDLE_OPTION_DATA_HOLIDAYS = "BUNDLE_OPTION_DATA_HOLIDAYS";
    public static final String BUNDLE_OPTION_HOLIDAY_COLABORATOR_REQUESTS = "BUNDLE_OPTION_HOLIDAY_COLABORATOR_REQUESTS";
    public static final String BUNDLE_OPTION_HOLIDAY_ADITIONAL_COLABORATOR_REQUESTS = "BUNDLE_OPTION_HOLIDAY_ADITIONAL_COLABORATOR_REQUESTS";
    public static final String BUNDLE_OPTION_HOLIDAY_CALENDAR_PROPOSED = "BUNDLE_OPTION_HOLIDAY_CALENDAR_PROPOSED";
    public static final String BUNDLE_OPTION_HOLIDAY_CALENDAR= "BUNDLE_OPTION_HOLIDAY_CALENDAR";

    public static final String BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR= "BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR";

    public static final String BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR= "BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR";
    public static final String BUNDLE_OPTION_HOLIDAY_CALENDAR_PERIODS= "BUNDLE_OPTION_HOLIDAY_CALENDAR_PERIODS";

    public static final String ID_SISTEMA = "id_sistema";
    public static final String ID_PANTALLA = "id_pantalla";
        public static final String NOTIFICATION_TITLE = "title";
    public static final String NOTIFICATION_BODY = "body";


    public static final int ICON_NOMINA = 1;
    public static final int ICON_FONDOAHORRO = 2;
    public static final int ICON_GASOLINA = 3;
    public static final int ICON_PTU = 4;
    public static final int ICON_PENSION = 5;
    public static final int ICON_AGUINALDO = 6;

    public static final String BUNDLE_PAYROLL_OPTION = "PAYROLL_OPTION";

}
