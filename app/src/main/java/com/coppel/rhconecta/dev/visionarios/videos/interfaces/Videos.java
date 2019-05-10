package com.coppel.rhconecta.dev.visionarios.videos.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public interface Videos {

    interface View {
        void showVideos(ArrayList<Video> videos);

        void cargarVideos(ArrayList<Video> videos, Bitmap[] imagenes);

        Context getContext();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);


    }

    interface Presenter {
        void showVideos(ArrayList<Video> videos);

        void getVideos();

        void getVideosLocal();

        void getEncuestaLocal();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

        void getTextoLabel(String textNode, String defaultText, int textView);

    }

    interface Model {
        void getVideos();

        void getVideosLocal();

        void getEncuestaLocal();

        void getTextoLabel(String textNode, String defaultText, int textView);

    }
}


