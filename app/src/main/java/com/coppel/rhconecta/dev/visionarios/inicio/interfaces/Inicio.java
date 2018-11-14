package com.coppel.rhconecta.dev.visionarios.inicio.interfaces;

import android.content.Context;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public interface Inicio {

    interface View {
        Context getContext();

        void showUltimaEncuesta(Encuesta encuesta);

        void showBangesComunicados(int nuevos);

        void showBangesVideos(int nuevos);

        void ShowTextoDiccionario(String text, int textView);

        void showError();

        void guardarTextoDiccionario(String key, String text);

        void showVideosLanding(ArrayList<Video> videos);

        void showComunicadosLanding(ArrayList<Comunicado> comunicados);

        /**Modificación  7 Noviembre 2018
         * Se agregan los métodos showProgress() y hideProgress() para mostrar loader en el Dashboard*/
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void guardarLogin();

        void showUltimaEncuesta(Encuesta encuesta);

        void showError();

        void showBangesComunicados(int nuevos);

        void showBangesVideos(int nuevos);

        void getUltimaEncuesta();

        void getBadgesComunicados();

        void getBadgesVideos();

        void getComunicados();

        void getVideos();

        void ShowTextoDiccionario(String text, int textView);

        void getTextoLabel(String textNode, String defaultText, int textView);

        void guardarTextoDiccionario(String key, String text);

        void getTextoLabelError(String textNode, String defaultText);

        void showVideosLanding(ArrayList<Video> videos);

        void showComunicadosLanding(ArrayList<Comunicado> comunicados);

    }

    interface Model {
        void guardarLogin();

        void getBadgesComunicados();

        void getBadgesVideos();

        void getComunicados();

        void getVideos();

        void getUltimaEncuesta();

        void getTextoLabel(String textNode, String defaultText, int textView);

        void getTextoLabelError(String textNode, String defaultText);


    }
}