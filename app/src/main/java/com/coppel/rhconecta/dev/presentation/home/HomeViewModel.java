package com.coppel.rhconecta.dev.presentation.home;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.domain.home.entity.HomeParams;
import com.coppel.rhconecta.dev.domain.home.use_case.GetBadgesUseCase;
import com.coppel.rhconecta.dev.domain.home.use_case.GetBannersUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/* */
public class HomeViewModel {

    // Use cases
    @Inject
    GetBannersUseCase getBannersUseCase;
    @Inject
    GetBadgesUseCase getBadgesUseCase;

    @Inject
    SharedPreferences sharedPreferences;
    // Observables
    private MutableLiveData<ProcessStatus> loadBannersProcessStatus = new MutableLiveData<>();
    private MutableLiveData<ProcessStatus> loadBadgesProcessStatus = new MutableLiveData<>();
    // Values
    private List<Banner> banners;
    private Map<Badge.Type, Badge> badges;
    private Failure failure;

    /**
     *
     */
    @Inject
    HomeViewModel() {
    }

    /**
     *
     */
    void loadBanners() {
        loadBannersProcessStatus.postValue(ProcessStatus.LOADING);
        HomeParams params = new HomeParams(getEmployeeNumber(), getAuthHeader(), 1);
        getBannersUseCase.run(params, result ->
                result.fold(this::onLoadBannersFailure, this::onLoadBannersRight)
        );
    }

    /* */
    private void onLoadBannersFailure(Failure failure) {
        this.failure = failure;
        loadBannersProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /* */
    private void onLoadBannersRight(List<Banner> banners) {
        this.banners = banners;
        loadBannersProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     */
    void loadBadges() {
        loadBadgesProcessStatus.postValue(ProcessStatus.LOADING);
        HomeParams params = new HomeParams(getEmployeeNumber(), getAuthHeader(), 1);
        getBadgesUseCase.run(params, result ->
                result.fold(this::onLoadBadgesFailure, this::onLoadBadgesRight)
        );
    }

    private String getEmployeeNumber() {
        return AppUtilities.getStringFromSharedPreferences(
                sharedPreferences,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        );
    }

    private String getAuthHeader() {
        return AppUtilities.getStringFromSharedPreferences(
                sharedPreferences,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
    }

    /* */
    private void onLoadBadgesFailure(Failure failure) {
        this.failure = failure;
        loadBadgesProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /* */
    private void onLoadBadgesRight(Map<Badge.Type, Badge> badges) {
        this.badges = badges;
        loadBadgesProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     */
    LiveData<ProcessStatus> getLoadBannersProcessStatus() {
        return loadBannersProcessStatus;
    }

    /**
     *
     */
    LiveData<ProcessStatus> getLoadBadgesProcessStatus() {
        return loadBadgesProcessStatus;
    }

    /**
     * @return
     */
    List<Banner> getBanners() {
        return banners;
    }

    /**
     * @return
     */
    Map<Badge.Type, Badge> getBadges() {
        return badges;
    }

    /**
     * @return
     */
    Failure getFailure() {
        return failure;
    }

}
