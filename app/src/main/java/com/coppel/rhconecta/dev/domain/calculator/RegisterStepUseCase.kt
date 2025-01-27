package com.coppel.rhconecta.dev.domain.calculator

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterStepUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val calculatorRepository: CalculatorRepository
) {
    suspend operator fun invoke(vararg params: String, register: Register) {
        return withContext(coroutineDispatcher) {
            calculatorRepository.registerStep(url = params[0], token = params[1], register)
        }
    }
}
