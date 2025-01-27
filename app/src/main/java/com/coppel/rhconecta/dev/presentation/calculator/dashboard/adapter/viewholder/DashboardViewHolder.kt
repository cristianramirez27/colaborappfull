package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

abstract class DashboardViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
     abstract fun onBind(dashboardItem: DashboardItem)
}
