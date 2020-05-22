package com.coppel.rhconecta.dev.data.poll;

import android.util.Log;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.common.BasicUserInformationFacade;
import com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count.GetAvailablePollCountRequest;
import com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count.GetAvailablePollCountResponse;
import com.coppel.rhconecta.dev.data.poll.model.get_poll.GetPollRequest;
import com.coppel.rhconecta.dev.data.poll.model.get_poll.GetPollResponse;
import com.coppel.rhconecta.dev.data.poll.model.send_poll.SendPollRequest;
import com.coppel.rhconecta.dev.data.poll.model.send_poll.SendPollResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.domain.poll.PollRepository;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;
import com.coppel.rhconecta.dev.domain.poll.entity.Question;
import com.coppel.rhconecta.dev.domain.poll.failure.NotPollAvailableFailure;
import com.coppel.rhconecta.dev.domain.poll.use_case.GetPollUseCase;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 *
 */
public class PollRepositoryImpl implements PollRepository {

    /* */
    private PollApiService apiService;
    /* */
    private BasicUserInformationFacade basicUserInformationFacade;

    /* */
    @Inject
    public PollRepositoryImpl() {
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(PollApiService.class);
        basicUserInformationFacade = new BasicUserInformationFacade(CoppelApp.getContext());
    }

    /**
     *
     *
     */
    @Override
    public void getPoll(UseCase.OnResultFunction<Either<Failure, Poll>> callback) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        int clvOption = 1;
        String authHeader = basicUserInformationFacade.getAuthHeader();
        GetPollRequest request = new GetPollRequest(employeeNum, clvOption);
        apiService
                .getPoll(authHeader, ServicesConstants.GET_ENCUESTAS, request)
                .enqueue(getCallbackGetPollResponse(callback));
    }

    /**
     *
     *
     */
    private Callback<GetPollResponse> getCallbackGetPollResponse(
            UseCase.OnResultFunction<Either<Failure, Poll>> callback
    ) {
        return new Callback<GetPollResponse>() {
            @Override
            public void onResponse(Call<GetPollResponse> call, Response<GetPollResponse> response) {
                try {
                    GetPollResponse body = response.body();
                    assert body != null;
                    GetPollResponse.Response responseInstance = body.data.response;
                    if(responseInstance.wasFailed())
                        callback.onResult(getNotPollAvailableFailure(responseInstance.mensaje));
                    else {
                        Poll poll = responseInstance.toPoll();
                        Either<Failure, Poll> result = new Either<Failure, Poll>().new Right(poll);
                        callback.onResult(result);
                    }
                } catch (Exception exception){
                    Log.e(getClass().getName(), exception.toString());
                    callback.onResult(getNotPollAvailableFailure(exception.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<GetPollResponse> call, Throwable t) {
                callback.onResult(getNotPollAvailableFailure(t.getMessage()));
            }

            private Either<Failure, Poll> getNotPollAvailableFailure(String message) {
                NotPollAvailableFailure failure = new NotPollAvailableFailure(message);
                return new Either<Failure, Poll>().new Left(failure);
            }
        };
    }

    /**
     *
     *
     */
    @Override
    public void sendPoll(Poll poll, UseCase.OnResultFunction<Either<Failure, UseCase.None>> callback) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        int clvOption = 2;
        String authHeader = basicUserInformationFacade.getAuthHeader();
        ArrayList<SendPollRequest.AnswerServer> answersServer = new ArrayList<>();
        for(Question question : poll.getQuestions())
            answersServer.add(SendPollRequest.AnswerServer.fromQuestion(question));
        SendPollRequest request = new SendPollRequest(employeeNum, answersServer, clvOption);
        apiService
                .sendPoll(authHeader, ServicesConstants.GET_ENCUESTAS, request)
                .enqueue(getCallbackSendPollResponse(callback));

    }

    /**
     *
     *
     */
    private Callback<SendPollResponse> getCallbackSendPollResponse(
            UseCase.OnResultFunction<Either<Failure, UseCase.None>> callback
    ) {
        return new Callback<SendPollResponse>() {
            @Override
            public void onResponse(Call<SendPollResponse> call, Response<SendPollResponse> response) {
                SendPollResponse body = response.body();
                assert body != null;
                Either<Failure, UseCase.None> result;
                if(body.meta.isSuccess())
                    result = new Either<Failure, UseCase.None>()
                            .new Right(UseCase.None.getInstance());
                else {
                    Failure failure = new ServerFailure();
                    result = new Either<Failure, UseCase.None>()
                            .new Left(failure);
                }
                callback.onResult(result);
            }

            @Override
            public void onFailure(Call<SendPollResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, UseCase.None> result = new Either<Failure, UseCase.None>().new Left(failure);
                callback.onResult(result);
            }
        };
    }

    /**
     *
     *
     */
    @Override
    public void getAvailablePollCount(UseCase.OnResultFunction<Either<Failure, Integer>> callback) {
        long employeeNum = basicUserInformationFacade.getEmployeeNum();
        int clvOption = 1;
        String authHeader = basicUserInformationFacade.getAuthHeader();
        GetAvailablePollCountRequest request = new GetAvailablePollCountRequest(employeeNum, clvOption);
        apiService.getAvailablePollCount(
                authHeader,
                ServicesConstants.GET_HOME,
                request
        ).enqueue(getAvailablePollCountResponseCallback(callback));
    }

    /**
     *
     *
     */
    private Callback<GetAvailablePollCountResponse> getAvailablePollCountResponseCallback(
            UseCase.OnResultFunction<Either<Failure, Integer>> callback
    ) {
        return new Callback<GetAvailablePollCountResponse>() {
            @Override
            public void onResponse(Call<GetAvailablePollCountResponse> call, Response<GetAvailablePollCountResponse> response) {
                try {
                    GetAvailablePollCountResponse body = response.body();
                    assert body != null;
                    int availablePollCount = body.data.response.Badges.opc_encuesta;
                    Either<Failure, Integer> result = new Either<Failure, Integer>().new Right(availablePollCount);
                    callback.onResult(result);
                } catch (Exception e){
                    Failure failure = new ServerFailure();
                    Either<Failure, Integer> result = new Either<Failure, Integer>().new Left(failure);
                    callback.onResult(result);
                }
            }

            @Override
            public void onFailure(Call<GetAvailablePollCountResponse> call, Throwable t) {
                Failure failure = new ServerFailure();
                Either<Failure, Integer> result = new Either<Failure, Integer>().new Left(failure);
                callback.onResult(result);
            }
        };
    }

}
