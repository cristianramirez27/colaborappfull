package com.coppel.rhconecta.dev.visionarios.comunicados.presenters;

import com.coppel.rhconecta.dev.visionarios.comunicados.interfaces.Comunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.models.ComunicadosModel;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

import java.util.ArrayList;

public class ComunicadosPresenter implements Comunicados.Presenter {


    private Comunicados.View view;
    private Comunicados.Model model;

    public ComunicadosPresenter(Comunicados.View view) {
        this.view = view;
        InternalDatabase idb = new InternalDatabase(this.view.getContext());
        this.model = new ComunicadosModel(this, idb);
    }

    @Override
    public void showComunicados(ArrayList<Comunicado> comunicados) {
        if (view != null) {
            view.showComunicados(comunicados);
        }
    }

    @Override
    public void getComunicados() {
        if (view != null) {
            model.getComunicados();
        }
    }

    @Override
    public void getComunicadosLocal() {
        this.model.getComunicadosLocal();
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

