package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Response;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public class ResponseObtenerComunicados {

    public Meta meta;
    public Data data;

    public ArrayList<Comunicado> comunicados;

    public ResponseObtenerComunicados(ArrayList<Comunicado> comunicados) {
        this.comunicados = comunicados;
    }

    public ResponseObtenerComunicados(Meta Meta, Data data) {
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

