package com.coppel.rhconecta.dev.resources.db.models;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class SectionDb extends RealmObject {
    private int parentId;
    private int id;
    private String name;

    public SectionDb() {
    }

    public SectionDb(int parentId, int id, String name) {
        this.parentId = parentId;
        this.id = id;
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
