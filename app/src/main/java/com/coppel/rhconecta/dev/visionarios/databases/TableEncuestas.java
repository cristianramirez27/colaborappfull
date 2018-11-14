package com.coppel.rhconecta.dev.visionarios.databases;

import android.database.Cursor;
import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

import java.util.ArrayList;

public class TableEncuestas {

    private InternalDatabase db;
    private String TABLA_NOMBRE = "encuestas";
    private boolean resetOnStart = false; // SI ES TRUE ELIMINA LA TABLA PARA GENERARLA NUEVAMENTE

    public TableEncuestas(InternalDatabase db, boolean resetOnStart) {
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
                "idencuestas INT NOT NULL," +
                "nombre VARCHAR(100) NOT NULL," +
                "img_encuesta_preview VARCHAR(100) NOT NULL," +
                "img_encuesta_landing VARCHAR(100) NOT NULL," +
                "estatus INT NOT NULL," +
                "visto INT NOT NULL," +

                "PRIMARY KEY (idencuestas)) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "create table " + TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "create table " + TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insert(Encuesta obj) {


        String query = "INSERT INTO " + this.TABLA_NOMBRE + " VALUES (" +
                " \"" + obj.getIdencuestas() + "\", " +
                " \"" + obj.getNombre() + "\", " +
                " \"" + obj.getImg_encuesta_preview() + "\", " +
                " \"" + obj.getImg_encuesta_landing() + "\", " +
                " \"" + obj.getEstatus() + "\", " +
                " \"" + (obj.isVisto() ? 1 : 0) + "\" ) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "INSERT  " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "INSERT  " + this.TABLA_NOMBRE + "  ERROR!");
        }
    }

    public void update(Encuesta obj) {


        String query = "UPDATE " + this.TABLA_NOMBRE + " SET " +

                " nombre=\"" + obj.getNombre() + "\", " +
                " img_encuesta_preview=\"" + obj.getImg_encuesta_preview() + "\", " +
                " img_encuesta_landing=\"" + obj.getImg_encuesta_landing() + "\", " +
                " estatus=\"" + obj.getEstatus() + "\", " +
                " visto=\"" + (obj.isVisto() ? 1 : 0) + "\" WHERE idencuestas=" + obj.getIdencuestas() + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "UPDATE  " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "UPDATE  " + this.TABLA_NOMBRE + "  ERROR!");
        }
    }

    public Encuesta select(String id) {
        Encuesta obj;
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + " c WHERE c.idencuestas='" + id + "' ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    boolean visto = cursor.getInt(cursor.getColumnIndex("visto")) == 1 ? true : false;
                    obj = new Encuesta(
                            cursor.getInt(cursor.getColumnIndex("idencuestas")),
                            cursor.getString(cursor.getColumnIndex("nombre")),
                            cursor.getString(cursor.getColumnIndex("img_encuesta_preview")),
                            cursor.getString(cursor.getColumnIndex("img_encuesta_landing")),
                            cursor.getInt(cursor.getColumnIndex("estatus"))

                    );
                    obj.setVisto(visto);


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

        cursor.close();

        return obj;
    }

    public ArrayList<Encuesta> select() {
        Encuesta obj;
        ArrayList<Encuesta> objs = new ArrayList<Encuesta>();
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + "  ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    boolean visto = cursor.getInt(cursor.getColumnIndex("visto")) == 1 ? true : false;
                    obj = new Encuesta(
                            cursor.getInt(cursor.getColumnIndex("idencuestas")),
                            cursor.getString(cursor.getColumnIndex("nombre")),
                            cursor.getString(cursor.getColumnIndex("img_encuesta_preview")),
                            cursor.getString(cursor.getColumnIndex("img_encuesta_landing")),
                            cursor.getInt(cursor.getColumnIndex("estatus"))

                    );
                    obj.setVisto(visto);
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

        cursor.close();

        return objs;
    }

    public void delete(String id) {

        String query = "DELETE FROM " + this.TABLA_NOMBRE + " WHERE idencuestas=" + id + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insertIfNotExist(Encuesta obj) {

        Encuesta objSelect = this.select(Integer.toString(obj.getIdencuestas()));
        if (objSelect == null) {
            this.insert(obj);
        } else {

            obj.setVisto(objSelect.isVisto()); // actualiza su estado de si ya lo ha visualizado el usuario
            this.update(obj);
        }
    }

}