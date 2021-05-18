package com.coppel.rhconecta.dev.data.release.model.get_releases_previews;

/* */
public class GetReleasesPreviewsRequest {

    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;

    /**
     *
     */
    public GetReleasesPreviewsRequest(Long num_empleado, int clv_opcion) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
    }

}
