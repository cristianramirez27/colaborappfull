package com.coppel.rhconecta.dev.business.utils;

import android.content.Context;

import com.coppel.rhconecta.dev.R;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.UnknownHostException;

public class ServicesUtilities {
    /**
     * Handles an error response
     *
     * @param context       Application context
     * @param responseCode  Response Code
     * @param message       Error message
     * @param servicesError Error Object
     * @return An error object with an error message
     */
    public ServicesError getErrorByStatusCode(Context context, int responseCode, String message, ServicesError servicesError) {
        switch (responseCode) {
            case 400: //Bad Request
                servicesError.setMessage(context.getString(R.string.error_bad_request));
                break;
            case 401: //Unauthorized
                servicesError.setMessage(context.getString(R.string.error_unauthorized));
                break;
            case 404: //Not Found
                servicesError.setMessage(context.getString(R.string.error_not_found));
                break;
            case 500: //Internal Server Error
                servicesError.setMessage(context.getString(R.string.server_error));
                break;
            default:
                servicesError.setMessage(message);
                break;
        }
        return servicesError;
    }

    /**
     * Handles a retrofit error response
     *
     * @param context     Application context
     * @param t           Exception object
     * @param requestType Request type
     * @return An error object
     */
    public ServicesError getOnFailureResponse(Context context, Throwable t, int requestType) {
        ServicesError servicesError = new ServicesError();
        servicesError.setType(requestType);

        if(t instanceof UnknownHostException){
            servicesError.setMessage(context.getString(R.string.network_error));
        } else if (t instanceof IOException) {
            servicesError.setMessage(context.getString(R.string.error_generic_service));
        } else if (t instanceof IllegalStateException) {
            servicesError.setMessage(context.getString(R.string.error_serialization));
        } else {
            servicesError.setMessage(context.getString(R.string.error_unknown));
        }
        return servicesError;
    }

    /**
     * Return a json object from string
     *
     * @param s string
     * @param c type class to convert
     * @return a object convertion JSON
     */
    public Object parseToObjectClass(String s, Class c) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(s, c);
        } catch (JsonParseException e) {
            return null;
        }
    }
}
