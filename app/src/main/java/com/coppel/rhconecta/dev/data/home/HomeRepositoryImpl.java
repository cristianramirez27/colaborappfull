package com.coppel.rhconecta.dev.data.home;

import android.content.Context;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationRequest;
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.domain.home.HomeRepository;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.views.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.coppel.rhconecta.dev.views.utils.AppUtilities.getStringFromSharedPreferences;

/**
 *
 *
 */
public class HomeRepositoryImpl implements HomeRepository {

    /* */
    private HomeApiService apiService;
    /* */
    private Context context;

    /**
     *
     *
     */
    @Inject public HomeRepositoryImpl(){
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(HomeApiService.class);
        context = CoppelApp.getContext();
    }

    /**
     *
     * @param callback
     * @return
     */
    @Override
    public void getBanners(
            UseCase.OnResultFunction<Either<Failure, List<Banner>>> callback
    ) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 1;
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetMainInformationRequest request = new GetMainInformationRequest(employeeNum, clvOption);

        apiService.getMainInformation(
                authHeader,
                ServicesConstants.GET_HOME,
                request
        ).enqueue(new Callback<GetMainInformationResponse>() {

            @Override
            public void onResponse(Call<GetMainInformationResponse> call, Response<GetMainInformationResponse> response) {
                GetMainInformationResponse body = response.body();
                List<GetMainInformationResponse.BannerServer> carrusel = body.data.response.Carrusel;
                ArrayList<Banner> banners = new ArrayList<>();
                for (GetMainInformationResponse.BannerServer bannerServer: carrusel)
                    banners.add(bannerServer.toBanner());
                Either<Failure, List<Banner>> result = new Either<Failure, List<Banner>>().new Right(banners);
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<GetMainInformationResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, List<Banner>> result = new Either<Failure, List<Banner>>().new Left(failure);
                callback.onResult(result);
            }

        });
    }

    /**
     *
     * @param callback
     * @return
     */
    @Override
    public void getBadges(
            UseCase.OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>> callback
    ) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 1;
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetMainInformationRequest request = new GetMainInformationRequest(employeeNum, clvOption);

        apiService.getMainInformation(
                authHeader,
                ServicesConstants.GET_HOME,
                request
        ).enqueue(new Callback<GetMainInformationResponse>() {

            @Override
            public void onResponse(Call<GetMainInformationResponse> call, Response<GetMainInformationResponse> response) {
                GetMainInformationResponse body = response.body();
                assert body != null;
                HashMap<Badge.Type, Badge> badges = new HashMap<>();
                badges.put(Badge.Type.RELEASE, body.data.response.Badges.getReleaseBadge());
                badges.put(Badge.Type.VISIONARY, body.data.response.Badges.getVisionaryBadge());
                badges.put(Badge.Type.COLLABORATOR_AT_HOME, body.data.response.Badges.getCollaboratorAtHomeBadge());
                badges.put(Badge.Type.POLL, body.data.response.Badges.getPollBadge());
                Either<Failure, Map<Badge.Type, Badge>> result =
                        new Either<Failure, Map<Badge.Type, Badge>>().new Right(badges);
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<GetMainInformationResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, Map<Badge.Type, Badge>> result =
                        new Either<Failure, Map<Badge.Type, Badge>>().new Left(failure);
                callback.onResult(result);
            }

        });
    }

}
