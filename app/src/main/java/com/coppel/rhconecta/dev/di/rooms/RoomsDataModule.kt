package com.coppel.rhconecta.dev.di.rooms

import com.coppel.rhconecta.dev.data.rooms.RoomsRepositoryImpl
import com.coppel.rhconecta.dev.domain.rooms.RoomsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RoomsDataModule {
    @Binds
    abstract fun providerRoomsRepository(roomsRepositoryImpl: RoomsRepositoryImpl): RoomsRepository
}