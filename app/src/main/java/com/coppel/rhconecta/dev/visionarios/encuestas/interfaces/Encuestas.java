package com.coppel.rhconecta.dev.visionarios.encuestas.interfaces;

import android.content.Context;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;

import java.util.ArrayList;

public interface Encuestas {

    interface View {
        void showPreguntas(ArrayList<Pregunta> preguntas);

        void showPregunta(int idxPregunta);

        void showEncuestaGuardada(String msg);

        void showEncuestaNoGuardada(String msg);

        void showEncuestaNoGuardadaError();

        Context getContext();

        void ShowTextoDiccionario(String text, int textView);

        void ShowTextoDiccionarioError(String text, int textView);

        void guardarTextoDiccionario(String text, String key);

    }

    interface Presenter {
        void showPreguntas(ArrayList<Pregunta> preguntas);

        void getPreguntas(int idencuesta);

        void showPregunta(int idxPregunta);

        void guardarEncuesta(ArrayList<Pregunta> preguntas);

        void showEncuestaGuardada(String msg);

        void showEncuestaNoGuardada(String msg);

        void showEncuestaNoGuardadaError();

        void setEncuestaVisto(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

        void getTextoLabel(String textNode, String defaultText, int textView);

        void getTextoLabelError(String textNode, String defaultText, int textView);

        void ShowTextoDiccionarioError(String text, int textView);

        void guardarTextoDiccionario(String text, String key);

    }

    interface Model {
        void getPreguntas(int idencuesta);

        void guardarEncuesta(ArrayList<Pregunta> preguntas);

        void setEncuestaVisto(Encuesta encuesta);

        void getTextoLabel(String textNode, String defaultText, int textView);

        void getTextoLabelError(String textNode, String defaultText, int textView);

    }
}