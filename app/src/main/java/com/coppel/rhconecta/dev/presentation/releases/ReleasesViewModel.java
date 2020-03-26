package com.coppel.rhconecta.dev.presentation.releases;

import android.arch.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.use_case.GetReleasesPreviewsUseCase;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class ReleasesViewModel {

    // Use cases
    @Inject
    public GetReleasesPreviewsUseCase getReleasesPreviewsUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadReleasesPreviewsStatus = new MutableLiveData<>();
    // Values
    private List<ReleasePreview> releasesPreviews;
    private Failure failure;

    /**
     *
     *
     */
    @Inject public ReleasesViewModel() {

    }

    /**
     *
     *
     */
    public void loadReleasesPreviews() {
        loadReleasesPreviewsStatus.postValue(ProcessStatus.LOADING);
        getReleasesPreviewsUseCase.run(UseCase.None.getInstance(), result -> {
            result.fold(onLoadReleasesPreviewsFailure, onLoadReleasesPreviewsRight);
        });
    }

    /* */
    private Either.Fn<Failure> onLoadReleasesPreviewsFailure = failure -> {
        this.failure = failure;
        loadReleasesPreviewsStatus.postValue(ProcessStatus.FAILURE);
    };

    /* */
    private Either.Fn<List<ReleasePreview>> onLoadReleasesPreviewsRight = releasesPreviews -> {
        this.releasesPreviews = releasesPreviews;
        loadReleasesPreviewsStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     * @return
     */
    public MutableLiveData<ProcessStatus> getLoadReleasesPreviewsStatus() {
        return loadReleasesPreviewsStatus;
    }

    /**
     *
     * @return
     */
    public List<ReleasePreview> getReleasesPreviews() {
        return releasesPreviews;
    }

    /**
     *
     * @return
     */
    public Failure getFailure() {
        return failure;
    }

}
