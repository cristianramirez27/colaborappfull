package com.coppel.rhconecta.dev.data.rooms

import com.coppel.rhconecta.dev.business.utils.ServicesConstants
import com.coppel.rhconecta.dev.data.rooms.dto.CheckRequest
import com.coppel.rhconecta.dev.data.rooms.dto.ReservationRequest
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.rooms.Check
import com.coppel.rhconecta.dev.domain.rooms.Reservation
import com.coppel.rhconecta.dev.domain.rooms.RoomsRepository
import retrofit2.HttpException
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val roomsApiService: RoomsApiService
) : RoomsRepository {
    override suspend fun getListReservations(url: String, token: String, timeZone : String): Either<Exception, List<Reservation>> {
        try {
            val response = roomsApiService.getReservations(url, ReservationRequest(token, "GMT-5"))
            return if (response.isSuccessful && response.body() != null) {
                val responseObject = response.body()!!
                if (responseObject.meta.status.equals(ServicesConstants.SUCCESS)) {
                    val list = responseObject.data.response.list.map { dto ->
                        Reservation()
                    }
                    Either<Exception, List<Reservation>>().Right(list)
                } else {
                    Either<Exception, List<Reservation>>().Left(Exception(responseObject.data.response.userMessage))
                }
            } else {
                Either<Exception, List<Reservation>>().Left(HttpException(response))
            }
        } catch (exception: Exception) {
            return Either<Exception, List<Reservation>>().Left(exception)
        }
    }

    override suspend fun checkReservation(url: String, token: String, clvRoom: String): Either<Exception, Check> {
        try {
            val response = roomsApiService.checkReservation(url, CheckRequest(token, clvRoom))
            return if (response.isSuccessful && response.body() != null) {
                val responseObject = response.body()!!
                if (responseObject.meta.status.equals(ServicesConstants.SUCCESS)) {
                    //val list = responseObject.data.response
                    Either<Exception, Check>().Right(Check())
                } else {
                    Either<Exception, Check>().Left(Exception(responseObject.data.response.userMessage))
                }
            } else {
                Either<Exception, Check>().Left(HttpException(response))
            }
        } catch (exception: Exception) {
            return Either<Exception, Check>().Left(exception)
        }
    }
}
