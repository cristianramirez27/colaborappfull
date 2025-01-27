package com.coppel.rhconecta.dev.presentation.calculator.dialog

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.di.calculator.InjectorCalculator
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.UIState
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ScoreCalculatorDialog : DialogFragment() {
    private var score: Int = 0
    private val EMOJI_FILTER: InputFilter = object : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (index in start until end) {
                val char = source[index]
                val type = Character.getType(char)
                val code = char.code
                if (code == 9725 || code == 9726 || type == Character.SURROGATE.toInt() || type == Character.NON_SPACING_MARK.toInt() || type == Character.OTHER_SYMBOL.toInt() || type == Character.OTHER_PUNCTUATION.toInt()) {
                    return ""
                }
            }
            return null
        }
    }
    private val dialogProgress by lazy { ProgressDialog() }
    private val dialogWarning by lazy { DialogFragmentWarning() }
    private lateinit var viewModel: ScoreViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var numberEmployer: String

    @Inject
    lateinit var contextInject: Context
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication
    private val optionClick by lazy {
        object : DialogFragmentWarning.OnOptionClick {
            override fun onLeftOptionClick() {
            }

            override fun onRightOptionClick() {
                dialogWarning.close()
                this@ScoreCalculatorDialog.dismiss()
                activity?.finish()
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
        calculatorFragmentCommunication.registerStep(5)
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]
        setStyle(STYLE_NO_TITLE, R.style.AlertHelpCalculatorDialogCopper)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_calculator_score, container, false)
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rating = view.findViewById<RatingBar>(R.id.ratingBar)
        val comment = view.findViewById<EditText>(R.id.editTextTextMultiLine)
        val comment2 = view.findViewById<EditText>(R.id.editTextTextMultiLine2)
        val exit = view.findViewById<ImageView>(R.id.imageView10)
        val button = view.findViewById<Button>(R.id.button11)
        val filters = arrayOf(EMOJI_FILTER)
        button.alpha = 0.5f
        button.isEnabled = false
        comment.filters = filters
        comment2.filters = filters
        rating.setOnRatingBarChangeListener { ratingBar, fl, b ->
            score = fl.toInt()
            if (score > 0) {
                button.alpha = 1f
                button.isEnabled = true
            } else {
                button.alpha = 0.5f
                button.isEnabled = false
            }
        }
        exit.setOnClickListener {
            dismiss()
            activity?.finish()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UIState.Error -> {
                            showDialog(state.message)
                        }

                        UIState.InProgress -> {
                            dialogProgress.show(parentFragmentManager, "")
                        }

                        UIState.Initial -> {}
                        is UIState.Success<*> -> {
                            AppUtilities.saveStringInSharedPreferences(
                                requireContext(),
                                AppConstants.SHARED_PREFERENCES_SURVEY_SCORE_CALCULATOR,
                                Date().time.toString()
                            )
                            if (state.result is String)
                                showDialog(state.result)
                        }
                    }
                }
            }
        }
        button.setOnClickListener {
            if (score > 0) {
                val token =
                    AppUtilities.getStringFromSharedPreferences(contextInject, AppConstants.SHARED_PREFERENCES_TOKEN)
                val url =
                    AppUtilities.getStringFromSharedPreferences(contextInject, AppConfig.ENDPOINT_CALCULATOR_RATING)
                viewModel.score(
                    url,
                    token,
                    score,
                    numberEmployer,
                    comment.text.toString(),
                    comment2.text.toString()
                )
            }
        }
    }

    private fun showDialog(message: String) {
        dialogWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept))
        dialogWarning.setOnOptionClick(optionClick)
        dialogProgress.dismiss()
        dialogWarning.show(parentFragmentManager, DialogFragmentWarning.TAG)
    }
}
