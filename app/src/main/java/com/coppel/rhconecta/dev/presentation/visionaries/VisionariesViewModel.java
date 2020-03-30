package com.coppel.rhconecta.dev.presentation.visionaries;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionariesPreviewsUseCase;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import java.util.List;

import javax.inject.Inject;

public class VisionariesViewModel {

    // Use cases
    @Inject
    public GetVisionariesPreviewsUseCase getVisionariesPreviewsUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadVisionariesPreviewsStatus = new MutableLiveData<>();
    // Values
    private List<VisionaryPreview> visionariesPreviews;
    private Failure failure;

    /**
     *
     *
     */
    @Inject public VisionariesViewModel() { }

    /**
     *
     *
     */
    public void loadReleasesPreviews() {
        loadVisionariesPreviewsStatus.postValue(ProcessStatus.LOADING);
        getVisionariesPreviewsUseCase.run(UseCase.None.getInstance(), result -> {
            result.fold(onLoadVisionariesPreviewsFailure, onLoadVisionariesPreviewsRight);
        });
    }

    /* */
    private Either.Fn<Failure> onLoadVisionariesPreviewsFailure = failure -> {
        this.failure = failure;
        loadVisionariesPreviewsStatus.postValue(ProcessStatus.FAILURE);
    };

    /* */
    private Either.Fn<List<VisionaryPreview>> onLoadVisionariesPreviewsRight = visionariesPreviews -> {
        this.visionariesPreviews = visionariesPreviews;
        loadVisionariesPreviewsStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     * @return
     */
    public LiveData<ProcessStatus> getLoadVisionariesPreviewsStatus() {
        return loadVisionariesPreviewsStatus;
    }

    /**
     *
     * @return
     */
    public List<VisionaryPreview> getVisionariesPreviews() {
        return visionariesPreviews;
    }

    /**
     *
     * @return
     */
    public Failure getFailure() {
        return failure;
    }

}
