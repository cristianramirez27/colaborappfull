package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTextEmail extends ConstraintLayout implements View.OnClickListener {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.imgvClear)
    ImageView imgvClear;
    @BindView(R.id.imgvAccount)
    ImageView imgvAccount;
    @BindView(R.id.txvWarning)
    TextView txvWarning;
    @BindView(R.id.view4)
    View view4;

    public EditTextEmail(Context context) {
        super(context);
        initViews();
    }

    public EditTextEmail(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.edit_text_email, this);
        ButterKnife.bind(this);
        imgvClear.setOnClickListener(this);
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    imgvClear.setVisibility(VISIBLE);
                } else {
                    imgvClear.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvClear:
                edtEmail.setText("");
                break;
        }
    }

    public void setWhiteMode() {
        imgvAccount.setImageResource(R.drawable.ic_account_white);
        edtEmail.setTextColor(getResources().getColor(android.R.color.white));
        edtEmail.setHintTextColor(getResources().getColor(android.R.color.white));
        imgvClear.setImageResource(R.drawable.ic_close_white);
        view4.setBackgroundColor(getResources().getColor(android.R.color.white));
        txvWarning.setTextColor(getResources().getColor(R.color.colorYellowCoppel));
    }

    public String getText() {
        return edtEmail.getText().toString();
    }

    public void setText(String text) {
        edtEmail.setText(text);
    }

    public void setWarningMessage(String message) {
        txvWarning.setText(message);
    }

    public void clearWarning() {
        txvWarning.setText("");
    }
}
