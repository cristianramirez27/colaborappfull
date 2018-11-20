package com.coppel.rhconecta.dev.visionarios.videos.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.utils.DownloadImageTask;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.VideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.presenters.VideosDetallePresenter;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

import uk.breedrapps.vimeoextractor.OnVimeoExtractionListener;
import uk.breedrapps.vimeoextractor.VimeoExtractor;
import uk.breedrapps.vimeoextractor.VimeoVideo;

public class VideosDetalleActivity extends AppCompatActivity implements VideosDetalle.View {

    private String TAG = "VideosDetalleActivity";
    VideosDetallePresenter presenter;

    private Toolbar toolbar;
    private MediaController mediacontroller;
    private VideoView videoPlayer;
    private ImageView imgPlayVideo;
    private TextView labelTitulo;
    private TextView labelEncabezado;
    private TextView labelContenido;
    private TextView labelFecha;
    private TextView labelVisitas;
    private TextView labelLikes;
    private TextView labelDislikes;
    private ImageView btnlike;
    private ImageView btnDislike;
    private ImageView imgPreview;
    private WebView webViewVideo;
    private View bordeAmarillo;

    private String videoEjemplo2 = "https://vimeo.com/264123984/cd7df59153";

    private Video video;
    private ResponseObtenerVideosDetalle videoDetalle;
    private String StringHtmlVideo = "<head><meta http-equiv=\"Content-Security-Policy\" content=\"default-src * gap:; script-src * 'unsafe-inline' 'unsafe-eval'; connect-src *; img-src * data: blob: android-webview-video-poster:; style-src * 'unsafe-inline';\"></head><iframe id='videplayer' src='//player.vimeo.com/video/<IDVIDEO>?title=0&amp;byline=0&amp;portrait=0&amp;color=ffffff' width='100%' height='100%' frameborder='0' ></iframe>";
    private String StringHtmlVideoPlay = "<head><meta http-equiv=\"Content-Security-Policy\" content=\"default-src * gap:; script-src * 'unsafe-inline' 'unsafe-eval'; connect-src *; img-src * data: blob: android-webview-video-poster:; style-src * 'unsafe-inline';\"></head><iframe id='videplayer' src='//player.vimeo.com/video/<IDVIDEO>?title=0&amp;byline=0&amp;portrait=0&amp;color=ffffff&amp;autoplay=1' width='100%' height='100%' frameborder='0'  allow='autoplay' ></iframe>";

    private String StringUrlVideo = "https://player.vimeo.com/video/<IDVIDEO>?title=0&amp;byline=0&amp;portrait=0&amp;color=ffffff";
    private String StringUrlVideoPlay = "https://player.vimeo.com/video/<IDVIDEO>?title=0&amp;byline=0&amp;portrait=0&amp;color=ffffff&amp;autoplay=1";

    private Encuesta ultimaEncuesta;
    private SurveyInboxView surveyInboxView;

