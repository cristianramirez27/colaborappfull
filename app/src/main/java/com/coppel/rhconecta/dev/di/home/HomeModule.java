package com.coppel.rhconecta.dev.di.home;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager;
import com.coppel.rhconecta.dev.data.home.HomeLocalRepository;
import com.coppel.rhconecta.dev.data.home.HomeRepository;
import com.coppel.rhconecta.dev.framework.home.HomeApiService;
import com.coppel.rhconecta.dev.framework.home.HomeLocalRepositoryImpl;
import com.coppel.rhconecta.dev.framework.home.HomeRepositoryImpl;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.ZendeskManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import dagger.Module;
import dagger.Provides;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Retrofit;

@Module
public class HomeModule {

    @Provides
    HomeRepository provideHomeRepository(
            Context context,
            HomeApiService apiService,
            FirebaseCrashlytics firebaseCrashlytics,
            FirebaseAnalytics firebaseAnalytics
    ) {
        return new HomeRepositoryImpl(
                context,
                apiService,
                firebaseCrashlytics,
                firebaseAnalytics
        );
    }

    @Provides
    HomeLocalRepository provideHomeLocalRepository(Context context) {
        return new HomeLocalRepositoryImpl(context);
    }

    @Provides
    ZendeskManager provideZendeskUtil(Context context) {
        return new ZendeskManager(context);
    }

    @Provides
    Context provideContext() {
        return CoppelApp.getContext();
    }

    @Provides
    CoroutineDispatcher providesIoDispatcher() {
        return Dispatchers.getIO();
    }

    @Provides
    FirebaseCrashlytics provideFirebaseCrashlytics() {
        return FirebaseCrashlytics.getInstance();
    }

    @Provides
    FirebaseAnalytics provideFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(
                AppConstants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
    }

    @Provides
    @NonNull
    HomeApiService provideAPI(Retrofit retrofit) {
        return retrofit.create(HomeApiService.class);
    }

    @Provides
    Retrofit provideRetrofit() {
        return ServicesRetrofitManager.getInstance().getRetrofitAPI();
    }
}
