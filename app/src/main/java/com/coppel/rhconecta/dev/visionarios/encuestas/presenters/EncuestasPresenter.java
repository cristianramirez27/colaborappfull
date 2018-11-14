package com.coppel.rhconecta.dev.visionarios.encuestas.presenters;

import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.encuestas.interfaces.Encuestas;
import com.coppel.rhconecta.dev.visionarios.encuestas.models.EncuestasModel;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;

import java.util.ArrayList;

public class EncuestasPresenter implements Encuestas.Presenter {

    private Encuestas.View view;
    private EncuestasModel model;

    public EncuestasPresenter(Encuestas.View view) {
        this.view = view;
        InternalDatabase idb = new InternalDatabase(this.view.getContext());
        this.model = new EncuestasModel(this, idb);
    }

    @Override
    public void showPreguntas(ArrayList<Pregunta> preguntas) {
        if (this.view != null) {
            this.view.showPreguntas(preguntas);
        }
    }

    @Override
    public void getPreguntas(int idencuesta) {

        this.model.getPreguntas(idencuesta);
    }

    @Override
    public void showPregunta(int idxPregunta) {
        if (this.view != null) {
            this.view.showPregunta(idxPregunta);
        }
    }

    @Override
    public void guardarEncuesta(ArrayList<Pregunta> preguntas) {
        this.model.guardarEncuesta(preguntas);
    }

    @Override
    public void showEncuestaGuardada(String msg) {
        if (this.view != null) {
            this.view.showEncuestaGuardada(msg);
        }
    }

    @Override
    public void showEncuestaNoGuardada(String msg) {
        if (this.view != null) {
            this.view.showEncuestaNoGuardada(msg);
        }
    }

    @Override
    public void showEncuestaNoGuardadaError() {
        if (this.view != null) {
            this.view.showEncuestaNoGuardadaError();
        }
    }

    @Override
    public void setEncuestaVisto(Encuesta encuesta) {
        this.model.setEncuestaVisto(encuesta);
    }


    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (this.view != null) {
            this.view.ShowTextoDiccionario(text, textView);
        }
    }

    @Override
    public void ShowTextoDiccionarioError(String text, int textView) {
        if (this.view != null) {
            this.view.ShowTextoDiccionarioError(text, textView);
        }
    }

    @Override
    public void getTextoLabel(String textNode, String defaultText, int textView) {
        this.model.getTextoLabel(textNode, defaultText, textView);
    }

    @Override
    public void getTextoLabelError(String textNode, String defaultText, int textView) {
        this.model.getTextoLabelError(textNode, defaultText, textView);
    }

    @Override
    public void guardarTextoDiccionario(String text, String key) {
        if (this.view != null) {
            this.view.guardarTextoDiccionario(key, text);
        }
    }
}

