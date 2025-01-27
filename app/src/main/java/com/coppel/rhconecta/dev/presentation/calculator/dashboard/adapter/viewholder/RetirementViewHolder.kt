package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

class RetirementViewHolder (view : View) : DashboardViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.textView17)
    private val ids = listOf(R.id.textView19, R.id.textView21, R.id.textView18, R.id.textView22,  R.id.textView20, R.id.textView23 )
    private val textViews = ids.map { view.findViewById<TextView>(it) }

    override fun onBind(dashboardItem: DashboardItem) {
        if (dashboardItem is DashboardItem.Retirement){
            title.text = dashboardItem.title
            var i= 0
            dashboardItem.list.forEach {
                textViews[i].text = it.label
                i =i.inc()
                textViews[i].text = it.value
                i = i.inc()
            }
        }
    }
}
