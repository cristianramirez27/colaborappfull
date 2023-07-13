package com.coppel.rhconecta.dev.domain.rooms

import com.coppel.rhconecta.dev.domain.common.Either

interface RoomsRepository {
    suspend fun getListReservations(url: String, token : String, timeZone : String) : Either<Exception, List<Reservation>>
    suspend fun checkReservation(url: String, token : String, clvRoom :String) : Either<Exception,Check>
}
