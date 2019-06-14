package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.Itinerary;
import com.coppel.rhconecta.dev.views.adapters.ItineraryRecyclerAdapter;

import java.util.List;

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


    public TextView getTipoItinerario() {
        return tipoItinerario;
    }

    public void setTipoItinerario(String tipoItinerario) {
        this.tipoItinerario.setText(tipoItinerario);
        this.tipoItinerario.setVisibility(VISIBLE);
    }

    public RecyclerView getRcvItinerario() {
        return rcvItinerario;
    }

    public void setRcvItinerario(RecyclerView rcvItinerario) {
        this.rcvItinerario = rcvItinerario;
    }

    public TextView getTotalNoches() {
        return totalNoches;
    }

    public void setTotalNoches(String totalNoches) {
        this.totalNoches.setText(totalNoches);
    }

    public void setDataRecyclerView(List<Itinerary> itineraryList){

        rcvItinerario.setHasFixedSize(true);
        rcvItinerario.setLayoutManager(new LinearLayoutManager(getContext()));
        ItineraryRecyclerAdapter itineraryRecyclerAdapter = new ItineraryRecyclerAdapter(itineraryList);
        rcvItinerario.setAdapter(itineraryRecyclerAdapter);

    }
}
