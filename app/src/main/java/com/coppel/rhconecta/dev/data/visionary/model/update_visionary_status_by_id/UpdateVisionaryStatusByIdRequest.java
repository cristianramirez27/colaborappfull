package com.coppel.rhconecta.dev.data.visionary.model.update_visionary_status_by_id;

/**
 *
 *
 */
public class UpdateVisionaryStatusByIdRequest {

    /* */
    public long idu_videos;
    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;
    /* */
    public int clv_tipolog;

    public UpdateVisionaryStatusByIdRequest(long idu_videos, Long num_empleado, int clv_opcion, int clv_tipolog) {
        this.idu_videos = idu_videos;
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
        this.clv_tipolog = clv_tipolog;
    }

}
