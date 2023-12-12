package com.coppel.rhconecta.dev.business.Configuration;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


/**
 * Created on 23/08/2016.
 *
 * @author fausto@kokonutstudio
 * @version 1.0
 * <p>
 * Clase para definir las constantes utilizadas en la aplicación.
 */
public class AppConfig {

    public final static int ANDROID_OS = 1;

    public final static String ENDPOINT_WITHDRAWSAVINGS = "ENDPOINT_WITHDRAWSAVINGS";
    public final static String ENDPOINT_BENEFICIOS = "ENDPOINT_BENEFICIOS";
    public final static String ENDPOINT_VALIDARFIRMA = "ENDPOINT_VALIDARFIRMA";
    public final static String ENDPOINT_PREVIEW = "ENDPOINT_PREVIEW";
    public final static String ENDPOINT_CARTASGENERAR = "ENDPOINT_CARTASGENERAR";
    public final static String ENDPOINT_CARTASCONFIG = "ENDPOINT_CARTASCONFIG";
    public final static String ENDPOINT_PAYSHEET = "ENDPOINT_PAYSHEET";
    public final static String ENDPOINT_SAVINGS = "ENDPOINT_SAVINGS";
    public final static String ENDPOINT_PROFILE = "ENDPOINT_PROFILE";
    public final static String ENDPOINT_HOME = "ENDPOINT_HOME";
    public final static String ENDPOINT_COMUNICADOS = "ENDPOINT_COMUNICADOS";
    public final static String ENDPOINT_VISIONARIOS = "ENDPOINT_VISIONARIOS";
    public final static String ENDPOINT_STAYHOME = "ENDPOINT_STAYHOME";
    public final static String ENDPOINT_ENCUESTAS = "ENDPOINT_ENCUESTAS";
    public final static String ENDPOINT_PASSWORD_RECOVER = "ENDPOINT_PASSWORD_RECOVER";
    public final static String ENDPOINT_LOGIN = "ENDPOINT_LOGIN";
    public final static String URL_MAIN_LOGIN = "URL_MAIN_LOGIN";
    public final static String URL_MAIN = "URL_MAIN";
    public final static String URL_COVID_SURVEY = "URL_COVID_SURVEY";

    public final static String ENDPOINT_EXPENSES_TRAVEL = "ENDPOINT_TRAVEL_EXPENSES";

    public final static String ENDPOINT_HOLIDAYS = "ENDPOINT_HOLIDAYS";
    public final static String ENDPOINT_HOLIDAY_BONUS = "ENDPOINT_HOLIDAY_BONUS";
    public final static String ENDPOINT_COLLAGE = "ENDPOINT_COLLAGE";
    public final static String ENDPOINT_QR = "ENDPOINT_QR";
    public final static String ENDPOINT_BENEFIT_CODE = "ENDPOINT_BENEFIT_CODE";
    public final static String ENDPOINT_SECTION_TIME = "ENDPOINT_SECTION_TIME";
    public final static String ENDPOINT_LOGIN_APPS = "ENDPOINT_LOGIN_APPS";
    public final static String ENDPOINT_ADDITIONALS = "ENDPOINT__ADDITIONALS";

    //VISIONARIOS
    public final static String VISIONARIOS_URL = "VISIONARIOS_URL";
    public final static String APLICACION_KEY = "APLICACION_KEY";


    public final static int CLAVE_LETTER_MAX = 4;

