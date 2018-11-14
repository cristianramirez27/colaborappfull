package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin;

import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Request.JSON_GuardarLogin;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response.ResponseGuardarLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceGuardarLogin {
    @POST("/api/visionarios/login")
    Call<ResponseGuardarLogin> post(@Header("Authorization") String authHeader, @Body JSON_GuardarLogin body);
}
