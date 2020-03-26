package com.coppel.rhconecta.dev.data.home.model.get_main_information;

public class GetMainInformationRequest {

    public Long num_empleado;
    public int clv_opcion;

    /**
     *
     * @param num_empleado
     * @param clv_opcion
     */
    public GetMainInformationRequest(Long num_empleado, int clv_opcion) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
    }

}
