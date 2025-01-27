package com.coppel.rhconecta.dev.resources.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class NotificationsUser extends RealmObject {

    @PrimaryKey
    private String IIUID;
    private String USER_NUMBER;
    private int ID_SISTEMA;
    private int ID_NOTIFICACION;

    public NotificationsUser() {
    }

    public NotificationsUser(String IIUID, String USER_NUMBER, int ID_SISTEMA) {
        this.IIUID = IIUID;
        this.USER_NUMBER = USER_NUMBER;
        this.ID_SISTEMA = ID_SISTEMA;
    }


    public NotificationsUser(String IIUID, String USER_NUMBER, int ID_SISTEMA, int ID_NOTIFICACION) {
        this.IIUID = IIUID;
        this.USER_NUMBER = USER_NUMBER;
        this.ID_SISTEMA = ID_SISTEMA;
        this.ID_NOTIFICACION = ID_NOTIFICACION;
    }

    public int getID_NOTIFICACION() {
        return ID_NOTIFICACION;
    }

    public void setID_NOTIFICACION(int ID_NOTIFICACION) {
        this.ID_NOTIFICACION = ID_NOTIFICACION;
    }

    public String getIIUID() {
        return IIUID;
    }

    public void setIIUID(String IIUID) {
        this.IIUID = IIUID;
    }

    public String getUSER_NUMBER() {
        return USER_NUMBER;
    }

    public void setUSER_NUMBER(String USER_NUMBER) {
        this.USER_NUMBER = USER_NUMBER;
    }

    public int getID_SISTEMA() {
        return ID_SISTEMA;
    }

    public void setID_SISTEMA(int ID_SISTEMA) {
        this.ID_SISTEMA = ID_SISTEMA;
    }
}
