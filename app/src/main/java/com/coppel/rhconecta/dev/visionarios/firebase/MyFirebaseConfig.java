package com.coppel.rhconecta.dev.visionarios.firebase;

import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFirebaseConfig {

    public MyFirebaseConfig() {
    }


    public static String getURL_VISIONARIOS (){
        final String url = ConstantesGlobales.URL_API;
        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("config").child("URL_VISIONARIOS").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String str = dataSnapshot.getValue(String.class);
                        //return str;


                    } else { //si no existe nodo
                       //return url;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("DICCIONARIO", "error");
                    //return url;
                }
            });

        } catch (Exception e) {
           // return url;
            Log.d("DICCIONARIO", e.getMessage());
        }
        return url;
    }
}
