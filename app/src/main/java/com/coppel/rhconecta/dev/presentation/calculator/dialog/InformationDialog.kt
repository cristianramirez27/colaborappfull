package com.coppel.rhconecta.dev.presentation.calculator.dialog

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
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.FirstStepFragment

class InformationDialog : DialogFragment() {

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
        val numberOne =  view.findViewById<TextView>(R.id.textView12)
        numberOne.text = generateHtml(resources.getString(R.string.text_information_dialog_calculator))
        val numberTwo =  view.findViewById<TextView>(R.id.textView14)
        numberTwo.text = generateHtml(resources.getString(R.string.text3_information_dialog_calculator))
        val text =  view.findViewById<TextView>(R.id.textView13)
        text.movementMethod = LinkMovementMethod.getInstance()
        text.setLinkTextColor(getColor(requireContext(),R.color.colorBlueLight))
        text.text = generateHtml(resources.getString(R.string.text2_information_dialog_calculator))
        val button =  view.findViewById<Button>(R.id.button5)
        button.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.content,
                FirstStepFragment.newInstance()).commit()
            dismiss()
        }
    }

    private fun generateHtml(source : String): Spanned{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            return Html.fromHtml(source);
        }
    }
}
