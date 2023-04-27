package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.SavingRecyclerAdapter
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Header
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Item

class FirstStepFragment : Fragment() {

    private var list: MutableList<Item> = arrayListOf()
    private var listTemp: MutableList<Item> = arrayListOf()
    private val adapterDashboard by lazy { SavingRecyclerAdapter(list) }
    private var showItem = false
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorFragmentCommunication)
            calculatorFragmentCommunication = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listTemp.add(Header("Fondo de Ahorro", R.drawable.ic_info, R.string.text_dashboard_info_help))
        listTemp.add(Item("Fondo de Ahorro", "$200  MXN"))
        listTemp.add(Header("Caja de Ahorro", R.drawable.ic_info, R.string.text_dashboard_info_help2))
        listTemp.add(Item("Aportaciones ordinarias", "$1000,000  MXN"))
        listTemp.add(Item("Aportaciones addicionales", "$50,000  MXN"))
        listTemp.add(Item("AER", "$200  MXN", R.drawable.ic_info, R.string.text_complete_dashboard_info_help3))
        listTemp.add(Item("AER 60", "$200  MXN", R.drawable.ic_info, R.string.text_complete_dashboard_info_help4))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculatorFragmentCommunication.showFooter(true)
        val segmentedProgressbar = view.findViewById<SegmentedProgressBar>(R.id.segmented_progressbar)
        segmentedProgressbar?.setSegmentCount(4)
        segmentedProgressbar?.setCompletedSegments(1)
        val rvItems = view.findViewById<RecyclerView>(R.id.rvItems)
        rvItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterDashboard
            adapterDashboard.notifyDataSetChanged()
        }
        val textView26 = view.findViewById<TextView>(R.id.textView26)
        textView26.setOnClickListener {
            val flag = !showItem
            var icon = -1
            if (flag) {
                icon = R.drawable.ic_arrow_up
                list.addAll(listTemp)
            } else {
                icon = R.drawable.ic_arrow_down
                list.clear()
            }
            textView26.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
            adapterDashboard.notifyDataSetChanged()
            showItem = flag
        }
        val button6 = view.findViewById<Button>(R.id.button6)
        button6.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content, SecondStepFragment())
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): FirstStepFragment {
            return FirstStepFragment()
        }
    }
}
