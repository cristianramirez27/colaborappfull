package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.ImageLoaderUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardColaboratorControl extends LinearLayout {

    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.imgColaborador)
    CircleImageView imgColaborador;
    @BindView(R.id.nombreColaborador)
    TextView nombreColaborador;
    @BindView(R.id.numColaborador)
    TextView numColaborador;
    @BindView(R.id.numeroControl)
    TextView numeroControl;
    @BindView(R.id.descripcion)
    TextView descripcion;

    @BindView(R.id.itinerario)
    TextView itinerario;
    @BindView(R.id.fechas)
    TextView fechas;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.layoutStatus)
    LinearLayout layoutStatus;

    public CardColaboratorControl(Context context) {
        super(context);
        initViews();
    }

    public CardColaboratorControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.card_colaborator_control, this);
        ButterKnife.bind(this);
    }


    public void setCardviewBackground(int backgroundResource) {
        this.cardview.setBackgroundResource(backgroundResource);
    }

    public void setImgColaborador(String url) {
        ImageLoaderUtil.loadPictureFromURL(getContext(),url,imgColaborador);
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador.setText(nombreColaborador);
    }

    public void setNumColaborador(String numColaborador) {
        this.numColaborador.setText(numColaborador);
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl.setText(numeroControl);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.setText(descripcion);
    }

    public void setItinerario(String itinerario) {
        this.itinerario.setText(itinerario);
    }

    public void setFechas(String fechas) {
        this.fechas.setText(fechas);
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void setLayoutStatus(int iResource) {
        this.layoutStatus.setBackgroundResource(iResource);
    }
}
