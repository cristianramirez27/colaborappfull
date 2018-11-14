package com.coppel.rhconecta.dev.visionarios.videos.presenters;

import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.VideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.models.VideosDetalleModel;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

public class VideosDetallePresenter implements VideosDetalle.Presenter {


    private VideosDetalle.View view;
    private VideosDetalle.Model model;


    public VideosDetallePresenter(VideosDetalle.View view) {
        this.view = view;
        InternalDatabase idb = new InternalDatabase(this.view.getContext());
        this.model = new VideosDetalleModel(this, idb);
    }

    @Override
    public void showVideo(Video video) {
        if (view != null) {
            view.showVideo(video);
        }
    }

    @Override
    public void showVideoDetalle(ResponseObtenerVideosDetalle videoDetalle) {
        if (this.view != null) {
            this.view.showVideoDetalle(videoDetalle);
        }
    }

    @Override
    public void getVideoDetalle(int idvideo) {
        this.model.getVideoDetalle(idvideo);
    }

    @Override
    public void guardarLikeDislike(int idvideo, int tipolog) {
        this.model.guardarLikeDislike(idvideo, tipolog);
    }

    @Override
    public void guardarLog(int idvideo, int tipolog) {
        this.model.guardarLog(idvideo, tipolog);
    }

    @Override
    public void showLikeDislike(int tipolog) {
        if (this.view != null) {
            this.view.showLikeDislike(tipolog);
        }
    }

    @Override
    public void setVideoVisto(Video video) {
        this.model.setVideoVisto(video);
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
