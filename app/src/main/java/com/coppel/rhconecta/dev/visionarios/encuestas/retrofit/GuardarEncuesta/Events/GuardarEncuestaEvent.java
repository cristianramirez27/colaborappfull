package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Events;

import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.GuardarEncuesta_Callback;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Response.ResponseGuardarEncuesta;

public class GuardarEncuestaEvent {

    private ResponseGuardarEncuesta serverResponse;

    public GuardarEncuestaEvent(ResponseGuardarEncuesta serverResponse, final GuardarEncuesta_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessGuardarEncuesta(serverResponse);
    }

    /*public GuardarEncuestaEvent(ArrayList<Encuesta> serverResponse, final GuardarEncuesta_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessGuardarEncuesta(serverResponse);
    }*/

    public ResponseGuardarEncuesta getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ResponseGuardarEncuesta serverResponse) {
        this.serverResponse = serverResponse;
    }
}
