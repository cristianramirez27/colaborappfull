package com.coppel.rhconecta.dev.visionarios.databases;

import android.database.Cursor;
import android.util.Log;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public class TableComunicados {

    private InternalDatabase db;
    private String TABLA_NOMBRE = "comunicados";
    private boolean resetOnStart = false; // SI ES TRUE ELIMINA LA TABLA PARA GENERARLA NUEVAMENTE

    public TableComunicados(InternalDatabase db, boolean resetOnStart) {
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

    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método---
     *
     * **/
    public void createTable() {

        String query = "CREATE TABLE IF NOT EXISTS " + this.TABLA_NOMBRE + " (" +
                "idavisos INT NOT NULL," +
                "nombre VARCHAR(100) NOT NULL," +
                "encabezado VARCHAR(100) NOT NULL," +
                "titulo VARCHAR(100) NOT NULL," +
                "contenido TEXT NOT NULL," +
                "imagen_aviso_preview VARCHAR(100) NOT NULL," +
                "imagen_aviso_landing VARCHAR(100) NOT NULL," +
                "fecha VARCHAR(100) NOT NULL," +
                "estatus INT NOT NULL," +
                "visto INT NOT NULL," +
                "landing_visible INT NOT NULL," +
                "PRIMARY KEY (idavisos)) ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "create table "+TABLA_NOMBRE+" OK!");
        } else {
            Log.d("MYSQLITE", "create table "+TABLA_NOMBRE+" ERROR!");
        }
    }

    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método---
     *
     * 6 Noviembre 2018
     * Modificacion: Se modificó la siguiente función  para corregir el insertado
     * de los comunicados de manera local.
     *
     * **/
    public void insert(Comunicado obj) {


        String query = "INSERT INTO "+this.TABLA_NOMBRE+ " VALUES ("+
                " \""+obj.getIdavisos()+"\", " +
                " \""+obj.getNombre()+"\", " +
                " \""+obj.getEncabezado()+"\", " +
                " \""+obj.getTitulo()+"\", " +
                " '"+obj.getContenido()+"', " +
                " \""+obj.getImagen_aviso_preview()+"\", " +
                " \""+obj.getImagen_aviso_landing()+"\", " +
                " \""+obj.getDate()+"\", " +
                " \""+obj.getEstatus()+"\", " +
                //" \""+(obj.isVisto()? 1 : 0)+"\", " +
                " \""+obj.getopc_visto()+"\", " +
                " \""+obj.getLanding_visible()+"\" ) ";

        Log.d("MYSQLITE QUERY",query);

        if(this.db.Query(query)){
            Log.d("MYSQLITE","INSERT  "+this.TABLA_NOMBRE+"   OK!");
        }else{
            Log.d("MYSQLITE","INSERT  "+this.TABLA_NOMBRE+"  ERROR!");
        }
    }

    public void update(Comunicado obj) {


        String query = "UPDATE "+this.TABLA_NOMBRE+ " SET "+

                " nombre=\""+obj.getNombre()+"\", " +
                " encabezado=\""+obj.getEncabezado()+"\", " +
                " titulo=\""+obj.getTitulo()+"\", " +
                " contenido='"+obj.getContenido()+"', " +
                " imagen_aviso_preview=\""+obj.getImagen_aviso_preview()+"\", " +
                " imagen_aviso_landing=\""+obj.getImagen_aviso_landing()+"\", " +
                " fecha=\""+obj.getDate()+"\", " +
                " estatus=\""+obj.getEstatus()+"\", " +
                " landing_visible=\""+obj.getLanding_visible()+"\", " +
                " visto=\""+obj.getopc_visto() +"\" WHERE idavisos="+obj.getIdavisos()+" ";
                //" visto=\""+(obj.isVisto()? 1 : 0)+"\" WHERE idavisos="+obj.getIdavisos()+" ";

        if(this.db.Query(query)){
            Log.d("MYSQLITE","UPDATE  "+this.TABLA_NOMBRE+" OK!");
        }else{
            Log.d("MYSQLITE","UPDATE  "+this.TABLA_NOMBRE+"  ERROR!");
        }
    }

    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método---
     *
     * **/
    public Comunicado select(String id) {
        Comunicado obj;
        String selectQuery = "SELECT * FROM "+this.TABLA_NOMBRE+" c WHERE c.idavisos='"+id+"' ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try{
            if (cursor.moveToFirst()) {
                do {
                    boolean visto =cursor.getInt(cursor.getColumnIndex("visto"))==1 ? true : false;
                    obj =   new Comunicado(
                            cursor.getInt(cursor.getColumnIndex("idavisos")),
                            cursor.getString(cursor.getColumnIndex("nombre")),
                            cursor.getString(cursor.getColumnIndex("encabezado")),
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("contenido")),
                            cursor.getString(cursor.getColumnIndex("imagen_aviso_preview")),
                            cursor.getString(cursor.getColumnIndex("imagen_aviso_landing")),
                            cursor.getString(cursor.getColumnIndex("fecha")),
                            cursor.getInt(cursor.getColumnIndex("estatus")),
                            cursor.getInt(cursor.getColumnIndex("landing_visible")),
                            cursor.getInt(cursor.getColumnIndex("visto"))
                    );
                    obj.setVisto(visto);



                } while (cursor.moveToNext());
                Log.d("MYSQLITE","SELECT ONE "+this.TABLA_NOMBRE+"  OK! CON RESULTADOS");

            }else{
                Log.d("MYSQLITE","SELECT ONE "+this.TABLA_NOMBRE+"  OK! SIN RESULTADOS!");
                obj = null;
            }

        }catch (Exception e){
            Log.d("MYSQLITE","SELECT ONE ERROR!");
            obj = null;

        }

        cursor.close();

        return obj;
    }

    /**
     * 29 Octubre 2018
     * Modificacion: Se agregan cambios en método---
     *
     * **/
    public ArrayList<Comunicado> select() {
        Comunicado obj;
        ArrayList<Comunicado> objs = new ArrayList<Comunicado>();
        String selectQuery = "SELECT * FROM "+this.TABLA_NOMBRE+"  ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try{
            if (cursor.moveToFirst()) {
                do {
                    boolean visto =cursor.getInt(cursor.getColumnIndex("visto"))==1 ? true : false;
                    obj =   new Comunicado(
                            cursor.getInt(cursor.getColumnIndex("idavisos")),
                            cursor.getString(cursor.getColumnIndex("nombre")),
                            cursor.getString(cursor.getColumnIndex("encabezado")),
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("contenido")),
                            cursor.getString(cursor.getColumnIndex("imagen_aviso_preview")),
                            cursor.getString(cursor.getColumnIndex("imagen_aviso_landing")),
                            cursor.getString(cursor.getColumnIndex("fecha")),
                            cursor.getInt(cursor.getColumnIndex("estatus")),
                            cursor.getInt(cursor.getColumnIndex("landing_visible")),
                            cursor.getInt(cursor.getColumnIndex("visto"))


                    );
                    obj.setVisto(visto);
                    objs.add(obj);


                } while (cursor.moveToNext());
                Log.d("MYSQLITE","SELECT MULTI "+this.TABLA_NOMBRE+"  OK! CON RESULTADOS");

            }else{
                Log.d("MYSQLITE","SELECT MULTI "+this.TABLA_NOMBRE+"  OK! SIN RESULTADOS!");
                objs = null;
            }

        }catch (Exception e){
            Log.d("MYSQLITE","SELECT MULTI ERROR! "+e.getMessage());
            objs = null;

        }

        cursor.close();

        return objs;
    }

    public void delete(String id) {

        String query = "DELETE FROM " + this.TABLA_NOMBRE + " WHERE idavisos=" + id + " ";

        if (this.db.Query(query)) {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " OK!");
        } else {
            Log.d("MYSQLITE", "DELETE " + this.TABLA_NOMBRE + " ERROR!");
        }
    }

    public void insertIfNotExist(Comunicado obj) {

        Comunicado objSelect = this.select(Integer.toString(obj.getIdavisos()));
        if (objSelect == null) {
            this.insert(obj);
        } else {

            obj.setVisto(objSelect.isVisto()); // actualiza su estado de si ya lo ha visualizado el usuario
            this.update(obj);
        }
    }


}
