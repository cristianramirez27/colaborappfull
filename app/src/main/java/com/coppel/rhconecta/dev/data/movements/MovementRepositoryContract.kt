package com.coppel.rhconecta.dev.data.movements

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsResponse

/**
 * Define methods in repository
 */
interface MovementRepositoryContract {
    suspend fun getAllMovements(): Either<Failure, GetMovementsResponse>
}