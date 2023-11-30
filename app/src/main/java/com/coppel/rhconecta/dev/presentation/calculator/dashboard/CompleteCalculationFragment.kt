package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil
import com.coppel.rhconecta.dev.di.calculator.InjectorCalculator
import com.coppel.rhconecta.dev.domain.calculator.DataSavings
import com.coppel.rhconecta.dev.domain.calculator.InformationCalculator
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.DashboardRecyclerAdapter
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.*
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ProgressDialog
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ScoreCalculatorDialog
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class CompleteCalculationFragment : Fragment() {
    private val dialog by lazy { ScoreCalculatorDialog() }
    private val dialogProgress by lazy { ProgressDialog() }
    private var list: MutableList<DashboardItem> = arrayListOf()
    private val adapterDashboard by lazy { DashboardRecyclerAdapter(list) }
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CompleteViewModel

    @Inject
    lateinit var contextInject: Context
    private val dialogWarning by lazy { DialogFragmentWarning() }
    private val listInfo = arrayListOf(
        R.string.text_dashboard_complete_info_help,
        R.string.text_dashboard_info_help2,
        R.string.text_dashboard_info_help5,
        R.string.text_complete_dashboard_info_help3,
        R.string.text_complete_dashboard_info_help4
    )
    private val listTitlesHeaders by lazy { resources.getStringArray(R.array.headers_calculator_complete) }
    private val listSubTitlesHeaders by lazy { resources.getStringArray(R.array.subtitles_headers_calculator_complete) }
    private val listItemInformation by lazy { resources.getStringArray(R.array.information_items_calculator_complete) }
    private val listItemSavingsBank by lazy { resources.getStringArray(R.array.savings_bank_items_calculator_complete) }
    private val optionClick by lazy {
        object : DialogFragmentWarning.OnOptionClick {
            override fun onLeftOptionClick() {
            }

            override fun onRightOptionClick() {
                dialogWarning.close()
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorFragmentCommunication)
            calculatorFragmentCommunication = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorCalculator.getComponentCalculator().inject(this)
        calculatorFragmentCommunication.registerStep(4)
        viewModel = ViewModelProvider(this, viewModelFactory)[CompleteViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_complete_calculation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.rv).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = adapterDashboard
        }
        val retirementData = calculatorFragmentCommunication.getInfo<RetirementData>("retirenment")
        val savings = calculatorFragmentCommunication.getInfo<DataSavings>("savings")
        val info = calculatorFragmentCommunication.getInfo<InformationCalculator>("info")
        val token = AppUtilities.getStringFromSharedPreferences(contextInject, AppConstants.SHARED_PREFERENCES_TOKEN)
        val url = AppUtilities.getStringFromSharedPreferences(contextInject, AppConfig.ENDPOINT_CALCULATOR_ESTIMATE)
        val profile = calculatorFragmentCommunication.getProfileInformation()
        viewModel.getCalculation(
            url,
            token,
            profile.nss,
            savings,
            retirementData,
            info,
            listTitlesHeaders,
            listSubTitlesHeaders,
            listItemInformation,
            listItemSavingsBank,
            listInfo,
            R.drawable.ic_info
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect() { state ->
                    when (state) {
                        is UIState.Error -> {
                            dialogProgress.dismiss()
                            dialogWarning.setSinlgeOptionData(
                                getString(R.string.attention),
                                state.message,
                                getString(R.string.accept)
                            )
                            dialogWarning.setOnOptionClick(optionClick)
                            dialogWarning.show(parentFragmentManager, DialogFragmentWarning.TAG)
                        }

                        UIState.InProgress -> dialogProgress.show(parentFragmentManager, "")
                        is UIState.Success<*> -> {
                            if (state.result is ResultData) {
                                val result: ResultData = state.result
                                list.clear()
                                list.add(DashboardItem.Header(R.string.title_complete_dashboard, "", 4, 4))
                                list.add(
                                    DashboardItem.Retirement(
                                        getString(R.string.title_retirement_dashboard),
                                        result.information
                                    )
                                )
                                list.add(
                                    DashboardItem.Saving(
                                        getString(R.string.title_saving_dashboard),
                                        result.funds,
                                        getString(R.string.content_saving_dashboard),
                                        ItemData(getString(R.string.text_dashboard_total), result.balance)
                                    )
                                )
                                result.retirement?.let { ret ->
                                    list.add(
                                        DashboardItem.InformationSalary(
                                            Pair(
                                                InformationData(
                                                    getString(R.string.title_information_salary),
                                                    ItemData(
                                                        getString(R.string.label_information_salary),
                                                        ret
                                                    )
                                                ),
                                                result.imss?.let {
                                                    InformationData(
                                                        getString(R.string.title_information_salary2),
                                                        ItemData(
                                                            getString(R.string.label_information_salary2),
                                                            result.imss
                                                        )
                                                    )
                                                }
                                            )
                                        )
                                    )
                                }
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
                                        requireActivity().setResult(
                                            Activity.RESULT_OK,
                                            Intent().putExtra("goto", OPTION_SAVING_FUND)
                                        )
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
                                dialogProgress.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showSurvey() {
        dialog.show(
            parentFragmentManager,
            "score"
        )
    }
}
