package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.Itinerary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItineraryRecyclerAdapter extends RecyclerView.Adapter<ItineraryRecyclerAdapter.ViewHolder> {

    private List<Itinerary> dataItems;

   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public ItineraryRecyclerAdapter(List<Itinerary> itineraryList) {
        this.dataItems = itineraryList;
    }

    @NonNull
    @Override
    public ItineraryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_itinerario_viaje, viewGroup, false);
        return new ItineraryRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.itemView.setHasTransientState(true);

    //dataItems.get(i).getControl()
        viewHolder.ciudadOrigen.setText(dataItems.get(i).getDes_ciudadO());
        viewHolder.fechaSalida.setText(dataItems.get(i).getFec_salida());
        viewHolder.ciudadDestino.setText(dataItems.get(i).getDes_ciudadD());
        viewHolder.fechaLlegada.setText(dataItems.get(i).getFec_cambio());
        viewHolder.numNoches.setText(String.valueOf(dataItems.get(i).getNum_noches()));
        viewHolder.imgIcon.setImageResource(getIcon(dataItems.get(i).getClv_transporte()));

    }


    private int getIcon(int clv_transporte){
        switch (clv_transporte){

            case 0:

                return R.drawable.ic_autobus;

            case 1:

                return R.drawable.ic_icn_avi_n;

        }

        return 0;

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<Itinerary> getDataItems() {
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

        @BindView(R.id.ciudadOrigen)
        TextView ciudadOrigen;
        @BindView(R.id.fechaSalida)
        TextView fechaSalida;
        @BindView(R.id.ciudadDestino)
        TextView ciudadDestino;
        @BindView(R.id.fechaLlegada)
        TextView fechaLlegada;
        @BindView(R.id.numNoches)
        TextView numNoches;
        @BindView(R.id.imgIcon)
        ImageView imgIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
