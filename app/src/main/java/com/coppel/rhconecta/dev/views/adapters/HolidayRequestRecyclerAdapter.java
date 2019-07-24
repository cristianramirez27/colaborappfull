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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayRequestRecyclerAdapter extends RecyclerView.Adapter<HolidayRequestRecyclerAdapter.ViewHolder> {

    private List<HolidayPeriod> dataItems;
    private IScheduleOptions IScheduleOptions;
    private boolean isSchedule;
//
    private OnRequestSelectedClickListener OnRequestSelectedClickListener;
   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public HolidayRequestRecyclerAdapter(List<HolidayPeriod> holidays,IScheduleOptions IScheduleOptions,boolean isSchedule) {
       // this.isColaborator = isColaborator;
        this.dataItems = holidays;
        this.isSchedule = isSchedule;
        this.IScheduleOptions = IScheduleOptions;
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
        viewHolder.diasVacaciones.setText(String.format("%s",String.valueOf(dataItems.get(i).getNum_dias())));
        viewHolder.layoutEstatusContainer.setVisibility( dataItems.get(i).getNom_estatus()!= null && !dataItems.get(i).getNom_estatus().isEmpty() ?  View.VISIBLE : View.GONE);

        viewHolder.checkboxElement.setChecked(dataItems.get(i).isSelected());

        viewHolder.checkboxElement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    dataItems.get(i).setSelected(true);
                    IScheduleOptions.showEliminatedOption(true,isSchedule ? "Eliminar" :"Cancelar Vacaciones");
                }else {
                    dataItems.get(i).setSelected(false);
                    hideEliminateOptionIfIsNecessary();
                }

            }
        });

        if(!isSchedule){
            viewHolder.estatus.setText(dataItems.get(i).getNom_estatus());
            //viewHolder.estatus.setTextColor(Color.parseColor(dataItems.get(i).getDes_colorletra()));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor(dataItems.get(i).getColor()));
            gd.setCornerRadius(20);
            viewHolder.estatus.setBackgroundDrawable(gd);
        }

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRequestSelectedClickListener.onRequestSelectedClick(dataItems.get(i));
            }
        });


    }


    private void hideEliminateOptionIfIsNecessary(){
        for( HolidayPeriod period : dataItems){
            if(period.isSelected()){
                IScheduleOptions.showEliminatedOption(true ,isSchedule ? "Eliminar" :"Cancelar Vacaciones");
                return;
            }
        }

        IScheduleOptions.showEliminatedOption(false,"");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<HolidayPeriod> getDataItemsSelected(){
        List<HolidayPeriod> periodsSelected = new ArrayList<>();
        for (HolidayPeriod period : dataItems){
            if(period.isSelected()){
                periodsSelected.add(period);
            }
        }

        return periodsSelected;

    }

    public List<HolidayPeriod> getAllItems(){
        return dataItems;

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
