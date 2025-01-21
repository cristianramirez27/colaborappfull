package com.coppel.rhconecta.dev.data.home

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase.OnResultFunction
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.Badge
import com.coppel.rhconecta.dev.domain.home.entity.Banner
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability

/**
 *
 */
interface HomeRepository {
    /**
     *
     */
    fun getBanners(
        employeeNum: String,
        authHeader: String,
        callback: OnResultFunction<Either<Failure, List<Banner>>>,
    )

    /**
     *
     */
    fun getBadges(
        employeeNum: String,
        authHeader: String,
        callback: OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>>,
    )

    /**
     * Gets data for help desk availability.
     */
    suspend fun getHelpDeskServiceAvailability(
        employeeNum: String,
        authHeader: String,
    ): Either<Failure, HelpDeskAvailability>
}