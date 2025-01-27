package com.coppel.rhconecta.dev.resources.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

import java.util.List;

@RealmClass
public class MainSection extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    @Ignore
    private RealmList<SectionDb> subSections;

    public MainSection() {
    }

    public MainSection(int id, String name, List<SectionDb> subSections) {
        this.id = id;
        this.name = name;
        this.subSections = new RealmList(subSections);
    }

    public MainSection(int id, String name, RealmList<SectionDb> subSections) {
        this.id = id;
        this.name = name;
        this.subSections = new RealmList(subSections);
    }

    public RealmList<SectionDb> getSubSections() {
        return subSections;
    }

    public void setSubSections(RealmList<SectionDb> subSections) {
        this.subSections = subSections;
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
