package com.coppel.rhconecta.dev.framework.movements

import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @Headers("Content-Type: application/json")
    @POST
    suspend fun getMovements(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body servicesRequest: GetMovementsRequest
    ): Response<GetMovementsAPIResponse>
}
