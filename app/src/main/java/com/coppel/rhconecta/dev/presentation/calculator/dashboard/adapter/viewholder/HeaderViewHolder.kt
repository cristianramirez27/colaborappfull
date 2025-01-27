package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.DashboardItem

class HeaderViewHolder(view : View) : DashboardViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.textView16)
    private val progressBar = view.findViewById<SegmentedProgressBar>(R.id.segmented_progressbar)
    private val label = view.findViewById<TextView>(R.id.section_label)

    override fun onBind(dashboardItem: DashboardItem) {
        if (dashboardItem is DashboardItem.Header){
            title.setText(dashboardItem.title)
            label.text = dashboardItem.label
            progressBar.let {
                it.setCompletedSegments(dashboardItem.segmentCompleted)
                it.setSegmentCount(dashboardItem.totalSegment)
            }
        }
    }
}
