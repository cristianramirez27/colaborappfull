package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.AditionalTraveller;
import com.coppel.rhconecta.dev.views.adapters.AditionalTravellersRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.ItineraryRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViajerosAdicionales extends RelativeLayout {


    @BindView(R.id.rcvViajeros)
    RecyclerView rcvViajeros;



    public ViajerosAdicionales(Context context) {
        super(context);
        initViews();
    }

    public ViajerosAdicionales(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_viajeros_adicionales, this);
        ButterKnife.bind(this);
    }


    public void setDataRecyclerView(List<AditionalTraveller> travellerList) {
        rcvViajeros.setHasFixedSize(true);
        rcvViajeros.setLayoutManager(new LinearLayoutManager(getContext()));
        AditionalTravellersRecyclerAdapter aditionalTravellersRecyclerAdapter = new AditionalTravellersRecyclerAdapter(travellerList);
        rcvViajeros.setAdapter(aditionalTravellersRecyclerAdapter);
    }
}
