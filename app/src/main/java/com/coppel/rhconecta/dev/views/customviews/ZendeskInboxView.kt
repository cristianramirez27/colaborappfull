package com.coppel.rhconecta.dev.views.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.coppel.rhconecta.dev.R

class ZendeskInboxView : RelativeLayout {
    private lateinit var imgInbox: ImageView
    private lateinit var tvNotification: TextView

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val li = getContext().getSystemService(infService) as LayoutInflater
        li.inflate(R.layout.zendesk_inbox, this, true)
        imgInbox = findViewById(R.id.imgChat)
        tvNotification = findViewById(R.id.tvNotification)
    }

    fun setCountMessages(numberMessages: Int) {
        if (numberMessages > 0) {
            tvNotification.text = String.format("%d",numberMessages)
            tvNotification.visibility = VISIBLE
        } else {
            tvNotification.text = ""
            tvNotification.visibility = INVISIBLE
        }
    }

    fun setActive(numberMessages: Int) {
        imgInbox.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_zendesk_chat_active
            )
        )
    }

    fun setDisable() {
        imgInbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_zendesk_chat))
    }
}