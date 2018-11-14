package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Response;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public class Data {
    public ArrayList<Comunicado> response;

    public Data(ArrayList<Comunicado> response) {
        this.response = response;
    }


    public ArrayList<Comunicado> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Comunicado> response) {
        this.response = response;
    }
}
