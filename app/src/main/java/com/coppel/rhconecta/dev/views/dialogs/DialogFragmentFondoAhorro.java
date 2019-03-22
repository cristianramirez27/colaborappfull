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
import com.coppel.rhconecta.dev.business.models.GuardarRetiroResponse;

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
        txvFolio.setText(String.format("Folio: %s",folio));
        txvFecha.setText(String.format("Fecha: %s",date));
        txvHora.setText(String.format("Hora: %s",time));


        return view;
    }

    public void initView(String title,String folio,String date,String time) {

        this.title = title;
        this.folio = folio;
        this.date = date;
        this.time = time;
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
