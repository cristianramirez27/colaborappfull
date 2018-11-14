package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Request.JSON_ObtenerVideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceObtenerVideosDetalle {


    @POST("/api/visionarios/relinfovideo")
    Call<ResponseObtenerVideosDetalle> post(@Header("Authorization") String authHeader, @Body JSON_ObtenerVideosDetalle body);

}
