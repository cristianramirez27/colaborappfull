package com.coppel.rhconecta.dev.presentation.calculator.dashboard.model

sealed class DashboardItem{
    class Retirement(var title : String, var list: List<ItemData>) : DashboardItem()
    class Saving(var title : String, var list: List<Item>, var content : String?, var total : ItemData) : DashboardItem()
    class InformationSalary(var data: Pair<InformationData, InformationData>) : DashboardItem()
    class OptionalItem(var title: String, var content: String, var textAction: Int, var icon : Int,val listener : android.view.View.OnClickListener? = null) : DashboardItem()
    class Button(val title : Int, val listener : android.view.View.OnClickListener? = null) : DashboardItem()
    class Header(var title : Int, val label : String ,val segmentCompleted : Int, val totalSegment: Int) : DashboardItem()

    fun getType() : Int {
        return when (this) {
            is Retirement -> 0
            is Saving ->  1
            is InformationSalary -> 2
            is OptionalItem -> 3
            is Button -> 4
            is Header -> 5
        }
    }
}
