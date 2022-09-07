package com.coppel.rhconecta.dev.views.activities;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.business.models.ZendeskResponse;
import com.coppel.rhconecta.dev.data.home.model.get_main_information.BaseResponse;
import com.coppel.rhconecta.dev.domain.analytics.use_case.SendTimeByAnalyticsFlowUseCase;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability;
import com.coppel.rhconecta.dev.domain.home.use_case.GetHelpDeskServiceAvailabilityUseCase;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

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
    private MutableLiveData<ProcessStatus> loadBadgesProcessStatus = new MutableLiveData<>();
    // Values
    private Failure failure;

    /* */
    @Inject public HomeActivityViewModel() { /* Empty constructor */ }

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
     *
     */
    public void getHelpDeskServiceAvailability() {
        sendTimeByAnalyticsFlowStatus.postValue(ProcessStatus.LOADING);
        /*GetHelpDeskServiceAvailabilityUseCase.Params params =
                new GetHelpDeskServiceAvailabilityUseCase.Params(analyticsFlow, timeInSeconds);*/
        getHelpDeskServiceAvailabilityUseCase.run(UseCase.None.getInstance(), (Either<Failure, ZendeskResponse> result) ->
                result.fold(this::onLoadHelpDeskServiceAvailabilityFailure, this::onLoadHelpDeskServiceAvailabilitySuccess)
        );
    }

    /* */
    private void onLoadHelpDeskServiceAvailabilitySuccess(ZendeskResponse helpDeskAvailability) {
        Log.v("DEBUG-RESPONSE",new Gson().toJson(helpDeskAvailability));
        loadBadgesProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /* */
    private void onLoadHelpDeskServiceAvailabilityFailure(Failure failure){
        this.failure = failure;
        loadBadgesProcessStatus.postValue(ProcessStatus.FAILURE);
    }
}
