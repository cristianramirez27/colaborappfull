package com.coppel.rhconecta.dev.visionarios.videos.models;

import android.util.Log;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableEncuestas;
import com.coppel.rhconecta.dev.visionarios.databases.TableVideos;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.Videos;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.CommunicatorObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.ObtenerVideos_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response.ResponseObtenerVideos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class VideosModel implements Videos.Model,ObtenerVideos_Callback {

    private Videos.Presenter presenter;
    private InternalDatabase idb;
    public VideosModel(Videos.Presenter presenter) {
        this.presenter = presenter;
    }

    public VideosModel(Videos.Presenter presenter, InternalDatabase idb) {
        this.presenter = presenter;
        this.idb = idb;
    }

    @Override
    public void getVideosLocal() {
        if(presenter != null){
            TableVideos tableVideos = new TableVideos(this.idb,false);
            ArrayList<Video> videos = tableVideos.select();
            if(videos != null){
                for (int i = 0; i < videos.size(); i++) {
                    if(videos.get(i).getEstatus()==0){
                        videos.remove(i);
                    }
                }
            }
            tableVideos.closeDB();
            presenter.showVideos(videos);
        }

    }

    @Override
    public void getVideos() {
        if (presenter != null) {
            JSON_ObtenerVideos jsonRequest = new JSON_ObtenerVideos(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY));
            CommunicatorObtenerVideos communicatorObtenerVideos = new CommunicatorObtenerVideos();
            communicatorObtenerVideos.ObtenerApi(jsonRequest,VideosModel.this);
        }
    }

    @Override
    public void onSuccessObtenerVideos(ResponseObtenerVideos result) {
        Log.d("ObtenerVideos","SUCCESS!");

        try{
            presenter.showVideos(result.videos);
            Log.d("ObtenerVideos","Object SUCCESS!");

        }catch (Exception e){
            Log.d("ObtenerVideos",e.getMessage());
        }

    }

    @Override
    public void onFailObtenerVideos(String result) {
        Log.d("ObtenerVideos",result);

    }
    @Override
    public void getEncuestaLocal() {
        TableEncuestas tableEncuestas = new TableEncuestas(this.idb,false);
        ArrayList<Encuesta> encuestas = tableEncuestas.select();
        Encuesta ultimaEncuesta = null;
        if(encuestas != null) {
            if (encuestas.size() > 0) {
                ultimaEncuesta = encuestas.get(0);
            }
        }
        presenter.showUltimaEncuesta(ultimaEncuesta);
    }
    @Override
    public void getTextoLabel(final String textNode,final String defaultText,final int textView) {
        try{
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("pantallas").child("videos").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String str = dataSnapshot.getValue(String.class);
                        presenter.ShowTextoDiccionario(str,textView);
                    }else{ //si no existe nodo
                        presenter.ShowTextoDiccionario(defaultText,textView);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("DICCIONARIO","error");
                    presenter.ShowTextoDiccionario(defaultText,textView);
                }
            });

        }catch (Exception e){
            presenter.ShowTextoDiccionario(defaultText,textView);

            Log.d("DICCIONARIO",e.getMessage());
        }
    }
}
