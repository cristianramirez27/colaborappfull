package com.coppel.rhconecta.dev.di.profile_actions

import com.coppel.rhconecta.dev.presentation.profile_actions.ProfileActionsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


/* */
val profileActionsModule: Module = module {

    /* */
    viewModel {
        ProfileActionsViewModel(
                haveFingerprintsUseCase = get()
        )
    }

}