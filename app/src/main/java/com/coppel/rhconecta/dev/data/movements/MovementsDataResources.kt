package com.coppel.rhconecta.dev.data.movements

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsResponse

/**
 * Define method for handle data in my movements
 * */
interface MovementsDataResources {
    suspend fun getMovements(): Either<Failure, GetMovementsResponse>
}
