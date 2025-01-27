package com.coppel.rhconecta.dev.data.release.model.get_release_by_id;

/* */
public class GetReleaseByIdRequest {

    /* */
    public String num_empleado;
    /* */
    public int opcion;
    /* */
    public String idu_avisos;
    /* */
    public Integer opc_acceso;

    /** */
    public GetReleaseByIdRequest(String num_empleado, int opcion, String idu_avisos, Integer opc_acceso) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
        this.idu_avisos = idu_avisos;
        this.opc_acceso = opc_acceso;
    }

}
