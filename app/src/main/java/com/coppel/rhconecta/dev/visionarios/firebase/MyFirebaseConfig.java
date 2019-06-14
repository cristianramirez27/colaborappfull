package com.coppel.rhconecta.dev.visionarios.firebase;

import android.app.Activity;
import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFirebaseConfig {

    public MyFirebaseConfig() {
    }


    public static void getURL_VISIONARIOS (final Activity activity){
        final String url = "";
        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("config").child("URL_VISIONARIOS").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String url_new = dataSnapshot.getValue(String.class);
                        InternalDatabase idb = new InternalDatabase(activity.getApplicationContext());

                        TableConfig tableConfig = new TableConfig(idb,false);
                        Config config = new Config(1,url_new);
                        tableConfig.insertIfNotExist(config);

                        Log.d("CONFIG VISIONARIOS","SE OBTUBO CORRECTAMENTE EL URL "+url_new);

                    } else { //si no existe nodo
                        InternalDatabase idb = new InternalDatabase(activity.getApplicationContext());
                        TableConfig tableConfig = new TableConfig(idb,false);
                        Config config = new Config(1,"");
                        tableConfig.insertIfNotExist(config);
                        Log.d("CONFIG VISIONARIOS","NO EXISTE EL NODO EN FIREBASE: URL_VISIONARIOS");

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    InternalDatabase idb = new InternalDatabase(activity.getApplicationContext());
                    TableConfig tableConfig = new TableConfig(idb,false);
                    Config config = new Config(1,"");
                    tableConfig.insertIfNotExist(config);
                    Log.d("CONFIG VISIONARIOS", "error al obtener url visionarios");
                }
            });

        } catch (Exception e) {
            InternalDatabase idb = new InternalDatabase(activity.getApplicationContext());
            TableConfig tableConfig = new TableConfig(idb,false);
            Config config = new Config(1,"");
            tableConfig.insertIfNotExist(config);
            Log.d("CONFIG VISIONARIOS", "ERROR AL OBTENER URL VISIONARIOS"+e.getMessage());
        }
    }
}
