package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Events;

import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ObtenerComunicados_Callback;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public class ObtenerComunicadosEvent {

    private ArrayList<Comunicado> serverResponse;

 /*   public ObtenerComunicadosEvent(ResponseObtenerComunicados serverResponse, final ObtenerComunicados_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerComunicados(serverResponse);
    }*/

    public ObtenerComunicadosEvent(ArrayList<Comunicado> serverResponse, final ObtenerComunicados_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerComunicados(serverResponse);
    }

    public ArrayList<Comunicado> getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ArrayList<Comunicado> serverResponse) {
        this.serverResponse = serverResponse;
    }


}
