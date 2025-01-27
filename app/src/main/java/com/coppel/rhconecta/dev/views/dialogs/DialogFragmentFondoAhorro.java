package com.coppel.rhconecta.dev.views.dialogs;

import android.os.Bundle;
import android.view.Gravity;
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

public class DialogFragmentFondoAhorro extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentFondoAhorro.class.getSimpleName();
    private OnOptionClick onOptionClick;
    @BindView(R.id.txvAction)
    TextView txvAction;
    @BindView(R.id.txvFolio)
    TextView txvFolio;
    @BindView(R.id.txvFecha)
    TextView txvFecha;
    @BindView(R.id.txvHora)
    TextView txvHora;
    @BindView(R.id.btnActionAccept)
    Button btnActionAccept;

    private String title= "";
    private String folio= "";
    private String date= "";
    private String time= "";
    private boolean enable;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_fondo_ahorro, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        btnActionAccept.setOnClickListener(this);
        txvAction.setText(title);
        txvFolio.setText(folio);
        txvFecha.setText(date);
        txvHora.setText(time);

        txvFecha.setVisibility(date.isEmpty() ? View.GONE : View.VISIBLE);
        if (enable) {
            txvAction.setTextSize(18f);
            txvFecha.setTextSize(14f);
            txvHora.setTextSize(14f);
            txvFecha.setGravity(Gravity.LEFT);
            txvHora.setGravity(Gravity.LEFT);
        }


        return view;
    }

    public void initView(String title,String folio,String date,String time) {
        enable = false;
        this.title = title;
        this.folio = String.format("Folio: %s",folio);
        this.date = String.format("Fecha: %s",date);
        this.time = String.format("Hora: %s",time);
    }

    public void initView(String title, String folioCredito, String folioAdicional) {
        this.title = title;
        this.folio = "";
        date = folioCredito;
        time = folioAdicional;
        enable = true;
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


}
