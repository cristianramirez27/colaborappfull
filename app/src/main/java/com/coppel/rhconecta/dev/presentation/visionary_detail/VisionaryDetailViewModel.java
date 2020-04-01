package com.coppel.rhconecta.dev.presentation.visionary_detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionaryUseCase;
import com.coppel.rhconecta.dev.domain.visionary.use_case.UpdateVisionaryStatusUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/**
 *
 *
 */
public class VisionaryDetailViewModel {

    // Use cases
    @Inject
    public GetVisionaryUseCase getVisionaryUseCase;
    @Inject
    public UpdateVisionaryStatusUseCase updateVisionaryStatusUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadVisionaryProcessStatus = new MutableLiveData<>();
    private MutableLiveData<ProcessStatus> updateVisionaryStatusProcessStatus = new MutableLiveData<>();
    // Values
    private Visionary visionary;
    private Failure failure;


    /**
     *
     *
     */
    @Inject public VisionaryDetailViewModel() { }

    /**
     *
     * @param visionaryId
     */
    void loadVisionary(String visionaryId) {
        loadVisionaryProcessStatus.postValue(ProcessStatus.LOADING);
        GetVisionaryUseCase.Params params = new GetVisionaryUseCase.Params(visionaryId);
        getVisionaryUseCase.run(params, result ->
                result.fold(this::onLoadVisionaryFailure, this::onLoadVisionaryRight)
        );
    }

    /**
     *
     * @param failure
     */
    private void onLoadVisionaryFailure(Failure failure){
        this.failure = failure;
        loadVisionaryProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     * @param visionary
     */
    private void onLoadVisionaryRight(Visionary visionary){
        this.visionary = visionary;
        loadVisionaryProcessStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     * @param status
     */
    void updateVisionaryStatus(Visionary.Status status) {
        String visionaryId = visionary.getId();
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.LOADING);
        UpdateVisionaryStatusUseCase.Params params =
                new UpdateVisionaryStatusUseCase.Params(visionaryId, status);
        updateVisionaryStatusUseCase.run(params, result ->
                result.fold(this::onUpdateVisionaryStatusFailure, this::onUpdateVisionaryStatusRight)
        );
    }

    /**
     *
     * @param failure
     */
    private void onUpdateVisionaryStatusFailure(Failure failure){
        this.failure = failure;
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     * @param status
     */
    private void onUpdateVisionaryStatusRight(Visionary.Status status){
        Visionary updatedVisionary = visionary.cloneVisionary();
        updatedVisionary.setStatus(status);
        this.visionary = updatedVisionary;
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     * @return
     */
    LiveData<ProcessStatus> getLoadVisionaryProcessStatus() {
        return loadVisionaryProcessStatus;
    }

    /**
     *
     * @return
     */
    LiveData<ProcessStatus> getUpdateVisionaryStatusProcessStatus() {
        return updateVisionaryStatusProcessStatus;
    }

    /**
     *
     * @return
     */
    public Visionary getVisionary() {
        return visionary;
    }

    /**
     *
     * @return
     */
    public Failure getFailure() {
        return failure;
    }

}
