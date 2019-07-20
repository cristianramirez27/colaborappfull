package com.coppel.rhconecta.dev.views.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayRequestRecyclerAdapter extends RecyclerView.Adapter<HolidayRequestRecyclerAdapter.ViewHolder> {

    private List<HolidayPeriod> dataItems;
   // private boolean isColaborator;
//
    private OnRequestSelectedClickListener OnRequestSelectedClickListener;
   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public HolidayRequestRecyclerAdapter(List<HolidayPeriod> holidays) {
       // this.isColaborator = isColaborator;
        this.dataItems = holidays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_solicitud_vacaciones, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);

        viewHolder.fechaInicio.setText(dataItems.get(i).getFec_ini());
        viewHolder.fechaFin.setText(dataItems.get(i).getFec_fin());
        viewHolder.diasVacaciones.setText(String.format("%s %s",String.valueOf(dataItems.get(i).getNum_dias()),"días"));
        viewHolder.layoutEstatusContainer.setVisibility( dataItems.get(i).getNom_estatus()!= null && !dataItems.get(i).getNom_estatus().isEmpty() ?  View.VISIBLE : View.GONE);

       /* viewHolder.status.setTextColor(Color.parseColor(dataItems.get(i).getDes_colorletra()));
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(dataItems.get(i).getDes_color()));
        gd.setCornerRadius(20);
        viewHolder.status.setBackgroundDrawable(gd);*/
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRequestSelectedClickListener.onRequestSelectedClick(dataItems.get(i));
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.checkboxElement)
        CheckBox checkboxElement;
        @BindView(R.id.layoutEstatus)
        LinearLayout layoutEstatus;
        @BindView(R.id.layoutEstatusContainer)
        RelativeLayout layoutEstatusContainer;
        @BindView(R.id.estatus)
        TextView estatus;
        @BindView(R.id.ic_solicitud)
        ImageView ic_solicitud;
        @BindView(R.id.fechaInicio)
        TextView fechaInicio;
        @BindView(R.id.fechaFin)
        TextView fechaFin;
        @BindView(R.id.diasVacaciones)
        TextView diasVacaciones;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRequestSelectedClickListener {
        void onRequestSelectedClick(HolidayPeriod holidayPeriod);
    }

    public void setOnRequestSelectedClickListener(HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener onRequestSelectedClickListener) {
        OnRequestSelectedClickListener = onRequestSelectedClickListener;
    }
}
