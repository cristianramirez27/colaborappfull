package com.coppel.rhconecta.dev.presentation.calculator.welcome

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dialog.InformationDialog
import com.coppel.rhconecta.dev.presentation.calculator.privacy.PrivacyCalculatorFragment

class WelcomeCalculatorFragment : Fragment() {
    companion object {
        fun newInstance() = WelcomeCalculatorFragment()
    }

    private lateinit var viewModel: WelcomeCalculatorViewModel
    private lateinit var dialog: InformationDialog
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorFragmentCommunication)
            calculatorFragmentCommunication = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = InformationDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WelcomeCalculatorViewModel::class.java]
        calculatorFragmentCommunication.showFooter(false)
        val textWarning = view.findViewById<TextView>(R.id.textView8)
        textWarning.setLinkTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlueLight))
        textWarning.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.content, PrivacyCalculatorFragment.newInstance()).commit()
        }
        val check = view.findViewById<CheckBox>(R.id.checkBox)
        val button = view.findViewById<Button>(R.id.button3)
        button.alpha = 0.5f
        check.setOnCheckedChangeListener { _, ischecked ->
            button.isEnabled = ischecked
            val alpha = if (ischecked) 1f else 0.5f
            button.alpha = alpha
        }

        button.setOnClickListener {
            if (check.isChecked) {
                dialog.show(parentFragmentManager, "")
            }
        }
    }
}
