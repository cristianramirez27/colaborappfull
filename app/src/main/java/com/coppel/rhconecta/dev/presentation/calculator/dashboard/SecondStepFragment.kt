package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.business.utils.MoneyTextWatcher
import com.coppel.rhconecta.dev.di.calculator.InjectorCalculator
import com.coppel.rhconecta.dev.domain.calculator.DataSavings
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dialog.HelpCalculatorDialog
import com.coppel.rhconecta.dev.presentation.calculator.dialog.ProgressDialog
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import com.coppel.rhconecta.dev.views.utils.TextUtilities
import javax.inject.Inject

class SecondStepFragment : Fragment(), View.OnClickListener {
    private val dialogHelp by lazy { HelpCalculatorDialog() }
    private val ballon by lazy {
        TextUtilities.getBallon(
            requireContext(),
            R.string.text_dashboard_help_infonavit,
            this
        )
    }
    lateinit var edithInfonavit: EditText

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CalculationViewModel

    @Inject
    lateinit var contextInject: Context
    private val dialogProgress by lazy { ProgressDialog() }
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication
    private val dialogWarning by lazy { DialogFragmentWarning() }
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
        calculatorFragmentCommunication.registerStep(2)
        viewModel = ViewModelProvider(this, viewModelFactory)[CalculationViewModel::class.java]
        val token = AppUtilities.getStringFromSharedPreferences(contextInject, AppConstants.SHARED_PREFERENCES_TOKEN)
        val url = AppUtilities.getStringFromSharedPreferences(contextInject, AppConfig.ENDPOINT_CALCULATOR_WEEKS)
        val profile = calculatorFragmentCommunication.getProfileInformation()
        viewModel.getListedWeek(url, token, profile.nss, profile.antiguedad)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.textView16)
        val date = view.findViewById<TextView>(R.id.section_label)
        val helpImss = view.findViewById<TextView>(R.id.textView37)
        val helpAfore = view.findViewById<TextView>(R.id.textView40)
        val helpVoluntaryAfore = view.findViewById<TextView>(R.id.textView42)
        val helpInfonavit = view.findViewById<TextView>(R.id.textView45)
        val titleInfonavit = view.findViewById<TextView>(R.id.textView44)
        edithInfonavit = view.findViewById<EditText>(R.id.editTextNumberDecimal4)
        val edithImss = view.findViewById<EditText>(R.id.editTextNumberDecimal)
        val edithAfore = view.findViewById<EditText>(R.id.editTextNumberDecimal2)
        val edithVoluntary = view.findViewById<EditText>(R.id.editTextNumberDecimal3)
        val icon = view.findViewById<ImageView>(R.id.imageView8)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox2)
        val checkBox2 = view.findViewById<CheckBox>(R.id.checkBox3)
        val segmentedProgress = view.findViewById<SegmentedProgressBar>(R.id.segmented_progressbar)
        val button = view.findViewById<Button>(R.id.button7)
        edithInfonavit.addTextChangedListener(MoneyTextWatcher(edithInfonavit, false))
        val moneyTextWatcher = MoneyTextWatcher(edithImss, false)
        moneyTextWatcher.setPrefix("")
        edithImss.addTextChangedListener(moneyTextWatcher)
        edithAfore.addTextChangedListener(MoneyTextWatcher(edithAfore, false))
        edithVoluntary.addTextChangedListener(MoneyTextWatcher(edithVoluntary, false))

        title.setText(R.string.title_dashboard_calculator2)
        date.text = null
        button.alpha = 0.5f
        segmentedProgress.setCompletedSegments(2)
        segmentedProgress.setSegmentCount(4)
        icon.setOnClickListener(this)
        helpImss.setOnClickListener(this)
        helpAfore.setOnClickListener(this)
        helpVoluntaryAfore.setOnClickListener(this)
        helpInfonavit.setOnClickListener(this)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            checkBox2.isChecked = !isChecked
            if (isChecked)
                validateStateButton(button, isChecked)
        }
        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            checkBox.isChecked = !isChecked
            if (isChecked) {
                validateStateButton(button, isChecked)
                helpInfonavit.visibility = VISIBLE
                titleInfonavit.visibility = VISIBLE
                edithInfonavit.visibility = VISIBLE
            } else {
                helpInfonavit.visibility = GONE
                titleInfonavit.visibility = GONE
                edithInfonavit.visibility = GONE
            }
        }
        button.setOnClickListener {
            if ((checkBox.isChecked || checkBox2.isChecked)) {
                ThirdStepFragment.retirementData = null
                val savings = DataSavings(
                    edithAfore.text.toString().filter { it in "0123456789" }.parseSaveLong(),
                    edithVoluntary.text.toString().filter { it in "0123456789" }.parseSaveLong(),
                    edithImss.text.toString().filter { it in "0123456789" }.parseSaveInt(),
                )
                if (checkBox2.isChecked)
                    savings.infonavit = edithInfonavit.text.toString().filter { it in "0123456789" }.parseSaveLong()

                viewModel.saveDataSavings(savings)
                calculatorFragmentCommunication.saveInfo("savings", savings)
                parentFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content, ThirdStepFragment())
                    .commit()

            }
        }
        viewModel.uiState.observe(this) { state ->
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
                    edithImss.setText("${state.result}")
                    if (dialogProgress.isAdded)
                        dialogProgress.dismiss()
                }
            }
        }
    }

    private fun validateStateButton(button: Button, validation: Boolean) {
        synchronized(this) {
            button.alpha = if (validation) 1.0f else 0.5f
            button.isClickable = validation
        }
    }

    override fun onClick(p0: View?) {
        p0?.let { v ->
            when (v.id) {
                R.id.imageView8 -> {
                    val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(v.windowToken, 0)
                    ballon.showAlignBottom(v)
                }

                R.id.textView37 -> {
                    dialogHelp.setData(
                        R.string.title_dialog_help_information,
                        R.string.subtitle_dialog_help_imss,
                        R.drawable.imss,
                        R.string.text2_information_dialog_calculator
                    )
                    dialogHelp.show(parentFragmentManager, "imss")
                }

                R.id.textView40 -> {
                    dialogHelp.setData(
                        R.string.title_dialog_help_information,
                        R.string.subtitle_dialog_help_afore,
                        R.drawable.ic_afore
                    )
                    dialogHelp.show(parentFragmentManager, "afore")
                }

                R.id.textView42 -> {
                    dialogHelp.setData(
                        R.string.title_dialog_help_information,
                        R.string.subtitle_dialog_help_voluntary_afore,
                        R.drawable.ic_afore_voluntary
                    )
                    dialogHelp.show(parentFragmentManager, "voluntary_afore")
                }

                R.id.textView45 -> {
                    dialogHelp.setData(
                        R.string.title_dialog_help_information,
                        R.string.subtitle_dialog_help_infonavit,
                        R.drawable.ic_infonavit_cal
                    )
                    dialogHelp.show(parentFragmentManager, "infonavit")
                }

                else -> {}
            }
        }
    }
}

fun String.parseSaveLong(): Long {
    return try {
        this.toLong()
    } catch (e: Exception) {
        0L
    }
}

fun String.parseSaveInt(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        0
    }
}