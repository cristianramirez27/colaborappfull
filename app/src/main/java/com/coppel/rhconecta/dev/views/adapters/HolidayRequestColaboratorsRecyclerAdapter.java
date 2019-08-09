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
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.views.customviews.RequestHolidaysColaboratorView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class HolidayRequestColaboratorsRecyclerAdapter extends RecyclerView.Adapter<HolidayRequestColaboratorsRecyclerAdapter.ViewHolder> {

    private List<HolidayPeriod> dataItems;
    private IScheduleOptions IScheduleOptions;
    private boolean showMarker;

    private OnRequestSelectedClickListener OnRequestSelectedClickListener;
   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public HolidayRequestColaboratorsRecyclerAdapter(List<HolidayPeriod> holidays, IScheduleOptions IScheduleOptions) {
       // this.isColaborator = isColaborator;
        this.dataItems = holidays;
        this.IScheduleOptions = IScheduleOptions;
    }

    public HolidayRequestColaboratorsRecyclerAdapter(List<HolidayPeriod> dataItems, boolean showMarker) {
        this.dataItems = dataItems;
        this.showMarker = showMarker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_solicitud_colaborador_vacaciones, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        dataItems.get(i).setShowMarker(showMarker);
        viewHolder.requestColaborator.setDetailData(dataItems.get(i),false);

        viewHolder.requestColaborator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(OnRequestSelectedClickListener != null ){
                    OnRequestSelectedClickListener.onRequestSelectedClick(dataItems.get(i));
                }
            }
        });
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

        @BindView(R.id.container)
        RelativeLayout container;
        @BindView(R.id.requestColaborator)
        RequestHolidaysColaboratorView requestColaborator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRequestSelectedClickListener {
        void onRequestSelectedClick(HolidayPeriod holidayPeriod);
    }

    public void setOnRequestSelectedClickListener(HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener onRequestSelectedClickListener) {
        OnRequestSelectedClickListener = onRequestSelectedClickListener;
    }
}
