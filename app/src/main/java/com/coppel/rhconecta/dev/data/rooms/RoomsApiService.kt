package com.coppel.rhconecta.dev.data.rooms

import com.coppel.rhconecta.dev.data.rooms.dto.CheckRequest
import com.coppel.rhconecta.dev.data.rooms.dto.CheckResponse
import com.coppel.rhconecta.dev.data.rooms.dto.ReservationRequest
import com.coppel.rhconecta.dev.data.rooms.dto.ReservationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface RoomsApiService {
    @Headers("Content-Type: application/json")
    @POST
    suspend fun getReservations(@Url url: String,
                                @Body request: ReservationRequest
    ) : Response<ReservationResponse>

    @Headers("Content-Type: application/json")
    @POST
    suspend fun checkReservation(@Url url: String,  @Body request: CheckRequest) : Response<CheckResponse>
}