    private boolean videoEnCurso = false;
    private boolean videoCompleto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_detalle);

        presenter = new VideosDetallePresenter(this);
        videoPlayer = (VideoView) findViewById(R.id.videoPlayer);
        imgPlayVideo = (ImageView) findViewById(R.id.imgPlayVideo);
        labelTitulo = (TextView) findViewById(R.id.labelTitulo);
        labelEncabezado = (TextView) findViewById(R.id.labelEncabezado);
        labelContenido = (TextView) findViewById(R.id.labelContenido);
        labelFecha = (TextView) findViewById(R.id.labelFecha);
        labelVisitas = (TextView) findViewById(R.id.labelVisitas);
        labelLikes = (TextView) findViewById(R.id.labelLikes);
        labelDislikes = (TextView) findViewById(R.id.labelDislikes);
        btnlike = (ImageView) findViewById(R.id.btnlike);
        btnDislike = (ImageView) findViewById(R.id.btnDislike);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        webViewVideo = (WebView) findViewById(R.id.webViewVideo);
        bordeAmarillo = (View) findViewById(R.id.bordeAmarillo);

        initializeToolBar();
        video = (Video) getIntent().getSerializableExtra("video");
        presenter.showVideo(video);
        presenter.getVideoDetalle(video.getIdvideos());
        presenter.setVideoVisto(video);
        presenter.getTextoLabel("cTitulo", "Visionarios", R.id.toolbar);

        presenter.getEncuestaLocal();

        /**Modificacion: 1 Noviembre 2018
         * Se quita el icono de encuestas**/
        surveyInboxView.setVisibility(View.INVISIBLE);

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


    @Override
    public void onBackPressed() { //DESHABILITA EL BOTON DE REGRESAR

        if (true) {
            if (videoEnCurso) {
                if (!videoCompleto) {
                    presenter.guardarLog(video.getIdvideos(), 2);
                }
            }
            finish();
        } else {
            super.onBackPressed();
        }

    }


    private void playVideo(final String stream) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoEnCurso = true;
                videoCompleto = false;
                mediacontroller = new MediaController(VideosDetalleActivity.this);
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
                        imgPreview.setVisibility(View.VISIBLE);
                        imgPlayVideo.setVisibility(View.VISIBLE);

                    }
                });


            }
        });
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

    void initWebViewVideoPlay() {
        webViewVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewVideo.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webViewVideo.setWebViewClient(new WebViewClient());
        webViewVideo.getSettings().setJavaScriptEnabled(true);
        webViewVideo.setBackgroundColor(Color.BLACK);
        // webViewVideo.loadUrl(StringUrlVideo);

        webViewVideo.loadDataWithBaseURL(StringUrlVideoPlay, StringHtmlVideoPlay,
                "text/html; charset=utf-8", "UTF-8", null);
    }


    public void playVideo(View v) {
        if (imgPlayVideo.getVisibility() == View.VISIBLE) {
            initializePlayer();
            imgPreview.setVisibility(View.INVISIBLE);
            imgPlayVideo.setVisibility(View.INVISIBLE);

        }
    }

    void initializeToolBar() {
        bordeAmarillo.setVisibility(View.GONE);
        surveyInboxView = (SurveyInboxView) findViewById(R.id.surveyInbox);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Visionarios");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoEnCurso) {
                    if (!videoCompleto) {
                        presenter.guardarLog(video.getIdvideos(), 2);
                    }
                }
                finish();
            }
        });

        surveyInboxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ultimaEncuesta != null) {
                    Intent intentEncuesta = new Intent(v.getContext(), EncuestaActivity.class);
                    intentEncuesta.putExtra("encuesta", ultimaEncuesta);
                    startActivity(intentEncuesta);
                } else {
                    Toast.makeText(getBaseContext(), "No hay encuestas nuevas", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 30 Octubre 2018
     * Modificacion: Se modificó la siguiente función para la corrección de texto en la vista detallada del video---
     *
     * **/
    @Override
    public void showVideo(Video video) {
        if(video != null){


            try{
                new DownloadImageTask((ImageView) imgPreview).execute(video.getImagen_video_preview());
            }catch (Exception e){
                Log.d("VideoAdapter","No se ha podido descargar la imagen del video.");
            }

            labelTitulo.setText(video.getTitulo());
            labelEncabezado.setText("\n"+video.getDescripcion());

            String date=video.getFecha();
            String fechaFormateada;
            try {
                String[] fechas = date.split("-");
                fechaFormateada=fechas[2]+"-"+fechas[1]+"-"+fechas[0];
            }
            catch(Exception e) {
                fechaFormateada=date;
            }

            labelFecha.setText(fechaFormateada);
            presenter.getTextoLabel("vistas","",R.id.labelVisitas);
            labelContenido.setText(Html.fromHtml(video.getDescripcion()));
            labelLikes.setText("");
            labelDislikes.setText("");
            StringHtmlVideoPlay=StringHtmlVideoPlay.replace("<IDVIDEO>",Integer.toString(video.getIdvideos()));
            StringUrlVideo=StringUrlVideo.replace("<IDVIDEO>",Integer.toString(video.getIdvideos()));
            StringUrlVideoPlay=StringUrlVideoPlay.replace("<IDVIDEO>",Integer.toString(video.getIdvideos()));

            StringHtmlVideo=StringHtmlVideo.replace("<IDVIDEO>",Integer.toString(video.getIdvideos()));
            Log.d("VIDEO",StringUrlVideo);
        }
    }


    @Override
    public void showVideoDetalle(ResponseObtenerVideosDetalle videoDetalle) {
        if (videoDetalle != null) {

            btnlike.setOnClickListener(null);
            btnDislike.setOnClickListener(null);
            this.videoDetalle = videoDetalle;
            if (videoDetalle.getLogaction() != null) {
                if (videoDetalle.getLogaction().getTipo_log() == 5) { //like
                    btnlike.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_36dp));

                } else if (videoDetalle.getLogaction().getTipo_log() == 6) { //dislike
                    btnDislike.setImageDrawable(getDrawable(R.drawable.ic_thumb_down_red_36dp));

                } else { // no se ha dado like ni dislike al video
                    Log.d("NOACTION", "NO ACTION LOG EN ESTE VIDEO");
                    btnlike.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_gris_36dp));
                    btnDislike.setImageDrawable(getDrawable(R.drawable.ic_thumb_down_gris_36dp));

                    btnlike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.guardarLikeDislike(video.getIdvideos(), 5);
                        }
                    });
                    btnDislike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.guardarLikeDislike(video.getIdvideos(), 6);

                        }
                    });
                }
            } else {
                Log.d("NOACTION", "Objetu null NO ACTION LOG EN ESTE VIDEO");
                btnlike.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_gris_36dp));
                btnDislike.setImageDrawable(getDrawable(R.drawable.ic_thumb_down_gris_36dp));

                btnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.guardarLikeDislike(video.getIdvideos(), 5);
                    }
                });
                btnDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.guardarLikeDislike(video.getIdvideos(), 6);

                    }
                });
            }

        }
    }

    @Override
    public void showLikeDislike(int tipolog) {
        if (this.videoDetalle.getLogaction() != null) {
            this.videoDetalle.getLogaction().setIdlogaction(tipolog);
        } else {
            this.videoDetalle.setLogaction(new ResponseObtenerVideosDetalle.LogAction(0, tipolog));
        }
        this.presenter.showVideoDetalle(this.videoDetalle);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    public void setFullScreenVideo(View v) {
        Intent intentFullScreen = new Intent(v.getContext(), VideosFullScreenActivity.class);
        intentFullScreen.putExtra("video", video);
        intentFullScreen.putExtra("baseUrl", StringUrlVideo);
        intentFullScreen.putExtra("videoHtml", StringHtmlVideo);
        startActivity(intentFullScreen);
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {
        if (encuesta != null) {
            surveyInboxView.setCountMessages(1);
            ultimaEncuesta = encuesta;
        } else {
            surveyInboxView.setCountMessages(0);
        }
    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (textView == R.id.toolbar) {
            toolbar.setTitle(text);
        } else {
            if (textView == R.id.labelVisitas) {
                TextView textLabel = (TextView) findViewById(textView);
                textLabel.setText(video.getVistas() + " Vistas");
            } else {
                TextView textLabel = (TextView) findViewById(textView);
                textLabel.setText(text);
            }
        }
    }
}
