package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Request.JSON_ObtenerEncuestasPreguntas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceObtenerEncuestasPreguntas {
    @POST("/api/visionarios/encuesta")
    Call<ArrayList<Pregunta>> post(@Header("Authorization") String authHeader, @Body JSON_ObtenerEncuestasPreguntas body);
}
