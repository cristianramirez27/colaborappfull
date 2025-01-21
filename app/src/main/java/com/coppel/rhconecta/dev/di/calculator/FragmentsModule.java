package com.coppel.rhconecta.dev.di.calculator;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorViewModel;
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.CalculationViewModel;
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.CompleteViewModel;
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ScoreViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

import javax.inject.Singleton;

@Module
public abstract class FragmentsModule {

    @Binds
    @Singleton
    abstract ViewModelProvider.Factory bindViewModelProvider(CalculatorViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(CalculatorViewModel.class)
    abstract ViewModel bindCalculatorViewModel(CalculatorViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CalculationViewModel.class)
    abstract ViewModel bindCalculationViewModel(CalculationViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ScoreViewModel.class)
    abstract ViewModel bindScoreViewModel(ScoreViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CompleteViewModel.class)
    abstract ViewModel bindCompleteViewModel(CompleteViewModel viewModel);

}
