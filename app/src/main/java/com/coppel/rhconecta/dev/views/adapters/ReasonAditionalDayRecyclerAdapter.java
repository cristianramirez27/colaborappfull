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
import com.coppel.rhconecta.dev.business.interfaces.IControlViews;
import com.coppel.rhconecta.dev.business.models.Center;
import com.coppel.rhconecta.dev.business.models.ReasonAditionaDay;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentControlAditionalDays;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReasonAditionalDayRecyclerAdapter extends RecyclerView.Adapter<ReasonAditionalDayRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;
    private IControlViews IControlViews;

    private List<ReasonAditionaDay> reasonAditionaDays;
    public ReasonAditionalDayRecyclerAdapter(Context context, List<ReasonAditionaDay> reasonAditionaDays,IControlViews IControlViews) {
        this.reasonAditionaDays = reasonAditionaDays;
        this.context = context;
        this.IControlViews = IControlViews;
    }

    @NonNull
    @Override
    public ReasonAditionalDayRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_center, viewGroup, false);
        return new ReasonAditionalDayRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final ReasonAditionaDay currentItem = reasonAditionaDays.get(position);
        viewHolder.txvName.setText(currentItem.getNom_motivo());
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
                    IControlViews.enabledOtherReason(currentItem.getIdu_motivo() == 4 ? true : false);
                    IControlViews.enabledAuthorized(currentItem.getIdu_motivo() != 4 ? true : false);

                } else {
                    IControlViews.enabledAuthorized(false);
                    selectedPosition = -1;
                    IControlViews.enabledOtherReason(false);

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
        for(ReasonAditionaDay dato: reasonAditionaDays){
            if(dato.isSelected())
                return true;
        }
        return false;
    }

    private void setSelected(boolean isSelected){
        for(ReasonAditionaDay reason : reasonAditionaDays){
            reason.setSelected(isSelected);
        }
    }

    public List<ReasonAditionaDay> getReasonAditionalDays(){
        return reasonAditionaDays;
    }

    public ReasonAditionaDay getReasonAditionalDaySelected(){

        for(ReasonAditionaDay reason: reasonAditionaDays){
            if(reason.isSelected()){
                return reason;
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return reasonAditionaDays.size();
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
