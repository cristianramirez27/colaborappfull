package com.coppel.rhconecta.dev.business.Configuration;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;


/**
 * Created on 23/08/2016.
 *
 * @author fausto@kokonutstudio
 * @version 1.0
 *
 * Clase para definir las constantes utilizadas en la aplicación.
 *
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
    public final static String ENDPOINT_COLLAGE = "ENDPOINT_COLLAGE";
    public final static String ENDPOINT_QR = "ENDPOINT_QR";
    public final static String ENDPOINT_BENEFIT_CODE = "ENDPOINT_BENEFIT_CODE";


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
    public final static String YES = "YES";
    public final static String NO = "NO";

    public final static String BLOCK_COLLAGE = "BLOCK_COLLAGE";
    /**Se almacenan los endpoints*/
    public static void setEndpointConfig(FirebaseRemoteConfig mFirebaseRemoteConfig){


        String fondoConsulta = mFirebaseRemoteConfig.getString(ENDPOINT_WITHDRAWSAVINGS);
        String beneficios = mFirebaseRemoteConfig.getString(ENDPOINT_BENEFICIOS);
        String validarFirma = mFirebaseRemoteConfig.getString(ENDPOINT_VALIDARFIRMA);
        String preview = mFirebaseRemoteConfig.getString(ENDPOINT_PREVIEW);
        String cartasgenerar = mFirebaseRemoteConfig.getString(ENDPOINT_CARTASGENERAR);
        String cartasconfig = mFirebaseRemoteConfig.getString(ENDPOINT_CARTASCONFIG);
        String paysheet = mFirebaseRemoteConfig.getString(ENDPOINT_PAYSHEET);
        String savings = mFirebaseRemoteConfig.getString(ENDPOINT_SAVINGS);
        String qr = mFirebaseRemoteConfig.getString(ENDPOINT_QR);
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

        String expenses_travel = mFirebaseRemoteConfig.getString(ENDPOINT_EXPENSES_TRAVEL);
        String holidays = mFirebaseRemoteConfig.getString(ENDPOINT_HOLIDAYS);
        String collage = mFirebaseRemoteConfig.getString(ENDPOINT_COLLAGE);





        //VISIONARIOS
        String visionarios_url = mFirebaseRemoteConfig.getString(VISIONARIOS_URL);
        String aplicacion_key = mFirebaseRemoteConfig.getString(APLICACION_KEY);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_WITHDRAWSAVINGS,fondoConsulta);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_BENEFICIOS,beneficios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_VALIDARFIRMA,validarFirma);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PREVIEW,preview);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CARTASGENERAR,cartasgenerar);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CARTASCONFIG,cartasconfig);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PAYSHEET,paysheet);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_SAVINGS,savings);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PROFILE,profile);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_HOME, home);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_COMUNICADOS, comunicados);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_VISIONARIOS, visionarios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_STAYHOME, stayHome);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_ENCUESTAS, encuestas);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PASSWORD_RECOVER,passrecover);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_LOGIN,login);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), URL_MAIN_LOGIN,main_login);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), URL_MAIN,url_main);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_EXPENSES_TRAVEL,expenses_travel);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_HOLIDAYS,holidays);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_QR,qr);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_COLLAGE,collage);




        //VISIONARIOS
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), VISIONARIOS_URL,visionarios_url);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), APLICACION_KEY,aplicacion_key);


        /*Bloquear modulos*/
        String block_saving= mFirebaseRemoteConfig.getString(BLOCK_SAVINGS);
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
        String block_covid_survey = mFirebaseRemoteConfig.getString(BLOCK_COVID_SURVEY);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_SAVINGS,block_saving);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_PAYSHEET,block_paysheet);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_CARTASCONFIG,block_letters);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_BENEFICIOS,block_benefits);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_EXPENSES,block_travel);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS,block_holiday);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_PROFILE,block_profile);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_VISIONARIOS,block_visionarios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_STAYHOME, block_stayhome);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COMUNICADOS,block_communication);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_ENCUESTAS,block_surveys);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK,block_message);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COLLAGE,block_collage);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_QR,block_qr);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), BLOCK_COVID_SURVEY,block_covid_survey);

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


    /**PushSnackBar Configuration*/
    public static PushSnackBarConfig getPushSnackBar(Context context) {
        PushSnackBarConfig pushSnackBarConfig = new PushSnackBarConfig(R.layout.push_snackbar_layout);
        //  pushSnackBarConfig.setView(new AVLoadingIndicatorView(context));
        pushSnackBarConfig.setName("PushSnackBar");
        pushSnackBarConfig.setColorText(CoppelApp.getContext().getResources().getColor(R.color.colorBackgroundCoppelBlanco));
        pushSnackBarConfig.setColor(CoppelApp.getContext().getResources().getColor(R.color.colorRed));
        return pushSnackBarConfig;
    }

    public static String getVersionApp(){
        PackageManager manager = CoppelApp.getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(CoppelApp.getContext().getPackageName(), 0);
            return  info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
