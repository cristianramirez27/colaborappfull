package com.coppel.rhconecta.dev.presentation.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.business.interactors.ServicesInteractor;
import com.coppel.rhconecta.dev.business.models.CoppelServicesProfileRequest;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.domain.home.use_case.GetBannersUseCase;
import com.coppel.rhconecta.dev.presentation.common.ProcessStatus;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

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
    private Failure failure;
    // Attributes
    private Context context;

    /**
     *
     *
     */
    @Inject HomeViewModel() { }

    /**
     *
     * @param context
     */
    public void init(Context context){
        this.context = context;
    }

    /**
     *
     *
     */
    public void loadBanners() {
        loadBannersStatus.postValue(ProcessStatus.LOADING);
        String employeeNum = AppUtilities.getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        );
        GetBannersUseCase.Params params = new GetBannersUseCase.Params(employeeNum);
        getBannersUseCase.invoke(params, result -> {
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
    private Either.Fn<List<Banner>> onLoadBannersRight = aBoolean -> {
        Log.i(getClass().getName(), aBoolean.toString());
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
    public Failure getFailure() {
        return failure;
    }
}
