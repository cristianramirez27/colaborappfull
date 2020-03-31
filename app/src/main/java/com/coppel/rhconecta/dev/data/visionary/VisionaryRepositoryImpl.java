package com.coppel.rhconecta.dev.data.visionary;

import android.content.Context;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsResponse;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
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
public class VisionaryRepositoryImpl implements VisionaryRepository {

    /* */
    private VisionaryApiService apiService;
    /* */
    private Context context;

    /**
     *
     *
     */
    @Inject
    public VisionaryRepositoryImpl(){
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(VisionaryApiService.class);
        context = CoppelApp.getContext();
    }

    /**
     *
     * @param callback
     */
    @Override
    public void getVisionariesPreviews(UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 1;
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetVisionariesPreviewsRequest request = new GetVisionariesPreviewsRequest(employeeNum, clvOption);
        apiService.getVisionariesPreviews(
                authHeader,
                ServicesConstants.GET_VISIONARIOS,
                request
        ).enqueue(new Callback<GetVisionariesPreviewsResponse>() {

            @Override
            public void onResponse(Call<GetVisionariesPreviewsResponse> call, Response<GetVisionariesPreviewsResponse> response) {
                GetVisionariesPreviewsResponse body = response.body();
                List<GetVisionariesPreviewsResponse.VisionaryPreviewServer> visionariesPreviewsServer = body.data.response;
                ArrayList<VisionaryPreview> visionariesPreviews = new ArrayList<>();
                for (GetVisionariesPreviewsResponse.VisionaryPreviewServer visionaryPreviewServer: visionariesPreviewsServer)
                    visionariesPreviews.add(visionaryPreviewServer.toVisionaryPreview());
                Either<Failure, List<VisionaryPreview>> result =
                        new Either<Failure, List<VisionaryPreview>>().new Right(visionariesPreviews);
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<GetVisionariesPreviewsResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, List<VisionaryPreview>> result = new Either<Failure, List<VisionaryPreview>>().new Left(failure);
                callback.onResult(result);
            }

        });
    }

    /**
     *
     *
     * @param visionaryId
     * @param callback
     */
    @Override
    public void getVisionaryById(String visionaryId, UseCase.OnResultFunction<Either<Failure, Visionary>> callback) {
        long employeeNum = Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
        int clvOption = 2;
        long visionaryIdInt = Long.parseLong(visionaryId);
        String authHeader = getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
        GetVisionaryByIdRequest request = new GetVisionaryByIdRequest(employeeNum, clvOption, visionaryIdInt);
        apiService.getVisionaryById(
                authHeader,
                ServicesConstants.GET_VISIONARIOS,
                request
        ).enqueue(new Callback<GetVisionaryByIdResponse>() {

            @Override
            public void onResponse(Call<GetVisionaryByIdResponse> call, Response<GetVisionaryByIdResponse> response) {
                GetVisionaryByIdResponse body = response.body();
                GetVisionaryByIdResponse.VisionaryServer visionaryServer = body.data.response;
                Visionary visionary = visionaryServer.toVisionary();
                Either<Failure, Visionary> result = new Either<Failure, Visionary>().new Right(visionary);
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<GetVisionaryByIdResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, Visionary> result = new Either<Failure, Visionary>().new Left(failure);
                callback.onResult(result);
            }

        });
    }

}
