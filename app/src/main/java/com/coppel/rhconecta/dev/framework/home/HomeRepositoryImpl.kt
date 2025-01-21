package com.coppel.rhconecta.dev.framework.home

import android.content.Context
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.business.utils.ServicesConstants
import com.coppel.rhconecta.dev.data.common.getOnFailureResponse
import com.coppel.rhconecta.dev.data.home.HomeRepository
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationRequest
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationResponse
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase.OnResultFunction
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure
import com.coppel.rhconecta.dev.domain.home.entity.Badge
import com.coppel.rhconecta.dev.domain.home.entity.Banner
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import com.coppel.rhconecta.dev.domain.movements.GetMovementsFailure
import com.coppel.rhconecta.dev.framework.retrofitApiCall
import com.coppel.rhconecta.dev.framework.toHelpDeskAvailabilityDomain
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: HomeApiService,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: FirebaseAnalytics,
) : HomeRepository {

    /**
     *
     */
    override fun getBanners(
        employeeNum: String,
        authHeader: String,
        callback: OnResultFunction<Either<Failure, List<Banner>>>,
    ) {
        val employeeNum = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        )
        val clvOption = 1
        val authHeader = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_TOKEN
        )
        FirebaseCrashlytics.getInstance().setUserId(
            AppUtilities.getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
            )
        )
        FirebaseAnalytics.getInstance(context).setUserId(
            AppUtilities.getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
            )
        )
        val request = GetMainInformationRequest(employeeNum, clvOption)
        val url =
            if ((ServicesConstants.GET_HOME == null || ServicesConstants.GET_HOME.isEmpty())) ServicesConstants.GET_HOME_LOCAL else ServicesConstants.GET_HOME
        apiService.getMainInformation(
            authHeader,
            "2024-03-25T17:38:35.244Z",
            "-99.985171",
            "20.270460",
            "fs9999c7q86c33cdfd5f55",
            url,
            request
        ).enqueue(object : Callback<GetMainInformationResponse?> {
            override fun onResponse(
                call: Call<GetMainInformationResponse?>,
                response: Response<GetMainInformationResponse?>,
            ) {
                try {
                    val body = response.body()
                    val carrusel = body!!.data.response.Carrusel
                    val banners = ArrayList<Banner>()
                    for (bannerServer in carrusel) banners.add(bannerServer.toBanner())
                    val result: Either<Failure, List<Banner>> =
                        Either<Failure, List<Banner>>().Right(banners)
                    callback.onResult(result)
                } catch (e: Exception) {
                    val failure: Failure = ServerFailure(e.message)
                    val result: Either<Failure, List<Banner>> =
                        Either<Failure, List<Banner>>().Left(failure)
                    callback.onResult(result)
                }
            }

            override fun onFailure(call: Call<GetMainInformationResponse?>, t: Throwable) {
                val failure: Failure = ServerFailure()
                val result: Either<Failure, List<Banner>> =
                    Either<Failure, List<Banner>>().Left(failure)
                callback.onResult(result)
            }
        })
    }

    /**
     *
     */
    override fun getBadges(
        employeeNum: String,
        authHeader: String,
        callback: OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>>,
    ) {
        val employeeNum = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        )
        val clvOption = 1
        val authHeader = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_TOKEN
        )
        val url =
            if ((ServicesConstants.GET_HOME == null || ServicesConstants.GET_HOME.isEmpty())) ServicesConstants.GET_HOME_LOCAL else ServicesConstants.GET_HOME
        val request = GetMainInformationRequest(employeeNum, clvOption)
        apiService.getMainInformation(
            authHeader,
            "2024-03-25T17:38:35.244Z",
            "-99.985171",
            "20.270460",
            "fs9999c7q86c33cdfd5f55",
            url,
            request
        ).enqueue(object : Callback<GetMainInformationResponse?> {
            override fun onResponse(
                call: Call<GetMainInformationResponse?>,
                response: Response<GetMainInformationResponse?>,
            ) {
                try {
                    val body = response.body()!!
                    AppUtilities.saveStringInSharedPreferences(
                        context,
                        AppConstants.ZENDESK_FEATURE,
                        body.data.response.clv_chatActivo
                    )
                    val badges = HashMap<Badge.Type, Badge>()
                    badges[Badge.Type.RELEASE] = body.data.response.Badges.releaseBadge
                    badges[Badge.Type.VISIONARY] = body.data.response.Badges.visionaryBadge
                    badges[Badge.Type.COLLABORATOR_AT_HOME] =
                        body.data.response.Badges.collaboratorAtHomeBadge
                    badges[Badge.Type.POLL] = body.data.response.Badges.pollBadge
                    val result: Either<Failure, Map<Badge.Type, Badge>> =
                        Either<Failure, Map<Badge.Type, Badge>>().Right(badges)
                    callback.onResult(result)
                } catch (e: Exception) {
                    val failure: Failure = ServerFailure()
                    val result: Either<Failure, Map<Badge.Type, Badge>> =
                        Either<Failure, Map<Badge.Type, Badge>>().Left(failure)
                    callback.onResult(result)
                }
            }

            override fun onFailure(call: Call<GetMainInformationResponse?>, t: Throwable) {
                val failure: Failure = ServerFailure()
                val result: Either<Failure, Map<Badge.Type, Badge>> =
                    Either<Failure, Map<Badge.Type, Badge>>().Left(failure)
                callback.onResult(result)
            }
        })
    }

    override suspend fun getHelpDeskServiceAvailability(
        employeeNum: String,
        authHeader: String,
    ): Either<Failure, HelpDeskAvailability> {
        return try {
            retrofitApiCall {
                apiService.getHelpDeskServiceAvailability(
                    authHeader,
                    ServicesConstants.GET_HELP_DESK_SERVICE_AVAILABILITY,
                    HomeRequest(employeeNum, "", authHeader, OPTION_ZENDESK, AppConfig.ANDROID_OS)
                )
            }.let {
                try {
                    val response =
                        it?.data?.response?.toHelpDeskAvailabilityDomain()
                    Either<Failure, HelpDeskAvailability>().Right(response)
                } catch (e: Exception) {
                    Either<Failure, HelpDeskAvailability>().Left(ServerFailure())
                }
            }
        } catch (e: Exception) {
            val error = e.getOnFailureResponse()
            val failure: Failure = GetMovementsFailure(error.name)
            Either<Failure, HelpDeskAvailability>().Left(failure)
        }
    }

    companion object {
        const val OPTION_ZENDESK = 58
        const val OPTION_BADGES = 1
    }
}
