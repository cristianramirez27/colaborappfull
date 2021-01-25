package com.coppel.rhconecta.dev.data.analytics;

import androidx.annotation.NonNull;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.analytics.model.send_time_by_analytics_flow.SendTimeByAnalyticsFlowRequest;
import com.coppel.rhconecta.dev.data.analytics.model.send_time_by_analytics_flow.SendTimeByAnalyticsFlowResponse;
import com.coppel.rhconecta.dev.data.common.BasicUserInformationFacade;
import com.coppel.rhconecta.dev.domain.analytics.AnalyticsRepository;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/* */
public class AnalyticsRepositoryImpl implements AnalyticsRepository {

    /* */
    private AnalyticsApiService apiService;
    /* */
    private BasicUserInformationFacade basicUserInformationFacade;

    /**
     *
     */
    @Inject
    public AnalyticsRepositoryImpl() {
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(AnalyticsApiService.class);
        basicUserInformationFacade = new BasicUserInformationFacade(CoppelApp.getContext());
    }

    /**
     *
     */
    @Override
    public void sendTimeByAnalyticsFlow(
            AnalyticsFlow analyticsFlow,
            long timeInSecondsByFlow,
            UseCase.OnResultFunction<Either<Failure, UseCase.None>> callback
    ) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        SendTimeByAnalyticsFlowRequest request = new SendTimeByAnalyticsFlowRequest(
                employeeNum,
                analyticsFlow,
                timeInSecondsByFlow
        );

        String authHeader = basicUserInformationFacade.getAuthHeader();
        String url = ServicesConstants.GET_ENDPOINT_SECTION_TIME;
        apiService
                .sendTimeByAnalyticsFlow(authHeader, url, request)
                .enqueue(createSendTimeByAnalyticsFlowCallback(callback));
    }

    /**
     *
     */
    private Callback<SendTimeByAnalyticsFlowResponse> createSendTimeByAnalyticsFlowCallback(
            UseCase.OnResultFunction<Either<Failure, UseCase.None>> callback
    ) {
        return new Callback<SendTimeByAnalyticsFlowResponse>() {

            /**
             *
             */
            @Override
            public void onResponse(
                    @NonNull Call<SendTimeByAnalyticsFlowResponse> call,
                    @NonNull Response<SendTimeByAnalyticsFlowResponse> response
            ) {
                try {
                    SendTimeByAnalyticsFlowResponse body = response.body();
                    assert body != null;
                    SendTimeByAnalyticsFlowResponse.Response responseInstance = body.data.response;
                    if (responseInstance.wasFailed())
                        callback.onResult(getSendTimeByAnalyticsFlowResponse(responseInstance.userMessage));
                    else {
                        Either<Failure, UseCase.None> result =
                                new Either<Failure, UseCase.None>().new Right(UseCase.None.getInstance());
                        callback.onResult(result);
                    }
                } catch (Exception exception) {
                    callback.onResult(getSendTimeByAnalyticsFlowResponse(exception.getMessage()));
                }
            }

            /**
             *
             */
            @Override
            public void onFailure(
                    @NonNull Call<SendTimeByAnalyticsFlowResponse> call,
                    @NonNull Throwable t
            ) {
                callback.onResult(getSendTimeByAnalyticsFlowResponse(t.getMessage()));
            }

            /**
             *
             */
            private Either<Failure, UseCase.None> getSendTimeByAnalyticsFlowResponse(String message) {
                ServerFailure failure = new ServerFailure(message);
                return new Either<Failure, UseCase.None>().new Left(failure);
            }

        };
    }

}
