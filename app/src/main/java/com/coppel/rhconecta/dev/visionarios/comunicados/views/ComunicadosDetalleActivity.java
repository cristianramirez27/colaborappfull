package com.coppel.rhconecta.dev.visionarios.comunicados.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.visionarios.comunicados.interfaces.ComunicadosDetalle;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.presenters.ComunicadosDetallePresenter;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.utils.DownloadImageTask;

public class ComunicadosDetalleActivity extends AppCompatActivity implements ComunicadosDetalle.View {

    ComunicadosDetallePresenter presenter;
    private Comunicado comunicado;

    private TextView labelHeader;
    private TextView labelTitulo;
    private TextView labelContenido;
    private ImageView imgComunicado;
    private Toolbar toolbar;

    private SurveyInboxView surveyInboxView;

    Encuesta ultimaEncuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicados_detalle);
        presenter = new ComunicadosDetallePresenter(this);
        initializeToolBar();

        labelHeader = (TextView) findViewById(R.id.labelHeader);
        labelTitulo = (TextView) findViewById(R.id.labelTitulo);
        labelContenido = (TextView) findViewById(R.id.labelContenido);
        imgComunicado = (ImageView) findViewById(R.id.imgComunicado);
        comunicado = (Comunicado) getIntent().getSerializableExtra("comunicado");

        presenter.getTextoLabel("cTitulo", "Comunicado", R.id.toolbar);
        presenter.getTextoLabel("cHeader", "COMUNICADO", R.id.labelHeader);

        presenter.showComunicadosDetalle(comunicado);
        presenter.setComunicadoVisto(comunicado);
        presenter.getEncuestaLocal();

        /**Modificacion: 1 Noviembre 2018
         * Se quita el icono de encuestas**/
        surveyInboxView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showComunicadosDetalle(Comunicado comunicado) {
        if (comunicado != null) {
            labelHeader.setText("Comunicado");
            labelTitulo.setText(comunicado.getTitulo());
            labelContenido.setText(Html.fromHtml(comunicado.getContenido()));
            try {
                new DownloadImageTask((ImageView) findViewById(R.id.imgComunicado))
                        .execute(comunicado.getImagen_aviso_preview());
            } catch (Exception e) {
                Log.d("Comunicado", "No se ha podido descargar la imagen.");
            }

        } else {
            Log.d("Comunicado", "No se ha podido cargar el comunicado.");
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
        toolbar.setTitle("Comunicado");
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