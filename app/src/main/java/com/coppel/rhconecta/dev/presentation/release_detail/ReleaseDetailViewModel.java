package com.coppel.rhconecta.dev.presentation.release_detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.domain.release.use_case.GetReleaseUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/**
 *
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
     *
     */
    @Inject public ReleaseDetailViewModel() { }

    /**
     *
     * @param releaseId
     */
    public void loadRelease(int releaseId) {
        loadReleaseStatus.postValue(ProcessStatus.LOADING);
        GetReleaseUseCase.Params params = new GetReleaseUseCase.Params(releaseId);
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
     * @return
     */
    public LiveData<ProcessStatus> getLoadReleaseStatus() {
        return loadReleaseStatus;
    }

    /**
     *
     * @return
     */
    public Release getRelease() {
        return release;
    }

    /**
     *
     * @return
     */
    public Failure getFailure() {
        return failure;
    }

}
