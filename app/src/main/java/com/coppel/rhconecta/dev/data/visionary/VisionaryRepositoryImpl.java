package com.coppel.rhconecta.dev.data.visionary;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.common.BasicUserInformationFacade;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsResponse;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdResponse;
import com.coppel.rhconecta.dev.data.visionary.model.update_visionary_status_by_id.UpdateVisionaryStatusByIdRequest;
import com.coppel.rhconecta.dev.data.visionary.model.update_visionary_status_by_id.UpdateVisionaryStatusByIdResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/* */
public class VisionaryRepositoryImpl implements VisionaryRepository {

    /* */
    private final VisionaryApiService apiService;

    /* */
    private final BasicUserInformationFacade basicUserInformationFacade;

    /**
     *
     */
    @Inject
    public VisionaryRepositoryImpl() {
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(VisionaryApiService.class);
        basicUserInformationFacade = new BasicUserInformationFacade(CoppelApp.getContext());
    }

    /**
     *
     */
    @Override
    public void getVisionariesPreviews(
            VisionaryType type,
            AccessOption accessOption,
            UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback
    ) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        int clvOption = 1;
        String authHeader = basicUserInformationFacade.getAuthHeader();
        GetVisionariesPreviewsRequest request =
                new GetVisionariesPreviewsRequest(employeeNum, clvOption);
        String url = type == VisionaryType.VISIONARIES ?
                ServicesConstants.GET_VISIONARIOS : ServicesConstants.GET_VISIONARIOS_STAY_HOME;
        apiService.getVisionariesPreviews(authHeader, url, request)
                .enqueue(getCallbackGetVisionariesPreviewsResponse(callback));
    }

    /**
     *
     */
    private Callback<GetVisionariesPreviewsResponse> getCallbackGetVisionariesPreviewsResponse(
            UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback
    ) {
        return new Callback<GetVisionariesPreviewsResponse>() {
            @Override
            public void onResponse(Call<GetVisionariesPreviewsResponse> call, Response<GetVisionariesPreviewsResponse> response) {
                try {
                    GetVisionariesPreviewsResponse body = response.body();
                    assert body != null;
                    List<GetVisionariesPreviewsResponse.VisionaryPreviewServer> visionariesPreviewsServer = body.data.response;
                    ArrayList<VisionaryPreview> visionariesPreviews = new ArrayList<>();
                    for (GetVisionariesPreviewsResponse.VisionaryPreviewServer visionaryPreviewServer : visionariesPreviewsServer)
                        visionariesPreviews.add(visionaryPreviewServer.toVisionaryPreview());
                    Either<Failure, List<VisionaryPreview>> result =
                            new Either<Failure, List<VisionaryPreview>>().new Right(visionariesPreviews);
                    callback.onResult(result);
                } catch (Exception exception) {
                    callback.onResult(getServerFailure());
                }

            }

            @Override
            public void onFailure(Call<GetVisionariesPreviewsResponse> call, Throwable t) {
                callback.onResult(getServerFailure());
            }

            private Either<Failure, List<VisionaryPreview>> getServerFailure() {
                Failure failure = new ServerFailure();
                return new Either<Failure, List<VisionaryPreview>>().new Left(failure);
            }
        };
    }

    /**
     *
     */
    @Override
    public void getVisionaryById(
            VisionaryType type,
            String visionaryId,
            AccessOption accessOption,
            UseCase.OnResultFunction<Either<Failure, Visionary>> callback
    ) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        int clvOption = 2;
        long visionaryIdInt = Long.parseLong(visionaryId);

        String authHeader = basicUserInformationFacade.getAuthHeader();
        GetVisionaryByIdRequest request =
                new GetVisionaryByIdRequest(employeeNum, clvOption, visionaryIdInt);
        String url = type == VisionaryType.VISIONARIES ?
                ServicesConstants.GET_VISIONARIOS : ServicesConstants.GET_VISIONARIOS_STAY_HOME;
        apiService.getVisionaryById(
                authHeader,
                url,
                request
        ).enqueue(getCallbackGetVisionaryByIdResponse(callback));
    }

    /**
     *
     */
    private Callback<GetVisionaryByIdResponse> getCallbackGetVisionaryByIdResponse(
            UseCase.OnResultFunction<Either<Failure, Visionary>> callback
    ) {
        return new Callback<GetVisionaryByIdResponse>() {

            @Override
            public void onResponse(Call<GetVisionaryByIdResponse> call, Response<GetVisionaryByIdResponse> response) {
                try {
                    GetVisionaryByIdResponse body = response.body();
                    assert body != null;
                    GetVisionaryByIdResponse.VisionaryServer visionaryServer = body.data.response;
                    Visionary visionary = visionaryServer.toVisionary();
                    Either<Failure, Visionary> result = new Either<Failure, Visionary>().new Right(visionary);
                    callback.onResult(result);
                } catch (Exception exception) {
                    callback.onResult(getServerFailure());
                }
            }

            @Override
            public void onFailure(Call<GetVisionaryByIdResponse> call, Throwable t) {
                callback.onResult(getServerFailure());

            }

            private Either<Failure, Visionary> getServerFailure() {
                Failure failure = new ServerFailure();
                return new Either<Failure, Visionary>().new Left(failure);
            }
        };
    }

    /**
     *
     */
    @Override
    public void updateVisionaryStatusById(
            VisionaryType type,
            String visionaryId,
            Visionary.RateStatus status,
            String rateOptionId,
            UseCase.OnResultFunction<Either<Failure, Visionary.RateStatus>> callback
    ) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        int clvOption = 3;
        int clvType = status == Visionary.RateStatus.LIKED ? 5 : 6;
        long visionaryIdInt = Long.parseLong(visionaryId);
        Integer rateOptionIdAsInteger = null;
        if (rateOptionId != null)
            rateOptionIdAsInteger = Integer.parseInt(rateOptionId);
        String authHeader = basicUserInformationFacade.getAuthHeader();

        UpdateVisionaryStatusByIdRequest request = new UpdateVisionaryStatusByIdRequest(
                visionaryIdInt,
                employeeNum,
                clvOption,
                clvType,
                rateOptionIdAsInteger
        );

        String url = type == VisionaryType.VISIONARIES ?
                ServicesConstants.GET_VISIONARIOS : ServicesConstants.GET_VISIONARIOS_STAY_HOME;

        apiService.updateVisionaryStatusById(authHeader, url, request)
                .enqueue(getCallbackUpdateVisionaryStatusByIdResponse(status, callback));
    }


    /**
     *
     */
    private Callback<UpdateVisionaryStatusByIdResponse> getCallbackUpdateVisionaryStatusByIdResponse(
            Visionary.RateStatus status,
            UseCase.OnResultFunction<Either<Failure, Visionary.RateStatus>> callback
    ) {
        return new Callback<UpdateVisionaryStatusByIdResponse>() {

            @Override
            public void onResponse(Call<UpdateVisionaryStatusByIdResponse> call, Response<UpdateVisionaryStatusByIdResponse> response) {
                try {
                    Either<Failure, Visionary.RateStatus> result =
                            new Either<Failure, Visionary.RateStatus>().new Right(status);
                    callback.onResult(result);
                } catch (Exception exception) {
                    callback.onResult(getServerFailure());
                }
            }

            @Override
            public void onFailure(Call<UpdateVisionaryStatusByIdResponse> call, Throwable t) {
                callback.onResult(getServerFailure());
            }

            private Either<Failure, Visionary.RateStatus> getServerFailure() {
                Failure failure = new ServerFailure();
                return new Either<Failure, Visionary.RateStatus>().new Left(failure);
            }
        };
    }

}
