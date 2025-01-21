package com.coppel.rhconecta.dev.presentation.calculator.dialog

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R

class MoreInformationDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AlertHelpCalculatorDialogCopper)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_calculator_more_info, container, false)
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<ImageView>(R.id.imageView10)
        val text = view.findViewById<TextView>(R.id.textView46)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            text.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        button.setOnClickListener { dismiss() }
    }
}
