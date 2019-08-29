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
import com.coppel.rhconecta.dev.business.models.Center;
import com.coppel.rhconecta.dev.business.utils.Command;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CenterRecyclerAdapter extends RecyclerView.Adapter<CenterRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;

    private Command actionSelect;

    private List<Center> centers;
    public CenterRecyclerAdapter(Context context, List<Center> centers) {
        this.centers = centers;
        this.context = context;
    }


    public void setActionSelect(Command actionSelect) {
        this.actionSelect = actionSelect;
    }

    @NonNull
    @Override
    public CenterRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_center, viewGroup, false);
        return new CenterRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Center currentItem = centers.get(position);

        viewHolder.txvName.setText(currentItem.getNum_centro() > 0 ?
                String.format("%s %s",currentItem.getNum_centro(),currentItem.getNom_centro()):
                String.format("%s",currentItem.getNom_centro()));
        viewHolder.checkboxElement.setChecked(currentItem.isSelected() ? true : false);

        if(position == selectedPosition) {
            viewHolder.checkboxElement.setChecked(true);
            currentItem.setSelected(true);

            if(actionSelect != null)actionSelect.execute();

        } else {
            currentItem.setSelected(false);
            viewHolder.checkboxElement.setChecked(false);

             if(actionSelect != null)actionSelect.execute();
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

    public boolean hasCenterSelected(){
        for(Center dato: centers){
            if(dato.isSelected())
                return true;
        }
        return false;
    }

    private void setSelected(boolean isSelected){
        for(Center center : centers){
            center.setSelected(isSelected);
        }
    }

    public List<Center> getCenters(){
        return centers;
    }

    public Center getCenterSelected(){

        for(Center center: centers){
            if(center.isSelected()){
                return center;
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return centers.size();
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
