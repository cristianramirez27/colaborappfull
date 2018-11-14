package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class Data {
    public ArrayList<Video> response;

    public Data(ArrayList<Video> response) {
        this.response = response;
    }


    public ArrayList<Video> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Video> response) {
        this.response = response;
    }
}
