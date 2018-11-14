package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Events;

import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.GuardarLogin_Callback;

public class ErrorEvent {
    private int errorCode;
    private String errorMsg;

    public ErrorEvent(int errorCode, String errorMsg, final GuardarLogin_Callback callback) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        callback.onFailGuardarLogin(errorMsg);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

