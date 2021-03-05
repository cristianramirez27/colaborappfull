package com.coppel.rhconecta.dev.di

import com.coppel.rhconecta.dev.CoppelApp
import com.coppel.rhconecta.dev.di.profile_actions.fingerprintModule
import com.coppel.rhconecta.dev.di.profile_actions.profileActionsModule
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module

/**
 * Initialize the Koin instance with the modules associated to the project.
 */
fun CoppelApp.initKoin() {
    val modules: List<Module> = listOf(
            fingerprintModule,
            profileActionsModule,
    )
    startKoin(applicationContext, modules)
}