package com.coppel.rhconecta.dev.views.dialogs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentWarning extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentWarning.class.getSimpleName();
    private static final int SINGLE_OPTION = 0;
    private static final int TWO_OPTIONS = 1;
    private int selectedType;
    private String title;
    private String message;
    private String leftOptionText;
    private String rightOptionText;
    private OnOptionClick onOptionClick;
    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.txvMessage)
    TextView txvMessage;
    @BindView(R.id.btnLeftOption)
    Button btnLeftOption;
    @BindView(R.id.btnRightOption)
    Button btnRightOption;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_warning, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        initView(selectedType);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }
        return view;
    }

    private void initView(int type) {
        txvTitle.setText(title);
        txvMessage.setText(message);
        switch (type) {
            case SINGLE_OPTION:
                btnLeftOption.setVisibility(View.GONE);
                btnRightOption.setText(rightOptionText);
                btnRightOption.setOnClickListener(this);
                break;
            case TWO_OPTIONS:
                btnLeftOption.setText(leftOptionText);
                btnRightOption.setText(rightOptionText);
                btnLeftOption.setOnClickListener(this);
                btnRightOption.setOnClickListener(this);
                break;
        }
    }

    public void setSinlgeOptionData(String title, String message, String rightOptionText) {
        selectedType = SINGLE_OPTION;
        this.title = title;
        this.message = message;
        this.rightOptionText = rightOptionText;
    }

    public void setTwoOptionsData(String title, String message, String leftOptionText, String rightOptionText) {
        selectedType = TWO_OPTIONS;
        this.title = title;
        this.message = message;
        this.leftOptionText = leftOptionText;
        this.rightOptionText = rightOptionText;
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (onOptionClick != null) {
            switch (view.getId()) {
                case R.id.btnLeftOption:
                    onOptionClick.onLeftOptionClick();
                    break;
                case R.id.btnRightOption:
                    onOptionClick.onRightOptionClick();
                    break;
            }
        }
    }

    public interface OnOptionClick {

        void onLeftOptionClick();

        void onRightOptionClick();
    }

    public void setOnOptionClick(OnOptionClick onOptionClick) {
        this.onOptionClick = onOptionClick;
    }

    public void setTitle(String title) {
        txvTitle.setText(title);
    }

    public void setMessage(String message) {
        txvMessage.setText(message);
    }

    public void setLeftOptionText(String leftOptionText) {
        btnLeftOption.setText(leftOptionText);
    }

    public void setRightOptionText(String rightOptionText) {
        btnRightOption.setText(rightOptionText);
    }
}
