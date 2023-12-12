package com.coppel.rhconecta.dev.framework.home

import android.content.Context
import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.data.home.HomeLocalRepository
import com.coppel.rhconecta.dev.domain.common.Either
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

class HomeLocalRepositoryImpl @Inject constructor(val context: Context) : HomeLocalRepository {

    override suspend fun getPersonalInfo(): Either<Failure, HelpDeskDataRequired> {
        var objectData: ProfileResponse.Response? = null

        val data = getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_PROFILE_RESPONSE
        )

        if (data != null && data.isNotEmpty())
            objectData = Gson().fromJson(data, ProfileResponse.Response::class.java)

        return objectData?.let {
            Either<Failure, HelpDeskDataRequired>().Right(objectData.toHelpDeskDataRequired())
        } ?: Either<Failure, HelpDeskDataRequired>().Left(ServerFailure())
    }

    override suspend fun saveDataHelpDeskAvailability(data: LocalDataHelpDeskAvailability): Either<Failure, Boolean> {
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
        return Either<Failure, Boolean>().Right(true)
    }

    override suspend fun getDataHelpDeskAvailability(): Either<Failure, LocalDataHelpDeskAvailability> {

        val millisExpirationDate = getStringFromSharedPreferences(
            context,
            AppConstants.ZENDESK_EXPECTED_MILLIS,
        )
        val messageError = getStringFromSharedPreferences(
            context,
            AppConstants.ZENDESK_OUT_SERVICE_MESSAGE,
        )

        return Either<Failure, LocalDataHelpDeskAvailability>().Right(
            LocalDataHelpDeskAvailability(
                errorMessage = messageError,
                expiredDateInMillis = millisExpirationDate,
            )
        )
    }
}
