package com.coppel.rhconecta.dev.domain.rooms

import com.coppel.rhconecta.dev.domain.common.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckReservationUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val roomsRepository: RoomsRepository
) {
    suspend operator fun invoke(vararg params: String): Either<Exception, Check> {
        return withContext(coroutineDispatcher) {
            roomsRepository.checkReservation(params[0], params[1], params[2])
        }
    }
}
