package com.coppel.rhconecta.dev.presentation.visionary_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionaryPreviewUseCase;
import com.coppel.rhconecta.dev.domain.visionary.use_case.GetVisionaryUseCase;
import com.coppel.rhconecta.dev.domain.visionary.use_case.SendVisionaryActionUseCase;
import com.coppel.rhconecta.dev.domain.visionary.use_case.UpdateVisionaryStatusUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import javax.inject.Inject;

import kotlin.Unit;

/** */
public class VisionaryDetailViewModel {

    // Use cases
    @Inject
    GetVisionaryUseCase getVisionaryUseCase;
    @Inject
    GetVisionaryPreviewUseCase getVisionaryPreviewUseCase;
    @Inject
    UpdateVisionaryStatusUseCase updateVisionaryStatusUseCase;
    @Inject
    SendVisionaryActionUseCase sendVisionaryActionUseCase;
    // Observables
    private final MutableLiveData<ProcessStatus> loadVisionaryProcessStatus = new MutableLiveData<>();
    private final MutableLiveData<ProcessStatus> updateVisionaryStatusProcessStatus = new MutableLiveData<>();
    private final MutableLiveData<ProcessStatus> sendVisionaryActionProcessStatus = new MutableLiveData<>();
    // Values
    private Visionary visionary;
    private VisionaryPreview visionaryPreview;
    private Failure failure;

    /* */
    @Inject
    VisionaryDetailViewModel() { /* Empty body */ }

    /** */
    void loadVisionary(VisionaryType visionaryType, String visionaryId, AccessOption accessOption) {
        loadVisionaryProcessStatus.postValue(ProcessStatus.LOADING);
        GetVisionaryUseCase.Params params =
                new GetVisionaryUseCase.Params(visionaryType, visionaryId, accessOption);
        getVisionaryUseCase.run(params, result ->
                result.fold(this::onLoadVisionaryFailure, this::onLoadVisionaryRight)
        );
    }

    /** */
    private void onLoadVisionaryFailure(Failure failure) {
        this.failure = failure;
        loadVisionaryProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /** */
    private void onLoadVisionaryRight(Visionary visionary) {
        this.visionary = visionary;
        loadVisionaryProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /** */
    void loadVisionaryPreview(VisionaryType visionaryType, String visionaryId, AccessOption accessOption) {
        loadVisionaryProcessStatus.postValue(ProcessStatus.LOADING);
        GetVisionaryPreviewUseCase.Params params =
                new GetVisionaryPreviewUseCase.Params(visionaryType, visionaryId, accessOption);
        getVisionaryPreviewUseCase.run(params, result ->
                result.fold(this::onLoadVisionaryPreviewFailure, this::onLoadVisionaryPreviewRight)
        );
    }

    /** */
    private void onLoadVisionaryPreviewFailure(Failure failure) {
        this.failure = failure;
        loadVisionaryProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /** */
    private void onLoadVisionaryPreviewRight(VisionaryPreview visionaryPreview) {
        this.visionaryPreview = visionaryPreview;
        loadVisionaryProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /** */
    void updateVisionaryStatus(VisionaryType visionaryType, Visionary.RateStatus status) {
        String visionaryId = visionary.getId();
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.LOADING);
        UpdateVisionaryStatusUseCase.Params params =
                new UpdateVisionaryStatusUseCase.Params(visionaryType, visionaryId, status);
        updateVisionaryStatusUseCase.run(params, result ->
                result.fold(this::onUpdateVisionaryStatusFailure, this::onUpdateVisionaryStatusRight)
        );
    }

    /** */
    private void onUpdateVisionaryStatusFailure(Failure failure) {
        this.failure = failure;
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /** */
    private void onUpdateVisionaryStatusRight(Visionary.RateStatus status) {
        Visionary updatedVisionary = visionary.cloneVisionary();
        updatedVisionary.setRateStatus(status);
        this.visionary = updatedVisionary;
        updateVisionaryStatusProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /** */
    void sendVisionaryAction(VisionaryType type, int clvType) {
        long visionaryId = Long.parseLong(visionary.getId());
        sendVisionaryActionProcessStatus.postValue(ProcessStatus.LOADING);
        SendVisionaryActionUseCase.Params params = new SendVisionaryActionUseCase.Params(
                type,
                visionaryId,
                clvType
        );
        sendVisionaryActionUseCase.run(params, result ->
                result.fold(this::onSendVisionaryActionFailure, this::onSendVisionaryActionRight)
        );
    }

    /** */
    private void onSendVisionaryActionFailure(Failure failure) {
        this.failure = failure;
        sendVisionaryActionProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /** */
    private void onSendVisionaryActionRight(Unit unit) {
        sendVisionaryActionProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /** */
    LiveData<ProcessStatus> getLoadVisionaryProcessStatus() {
        return loadVisionaryProcessStatus;
    }

    /** */
    LiveData<ProcessStatus> getUpdateVisionaryStatusProcessStatus() {
        return updateVisionaryStatusProcessStatus;
    }

    /** */
    LiveData<ProcessStatus> getSendVisionaryActionProcessStatus() {
        return sendVisionaryActionProcessStatus;
    }

    /** */
    Visionary getVisionary() {
        return visionary;
    }

    /** */
    VisionaryPreview getVisionaryPreview() {
        return visionaryPreview;
    }

    /** */
    Failure getFailure() {
        return failure;
    }

}
