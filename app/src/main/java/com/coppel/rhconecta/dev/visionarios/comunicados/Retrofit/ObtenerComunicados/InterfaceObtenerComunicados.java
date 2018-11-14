package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados;

import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request.JSON_ObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceObtenerComunicados {
    @POST("/api/visionarios/avisosmenu")
    Call<ArrayList<Comunicado>> post(@Header("Authorization") String authHeader, @Body JSON_ObtenerComunicados body);
}
