package com.coppel.rhconecta.dev.framework.home

import android.content.Context
import com.coppel.rhconecta.dev.CoppelApp
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.business.utils.ServicesConstants
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager
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
import com.coppel.rhconecta.dev.framework.DataResponse
import com.coppel.rhconecta.dev.framework.toHelpDeskAvailabilityDomain
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {
    /* */
    private val apiService: HomeApiService

    /* */
    private val context: Context

    /**
     *
     */
    init {
        val retrofit = ServicesRetrofitManager.getInstance().retrofitAPI
        apiService = retrofit.create(HomeApiService::class.java)
        context = CoppelApp.getContext()
    }

    /**
     *
     */
    override fun getBanners(
        callback: OnResultFunction<Either<Failure, List<Banner>>>,
    ) {
        val employeeNum = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ).toLong()
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
        apiService.getMainInformation(
            authHeader,
            ServicesConstants.GET_HOME,
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
        callback: OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>>,
    ) {
        val employeeNum = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ).toLong()
        val clvOption = 1
        val authHeader = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_TOKEN
        )
        val request = GetMainInformationRequest(employeeNum, clvOption)
        apiService.getMainInformation(
            authHeader,
            ServicesConstants.GET_HOME,
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

    override suspend fun getHelpDeskServiceAvailability(): Either<Failure, HelpDeskAvailability> {
        val employeeNumStr = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        )
        val authHeader = AppUtilities.getStringFromSharedPreferences(
            context,
            AppConstants.SHARED_PREFERENCES_TOKEN
        )
        val clvOption = 58
        val request = HomeRequest(employeeNumStr, "", authHeader, clvOption, AppConfig.ANDROID_OS)

        return withContext(Dispatchers.IO) {
            lateinit var result: Either<Failure, HelpDeskAvailability>

            apiService.getHelpDeskServiceAvailability(
                authHeader,
                ServicesConstants.GET_HELP_DESK_SERVICE_AVAILABILITY,
                request
            ).enqueue(object : Callback<DataResponse<List<HelpDeskAvailabilityServer>>> {
                override fun onResponse(
                    call: Call<DataResponse<List<HelpDeskAvailabilityServer>>>,
                    response: Response<DataResponse<List<HelpDeskAvailabilityServer>>>,
                ) {

                    result = try {
                        val response =
                            response.body()!!.data.response.toHelpDeskAvailabilityDomain()
                        Either<Failure, HelpDeskAvailability>().Right(response)
                    } catch (e: Exception) {
                        Either<Failure, HelpDeskAvailability>().Left(ServerFailure())
                    }
                }

                override fun onFailure(
                    call: Call<DataResponse<List<HelpDeskAvailabilityServer>>>,
                    t: Throwable,
                ) {
                    result = Either<Failure, HelpDeskAvailability>().Left(ServerFailure())
                }
            })
            result
        }
    }
}
