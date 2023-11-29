package com.coppel.rhconecta.dev.views.activities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import com.coppel.rhconecta.dev.domain.home.use_case.GetHelpDeskServiceAvailabilityUseCase
import com.coppel.rhconecta.dev.domain.home.use_case.GetLocalDataHelpDeskAvailabilityUseCase
import com.coppel.rhconecta.dev.domain.home.use_case.SaveLocalDataHelpDeskAvailabilityUseCase
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class ZendeskViewModel @Inject constructor() {


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

    fun clickChatZendesk(configuration: JsonObject) {
        getLocalDataHelpDeskAvailability(configuration)
    }

    /**
     * Get local user data
     */
    private fun getLocalDataHelpDeskAvailability(configuration: JsonObject) {

        //val mensaje: String = configuration.get("mensaje").asString

        //val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault()).format(Date())
        var mensaje = ""
        val currentTime = Calendar.getInstance().time
        Log.i("prueba", "currentTime: $currentTime")
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        Log.i("prueba", "currentTime: $currentDate")
        //Obtener dia actual
        val dayOfWeek = "dia_" + Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        Log.i("prueba", "diaActual: $currentDate")
        // Obtener la hora del JSON
        //val horaInicio = "8:00:00"
        //val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val horaInicio = currentDate + " " + configuration.get("inicio").asString
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val dateInicio = format.parse(horaInicio)
        Log.i("prueba", "dateInicio json: $dateInicio")

        val horaFin = currentDate + " " + configuration.get("fin").asString
        val dateFin = format.parse(horaFin)
        Log.i("prueba", "dateFin json: $dateFin")

        if (configuration.get("clv_activo").asInt == 0) {
            Log.i("prueba", " estas en el dia domingo ")
            mensaje = configuration.get("mensaje").asString
            validateAvailableChatAccordingErrorMessage(mensaje)
        } else if (dateInicio!!.before(currentTime) && dateFin!!.after(currentTime)) {
            Log.i("prueba", " estas en el horario adecuado")
            mensaje = ""
            validateAvailableChatAccordingErrorMessage(mensaje)
        } else {
            Log.i("prueba", "No estas en el horario adecuado")
            mensaje = configuration.get("mensaje").asString
            validateAvailableChatAccordingErrorMessage(mensaje)
        }

        //validateAvailableChatAccordingErrorMessage(mensaje)

        /*getLocalDataHelpDeskAvailabilityUseCase.run(
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
        }*/
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
        getHelpDeskServiceAvailabilityUseCase.run(
                UseCase.None.getInstance()
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