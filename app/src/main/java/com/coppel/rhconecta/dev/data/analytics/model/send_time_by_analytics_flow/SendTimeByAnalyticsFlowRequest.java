package com.coppel.rhconecta.dev.data.analytics.model.send_time_by_analytics_flow;

import androidx.annotation.Keep;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;

@Keep
public class SendTimeByAnalyticsFlowRequest {

    /* */
    public long num_empleado;

    /* */
    public int clv_opcion;

    /* */
    public int clv_sistema;

    /* */
    public long seg_acceso;

    /**
     *
     */
    public SendTimeByAnalyticsFlowRequest(
            long employeeNumber,
            AnalyticsFlow analyticsFlow,
            long timeInSeconds
    ) {
        this.num_empleado = employeeNumber;
        this.clv_opcion = 1;
        this.clv_sistema = getClvSistemaByAnalyticsFlow(analyticsFlow);
        this.seg_acceso = timeInSeconds;
    }

    /**
     *
     */
    private int getClvSistemaByAnalyticsFlow(AnalyticsFlow analyticsFlow) {
        switch (analyticsFlow) {
            case RELEASES: return 19;
            case VISIONARIES: return 18;
            case VIDEOS: return 17;
        }
        return 0;
    }

}
