package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder.*
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

class DashboardRecyclerAdapter (val list: List<DashboardItem>) :  RecyclerView.Adapter<DashboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        return when(viewType){
            0 -> RetirementViewHolder(inflater.inflate(R.layout.item_data_retirement, parent, false))
            1 -> SavingViewHolder(inflater.inflate(R.layout.item_saving, parent, false))
            2 -> InformationSalaryViewHolder(inflater.inflate(R.layout.item_information_salary, parent, false))
            3 -> OptionalInformationViewHolder(inflater.inflate(R.layout.item_optional_information, parent, false))
            4 -> ButtonViewHolder(inflater.inflate(R.layout.item_button_dashboard, parent, false))
            else -> HeaderViewHolder(inflater.inflate(R.layout.item_head_calculator, parent, false))
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getType()
    }
}
