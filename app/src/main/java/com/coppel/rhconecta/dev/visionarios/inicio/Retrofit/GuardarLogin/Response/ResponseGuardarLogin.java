package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response;

public class ResponseGuardarLogin {

    private int fileCount;
    private int affectedRows;
    private int insertId;
    private int serverStatus;
    private int warningCount;
    private String message;
    private boolean protocol41;
    private int changedRows;

    public ResponseGuardarLogin(int fileCount, int affectedRows, int insertId, int serverStatus, int warningCount, String message, boolean protocol41, int changedRows) {
        this.fileCount = fileCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.serverStatus = serverStatus;
        this.warningCount = warningCount;
        this.message = message;
        this.protocol41 = protocol41;
        this.changedRows = changedRows;
    }

    public ResponseGuardarLogin() {
    }
}
