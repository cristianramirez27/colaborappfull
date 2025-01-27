package com.coppel.rhconecta.dev.views.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.views.customviews.RequestHolidaysColaboratorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayRequestColaboratorsRecyclerAdapter extends RecyclerView.Adapter<HolidayRequestColaboratorsRecyclerAdapter.ViewHolder> {

    private List<HolidayPeriod> dataItems;
    private IScheduleOptions IScheduleOptions;
    private boolean showMarker;
    private boolean enableThemeHoliday;

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

    public void setEnableThemeHoliday(boolean enableThemeHoliday) {
        this.enableThemeHoliday = enableThemeHoliday;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_solicitud_colaborador_vacaciones, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        if(enableThemeHoliday){
            viewHolder.requestColaborator.setThemeHoliday();
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        HolidayPeriod data = dataItems.get(i);
        data.setShowMarker(showMarker);
        viewHolder.requestColaborator.setDetailData(data,false);
        if (data.getIdu_marca() == 1 && OnRequestSelectedClickListener != null)
            OnRequestSelectedClickListener.showLabelSplice(true);

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
        void showLabelSplice(boolean enable);
    }

    public void setOnRequestSelectedClickListener(HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener onRequestSelectedClickListener) {
        OnRequestSelectedClickListener = onRequestSelectedClickListener;
    }
}
