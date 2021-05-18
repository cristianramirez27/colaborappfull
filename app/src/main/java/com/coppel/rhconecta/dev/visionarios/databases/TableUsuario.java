package com.coppel.rhconecta.dev.visionarios.databases;

import android.database.Cursor;

import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;

import java.util.ArrayList;

public class TableUsuario {

    private InternalDatabase db;
    private String TABLA_NOMBRE = "usuario";
    private boolean resetOnStart = false; // SI ES TRUE ELIMINA LA TABLA PARA GENERARLA NUEVAMENTE

    public TableUsuario(InternalDatabase db, boolean resetOnStart) {
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
        this.db.Query(query);
    }

    public void createTable() {

        String query = "CREATE TABLE IF NOT EXISTS " + this.TABLA_NOMBRE + " (" +
                "id INT NOT NULL," +
                "numeroempleado VARCHAR(50) NOT NULL," +
                "nombre VARCHAR(100) NOT NULL," +
                "apellidop VARCHAR(50) NULL," +
                "apellidom VARCHAR(50) NULL," +
                "estatus INT NULL," +
                "numerocentro INT NULL," +
                "sexo INT NULL," +
                "fechanacimiento DATE NULL," +
                "fechaalta DATE NULL," +
                "descripcionpuesto VARCHAR(70) NULL," +
                "idestado INT NULL," +
                "estado VARCHAR(50) NULL," +
                "PRIMARY KEY (id)) ";

        this.db.Query(query);
    }

    public void insert(Usuario obj) {


        String query = "INSERT INTO " + this.TABLA_NOMBRE + " VALUES (" +
                " \"" + obj.getId() + "\", " +
                " \"" + obj.getNumeroempleado() + "\", " +
                " \"" + obj.getNombre() + "\", " +
                " \"" + obj.getApellidop() + "\", " +
                " \"" + obj.getApellidom() + "\", " +
                " \"" + obj.getEstatus() + "\", " +
                " \"" + obj.getNumerocentro() + "\", " +
                " \"" + obj.getSexo() + "\", " +
                " \"" + obj.getFechanacimiento() + "\", " +
                " \"" + obj.getFechaalta() + "\", " +
                " \"" + obj.getDescripcionpuesto() + "\", " +
                " \"" + obj.getIdestado() + "\", " +
                " \"" + obj.getEstado() + "\" ) ";

        this.db.Query(query);
    }

    public void update(Usuario obj) {


        String query = "UPDATE " + this.TABLA_NOMBRE + " SET " +

                " numeroempleado=\"" + obj.getNumeroempleado() + "\", " +
                " nombre=\"" + obj.getNombre() + "\", " +
                " apellidop=\"" + obj.getApellidop() + "\", " +
                " apellidom=\"" + obj.getApellidom() + "\", " +
                " estatus=\"" + obj.getEstatus() + "\", " +
                " numerocentro=\"" + obj.getNumerocentro() + "\", " +
                " sexo=\"" + obj.getSexo() + "\", " +
                " fechanacimiento=\"" + obj.getFechanacimiento() + "\", " +
                " fechaalta=\"" + obj.getFechaalta() + "\", " +
                " descripcionpuesto=\"" + obj.getDescripcionpuesto() + "\", " +
                " idestado=\"" + obj.getIdestado() + "\", " +
                " estado=\"" + obj.getEstado() + "\" WHERE id=" + obj.getId() + " ";

        this.db.Query(query);
    }

    public Usuario select(String id) {
        Usuario obj;
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + " c WHERE c.id='" + id + "' ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    obj = new Usuario(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("numeroempleado")),
                            cursor.getString(cursor.getColumnIndex("nombre")),
                            cursor.getString(cursor.getColumnIndex("apellidop")),
                            cursor.getString(cursor.getColumnIndex("apellidom")),
                            cursor.getInt(cursor.getColumnIndex("estatus")),
                            cursor.getInt(cursor.getColumnIndex("numerocentro")),
                            cursor.getInt(cursor.getColumnIndex("sexo")),
                            cursor.getString(cursor.getColumnIndex("fechanacimiento")),
                            cursor.getString(cursor.getColumnIndex("fechaalta")),
                            cursor.getString(cursor.getColumnIndex("descripcionpuesto")),
                            cursor.getInt(cursor.getColumnIndex("idestado")),
                            cursor.getString(cursor.getColumnIndex("estado"))
                    );


                } while (cursor.moveToNext());
            } else {
                obj = null;
            }
        } catch (Exception e) {
            obj = null;
        }

        if (cursor != null) {
            cursor.close();
        }

        return obj;
    }

    public ArrayList<Usuario> select() {
        Usuario obj;
        ArrayList<Usuario> objs = new ArrayList<Usuario>();
        String selectQuery = "SELECT * FROM " + this.TABLA_NOMBRE + "  ;";
        Cursor cursor = this.db.rawQuery(selectQuery);
        try {
            if (cursor.moveToFirst()) {
                do {
                    obj = new Usuario(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("numeroempleado")),
                            cursor.getString(cursor.getColumnIndex("nombre")),
                            cursor.getString(cursor.getColumnIndex("apellidop")),
                            cursor.getString(cursor.getColumnIndex("apellidom")),
                            cursor.getInt(cursor.getColumnIndex("estatus")),
                            cursor.getInt(cursor.getColumnIndex("numerocentro")),
                            cursor.getInt(cursor.getColumnIndex("sexo")),
                            cursor.getString(cursor.getColumnIndex("fechanacimiento")),
                            cursor.getString(cursor.getColumnIndex("fechaalta")),
                            cursor.getString(cursor.getColumnIndex("descripcionpuesto")),
                            cursor.getInt(cursor.getColumnIndex("idestado")),
                            cursor.getString(cursor.getColumnIndex("estado"))
                    );

                    objs.add(obj);


                } while (cursor.moveToNext());
            } else {
                objs = null;
            }
        } catch (Exception e) {
            objs = null;
        }

        if (cursor != null) {
            cursor.close();
        }

        return objs;
    }

    public void delete(String id) {
        String query = "DELETE FROM " + this.TABLA_NOMBRE + " WHERE idavisos=" + id + " ";
        this.db.Query(query);
    }

    public void insertIfNotExist(Usuario obj) {

        Usuario objSelect = this.select(Integer.toString(obj.getId()));
        if (objSelect == null) {
            this.insert(obj);
        } else {

            this.update(obj);
        }
    }

}
