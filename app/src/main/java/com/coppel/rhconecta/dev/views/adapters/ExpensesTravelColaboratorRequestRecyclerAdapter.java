package com.coppel.rhconecta.dev.views.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpensesTravelColaboratorRequestRecyclerAdapter extends RecyclerView.Adapter<ExpensesTravelColaboratorRequestRecyclerAdapter.ViewHolder> {

    private List<ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator> dataItems;
    private boolean isColaborator;

    private OnRequestSelectedClickListener OnRequestSelectedClickListener;
   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public ExpensesTravelColaboratorRequestRecyclerAdapter(List<ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator> requestComplementsColaboratorList,boolean isColaborator) {
        this.isColaborator = isColaborator;
        this.dataItems = requestComplementsColaboratorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_solicitud_viaje, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);

        if(dataItems.get(i).getTipo() == 1){
            viewHolder.numSolicitud.setText(dataItems.get(i).getCLV_CONTROL());
            viewHolder.txtMessage.setText(dataItems.get(i).getDes_solicitud());
        }
        /*else{
            viewHolder.numSolicitud.setText(String.valueOf(dataItems.get(i).getClv_solicitud()));
        }*/

        viewHolder.nameEmployer.setVisibility(isColaborator ? View.GONE : View.VISIBLE);
        viewHolder.nameEmployer.setText(dataItems.get(i).getNomviajero() != null ? dataItems.get(i).getNomviajero()  : "");

        viewHolder.itinerario.setText(dataItems.get(i).getItinerario());
        viewHolder.fechaInicio.setText(dataItems.get(i).getFechasalida());
        viewHolder.fechaFin.setText(dataItems.get(i).getFecharegreso());


        //Integer[] statusData = getStatusConfiguration(dataItems.get(i).getClv_estatus());
        //viewHolder.status.setText(CoppelApp.getContext().getString(statusData[0]));
        //viewHolder.status.setTextColor(CoppelApp.getContext().getResources().getColor(statusData[1]));
        //viewHolder.status.setBackgroundResource(statusData[2]);
       // viewHolder.status.setBackgroundColor(Color.parseColor(dataItems.get(i).getDes_color()));

        if(!isColaborator){
            viewHolder.status.setText(dataItems.get(i).getNom_estatus());
            if(dataItems.get(i).getNom_estatus().length() >= 20)
                viewHolder.status.setTextSize(7);
        }else {
            viewHolder.status.setText(dataItems.get(i).getEstatus());
            if(dataItems.get(i).getEstatus().length() >= 20)
                viewHolder.status.setTextSize(7);
        }



        viewHolder.status.setTextColor(Color.parseColor(dataItems.get(i).getDes_colorletra()));
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(dataItems.get(i).getDes_color()));
        gd.setCornerRadius(20);
        viewHolder.status.setBackgroundDrawable(gd);

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRequestSelectedClickListener.onRequestSelectedClick(dataItems.get(i));
            }
        });


    }


    private Integer[] getStatusConfiguration(int clv_status){

        Integer[] data = new  Integer[3];

        switch (clv_status){

            case 0:
                data[0] =R.string.status_save;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_grey;
                return data;

            case 1:
            case 2:
                data[0] =R.string.status_pending;
                data[1] =R.color.colorBackgroundCoppelNegro;
                data[2] =R.drawable.backgroud_rounder_yellow;
                return data;

            case 3:
                data[0] =R.string.status_canceled;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_red;
                return data;

            case 5:
                data[0] =R.string.status_refused;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_red;
                return data;
        }

        return data;

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.nameEmployer)
        TextView nameEmployer;
        @BindView(R.id.numSolicitud)
        TextView numSolicitud;
        @BindView(R.id.txtMessage)
        TextView txtMessage;
        @BindView(R.id.itinerario)
        TextView itinerario;
        @BindView(R.id.fechaInicio)
        TextView fechaInicio;
        @BindView(R.id.fechaFin)
        TextView fechaFin;
        @BindView(R.id.status)
        TextView status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRequestSelectedClickListener {
        void onRequestSelectedClick(ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator RequestComplementsColaborator);
    }

    public void setOnRequestSelectedClickListener(ExpensesTravelColaboratorRequestRecyclerAdapter.OnRequestSelectedClickListener onRequestSelectedClickListener) {
        OnRequestSelectedClickListener = onRequestSelectedClickListener;
    }
}
