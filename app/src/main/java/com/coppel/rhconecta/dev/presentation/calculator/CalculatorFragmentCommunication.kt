package com.coppel.rhconecta.dev.presentation.calculator

import com.coppel.rhconecta.dev.business.models.ProfileResponse

interface CalculatorFragmentCommunication {
    fun showFooter(enable: Boolean)
    fun getProfileInformation(): ProfileResponse.Response
    fun registerStep(step: Int)
    fun <T : Any> getInfo(key: String): T
    fun <T : Any> saveInfo(key: String, t: T)
}
