package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;

import java.util.ArrayList;

public interface ObtenerEncuestasPreguntas_Callback {

    void onSuccessObtenerEncuestasPreguntas(ArrayList<Pregunta> result);

    void onFailObtenerEncuestasPreguntas(String result);
}
