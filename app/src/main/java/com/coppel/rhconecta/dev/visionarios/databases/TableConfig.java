package com.coppel.rhconecta.dev.visionarios.databases;

import android.database.Cursor;
import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.utils.Config;

import java.util.ArrayList;

public class TableConfig {
    private InternalDatabase db;
    private String TABLA_NOMBRE = "config";
    private boolean resetOnStart = false; // SI ES TRUE ELIMINA LA TABLA PARA GENERARLA NUEVAMENTE

    public TableConfig(InternalDatabase db, boolean resetOnStart) {
        this.db = db;
        this.db.openDB();

        this.resetOnStart = resetOnStart;
        if (this.resetOnStart) {
            this.dropTable();
        }
        this.createTable();
    }

    public void closeDB() {
        this.db.closeBD();
    }

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS " + this.TABLA_NOMBRE + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", " drop table " + TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "drop table " + TABLA_NOMBRE + " ERROR!");
        }
    }

    public void createTable() {

        String query = "CREATE TABLE IF NOT EXISTS " + this.TABLA_NOMBRE + " (" +
                "id INT NOT NULL," +
                "url_visionarios VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (id)) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "create table " + TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "create table " + TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insert(Config obj) {


        String query = "INSERT INTO " + this.TABLA_NOMBRE + " VALUES (" +
                " \"" + obj.getId() + "\", " +
                " \"" + obj.getURL_VISIONARIOS() + "\" ) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "INSERT  " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "INSERT  " + this.TABLA_NOMBRE + "  ERROR!");
        }
    }

    public void update(Config obj) {


        String query = "UPDATE " + this.TABLA_NOMBRE + " SET " +
                " url_visionarios=\"" + obj.getURL_VISIONARIOS() + "\" WHERE id=" + obj.getId() + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "UPDATE  " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "UPDATE  " + this.TABLA_NOMBRE + "  ERROR!");
        }
    }

    public Config select(String id) {
        Config obj;
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + " c WHERE c.id='" + id + "' ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    obj = new Config(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("url_visionarios"))
                    );


                } while (cursor.moveToNext());
                Log.d("MYSQLITE", "SELECT ONE " + this.TABLA_NOMBRE + "  OK! CON RESULTADOS");

            } else {
                Log.d("MYSQLITE", "SELECT ONE " + this.TABLA_NOMBRE + "  OK! SIN RESULTADOS!");
                obj = null;
            }

        } catch (Exception e) {
            Log.d("MYSQLITE", "SELECT ONE ERROR!");
            obj = null;

        }

        if (cursor != null) {
            cursor.close();
        }

        return obj;
    }

    public ArrayList<Config> select() {
        Config obj;
        ArrayList<Config> objs = new ArrayList<Config>();
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + "  ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    obj = new Config(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("url_visionarios"))
                    );

                    objs.add(obj);


                } while (cursor.moveToNext());
                Log.d("MYSQLITE", "SELECT MULTI " + this.TABLA_NOMBRE + "  OK! CON RESULTADOS");

            } else {
                Log.d("MYSQLITE", "SELECT MULTI " + this.TABLA_NOMBRE + "  OK! SIN RESULTADOS!");
                objs = null;
            }

        } catch (Exception e) {
            Log.d("MYSQLITE", "SELECT MULTI ERROR! " + e.getMessage());
            objs = null;

        }

        if (cursor != null) {
            cursor.close();
        }

        return objs;
    }

    public void delete(String id) {

        String query = "DELETE FROM " + this.TABLA_NOMBRE + " WHERE id=" + id + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insertIfNotExist(Config obj) {

        Config objSelect = this.select(Integer.toString(obj.getId()));
        if (objSelect == null) {
            this.insert(obj);
        } else {

            this.update(obj);
        }
    }

}



