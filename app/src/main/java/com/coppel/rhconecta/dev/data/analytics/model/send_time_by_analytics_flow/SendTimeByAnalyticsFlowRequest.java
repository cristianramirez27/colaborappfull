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
            case SAVING_FUND: return 9;
            case HOLIDAYS_COLABORADOR:
            case HOLIDAYS_GERENTE: return 10;
            case TRAVEL_EXPENSES_COLABORADOR: return 11;
            case VIDEOS: return 17;
            case VISIONARIES: return 18;
            case RELEASES: return 19;
            case POLL: return 20;
            case PAYROLL_VOUCHER: return 21;
            case LETTERS: return 22;
            case BENEFITS: return 23;
            case COLLAGE: return 24;
            case COVID_SURVEY: return 25;
        }
        return 0;
    }

}
