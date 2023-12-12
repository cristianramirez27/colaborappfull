package com.coppel.rhconecta.dev.presentation.calculator.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.FirstStepFragment
import java.util.*

class InformationDialog : DialogFragment() {
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorFragmentCommunication)
            calculatorFragmentCommunication = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AlertCalculatorDialogCopper)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_information, container, false);
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberOne = view.findViewById<TextView>(R.id.textView12)
        numberOne.text = generateHtml(resources.getString(R.string.text_information_dialog_calculator))
        val numberTwo = view.findViewById<TextView>(R.id.textView14)
        numberTwo.text = generateHtml(resources.getString(R.string.text3_information_dialog_calculator))
        val text = view.findViewById<TextView>(R.id.textView13)
        text.movementMethod = LinkMovementMethod.getInstance()
        text.setLinkTextColor(getColor(requireContext(), R.color.colorBlueLight))
        val profile = calculatorFragmentCommunication.getProfileInformation()
        var name: String = profile.nombreColaborador.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0]
        name = name.substring(0, 1).uppercase(Locale.getDefault()) + name.substring(1).lowercase(Locale.getDefault())
        text.text = generateHtml(
            resources.getString(
                R.string.text2_information_dialog_calculator,
                name,
                profile.curp,
                profile.nss
            )
        )
        val button = view.findViewById<Button>(R.id.button5)
        button.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null).replace(
                R.id.content,
                FirstStepFragment.newInstance()
            ).commit()
            dismiss()
        }
    }

    private fun generateHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }
}
