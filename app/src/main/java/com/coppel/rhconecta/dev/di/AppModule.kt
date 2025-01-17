package com.coppel.rhconecta.dev.di

import com.coppel.rhconecta.dev.CoppelApp
import com.coppel.rhconecta.dev.di.movements.movementsModule
import com.coppel.rhconecta.dev.di.profile_actions.fingerprintModule
import com.coppel.rhconecta.dev.di.profile_actions.profileActionsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.module.Module
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidLogger

/**
 * Initialize the Koin instance with the modules associated to the project.
 */
fun CoppelApp.initKoin() {
    val modules: List<Module> = listOf(
        fingerprintModule,
        profileActionsModule,
        movementsModule
    )
    //startKoin(applicationContext,modules)
    startKoin {
        androidContext(this@initKoin)
        androidLogger()
        modules(modules)
    }

}

