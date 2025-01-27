package com.coppel.rhconecta.dev.data.poll.model.get_poll;

public class GetPollRequest {

    /* */
    public String num_empleado;
    /* */
    public int opcion;

    /**
     *
     *
     */
    public GetPollRequest(String num_empleado, int opcion) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
    }

}
