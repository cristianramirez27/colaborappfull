package com.coppel.rhconecta.dev.visionarios.comunicados.interfaces;

import android.content.Context;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

public interface ComunicadosDetalle {

    interface View {
        void showComunicadosDetalle(Comunicado comunicado);

        Context getContext();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

    }

    interface Presenter {
        void showComunicadosDetalle(Comunicado comunicados);

        void setComunicadoVisto(Comunicado comunicado);

        void getEncuestaLocal();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

        void getTextoLabel(String textNode, String defaultText, int textView);
    }

    interface Model {
        void setComunicadoVisto(Comunicado comunicado);

        void getEncuestaLocal();

        void getTextoLabel(String textNode, String defaultText, int textView);

    }
}
