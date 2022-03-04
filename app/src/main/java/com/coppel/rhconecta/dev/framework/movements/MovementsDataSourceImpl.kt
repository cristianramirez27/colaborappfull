package com.coppel.rhconecta.dev.framework.movements

import android.content.Context
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.utils.ServicesConstants
import com.coppel.rhconecta.dev.data.common.TypeError
import com.coppel.rhconecta.dev.data.common.getOnFailureResponse
import com.coppel.rhconecta.dev.data.movements.MovementsDataResources
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsFailure
import com.coppel.rhconecta.dev.domain.movements.GetMovementsResponse
import com.coppel.rhconecta.dev.framework.retrofitApiCall
import com.coppel.rhconecta.dev.framework.toMovementsDomainList
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities

/**
Get data implementation
 * */
class MovementsDataSourceImpl(
    private val context: Context,
    private val movementsApiService: APIService,
) : MovementsDataResources {

    override suspend fun getMovements(): Either<Failure, GetMovementsResponse> =
        try {
            val authHeader: String = getAuthHeader()
            val request = GetMovementsRequest(getEmployeeNumber())
            val url = ServicesConstants.GET_MY_MOVEMENTS
            retrofitApiCall {
                movementsApiService.getMovements(
                    url,
                    authHeader,
                    request
                )
            }.let {
                val code = it?.data?.response?.code.orEmpty()
                val dataList: List<Movement> = if (code == NO_MOVEMENTS_GENERATED) {
                    emptyList()
                } else {
                    it?.data?.response?.toMovementsDomainList().orEmpty()
                }

                val message = it?.data?.response?.descriptionMessage.orEmpty()
                val response = GetMovementsResponse(code, dataList, message)

                Either<Failure, GetMovementsResponse>().Right(response)
            }
        } catch (e: Exception) {
            val error = e.getOnFailureResponse()
            val message = getMessageFailure(error)
            val failure: Failure = GetMovementsFailure(message)
            Either<Failure, GetMovementsResponse>().Left(failure)
        }

    private fun getMessageFailure(error: TypeError): String {
        val id = when (error) {
            TypeError.ERROR_CONNECTION -> R.string.network_error
            TypeError.ERROR_UNAVAILABLE -> R.string.error_generic_service
            TypeError.ERROR_BAD_FORMAT -> R.string.error_serialization
            TypeError.ERROR_UNKNOWN_ERROR -> R.string.error_unknown
        }
        return context.getString(id)
    }


    /** */
    private fun getAuthHeader(): String =
        AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_TOKEN
        )

    private fun getEmployeeNumber(): String =
        AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        )

    companion object {
        const val MOVEMENTS_END_MONT = "01"
        const val NO_MOVEMENTS_GENERATED = "00"
    }
}
