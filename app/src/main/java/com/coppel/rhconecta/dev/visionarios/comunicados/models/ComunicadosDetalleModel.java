package com.coppel.rhconecta.dev.visionarios.comunicados.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ComunicadoVisto;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ComunicadoVisto_Callback;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request.JSON_ObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.interfaces.ComunicadosDetalle;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableComunicados;
import com.coppel.rhconecta.dev.visionarios.databases.TableEncuestas;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.firebase.ComunicadosFirebase;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ComunicadosDetalleModel implements ComunicadosDetalle.Model, ComunicadoVisto_Callback {
    private String TAG ="ComunicadosDetalleModel";
    private ComunicadosDetalle.Presenter presenter;
    private InternalDatabase idb;

    public ComunicadosDetalleModel(ComunicadosDetalle.Presenter presenter,InternalDatabase idb) {
        this.presenter = presenter;
        this.idb=idb;
    }

    public ComunicadosDetalleModel(ComunicadosDetalle.Presenter presenter) {
        this.presenter = presenter;
    }

    /** 6 Noviembre 2018
     * Modificacion: Se modificó la siguiente función para el cambio de guardado de los comunicados vistos en firebase.
     *
     * **/
    @Override
    public void setComunicadoVisto(Comunicado comunicado) {

        TableUsuario tableUsuario = new TableUsuario(this.idb,false);
        Usuario usuario = tableUsuario.select("1");

        if(usuario!=null){
            TableComunicados tableComunicados = new TableComunicados(this.idb,false);

            if(comunicado.getopc_visto() != 1 )
            {
                comunicado.setVisto(true);
                comunicado.setopc_visto(1);
                tableComunicados.update(comunicado);

                JSON_ObtenerComunicados jsonRequest = new JSON_ObtenerComunicados(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), usuario.getNumeroempleado(), comunicado.getIdavisos());
                ComunicadoVisto ComunicadoVisto = new ComunicadoVisto();
                ComunicadoVisto.ObtenerApi(jsonRequest,ComunicadosDetalleModel.this);
            }

            tableComunicados.closeDB();
            /*
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE);
            ArrayList<Comunicado> comunicadosLocales = tableComunicados.select();
            ArrayList<ComunicadosFirebase> comunicadosFirebaseUpdate = new ArrayList<ComunicadosFirebase>();
            for (int i = 0; i < comunicadosLocales.size(); i++) {
                Comunicado obj = comunicadosLocales.get(i);
                if(obj.isVisto()){
                    comunicadosFirebaseUpdate.add(new ComunicadosFirebase(obj.getIdavisos()+"",0));
                }else{
                    comunicadosFirebaseUpdate.add(new ComunicadosFirebase(obj.getIdavisos()+"",1));
                }
            }
            tableComunicados.closeDB();

            dbref.child(usuario.getNumeroempleado()).child("comunicados").setValue(comunicadosFirebaseUpdate).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("FBDATABASE",e.getMessage());
                }
            });*/
            Log.d("SetVisto","Comunicado visto id:"+comunicado.getIdavisos());
        }else{
            Log.d(TAG,"ERROR setVito: Usuario no encontrado tn TableUsuario localmente");
        }
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
    public void getTextoLabel(final String textNode, final String defaultText, final int textView) {
        try{
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("pantallas").child("detalle_comunicados").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
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

    @Override
    public void ComunicadoVistoMensaje(ArrayList<Comunicado> result)
    {
        Log.d("ComunicadoVistoMensaje","Prueba");
    }
}
