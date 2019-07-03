package com.coppel.rhconecta.dev.visionarios.inicio.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.CommunicatorObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ObtenerComunicados_Callback;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request.JSON_ObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableComunicados;
import com.coppel.rhconecta.dev.visionarios.databases.TableEncuestas;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.databases.TableVideos;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.CommunicatorObtenerEncuestas;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.ObtenerEncuestas_Callback;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Request.JSON_ObtenerEncuestas;
import com.coppel.rhconecta.dev.visionarios.firebase.ComunicadosFirebase;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.firebase.VideosFirebase;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.CommunicatorGuardarLogin;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.GuardarLogin_Callback;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Request.JSON_GuardarLogin;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response.ResponseGuardarLogin;
import com.coppel.rhconecta.dev.visionarios.inicio.interfaces.Inicio;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.CommunicatorObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.ObtenerVideos_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response.ResponseObtenerVideos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class InicioModel implements Inicio.Model,ObtenerEncuestas_Callback, ObtenerComunicados_Callback, ObtenerVideos_Callback,GuardarLogin_Callback {

    private String TAG ="InicioModel";
    private Inicio.Presenter presenter;
    private InternalDatabase idb;

    public InicioModel(Inicio.Presenter presenter) {
        this.presenter = presenter;
    }

    public InicioModel(Inicio.Presenter presenter, InternalDatabase idb) {
        this.presenter = presenter;
        this.idb = idb;
    }

    /**
     * 29 Octubre 2018
     * Modificacion: Se agrega validacion de {comunicados} vacíos---
     *
     * **/
    @Override
    public void getBadgesComunicados() {
        TableComunicados tableComunicados = new TableComunicados(this.idb,false);
        ArrayList<Comunicado> comunicados= tableComunicados.select();
        tableComunicados.closeDB();
        ArrayList<Comunicado> comunicadosLanding = new ArrayList<Comunicado>();

        int nuevos=0;
        if(comunicados != null){
            for (int i = 0; i < comunicados.size() ; i++) {
                if(!comunicados.get(i).isVisto() && comunicados.get(i).getEstatus()==1){
                    nuevos++;
                }
                if(comunicados.get(i).getLanding_visible()==1 && comunicados.get(i).getEstatus()==1){
                    comunicadosLanding.add(comunicados.get(i));
                }
            }
        }

        this.presenter.showBangesComunicados(nuevos);
        this.presenter.showComunicadosLanding(comunicadosLanding);

    }

    /**
     * 29 Octubre 2018
     * Modificacion: Se agrega validacion de {videos} vacíos---
     *
     * **/
    @Override
    public void getBadgesVideos() {
        TableVideos tableVideos = new TableVideos(this.idb,false);
        ArrayList<Video> videos= tableVideos.select();
        tableVideos.closeDB();
        ArrayList<Video> videosLanding = new ArrayList<Video>();

        int nuevos=0;
        if(videos != null){
            for (int i = 0; i < videos.size() ; i++) {
                if(!videos.get(i).isVisto() && videos.get(i).getEstatus()==1){
                    nuevos++;
                }
                if(videos.get(i).getLanding_visible()==1 && videos.get(i).getEstatus()==1){
                    videosLanding.add(videos.get(i));
                }
            }
        }

        this.presenter.showBangesVideos(nuevos);
        this.presenter.showVideosLanding(videosLanding);

    }

    @Override
    public void getComunicados() {
        if(presenter != null){
            TableUsuario tableUsuario = new TableUsuario(idb,false);
            Usuario usuario = tableUsuario.select("1");
            tableUsuario.closeDB();
            JSON_ObtenerComunicados jsonRequest = new JSON_ObtenerComunicados(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), usuario.getNumeroempleado());
            CommunicatorObtenerComunicados communicatorObtenerComunicados = new CommunicatorObtenerComunicados();
            communicatorObtenerComunicados.ObtenerApi(jsonRequest,InicioModel.this);
        }
    }

    @Override
    public void getVideos() {
        if(presenter != null){
            TableUsuario tableUsuario = new TableUsuario(idb,false);
            Usuario usuario = tableUsuario.select("1");
            tableUsuario.closeDB();
            JSON_ObtenerVideos jsonRequest = new JSON_ObtenerVideos(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY), usuario.getNumeroempleado());
            CommunicatorObtenerVideos communicatorObtenerVideos = new CommunicatorObtenerVideos();
            communicatorObtenerVideos.ObtenerApi(jsonRequest,InicioModel.this);
        }
    }

    @Override
    public void getUltimaEncuesta() {
        TableUsuario tableUsuario = new TableUsuario(idb,false);
        Usuario usuario = tableUsuario.select("1");
        tableUsuario.closeDB();
        JSON_ObtenerEncuestas jsonRequest = new JSON_ObtenerEncuestas(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY),usuario.getNumeroempleado());
        CommunicatorObtenerEncuestas communicatorObtenerEncuestas = new CommunicatorObtenerEncuestas();
        communicatorObtenerEncuestas.ObtenerApi(jsonRequest,InicioModel.this);
    }

    @Override
    public void getTextoLabel(String textNode, final String defaultText, final int textView) {

        try{
            guardarTextoDiccionario(textNode,defaultText);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("pantallas").child("home").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void getTextoLabelError(final String textNode, final String defaultText) {

        try{
            guardarTextoDiccionario(textNode,defaultText);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("modulos").child("msj_error").child(textNode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String str = dataSnapshot.getValue(String.class);
                        guardarTextoDiccionario(textNode,str);

                    }else{ //si no existe nodo
                        guardarTextoDiccionario(textNode,defaultText);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("DICCIONARIO","error");
                    guardarTextoDiccionario(textNode,defaultText);
                }
            });

        }catch (Exception e){
            guardarTextoDiccionario(textNode,defaultText);

            Log.d("DICCIONARIO",e.getMessage());
        }
    }


    @Override
    public void onSuccessObtenerEncuestas(ArrayList<Encuesta> result) {
        Log.d("ObtenerEncuestas","SUCCESS!");
        Encuesta ultimaEncuesta =null;
        if(result!=null){
            try{
                Log.d("ObtenerEncuestas","Array buscando ultimaEncuesta!");
                for (int i = 0; i < result.size(); i++) {
                    if(result.get(i).getEstatus()==1){
                        Log.d("ObtenerEncuestas","Array ultimaEncuesta activa success!");
                        ultimaEncuesta=result.get(i);
                        break;
                    }
                }
                TableEncuestas tableEncuestas = new TableEncuestas(this.idb,true);
                tableEncuestas.insertIfNotExist(ultimaEncuesta);
                tableEncuestas.closeDB();
            }catch (Exception e){
                Log.d("ObtenerEncuestas",e.getMessage());

                this.presenter.showError();
            }
        }else{
            Log.d("ObtenerEncuestas","Array error!");
            this.presenter.showError();

        }

        this.presenter.showUltimaEncuesta(ultimaEncuesta);

    }

    @Override
    public void onFailObtenerEncuestas(String result) {
        Log.d("ObtenerEncuestas","FAIL! "+result);
        TableEncuestas tableEncuestas = new TableEncuestas(this.idb,true);
        tableEncuestas.closeDB();
        this.presenter.showError();

    }


    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método onSuccessObtenerComunicados---
     *
     *
     * 6 Noviembre 2018
     * Modificacion: Se modificaron las siguientes funciones para la corrección del tipo de dato guardado
     * en firebase para los iconos de badges:
     * **/
    @Override
    public void onSuccessObtenerComunicados(final ArrayList<Comunicado> result) {
        Log.d("ObtenerComunicados", "SUCCESS!");
        TableUsuario tableUsuario = new TableUsuario(this.idb,false);
        final Usuario usuario = tableUsuario.select("1");
        final String numeroEmpleado;
        if(usuario!=null) {
            numeroEmpleado=usuario.getNumeroempleado();
        }else{
            Log.d(TAG,"ERROR: Usuario no encontrado en TableUsuario localmente");
            numeroEmpleado="0";
        }

        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE);
            Log.d("ObtenerComunicados", "Array SUCCESS!");

            final TableComunicados tableComunicados = new TableComunicados(this.idb,false);

            /*ELIMINA LOS COMUNICADOS LOCALES QUE NO ESTEN EN LOS NUEVOS*/
            ArrayList<Comunicado> comunicadosLocalesExistentes= tableComunicados.select();
            if(comunicadosLocalesExistentes != null){
                for (int i=0;i<comunicadosLocalesExistentes.size();i++){
                    Comunicado obj = comunicadosLocalesExistentes.get(i);
                    boolean existe=false;
                    for (int j = 0; j < result.size(); j++) {
                        Comunicado obj2 = result.get(j);
                        if(obj.getIdavisos()==obj2.getIdavisos()){
                            existe=true;
                        }
                    }
                    if(!existe){
                        tableComunicados.delete(obj.getIdavisos()+"");
                    }
                }
            }

            //inserta el registro localmente si no existe y si existe lo actualiza, manteniendo el estado de "visto"
            for (int i = 0; i < result.size(); i++) {
                Comunicado obj = result.get(i);
                tableComunicados.insertIfNotExist(obj);
            }

            getBadgesComunicados();

            //guarda los registros en firebase para mantener los registros de "visto"
            /*dbref.child(numeroEmpleado).child("comunicados").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) { //lee firebase

                    TableComunicados tableComunicados2 = new TableComunicados(idb,false);


                    if(dataSnapshot.exists()){ // si ya existe nodo comunicados
                        Log.d("FBDATABASE", "Ya existe nodo comunicados del usuario");
                        GenericTypeIndicator<ArrayList<ComunicadosFirebase>> t = new GenericTypeIndicator<ArrayList<ComunicadosFirebase>>(){};
                        ArrayList<ComunicadosFirebase> comunicadosFirebasesExistente =  dataSnapshot.getValue(t);

                        for (int i = 0; i <comunicadosFirebasesExistente.size(); i++) {
                            Comunicado obj =  tableComunicados2.select(comunicadosFirebasesExistente.get(i).id);
                            if(obj != null){

                                if(comunicadosFirebasesExistente.get(i).showed==0){
                                    obj.setVisto(true);
                                }else{
                                    obj.setVisto(false);
                                }


                                tableComunicados2.update(obj);
                            }
                        }

                        ArrayList<Comunicado> comunicadosLocales= tableComunicados2.select();
                        tableComunicados2.closeDB();

                        ArrayList<ComunicadosFirebase> comunicadosFirebaseExistente = new ArrayList<ComunicadosFirebase>();
                        if(comunicadosLocales !=null){
                            for (int i = 0; i < comunicadosLocales.size(); i++) {
                                Comunicado obj = comunicadosLocales.get(i);
                                if(obj.isVisto()){
                                    comunicadosFirebaseExistente.add(new ComunicadosFirebase(obj.getIdavisos()+"",0));
                                }else{
                                    comunicadosFirebaseExistente.add(new ComunicadosFirebase(obj.getIdavisos()+"",1));

                                }
                            }
                        }

                        Log.d("FBDATABASE", "Si ya existe nodo comunicados, lo reemplaza.");
                        dbref.child(numeroEmpleado).child("comunicados").setValue(comunicadosFirebaseExistente).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("FBDATABASE",e.getMessage());
                            }
                        });



                    }else{ //si no existe nodo

                        ArrayList<Comunicado> comunicadosLocales= tableComunicados.select();
                        tableComunicados.closeDB();

                        ArrayList<ComunicadosFirebase> comunicadosFirebaseInicial = new ArrayList<ComunicadosFirebase>();
                        if(comunicadosLocales !=null){
                            for (int i = 0; i < comunicadosLocales.size(); i++) {
                                Comunicado obj = comunicadosLocales.get(i);
                                if(obj.isVisto()){
                                    comunicadosFirebaseInicial.add(new ComunicadosFirebase(obj.getIdavisos()+"",0));
                                }else{
                                    comunicadosFirebaseInicial.add(new ComunicadosFirebase(obj.getIdavisos()+"",1));
                                }
                            }
                        }

                        Log.d("FBDATABASE", "No existe nodo comunicados, se crea.");
                        dbref.child(numeroEmpleado).child("comunicados").setValue(comunicadosFirebaseInicial).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("FBDATABASE",e.getMessage());
                            }
                        });

                    }

                    getBadgesComunicados();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });*/
        } catch (Exception e) {
            Log.d("ObtenerComunicados","SUCCESS FAIL: "+e.getMessage());
        }
    }

    @Override
    public void onFailObtenerComunicados(String result) {
        Log.d("ObtenerComunicados","FAIL! "+result);
    }


    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método onSuccessObtenerVideos---
     *
     * 6 Noviembre 2018
     * Modificacion: Se modificaron las siguientes funciones para la corrección del tipo de dato guardado
     * en firebase para los iconos de badges:
     * **/
    @Override
    public void onSuccessObtenerVideos(ResponseObtenerVideos result) {
        Log.d("ObtenerVideos", "SUCCESS!");

        TableUsuario tableUsuario = new TableUsuario(this.idb,false);
        final Usuario usuario = tableUsuario.select("1");
        final String numeroEmpleado;
        if(usuario!=null) {
            numeroEmpleado=usuario.getNumeroempleado();
        }else{
            Log.d(TAG,"ERROR: Usuario no encontrado en TableUsuario localmente");
            numeroEmpleado="0";
        }

        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE);
            Log.d("ObtenerVideos", "Array SUCCESS!");

            final TableVideos tableVideos = new TableVideos(this.idb,false);

            //elimina videos que ya no esten en los nuevos
            ArrayList<Video> videosLocalesExistentes= tableVideos.select();

            if(videosLocalesExistentes!= null){
                for (int i=0;i<videosLocalesExistentes.size();i++){
                    Video obj = videosLocalesExistentes.get(i);
                    boolean existe=false;
                    for (int j = 0; j < result.videos.size(); j++) {
                        Video obj2 = result.videos.get(j);
                        if(obj.getIdvideos()==obj2.getIdvideos()){
                            existe=true;
                        }
                    }

                    if(!existe){
                        tableVideos.delete(obj.getIdvideos()+"");
                    }

                }
            }


            //inserta el registro localmente si no existe y si existe lo actualiza, manteniendo el estado de "visto"

            for (int i = 0; i < result.videos.size(); i++) {
                Video obj = result.videos.get(i);
                tableVideos.insertIfNotExist(obj);
            }

            getBadgesVideos();

            //guarda los registros en firebase para mantener los registros de "visto"
           /* dbref.child(numeroEmpleado).child("videos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) { //lee firebase

                    TableVideos tableVideos1 = new TableVideos(idb,false);

                    if(dataSnapshot.exists()){ // si ya existe nodo comunicados
                        Log.d("FBDATABASE", "Ya existe nodo videos del usuario");
                        GenericTypeIndicator<ArrayList<VideosFirebase>> t = new GenericTypeIndicator<ArrayList<VideosFirebase>>(){};
                        ArrayList<VideosFirebase> videosFirebasesExistente =  dataSnapshot.getValue(t);

                        for (int i = 0; i <videosFirebasesExistente.size(); i++) {
                            Video obj =  tableVideos1.select(videosFirebasesExistente.get(i).id);
                            if(obj != null){

                                if(videosFirebasesExistente.get(i).showed==0){
                                    obj.setVisto(true);
                                }else{
                                    obj.setVisto(false);
                                }
                                tableVideos1.update(obj);
                            }
                        }

                        ArrayList<Video> videosLocales= tableVideos.select();
                        tableVideos.closeDB();
                        ArrayList<VideosFirebase> videosFirebaseInicial = new ArrayList<VideosFirebase>();
                        if(videosLocales !=null){
                            for (int i = 0; i < videosLocales.size(); i++) {
                                Video obj = videosLocales.get(i);

                                if(obj.isVisto()){
                                    videosFirebaseInicial.add(new VideosFirebase(obj.getIdvideos()+"",0));
                                }else{
                                    videosFirebaseInicial.add(new VideosFirebase(obj.getIdvideos()+"",1));
                                }
                            }
                        }

                        Log.d("FBDATABASE", "Si existe nodo videos, se actualzia.");
                        dbref.child(numeroEmpleado).child("videos").setValue(videosFirebaseInicial).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("FBDATABASE",e.getMessage());
                            }
                        });


                    }else{ //si no existe nodo

                        ArrayList<Video> videosLocales= tableVideos.select();
                        tableVideos.closeDB();
                        ArrayList<VideosFirebase> videosFirebaseInicial = new ArrayList<VideosFirebase>();
                        if(videosLocales !=null){
                            for (int i = 0; i < videosLocales.size(); i++) {
                                Video obj = videosLocales.get(i);
                                if(obj.isVisto()){
                                    videosFirebaseInicial.add(new VideosFirebase(obj.getIdvideos()+"",0));
                                }else{
                                    videosFirebaseInicial.add(new VideosFirebase(obj.getIdvideos()+"",1));
                                }
                            }
                        }

                        Log.d("FBDATABASE", "No existe nodo videos, se crea.");
                        dbref.child(numeroEmpleado).child("videos").setValue(videosFirebaseInicial).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("FBDATABASE",e.getMessage());
                            }
                        });

                    }

                    getBadgesVideos();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });*/



        } catch (Exception e) {
            Log.d("ObtenerVideos","SUCCESS FAIL: "+e.getMessage());
        }
    }

    @Override
    public void onFailObtenerVideos(String result) {
        Log.d("ObtenerVideos","FAIL");

    }

    public void guardarTextoDiccionario(String key, String text){
        this.presenter.guardarTextoDiccionario(key,text);
    }

    @Override
    public void guardarLogin() {


        // Usuario usuario = new Usuario(1,ConstantesGlobales.NUMERO_EMPLEADO,"Irving","Estrada","Apellido2",1,4029,2,"1990-01-01","2010-10-10","JEFE",1,"Culiacan");
        // TableUsuario tableUsuario = new TableUsuario(this.idb,true);
        // tableUsuario.insertIfNotExist(usuario);

        Usuario usuario;
        TableUsuario tableUsuario = new TableUsuario(this.idb,false);
        usuario=tableUsuario.select("1");
        if(usuario!=null) {
            JSON_GuardarLogin jsonRequest = new JSON_GuardarLogin(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.APLICACION_KEY),
                    usuario.getNumeroempleado(),
                    usuario.getNombre(),
                    usuario.getApellidop(),
                    usuario.getApellidom(),
                    usuario.getEstatus(),
                    usuario.getNumerocentro(),
                    usuario.getSexo(),
                    usuario.getFechanacimiento(),
                    usuario.getFechaalta(),
                    usuario.getDescripcionpuesto(),
                    usuario.getIdestado(),
                    usuario.getEstado());
            CommunicatorGuardarLogin communicatorGuardarLogin = new CommunicatorGuardarLogin();
            communicatorGuardarLogin.ObtenerApi(jsonRequest,InicioModel.this);
        }else{
            Log.d(TAG,"ERROR: Usuario no encontrado en TableUsuario localmente");
        }

    }


    @Override
    public void onSuccessGuardarLogin(ResponseGuardarLogin result) {
        this.getUltimaEncuesta();
        this.getComunicados();
    }

    @Override
    public void onFailGuardarLogin(String result) {

    }
}
