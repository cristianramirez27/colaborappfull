package com.coppel.rhconecta.dev.visionarios.comunicados.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.visionarios.comunicados.adapters.AdapterComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.interfaces.Comunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.presenters.ComunicadosPresenter;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;

import java.util.ArrayList;

public class ComunicadosActivity extends AppCompatActivity implements Comunicados.View {

    Toolbar toolbar;
    Comunicados.Presenter presenter;

    ListView listContenido;
    AdapterComunicados adapterComunicados;
    Encuesta ultimaEncuesta;
    private SurveyInboxView surveyInboxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicados);
        initializeToolBar();

        presenter = new ComunicadosPresenter(this);
        presenter.getTextoLabel("cTitulo", "Comunicados", R.id.toolbar);
        listContenido = (ListView) findViewById(R.id.listContenido);

        presenter.getComunicadosLocal();
        presenter.getEncuestaLocal();

    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.getComunicadosLocal();
        presenter.getEncuestaLocal();

    }
    void initializeToolBar() {
        surveyInboxView = (SurveyInboxView) findViewById(R.id.surveyInbox);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Comunicados");
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
    public void showComunicados(final ArrayList<Comunicado> comunicados) {
        if (comunicados != null) {
            adapterComunicados = new AdapterComunicados(this.getBaseContext(), comunicados);
            listContenido.setAdapter(adapterComunicados);
            listContenido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startComunicadosDetalle(comunicados.get(position));
                }
            });
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

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (textView == R.id.toolbar) {
            toolbar.setTitle(text);
        } else {
            TextView textLabel = (TextView) findViewById(textView);
            textLabel.setText(text);
        }
    }

    void startComunicadosDetalle(Comunicado comunicado) {
        Intent intent = new Intent(this, ComunicadosDetalleActivity.class);
        intent.putExtra("comunicado", comunicado);
        startActivity(intent);
    }

}
