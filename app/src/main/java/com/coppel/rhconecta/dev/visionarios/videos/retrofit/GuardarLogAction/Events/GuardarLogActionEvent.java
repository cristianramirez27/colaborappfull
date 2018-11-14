package com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Events;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.GuardarLogAction_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Response.ResponseGuardarLogAction;

public class GuardarLogActionEvent {

    private ResponseGuardarLogAction serverResponse;

    public GuardarLogActionEvent(ResponseGuardarLogAction serverResponse, final GuardarLogAction_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessGuardarLogAction(serverResponse);
    }

    /*public GuardarLogActionEvent(ArrayList<Video> serverResponse, final GuardarLogAction_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessGuardarLogAction(serverResponse);
    }*/

    public ResponseGuardarLogAction getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ResponseGuardarLogAction serverResponse) {
        this.serverResponse = serverResponse;
    }

}
