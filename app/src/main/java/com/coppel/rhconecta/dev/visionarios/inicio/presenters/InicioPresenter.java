package com.coppel.rhconecta.dev.visionarios.inicio.presenters;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.inicio.interfaces.Inicio;
import com.coppel.rhconecta.dev.visionarios.inicio.models.InicioModel;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class InicioPresenter implements Inicio.Presenter {

    private Inicio.View view;
    private InicioModel model;

    public Inicio.View getView() {
        return view;
    }

    public void setView(Inicio.View view) {
        this.view = view;
    }

    public InicioPresenter(Inicio.View view) {
        this.view = view;
        InternalDatabase idb = new InternalDatabase(this.view.getContext());
        this.model = new InicioModel(this, idb);
    }

    @Override
    public void showError() {
        if (this.view != null) {
            this.view.showError();
        }
    }

    @Override
    public void guardarLogin() {

        this.view.showProgress();
        this.model.guardarLogin();
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {
        if (this.view != null) {
            this.view.showUltimaEncuesta(encuesta);
        }
    }

    @Override
    public void showBangesComunicados(int nuevos) {
        if (this.view != null) {
            this.view.showBangesComunicados(nuevos);
        }
    }

    @Override
    public void showBangesVideos(int nuevos) {
        if (this.view != null) {
            this.view.showBangesVideos(nuevos);
        }
    }

    @Override
    public void getUltimaEncuesta() {
        this.model.getUltimaEncuesta();
    }

    @Override
    public void getBadgesComunicados() {
        this.model.getBadgesComunicados();
    }

    @Override
    public void getBadgesVideos() {
        this.model.getBadgesVideos();
    }

    @Override
    public void getComunicados() {
        this.model.getComunicados();
    }

    @Override
    public void getVideos() {
        this.model.getVideos();
    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (view != null) {
            this.view.ShowTextoDiccionario(text, textView);
        }
    }

    @Override
    public void getTextoLabel(String textNode, String defaultText, int textView) {
        this.model.getTextoLabel(textNode, defaultText, textView);
    }

    @Override
    public void guardarTextoDiccionario(String key, String text) {
        if (this.view != null) {
            this.view.guardarTextoDiccionario(key, text);
        }
    }

    @Override
    public void getTextoLabelError(String textNode, String defaultText) {
        this.model.getTextoLabelError(textNode, defaultText);
    }

    @Override
    public void showVideosLanding(ArrayList<Video> videos) {
        if (this.view != null) {
            this.view.showVideosLanding(videos);
        }
    }

    @Override
    public void showComunicadosLanding(ArrayList<Comunicado> comunicados) {

        if (this.view != null) {
            this.view.showComunicadosLanding(comunicados);
        }
    }
}
