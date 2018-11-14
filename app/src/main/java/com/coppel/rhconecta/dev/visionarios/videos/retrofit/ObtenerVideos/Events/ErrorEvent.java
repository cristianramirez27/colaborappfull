package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Events;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.ObtenerVideos_Callback;

public class ErrorEvent {
    private int errorCode;
    private String errorMsg;

    public ErrorEvent(int errorCode, String errorMsg, final ObtenerVideos_Callback callback) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        callback.onFailObtenerVideos(errorMsg);
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
