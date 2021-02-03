package com.coppel.rhconecta.dev.presentation.poll_toolbar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.use_case.GetAvailablePollCountUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/**
 *
 *
 */
public class PollToolbarViewModel {

    // Use cases
    @Inject
    GetAvailablePollCountUseCase getAvailablePollCountUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> getAvailablePollCountProcessStatus = new MutableLiveData<>();
    // Values
    private int availableCount;
    private Failure failure;


    /**
     *
     *
     */
    @Inject PollToolbarViewModel() { /* PASS */ }

    /**
     *
     *
     */
    void loadAvailablePollCount() {
        getAvailablePollCountProcessStatus.postValue(ProcessStatus.LOADING);
        getAvailablePollCountUseCase.run(UseCase.None.getInstance(), result ->
                result.fold(this::onGetAvailablePollCountFailure, this::onGetAvailablePollCountRight)
        );
    }

    /**
     *
     *
     */
    private void onGetAvailablePollCountFailure(Failure failure){
        this.failure = failure;
        getAvailablePollCountProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     *
     */
    private void onGetAvailablePollCountRight(Integer availableCount){
        this.availableCount = availableCount;
        getAvailablePollCountProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     *
     */
    LiveData<ProcessStatus> getGetAvailablePollCountProcessStatus() {
        return getAvailablePollCountProcessStatus;
    }

    /**
     *
     *
     */
    int getAvailableCount() {
        return availableCount;
    }

    /**
     *
     *
     */
    Failure getFailure() {
        return failure;
    }

}
