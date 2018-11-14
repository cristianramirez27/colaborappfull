package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Events;

import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.ObtenerEncuestas_Callback;

public class ErrorEvent {
    private int errorCode;
    private String errorMsg;

    public ErrorEvent(int errorCode, String errorMsg, final ObtenerEncuestas_Callback callback) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        callback.onFailObtenerEncuestas(errorMsg);
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