package com.coppel.rhconecta.dev.visionarios.encuestas.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.visionarios.encuestas.adapters.AdapterRespuestas;
import com.coppel.rhconecta.dev.visionarios.encuestas.interfaces.Encuestas;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;
import com.coppel.rhconecta.dev.visionarios.encuestas.presenters.EncuestasPresenter;
import com.coppel.rhconecta.dev.visionarios.utils.DialogCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EncuestaActivity extends AppCompatActivity implements Encuestas.View {
    private ProgressDialog progressDialog;
    private DialogCustom dialogCustom;
    private EncuestasPresenter presenter;
    private Toolbar toolbar;
    private ArrayList<Pregunta> preguntas;
    private AdapterRespuestas adapterRespuestas;
    private TextView labelPreguntaActual;
    private TextView labelEncuesta;
    private TextView labelEncuestaDesc;
    private TextView labelPregunta;
    private ListView listPreguntas;
    private TextView labelError;
    private Button btnSiguiente;
    private Button btnEnviarEncuesta;
    private ProgressBar progressEncuesta;
    private int progress;
    private int idxPregunta;
    private Encuesta encuesta;
    private int currentRespuestaSelected = -1;
    private Map<String, String> diccionario = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        encuesta = (Encuesta) getIntent().getSerializableExtra("encuesta");
        presenter = new EncuestasPresenter(this);
        labelPreguntaActual = (TextView) findViewById(R.id.labelPreguntaActual);
        labelEncuesta = (TextView) findViewById(R.id.labelEncuesta);
        labelEncuestaDesc = (TextView) findViewById(R.id.labelEncuestaDesc);
        labelPregunta = (TextView) findViewById(R.id.labelPregunta);
        listPreguntas = (ListView) findViewById(R.id.listPreguntas);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnEnviarEncuesta = (Button) findViewById(R.id.btnEnviarEncuesta);
        progressEncuesta = (ProgressBar) findViewById(R.id.progressEncuesta);
        labelError = (TextView) findViewById(R.id.labelError);
        initializeToolBar();
        presenter.getTextoLabel("cTitulo", "encuesta", R.id.toolbar);
        presenter.getTextoLabel("btnEnviar", "enviar encuesta", R.id.btnEnviarEncuesta);
        presenter.getTextoLabel("btnSiguiente", "Siguiente", R.id.btnSiguiente);
        presenter.getTextoLabelError("errorInternet", "sin conexión a internet", R.id.labelError);
        presenter.getTextoLabelError("errorSeleccion", "Necesitas seleccionar una opción", 0);
        presenter.getTextoLabelError("errorServidor", "Lo sentimos, tenemos un problema en nuestros servidores, intente mas tarde", 0);
        presenter.getTextoLabelError("errorEncuestaEnviada", "Ya has llenado la encuesta previamente", 0);


        presenter.getPreguntas(encuesta.getIdencuestas());


        progressDialog = ProgressDialog.show(this, "", "", true, false);
    }

    @Override
    public void onBackPressed() { //DESHABILITA EL BOTON DE REGRESAR

        if (true) {
            //TODO
        } else {
            super.onBackPressed();
        }

    }

    void initializeToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Encuesta");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showPreguntas(ArrayList<Pregunta> preguntas) {

        progressDialog.dismiss();

        if (preguntas != null) {
            labelEncuesta.setText("Encuesta: " + encuesta.getNombre());
            labelEncuestaDesc.setText("Queremos conocer tus hábitos respecto a " + encuesta.getNombre() + ", por ello, responde las siguientes preguntas y ayúdanos a mejorar.");

            this.preguntas = preguntas;
            presenter.showPregunta(0);
        } else {
            labelEncuesta.setText("");
            labelPreguntaActual.setText(" ");
            labelPregunta.setText(" ");
            labelEncuestaDesc.setText(" ");
            progressEncuesta.setProgress(0);
            btnSiguiente.setVisibility(View.INVISIBLE);
            labelError.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), labelError.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setCheckRespuesta(int idx) {

        preguntas.get(this.idxPregunta).resetRespuestas();
        preguntas.get(this.idxPregunta).getRespuestas().get(idx).setSeleccionado(true);
        currentRespuestaSelected = idx;
    }

    @Override
    public void showPregunta(final int idxPregunta) {
        currentRespuestaSelected = -1;
        this.idxPregunta = idxPregunta;
        Pregunta p = preguntas.get(idxPregunta);
        labelPreguntaActual.setText("pregunta " + (idxPregunta + 1) + " de " + preguntas.size());
        adapterRespuestas = new AdapterRespuestas(getBaseContext(), p.getRespuestas());
        listPreguntas.setAdapter(adapterRespuestas);
        try {
            progress = (int) (((idxPregunta + 1) * 100) / preguntas.size());
        } catch (Exception e) {
            progress = 0;
        }
        progressEncuesta.setProgress(progress);
        labelPregunta.setText(p.getContenido());

        if (idxPregunta >= (preguntas.size() - 1)) {
            btnEnviarEncuesta.setVisibility(View.VISIBLE);
            btnSiguiente.setVisibility(View.GONE);
            btnEnviarEncuesta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentRespuestaSelected == -1) {
                        Toast.makeText(getBaseContext(), diccionario.get("errorSeleccion").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.guardarEncuesta(preguntas);
                    }
                }
            });
        } else {
            btnSiguiente.setVisibility(View.VISIBLE);
            btnEnviarEncuesta.setVisibility(View.GONE);
            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentRespuestaSelected == -1) {
                        Toast.makeText(getBaseContext(), diccionario.get("errorSeleccion").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.showPregunta((idxPregunta + 1));
                    }
                }
            });

        }
    }

    @Override
    public void showEncuestaGuardada(String msg) {
        this.presenter.setEncuestaVisto(encuesta);
        dialogCustom = new DialogCustom(EncuestaActivity.this, R.layout.dialog_custom_encuesta_success);
        dialogCustom.showDialogActionNoButton("Tu encuesta se envió con éxito", "¡Gracias!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustom.CloseDialog();
                finish();
            }
        });
    }

    @Override
    public void showEncuestaNoGuardada(String msg) {
        this.presenter.setEncuestaVisto(encuesta);
        dialogCustom = new DialogCustom(EncuestaActivity.this, R.layout.dialog_custom);
        dialogCustom.showDialogActionNoButton(diccionario.get("errorEncuestaEnviada").toString(), "¡Gracias!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustom.CloseDialog();

            }
        });
    }

    @Override
    public void showEncuestaNoGuardadaError() {
        dialogCustom = new DialogCustom(EncuestaActivity.this, R.layout.dialog_custom);
        dialogCustom.showDialogActionNoButton(diccionario.get("errorServidor").toString(), " ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustom.CloseDialog();

            }
        });
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (textView == R.id.toolbar) {
            toolbar.setTitle(text);
        } else {
            Button textLabel = (Button) findViewById(textView);
            if (textLabel != null) {
                textLabel.setText(text);
            }
        }
    }

    @Override
    public void ShowTextoDiccionarioError(String text, int textView) {
        TextView textLabel = (TextView) findViewById(textView);
        if (textLabel != null) {
            textLabel.setText(text);
        }
    }

    @Override
    public void guardarTextoDiccionario(String key, String text) {
        if (diccionario.containsKey(key)) {
            Log.d("DICCIONARIO", key + " reemplazado");
            diccionario.remove(key);
            diccionario.put(key, text);
        } else {
            Log.d("DICCIONARIO", key + " guardado");
            diccionario.put(key, text);
        }
    }
}
