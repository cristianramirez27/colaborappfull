package com.coppel.rhconecta.dev.data.analytics.model.send_visit_section;

import androidx.annotation.Keep;

@Keep
public class SendVisitSectionRequest {

    /* */
    public long num_empleado;

    /* */
    public int clv_opcion;

    /* */
    public int clv_sistema;

    /* */
    public int clv_acceso;

    /**
     *
     */
    public SendVisitSectionRequest(
            long employeeNumber,
            int clvSistema,
            int clvAcceso
    ) {
        this.num_empleado = employeeNumber;
        this.clv_opcion = 2;
        this.clv_sistema = clvSistema;
        this.clv_acceso = clvAcceso;
    }

}