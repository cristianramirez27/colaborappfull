package com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Response;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class Response {
    private ArrayList<Video> videos;

    /*Message Error*/

    public Response(ArrayList<Video> videos) {
        this.videos = videos;
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
