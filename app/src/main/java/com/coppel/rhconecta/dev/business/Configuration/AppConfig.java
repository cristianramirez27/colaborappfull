package com.coppel.rhconecta.dev.business.Configuration;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.io.Serializable;

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


    public final static String ENDPOINT_BENEFICIOS = "ENDPOINT_BENEFICIOS";
    public final static String ENDPOINT_VALIDARFIRMA = "ENDPOINT_VALIDARFIRMA";
    public final static String ENDPOINT_PREVIEW = "ENDPOINT_PREVIEW";
    public final static String ENDPOINT_CARTASGENERAR = "ENDPOINT_CARTASGENERAR";
    public final static String ENDPOINT_CARTASCONFIG = "ENDPOINT_CARTASCONFIG";
    public final static String ENDPOINT_PAYSHEET = "ENDPOINT_PAYSHEET";
    public final static String ENDPOINT_SAVINGS = "ENDPOINT_SAVINGS";
    public final static String ENDPOINT_PROFILE = "ENDPOINT_PROFILE";
    public final static String ENDPOINT_PASSWORD_RECOVER = "ENDPOINT_PASSWORD_RECOVER";
    public final static String ENDPOINT_LOGIN = "ENDPOINT_LOGIN";
    public final static String URL_MAIN_LOGIN = "URL_MAIN_LOGIN";
    public final static String URL_MAIN = "URL_MAIN";

    //VISIONARIOS
    public final static String VISIONARIOS_URL = "VISIONARIOS_URL";
    public final static String APLICACION_KEY = "APLICACION_KEY";


    public final static int CLAVE_LETTER_MAX = 4;


    /**Se almacenan los endpoints*/
    public static void setEndpointConfig(FirebaseRemoteConfig mFirebaseRemoteConfig){
        String beneficios = mFirebaseRemoteConfig.getString(ENDPOINT_BENEFICIOS);
        String validarFirma = mFirebaseRemoteConfig.getString(ENDPOINT_VALIDARFIRMA);
        String preview = mFirebaseRemoteConfig.getString(ENDPOINT_PREVIEW);
        String cartasgenerar = mFirebaseRemoteConfig.getString(ENDPOINT_CARTASGENERAR);
        String cartasconfig = mFirebaseRemoteConfig.getString(ENDPOINT_CARTASCONFIG);
        String paysheet = mFirebaseRemoteConfig.getString(ENDPOINT_PAYSHEET);
        String savings = mFirebaseRemoteConfig.getString(ENDPOINT_SAVINGS);
        String profile = mFirebaseRemoteConfig.getString(ENDPOINT_PROFILE);
        String passrecover = mFirebaseRemoteConfig.getString(ENDPOINT_PASSWORD_RECOVER);
        String login = mFirebaseRemoteConfig.getString(ENDPOINT_LOGIN);
        String main_login = mFirebaseRemoteConfig.getString(URL_MAIN_LOGIN);
        String url_main = mFirebaseRemoteConfig.getString(URL_MAIN);

        //VISIONARIOS
        String visionarios_url = mFirebaseRemoteConfig.getString(VISIONARIOS_URL);
        String aplicacion_key = mFirebaseRemoteConfig.getString(APLICACION_KEY);

        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_BENEFICIOS,beneficios);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_VALIDARFIRMA,validarFirma);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PREVIEW,preview);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CARTASGENERAR,cartasgenerar);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_CARTASCONFIG,cartasconfig);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PAYSHEET,paysheet);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_SAVINGS,savings);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PROFILE,profile);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_PASSWORD_RECOVER,passrecover);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), ENDPOINT_LOGIN,login);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), URL_MAIN_LOGIN,main_login);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), URL_MAIN,url_main);

        //VISIONARIOS
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), VISIONARIOS_URL,visionarios_url);
        AppUtilities.saveStringInSharedPreferences(getApplicationContext(), APLICACION_KEY,aplicacion_key);


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
