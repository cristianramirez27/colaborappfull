package com.coppel.rhconecta.dev.presentation.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.rhconecta.dev.domain.calculator.InformationCalculator
import com.coppel.rhconecta.dev.domain.calculator.InformationCalculatorUseCase
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.UIState
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Header
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.InformationEmployee
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CalculatorViewModel @Inject constructor(
    private val informationCalculatorUseCase: InformationCalculatorUseCase,
) : ViewModel() {
    private var _information = MutableStateFlow<InformationEmployee?>(null)
    private var _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState: StateFlow<UIState> = _uiState
    val information: StateFlow<InformationEmployee?> = _information
    private var _list = MutableLiveData<List<Item>>()
    private var _calculator = MutableLiveData<InformationCalculator>()
    var calculator: LiveData<InformationCalculator> = _calculator
    val list: LiveData<List<Item>> = _list
    private val simpleDateFormat by lazy { SimpleDateFormat("dd/MM/yyyy") }


    private fun validateValues(
        amount: Int,
        flag: Boolean,
        list: MutableList<Item>,
        title: String,
        icon: Int? = null,
        info: Int? = null
    ) {
        if (flag)
            list.add(Item(title, amount.formatInteger(), icon, info))
    }

    private fun validateField(value: String?, list: MutableList<Int>, index: Int): Boolean {
        return value?.parseSaveInt()?.let { list[index] = it; it > 0 } == true
    }

    fun getInformationCalculator(
        url: String,
        token: String,
        numberEmployer: String,
        listTitles: List<String>,
        listIcons: List<Int?>,
        listInfo: List<Int?>
    ) {
        viewModelScope.launch {
            _uiState.value = UIState.InProgress
            val result = informationCalculatorUseCase(url, token, numberEmployer)
            result.fold({ e ->
                _uiState.value = UIState.Error(e.message ?: "Error")
            }, { info ->
                _calculator.value = info
                val listTemp: MutableList<Item> = arrayListOf()
                val listValues: MutableList<Int> = arrayListOf(0, 0, 0, 0)
                var total = 0
                info.imp_fondodeahorro?.let {
                    val amount = it.parseSaveInt()
                    if (amount > 0) {
                        listTemp.add(Header(listTitles[4], listIcons[4], listInfo[4]))
                        listTemp.add(Item(listTitles[4], amount.formatInteger()))
                        total += amount
                    }
                }
                val listBooleans = listOf(
                    validateField(info.imp_aportacionesordinarias, listValues, 0),
                    validateField(info.imp_aportacionesadicionales, listValues, 1),
                    validateField(info.imp_aer, listValues, 2),
                    validateField(info.imp_aer60, listValues, 3)
                )

                if (listBooleans.any { it })
                    listTemp.add(Header(listTitles[5], listIcons[5], listInfo[5]))
                for (i in 0..3) {
                    validateValues(listValues[i], listBooleans[i], listTemp, listTitles[i], listIcons[i], listInfo[i])
                    total += listValues[i]
                }
                _list.value = listTemp
                var age = 0
                var labelAntiquity = ""
                info.antiguedad?.let {
                    val date = simpleDateFormat.parse(it)
                    val currentDate = Date()
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    val calendarCurrent = Calendar.getInstance()
                    calendarCurrent.time = currentDate

                    val stringBuilder = StringBuilder()
                    var month = calendarCurrent.get(Calendar.MONTH) - calendar.get(Calendar.MONTH)
                    var year = calendarCurrent.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
                    if (calendar.get(Calendar.DAY_OF_MONTH) > calendarCurrent.get(Calendar.DAY_OF_MONTH)) {
                        month -= 1
                    }

                    if (month < 0) {
                        month += 12
                        year -= 1
                    }

                    if (year >= 1)
                        stringBuilder.append("$year años ")
                    if (month >= 1)
                        stringBuilder.append("$month meses")

                    labelAntiquity = stringBuilder.toString()
                }

                info.fechaNacimiento?.let {
                    age = calculateYear(it)
                }
                _uiState.value = UIState.Success(
                    InformationEmployee(
                        "$age años",
                        labelAntiquity,
                        info.imp_salariomensual.parseSaveInt(),
                        info.imp_aportemensual.parseSaveInt().formatInteger(),
                        "$total",
                        info.fechaCorte
                    )
                )
            })
        }
    }

    private fun calculateYear(dates: String): Int {
        val date = simpleDateFormat.parse(dates)
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        val calendarCurrent = Calendar.getInstance()
        calendarCurrent.time = currentDate
        var month = calendarCurrent.get(Calendar.MONTH) - calendar.get(Calendar.MONTH)
        var year = calendarCurrent.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
        if (calendar.get(Calendar.DAY_OF_MONTH) > calendarCurrent.get(Calendar.DAY_OF_MONTH)) {
            month -= 1
        }

        if (month < 0) {
            year -= 1
        }
        return year
    }
}

fun String.parseSaveInt(): Int {
    return try {
        this.toDouble().toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun Int.formatInteger(): String {
    val parsed = BigDecimal(this)
    val formatter = DecimalFormat("$ #,### MXN", DecimalFormatSymbols(Locale.US))
    return formatter.format(parsed)
}
