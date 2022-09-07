package com.coppel.rhconecta.dev.di.home;

import com.coppel.rhconecta.dev.domain.home.HomeRepository;
import com.coppel.rhconecta.dev.framework.home.HomeRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    HomeRepository provideHomeRepository() {
        return new HomeRepositoryImpl();
    }

}
