package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public interface VideoVisto_Callback {
    void VideoVistoMensaje(ArrayList<Video> result);
}
