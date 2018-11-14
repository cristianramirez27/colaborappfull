package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Response;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;

import java.util.ArrayList;

public class Data {
    public ArrayList<Pregunta> response;

    public Data(ArrayList<Pregunta> response) {
        this.response = response;
    }

    public ArrayList<Pregunta> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Pregunta> response) {
        this.response = response;
    }
}