    /*Bloqueo de módulos*/
    public final static String BLOCK_SAVINGS = "BLOCK_SAVINGS";
    public final static String BLOCK_PAYSHEET = "BLOCK_PAYSHEET";
    public final static String BLOCK_CARTASCONFIG = "BLOCK_CARTASCONFIG";
    public final static String BLOCK_BENEFICIOS = "BLOCK_BENEFICIOS";
    public final static String BLOCK_TRAVEL_EXPENSES = "BLOCK_TRAVEL_EXPENSES";
    public final static String BLOCK_HOLIDAYS = "BLOCK_HOLIDAYS";
    public final static String BLOCK_PROFILE = "BLOCK_PROFILE";
    public final static String BLOCK_VISIONARIOS = "BLOCK_VISIONARIOS";
    public final static String BLOCK_STAYHOME = "BLOCK_STAYHOME";
    public final static String BLOCK_COMUNICADOS = "BLOCK_COMUNICADOS";
    public final static String BLOCK_ENCUESTAS = "BLOCK_ENCUESTAS";
    public final static String BLOCK_QR = "BLOCK_QR";
    public final static String BLOCK_COVID_SURVEY = "BLOCK_COVID_SURVEY";
    public final static String MESSAGE_FOR_BLOCK = "MESSAGE_FOR_BLOCK";
    public final static String BLOCK_COCREA = "BLOCK_COCREA";
    public final static String BLOCK_WHEATHER = "BLOCK_WHEATER";
    public final static String BLOCK_FUND_WITHDRAW = "BLOCK_FUND_WITHDRAW";
    public final static String BLOCK_FUND_PAY = "BLOCK_FUND_PAY";
    public final static String BLOCK_FUND_ADDITIONALS = "BLOCK_FUND_ADDITIONALS";
    public final static String BLOCK_LETTER_PROOF = "BLOCK_LETTER_PROOF";
    public final static String BLOCK_LETTER_VISA = "BLOCK_LETTER_VISA";
    public final static String BLOCK_LETTER_CREDIT = "BLOCK_LETTER_CREDIT";
    public final static String BLOCK_LETTER_INFONAVIT = "BLOCK_LETTER_INFONAVIT";
    public final static String BLOCK_LETTER_IMSS = "BLOCK_LETTER_IMSS";
    public final static String BLOCK_LETTER_KINDER = "BLOCK_LETTER_KINDER";
    public final static String BLOCK_VOUCHER_PAYROLL = "BLOCK_VOUCHER_PAYROLL";
    public final static String BLOCK_VOUCHER_BONUS = "BLOCK_VOUCHER_BONUS";
    public final static String BLOCK_VOUCHER_GAS = "BLOCK_VOUCHER_GAS";
    public final static String BLOCK_VOUCHER_PTU = "BLOCK_VOUCHER_PTU";
    public final static String BLOCK_VOUCHER_ALLOWANCE = "BLOCK_VOUCHER_ALLOWANCE";
    public final static String BLOCK_VOUCHER_FUND = "BLOCK_VOUCHER_FUND";
    public final static String BLOCK_TRAVEL_MANAGER_AUTHORIZE = "BLOCK_TRAVEL_MANAGER_AUTHORIZE";
    public final static String BLOCK_TRAVEL_MANAGER_CONTROLS = "BLOCK_TRAVEL_MANAGER_CONTROLS";
    public final static String BLOCK_HOLIDAYS_MANAGER_REQUEST = "BLOCK_HOLIDAYS_MANAGER_REQUEST";
    public final static String BLOCK_HOLIDAYS_MANAGER_CALENDAR = "BLOCK_HOLIDAYS_MANAGER_CALENDAR";
    public final static String BLOCK_HOLIDAYS_MANAGER_ADDITIONAL = "BLOCK_HOLIDAYS_MANAGER_ADDITIONAL";
    public final static String ENDPOINT_VACANCIES = "ENDPOINT_VACANT";
    public final static String BLOCK_VACANCIES = "BLOCK_VACANT";
    public final static String BLOCK_VACANCIES_MESSAGE = "BLOCK_VACANT_MESSAGE";
    public final static String BLOCK_MY_MOVEMENTS = "BLOCK_MYMOVEMENTS";
    public final static String BLOCK_MYMOVEMENTS_MESSAGE = "BLOCK_MYMOVEMENTS_MESSAGE";

    public final static String YES = "YES";
    public final static String NO = "NO";

