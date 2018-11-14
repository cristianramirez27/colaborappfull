package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class ResponseObtenerVideos {

    public Meta meta;
    public Data data;

    public ArrayList<Video> videos;

    public ResponseObtenerVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ResponseObtenerVideos(Meta Meta, Data data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta Meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

