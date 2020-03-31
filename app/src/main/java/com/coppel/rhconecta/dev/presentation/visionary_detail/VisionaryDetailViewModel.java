package com.coppel.rhconecta.dev.presentation.visionary_detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionaryUseCase;
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
    // Observables
    private MutableLiveData<ProcessStatus> loadVisionaryStatus = new MutableLiveData<>();
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
    public void loadVisionary(String visionaryId) {
        loadVisionaryStatus.postValue(ProcessStatus.LOADING);
        GetVisionaryUseCase.Params params = new GetVisionaryUseCase.Params(visionaryId);
        getVisionaryUseCase.run(params, result -> {
            result.fold(onLoadVisionaryFailure, onLoadVisionaryRight);
        });
    }

    /* */
    private Either.Fn<Failure> onLoadVisionaryFailure = failure -> {
        this.failure = failure;
        loadVisionaryStatus.postValue(ProcessStatus.FAILURE);
    };

    /* */
    private Either.Fn<Visionary> onLoadVisionaryRight = visionary -> {
        this.visionary = visionary;
        loadVisionaryStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     * @return
     */
    public LiveData<ProcessStatus> getLoadVisionaryStatus() {
        return loadVisionaryStatus;
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