    public final static String BLOCK_MESSAGE_PROFILE = "BLOCK_MESSAGE_PROFILE";
    public final static String BLOCK_MESSAGE_SAVINGS = "BLOCK_MESSAGE_SAVINGS";
    public final static String BLOCK_MESSAGE_PAYSHEET = "BLOCK_MESSAGE_PAYSHEET";
    public final static String BLOCK_MESSAGE_CARTASCONFIG = "BLOCK_MESSAGE_CARTASCONFIG";
    public final static String BLOCK_MESSAGE_BENEFICIOS = "BLOCK_MESSAGE_BENEFICIOS";
    public final static String BLOCK_MESSAGE_TRAVEL_EXPENSES = "BLOCK_MESSAGE_TRAVEL_EXPENSES";
    public final static String BLOCK_MESSAGE_HOLIDAYS = "BLOCK_MESSAGE_HOLIDAYS";
    public final static String BLOCK_MESSAGE_VISIONARIOS = "BLOCK_MESSAGE_VISIONARIOS";
    public final static String BLOCK_MESSAGE_COMUNICADOS = "BLOCK_MESSAGE_COMUNICADOS";
    public final static String BLOCK_MESSAGE_ENCUESTAS = "BLOCK_MESSAGE_ENCUESTAS";
    public final static String BLOCK_MESSAGE_COLLAGE = "BLOCK_MESSAGE_COLLAGE";
    public final static String BLOCK_MESSAGE_CALCULATOR = "BLOCK_MESSAGE_CALCULATOR";
    public final static String BLOCK_MESSAGE_QR = "BLOCK_MESSAGE_QR";
    public final static String BLOCK_MESSAGE_STAYHOME = "BLOCK_MESSAGE_STAYHOME";
    public final static String BLOCK_MESSAGE_COVID_SURVEY = "BLOCK_MESSAGE_COVID_SURVEY";
    public final static String BLOCK_FUND_WITHDRAW_MESSAGE = "BLOCK_FUND_WITHDRAW_MESSAGE";
    public final static String BLOCK_FUND_PAY_MESSAGE = "BLOCK_FUND_PAY_MESSAGE";
    public final static String BLOCK_FUND_ADDITIONALS_MESSAGE = "BLOCK_FUND_ADDITIONALS_MESSAGE";
    public final static String BLOCK_LETTER_PROOF_MEESAGE = "BLOCK_LETTER_PROOF_MEESAGE";
    public final static String BLOCK_LETTER_VISA_MEESAGE = "BLOCK_LETTER_VISA_MEESAGE";
    public final static String BLOCK_LETTER_CREDIT_MESSAGE = "BLOCK_LETTER_CREDIT_MESSAGE";
    public final static String BLOCK_LETTER_INFONAVIT_MESSAGE = "BLOCK_LETTER_INFONAVIT_MESSAGE";
    public final static String BLOCK_LETTER_IMSS_MESSAGE = "BLOCK_LETTER_IMSS_MESSAGE";
    public final static String BLOCK_LETTER_KINDER_MESSAGE = "BLOCK_LETTER_KINDER_MESSAGE";
    public final static String BLOCK_VOUCHER_PAYROLL_MESSAGE = "BLOCK_VOUCHER_PAYROLL_MESSAGE";
    public final static String BLOCK_VOUCHER_BONUS_MESSAGE = "BLOCK_VOUCHER_BONUS_MESSAGE";
    public final static String BLOCK_VOUCHER_GAS_MESSAGE = "BLOCK_VOUCHER_GAS_MESSAGE";
    public final static String BLOCK_VOUCHER_PTU_MESSAGE = "BLOCK_VOUCHER_PTU_MESSAGE";
    public final static String BLOCK_VOUCHER_ALLOWANCE_MESSAGE = "BLOCK_VOUCHER_ALLOWANCE_MESSAGE";
    public final static String BLOCK_VOUCHER_FUND_MESSAGE = "BLOCK_VOUCHER_FUND_MESSAGE";
    public final static String BLOCK_TRAVEL_MANAGER_AUTHORIZE_MESSAGE = "BLOCK_TRAVEL_MANAGER_AUTHORIZE_MESSAGE";
    public final static String BLOCK_TRAVEL_MANAGER_CONTROLS_MESSAGE = "BLOCK_TRAVEL_MANAGER_CONTROLS_MESSAGE";
    public final static String BLOCK_HOLIDAYS_MANAGER_REQUEST_MESSAGE = "BLOCK_HOLIDAYS_MANAGER_REQUEST_MESSAGE";
    public final static String BLOCK_HOLIDAYS_MANAGER_CALENDAR_MESSAGE = "BLOCK_HOLIDAYS_MANAGER_CALENDAR_MESSAGE";
    public final static String BLOCK_HOLIDAYS_MANAGER_ADDITIONAL_MESSAGE = "BLOCK_HOLIDAYS_MANAGER_ADDITIONAL_MESSAGE";

