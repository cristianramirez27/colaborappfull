package com.coppel.rhconecta.dev.visionarios.videos.interfaces;

import android.content.Context;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

public interface VideosDetalle {

    interface View {
        void showVideo(Video video);

        void showVideoDetalle(ResponseObtenerVideosDetalle videoDetalle);

        void showLikeDislike(int tipolog);

        Context getContext();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

    }

    interface Presenter {
        void showVideo(Video video);

        void showVideoDetalle(ResponseObtenerVideosDetalle videoDetalle);

        void getVideoDetalle(int idvideo);

        void guardarLikeDislike(int idvideo, int tipolog);

        void guardarLog(int idvideo, int tipolog);

        void showLikeDislike(int tipolog);

        void setVideoVisto(Video video);

        void getEncuestaLocal();

        void showUltimaEncuesta(Encuesta encuesta);

        void ShowTextoDiccionario(String text, int textView);

        void getTextoLabel(String textNode, String defaultText, int textView);


    }

    interface Model {
        void getVideoDetalle(int idvideo);

        void guardarLikeDislike(int idvideo, int tipolog);

        void guardarLog(int idvideo, int tipolog);

        void setVideoVisto(Video video);

        void getEncuestaLocal();

        void getTextoLabel(String textNode, String defaultText, int textView);

    }
}
