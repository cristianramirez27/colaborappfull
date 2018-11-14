package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta;

import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Request.JSON_GuardarEncuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Response.ResponseGuardarEncuesta;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceGuardarEncuesta {
    @POST("/api/visionarios/saveencuesta")
    Call<ResponseGuardarEncuesta> post(@Header("Authorization") String authHeader, @Body JSON_GuardarEncuesta body);
}
