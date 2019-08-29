package com.coppel.rhconecta.dev.business.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;

/**
 * Created on 23/08/2016.
 *
 * @author fausto@kokonutstudio
 * @version 1.0
 *
 * Clase de utilidad para obtener la información del dispositivo en el que se ejecuta la aplicación.
 *
 */
public class DeviceManager {


    private static Context context = CoppelApp.getContext();


    /**
     * Método para ocultar el teclado
     *
     * @param  activity {@link Activity}
     */
    public static void hideKeyBoard(Activity activity){

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyBoard(Activity activity){

        View view = activity.getCurrentFocus();
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }



    public static int convertDpToPixels(int dp) {
        DisplayMetrics displayMetrics = CoppelApp.getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int convertPixelsToDp(int px) {
        DisplayMetrics displayMetrics = CoppelApp.getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }


    public static int getToolBarHeight(Context context) {
        int[] attrs = new int[] {R.attr.actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }


    public static int getWidthScreen(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        return Math.round(dpWidth);
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
