package com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count;

/**
 *
 *
 */
public class GetAvailablePollCountRequest {

    /* */
    public String num_empleado;
    /* */
    public int opcion;

    /**
     *
     *
     */
    public GetAvailablePollCountRequest(String num_empleado, int opcion) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
    }

}
