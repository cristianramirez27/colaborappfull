package com.coppel.rhconecta.dev.presentation.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.domain.analytics.use_case.SendTimeByAnalyticsFlowUseCase;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/* */
public class SplashViewModel {

    // Use cases
    @Inject
    public SendTimeByAnalyticsFlowUseCase sendTimeByAnalyticsFlowUseCase;
    // Observables
    private final MutableLiveData<ProcessStatus> sendTimeByAnalyticsFlowStatus = new MutableLiveData<>();
    // Values
    private Failure failure;

    /* */
    @Inject
    public SplashViewModel() { /* Empty constructor */ }

    /**
     *
     */
    public void sendTimeByAnalyticsFlow(AnalyticsFlow analyticsFlow, long timeInSeconds) {
        sendTimeByAnalyticsFlowStatus.postValue(ProcessStatus.LOADING);
        SendTimeByAnalyticsFlowUseCase.Params params =
                new SendTimeByAnalyticsFlowUseCase.Params(analyticsFlow, timeInSeconds);
        sendTimeByAnalyticsFlowUseCase.run(params, result ->
                result.fold(onSendTimeByAnalyticsFlowFailure, onSendTimeByAnalyticsFlowRight)
        );
    }

    /* */
    private final Either.Fn<Failure> onSendTimeByAnalyticsFlowFailure = failure -> {
        this.failure = failure;
        sendTimeByAnalyticsFlowStatus.postValue(ProcessStatus.FAILURE);
    };

    /* */
    private final Either.Fn<UseCase.None> onSendTimeByAnalyticsFlowRight = releasesPreviews -> {
        sendTimeByAnalyticsFlowStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     */
    public LiveData<ProcessStatus> getSendTimeByAnalyticsFlowStatus() {
        return sendTimeByAnalyticsFlowStatus;
    }

    /**
     *
     */
    public Failure getFailure() {
        return failure;
    }

}
