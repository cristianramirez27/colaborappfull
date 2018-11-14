package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Response;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;

import java.util.ArrayList;

public class Response {

    private ArrayList<Pregunta> avisosCatalogos;

    /*Message Error*/

    public Response(ArrayList<Pregunta> avisosCatalogos) {
        this.avisosCatalogos = avisosCatalogos;
    }

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
