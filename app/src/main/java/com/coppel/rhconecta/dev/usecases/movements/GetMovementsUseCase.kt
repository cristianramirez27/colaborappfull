package com.coppel.rhconecta.dev.usecases.movements

import com.coppel.rhconecta.dev.data.movements.MovementRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsParams
import com.coppel.rhconecta.dev.domain.movements.GetMovementsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Use case for get all movements
 * */
class GetMovementsUseCase(private val movementRepository: MovementRepository) :
    UseCase<GetMovementsResponse, GetMovementsParams>() {
    override fun run(
        params: GetMovementsParams?,
        callback: OnResultFunction<Either<Failure, GetMovementsResponse>>?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movementRepository.getAllMovements()
            callback?.onResult(response)
        }
    }
}
