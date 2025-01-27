package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.di.calculator.InjectorCalculator
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorViewModel
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter.SavingRecyclerAdapter
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.InformationEmployee
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Item
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ProgressDialog
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FirstStepFragment : Fragment() {

    private var list: MutableList<Item> = arrayListOf()
    private var listTemp: MutableList<Item> = arrayListOf()
    private val adapterDashboard by lazy { SavingRecyclerAdapter(list) }
    private var showItem = false
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication
    private lateinit var viewModel: CalculatorViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var numberEmployer: String

    @Inject
    lateinit var contextInject: Context
    private val dialogWarning by lazy { DialogFragmentWarning() }
    private val dialogProgress by lazy { ProgressDialog() }
    private val simpleDateFormat by lazy { SimpleDateFormat("dd/MM/yyyy") }
    private val listTitles = arrayListOf(
        "Aportaciones ordinarias",
        "Aportaciones adicionales",
        "AER",
        "AER 60",
        "Fondo de Ahorro",
        "Caja de Ahorro"
    )
    private val listIcons =
        arrayListOf(null, null, R.drawable.ic_info, R.drawable.ic_info, R.drawable.ic_info, R.drawable.ic_info)
    private val listInfo = arrayListOf(
        null,
        null,
        R.string.text_complete_dashboard_info_help3,
        R.string.text_complete_dashboard_info_help4,
        R.string.text_dashboard_info_help,
        R.string.text_dashboard_info_help2
    )
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
        calculatorFragmentCommunication.registerStep(1)
        viewModel = ViewModelProvider(this, viewModelFactory)[CalculatorViewModel::class.java]
        val token = AppUtilities.getStringFromSharedPreferences(contextInject, AppConstants.SHARED_PREFERENCES_TOKEN)
        val url = AppUtilities.getStringFromSharedPreferences(contextInject, AppConfig.ENDPOINT_CALCULATOR)
        viewModel.getInformationCalculator(url, token, numberEmployer, listTitles, listIcons, listInfo)
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
        val textViewDate = view.findViewById<TextView>(R.id.section_label)
        textViewDate.text = simpleDateFormat.format(Date())
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
        viewModel.list.observe(viewLifecycleOwner) { l ->
            listTemp.clear()
            listTemp.addAll(l)
        }
        viewModel.calculator.observe(viewLifecycleOwner) { info ->
            calculatorFragmentCommunication.saveInfo("info", info)
        }
        val textAge = view.findViewById<TextView>(R.id.textView21)
        val textAntiquity = view.findViewById<TextView>(R.id.textView22)
        val textSalary = view.findViewById<TextView>(R.id.textView23)
        val textMothSaving = view.findViewById<TextView>(R.id.textView25)
        val textTotal = view.findViewById<TextView>(R.id.textView31)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UIState.InProgress -> dialogProgress.show(parentFragmentManager, "")
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

                        is UIState.Success<*> -> {
                            if (state.result is InformationEmployee) {
                                val it: InformationEmployee = state.result
                                textAge.text = it.age
                                textAntiquity.text = it.antiquity
                                textSalary.text = "${formatInteger("${it.salary}")} MXN"
                                textMothSaving.text = it.mothSaving
                                textTotal.text = formatInteger(it.total)
                                textViewDate.text = it.fechaCorte
                                calculatorFragmentCommunication.saveInfo("salary", it.salary)
                            }
                            dialogProgress.dismiss()
                        }
                        else -> {
                            // Manejo genérico para estados no contemplados
                            dialogProgress.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun formatInteger(str: String): String? {
        val parsed = BigDecimal(str)
        val formatter = DecimalFormat("$ #,###", DecimalFormatSymbols(Locale.US))
        return formatter.format(parsed)
    }

    companion object {
        @JvmStatic
        fun newInstance(): FirstStepFragment {
            return FirstStepFragment()
        }
    }
}
