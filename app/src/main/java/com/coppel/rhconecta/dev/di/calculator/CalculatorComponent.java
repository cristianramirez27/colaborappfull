package com.coppel.rhconecta.dev.di.calculator;

import android.content.Context;
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorActivity;
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.CompleteCalculationFragment;
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.FirstStepFragment;
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.SecondStepFragment;
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ScoreCalculatorDialog;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {FragmentsModule.class, CalculatorModule.class})
public interface CalculatorComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Context context);

        CalculatorComponent build();
    }

    void inject(CalculatorActivity calculatorActivity);

    void inject(FirstStepFragment firstStepFragment);

    void inject(SecondStepFragment secondStepFragment);

    void inject(ScoreCalculatorDialog scoreCalculatorDialog);

    void inject(CompleteCalculationFragment completeCalculationFragment);
}

