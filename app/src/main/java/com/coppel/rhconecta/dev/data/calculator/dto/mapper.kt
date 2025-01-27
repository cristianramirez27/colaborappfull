package com.coppel.rhconecta.dev.data.calculator.dto

import com.coppel.rhconecta.dev.domain.calculator.InformationCalculator
import com.coppel.rhconecta.dev.domain.calculator.InformationWeek

fun CalculatorResponse.Response.toInformationCalculator(): InformationCalculator {
    return InformationCalculator(
        Antiguedad,
        FechaCorte,
        FechaNacimiento,
        MesesAcumulables,
        SalarioBase,
        TipoNomina,
        imp_aer,
        imp_aer60,
        imp_aportacionesadicionales,
        imp_aportacionesordinarias,
        imp_aportemensual,
        imp_fondodeahorro,
        imp_salariomensual
    )
}

fun WeekResponse.Response.toInformationWeek(): InformationWeek {
    return InformationWeek(weeks)
}
