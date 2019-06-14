package com.coppel.rhconecta.dev.visionarios.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class InternalDatabase {

    private Context context;
    private String BD_NOMBRE = "visionarios";
    private SQLiteDatabase bd;

    public InternalDatabase() {

    }

    public InternalDatabase(Context context) {
        this.context = context;
    }

    public void openDB() {
        try {

            this.bd = context.openOrCreateDatabase(this.BD_NOMBRE, this.context.MODE_PRIVATE, null);
        } catch (Exception e) {

            //Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    public boolean Query(String query) {

        boolean success = true;

        try {
            this.bd.execSQL(query);
        } catch (Exception e) {
            success = false;
            //Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT);

        }

        return success;
    }

    public Cursor rawQuery(String query) {


        Cursor cursor;

        try {
            cursor = this.bd.rawQuery(query, null);


        } catch (SQLiteException e) {
            //Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT);
            return null;
        } finally {
            //release all your resources
        }

        return cursor;
    }

    public void closeBD() {
        this.bd.close();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
