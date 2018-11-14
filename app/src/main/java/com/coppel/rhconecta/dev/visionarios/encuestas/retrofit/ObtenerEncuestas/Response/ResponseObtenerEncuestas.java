package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Response;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

import java.util.ArrayList;

public class ResponseObtenerEncuestas {

    public Meta meta;
    public Data data;

    public ArrayList<Encuesta> comunicados;

    public ResponseObtenerEncuestas(ArrayList<Encuesta> comunicados) {
        this.comunicados = comunicados;
    }

    public ResponseObtenerEncuestas(Meta Meta, Data data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta Meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

