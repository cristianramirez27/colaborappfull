package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import android.widget.Button
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

class ButtonViewHolder ( view : View) : DashboardViewHolder(view) {
    private val button = view.findViewById<Button>(R.id.button10)

    override fun onBind(dashboardItem: DashboardItem) {
        if (dashboardItem is DashboardItem.Button){
            button.setText(dashboardItem.title)
            button.setOnClickListener(dashboardItem.listener)
        }
    }
}
