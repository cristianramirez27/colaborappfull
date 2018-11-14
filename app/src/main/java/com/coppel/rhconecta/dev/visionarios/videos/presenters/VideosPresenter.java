package com.coppel.rhconecta.dev.visionarios.videos.presenters;

import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.Videos;
import com.coppel.rhconecta.dev.visionarios.videos.models.VideosModel;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class VideosPresenter implements Videos.Presenter {

    private Videos.View view;
    private Videos.Model model;

    public VideosPresenter(Videos.View view) {
        this.view = view;
        InternalDatabase idb = new InternalDatabase(this.view.getContext());
        this.model = new VideosModel(this, idb);

    }

    @Override
    public void showVideos(ArrayList<Video> videos) {
        if (this.view != null) {
            this.view.showVideos(videos);
        }
    }

    @Override
    public void getVideos() {
        this.model.getVideos();

    }

    @Override
    public void getVideosLocal() {
        this.model.getVideosLocal();
    }

    @Override
    public void getEncuestaLocal() {
        this.model.getEncuestaLocal();
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {
        if (this.view != null) {
            this.view.showUltimaEncuesta(encuesta);
        }
    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (this.view != null) {
            this.view.ShowTextoDiccionario(text, textView);
        }
    }

    @Override
    public void getTextoLabel(String textNode, String defaultText, int textView) {
        this.model.getTextoLabel(textNode, defaultText, textView);
    }

}
