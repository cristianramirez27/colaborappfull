package com.coppel.rhconecta.dev.di.poll;

import com.coppel.rhconecta.dev.data.poll.PollRepositoryImpl;
import com.coppel.rhconecta.dev.domain.poll.PollRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PollModule {

    @Provides
    public PollRepository providePollRepository() {
        return new PollRepositoryImpl();
    }

}
