package com.coppel.rhconecta.dev.presentation.calculator.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.rhconecta.dev.domain.calculator.ScoreCalculator
import com.coppel.rhconecta.dev.domain.calculator.ScoreUseCase
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreViewModel @Inject constructor(private val scoreUseCase: ScoreUseCase) : ViewModel() {
    private var _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState: StateFlow<UIState> = _uiState
    fun score(url: String, token: String, score: Int, numberEmployer: String, like: String, change: String) {
        viewModelScope.launch {
            _uiState.value = UIState.InProgress
            val result =
                scoreUseCase(url, token, scoreCalculator = ScoreCalculator(score, numberEmployer, like, change))
            result.fold({ e ->
                UIState.Error(e.message ?: "Error")
            }, { info ->
                _uiState.value = if (info.code == 200) {
                    UIState.Success<String>(info.message)
                } else {
                    UIState.Error(info.message)
                }
            })
        }
    }
}
