package com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id;

/* */
public class GetVisionaryByIdRequest {

    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;
    /* */
    public long idu_videos;

    /**
     *
     */
    public GetVisionaryByIdRequest(Long num_empleado, int clv_opcion, long idu_videos) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
        this.idu_videos = idu_videos;
    }

}
