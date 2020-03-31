package com.coppel.rhconecta.dev.di.visionary;

import com.coppel.rhconecta.dev.data.visionary.VisionaryRepositoryImpl;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class VisionaryModule {

    /**
     *
     *
     * @return
     */
    @Provides
    public VisionaryRepository provideVisionaryRepository(){
        return new VisionaryRepositoryImpl();
    }

}
