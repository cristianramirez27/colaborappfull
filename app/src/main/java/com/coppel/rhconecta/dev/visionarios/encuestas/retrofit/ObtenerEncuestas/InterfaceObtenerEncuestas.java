package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Request.JSON_ObtenerEncuestas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceObtenerEncuestas {
    @POST("/api/visionarios/encuestasmenu")
    Call<ArrayList<Encuesta>> post(@Header("Authorization") String authHeader, @Body JSON_ObtenerEncuestas body);
}
