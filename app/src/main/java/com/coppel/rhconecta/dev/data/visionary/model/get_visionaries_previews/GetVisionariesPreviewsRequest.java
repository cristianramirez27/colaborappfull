package com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews;

/* */
public class GetVisionariesPreviewsRequest {

    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;
    /* */
    public long opc_acceso;

    /**
     *
     */
    public GetVisionariesPreviewsRequest(Long num_empleado, int clv_opcion, long opc_acceso) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
        this.opc_acceso = opc_acceso;
    }

}
