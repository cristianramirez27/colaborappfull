package com.coppel.rhconecta.dev.visionarios.comunicados.interfaces;

import android.content.Context;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

import java.util.ArrayList;

public interface Comunicados {

    interface View {
        void showComunicados(ArrayList<Comunicado> comunicados);

        Context getContext();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

    }

    interface Presenter {
        void showComunicados(ArrayList<Comunicado> comunicados);

        void getComunicados();

        void getComunicadosLocal();

        void getEncuestaLocal();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

        void getTextoLabel(String textNode, String defaultText, int textView);
    }

    interface Model {
        void getComunicados();

        void getComunicadosLocal();

        void getEncuestaLocal();

        void getTextoLabel(String textNode, String defaultText, int textView);

    }
}
