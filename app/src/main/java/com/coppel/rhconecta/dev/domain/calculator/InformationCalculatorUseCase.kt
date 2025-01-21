package com.coppel.rhconecta.dev.domain.calculator

import com.coppel.rhconecta.dev.domain.common.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InformationCalculatorUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val calculatorRepository: CalculatorRepository
) {
    suspend operator fun invoke(vararg params: String): Either<Exception, InformationCalculator> {
        return withContext(coroutineDispatcher) {
            calculatorRepository.getInformationCalculator(
                url = params[0],
                token = params[1],
                numberEmployer = params[2]
            )
        }
    }
}
