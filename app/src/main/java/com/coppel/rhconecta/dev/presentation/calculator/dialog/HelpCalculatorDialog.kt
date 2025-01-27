package com.coppel.rhconecta.dev.presentation.calculator.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.CalculatorFragmentCommunication
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.CompleteCalculationFragment
import java.util.*
import kotlin.properties.Delegates

class HelpCalculatorDialog : DialogFragment() {
    private var title by Delegates.notNull<Int>()
    private var subTitle by Delegates.notNull<Int>()
    private var image: Int? = null
    private var foot: Int? = null
    private lateinit var calculatorFragmentCommunication: CalculatorFragmentCommunication
    private val dialogHelp by lazy {
        MoreInformationDialog()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalculatorFragmentCommunication)
            calculatorFragmentCommunication = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AlertHelpCalculatorDialogCopper)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_calculator_help, container, false)
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleTextView = view.findViewById<TextView>(R.id.textView11)
        val subTitleTextView = view.findViewById<TextView>(R.id.textView46)
        val imageView = view.findViewById<ImageView>(R.id.imageView9)
        val footTextView = view.findViewById<TextView>(R.id.textView47)
        val linkTextView = view.findViewById<TextView>(R.id.link)
        linkTextView.setLinkTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlueLight))
        val buttonInfo = view.findViewById<Button>(R.id.button9)
        titleTextView.setText(title)
        subTitleTextView.setText(subTitle)
        val button = view.findViewById<ImageView>(R.id.imageView10)
        var enableComplete = false
        val profile = calculatorFragmentCommunication.getProfileInformation()
        var name: String = profile.nombreColaborador.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0]
        name = name.substring(0, 1).uppercase(Locale.getDefault()) + name.substring(1).lowercase(Locale.getDefault())
        image?.let {
            imageView.setImageResource(it)
            imageView.adjustViewBounds = true
            buttonInfo.isEnabled = false
            linkTextView.isEnabled = false
            enableComplete = false
        } ?: run {
            enableComplete = true
            imageView.visibility = GONE
            footTextView.visibility = GONE
            button.visibility = GONE
            subTitleTextView.textAlignment = 2
            buttonInfo.visibility = VISIBLE
            linkTextView.visibility = VISIBLE
        }
        foot?.let {
            footTextView.visibility = VISIBLE
            footTextView.movementMethod = LinkMovementMethod.getInstance()
            footTextView.setLinkTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlueLight))
            footTextView.text = generateHtml(
                resources.getString(
                    R.string.text_information_dialog_calculator_2,
                    name,
                    profile.curp,
                    profile.nss
                )
            )
        } ?: run { footTextView.visibility = GONE }

        button.setOnClickListener { dismiss() }
        linkTextView.setOnClickListener { dialogHelp.show(parentFragmentManager, "more") }
        buttonInfo.setOnClickListener {
            if (enableComplete)
                parentFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.content, CompleteCalculationFragment()).commit()
            dismiss()
        }
    }

    fun setData(title: Int, subTitle: Int, image: Int? = null, foot: Int? = null) {
        this.title = title
        this.subTitle = subTitle
        this.image = image
        this.foot = foot
    }

    private fun generateHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source);
        }
    }
}
