package com.coppel.rhconecta.dev.resources.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Fausto on 25/08/2016.
 *
 * @author fausto@kokonutstudio
 * @version 1.0
 *
 * Clase singleton para obtener una instancia de Realm
 *
 */
public class RealmSingleton {

    private static RealmSingleton realmInstance;
    private static Realm realm;
    private RealmConfiguration realmConfig;


    private RealmSingleton(Context context){
        // Create the Realm configuration
        realmConfig = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }


    /**
     * Método para obtener una instancia de Realm
     *
     * @param  context {@link Context}
     *
     * @return {@link Realm} instancia de Realm
     */
    public  static Realm getRealmIntance(Context context){

        if(realmInstance == null)
            realmInstance = new RealmSingleton(context);

        return realm;
    }

}
