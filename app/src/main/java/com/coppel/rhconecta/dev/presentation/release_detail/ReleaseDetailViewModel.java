package com.coppel.rhconecta.dev.presentation.release_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.domain.release.use_case.GetReleaseUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/**
 *
 */
public class ReleaseDetailViewModel {

    // Use cases
    @Inject
    public GetReleaseUseCase getReleaseUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadReleaseStatus = new MutableLiveData<>();
    // Values
    private Release release;
    private Failure failure;


    /**
     *
     */
    @Inject public ReleaseDetailViewModel() { }

    /**
     *
     */
    public void loadRelease(int releaseId, AccessOption accessOption) {
        loadReleaseStatus.postValue(ProcessStatus.LOADING);
        GetReleaseUseCase.Params params = new GetReleaseUseCase.Params(releaseId, accessOption);
        getReleaseUseCase.run(params, result -> {
            result.fold(onLoadReleaseFailure, onLoadReleaseRight);
        });
    }

    /* */
    private Either.Fn<Failure> onLoadReleaseFailure = failure -> {
        this.failure = failure;
        loadReleaseStatus.postValue(ProcessStatus.FAILURE);
    };

    /* */
    private Either.Fn<Release> onLoadReleaseRight = release -> {
        this.release = release;
        loadReleaseStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     */
    public LiveData<ProcessStatus> getLoadReleaseStatus() {
        return loadReleaseStatus;
    }

    /**
     *
     */
    public Release getRelease() {
        return release;
    }

    /**
     *
     */
    public Failure getFailure() {
        return failure;
    }

}
