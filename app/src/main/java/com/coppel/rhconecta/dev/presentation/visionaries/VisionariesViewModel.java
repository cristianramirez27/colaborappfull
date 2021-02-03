package com.coppel.rhconecta.dev.presentation.visionaries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionariesPreviewsUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class VisionariesViewModel {

    // Use cases
    @Inject
    GetVisionariesPreviewsUseCase getVisionariesPreviewsUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadVisionariesPreviewsStatus = new MutableLiveData<>();
    // Values
    private List<VisionaryPreview> visionariesPreviews;
    private Failure failure;

    /**
     *
     *
     */
    @Inject VisionariesViewModel() { }

    /**
     *
     *
     */
    void loadReleasesPreviews(VisionaryType type, AccessOption accessOption) {
        loadVisionariesPreviewsStatus.postValue(ProcessStatus.LOADING);
        GetVisionariesPreviewsUseCase.Params params =
                new GetVisionariesPreviewsUseCase.Params(type, accessOption);
        getVisionariesPreviewsUseCase.run(params, result ->
                result.fold(this::onLoadVisionariesPreviewsFailure, this::onLoadVisionariesPreviewsRight)
        );
    }

    /**
     *
     *
     */
    private void onLoadVisionariesPreviewsFailure(Failure failure){
        this.failure = failure;
        loadVisionariesPreviewsStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     *
     */
    private void onLoadVisionariesPreviewsRight(List<VisionaryPreview> visionariesPreviews){
        this.visionariesPreviews = visionariesPreviews;
        loadVisionariesPreviewsStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     *
     */
    LiveData<ProcessStatus> getLoadVisionariesPreviewsStatus() {
        return loadVisionariesPreviewsStatus;
    }

    /**
     *
     *
     */
    List<VisionaryPreview> getVisionariesPreviews() {
        return visionariesPreviews;
    }

    /**
     *
     *
     */
    Failure getFailure() {
        return failure;
    }

}
