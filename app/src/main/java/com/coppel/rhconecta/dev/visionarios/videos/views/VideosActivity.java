package com.coppel.rhconecta.dev.visionarios.videos.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.inicio.views.InicioActivity;
import com.coppel.rhconecta.dev.visionarios.utils.DownloadImagesTask;
import com.coppel.rhconecta.dev.visionarios.videos.adapters.AdapterVideos;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.Videos;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.presenters.VideosPresenter;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity implements Videos.View {

    Toolbar toolbar;
    Videos.Presenter presenter;
    AdapterVideos adapterVideos;
    ListView listContenido;
    Encuesta ultimaEncuesta;
    private SurveyInboxView surveyInboxView;
    DialogFragmentLoader dialogFragmentLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
        listContenido = (ListView) findViewById(R.id.listContenido);
        initializeToolBar();

        presenter = new VideosPresenter(this);
        presenter.getTextoLabel("cTitulo", "Visionarios", R.id.toolbar);

        presenter.getVideosLocal();
        presenter.getEncuestaLocal();

    }

    @Override
    public void showVideos(final ArrayList<Video> videos) {
        if (videos != null) {
            String urls[] = new String[videos.size()];
            for(int x=0;x<videos.size();x++) {
                urls[x]= videos.get(x).getImagen_video_preview();
            }
            new DownloadImagesTask(this,videos).execute(urls);
        }else{
            dialogFragmentLoader.close();
            Toast.makeText(getBaseContext(), "No hay videos disponibles", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dialogFragmentLoader != null) {
            dialogFragmentLoader.dismissAllowingStateLoss();
        }
    }

    @Override public void onDestroy() { super.onDestroy(); Runtime.getRuntime().gc(); }

    @Override
    public void cargarVideos(ArrayList<Video> videos, Bitmap[] imagenes) {
        if (videos != null) {
            adapterVideos = new AdapterVideos(getBaseContext(), videos, imagenes);
            listContenido.setAdapter(adapterVideos);
            listContenido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startVideosDetalle(videos.get(position));
                }
            });

        }
        dialogFragmentLoader.close();
    }

    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método---
     *
     * **/
    @Override
    public void onBackPressed() { //DESHABILITA EL BOTON DE REGRESAR

        if (true) {

            finish();
            /**KokonutStudio
             * Se finaliza la activity, sin lanzar una nueva Activity de {@VideosDetalleActivity} */
            //Intent intentBack = new Intent(getBaseContext(),InicioActivity.class);
            //startActivity(intentBack);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public Context getContext() {
        return getBaseContext();
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

    void initializeToolBar() {
        surveyInboxView = (SurveyInboxView) findViewById(R.id.surveyInbox);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Visionarios");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    void startVideosDetalle(Video video) {
        Intent intent = new Intent(this, VideosDetalleActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);

    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (textView == R.id.toolbar) {
            toolbar.setTitle(text);
        } else {
            TextView textLabel = (TextView) findViewById(textView);
            textLabel.setText(text);
        }
    }
}
