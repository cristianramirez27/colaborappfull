package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.LocationEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatesRecyclerAdapter extends RecyclerView.Adapter<StatesRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;

    private List<LocationEntity> states;
    public StatesRecyclerAdapter(Context context, List<LocationEntity> states) {
        this.states = states;
        this.context = context;
    }

    @NonNull
    @Override
    public StatesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_datetime_letter, viewGroup, false);
        return new StatesRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final LocationEntity currentItem = states.get(position);
        viewHolder.txvName.setText(currentItem.getNombre());
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
        for(LocationEntity  dato: states){
            if(dato.isSelected())
                return true;
        }
        return false;
    }

    private void setSelected(boolean isSelected){
        for(LocationEntity state : states){
            state.setSelected(isSelected);
        }
    }

    public List<LocationEntity> getStates(){
        return states;
    }

    public LocationEntity getSelectedSchedule(){

        for(LocationEntity state: states){
            if(state.isSelected()){
                return state;
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return states.size();
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
