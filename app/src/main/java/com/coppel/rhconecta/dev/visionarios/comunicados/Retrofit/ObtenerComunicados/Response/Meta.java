package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Response;

public class Meta {
    public String id_transaction;
    public String status;
    public String time_elapsed;

    public Meta(String id_transaction, String status, String time_elapsed) {
        this.id_transaction = id_transaction;
        this.status = status;
        this.time_elapsed = time_elapsed;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime_elapsed() {
        return time_elapsed;
    }

    public void setTime_elapsed(String time_elapsed) {
        this.time_elapsed = time_elapsed;
    }

}
