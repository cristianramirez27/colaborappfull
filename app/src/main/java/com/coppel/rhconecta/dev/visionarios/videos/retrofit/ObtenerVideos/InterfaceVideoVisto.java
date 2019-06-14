package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceVideoVisto {
    @POST("/api/visionarios/videosvisto")
    Call<ArrayList<Video>> post(@Header("Authorization") String authHeader, @Body JSON_ObtenerVideos body);
}
