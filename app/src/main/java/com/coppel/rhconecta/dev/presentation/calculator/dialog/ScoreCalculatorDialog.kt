package com.coppel.rhconecta.dev.presentation.calculator.dialog

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import java.util.*

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
                if ( code == 9725 || code == 9726 || type == Character.SURROGATE.toInt() || type == Character.NON_SPACING_MARK.toInt() || type == Character.OTHER_SYMBOL.toInt()|| type == Character.OTHER_PUNCTUATION.toInt()) {
                    return ""
                }
            }
            return null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            if (score> 0){
                button.alpha = 1f
                button.isEnabled = true
            }else {
                button.alpha = 0.5f
                button.isEnabled = false
            }
        }
        exit.setOnClickListener {
            dismiss()
            activity?.finish()
        }
        button.setOnClickListener {
            if (score > 0) {
                AppUtilities.saveStringInSharedPreferences(
                    requireContext(),
                    AppConstants.SHARED_PREFERENCES_SURVEY_SCORE_CALCULATOR,
                    Date().time.toString()
                )
                dismiss()
                activity?.finish()
            }
        }
    }
}
