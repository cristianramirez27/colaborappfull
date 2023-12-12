package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.rhconecta.dev.domain.calculator.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val listedWeekCalculatorUseCase: ListedWeekCalculatorUseCase,
    private val registerStepUseCase: RegisterStepUseCase,
) : ViewModel() {
    private var _uiState = MutableLiveData<UIState>(UIState.Initial)
    val uiState: LiveData<UIState> = _uiState
    fun getListedWeek(url: String, token: String, nss: String, antiquity: String) {
        viewModelScope.launch {
            _uiState.value = UIState.InProgress
            val result = listedWeekCalculatorUseCase(url, token, nss, antiquity)
            result.fold({ e ->
                _uiState.value = UIState.Error(e.message ?: "")
            }, { info ->
                _uiState.value = UIState.Success(info.listedWeek)
            })
        }
    }

    fun registerStep(url: String, token: String, numberEmployer: String, nameEmployer: String, step: Int) {
        viewModelScope.launch {
            registerStepUseCase(url, token, register = Register(numberEmployer, nameEmployer, step))
        }
    }

    fun saveDataSavings(savings: DataSavings) {
        _uiState.value = UIState.Success(savings.weeks)
    }
}
