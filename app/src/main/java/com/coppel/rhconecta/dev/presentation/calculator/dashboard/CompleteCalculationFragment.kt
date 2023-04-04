package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.DashboardRecyclerAdapter
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.*
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ProgressDialog
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ScoreCalculatorDialog
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import java.util.Date

class CompleteCalculationFragment : Fragment() {
    private val dialog by lazy { ScoreCalculatorDialog() }
    private val dialogProgress by lazy { ProgressDialog() }
    private var list: MutableList<DashboardItem> = arrayListOf()
    private val adapterDashboard by lazy { DashboardRecyclerAdapter(list) }
    private var listTemp: ArrayList<Item> = arrayListOf()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_complete_calculation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.rv).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = adapterDashboard
        }
        listTemp.add(
            Header(
                "Fondo de Ahorro",
                R.drawable.ic_info,
                R.string.text_dashboard_complete_info_help,
                "Capital",
                "$6,000",
                "Intereses",
                "$3,000",
                true
            )
        )
        listTemp.add(Item("Fondo de Ahorro", "$200  MXN"))
        listTemp.add(
            Header(
                "Caja de Ahorro",
                R.drawable.ic_info,
                R.string.text_dashboard_info_help2,
                "Capital",
                "$6,000",
                "Intereses",
                "$3,000",
                true
            )
        )
        listTemp.add(Item("Aportaciones ordinarias", "$1000,000  MXN"))
        listTemp.add(Item("Aportaciones addicionales", "$50,000  MXN"))
        listTemp.add(Item("AER", "$200  MXN", R.drawable.ic_info, R.string.text_complete_dashboard_info_help3))
        listTemp.add(Item("AER 60", "$200  MXN", R.drawable.ic_info, R.string.text_complete_dashboard_info_help4))
        listTemp.add(
            Header(
                "Beneficio al retiro",
                R.drawable.ic_info,
                R.string.text_dashboard_info_help5,
                "Capital",
                "$200",
                detail = true
            )
        )
        listTemp.add(Item("Beneficio al retiro", "$200  MXN"))

        list.add(DashboardItem.Header(R.string.title_complete_dashboard, "", 4, 4))
        list.add(
            DashboardItem.Retirement(
                getString(R.string.title_retirement_dashboard),
                listOf(
                    ItemData(getString(R.string.age_label_retirement_dashboard), "65 años"),
                    ItemData(getString(R.string.antiquity_label_retirement_dashboard), "35 años 11 meses"),
                    ItemData(getString(R.string.weeks_imss_label_retirement_dashboard), "3,000 semanas")
                )
            )
        )
        list.add(
            DashboardItem.Saving(
                getString(R.string.title_saving_dashboard),
                listTemp,
                getString(R.string.content_saving_dashboard),
                ItemData(getString(R.string.text_dashboard_total), "$3,808,000")
            )
        )
        list.add(
            DashboardItem.InformationSalary(
                Pair(
                    InformationData(
                        getString(R.string.title_information_salary),
                        ItemData(getString(R.string.label_information_salary), "40%")
                    ),
                    InformationData(
                        getString(R.string.title_information_salary2),
                        ItemData(getString(R.string.label_information_salary2), "20%")
                    )
                )
            )
        )
        list.add(
            DashboardItem.OptionalItem(
                getString(R.string.title_information_optional_item),
                getString(R.string.content_information_optional_item),
                R.string.text_action_item,
                R.drawable.ic_saving_calculator,
                { activity?.onBackPressed() })
        )
        list.add(
            DashboardItem.OptionalItem(
                getString(R.string.title_information_optional_item2),
                getString(R.string.content_information_optional_item2),
                R.string.text_action_item2,
                R.drawable.ic_saving_calculator2
            ) {
                requireActivity().setResult(Activity.RESULT_OK, Intent().putExtra("goto", OPTION_SAVING_FUND))
                requireActivity().finish()
            })
        list.add(DashboardItem.Button(R.string.text_dashboard_complete_button) {

            val validation: String? = AppUtilities.getStringFromSharedPreferences(
                requireContext(),
                AppConstants.SHARED_PREFERENCES_SURVEY_SCORE_CALCULATOR
            )
            validation?.let {
                val current = Date()
                val date = Date()
                date.time = it.toLong()
                if (DateTimeUtil.validateTimeElapsedSurvey(date, current)) {
                    showSurvey()
                } else {
                    requireActivity().finish()
                }
            } ?: showSurvey()
        })
        adapterDashboard.notifyDataSetChanged()
        dialogProgress.show(parentFragmentManager, "")
        handler.postDelayed({ dialogProgress.dismiss() }, 2000L)

    }

    private fun showSurvey() {
        dialog.show(
            parentFragmentManager,
            "score"
        )
    }
}
