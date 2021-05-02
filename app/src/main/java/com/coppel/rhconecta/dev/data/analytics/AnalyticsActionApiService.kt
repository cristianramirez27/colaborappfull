package com.coppel.rhconecta.dev.data.analytics

import com.coppel.rhconecta.dev.data.analytics.model.send_analytics_action.SendAnalyticsActionRequest
import com.coppel.rhconecta.dev.data.analytics.model.send_analytics_action.SendAnalyticsActionResponse
import retrofit2.Response
import retrofit2.http.*

/** */
interface AnalyticsActionApiService {

    /**  */
    @Headers("Content-Type: application/json")
    @POST
    suspend fun sendAnalyticsAction(
            @Header("Authorization") authHeader: String,
            @Url url: String,
            @Body request: SendAnalyticsActionRequest
    ): Response<SendAnalyticsActionResponse>
}