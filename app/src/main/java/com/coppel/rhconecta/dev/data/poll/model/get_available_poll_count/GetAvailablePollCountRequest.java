package com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count;

/**
 *
 *
 */
public class GetAvailablePollCountRequest {

    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;

    /**
     *
     *
     */
    public GetAvailablePollCountRequest(Long num_empleado, int clv_opcion) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
    }

}
