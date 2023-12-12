package com.coppel.rhconecta.dev.views.activities

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import com.coppel.rhconecta.dev.domain.home.entity.HomeParams
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import com.coppel.rhconecta.dev.domain.home.use_case.GetHelpDeskServiceAvailabilityUseCase
import com.coppel.rhconecta.dev.domain.home.use_case.GetLocalDataHelpDeskAvailabilityUseCase
import com.coppel.rhconecta.dev.domain.home.use_case.SaveLocalDataHelpDeskAvailabilityUseCase
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import java.util.*
import javax.inject.Inject

class ZendeskViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {


    @Inject
    lateinit var getLocalDataHelpDeskAvailabilityUseCase: GetLocalDataHelpDeskAvailabilityUseCase

    @Inject
    lateinit var saveLocalDataHelpDeskAvailabilityUseCase: SaveLocalDataHelpDeskAvailabilityUseCase

    @Inject
    lateinit var getHelpDeskServiceAvailabilityUseCase: GetHelpDeskServiceAvailabilityUseCase


    private var failure: Failure? = null

    val messageObserver = MutableLiveData<String>()
    val chatObserver = MutableLiveData<Boolean>()

    lateinit var actionsModelCallback: ActionsModelCallback


    /**
     * start handleAvailavityServiceZendesk
     */

    fun setCallBack(actionsModelCallback: ActionsModelCallback) {
        this.actionsModelCallback = actionsModelCallback
    }

    fun clickChatZendesk() {
        getLocalDataHelpDeskAvailability()
    }

    /**
     * Get local user data
     */
    private fun getLocalDataHelpDeskAvailability() {
        getLocalDataHelpDeskAvailabilityUseCase.run(
            UseCase.None.getInstance()
        ) { result: Either<Failure, LocalDataHelpDeskAvailability> ->
            result.fold(
                { failure: Failure ->
                    onLoadGenericFailure(
                        failure
                    )
                }
            ) { result: LocalDataHelpDeskAvailability ->
                onLoadLocalDataHelpDeskSuccess(
                    result
                )
            }
        }
    }

    private fun onLoadLocalDataHelpDeskSuccess(result: LocalDataHelpDeskAvailability) {
        handleIsThereExpirationDate(result)
    }

    private fun handleIsThereExpirationDate(dataHelpDeskAvailability: LocalDataHelpDeskAvailability) {
        val expiredDateInMillis = dataHelpDeskAvailability.expiredDateInMillis
        if (expiredDateInMillis == null || expiredDateInMillis.isEmpty()) {
            downloadExpirationDateHelpDeskService()
        } else {
            handleAccordingExpirationDate(dataHelpDeskAvailability)
        }
    }

    private fun handleAccordingExpirationDate(dataHelpDeskAvailability: LocalDataHelpDeskAvailability) {
        val savedExpiredDateMillis = dataHelpDeskAvailability.expiredDateInMillis?.toLong() ?: 0L
        val currentDate = Date()
        val currentMillis = currentDate.time

        savedExpiredDateMillis > currentMillis
        if (savedExpiredDateMillis > currentMillis) {
            validateAvailableChatAccordingErrorMessage(dataHelpDeskAvailability.errorMessage)
        } else {
            downloadExpirationDateHelpDeskService()
        }
    }

    /**
     * Request at service the waiting time to check help desk availability (zendesk)
     */
    private fun downloadExpirationDateHelpDeskService() {
        val params = HomeParams(getEmployeeNumber(), getAuthHeader(), 58)
        getHelpDeskServiceAvailabilityUseCase.run(
            params
        ) { result: Either<Failure, HelpDeskAvailability> ->
            result.fold(
                { failure: Failure ->
                    onLoadGenericFailure(
                        failure
                    )
                }
            ) { result: HelpDeskAvailability ->
                onLoadDownloadExpirationSuccess(
                    result
                )
            }
        }
    }

    private fun getEmployeeNumber(): String {
        return AppUtilities.getStringFromSharedPreferences(
            sharedPreferences,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        )
    }

    private fun getAuthHeader(): String {
        return AppUtilities.getStringFromSharedPreferences(
            sharedPreferences,
            AppConstants.SHARED_PREFERENCES_TOKEN
        )
    }

    private fun onLoadDownloadExpirationSuccess(result: HelpDeskAvailability) {
        saveLocalDataHelpDesk(result)
    }

    private fun saveLocalDataHelpDesk(helpDeskAvailability: HelpDeskAvailability) {
        val date = helpDeskAvailability.horas.split(":").toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, date[0].toInt())
        calendar.add(Calendar.MINUTE, date[1].toInt())
        calendar.add(Calendar.SECOND, date[2].toInt())
        val expectedDate = calendar.time
        val millisExpected = expectedDate.time.toString()
        val errorMessage = helpDeskAvailability.mensaje
        val localDataHelpDeskAvailability =
            LocalDataHelpDeskAvailability(errorMessage, millisExpected)
        saveLocalDataHelpDeskAvailabilityUseCase.run(localDataHelpDeskAvailability) { result ->
            result.fold({ failure: Failure ->
                onLoadGenericFailure(
                    failure
                )
            }) {
                onLoadSaveLocalDataHelpDesk(
                    errorMessage
                )
            }
        }
    }

    private fun onLoadSaveLocalDataHelpDesk(errorMessage: String) {
        validateAvailableChatAccordingErrorMessage(errorMessage)
    }

    private fun validateAvailableChatAccordingErrorMessage(errorMessage: String?) {
        if (errorMessage.isNullOrEmpty()) {
            actionsModelCallback.launchChat()
        } else {
            errorMessage.let {
                actionsModelCallback.launchMessage(it)
            }
        }
    }


    private fun onLoadGenericFailure(failure: Failure) {
        this.failure = failure
    }


    fun featureIsEnable(featureIsEnable: Boolean) {
        if (featureIsEnable) {
            actionsModelCallback.enableAndRefreshZendeskFeature()
        } else {
            actionsModelCallback.disableZendeskFeature()
        }
    }
}

interface ActionsModelCallback {
    fun launchChat()
    fun launchMessage(value: String)
    fun enableAndRefreshZendeskFeature()
    fun disableZendeskFeature()
}