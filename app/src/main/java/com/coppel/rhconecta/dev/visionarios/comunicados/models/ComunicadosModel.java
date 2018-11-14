package com.coppel.rhconecta.dev.visionarios.comunicados.models;

import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.CommunicatorObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ObtenerComunicados_Callback;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request.JSON_ObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.interfaces.Comunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableComunicados;
import com.coppel.rhconecta.dev.visionarios.databases.TableEncuestas;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComunicadosModel implements Comunicados.Model, ObtenerComunicados_Callback {

    private Comunicados.Presenter presenter;
    private InternalDatabase idb;

    public ComunicadosModel(Comunicados.Presenter presenter) {
        this.presenter = presenter;
    }

    public ComunicadosModel(Comunicados.Presenter presenter, InternalDatabase idb) {
        this.presenter = presenter;
        this.idb = idb;
    }

    @Override
    public void getComunicadosLocal() {
        if (presenter != null) {

            TableComunicados tableComunicados = new TableComunicados(this.idb, false);
            ArrayList<Comunicado> comunicados = tableComunicados.select();
            if (comunicados != null) {
                for (int i = 0; i < comunicados.size(); i++) {
                    if (comunicados.get(i).getEstatus() == 0) {
                        comunicados.remove(i);
                    }
                }
            }

            tableComunicados.closeDB();
            presenter.showComunicados(comunicados);
        }
    }

    @Override
    public void getEncuestaLocal() {
        TableEncuestas tableEncuestas = new TableEncuestas(this.idb, false);
        ArrayList<Encuesta> encuestas = tableEncuestas.select();
        Encuesta ultimaEncuesta = null;
        if (encuestas != null) {
            if (encuestas.size() > 0) {
                ultimaEncuesta = encuestas.get(0);
            }
        }
        presenter.showUltimaEncuesta(ultimaEncuesta);
    }

    @Override
    public void getTextoLabel(final String textNode, final String defaultText, final int textView) {
        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("pantallas").child("comunicados").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String str = dataSnapshot.getValue(String.class);
                        presenter.ShowTextoDiccionario(str, textView);
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
    public void getComunicados() {
        if (presenter != null) {

            JSON_ObtenerComunicados jsonRequest = new JSON_ObtenerComunicados(ConstantesGlobales.APLICACION_KEY);
            CommunicatorObtenerComunicados communicatorObtenerComunicados = new CommunicatorObtenerComunicados();
            communicatorObtenerComunicados.ObtenerApi(jsonRequest, ComunicadosModel.this);


        }
    }


    @Override
    public void onSuccessObtenerComunicados(ArrayList<Comunicado> result) {
        Log.d("ObtenerComunicados", "Array SUCCESS!");

        try {
            Log.d("ObtenerComunicados", "Array SUCCESS!");
            ArrayList<Comunicado> comunicadosResponse = new ArrayList<Comunicado>();
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).getEstatus() == 1) {
                    comunicadosResponse.add(result.get(i));
                }
            }
            presenter.showComunicados(comunicadosResponse);
        } catch (Exception e) {
            Log.d("ObtenerComunicados", e.getMessage());
        }
    }

    @Override
    public void onFailObtenerComunicados(String result) {
        Log.d("ObtenerComunicados", "FAIL! " + result);

    }
}
