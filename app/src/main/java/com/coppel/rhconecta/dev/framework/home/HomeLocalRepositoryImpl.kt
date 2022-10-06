package com.coppel.rhconecta.dev.framework.home

import android.content.Context
import com.coppel.rhconecta.dev.CoppelApp
import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.data.home.HomeLocalRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase.OnResultFunction
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import com.coppel.rhconecta.dev.framework.toHelpDeskDataRequired
import com.coppel.rhconecta.dev.views.modelview.HelpDeskDataRequired
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities.getStringFromSharedPreferences
import com.coppel.rhconecta.dev.views.utils.AppUtilities.saveStringInSharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

class HomeLocalRepositoryImpl @Inject constructor() : HomeLocalRepository {

    private val context: Context = CoppelApp.getContext()

    override fun getPersonalInfo(callback: OnResultFunction<Either<Failure, HelpDeskDataRequired>>) {
        var objectData: ProfileResponse.Response? = null
        val failure: Failure = ServerFailure()

        val data = getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE
        )

        if (data != null && data.isNotEmpty())
            objectData = Gson().fromJson(data, ProfileResponse.Response::class.java)

        val result: Either<Failure, HelpDeskDataRequired> =
            objectData?.let {
                Either<Failure, HelpDeskDataRequired>().Right(objectData.toHelpDeskDataRequired())
            } ?: Either<Failure, HelpDeskDataRequired>().Left(failure)

        callback.onResult(result)
    }

    override fun saveDataHelpDeskAvailability(
        callback: OnResultFunction<Either<Failure, Boolean>>,
        data: LocalDataHelpDeskAvailability,
    ) {
        saveStringInSharedPreferences(
            context,
            AppConstants.ZENDESK_EXPECTED_MILLIS,
            data.expiredDateInMillis
        )
        saveStringInSharedPreferences(
            context,
            AppConstants.ZENDESK_OUT_SERVICE_MESSAGE,
            data.errorMessage
        )
        val result = Either<Failure, Boolean>().Right(true)
        callback.onResult(result)
    }

    override fun getDataHelpDeskAvailability(callback: OnResultFunction<Either<Failure, LocalDataHelpDeskAvailability>>) {

        val millisExpirationDate = getStringFromSharedPreferences(
            context,
            AppConstants.ZENDESK_EXPECTED_MILLIS,
        )
        val messageError = getStringFromSharedPreferences(
            context,
            AppConstants.ZENDESK_OUT_SERVICE_MESSAGE,
        )

        val result = Either<Failure, LocalDataHelpDeskAvailability>().Right(
            LocalDataHelpDeskAvailability(
                errorMessage = messageError,
                expiredDateInMillis = millisExpirationDate,
            )
        )
        callback.onResult(result)
    }
}
