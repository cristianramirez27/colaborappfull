package com.coppel.rhconecta.dev.data.release;

import android.content.Context;
import android.util.Log;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.release.model.get_release_by_id.GetReleaseByIdRequest;
import com.coppel.rhconecta.dev.data.release.model.get_release_by_id.GetReleaseByIdResponse;
import com.coppel.rhconecta.dev.data.release.model.get_releases_previews.GetReleasesPreviewsRequest;
import com.coppel.rhconecta.dev.data.release.model.get_releases_previews.GetReleasesPreviewsResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.views.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

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
public class ReleaseRepositoryImpl implements ReleaseRepository {

    /* */
    private ReleaseApiService apiService;
    /* */
    private Context context;

    /**
     *
     *
     */
    @Inject
    public ReleaseRepositoryImpl(){
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(ReleaseApiService.class);
        context = CoppelApp.getContext();
    }

    /**
     *
     */
    @Override
    public void getReleasesPreviews(
            AccessOption accessOption,
            UseCase.OnResultFunction<Either<Failure, List<ReleasePreview>>> callback
    ) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 1;
        int accessOptionValue = accessOption.toInt();
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetReleasesPreviewsRequest request = new
                GetReleasesPreviewsRequest(employeeNum, clvOption, accessOptionValue);
        apiService.getReleasesPreviews(
                authHeader,
                ServicesConstants.GET_COMUNICADOS,
                request
        ).enqueue(new Callback<GetReleasesPreviewsResponse>() {

            @Override
            public void onResponse(Call<GetReleasesPreviewsResponse> call, Response<GetReleasesPreviewsResponse> response) {
                GetReleasesPreviewsResponse body = response.body();
                List<GetReleasesPreviewsResponse.ReleasePreviewServer> releasePreviewsServer = body.data.response;
                ArrayList<ReleasePreview> releasePreviews = new ArrayList<>();
                for (GetReleasesPreviewsResponse.ReleasePreviewServer releasePreviewServer: releasePreviewsServer)
                    releasePreviews.add(releasePreviewServer.toReleasePreview());
                Either<Failure, List<ReleasePreview>> result =
                        new Either<Failure, List<ReleasePreview>>().new Right(releasePreviews);
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<GetReleasesPreviewsResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, List<ReleasePreview>> result = new Either<Failure, List<ReleasePreview>>().new Left(failure);
                callback.onResult(result);
            }

        });
    }

    /**
     *
     */
    @Override
    public void getReleaseById(
            int releaseId,
            AccessOption accessOption,
            UseCase.OnResultFunction<Either<Failure, Release>> callback
    ) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 2;
        int accessOptionValue = accessOption == AccessOption.BANNER ? accessOption.toInt() : 0;
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetReleaseByIdRequest request = new
                GetReleaseByIdRequest(employeeNum, clvOption, releaseId, accessOptionValue);
        apiService.getReleaseById(
                authHeader,
                ServicesConstants.GET_COMUNICADOS,
                request
        ).enqueue(new Callback<GetReleaseByIdResponse>() {

            @Override
            public void onResponse(Call<GetReleaseByIdResponse> call, Response<GetReleaseByIdResponse> response) {
                GetReleaseByIdResponse body = response.body();
                Release release = body.data.response.toRelease();
                Either<Failure, Release> result = new Either<Failure, Release>().new Right(release);
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<GetReleaseByIdResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, Release> result = new Either<Failure, Release>().new Left(failure);
                callback.onResult(result);
            }

        });
    }

}
