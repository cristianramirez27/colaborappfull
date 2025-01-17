package com.coppel.rhconecta.dev.data.home.model.get_main_information;

/**
 *
 *
 */
public class GetMainInformationRequest {

    /* */
    public String num_empleado;
    /* */
    public int opcion;

    /**
     *
     *
     */
    public GetMainInformationRequest(String num_empleado, int opcion) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
    }

}
