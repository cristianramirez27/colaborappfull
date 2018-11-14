package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Response;

public class ResponseGuardarEncuesta {

    private String message;
    private boolean success;

    public ResponseGuardarEncuesta(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResponseGuardarEncuesta() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

