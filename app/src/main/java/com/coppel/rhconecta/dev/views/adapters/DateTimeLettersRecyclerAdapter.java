package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateTimeLettersRecyclerAdapter extends RecyclerView.Adapter<DateTimeLettersRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;

    private List<LetterConfigResponse.Horario> schedules;
    public DateTimeLettersRecyclerAdapter(Context context, List<LetterConfigResponse.Horario> schedules) {
        this.schedules = schedules;
        this.context = context;
    }

    @NonNull
    @Override
    public DateTimeLettersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_datetime_letter, viewGroup, false);
        return new DateTimeLettersRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final LetterConfigResponse.Horario currentItem = schedules.get(position);

        if(currentItem instanceof LetterConfigResponse.HorarioLaboral){
            viewHolder.txvName.setText(((LetterConfigResponse.HorarioLaboral) currentItem).getHrs_laboral());
        }else  if(currentItem instanceof LetterConfigResponse.HorarioComida){
            viewHolder.txvName.setText(((LetterConfigResponse.HorarioComida) currentItem).getHrs_comida());
        }else  if(currentItem instanceof LetterConfigResponse.DiaLaboral){
            viewHolder.txvName.setText(((LetterConfigResponse.DiaLaboral) currentItem).getDayName());
        }


        viewHolder.checkboxElement.setChecked(currentItem.isSelected() ? true : false);

        if (position == selectedPosition) {
            viewHolder.checkboxElement.setChecked(true);
            currentItem.setSelected(true);

        } else {
            currentItem.setSelected(false);
            viewHolder.checkboxElement.setChecked(false);
        }

        viewHolder.checkboxElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentItem.isSelected()) {
                    //currentItem.setSelected(false);
                    //viewHolder.checkboxElement.setChecked(false);
                    selectedPosition = position;

                } else {

                    selectedPosition = -1;
                    //setSelected(false);
                    //currentItem.setSelected(true);
                    //viewHolder.checkboxElement.setChecked(true);

                }

                notifyDataSetChanged();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public boolean hasScheduleSelected(){
        for(LetterConfigResponse.Horario dato : schedules){
            if(dato.isSelected())
                return true;
        }
        return false;
    }

    private void setSelected(boolean isSelected){
        for(LetterConfigResponse.Horario dato : schedules){
            dato.setSelected(isSelected);
        }
    }

    public List<LetterConfigResponse.Horario> getSchedules(){
        return schedules;
    }

    public String getSelectedSchedule(){

        for(LetterConfigResponse.Horario horario : schedules){
            if(horario.isSelected()){
                if(horario instanceof LetterConfigResponse.HorarioLaboral){
                    return ((LetterConfigResponse.HorarioLaboral) horario).getHrs_laboral();
                }else  if(horario instanceof LetterConfigResponse.HorarioComida){
                   return  ((LetterConfigResponse.HorarioComida) horario).getHrs_comida();
                }else  if(horario instanceof LetterConfigResponse.DiaLaboral){
                  return   ((LetterConfigResponse.DiaLaboral) horario).getDayName();
                }
            }
        }

        return "";
    }

    @Override
    public int getItemCount() {
        return schedules.size();
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

        @BindView(R.id.ctlContainer)
        RelativeLayout ctlContainer;
        @BindView(R.id.checkboxElement)
        CheckBox checkboxElement;
        @BindView(R.id.nameElement)
        TextView txvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
