package com.coppel.rhconecta.dev;

import android.app.Application;

import com.coppel.rhconecta.dev.resources.db.RealmHelper;

import io.realm.Realm;

public class CoppelApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(RealmHelper.configurateRealm(this));
    }
}
