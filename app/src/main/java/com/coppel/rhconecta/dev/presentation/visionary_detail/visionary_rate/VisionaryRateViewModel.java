package com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryRate;
import com.coppel.rhconecta.dev.domain.visionary.use_case.UpdateVisionaryStatusUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import javax.inject.Inject;

/* */
public class VisionaryRateViewModel {

    // Use cases
    @Inject
    public UpdateVisionaryStatusUseCase updateVisionaryStatusUseCase;

    // Observable
    private final MutableLiveData<ProcessStatus>
            updateVisionaryStatusProcessStatus = new MutableLiveData<>();

    // Values
    private Failure failure;


    /* */
    @Inject
    public VisionaryRateViewModel() { /* Empty body */ }

    /**
     *
     */
    void updateVisionaryStatus(
            VisionaryType visionaryType,
            Visionary visionary,
            Visionary.RateStatus status,
            VisionaryRate.Option option
    ) {
        String visionaryId = visionary.getId();
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.LOADING);
        UpdateVisionaryStatusUseCase.Params params =
                new UpdateVisionaryStatusUseCase.Params(visionaryType, visionaryId, status, option.getId());
        updateVisionaryStatusUseCase.run(params, result ->
                result.fold(this::onUpdateVisionaryStatusFailure, this::onUpdateVisionaryStatusRight)
        );
    }

    /**
     *
     */
    private void onUpdateVisionaryStatusFailure(Failure failure) {
        this.failure = failure;
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     */
    private void onUpdateVisionaryStatusRight(Visionary.RateStatus status) {
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     */
    public LiveData<ProcessStatus> getUpdateVisionaryStatusProcessStatus() {
        return updateVisionaryStatusProcessStatus;
    }

    /**
     *
     */
    public Failure getFailure() {
        return failure;
    }

}
