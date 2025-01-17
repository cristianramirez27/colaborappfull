package com.coppel.rhconecta.dev.data.release.model.get_releases_previews;

/** */
public class GetReleasesPreviewsRequest {

    /* */
    public String num_empleado;
    /* */
    public int opcion;
    /* */
    public Integer opc_acceso;

    /** */
    public GetReleasesPreviewsRequest(String num_empleado, int opcion, Integer opc_acceso) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
        this.opc_acceso = opc_acceso;
    }

}
