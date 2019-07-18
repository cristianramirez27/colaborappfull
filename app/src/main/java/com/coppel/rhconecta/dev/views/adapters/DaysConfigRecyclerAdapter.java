package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class DaysConfigRecyclerAdapter extends RecyclerView.Adapter<DaysConfigRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;

    private List<DaySelectedHoliday> daySelectedHolidays;
    public DaysConfigRecyclerAdapter(Context context, List<DaySelectedHoliday> daySelectedHolidays) {
        this.daySelectedHolidays = daySelectedHolidays;
        this.context = context;
    }

    @NonNull
    @Override
    public DaysConfigRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_day_config, viewGroup, false);
        return new DaysConfigRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final DaySelectedHoliday currentItem = daySelectedHolidays.get(position);

        viewHolder.day.setText(String.valueOf(currentItem.getDay()));
        viewHolder.halfDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    currentItem.setHalfDay(true);
                    viewHolder.halfDay.setBackgroundResource(R.drawable.backgroud_circle_stroke_blue);
                    viewHolder.labelHalfDay.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryBlueCoppel));
                }else {
                    currentItem.setHalfDay(false);
                    viewHolder.halfDay.setBackgroundResource(R.drawable.backgroud_circle_blue);
                    viewHolder.labelHalfDay.setTextColor(getContext().getResources().getColor(R.color.colorBackgroundCoppelBlanco));
                }
            }
        });
    }



    public List<DaySelectedHoliday> getDaySelectedHolidays(){
        return daySelectedHolidays;
    }

    @Override
    public int getItemCount() {
        return daySelectedHolidays.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.halfDay)
        Switch halfDay;
        @BindView(R.id.labelHalfDay)
        TextView labelHalfDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
