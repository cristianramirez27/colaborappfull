package com.coppel.rhconecta.dev.presentation.room.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.coppel.rhconecta.dev.R

class DialogInputRoom : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AlertDialogCopper)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_input_room, container, false)
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViews(view)
    }

    private fun iniViews(view: View){
        val edithText = view.findViewById<EditText>(R.id.edith_code_room)
        val button = view.findViewById<Button>(R.id.button_room_ok)
        button.setOnClickListener {
           Log.e("DialogInputRoom", edithText.text.toString())
            dismiss()
        }
    }
}
