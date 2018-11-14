package com.coppel.rhconecta.dev.resources.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class HomeMenuItem extends RealmObject {

    @PrimaryKey
    private String TAG;
    private String name;

    public HomeMenuItem() {
    }

    public HomeMenuItem(String name, String TAG) {
        this.name = name;
        this.TAG = TAG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}
