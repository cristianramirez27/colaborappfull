package com.wdullaer.datetimepickerholiday.date;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.wdullaer.datetimepickerholiday.R;

import java.util.List;


public class DaysConfigRecyclerAdapter extends RecyclerView.Adapter<DaysConfigRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;
    private TextView totalDaysTxt;

    private String[] months = new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto",
    "Septiembre","Octubre","Noviembre","Diciembre"};

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
        viewHolder.monthLabel.setText(months[currentItem.getMonth()]);
        if(currentItem.isHalfDay()){
            viewHolder.halfDay.setChecked(true);
            viewHolder.day.setBackgroundResource(R.drawable.backgroud_circle_stroke_blue);
            viewHolder.day.setTextColor(context.getResources().getColor(R.color.main_color_blue));
            viewHolder.labelHalfDay.setTextColor(context.getResources().getColor(R.color.main_color_blue));
        }else {
            viewHolder.halfDay.setChecked(false);
            viewHolder.day.setBackgroundResource(R.drawable.backgroud_circle_blue);
            viewHolder.day.setTextColor(context.getResources().getColor(R.color.mdtp_white));
            viewHolder.labelHalfDay.setTextColor(context.getResources().getColor(R.color.main_text_disable));

        }


        viewHolder.halfDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    currentItem.setHalfDay(true);
                    viewHolder.day.setBackgroundResource(R.drawable.backgroud_circle_stroke_blue);
                    viewHolder.day.setTextColor(context.getResources().getColor(R.color.main_color_blue));
                    viewHolder.labelHalfDay.setTextColor(context.getResources().getColor(R.color.main_color_blue));
                }else {
                    currentItem.setHalfDay(false);
                    viewHolder.day.setBackgroundResource(R.drawable.backgroud_circle_blue);
                    viewHolder.day.setTextColor(context.getResources().getColor(R.color.mdtp_white));
                    viewHolder.labelHalfDay.setTextColor(context.getResources().getColor(R.color.main_text_disable));
                }

                recalculateDays();
            }
        });
    }

    public void setTotalDaysTxt(TextView totalDaysTxt) {
        this.totalDaysTxt = totalDaysTxt;
    }

    private void recalculateDays(){
        double total = 0.0;
        for(DaySelectedHoliday day : daySelectedHolidays){
            total += day.isHalfDay() ? 0.5 : 1;
        }

        totalDaysTxt.setText(String.format("%s %s",String.valueOf(total), "días"));
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

        TextView day;
        Switch halfDay;
        TextView labelHalfDay;

        private TextView monthLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = (TextView) itemView.findViewById(R.id.day);
            halfDay = (Switch) itemView.findViewById(R.id.halfDay);
            labelHalfDay = (TextView) itemView.findViewById(R.id.labelHalfDay);
            monthLabel = (TextView) itemView.findViewById(R.id.monthLabel);
        }
    }

}
