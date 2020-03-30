package com.coppel.rhconecta.dev.data.visionary.model;

public class GetVisionariesPreviewsRequest {

    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;

    /**
     *
     * @param num_empleado
     * @param clv_opcion
     */
    public GetVisionariesPreviewsRequest(Long num_empleado, int clv_opcion) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
    }

}
