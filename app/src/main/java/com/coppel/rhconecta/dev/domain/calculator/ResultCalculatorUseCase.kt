package com.coppel.rhconecta.dev.domain.calculator

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.RetirementData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResultCalculatorUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val calculatorRepository: CalculatorRepository
) {
    suspend operator fun invoke(
        url: String,
        token: String,
        nss: String,
        savings: DataSavings, retirementData: RetirementData, informationCalculator: InformationCalculator
    ): Either<Exception, CalculationResult> {
        return withContext(coroutineDispatcher) {
            calculatorRepository.getResultCalculation(url, token, nss, savings, retirementData, informationCalculator)
        }
    }
}
