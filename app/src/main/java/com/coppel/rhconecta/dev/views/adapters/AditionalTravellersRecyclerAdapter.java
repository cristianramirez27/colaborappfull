package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.AditionalTraveller;
import com.coppel.rhconecta.dev.business.models.Itinerary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AditionalTravellersRecyclerAdapter extends RecyclerView.Adapter<AditionalTravellersRecyclerAdapter.ViewHolder> {

    private List<AditionalTraveller> dataItems;

    public AditionalTravellersRecyclerAdapter(List<AditionalTraveller> itineraryList) {
        this.dataItems = itineraryList;
    }

    @NonNull
    @Override
    public AditionalTravellersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_viajero_adicional, viewGroup, false);
        return new AditionalTravellersRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.numViajeros.setText(String.valueOf(dataItems.get(i).getTotal_viajeros()));
        viewHolder.numColaborador.setText(String.valueOf(dataItems.get(i).getNum_colaborador()));
        viewHolder.nombreColaborador.setText(dataItems.get(i).getNom_colaborador());
        viewHolder.centro.setText(dataItems.get(i).getNom_centro());
        viewHolder.empalme.setText(dataItems.get(i).getEmpalme());
        viewHolder.empalme.setTextColor(CoppelApp.getContext().getResources().getColor(R.color.colorPrimaryCoppelAzul));

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<AditionalTraveller> getDataItems() {
        return dataItems;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.numViajeros)
        TextView numViajeros;
        @BindView(R.id.numColaborador)
        TextView numColaborador;
        @BindView(R.id.nombreColaborador)
        TextView nombreColaborador;
        @BindView(R.id.centro)
        TextView centro;
        @BindView(R.id.empalme)
        TextView empalme;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
