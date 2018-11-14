package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Response;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;

import java.util.ArrayList;

public class ResponseObtenerEncuestasPreguntas {

    public Meta meta;
    public Data data;

    public ArrayList<Pregunta> comunicados;

    public ResponseObtenerEncuestasPreguntas(ArrayList<Pregunta> comunicados) {
        this.comunicados = comunicados;
    }

    public ResponseObtenerEncuestasPreguntas(Meta Meta, Data data) {
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
