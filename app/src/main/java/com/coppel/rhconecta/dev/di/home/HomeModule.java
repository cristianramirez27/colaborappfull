package com.coppel.rhconecta.dev.di.home;

import android.content.Context;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.data.home.HomeLocalRepository;
import com.coppel.rhconecta.dev.data.home.HomeRepository;
import com.coppel.rhconecta.dev.framework.home.HomeLocalRepositoryImpl;
import com.coppel.rhconecta.dev.framework.home.HomeRepositoryImpl;
import com.coppel.rhconecta.dev.views.utils.ZendeskUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    HomeRepository provideHomeRepository() {
        return new HomeRepositoryImpl();
    }

    @Provides
    HomeLocalRepository provideHomeLocalRepository() {
        return new HomeLocalRepositoryImpl();
    }

    @Provides
    ZendeskUtil provideZendeskUtil(Context context) {
        return new ZendeskUtil(context);
    }

    @Provides
    Context provideContext() {
        return CoppelApp.getContext();
    }
}
