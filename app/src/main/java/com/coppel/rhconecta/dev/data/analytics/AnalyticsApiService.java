package com.coppel.rhconecta.dev.data.analytics;

import com.coppel.rhconecta.dev.data.analytics.model.send_time_by_analytics_flow.SendTimeByAnalyticsFlowRequest;
import com.coppel.rhconecta.dev.data.analytics.model.send_time_by_analytics_flow.SendTimeByAnalyticsFlowResponse;
import com.coppel.rhconecta.dev.data.analytics.model.send_visit_section.SendVisitSectionRequest;
import com.coppel.rhconecta.dev.data.analytics.model.send_visit_section.SendVisitSectionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/** */
public interface AnalyticsApiService {

    /** */
    @Headers("Content-Type: application/json")
    @POST
    Call<SendTimeByAnalyticsFlowResponse> sendTimeByAnalyticsFlow(
            @Header("Authorization") String authHeader,
            @Header("X-Coppel-Date-Request") String dateRequest,
            @Header("X-Coppel-Latitude") String latitude,
            @Header("X-Coppel-Longitude") String longitude,
            @Header("X-Coppel-TransactionId") String transactionId,
            @Url String url,
            @Body SendTimeByAnalyticsFlowRequest request
    );

    /** */
    @Headers("Content-Type: application/json")
    @POST
    Call<SendVisitSectionResponse> sendVisitSection(
            @Header("Authorization") String authHeader,
            @Header("X-Coppel-Date-Request") String dateRequest,
            @Header("X-Coppel-Latitude") String latitude,
            @Header("X-Coppel-Longitude") String longitude,
            @Header("X-Coppel-TransactionId") String transactionId,
            @Url String url,
            @Body SendVisitSectionRequest request
    );

}
