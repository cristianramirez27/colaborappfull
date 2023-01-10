package com.coppel.rhconecta.dev.framework.home

import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationRequest
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationResponse
import com.coppel.rhconecta.dev.framework.DataResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface HomeApiService {
    /**
     *
     */
    @Headers("Content-Type: application/json")
    @POST
    fun getMainInformation(
        @Header("Authorization") authHeader: String,
        @Url url: String,
        @Body request: GetMainInformationRequest,
    ): Call<GetMainInformationResponse>

    /**
     * Get help desk availability
     */
    @Headers("Content-Type: application/json")
    @POST
    suspend fun getHelpDeskServiceAvailability(
        @Header("Authorization") authHeader: String,
        @Url url: String,
        @Body request: HomeRequest,
    ): Response<DataResponse<List<HelpDeskAvailabilityServer>>>
}