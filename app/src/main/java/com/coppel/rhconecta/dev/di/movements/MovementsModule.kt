package com.coppel.rhconecta.dev.di.movements

import com.coppel.rhconecta.dev.business.utils.ServicesRetrofitManager
import com.coppel.rhconecta.dev.data.movements.MovementRepository
import com.coppel.rhconecta.dev.data.movements.MovementsDataResources
import com.coppel.rhconecta.dev.framework.movements.APIService
import com.coppel.rhconecta.dev.framework.movements.MovementsDataSourceImpl
import com.coppel.rhconecta.dev.presentation.fondoahorro.movements.MovementsViewModel
import com.coppel.rhconecta.dev.usecases.movements.GetMovementsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val movementsModule: Module = module {

    viewModel {
        MovementsViewModel(
            getMovementsUseCase = get()
        )
    }
    factory { GetMovementsUseCase(movementRepository = get()) }
    factory { MovementRepository(remoteMovementsDataSource = get()) }
    single<MovementsDataResources> {
        val retrofit = ServicesRetrofitManager.getInstance().retrofitAPI
        MovementsDataSourceImpl(
            androidContext(),
            retrofit.create(APIService::class.java)
        )
    }
}
