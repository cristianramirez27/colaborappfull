package com.coppel.rhconecta.dev.di.profile_actions

import com.coppel.rhconecta.dev.presentation.profile_actions.ProfileActionsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

/* */
val profileActionsModule: Module = module {

    /* */
    viewModel {
        ProfileActionsViewModel(
                haveFingerprintsUseCase = get()
        )
    }

}