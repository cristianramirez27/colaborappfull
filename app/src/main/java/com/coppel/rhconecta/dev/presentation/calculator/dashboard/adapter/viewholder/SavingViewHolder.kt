package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.SavingRecyclerAdapter
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Item

class SavingViewHolder (view : View) : DashboardViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.textView26)
    private val content = view.findViewById<TextView>(R.id.textView59)
    private val value = view.findViewById<TextView>(R.id.textView31)
    private var showItem = false
    private var list : MutableList<Item> = arrayListOf()
    private var listTemp : List<Item> = emptyList()
    private val adapterDashboard  by lazy { SavingRecyclerAdapter(list) }
    private val recycler = view.findViewById<RecyclerView>(R.id.rvItems).apply {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(view.context)
        adapter = adapterDashboard
    }

    init {
        title.setOnClickListener {
            val flag = !showItem
            var icon = -1
            if (flag){
                icon = R.drawable.ic_arrow_up
                list.addAll(listTemp)
            }else {
                icon = R.drawable.ic_arrow_down
                list.clear()
            }
            title.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0)
            adapterDashboard.notifyDataSetChanged()
            showItem = flag
        }
    }
    override fun onBind(dashboardItem: DashboardItem) {
        if (dashboardItem is DashboardItem.Saving){
            title.text = dashboardItem.title
            content.text = dashboardItem.content
            listTemp = dashboardItem.list
            list.clear()
            value.text = dashboardItem.total.value
        }
    }
}
