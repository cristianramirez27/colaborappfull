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
import com.coppel.rhconecta.dev.business.models.ColaboratorControlsMonthResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpensesTravelMonthsRequestRecyclerAdapter extends RecyclerView.Adapter<ExpensesTravelMonthsRequestRecyclerAdapter.ViewHolder> {

    private List<ColaboratorControlsMonthResponse.ControlMonth> dataItems;
    private OnControlMonthClickListener OnControlMonthClickListener;
    private boolean isColaborator;
   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public ExpensesTravelMonthsRequestRecyclerAdapter(List<ColaboratorControlsMonthResponse.ControlMonth> requestComplementsColaboratorList,boolean isColaborator) {
        this.dataItems = requestComplementsColaboratorList;
        this.isColaborator = isColaborator;
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
        viewHolder.numSolicitud.setText(dataItems.get(i).getControl());

        viewHolder.nameEmployer.setVisibility(isColaborator ? View.GONE : View.VISIBLE);
        viewHolder.nameEmployer.setText(dataItems.get(i).getNomviajero() != null ? dataItems.get(i).getNomviajero()  : "");



        viewHolder.itinerario.setText(dataItems.get(i).getItinerario());
        viewHolder.fechaInicio.setText(dataItems.get(i).getFec_salida());
        viewHolder.fechaFin.setText(dataItems.get(i).getFec_regreso());

        /*Integer[] statusData = getStatusConfiguration(dataItems.get(i).getClv_estatus());
        viewHolder.status.setText(CoppelApp.getContext().getString(statusData[0]));
        viewHolder.status.setTextColor(CoppelApp.getContext().getResources().getColor(statusData[1]));
        viewHolder.status.setBackgroundResource(statusData[2]);*/

        viewHolder.status.setText(dataItems.get(i).getEstatus());
        if(dataItems.get(i).getEstatus().length() >= 20)
            viewHolder.status.setTextSize(7);
        viewHolder.status.setTextColor(Color.parseColor(dataItems.get(i).getDes_colorletra()));
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(dataItems.get(i).getDes_color()));
        gd.setCornerRadius(20);
        viewHolder.status.setBackgroundDrawable(gd);



        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnControlMonthClickListener.onControlMonthClick(dataItems.get(i));
            }
        });


    }


    private Integer[] getStatusConfiguration(int clv_status){

        Integer[] data = new  Integer[3];

        switch (clv_status){

            case 99:
                data[0] =R.string.status_payment;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_blue;
                return data;

            case -1:
                data[0] =R.string.status_no_payment;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_blac;
                return data;

            case 98:
                data[0] =R.string.status_dont_deposite;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_orange;
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

    public interface OnControlMonthClickListener {
        void onControlMonthClick(ColaboratorControlsMonthResponse.ControlMonth controlMonth);
    }

    public void setOnControlMonthClickListener(ExpensesTravelMonthsRequestRecyclerAdapter.OnControlMonthClickListener onControlMonthClickListener) {
        OnControlMonthClickListener = onControlMonthClickListener;
    }
}
