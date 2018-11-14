package com.coppel.rhconecta.dev.business.utils;

import android.content.Context;

import com.coppel.rhconecta.dev.R;

public class ServicesGeneralValidations {

    /**
     * Validate that the email and password are not null
     *
     * @param email    Email
     * @param password Password
     * @param context  Application context
     * @return Returns a success message if the parameters are not null, otherwise it returns an error message
     */
    public String validateEmailAndPassword(String email, String password, Context context) {
        if (email != null && password != null) {
            return ServicesConstants.SUCCESS;
        } else {
            return context.getString(R.string.error_account_null);
        }
    }

    /**
     * Validate size of user password
     *
     * @param password password
     * @param context  Application context
     * @return String with success or error message
     */
    public String validatePassword(String password, Context context) {
        if (password == null) {
            return context.getString(R.string.error_password);
        } else {
            String passwordPattern = ServicesConstants.REGEX_PASSWORD;
            if (password.matches(passwordPattern)) {
                return ServicesConstants.SUCCESS;
            } else {
                return context.getString(R.string.invalid_password);
            }
        }
    }

    /**
     * Validates the entered email
     *
     * @param email   email
     * @param context Application context
     * @return String with success or error message
     */
    public String validateEmail(String email, Context context) {
        if (email == null) {
            return context.getString(R.string.error_email);
        } else {
            String emailPattern = ServicesConstants.REGEX_EMAIL;
            email = email.trim();
            if (email.matches(emailPattern)) {
                return ServicesConstants.SUCCESS;
            } else {
                return context.getString(R.string.invalid_email);
            }
        }
    }

    /**
     * Checks if the responseCode is 200
     *
     * @param responseCode
     * @return Returns true if responseCode is 200, otherwise returns false
     */
    public boolean verifySuccessCode(int responseCode) {
        if (responseCode == 200) {
            return true;
        } else {
            return false;
        }
    }
}
