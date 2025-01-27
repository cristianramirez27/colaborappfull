package com.coppel.rhconecta.dev.presentation.calculator.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.RetirementData
import com.coppel.rhconecta.dev.presentation.calculator.dialog.HelpCalculatorDialog
import com.coppel.rhconecta.dev.presentation.calculator.formatInteger
import com.coppel.rhconecta.dev.views.utils.TextUtilities
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider
import okhttp3.internal.format

class ThirdStepFragment : Fragment(), View.OnClickListener {

    private val bottomSheetDialog by lazy { BottomSheetDialog(requireContext(), R.style.CustomBottomSheetStyle) }
    private val ballonAge by lazy {
        TextUtilities.getBallon(
            requireContext(),
            R.string.text_dashboard_calculation_age_help
        )
    }
    private val ballonSalary by lazy {
        TextUtilities.getBallon(
            requireContext(),
            R.string.title_dashboard_calculation_card2_help
        )
    }
    private val dialogHelp by lazy {
        HelpCalculatorDialog().apply {
            setData(
                R.string.title_dashboard_calculation_dialog,
                R.string.text_dashboard_calculation_dialog
            )
        }
    }
    private var newValue = ""
    private var oldValue = "0 %"
    private var salary = 0
    private var percent = 0
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorFragmentCommunication)
            calculatorFragmentCommunication = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculatorFragmentCommunication.registerStep(3)
        salary = calculatorFragmentCommunication.getInfo("salary")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_third_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scrollView = view.findViewById<ScrollView>(R.id.scroll_view)
        val age = view.findViewById<TextView>(R.id.textView49)
        val segmentedProgress = view.findViewById<SegmentedProgressBar>(R.id.segmented_progressbar)
        val title = view.findViewById<TextView>(R.id.textView16)
        val date = view.findViewById<TextView>(R.id.section_label)
        val textPercent = view.findViewById<TextView>(R.id.textView53)
        val textSalaryPercent = view.findViewById<TextView>(R.id.textView54)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_calculator)
        val cancelBottomSheet = bottomSheetDialog.findViewById<TextView>(R.id.textView57)
        val applyBottomSheet = bottomSheetDialog.findViewById<TextView>(R.id.textView58)
        val numberPicker = bottomSheetDialog.findViewById<NumberPicker>(R.id.number_picker)
        val seekBar = view.findViewById<Slider>(R.id.seekBar)
        val icon = view.findViewById<ImageView>(R.id.imageView11)
        val icon2 = view.findViewById<ImageView>(R.id.imageView12)
        val button = view.findViewById<Button>(R.id.button8)

        newValue = "0 %"
        title.setText(R.string.title_dashboard_calculation)
        date.text = null
        scrollView.viewTreeObserver.addOnScrollChangedListener { seekBar.invalidate() }
        segmentedProgress.setCompletedSegments(3)
        segmentedProgress.setSegmentCount(4)
        age.text = format(getString(R.string.text_dashboard_calculation_age), 60)
        val values: Array<String> = (0..30).map { i -> "$i %" }.toTypedArray()
        numberPicker?.apply {
            minValue = 0
            maxValue = values.size - 1
            wrapSelectorWheel = true
            setOnLongPressUpdateInterval(500)
            displayedValues = values
        }
        numberPicker?.setOnValueChangedListener { numberPicker2, i, i2 ->
            newValue = values[i2]
            percent = i2
        }
        seekBar.addOnChangeListener { _, value, _ ->
            val valueAge = value.toInt()
            age.text = format(getString(R.string.text_dashboard_calculation_age), valueAge)
            retirementData?.age = valueAge
        }
        icon.setOnClickListener(this)
        icon2.setOnClickListener(this)
        button.setOnClickListener(this)
        cancelBottomSheet?.setOnClickListener {
            textPercent.text = oldValue
            retirementData?.percent = oldValue.subSequence(0, oldValue.length - 2).toString().toInt()
            bottomSheetDialog.dismiss()
        }
        applyBottomSheet?.setOnClickListener {
            textPercent.text = newValue
            val value = (salary * percent) / 100
            textSalaryPercent.text = value.formatInteger()
            retirementData?.percent = percent
            bottomSheetDialog.dismiss()
        }
        textPercent.setOnClickListener {
            oldValue = textPercent.text.toString()
            bottomSheetDialog.show()
            numberPicker?.value = percent
        }

        retirementData?.let {
            oldValue = "${it.percent} %"
            textPercent.text = oldValue
            val value = (salary * percent) / 100
            textSalaryPercent.text = value.formatInteger()
        } ?: run { retirementData = RetirementData(60, 0) }
    }

    override fun onClick(p0: View?) {
        p0?.let { v ->
            when (v.id) {
                R.id.imageView11 -> ballonAge.showAlignBottom(v)
                R.id.imageView12 -> ballonSalary.showAlignBottom(v)
                R.id.button8 -> {
                    calculatorFragmentCommunication.saveInfo("retirenment", retirementData!!)
                    dialogHelp.show(parentFragmentManager, "Information")
                }
            }
        }
    }

    companion object {
        var retirementData: RetirementData? = null
    }
}
