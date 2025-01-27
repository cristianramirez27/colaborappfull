package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.rhconecta.dev.domain.calculator.DataSavings
import com.coppel.rhconecta.dev.domain.calculator.InformationCalculator
import com.coppel.rhconecta.dev.domain.calculator.ResultCalculatorUseCase
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

class CompleteViewModel @Inject constructor(private val resultCalculatorUseCase: ResultCalculatorUseCase) :
    ViewModel() {

    private var _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState: StateFlow<UIState> = _uiState
    private val formatter by lazy {
        DecimalFormat("$ #,### MXN", DecimalFormatSymbols(Locale.US)).apply {
            setRoundingMode(java.math.RoundingMode.DOWN)
        }
    }
    private val matchContext = java.math.MathContext(34, RoundingMode.DOWN)
    fun getCalculation(
        url: String,
        token: String,
        nss: String,
        savings: DataSavings,
        retirementData: RetirementData,
        informationCalculator: InformationCalculator,
        headers: Array<String>,
        subtitles: Array<String>,
        informationItems: Array<String>,
        savingsBankItems: Array<String>,
        listInfo: List<Int>,
        @IdRes icon: Int
    ) {
        viewModelScope.launch {
            _uiState.value = UIState.InProgress
            val result = resultCalculatorUseCase(url, token, nss, savings, retirementData, informationCalculator)
            result.fold({ e ->
                _uiState.value = UIState.Error(e.message ?: "")
            },
                { r ->
                    val info = arrayListOf<ItemData>(
                        ItemData(informationItems[0], r.age),
                        ItemData(informationItems[1], r.antiquity),
                        ItemData(informationItems[2], r.weeks)
                    )
                    val funds = ArrayList<Item>()
                    var total = BigInteger("0")
                    funds.add(
                        Header(
                            headers[0],
                            icon,
                            listInfo[0],
                            if (r.savingsFundCapital.isNullOrBlank()) null else subtitles[0],
                            r.savingsFundCapital?.formatInteger(),
                            r.savingsFundInterest.takeIf { it.isNullOrBlank() } ?: subtitles[1],
                            r.savingsFundInterest?.formatInteger(),
                            true
                        )
                    )
                    val saving = validationField(r.savingsFund)
                    total += saving.toBigInteger()
                    if (saving > BigDecimal.ZERO)
                        funds.add(Item(headers[0], formatter.format(saving)))

                    funds.add(
                        Header(
                            headers[1],
                            icon,
                            listInfo[1],
                            if (r.savingsBankCapital.isNullOrBlank()) null else subtitles[0],
                            r.savingsBankCapital?.formatInteger(),
                            r.savingsBankInterest.takeIf { it.isNullOrBlank() } ?: subtitles[1],
                            r.savingsBankInterest?.formatInteger(),
                            true
                        )
                    )
                    val ordinary = validationField(r.ordinaryContribution)
                    total += ordinary.toBigInteger()
                    if (ordinary > BigDecimal.ZERO)
                        funds.add(Item(savingsBankItems[0], formatter.format(ordinary)))
                    val voluntary = validationField(r.voluntaryContribution)
                    total += voluntary.toBigInteger()
                    if (voluntary > BigDecimal.ZERO)
                        funds.add(Item(savingsBankItems[1], formatter.format(voluntary)))
                    val aer = validationField(r.aer)
                    total += aer.toBigInteger()
                    if (aer > BigDecimal.ZERO)
                        funds.add(Item(savingsBankItems[2], formatter.format(aer), icon, listInfo[3]))
                    val aer2 = validationField(r.aer2)
                    total += aer2.toBigInteger()
                    if (aer2 > BigDecimal.ZERO)
                        funds.add(Item(savingsBankItems[3], formatter.format(aer2), icon, listInfo[4]))
                    var totalBenefit = validationField(r.benefit).toBigInteger()
                    total += totalBenefit
                    val pensionAmount = validationField(r.pensionBalance).toBigInteger()
                    totalBenefit += pensionAmount
                    total += pensionAmount
                    val label = if (totalBenefit > BigInteger.ZERO) formatter.format(totalBenefit) else null

                    funds.add(
                        Header(
                            headers[2],
                            icon,
                            listInfo[2],
                            if (r.benefitCapital.isNullOrEmpty()) "" else subtitles[0],
                            label,
                            detail = true
                        )
                    )
                    label?.let {
                        funds.add(Item(headers[2], it))

                    }
                    _uiState.value = UIState.Success(ResultData(
                        info,
                        funds,
                        processPercent(r.percentImss),
                        processPercent(r.percentRetirement),
                        formatter.format(total).let { t -> t.substring(0, t.length - 4) }
                    ))
                })

        }
    }

    private fun processPercent(value: String?): String? {
        val temp = value?.let { it.substring(0, it.length - 1).toFloat().toInt() }

        return temp?.let {
            if (it == 0) {
                null
            } else {
                "$it%"
            }
        }
    }

    private fun validationField(value: String?): BigDecimal {
        return value?.toBigDecimalOrNull(matchContext) ?: BigDecimal.ZERO
    }

}

fun String.formatInteger(): String {
    val parsed = BigDecimal(this)
    val formatter = DecimalFormat("$ #,### MXN", DecimalFormatSymbols(Locale.US))
    formatter.setRoundingMode(RoundingMode.DOWN)
    return formatter.format(parsed)
}