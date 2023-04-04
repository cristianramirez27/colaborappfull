package com.coppel.rhconecta.dev.presentation.calculator.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R

class ProgressDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_progress_calculator, container, false)
        isCancelable = false
        dialog?.window?.let {
            it.setBackgroundDrawableResource(R.color.transparent)
        }
        return view
    }
}
