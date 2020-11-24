package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTextPassword extends ConstraintLayout implements View.OnClickListener {

    private boolean isVisible;
    private OnEditorActionListener onEditorActionListener;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.imgvVisibility)
    ImageView imgvVisibility;
    @BindView(R.id.txvWarning)
    TextView txvWarning;

    public EditTextPassword(Context context) {
        super(context);
        initViews();
    }

    public EditTextPassword(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.edit_text_password, this);
        ButterKnife.bind(this);
        imgvVisibility.setOnClickListener(this);
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    imgvVisibility.setVisibility(VISIBLE);
                } else {
                    imgvVisibility.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(onEditorActionListener != null) {
                    onEditorActionListener.onEditorAction(textView, i, keyEvent);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvVisibility:
                togglePassword(isVisible);
                break;
        }
    }

    public String getText() {
        return edtPassword.getText().toString();
    }

    public void setText(String text) {
        edtPassword.setText(text);
    }

    private void togglePassword(boolean isVisible) {
        if (isVisible) {
            hidePassword();
        } else {
            showPassword();
        }
    }

    public void hidePassword() {
        imgvVisibility.setImageResource(R.drawable.ic_visibility_blue);
        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtPassword.setSelection(edtPassword.length());
        edtPassword.setTypeface(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
        isVisible = false;
    }

    public void showPassword() {
        imgvVisibility.setImageResource(R.drawable.ic_visibility_off_blue);
        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        edtPassword.setSelection(edtPassword.length());
        edtPassword.setTypeface(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
        isVisible = true;
    }

    public void setWarningMessage(String message) {
        txvWarning.setText(message);
    }

    public void clearWarning() {
        txvWarning.setText("");
    }

    public interface OnEditorActionListener {
        void onEditorAction(TextView textView, int i, KeyEvent keyEvent);
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        this.onEditorActionListener = onEditorActionListener;
    }
}
