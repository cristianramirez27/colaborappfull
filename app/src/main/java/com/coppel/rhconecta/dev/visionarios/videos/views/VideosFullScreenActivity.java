package com.coppel.rhconecta.dev.visionarios.videos.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.VideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.presenters.VideosDetallePresenter;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

import uk.breedrapps.vimeoextractor.OnVimeoExtractionListener;
import uk.breedrapps.vimeoextractor.VimeoExtractor;
import uk.breedrapps.vimeoextractor.VimeoVideo;

public class VideosFullScreenActivity extends AppCompatActivity implements VideosDetalle.View {

    VideosDetalle.Presenter presenter;
    private String TAG = "VideosFullScreenActivity";
    private WebView webViewVideo;
    private String StringUrlVideo;
    private String StringHtmlVideo;
    private Video video;
    private MediaController mediacontroller;
    private VideoView videoPlayer;
    private boolean videoEnCurso = false;
    private boolean videoCompleto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_full_screen);
        presenter = new VideosDetallePresenter(this);

        webViewVideo = (WebView) findViewById(R.id.webViewVideo);
        videoPlayer = (VideoView) findViewById(R.id.videoPlayer);
        StringUrlVideo = getIntent().getStringExtra("baseUrl");
        StringHtmlVideo = getIntent().getStringExtra("videoHtml");
        video = (Video) getIntent().getSerializableExtra("video");
        initializePlayer();
    }

    /**
     * Modificacion 4 Noviembre 2018 KokonutStudio
     * Se finizaliza el activity, para no regresar al video fullscreen
     * */
    @Override
    public void onBackPressed() { //DESHABILITA EL BOTON DE REGRESAR

        if (true) {
            if (videoEnCurso) {
                if (!videoCompleto) {
                    presenter.guardarLog(video.getIdvideos(), 2);
                }
            }

            /**KokonutStudio
             * Se finaliza la activity, sin lanzar una nueva Activity de {@VideosDetalleActivity} */
           /* Intent intent = new Intent(this, VideosDetalleActivity.class);
            intent.putExtra("video", video);
            startActivity(intent);*/

            //Se finaliza la activity
            finish();

        } else {
            super.onBackPressed();
        }

    }


    void initWebViewVideo() {
        webViewVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewVideo.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webViewVideo.setWebViewClient(new WebViewClient());
        webViewVideo.getSettings().setJavaScriptEnabled(true);
        webViewVideo.setBackgroundColor(Color.BLACK);
        // webViewVideo.loadUrl(StringUrlVideo);
        webViewVideo.loadDataWithBaseURL(StringUrlVideo, StringHtmlVideo,
                "text/html; charset=utf-8", "UTF-8", null);
    }


    private void initializePlayer() {
        if (video != null) {

            VimeoExtractor.getInstance().fetchVideoWithIdentifier(video.getIdvideos() + "", null, new OnVimeoExtractionListener() {
                @Override
                public void onSuccess(VimeoVideo videoVimeo) {
                    String hdStream = videoVimeo.getStreams().get("360p");
                    Log.d(TAG, "VIMEO VIDEO STREAM " + hdStream);
                    if (hdStream != null) {
                        presenter.guardarLog(video.getIdvideos(), 1);
                        playVideo(hdStream);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        } else {

        }
    }


    private void playVideo(final String stream) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoEnCurso = true;
                videoCompleto = false;
                mediacontroller = new MediaController(VideosFullScreenActivity.this);
                mediacontroller.setAnchorView(videoPlayer);
                videoPlayer.setMediaController(mediacontroller);
                videoPlayer.setBackgroundColor(Color.TRANSPARENT);
                Uri videoURI = Uri.parse(stream);
                videoPlayer.setVideoURI(videoURI);
                videoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.d(TAG, "Error en media player ");
                        return false;
                    }
                });

                videoPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (mp.isPlaying()) {
                            videoEnCurso = true;
                        } else {
                            videoEnCurso = false;
                        }
                        return false;
                    }
                });

                videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoPlayer.requestFocus();
                        videoPlayer.start();
                        videoEnCurso = true;
                      /*  mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                mediacontroller.setAnchorView(videoPlayer);
                            }
                        });*/

                    }
                });

                videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //mp.release();
                        Log.d(TAG, "Video Finish");
                        videoEnCurso = false;
                        videoCompleto = true;
                    }
                });


            }
        });
    }


    @Override
    public void showVideo(Video video) {

    }

    @Override
    public void showVideoDetalle(ResponseObtenerVideosDetalle videoDetalle) {

    }

    @Override
    public void showLikeDislike(int tipolog) {

    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {

    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {

    }
}
