package com.coppel.rhconecta.dev.presentation.calculator.dashboard.model

data class ResultData(
    val information: List<ItemData>,
    val funds: List<Item>,
    val imss: String?,
    val retirement: String?,
    val balance: String
)
