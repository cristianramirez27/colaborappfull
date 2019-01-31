package com.coppel.rhconecta.dev.business.Configuration;

import android.app.Activity;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.views.activities.WebExplorerActivity;
import com.coppel.rhconecta.dev.visionarios.MainActivity;

/**
 * Created by faust on 12/1/17.
 */

public class LinksNavigation<T> {
    public final static String KEY_URL= "_KEY_URL";
    public final static String SCHEME_DEV = "kstdev://";
    public final static String SCHEME_PROD = "kstProd://";
    public static String SCHEME = "kstdev://";

    public static int idLayoutWebActivity =  R.layout.activity_explorer;


    /*Region Secciones*/
    public final static String TARGET_LEGAL = "Legals";
    public final static String TARGET_CONTENT = "Content";



    public static void navigateTo(Activity activity, String dest){

        if(dest.startsWith(SCHEME)){// Si es una URI Interna
            openInternalTarget(activity,dest);

        }else{
            String externalUrl = dest.replace(SCHEME,"");
            NavigationUtil.openActivityWithStringParamTransition(activity, WebExplorerActivity.class,KEY_URL,externalUrl);

        }

    }

    /**Método para implementar la navegación a través de las alertas**/

    private static void openInternalTarget(Activity activity, String target){
        String internalTarget = target.replace(SCHEME,"");
        if(internalTarget.contains(TARGET_CONTENT)) {
            if(activity instanceof MainActivity){
              //  OnEventListener onEventListener = ((MainActivity)activity).getOnEventListener();
              //  onEventListener.onEvent(EVENT_GO_CONTENT,null);
            }
        }
    }


}
