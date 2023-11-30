package com.coppel.rhconecta.dev.data.calculator

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.coppel.rhconecta.dev.business.utils.ServicesConstants
import com.coppel.rhconecta.dev.data.calculator.dto.*
import com.coppel.rhconecta.dev.domain.calculator.*
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.RetirementData
import retrofit2.HttpException
import retrofit2.Response

class CalculatorRepositoryImpl(private val calculatorApiService: CalculatorApiService) :
    CalculatorRepository {
    override suspend fun getInformationCalculator(
        url: String,
        token: String,
        numberEmployer: String
    ): Either<Exception, InformationCalculator> {
        return baseCommonService(
            calculatorApiService.getInformationCalculator(token, url, RequestCalculator(numberEmployer)),
            { it.data.response.toInformationCalculator() },
            { it.data.response.userMessage }
        )
    }

    override suspend fun getInformationWeek(
        url: String,
        token: String,
        nss: String,
        antiquity: String
    ): Either<Exception, InformationWeek> {
        return baseCommonService(
            calculatorApiService.getListedWeek(token, url, RequestListedWeek(nss, antiquity)),
            { r -> r.data.response.toInformationWeek() },
            { r -> r.data.response.userMessage })
    }

    override suspend fun scoreCalculator(
        url: String,
        token: String,
        scoreCalculator: ScoreCalculator
    ): Either<Exception, ResultInformation> {
        return baseCommonService(
            calculatorApiService.scoreFlow(
                token, url,
                ScoreFlow(scoreCalculator.score, scoreCalculator.number, scoreCalculator.like, scoreCalculator.changed)
            ),
            { r -> r.data.toResultInformation() },
            { r -> r.data.response.userMessage }
        )
    }

    override suspend fun registerStep(
        url: String,
        token: String,
        register: Register
    ) {
        baseCommonService(calculatorApiService.registerStep(
            token,
            url,
            RegisterStep(register.nameEmployer, register.numberEmployer, register.step)
        ),
            {},
            { r -> r.data.response.userMessage })
    }

    override suspend fun getResultCalculation(
        url: String,
        token: String,
        nss: String,
        savings: DataSavings,
        retirementData: RetirementData,
        informationCalculator: InformationCalculator
    ): Either<Exception, CalculationResult> {
        return baseCommonService(
            calculatorApiService.getResultCalculation(token, url, RequestCalculation(
                baseSalary = informationCalculator.imp_salariomensual.parseSaveDouble(),
                dateBorn = informationCalculator.fechaNacimiento?.let { informationCalculator.processDate(it) } ?: "",
                dateStart = informationCalculator.antiguedad?.let { informationCalculator.processDate(it) } ?: "",
                savings = informationCalculator.imp_fondodeahorro.parseSaveDouble(),
                contributionsOrdinary = informationCalculator.imp_aportacionesordinarias.parseSaveDouble(),
                contributionsVoluntary = informationCalculator.imp_aportacionesadicionales.parseSaveDouble(),
                aer = informationCalculator.imp_aer.parseSaveDouble(),
                aer2 = informationCalculator.imp_aer60.parseSaveDouble(),
                initialMonths = informationCalculator.mesesAcumulables.toDouble(),
                directory = informationCalculator.tipoNomina,
                nss = nss,
                salaryBaseContribution = informationCalculator.salarioBase,
                voluntaryContribution = retirementData.percent,
                retirementAge = retirementData.age,
                infonavit = savings.infonavit?.toDouble() ?: 0.0,
                weeks = savings.weeks,
                afore = savings.ordinary.toDouble(),
                aforeVoluntary = savings.voluntary.toDouble()
            )
            ),
            { r -> r.data.response.toMapCalculationResult() },
            { r -> r.data.response.userMessage }
        )
    }

    private fun <T, S> baseCommonService(
        response: Response<S>,
        mapper: (S) -> T,
        mapperError: (S) -> String
    ): Either<Exception, T> where  S : CoppelGeneralParameterResponse {
        return try {
            if (response.isSuccessful && response.body() != null) {
                val responseObject = response.body()!!
                if (responseObject.meta.status.equals(ServicesConstants.SUCCESS)) {
                    Either<Exception, T>().Right(mapper(responseObject))
                } else {
                    Either<Exception, T>().Left(Exception(mapperError(responseObject)))
                }
            } else {
                Either<Exception, T>().Left(HttpException(response))
            }
        } catch (exception: Exception) {
            Either<Exception, T>().Left(exception)
        }
    }
}

fun String?.parseSaveDouble(): Double {
    return try {
        this?.toDouble() ?: 0.0
    } catch (e: Exception) {
        0.0
    }
}