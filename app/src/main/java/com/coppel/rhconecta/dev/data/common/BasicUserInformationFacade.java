package com.coppel.rhconecta.dev.data.common;

import android.content.Context;

import com.coppel.rhconecta.dev.views.utils.AppConstants;

import static com.coppel.rhconecta.dev.views.utils.AppUtilities.getStringFromSharedPreferences;

/**
 *
 *
 */
public class BasicUserInformationFacade {

    /* */
    private Context context;


    /**
     *
     *
     */
    public BasicUserInformationFacade(Context context){
        this.context = context;
    }

    /**
     *
     *
     */
    public Long getEmployeeNum(){
        return Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
    }

    /**
     *
     *
     */
    public String getAuthHeader(){
        return getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
    }

}
