package com.coppel.rhconecta.dev.data.movements

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsResponse

class MovementRepository(
    private val remoteMovementsDataSource: MovementsDataResources
) : MovementRepositoryContract {
    override suspend fun getAllMovements(): Either<Failure, GetMovementsResponse> =
        remoteMovementsDataSource.getMovements()
}