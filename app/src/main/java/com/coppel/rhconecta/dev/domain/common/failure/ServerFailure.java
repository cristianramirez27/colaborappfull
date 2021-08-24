package com.coppel.rhconecta.dev.domain.common.failure;

public class ServerFailure implements Failure {

    private String cause;
    private boolean sessionInvalid;

    public ServerFailure() {
        cause = null;
        sessionInvalid = false;
    }

    public ServerFailure(String cause){
        this.cause = cause;
        sessionInvalid = false;
    }

    public ServerFailure(String cause, boolean sessionInvalid) {
        this.cause = cause;
        this.sessionInvalid = sessionInvalid;
    }

    public String getCause() {
        return cause;
    }

    public boolean getSessionInvalid() {
        return sessionInvalid;
    }
}
