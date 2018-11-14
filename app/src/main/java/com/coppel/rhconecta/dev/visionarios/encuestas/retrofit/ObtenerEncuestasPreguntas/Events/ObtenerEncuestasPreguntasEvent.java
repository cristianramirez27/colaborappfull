package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Events;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.ObtenerEncuestasPreguntas_Callback;

import java.util.ArrayList;

public class ObtenerEncuestasPreguntasEvent {

    private ArrayList<Pregunta> serverResponse;

    public ObtenerEncuestasPreguntasEvent(ArrayList<Pregunta> serverResponse, final ObtenerEncuestasPreguntas_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerEncuestasPreguntas(serverResponse);
    }

    public ArrayList<Pregunta> getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ArrayList<Pregunta> serverResponse) {
        this.serverResponse = serverResponse;
    }

}
