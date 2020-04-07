package com.coppel.rhconecta.dev.data.poll;

import android.content.Context;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
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
import com.coppel.rhconecta.dev.domain.poll.use_case.SendPollUseCase;
import com.coppel.rhconecta.dev.views.utils.AppConstants;

import java.util.ArrayList;

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
public class PollRepositoryImpl implements PollRepository {

    /* */
    private PollApiService apiService;
    /* */
    private Context context;

    /* */
    @Inject
    public PollRepositoryImpl() {
        Retrofit retrofit = ServicesRetrofitManager.getInstance().getRetrofitAPI();
        apiService = retrofit.create(PollApiService.class);
        context = CoppelApp.getContext();
    }

    /**
     *
     *
     */
    @Override
    public void getPoll(UseCase.OnResultFunction<Either<Failure, Poll>> callback) {
        long employeeNum = getEmployeeNum();
        int clvOption = 1;
        String authHeader = getAuthHeader();
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
                    Poll poll = body.data.response.toPoll();
                    Either<Failure, Poll> result = new Either<Failure, Poll>().new Right(poll);
                    callback.onResult(result);
                } catch (Exception exception){
                    callback.onResult(getNotPollAvailableFailure());
                }
            }

            @Override
            public void onFailure(Call<GetPollResponse> call, Throwable t) {
                callback.onResult(getNotPollAvailableFailure());
            }

            private Either<Failure, Poll> getNotPollAvailableFailure() {
                Failure failure = new NotPollAvailableFailure();
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
        long employeeNum = getEmployeeNum();
        int clvOption = 2;
        String authHeader = getAuthHeader();
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
    private Long getEmployeeNum(){
        return Long.parseLong(getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        ));
    }

    /**
     *
     *
     */
    private String getAuthHeader(){
        return getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
    }

}
