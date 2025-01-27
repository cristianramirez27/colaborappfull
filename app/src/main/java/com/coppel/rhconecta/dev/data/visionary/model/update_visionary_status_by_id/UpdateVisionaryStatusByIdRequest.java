package com.coppel.rhconecta.dev.data.visionary.model.update_visionary_status_by_id;

/* */
public class UpdateVisionaryStatusByIdRequest {

    /* */
    public String idu_videos;
    /* */
    public String num_empleado;
    /* */
    public int clv_opcion;
    /* */
    public int clv_tipolog;
    /* */
    public String opc_calificar;

    /**
     *
     */
    public UpdateVisionaryStatusByIdRequest(String idu_videos, String num_empleado, int clv_opcion, int clv_tipolog, String opc_calificar) {
        this.idu_videos = idu_videos;
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
        this.clv_tipolog = clv_tipolog;
        this.opc_calificar = opc_calificar;
    }
}