    public final static String TITLE_COCREA = "TITLE_COCREA";
    public final static String ENDPOINT_COCREA = "URL_COCREA";
    public final static String ENDPOINT_COCREA_STORE = "URL_COCREA_STORE";

    public final static String BLOCK_COLLAGE = "BLOCK_COLLAGE";
    public final static String BLOCK_CALCULATOR = "BLOCK_CALCULATOR";

    public final static String TITLE_WHEATHER = "TITLE_WHEATHER";
    public final static String ENDPOINT_WHEATHER = "END_POINT_WEATHER";
    public final static String ENDPOINT_MYMOVEMENTS = "ENDPOINT_MYMOVEMENTS";
    public final static String ENDPOINT_LINEA_DE_DENUNCIA= "URL_LINEA_DE_DENUNCIA";
    public final static String BLOCK_LINEA_DE_DENUNCIA = "BLOCK_LINEA_DE_DENUNCIA";

    public final static String ENDPOINT_CALCULATOR = "ENDPOINT_CALCULATOR";
    public final static String ENDPOINT_CALCULATOR_WEEKS = "ENDPOINT_CALCULATOR_WEEKS";
    public final static String ENDPOINT_CALCULATOR_STEP = "ENDPOINT_CALCULATOR_STEP";
    public final static String ENDPOINT_CALCULATOR_RATING = "ENDPOINT_CALCULATOR_RATING";
    public final static String ENDPOINT_CALCULATOR_ESTIMATE = "ENDPOINT_CALCULATOR_ESTIMATE";

