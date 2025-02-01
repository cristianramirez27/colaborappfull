package com.coppel.rhconecta.dev.data.poll.model.get_poll;

public class GetPollRequest {

    /* */
    public String num_empleado;
    /* */
    public int clv_opcion;

    /**
     *
     *
     */
    public GetPollRequest(String num_empleado, int clv_opcion) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
    }

}
