package com.coppel.rhconecta.dev.di;

import com.coppel.rhconecta.dev.data.release.ReleaseRepositoryImpl;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ReleaseModule {

    @Provides
    ReleaseRepository provideReleaseRepository() {
        return new ReleaseRepositoryImpl();
    }

}
