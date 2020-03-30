package com.coppel.rhconecta.dev.di.home;

import com.coppel.rhconecta.dev.data.home.HomeRepositoryImpl;
import com.coppel.rhconecta.dev.domain.home.HomeRepository;

import dagger.Module;
import dagger.Provides;

@Module
class HomeModule {

    @Provides
    HomeRepository provideHomeRepository() {
        return new HomeRepositoryImpl();
    }

}
