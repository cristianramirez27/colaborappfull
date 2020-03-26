package com.coppel.rhconecta.dev.data.release;

import android.content.Context;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.release.model.get_releases_previews.GetReleasesPreviewsRequest;
import com.coppel.rhconecta.dev.data.release.model.get_releases_previews.GetReleasesPreviewsResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;
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
     * @param callback
     */
    @Override
    public void getReleasesPreviews(UseCase.OnResultFunction<Either<Failure, List<ReleasePreview>>> callback) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 1;
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetReleasesPreviewsRequest request = new GetReleasesPreviewsRequest(employeeNum, clvOption);
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

}