    /**
     * Se almacenan los endpoints
     */
    public static void setEndpointConfig(FirebaseRemoteConfig mFirebaseRemoteConfig) {


        String fondoConsulta = mFirebaseRemoteConfig.getString(ENDPOINT_WITHDRAWSAVINGS);
        String beneficios = mFirebaseRemoteConfig.getString(ENDPOINT_BENEFICIOS);
        String validarFirma = mFirebaseRemoteConfig.getString(ENDPOINT_VALIDARFIRMA);
        String preview = mFirebaseRemoteConfig.getString(ENDPOINT_PREVIEW);
        String cartasgenerar = mFirebaseRemoteConfig.getString(ENDPOINT_CARTASGENERAR);
        String cartasconfig = mFirebaseRemoteConfig.getString(ENDPOINT_CARTASCONFIG);
        String paysheet = mFirebaseRemoteConfig.getString(ENDPOINT_PAYSHEET);
        String savings = mFirebaseRemoteConfig.getString(ENDPOINT_SAVINGS);
        String qr = mFirebaseRemoteConfig.getString(ENDPOINT_QR);
        String benefitCode = mFirebaseRemoteConfig.getString(ENDPOINT_BENEFIT_CODE);
        String loginApps = mFirebaseRemoteConfig.getString(ENDPOINT_LOGIN_APPS);
        String sectionTime = mFirebaseRemoteConfig.getString(ENDPOINT_SECTION_TIME);
        String profile = mFirebaseRemoteConfig.getString(ENDPOINT_PROFILE);
        String home = mFirebaseRemoteConfig.getString(ENDPOINT_HOME);
        String comunicados = mFirebaseRemoteConfig.getString(ENDPOINT_COMUNICADOS);
        String visionarios = mFirebaseRemoteConfig.getString(ENDPOINT_VISIONARIOS);
        String stayHome = mFirebaseRemoteConfig.getString(ENDPOINT_STAYHOME);
        String encuestas = mFirebaseRemoteConfig.getString(ENDPOINT_ENCUESTAS);
        String passrecover = mFirebaseRemoteConfig.getString(ENDPOINT_PASSWORD_RECOVER);
        String login = mFirebaseRemoteConfig.getString(ENDPOINT_LOGIN);
        String main_login = mFirebaseRemoteConfig.getString(URL_MAIN_LOGIN);
        String url_main = mFirebaseRemoteConfig.getString(URL_MAIN);
        String url_wheather = mFirebaseRemoteConfig.getString(ENDPOINT_WHEATHER);
        String myMovementsEndPoint = mFirebaseRemoteConfig.getString(ENDPOINT_MYMOVEMENTS);
        String calculator_endpoint = mFirebaseRemoteConfig.getString(ENDPOINT_CALCULATOR);
        String calculator_endpoint_week = mFirebaseRemoteConfig.getString(ENDPOINT_CALCULATOR_WEEKS);
        String calculator_endpoint_step = mFirebaseRemoteConfig.getString(ENDPOINT_CALCULATOR_STEP);
        String calculator_endpoint_rating = mFirebaseRemoteConfig.getString(ENDPOINT_CALCULATOR_RATING);
        String calculator_endpoint_estimate = mFirebaseRemoteConfig.getString(ENDPOINT_CALCULATOR_ESTIMATE);

        String huellasAdicionales = mFirebaseRemoteConfig.getString(ENDPOINT_ADDITIONALS);
        String vacanciesEndPoint = mFirebaseRemoteConfig.getString(ENDPOINT_VACANCIES);

        String url_coCrea = mFirebaseRemoteConfig.getString(ENDPOINT_COCREA);
        String url_coCreaSite = mFirebaseRemoteConfig.getString(ENDPOINT_COCREA_STORE);

        String expenses_travel = mFirebaseRemoteConfig.getString(ENDPOINT_EXPENSES_TRAVEL);
        String holidays = mFirebaseRemoteConfig.getString(ENDPOINT_HOLIDAYS);
        String holidayBonus = mFirebaseRemoteConfig.getString(ENDPOINT_HOLIDAY_BONUS);
        String collage = mFirebaseRemoteConfig.getString(ENDPOINT_COLLAGE);

        String url_lineaDenuncia = mFirebaseRemoteConfig.getString(ENDPOINT_LINEA_DE_DENUNCIA);


        //VISIONARIOS
        String visionarios_url = mFirebaseRemoteConfig.getString(VISIONARIOS_URL);
        String aplicacion_key = mFirebaseRemoteConfig.getString(APLICACION_KEY);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_WITHDRAWSAVINGS, fondoConsulta);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_BENEFICIOS, beneficios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_VALIDARFIRMA, validarFirma);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PREVIEW, preview);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CARTASGENERAR, cartasgenerar);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CARTASCONFIG, cartasconfig);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PAYSHEET, paysheet);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_SAVINGS, savings);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PROFILE, profile);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_HOME, home);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_COMUNICADOS, comunicados);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_VISIONARIOS, visionarios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_STAYHOME, stayHome);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_ENCUESTAS, encuestas);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PASSWORD_RECOVER, passrecover);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_LOGIN, login);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), URL_MAIN_LOGIN, main_login);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), URL_MAIN, url_main);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_EXPENSES_TRAVEL, expenses_travel);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_HOLIDAYS, holidays);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_HOLIDAY_BONUS, holidayBonus);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_QR, qr);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_BENEFIT_CODE,benefitCode);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_LOGIN_APPS,loginApps);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_SECTION_TIME, sectionTime);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_COLLAGE, collage);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_COCREA, url_coCrea);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_COCREA_STORE, url_coCreaSite);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_ADDITIONALS, huellasAdicionales);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_WHEATHER, url_wheather);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_VACANCIES, vacanciesEndPoint);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_MYMOVEMENTS, myMovementsEndPoint);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CALCULATOR, calculator_endpoint);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CALCULATOR_WEEKS, calculator_endpoint_week);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CALCULATOR_STEP, calculator_endpoint_step);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CALCULATOR_RATING, calculator_endpoint_rating);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CALCULATOR_ESTIMATE, calculator_endpoint_estimate);
        //VISIONARIOS
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), VISIONARIOS_URL, visionarios_url);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), APLICACION_KEY, aplicacion_key);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_LINEA_DE_DENUNCIA, url_lineaDenuncia);

        /*Bloquear modulos*/
        String block_saving = mFirebaseRemoteConfig.getString(BLOCK_SAVINGS);
        String block_paysheet = mFirebaseRemoteConfig.getString(BLOCK_PAYSHEET);
        String block_letters = mFirebaseRemoteConfig.getString(BLOCK_CARTASCONFIG);
        String block_benefits = mFirebaseRemoteConfig.getString(BLOCK_BENEFICIOS);
        String block_travel = mFirebaseRemoteConfig.getString(BLOCK_TRAVEL_EXPENSES);
        String block_holiday = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS);
        String block_profile = mFirebaseRemoteConfig.getString(BLOCK_PROFILE);
        String block_visionarios = mFirebaseRemoteConfig.getString(BLOCK_VISIONARIOS);
        String block_stayhome = mFirebaseRemoteConfig.getString(BLOCK_STAYHOME);
        String block_communication = mFirebaseRemoteConfig.getString(BLOCK_COMUNICADOS);
        String block_surveys = mFirebaseRemoteConfig.getString(BLOCK_ENCUESTAS);
        String block_message = mFirebaseRemoteConfig.getString(MESSAGE_FOR_BLOCK);
        String block_collage = mFirebaseRemoteConfig.getString(BLOCK_COLLAGE);
        String block_qr = mFirebaseRemoteConfig.getString(BLOCK_QR);
        String block_coCrea = mFirebaseRemoteConfig.getString(BLOCK_COCREA);
        String block_title_coCrea = mFirebaseRemoteConfig.getString(TITLE_COCREA);
        String block_wheather = mFirebaseRemoteConfig.getString(BLOCK_WHEATHER);
        String block_covid_survey = mFirebaseRemoteConfig.getString(BLOCK_COVID_SURVEY);
        String block_title_wheather = mFirebaseRemoteConfig.getString(TITLE_WHEATHER);
        String block_fund_withdram = mFirebaseRemoteConfig.getString(BLOCK_FUND_WITHDRAW);
        String block_fund_pay = mFirebaseRemoteConfig.getString(BLOCK_FUND_PAY);
        String block_fund_additionals = mFirebaseRemoteConfig.getString(BLOCK_FUND_ADDITIONALS);
        String block_letter_proof = mFirebaseRemoteConfig.getString(BLOCK_LETTER_PROOF);
        String block_letter_visa = mFirebaseRemoteConfig.getString(BLOCK_LETTER_VISA);
        String block_letter_credit = mFirebaseRemoteConfig.getString(BLOCK_LETTER_CREDIT);
        String block_letter_infonavit = mFirebaseRemoteConfig.getString(BLOCK_LETTER_INFONAVIT);
        String block_letter_imss = mFirebaseRemoteConfig.getString(BLOCK_LETTER_IMSS);
        String block_letter_kinder = mFirebaseRemoteConfig.getString(BLOCK_LETTER_KINDER);
        String block_voucher_payroll = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_PAYROLL);
        String block_voucher_bonus = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_BONUS);
        String block_voucher_gas = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_GAS);
        String block_voucher_ptu = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_PTU);
        String block_voucher_allowance = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_ALLOWANCE);
        String block_voucher_fund = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_FUND);
        String block_travel_manager_authorize = mFirebaseRemoteConfig.getString(BLOCK_TRAVEL_MANAGER_AUTHORIZE);
        String block_travel_manager_controls = mFirebaseRemoteConfig.getString(BLOCK_TRAVEL_MANAGER_CONTROLS);
        String block_holidays_request = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS_MANAGER_REQUEST);
        String block_holidays_calendar = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS_MANAGER_CALENDAR);
        String block_holidays_additonal = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS_MANAGER_ADDITIONAL);
        String block_message_profile = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_PROFILE);
        String block_message_savings = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_SAVINGS);
        String block_message_paysheet = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_PAYSHEET);
        String block_message_letter_config = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_CARTASCONFIG);
        String block_message_benefits = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_BENEFICIOS);
        String block_message_travel_expenses = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_TRAVEL_EXPENSES);
        String block_message_holidays = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_HOLIDAYS);
        String block_message_visionarios = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_VISIONARIOS);
        String block_message_comunicados = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_COMUNICADOS);
        String block_message_survey = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_ENCUESTAS);
        String block_message_collage = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_COLLAGE);
        String block_message_qr = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_QR);
        String block_message_stayhome = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_STAYHOME);
        String block_message_covid = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_COVID_SURVEY);
        String block_fund_withdram_message = mFirebaseRemoteConfig.getString(BLOCK_FUND_WITHDRAW_MESSAGE);
        String block_fund_pay_message = mFirebaseRemoteConfig.getString(BLOCK_FUND_PAY_MESSAGE);
        String block_fund_additionals_message = mFirebaseRemoteConfig.getString(BLOCK_FUND_ADDITIONALS_MESSAGE);
        String block_letter_proof_message = mFirebaseRemoteConfig.getString(BLOCK_LETTER_PROOF_MEESAGE);
        String block_letter_visa_message = mFirebaseRemoteConfig.getString(BLOCK_LETTER_VISA_MEESAGE);
        String block_letter_credit_message = mFirebaseRemoteConfig.getString(BLOCK_LETTER_CREDIT_MESSAGE);
        String block_letter_infonavit_message = mFirebaseRemoteConfig.getString(BLOCK_LETTER_INFONAVIT_MESSAGE);
        String block_letter_imss_message = mFirebaseRemoteConfig.getString(BLOCK_LETTER_IMSS_MESSAGE);
        String block_letter_kinder_message = mFirebaseRemoteConfig.getString(BLOCK_LETTER_KINDER_MESSAGE);
        String block_voucher_payroll_message = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_PAYROLL_MESSAGE);
        String block_voucher_bonus_message = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_BONUS_MESSAGE);
        String block_voucher_gas_message = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_GAS_MESSAGE);
        String block_voucher_ptu_message = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_PTU_MESSAGE);
        String block_voucher_allowance_message = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_ALLOWANCE_MESSAGE);
        String block_voucher_fund_message = mFirebaseRemoteConfig.getString(BLOCK_VOUCHER_FUND_MESSAGE);
        String block_travel_manager_authorize_message = mFirebaseRemoteConfig.getString(BLOCK_TRAVEL_MANAGER_AUTHORIZE_MESSAGE);
        String block_travel_manager_controls_message = mFirebaseRemoteConfig.getString(BLOCK_TRAVEL_MANAGER_CONTROLS_MESSAGE);
        String block_holidays_request_message = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS_MANAGER_REQUEST_MESSAGE);
        String block_holidays_calendar_message = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS_MANAGER_CALENDAR_MESSAGE);
        String block_holidays_additonal_message = mFirebaseRemoteConfig.getString(BLOCK_HOLIDAYS_MANAGER_ADDITIONAL_MESSAGE);
        String block_vacancies = mFirebaseRemoteConfig.getString(BLOCK_VACANCIES);
        String block_vacancies_message = mFirebaseRemoteConfig.getString(BLOCK_VACANCIES_MESSAGE);
        String block_my_movements = mFirebaseRemoteConfig.getString(BLOCK_MY_MOVEMENTS);
        String block_my_movements_message = mFirebaseRemoteConfig.getString(BLOCK_MYMOVEMENTS_MESSAGE);
        String block_calculator = mFirebaseRemoteConfig.getString(BLOCK_CALCULATOR);
        String block_calculator_message = mFirebaseRemoteConfig.getString(BLOCK_MESSAGE_CALCULATOR);
        String block_lineaDenuncia = mFirebaseRemoteConfig.getString(BLOCK_LINEA_DE_DENUNCIA);


        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_PROFILE, block_message_profile);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_SAVINGS, block_message_savings);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_PAYSHEET, block_message_paysheet);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_CARTASCONFIG, block_message_letter_config);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_BENEFICIOS, block_message_benefits);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_TRAVEL_EXPENSES, block_message_travel_expenses);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_HOLIDAYS, block_message_holidays);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_VISIONARIOS, block_message_visionarios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_COMUNICADOS, block_message_comunicados);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_ENCUESTAS, block_message_survey);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_COLLAGE, block_message_collage);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_QR, block_message_qr);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_STAYHOME, block_message_stayhome);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_COVID_SURVEY, block_message_covid);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_FUND_WITHDRAW_MESSAGE, block_fund_withdram_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_FUND_PAY_MESSAGE, block_fund_pay_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_FUND_ADDITIONALS_MESSAGE, block_fund_additionals_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_PROOF_MEESAGE, block_letter_proof_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_VISA_MEESAGE, block_letter_visa_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_CREDIT_MESSAGE, block_letter_credit_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_INFONAVIT_MESSAGE, block_letter_infonavit_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_IMSS_MESSAGE, block_letter_imss_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_KINDER_MESSAGE, block_letter_kinder_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_PAYROLL_MESSAGE, block_voucher_payroll_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_BONUS_MESSAGE, block_voucher_bonus_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_GAS_MESSAGE, block_voucher_gas_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_PTU_MESSAGE, block_voucher_ptu_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_ALLOWANCE_MESSAGE, block_voucher_allowance_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_FUND_MESSAGE, block_voucher_fund_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_MANAGER_AUTHORIZE_MESSAGE, block_travel_manager_authorize_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_MANAGER_CONTROLS_MESSAGE, block_travel_manager_controls_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS_MANAGER_REQUEST_MESSAGE, block_holidays_request_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS_MANAGER_CALENDAR_MESSAGE, block_holidays_calendar_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS_MANAGER_ADDITIONAL_MESSAGE, block_holidays_additonal_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VACANCIES_MESSAGE, block_vacancies_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VACANCIES, block_vacancies);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_SAVINGS, block_saving);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_PAYSHEET, block_paysheet);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_CARTASCONFIG, block_letters);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_BENEFICIOS, block_benefits);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_EXPENSES, block_travel);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS, block_holiday);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_PROFILE, block_profile);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VISIONARIOS, block_visionarios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_STAYHOME, block_stayhome);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COMUNICADOS, block_communication);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_ENCUESTAS, block_surveys);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK, block_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COLLAGE, block_collage);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_QR, block_qr);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COVID_SURVEY, block_covid_survey);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), TITLE_COCREA, block_title_coCrea);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COCREA, block_coCrea);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), TITLE_WHEATHER, block_title_wheather);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_WHEATHER, block_wheather);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_FUND_WITHDRAW, block_fund_withdram);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_FUND_PAY, block_fund_pay);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_FUND_ADDITIONALS, block_fund_additionals);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_PROOF, block_letter_proof);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_VISA, block_letter_visa);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_CREDIT, block_letter_credit);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_INFONAVIT, block_letter_infonavit);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_IMSS, block_letter_imss);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LETTER_KINDER, block_letter_kinder);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_PAYROLL, block_voucher_payroll);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_BONUS, block_voucher_bonus);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_GAS, block_voucher_gas);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_PTU, block_voucher_ptu);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_ALLOWANCE, block_voucher_allowance);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VOUCHER_FUND, block_voucher_fund);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_MANAGER_AUTHORIZE, block_travel_manager_authorize);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_MANAGER_CONTROLS, block_travel_manager_controls);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS_MANAGER_REQUEST, block_holidays_request);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS_MANAGER_CALENDAR, block_holidays_calendar);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS_MANAGER_ADDITIONAL, block_holidays_additonal);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MY_MOVEMENTS, block_my_movements);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MYMOVEMENTS_MESSAGE, block_my_movements_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_CALCULATOR, block_calculator);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_MESSAGE_CALCULATOR, block_calculator_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_LINEA_DE_DENUNCIA, block_lineaDenuncia);

    }

   /* public static void setConfigurationDefault(){
        Preferencias prefs = MegaDesignApp.getInstance().getPrefs();
        prefs.saveData(WELCOME_ENABLED,true);
        prefs.saveData(TUTORIAL_ENABLED,true);
        prefs.saveData(MAINTENANCE_ENABLED,false);
        prefs.saveData(START_FACEBOOK,true);
        prefs.saveData(START_TWITTER,true);
        prefs.saveData(START_GOOGLE,true);
        prefs.saveData(NOTICE_PRIVACY,true);
        prefs.saveData(PROFILE_ENABLED,true);
        prefs.saveData(CONTACT_ENABLED,true);
        prefs.saveData(FAQ_ENABLED,true);
        prefs.saveData(CONTENT_ENABLED,true);
        prefs.saveData(DASHBOARD_BANNER_ENABLED,true);
        prefs.saveData(DASHBOARD_MESSAGE,true);
        prefs.saveData(DASHBOARD_CONTENT,true);
    }*/


    /**
     * PushSnackBar Configuration
     */
    public static PushSnackBarConfig getPushSnackBar(Context context) {
        PushSnackBarConfig pushSnackBarConfig = new PushSnackBarConfig(R.layout.push_snackbar_layout);
        //  pushSnackBarConfig.setView(new AVLoadingIndicatorView(context));
        pushSnackBarConfig.setName("PushSnackBar");
        pushSnackBarConfig.setColorText(CoppelApp.getContext().getResources().getColor(R.color.colorBackgroundCoppelBlanco));
        pushSnackBarConfig.setColor(CoppelApp.getContext().getResources().getColor(R.color.colorRed));
        return pushSnackBarConfig;
    }

    public static String getVersionApp() {
        PackageManager manager = CoppelApp.getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(CoppelApp.getContext().getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
