package com.coppel.rhconecta.dev.domain.calculator

import com.coppel.rhconecta.dev.domain.common.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListedWeekCalculatorUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val calculatorRepository: CalculatorRepository
) {
    suspend operator fun invoke(vararg params: String): Either<Exception, InformationWeek> {
        return withContext(coroutineDispatcher) {
            calculatorRepository.getInformationWeek(
                url = params[0],
                token = params[1],
                nss = params[2],
                antiquity = params[3]
            )
        }
    }
}
