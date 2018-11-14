package com.coppel.rhconecta.dev.visionarios.encuestas.models;

import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableEncuestas;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.encuestas.interfaces.Encuestas;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Respuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.presenters.EncuestasPresenter;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.CommunicatorGuardarEncuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.GuardarEncuesta_Callback;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Request.DataForm;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Request.JSON_GuardarEncuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Response.ResponseGuardarEncuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.CommunicatorObtenerEncuestasPreguntas;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.ObtenerEncuestasPreguntas_Callback;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Request.JSON_ObtenerEncuestasPreguntas;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EncuestasModel implements Encuestas.Model, ObtenerEncuestasPreguntas_Callback, GuardarEncuesta_Callback {

    private String TAG = "EncuestasModel";
    private EncuestasPresenter presenter;
    private InternalDatabase idb;

    public EncuestasModel(EncuestasPresenter presenter, InternalDatabase idb) {
        this.presenter = presenter;
        this.idb = idb;
    }

    public EncuestasModel(EncuestasPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getPreguntas(int idencuesta) {

        JSON_ObtenerEncuestasPreguntas jsonRequest = new JSON_ObtenerEncuestasPreguntas(ConstantesGlobales.APLICACION_KEY, idencuesta);
        CommunicatorObtenerEncuestasPreguntas communicatorObtenerEncuestasPreguntas = new CommunicatorObtenerEncuestasPreguntas();
        communicatorObtenerEncuestasPreguntas.ObtenerApi(jsonRequest, EncuestasModel.this);

    }

    @Override
    public void guardarEncuesta(ArrayList<Pregunta> preguntas) {
        ArrayList<DataForm> dataForm = new ArrayList<DataForm>();
        for (int i = 0; i < preguntas.size(); i++) {
            ArrayList<Respuesta> respuestas = preguntas.get(i).getRespuestas();
            for (int j = 0; j < respuestas.size(); j++) {
                Respuesta resp = respuestas.get(j);
                if (resp.isSeleccionado()) {
                    dataForm.add(new DataForm(resp.getIdrespuesta(), "preg_" + preguntas.get(i).getIdcuestionario() + "_" + preguntas.get(i).getIdpregunta() + "_" + resp.getIdrespuesta()));
                }
            }
        }

        TableUsuario tableUsuario = new TableUsuario(this.idb, false);
        Usuario usuario = tableUsuario.select("1");

        if (usuario != null) {
            JSON_GuardarEncuesta jsonRequest = new JSON_GuardarEncuesta(usuario.getNumeroempleado(), dataForm, ConstantesGlobales.APLICACION_KEY);
            CommunicatorGuardarEncuesta communicatorGuardarEncuesta = new CommunicatorGuardarEncuesta();
            communicatorGuardarEncuesta.ObtenerApi(jsonRequest, EncuestasModel.this);
        } else {
            presenter.showEncuestaNoGuardadaError();
            Log.d(TAG, "ERROR: Usuario no encontrado tn TableUsuario localmente");
        }


    }

    @Override
    public void setEncuestaVisto(Encuesta encuesta) {
        TableEncuestas tableEncuestas = new TableEncuestas(this.idb, false);
        encuesta.setVisto(true);
        tableEncuestas.update(encuesta);
        tableEncuestas.closeDB();
    }

    @Override
    public void onSuccessObtenerEncuestasPreguntas(ArrayList<Pregunta> result) {
        Log.d("ObtenerEncuesta", "SUCCESS!");

        if (result != null) {
            try {
                Log.d("ObtenerEncuesta", "Array SUCCESS!");
                for (int i = 0; i < result.size(); i++) {
                    result.get(i).decodingRespuestas();
                }
                presenter.showPreguntas(result);
            } catch (Exception e) {
                Log.d("ObtenerEncuesta", e.getMessage());
                presenter.showPreguntas(null);

            }
        } else {
            Log.d("ObtenerEncuesta", "Array error!");
            presenter.showPreguntas(null);

        }

    }

    @Override
    public void onFailObtenerEncuestasPreguntas(String result) {
        Log.d("ObtenerEncuesta", "FAIL! " + result);
        presenter.showPreguntas(null);

    }

    @Override
    public void onSuccessGuardarEncuesta(ResponseGuardarEncuesta result) {
        Log.d("GuardarEncuesta", "SUCCESS!");

        if (result != null) {
            try {
                if (result.isSuccess()) {
                    Log.d("GuardarEncuesta", "Response SUCCESS!");
                    ;
                    presenter.showEncuestaGuardada(result.getMessage());
                } else {
                    Log.d("GuardarEncuesta", "Response Fail!");
                    presenter.showEncuestaNoGuardada(result.getMessage());
                }
            } catch (Exception e) {

                Log.d("GuardarEncuesta", e.getMessage());
                presenter.showEncuestaNoGuardadaError();

            }
        } else {
            presenter.showEncuestaNoGuardadaError();
            Log.d("GuardarEncuesta", "Object error!");

        }
    }

    @Override
    public void onFailGuardarEncuesta(String result) {
        Log.d("GuardarEncuesta", "FAIL! " + result);
        presenter.showEncuestaNoGuardadaError();
    }

    @Override
    public void getTextoLabel(final String textNode, final String defaultText, final int textView) {
        try {
            guardarTextoDiccionario(textNode, defaultText);

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("pantallas").child("encuesta").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String str = dataSnapshot.getValue(String.class);
                        presenter.ShowTextoDiccionario(str, textView);
                        guardarTextoDiccionario(textNode, str);

                    } else { //si no existe nodo
                        presenter.ShowTextoDiccionario(defaultText, textView);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("DICCIONARIO", "error");
                    presenter.ShowTextoDiccionario(defaultText, textView);
                }
            });

        } catch (Exception e) {
            presenter.ShowTextoDiccionario(defaultText, textView);

            Log.d("DICCIONARIO", e.getMessage());
        }
    }

    @Override
    public void getTextoLabelError(final String textNode, final String defaultText, final int textView) {
        try {
            presenter.ShowTextoDiccionarioError(defaultText, textView);
            guardarTextoDiccionario(textNode, defaultText);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("msj_error").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String str = dataSnapshot.getValue(String.class);
                        Log.d("DICCIONARIO", "Texto Obtenido:" + str);
                        presenter.ShowTextoDiccionarioError(str, textView);
                        guardarTextoDiccionario(textNode, str);

                    } else { //si no existe nodo
                        Log.d("DICCIONARIO", "no existe:");
                        presenter.ShowTextoDiccionarioError(defaultText, textView);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("DICCIONARIO", "error");
                    presenter.ShowTextoDiccionarioError(defaultText, textView);
                }
            });

        } catch (Exception e) {
            Log.d("DICCIONARIO", e.getMessage());

            presenter.ShowTextoDiccionarioError(defaultText, textView);

        }
    }


    public void guardarTextoDiccionario(String text, String key) {

        presenter.guardarTextoDiccionario(key, text);
    }
}
