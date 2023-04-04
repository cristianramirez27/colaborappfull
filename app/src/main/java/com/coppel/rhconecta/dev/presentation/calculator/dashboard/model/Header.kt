package com.coppel.rhconecta.dev.presentation.calculator.dashboard.model


class Header(title: String,
             icon :Int,
             label :Int,
             var subtitle : String ?="",
             amountSub : String? ="",
             var subtitle2 : String? ="",
             var amountSub2 : String? ="",
             var detail : Boolean = false
): Item(title,amountSub,icon,label) {
    fun generateSubtitle() : String {
        return if (subtitle2.isNullOrEmpty())
            subtitle!!
        else
            "$subtitle\n$subtitle2"
    }
    fun generateAmount() : String {
        return if (amountSub2.isNullOrBlank())
            amount!!
        else
            "$amount\n$amountSub2"
    }
}
