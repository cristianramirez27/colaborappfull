package com.coppel.rhconecta.dev.di.calculator;

import com.coppel.rhconecta.dev.CoppelApp;

public class InjectorCalculator {
    private static CalculatorComponent component = null;

    public static CalculatorComponent getComponentCalculator() {
        if (component == null) {
            component = DaggerCalculatorComponent.builder().application(CoppelApp.getContext()).build();
        }
        return component;
    }
}
