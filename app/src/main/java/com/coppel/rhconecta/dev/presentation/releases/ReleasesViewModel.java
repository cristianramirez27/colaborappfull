package com.coppel.rhconecta.dev.presentation.releases;

import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.use_case.GetReleasesPreviewsUseCase;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import java.util.List;

import javax.inject.Inject;

/* */
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
     */
    @Inject public ReleasesViewModel() {

    }

    /**
     *
     */
    public void loadReleasesPreviews(AccessOption accessOption) {
        loadReleasesPreviewsStatus.postValue(ProcessStatus.LOADING);
        GetReleasesPreviewsUseCase.Params params = new GetReleasesPreviewsUseCase.Params(accessOption);
        getReleasesPreviewsUseCase.run(params, result -> {
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
     */
    public MutableLiveData<ProcessStatus> getLoadReleasesPreviewsStatus() {
        return loadReleasesPreviewsStatus;
    }

    /**
     *
     */
    public List<ReleasePreview> getReleasesPreviews() {
        return releasesPreviews;
    }

    /**
     *
     */
    public Failure getFailure() {
        return failure;
    }

}
