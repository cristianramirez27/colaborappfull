package com.coppel.rhconecta.dev.domain.calculator

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.RetirementData

interface CalculatorRepository {
    suspend fun getInformationCalculator(
        url: String,
        token: String,
        numberEmployer: String
    ): Either<Exception, InformationCalculator>

    suspend fun getInformationWeek(
        url: String,
        token: String,
        nss: String,
        antiquity: String
    ): Either<Exception, InformationWeek>

    suspend fun scoreCalculator(
        url: String,
        token: String,
        scoreCalculator: ScoreCalculator
    ): Either<Exception, ResultInformation>

    suspend fun registerStep(url: String, token: String, register: Register)

    suspend fun getResultCalculation(
        url: String,
        token: String,
        nss: String,
        savings: DataSavings,
        retirementData: RetirementData,
        informationCalculator: InformationCalculator
    ): Either<Exception, CalculationResult>
}
