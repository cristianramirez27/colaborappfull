package com.coppel.rhconecta.dev.views.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.business.models.ZendeskResponse;
import com.coppel.rhconecta.dev.domain.analytics.use_case.SendTimeByAnalyticsFlowUseCase;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.use_case.GetHelpDeskServiceAvailabilityUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/* */
public class HomeActivityViewModel {

    // Use cases
    @Inject
    public SendTimeByAnalyticsFlowUseCase sendTimeByAnalyticsFlowUseCase;
    @Inject
    public GetHelpDeskServiceAvailabilityUseCase getHelpDeskServiceAvailabilityUseCase;

    // Observables
    private MutableLiveData<ProcessStatus> sendTimeByAnalyticsFlowStatus = new MutableLiveData<>();
    private MutableLiveData<ZendeskResponse.Response> helpDeskServiceData = new MutableLiveData<>();
    // Values
    private Failure failure;

    /* */
    @Inject
    public HomeActivityViewModel() { /* Empty constructor */ }

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


    /**
     * Use case
     * Get help desk availability
     */
    public void getHelpDeskServiceAvailability() {
        sendTimeByAnalyticsFlowStatus.postValue(ProcessStatus.LOADING);
        getHelpDeskServiceAvailabilityUseCase.run(UseCase.None.getInstance(), (Either<Failure, ZendeskResponse> result) ->
                result.fold(this::onLoadHelpDeskServiceAvailabilityFailure, this::onLoadHelpDeskServiceAvailabilitySuccess)
        );
    }

    /**
     * Manage use case success
     * Get help desk availability
     *
     * @param helpDeskAvailability
     */
    private void onLoadHelpDeskServiceAvailabilitySuccess(ZendeskResponse helpDeskAvailability) {
        helpDeskServiceData.postValue(helpDeskAvailability.getData().getResponse()[0]);
    }

    /**
     * Manage use case error
     * Get help desk availability
     *
     * @param failure
     */
    private void onLoadHelpDeskServiceAvailabilityFailure(Failure failure) {
        this.failure = failure;
    }

    /**
     * Mutable used to observe and emit events to the view
     *
     * @return observer
     */
    public LiveData<ZendeskResponse.Response> getHelpDeskServiceAvailabilityData() {
        return helpDeskServiceData;
    }
}
