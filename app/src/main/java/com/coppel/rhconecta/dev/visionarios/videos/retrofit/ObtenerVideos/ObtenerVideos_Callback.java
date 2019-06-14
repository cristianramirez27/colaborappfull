package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response.ResponseObtenerVideos;

import java.util.ArrayList;

public interface ObtenerVideos_Callback {

    void onSuccessObtenerVideos(ResponseObtenerVideos result);

    void onFailObtenerVideos(String result);
}