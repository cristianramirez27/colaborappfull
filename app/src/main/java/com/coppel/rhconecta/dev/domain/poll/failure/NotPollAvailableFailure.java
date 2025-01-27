package com.coppel.rhconecta.dev.domain.poll.failure;

import com.coppel.rhconecta.dev.domain.common.failure.Failure;

public class NotPollAvailableFailure implements Failure {

    public String message;

    public NotPollAvailableFailure(String message) {
        this.message = message;
    }

}
