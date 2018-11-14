package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response;

public class Response {

    public String errorCode;
    public String userMessage;

    public Response() {
    }

    public Response(String errorCode, String userMessage) {
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
