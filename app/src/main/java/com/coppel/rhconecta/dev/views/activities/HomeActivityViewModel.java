package com.coppel.rhconecta.dev.views.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.domain.analytics.use_case.SendTimeByAnalyticsFlowUseCase;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.use_case.GetPersonalDataHelpDeskUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.views.modelview.HelpDeskDataRequired;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import javax.inject.Inject;

/* */
public class HomeActivityViewModel {

    // Use cases
    @Inject
    public SendTimeByAnalyticsFlowUseCase sendTimeByAnalyticsFlowUseCase;
    @Inject
    public GetPersonalDataHelpDeskUseCase getPersonalDataHelpDeskUseCase;

    // Observables
    private MutableLiveData<ProcessStatus> sendTimeByAnalyticsFlowStatus = new MutableLiveData<>();
    private MutableLiveData<HelpDeskDataRequired> helpDeskDataRequiredObserver = new MutableLiveData<>();
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
    public void getPersonalDataForHelpDesk() {
        sendTimeByAnalyticsFlowStatus.postValue(ProcessStatus.LOADING);
        getPersonalDataHelpDeskUseCase.run(UseCase.None.getInstance(), (Either<Failure, HelpDeskDataRequired> result) ->
                result.fold(this::onLoadPersonalDataFailure, this::onLoadPersonalDataSuccess)
        );
    }

    private void onLoadPersonalDataSuccess(HelpDeskDataRequired helpDeskDataRequired) {
        if (helpDeskDataRequired != null) {
            HelpDeskDataRequired data = new HelpDeskDataRequired(
                    helpDeskDataRequired.getEmployName(),
                    helpDeskDataRequired.getEmail(),
                    helpDeskDataRequired.getEmployNumber(),
                    helpDeskDataRequired.getJobName(),
                    helpDeskDataRequired.getDepartment(),
                    AppUtilities.getDeviceName(),
                    AppUtilities.getAndroidVersion(),
                    AppUtilities.getVersionApp());

            helpDeskDataRequiredObserver.postValue(data);

        }
    }

    private void onLoadPersonalDataFailure(Failure failure) {
        this.failure = failure;
    }

    public LiveData<HelpDeskDataRequired> getLoadPersonalDataSuccess() {
        return helpDeskDataRequiredObserver;
    }
}
