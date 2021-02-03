package com.coppel.rhconecta.dev.presentation.visionary_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionaryUseCase;
import com.coppel.rhconecta.dev.domain.visionary.use_case.UpdateVisionaryStatusUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import javax.inject.Inject;

/* */
public class VisionaryDetailViewModel {

    // Use cases
    @Inject
    GetVisionaryUseCase getVisionaryUseCase;
    @Inject
    UpdateVisionaryStatusUseCase updateVisionaryStatusUseCase;
    // Observables
    private final MutableLiveData<ProcessStatus> loadVisionaryProcessStatus = new MutableLiveData<>();
    private final MutableLiveData<ProcessStatus> updateVisionaryStatusProcessStatus = new MutableLiveData<>();
    // Values
    private Visionary visionary;
    private Failure failure;


    /* */
    @Inject
    VisionaryDetailViewModel() { /* Empty body */ }

    /**
     *
     */
    void loadVisionary(VisionaryType visionaryType, String visionaryId, AccessOption accessOption) {
        loadVisionaryProcessStatus.postValue(ProcessStatus.LOADING);
        GetVisionaryUseCase.Params params =
                new GetVisionaryUseCase.Params(visionaryType, visionaryId, accessOption);
        getVisionaryUseCase.run(params, result ->
                result.fold(this::onLoadVisionaryFailure, this::onLoadVisionaryRight)
        );
    }

    /**
     *
     */
    private void onLoadVisionaryFailure(Failure failure) {
        this.failure = failure;
        loadVisionaryProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     */
    private void onLoadVisionaryRight(Visionary visionary) {
        this.visionary = visionary;
        loadVisionaryProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     */
    void updateVisionaryStatus(VisionaryType visionaryType, Visionary.RateStatus status) {
        String visionaryId = visionary.getId();
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.LOADING);
        UpdateVisionaryStatusUseCase.Params params =
                new UpdateVisionaryStatusUseCase.Params(visionaryType, visionaryId, status);
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
        Visionary updatedVisionary = visionary.cloneVisionary();
        updatedVisionary.setRateStatus(status);
        this.visionary = updatedVisionary;
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     */
    LiveData<ProcessStatus> getLoadVisionaryProcessStatus() {
        return loadVisionaryProcessStatus;
    }

    /**
     *
     */
    LiveData<ProcessStatus> getUpdateVisionaryStatusProcessStatus() {
        return updateVisionaryStatusProcessStatus;
    }

    /**
     *
     */
    Visionary getVisionary() {
        return visionary;
    }

    /**
     *
     */
    Failure getFailure() {
        return failure;
    }

}
