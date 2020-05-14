package com.coppel.rhconecta.dev.presentation.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import java.util.Objects;

/**
 *
 *
 */
public class SingleActionDialog extends Dialog {

    /**
     *
     *
     */
    public SingleActionDialog(
            Context context,
            String title,
            String content,
            String actionSrc,
            View.OnClickListener actionClickListener
    ) {
        super(context);
        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_single_action);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
        Button btnAction = (Button) findViewById(R.id.btnAction);
        tvTitle.setText(title);
        tvContent.setText(content);
        btnAction.setText(actionSrc);
        btnAction.setOnClickListener(
                actionClickListener == null? v -> hide() : v -> {
                    actionClickListener.onClick(v);
                    hide();
                }
        );
    }



}
