package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

class OptionalInformationViewHolder(view : View) : DashboardViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.textView66)
    private val content = view.findViewById<TextView>(R.id.textView67)
    private val action = view.findViewById<TextView>(R.id.textView68)
    private val image = view.findViewById<ImageView>(R.id.imageView13)

    override fun onBind(dashboardItem: DashboardItem) {
        if (dashboardItem is DashboardItem.OptionalItem){
            title.text = dashboardItem.title
            content.text = dashboardItem.content
            action.setText(dashboardItem.textAction)
            action.setOnClickListener(dashboardItem.listener)
            image.setImageResource(dashboardItem.icon)
        }
    }
}
