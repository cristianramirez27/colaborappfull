package com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movement(
    val concept: String = "",
    val date: String = "",
    val invoice: String = "",
    val amount: String = ""
) : Parcelable
