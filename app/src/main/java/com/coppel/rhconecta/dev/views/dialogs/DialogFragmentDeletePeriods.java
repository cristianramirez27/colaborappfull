package com.coppel.rhconecta.dev.views.dialogs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DialogFragmentDeletePeriods extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentDeletePeriods.class.getSimpleName();
    private OnOptionClick onOptionClick;
    @BindView(R.id.imgvAction)
    ImageView imgvAction;
    @BindView(R.id.txvHeader)
    TextView txvHeader;
    @BindView(R.id.txvSubtitle)
    TextView txvSubtitle;
    @BindView(R.id.btnActionAccept)
    Button btnActionAccept;
    @BindView(R.id.btnActionCancel)
    Button btnActionCancel;


    private String title = "";
    private String msg = "";
    private int iResIcon;

    private int visibilityCancel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_delete_period, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        initView();
        return view;
    }

    public void initView() {
        btnActionCancel.setOnClickListener(this);
        btnActionAccept.setOnClickListener(this);
        btnActionCancel.setVisibility(visibilityCancel);
        txvHeader.setText(title);
        txvSubtitle.setText(msg);

        if(iResIcon > 0){
            imgvAction.setImageResource(iResIcon);
            imgvAction.setVisibility(View.VISIBLE);
        }else {
            imgvAction.setVisibility(GONE);
        }
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
                case R.id.btnActionCancel:
                    onOptionClick.onCancel();
                    break;


            }
        }
    }

    public interface OnOptionClick {

        void onAccept();
        void onCancel();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOnOptionClick(OnOptionClick onOptionClick) {
        this.onOptionClick = onOptionClick;
    }

    public void setTxvTitle(String text) {
        this.title = text;
    }

    public void setiResIcon(int iResIcon) {
        this.iResIcon = iResIcon;
    }

    public void setVisibleCancelButton(int visibility){
        visibilityCancel = visibility;
    }
}
