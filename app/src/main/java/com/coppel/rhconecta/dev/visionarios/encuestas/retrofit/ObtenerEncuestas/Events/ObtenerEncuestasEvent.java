package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Events;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.ObtenerEncuestas_Callback;

import java.util.ArrayList;

public class ObtenerEncuestasEvent {

    private ArrayList<Encuesta> serverResponse;


    public ObtenerEncuestasEvent(ArrayList<Encuesta> serverResponse, final ObtenerEncuestas_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerEncuestas(serverResponse);
    }

    public ArrayList<Encuesta> getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ArrayList<Encuesta> serverResponse) {
        this.serverResponse = serverResponse;
    }


}