package com.coppel.rhconecta.dev.views.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ConsultaAhorroAdicionalResponse;
import com.coppel.rhconecta.dev.business.models.GuardarRetiroResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentAhorroAdicional extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentAhorroAdicional.class.getSimpleName();
    private OnOptionClick onOptionClick;
    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.txvSubtitle)
    TextView txvSubtitle;
    @BindView(R.id.txvMessage)
    TextView txvMessage;
    @BindView(R.id.txtAmount)
    TextView txtAmount;
    @BindView(R.id.btnActionAccept)
    Button btnActionAccept;

    private String title;
    private String subtitle;
    private String msg;
    private String amoun;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_ahorro_adicional, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        initView();
        return view;
    }

    public void initView() {
        btnActionAccept.setOnClickListener(this);
        txvTitle.setText(title);
        txvSubtitle.setText(subtitle);
        txvMessage.setText(msg);
        txtAmount.setText(amoun);
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
                case R.id.btnActionAccept:
                    onOptionClick.onAccept();
                    break;
            }
        }
    }

    public interface OnOptionClick {

        void onAccept();

    }

    public void setOnOptionClick(OnOptionClick onOptionClick) {
        this.onOptionClick = onOptionClick;
    }

    public void setTxvTitle(String text) {
        this.title = text;
    }

    public void setTxvSubtitle(String text) {
        this.subtitle = text;
    }

    public void setTxvMessage(String text) {
        this.msg  = text;
    }

    public void setTxtAmount(String text) {
        this.amoun  = text;
    }
}
