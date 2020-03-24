package com.coppel.rhconecta.dev.domain.common.failure;

public class ServerFailure implements Failure {

    private String cause;

    public ServerFailure() {
        cause = null;
    }

    public ServerFailure(String cause){
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

}
