package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

class InformationSalaryViewHolder ( view : View) : DashboardViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.textView62)
    private val title2 = view.findViewById<TextView>(R.id.textView63)
    private val label = view.findViewById<TextView>(R.id.textView61)
    private val label2 = view.findViewById<TextView>(R.id.textView65)
    private val value = view.findViewById<TextView>(R.id.textView60)
    private val value2 = view.findViewById<TextView>(R.id.textView64)

    override fun onBind(dashboardItem: DashboardItem) {
        if (dashboardItem is DashboardItem.InformationSalary){
            val pair = dashboardItem.data
            pair.first.let {
                title.text = it.title
                label.text = it.salaryPercent.label
                value.text = it.salaryPercent.value
            }
            pair.second.let {
                title2.text = it.title
                label2.text = it.salaryPercent.label
                value2.text = it.salaryPercent.value
            }
        }
    }
}
