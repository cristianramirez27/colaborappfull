package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response.ResponseObtenerVideos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceObtenerVideos {


    @POST("/api/visionarios/videosmenu")
    Call<ResponseObtenerVideos> post(@Header("Authorization") String authHeader, @Body JSON_ObtenerVideos body);

}
