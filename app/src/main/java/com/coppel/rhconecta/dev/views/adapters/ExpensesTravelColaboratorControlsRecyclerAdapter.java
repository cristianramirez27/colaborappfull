package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpensesTravelColaboratorControlsRecyclerAdapter extends RecyclerView.Adapter<ExpensesTravelColaboratorControlsRecyclerAdapter.ViewHolder> {

    private List<ColaboratorRequestsListExpensesResponse.ControlColaborator> dataItems;
    private OnControlSelectedClickListener OnControlSelectedClickListener;

   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public ExpensesTravelColaboratorControlsRecyclerAdapter(List<ColaboratorRequestsListExpensesResponse.ControlColaborator> requestComplementsColaboratorList) {
        this.dataItems = requestComplementsColaboratorList;
    }

    @NonNull
    @Override
    public ExpensesTravelColaboratorControlsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_solicitud_viaje, viewGroup, false);
        return new ExpensesTravelColaboratorControlsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.itemView.setHasTransientState(true);


        viewHolder.numSolicitud.setText(dataItems.get(i).getControl());


        viewHolder.itinerario.setText(dataItems.get(i).getItinerario());
        viewHolder.fechaInicio.setText(dataItems.get(i).getFec_salida());
        viewHolder.fechaFin.setText(dataItems.get(i).getFec_regreso());


        Integer[] statusData = getStatusConfiguration(dataItems.get(i).getClv_estatus());
        viewHolder.status.setText(CoppelApp.getContext().getString(statusData[0]));
        viewHolder.status.setTextColor(CoppelApp.getContext().getResources().getColor(statusData[1]));
        viewHolder.status.setBackgroundResource(statusData[2]);

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnControlSelectedClickListener.onControlSelectedClick(dataItems.get(i));
            }
        });
    }


    private Integer[] getStatusConfiguration(int clv_status){

        Integer[] data = new  Integer[3];

        switch (clv_status){

            case 0:
                data[0] =R.string.status_active;
                data[1] =R.color.colorBackgroundCoppelBlanco;
                data[2] =R.drawable.backgroud_rounder_green;
                return data;

            case 1:
            case 3:
            case 7:
                data[0] =R.string.status_pending;
                data[1] =R.color.colorBackgroundCoppelNegro;
                data[2] =R.drawable.backgroud_rounder_yellow;
                return data;

            case 2:
            case 4:
            case 8:
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

    public List<ColaboratorRequestsListExpensesResponse.ControlColaborator> getDataItems() {
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
        @BindView(R.id.cardview)
        CardView cardview;
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

    public interface OnControlSelectedClickListener {
        void onControlSelectedClick(ColaboratorRequestsListExpensesResponse.ControlColaborator ControlColaborator);
    }

    public void setOnControlSelectedClickListener(ExpensesTravelColaboratorControlsRecyclerAdapter.OnControlSelectedClickListener onControlSelectedClickListener) {
        OnControlSelectedClickListener = onControlSelectedClickListener;
    }
}
