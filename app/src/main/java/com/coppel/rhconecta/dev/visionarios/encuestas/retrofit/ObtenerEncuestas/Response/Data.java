package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Response;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

import java.util.ArrayList;

public class Data {
    public ArrayList<Encuesta> response;

    public Data(ArrayList<Encuesta> response) {
        this.response = response;
    }

    public ArrayList<Encuesta> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Encuesta> response) {
        this.response = response;
    }
}
