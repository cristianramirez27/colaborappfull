package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Events;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.ObtenerVideos_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response.ResponseObtenerVideos;

import java.util.ArrayList;

public class ObtenerVideosEvent {

    private ResponseObtenerVideos serverResponse;

    public ObtenerVideosEvent(ResponseObtenerVideos serverResponse, final ObtenerVideos_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerVideos(serverResponse);
    }

    /*public ObtenerVideosEvent(ArrayList<Video> serverResponse, final ObtenerVideos_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerVideos(serverResponse);
    }*/

    public ResponseObtenerVideos getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ResponseObtenerVideos serverResponse) {
        this.serverResponse = serverResponse;
    }
}
