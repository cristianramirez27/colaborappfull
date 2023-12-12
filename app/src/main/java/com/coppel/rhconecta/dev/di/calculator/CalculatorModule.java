package com.coppel.rhconecta.dev.di.calculator;

import android.content.Context;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.calculator.CalculatorApiService;
import com.coppel.rhconecta.dev.data.calculator.CalculatorRepositoryImpl;
import com.coppel.rhconecta.dev.domain.calculator.CalculatorRepository;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import dagger.Module;
import dagger.Provides;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.CoroutineDispatcher;

@Module
public class CalculatorModule {

    @Provides
    CalculatorApiService providerCalculatorApiService() {
        return ServicesRetrofitManager.getInstance().getRetrofitAPI().create(CalculatorApiService.class);
    }

    @Provides
    CoroutineDispatcher providesIoDispatcher() {
        return Dispatchers.getIO();
    }

    @Provides
    CalculatorRepository providesCalculatorRepository(CalculatorApiService calculatorApiService) {
        return new CalculatorRepositoryImpl(calculatorApiService);
    }

    @Provides
    String providesNumberEmployer(Context context) {
        return AppUtilities.getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
        );
    }
}
