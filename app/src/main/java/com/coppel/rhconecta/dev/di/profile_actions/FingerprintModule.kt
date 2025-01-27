package com.coppel.rhconecta.dev.di.profile_actions

import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager
import com.coppel.rhconecta.dev.data.fingerprint.FingerprintApiService
import com.coppel.rhconecta.dev.data.fingerprint.FingerprintRepositoryImpl
import com.coppel.rhconecta.dev.domain.fingerprint.FingerprintRepository
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints.GetFingerprintsUseCase
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints.HaveFingerprintsUseCase
import com.coppel.rhconecta.dev.presentation.profile_actions.fingerprint.FingerprintViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/* */
val fingerprintModule: Module = module {

    /* */
    viewModel {
        FingerprintViewModel(
                getFingerprintsUseCase = get()
        )
    }

    /** */
    single<FingerprintRepository> {
        val retrofit = ServicesRetrofitManager.getInstance().retrofitAPI
        FingerprintRepositoryImpl(
                androidContext(),
                retrofit.create(FingerprintApiService::class.java)
        )
    }

    /** */
    factory {
        HaveFingerprintsUseCase(fingerprintRepository = get())
    }
    factory {
        GetFingerprintsUseCase(fingerprintRepository = get())
    }

}