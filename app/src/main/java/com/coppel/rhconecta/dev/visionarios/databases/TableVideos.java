package com.coppel.rhconecta.dev.visionarios.databases;

import android.database.Cursor;
import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;

import java.util.ArrayList;

public class TableVideos {
    private InternalDatabase db;
    private String TABLA_NOMBRE = "videos";
    private boolean resetOnStart = false; // SI ES TRUE ELIMINA LA TABLA PARA GENERARLA NUEVAMENTE

    public TableVideos(InternalDatabase db, boolean resetOnStart) {
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
                "idvideos INT NOT NULL," +
                "titulo VARCHAR(100) NOT NULL," +
                "encabezado VARCHAR(100) NOT NULL," +
                "estatus INT NOT NULL," +
                "descripcion VARCHAR(100) NOT NULL," +
                "duracion_h VARCHAR(100) NOT NULL," +
                "duracion_m VARCHAR(100) NOT NULL," +
                "fecha VARCHAR(100) NOT NULL," +
                "src VARCHAR(100) NOT NULL," +
                "imagen_video_preview VARCHAR(100) NOT NULL," +
                "vistas INT NOT NULL," +
                "estrellas INT NOT NULL," +
                "landing_visible INT NOT NULL," +
                "visto INT NOT NULL," +

                "PRIMARY KEY (idvideos)) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "create table " + TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "create table " + TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insert(Video obj) {


        String query = "INSERT INTO " + this.TABLA_NOMBRE + " VALUES (" +
                " \"" + obj.getIdvideos() + "\", " +
                " \"" + obj.getTitulo() + "\", " +
                " \"" + obj.getEncabezado() + "\", " +
                " \"" + obj.getEstatus() + "\", " +
                " \"" + obj.getDescripcion() + "\", " +
                " \"" + obj.getDuracion_h() + "\", " +
                " \"" + obj.getDuracion_m() + "\", " +
                " \"" + obj.getFecha() + "\", " +
                " \"" + obj.getSrc() + "\", " +
                " \"" + obj.getImagen_video_preview() + "\", " +
                " \"" + obj.getVistas() + "\", " +
                " \"" + obj.getEstrellas() + "\", " +
                " \"" + obj.getLanding_visible() + "\", " +
                " \"" + (obj.isVisto() ? 1 : 0) + "\" ) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "INSERT  " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "INSERT  " + this.TABLA_NOMBRE + "  ERROR!");
        }
    }

    public void update(Video obj) {


        String query = "UPDATE " + this.TABLA_NOMBRE + " SET " +
                " titulo=\"" + obj.getTitulo() + "\", " +
                " encabezado=\"" + obj.getEncabezado() + "\", " +
                " estatus=\"" + obj.getEstatus() + "\", " +
                " descripcion=\"" + obj.getDescripcion() + "\", " +
                " duracion_h=\"" + obj.getDuracion_h() + "\", " +
                " duracion_m=\"" + obj.getDuracion_m() + "\", " +
                " fecha=\"" + obj.getFecha() + "\", " +
                " src=\"" + obj.getSrc() + "\", " +
                " imagen_video_preview=\"" + obj.getImagen_video_preview() + "\", " +
                " vistas=\"" + obj.getVistas() + "\", " +
                " estrellas=\"" + obj.getEstrellas() + "\", " +
                " landing_visible=\"" + obj.getLanding_visible() + "\", " +
                " visto=\"" + (obj.isVisto() ? 1 : 0) + "\" WHERE idvideos=" + obj.getIdvideos() + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "UPDATE  " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "UPDATE  " + this.TABLA_NOMBRE + "  ERROR!");
        }
    }

    public Video select(String id) {
        Video obj;
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + "  WHERE idvideos=" + id + " ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    boolean visto = cursor.getInt(cursor.getColumnIndex("visto")) == 1 ? true : false;
                    obj = new Video(
                            cursor.getInt(cursor.getColumnIndex("idvideos")),
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("encabezado")),
                            cursor.getInt(cursor.getColumnIndex("estatus")),
                            cursor.getString(cursor.getColumnIndex("descripcion")),
                            cursor.getString(cursor.getColumnIndex("duracion_h")),
                            cursor.getString(cursor.getColumnIndex("duracion_m")),
                            cursor.getString(cursor.getColumnIndex("fecha")),
                            cursor.getString(cursor.getColumnIndex("src")),
                            cursor.getString(cursor.getColumnIndex("imagen_video_preview")),
                            cursor.getInt(cursor.getColumnIndex("vistas")),
                            cursor.getInt(cursor.getColumnIndex("estrellas")),
                            cursor.getInt(cursor.getColumnIndex("landing_visible"))
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

    public ArrayList<Video> select() {
        Video obj;
        ArrayList<Video> objs = new ArrayList<Video>();
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + "  ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    boolean visto = cursor.getInt(cursor.getColumnIndex("visto")) == 1 ? true : false;
                    obj = new Video(
                            cursor.getInt(cursor.getColumnIndex("idvideos")),
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("encabezado")),
                            cursor.getInt(cursor.getColumnIndex("estatus")),
                            cursor.getString(cursor.getColumnIndex("descripcion")),
                            cursor.getString(cursor.getColumnIndex("duracion_h")),
                            cursor.getString(cursor.getColumnIndex("duracion_m")),
                            cursor.getString(cursor.getColumnIndex("fecha")),
                            cursor.getString(cursor.getColumnIndex("src")),
                            cursor.getString(cursor.getColumnIndex("imagen_video_preview")),
                            cursor.getInt(cursor.getColumnIndex("vistas")),
                            cursor.getInt(cursor.getColumnIndex("estrellas")),
                            cursor.getInt(cursor.getColumnIndex("landing_visible"))

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
            Log.d("MYSQLITE", "SELECT MULTI ERROR!");
            objs = null;

        }

        cursor.close();

        return objs;
    }

    public void delete(String id) {

        String query = "DELETE FROM " + this.TABLA_NOMBRE + " WHERE idvideos=" + id + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insertIfNotExist(Video obj) {

        Video objSelect = this.select(Integer.toString(obj.getIdvideos()));
        if (objSelect == null) {
            this.insert(obj);
        } else {
            obj.setVisto(objSelect.isVisto()); // actualiza su estado de si ya lo ha visualizado el usuario
            this.update(obj);
        }
    }
}
