package com.coppel.rhconecta.dev.presentation.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.domain.home.use_case.GetBannersUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class HomeViewModel {

    // Use cases
    @Inject GetBannersUseCase getBannersUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadBannersStatus = new MutableLiveData<>();
    // Values
    private List<Banner> banners;
    private Failure failure;

    /**
     *
     *
     */
    @Inject HomeViewModel() { }

    /**
     *
     *
     */
    public void loadBanners() {
        loadBannersStatus.postValue(ProcessStatus.LOADING);
        getBannersUseCase.run(UseCase.None.getInstance(), result -> {
            result.fold(onLoadBannersFailure, onLoadBannersRight);
        });
    }

    /* */
    private Either.Fn<Failure> onLoadBannersFailure = failure -> {
        this.failure = failure;
        Log.e(getClass().getName(), failure.toString());
        loadBannersStatus.postValue(ProcessStatus.FAILURE);
    };

    /* */
    private Either.Fn<List<Banner>> onLoadBannersRight = banners -> {
        this.banners = banners;
        loadBannersStatus.postValue(ProcessStatus.COMPLETED);
    };

    /**
     *
     * @return
     */
    public LiveData<ProcessStatus> getLoadBannersStatus() {
        return loadBannersStatus;
    }

    /**
     *
     * @return
     */
    public List<Banner> getBanners() {
        return banners;
    }

    /**
     *
     * @return
     */
    public Failure getFailure() {
        return failure;
    }
}
