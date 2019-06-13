package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Events;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.VideoVisto_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class VideoVistoEvent {
    private String sMensaje;
    private ArrayList<Video> serverResponse;

    public VideoVistoEvent(ArrayList<Video> serverResponse, final VideoVisto_Callback callback){

        callback.VideoVistoMensaje(serverResponse);
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public ArrayList<Video> getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ArrayList<Video> serverResponse) {
        this.serverResponse = serverResponse;
    }
}
