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
import com.coppel.rhconecta.dev.business.models.Estatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;

    private List<Estatus> estatus;
    public StatusRecyclerAdapter(Context context, List<Estatus> estatus) {
        this.estatus = estatus;
        this.context = context;
    }

    @NonNull
    @Override
    public StatusRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_center, viewGroup, false);
        return new StatusRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Estatus currentItem = estatus.get(position);
        viewHolder.txvName.setText(currentItem.getNom_estatus_liq());
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

    public boolean hasCenterSelected(){
        for(Estatus dato: estatus){
            if(dato.isSelected())
                return true;
        }
        return false;
    }

    private void setSelected(boolean isSelected){
        for(Estatus estatus : estatus){
            estatus.setSelected(isSelected);
        }
    }

    public List<Estatus> getCenters(){
        return estatus;
    }

    public Estatus getCenterSelected(){

        for(Estatus estatus: estatus){
            if(estatus.isSelected()){
                return estatus;
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return estatus.size();
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
