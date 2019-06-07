package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Itinerario extends RelativeLayout {

    @BindView(R.id.tipoItinerario)
    TextView tipoItinerario;
    @BindView(R.id.rcvItinerario)
    RecyclerView rcvItinerario;
    @BindView(R.id.totalNoches)
    TextView totalNoches;


    public Itinerario(Context context) {
        super(context);
        initViews();
    }

    public Itinerario(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_itinerario_viaje, this);
        ButterKnife.bind(this);
    }




}
