package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.ImageLoaderUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardColaboratorControl extends LinearLayout {


    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.layoutColaboratorInfo)
    RelativeLayout layoutColaboratorInfo;
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

    @BindView(R.id.tituloControl)
    TextView tituloControl;


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

    public void setTituloControl(String tituloControl) {
        this.tituloControl.setText(tituloControl);
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

    public void setStatus(int type,int clv_status, String colorHexa, String status) {
        this.status.setText(status);
        Integer[] data = new  Integer[3];

        if(type == 1){

            switch (clv_status){

                case 0:
                    data[0] =R.string.status_save;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_grey;
                break;
                case 1:
                case 2:
                    data[0] =R.string.status_pending;
                    data[1] =R.color.colorBackgroundCoppelNegro;
                    data[2] =R.drawable.backgroud_rounder_yellow;
                        break;

                case 3:
                    data[0] =R.string.status_canceled;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_red;
                        break;

                case 5:
                    data[0] =R.string.status_refused;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_red;
                        break;
            }

        }else if(type == 2){

            switch (clv_status){

                case 0:
                    data[0] =R.string.status_active;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_green;
                   break;

                case 1:
                case 3:
                case 7:
                    data[0] =R.string.status_pending;
                    data[1] =R.color.colorBackgroundCoppelNegro;
                    data[2] =R.drawable.backgroud_rounder_yellow;
                   break;

                case 2:
                case 4:
                case 8:
                    data[0] =R.string.status_refused;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_red;
                   break;
            }

        }else if(type == 3){
            switch (clv_status){

                case 99:
                    data[0] =R.string.status_payment;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_blue;
                   break;

                case -1:
                    data[0] =R.string.status_no_payment;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_blac;
                   break;

                case 98:
                    data[0] =R.string.status_dont_deposite;
                    data[1] =R.color.colorBackgroundCoppelBlanco;
                    data[2] =R.drawable.backgroud_rounder_orange;
                   break;

            }
        }

        this.status.setText(getContext().getString(data[0]));
        this.status.setTextColor(getContext().getResources().getColor(data[1]));
        this.layoutStatus.setBackgroundResource(data[2]);

    }

    public void setLayoutStatus(int iResource) {
        this.layoutStatus.setBackgroundResource(iResource);
    }

    public void setVisibleColaboratorInfo(int visibility){
        layoutColaboratorInfo.setVisibility(visibility);
    }
}
