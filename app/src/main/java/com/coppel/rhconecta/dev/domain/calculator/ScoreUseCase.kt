package com.coppel.rhconecta.dev.domain.calculator

import com.coppel.rhconecta.dev.domain.common.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScoreUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val calculatorRepository: CalculatorRepository
) {
    suspend operator fun invoke(
        vararg params: String,
        scoreCalculator: ScoreCalculator
    ): Either<Exception, ResultInformation> {
        return withContext(coroutineDispatcher) {
            calculatorRepository.scoreCalculator(params[0], params[1], scoreCalculator)
        }
    }
}
