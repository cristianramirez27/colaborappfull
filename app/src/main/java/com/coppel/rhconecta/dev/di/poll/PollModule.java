package com.coppel.rhconecta.dev.di.poll;

import com.coppel.rhconecta.dev.data.poll.PollRepositoryImpl;
import com.coppel.rhconecta.dev.domain.poll.PollRepository;

import dagger.Module;
import dagger.Provides;

@Module
class PollModule {

    @Provides
    PollRepository providePollRepository() {
        return new PollRepositoryImpl();
    }

}
