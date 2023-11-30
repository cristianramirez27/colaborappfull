package com.coppel.rhconecta.dev.presentation.calculator.dashboard

sealed class UIState {
    object Initial : UIState()
    object InProgress : UIState()
    class Error(val message: String) : UIState()
    class Success<T>(val result: T) : UIState()
}