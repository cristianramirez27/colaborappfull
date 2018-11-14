package com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Request.JSON_GuardarLogAction;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Response.ResponseGuardarLogAction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceGuardarLogAction {

    @POST("/api/visionarios/savelogaction")
    Call<ResponseGuardarLogAction> post(@Header("Authorization") String authHeader, @Body JSON_GuardarLogAction body);

}
