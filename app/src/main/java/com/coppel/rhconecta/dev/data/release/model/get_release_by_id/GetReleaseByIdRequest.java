package com.coppel.rhconecta.dev.data.release.model.get_release_by_id;

/* */
public class GetReleaseByIdRequest {

    /* */
    public Long num_empleado;
    /* */
    public int clv_opcion;
    /* */
    public long idu_avisos;
    /* */
    public long opc_acceso;

    /**
     *
     */
    public GetReleaseByIdRequest(Long num_empleado, int clv_opcion, long idu_avisos, long opc_acceso) {
        this.num_empleado = num_empleado;
        this.clv_opcion = clv_opcion;
        this.idu_avisos = idu_avisos;
        this.opc_acceso = opc_acceso;
    }
}
