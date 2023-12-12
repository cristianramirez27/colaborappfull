package com.coppel.rhconecta.dev.domain.calculator

class InformationCalculator(
    var antiguedad: String?,
    val fechaCorte: String,
    val fechaNacimiento: String?,
    val mesesAcumulables: Int,
    val salarioBase: Double,
    val tipoNomina: Int,
    val imp_aer: String?,
    val imp_aer60: String?,
    val imp_aportacionesadicionales: String?,
    val imp_aportacionesordinarias: String?,
    val imp_aportemensual: String,
    val imp_fondodeahorro: String?,
    val imp_salariomensual: String
) {
    fun processDate(date: String): String {
        val fields = date.split("/")
        return "${fields[2]}-${fields[1]}-${fields[0]}"
    }
}
