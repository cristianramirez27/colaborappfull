package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Events;

import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.GuardarLogin_Callback;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response.ResponseGuardarLogin;

public class GuardarLoginEvent {

    private ResponseGuardarLogin serverResponse;

    public GuardarLoginEvent(ResponseGuardarLogin serverResponse, final GuardarLogin_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessGuardarLogin(serverResponse);
    }

    public ResponseGuardarLogin getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ResponseGuardarLogin serverResponse) {
        this.serverResponse = serverResponse;
    }
}
