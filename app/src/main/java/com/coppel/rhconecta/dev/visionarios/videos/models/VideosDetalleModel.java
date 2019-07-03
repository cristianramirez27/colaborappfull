package com.coppel.rhconecta.dev.visionarios.videos.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ComunicadoVisto_Callback;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableEncuestas;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.databases.TableVideos;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.firebase.VideosFirebase;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.coppel.rhconecta.dev.visionarios.videos.interfaces.VideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.CommunicatorGuardarLogAction;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.GuardarLogAction_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Request.JSON_GuardarLogAction;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Response.ResponseGuardarLogAction;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.VideoVisto_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.CommunicatorObtenerVideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.ObtenerVideosDetalle_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Request.JSON_ObtenerVideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.VideoVisto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class VideosDetalleModel implements VideosDetalle.Model, ObtenerVideosDetalle_Callback, GuardarLogAction_Callback, VideoVisto_Callback {
    private String TAG = "VideosDetalleModel";
    private VideosDetalle.Presenter presenter;
    private InternalDatabase idb;

    public VideosDetalleModel(VideosDetalle.Presenter presenter) {
        this.presenter = presenter;
    }


    public VideosDetalleModel(VideosDetalle.Presenter presenter, InternalDatabase idb) {
        this.presenter = presenter;
        this.idb = idb;
    }

    @Override
    public void getVideoDetalle(int idvideo) {
        TableUsuario tableUsuario = new TableUsuario(this.idb, false);
        Usuario usuario = tableUsuario.select("1");

        if (usuario != null) {
            JSON_ObtenerVideosDetalle jsonRequest = new JSON_ObtenerVideosDetalle(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), idvideo, usuario.getNumeroempleado());
            CommunicatorObtenerVideosDetalle communicatorObtenerVideosDetalle = new CommunicatorObtenerVideosDetalle();
            communicatorObtenerVideosDetalle.ObtenerApi(jsonRequest, VideosDetalleModel.this);
        } else {
            Log.d(TAG, "ERROR: Usuario no encontrado tn TableUsuario localmente");

        }

    }

    @Override
    public void onSuccessObtenerVideosDetalle(ResponseObtenerVideosDetalle result) {
        ResponseObtenerVideosDetalle videosDetalle = null;
        Log.d("ObtenerVideosDetalle", "SUCCESS!");

        try {
            videosDetalle = result;
            presenter.showVideoDetalle(videosDetalle);
            Log.d("ObtenerVideosDetalle", "Object SUCCESS!");

        } catch (Exception e) {
            Log.d("ObtenerVideosDetalle", e.getMessage());
        }
    }

    @Override
    public void onFailObtenerVideosDetalle(String result) {
        Log.d("ObtenerVideosDetalle", result);

    }

    @Override
    public void guardarLikeDislike(int idvideo, int tipolog) {
        TableUsuario tableUsuario = new TableUsuario(this.idb, false);
        Usuario usuario = tableUsuario.select("1");

        if (usuario != null) {
            JSON_GuardarLogAction jsonRequest = new JSON_GuardarLogAction(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), Integer.parseInt(usuario.getNumeroempleado()), idvideo, tipolog);

            CommunicatorGuardarLogAction communicatorGuardarLogAction = new CommunicatorGuardarLogAction();
            communicatorGuardarLogAction.ObtenerApi(jsonRequest, VideosDetalleModel.this);
            presenter.showLikeDislike(tipolog);
        } else {
            Log.d(TAG, "ERROR: Usuario no encontrado tn TableUsuario localmente");

        }
    }

    @Override
    public void guardarLog(int idvideo, int tipolog) {

        TableUsuario tableUsuario = new TableUsuario(this.idb, false);
        Usuario usuario = tableUsuario.select("1");

        if (usuario != null) {
            JSON_GuardarLogAction jsonRequest = new JSON_GuardarLogAction(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), Integer.parseInt(usuario.getNumeroempleado()), idvideo, tipolog);
            CommunicatorGuardarLogAction communicatorGuardarLogAction = new CommunicatorGuardarLogAction();
            communicatorGuardarLogAction.ObtenerApi(jsonRequest, VideosDetalleModel.this);
        } else {
            Log.d(TAG, "ERROR: Usuario no encontrado tn TableUsuario localmente");
        }

    }

    /** 6 Noviembre 2018
     * Modificacion: Se modificó la siguiente función para el cambio de guardado de los videos vistos en firebase.
     *
     * **/
    @Override
    public void setVideoVisto(Video video) {
        if(!video.isVisto()){

            TableUsuario tableUsuario = new TableUsuario(this.idb,false);
            Usuario usuario = tableUsuario.select("1");

            if(usuario!=null){
                TableVideos tableVideos = new TableVideos(this.idb,false);
                //video.setVisto(true);
                //tableVideos.update(video);

                if(video.getOpc_visto() != 1 )
                {
                    video.setVisto(true);
                    video.setOpc_visto(1);
                    tableVideos.update(video);

                    JSON_ObtenerVideos jsonRequest = new JSON_ObtenerVideos(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), usuario.getNumeroempleado(), video.getIdvideos(), video.getVistas());
                    VideoVisto VideoVisto = new VideoVisto();
                    VideoVisto.ObtenerApi(jsonRequest,VideosDetalleModel.this);
                }

                tableVideos.closeDB();

                /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE);
                ArrayList<Video> videosLocales = tableVideos.select();
                ArrayList<VideosFirebase> videosFirebasesUpdate = new ArrayList<VideosFirebase>();
                for (int i = 0; i < videosLocales.size(); i++) {
                    Video obj = videosLocales.get(i);
                    if(obj.isVisto()){
                        videosFirebasesUpdate.add(new VideosFirebase(obj.getIdvideos()+"",0));
                    }else{
                        videosFirebasesUpdate.add(new VideosFirebase(obj.getIdvideos()+"",1));
                    }
                }
                tableVideos.closeDB();

                dbref.child(usuario.getNumeroempleado()).child("videos").setValue(videosFirebasesUpdate).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FBDATABASE",e.getMessage());
                    }
                });*/
                Log.d("SetVisto","Video visto id:"+video.getIdvideos());
            }else{
                Log.d(TAG,"ERROR setvisto: Usuario no encontrado tn TableUsuario localmente");

            }
        }
    }

    @Override
    public void onSuccessGuardarLogAction(ResponseGuardarLogAction result) {
        ResponseGuardarLogAction resultadoSave = null;
        try {
            resultadoSave = result;
            Log.d("GuardarLogAction", "Object SUCCESS!");
        } catch (Exception e) {
            Log.d("GuardarLogAction", e.getMessage());
        }
    }

    @Override
    public void onFailGuardarLogAction(String result) {
        Log.d("GuardarLogAction", result);
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
            dbref.child("modulos").child("pantallas").child("detalle_videos").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void VideoVistoMensaje(ArrayList<Video> result) {
        Log.d("VideoVistoMensaje","Prueba");
    }
}


