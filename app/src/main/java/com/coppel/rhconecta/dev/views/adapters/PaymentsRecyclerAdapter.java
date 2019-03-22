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
import com.coppel.rhconecta.dev.business.models.ConsultaMetodosPagoResponse;
import com.coppel.rhconecta.dev.business.models.LocationEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentsRecyclerAdapter extends RecyclerView.Adapter<PaymentsRecyclerAdapter.ViewHolder> {

    private Context context;
    private int selectedPosition = -1;
    private List<ConsultaMetodosPagoResponse.PaymentWay> paymentWays;

    public PaymentsRecyclerAdapter(Context context, List<ConsultaMetodosPagoResponse.PaymentWay> payments) {
        this.paymentWays = payments;
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_datetime_letter, viewGroup, false);
        return new PaymentsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final ConsultaMetodosPagoResponse.PaymentWay paymentWay = paymentWays.get(position);
        viewHolder.txvName.setText(paymentWay.getNom_retiro());
        viewHolder.checkboxElement.setChecked(paymentWay.isSelected() ? true : false);

        if (position == selectedPosition) {
            viewHolder.checkboxElement.setChecked(true);
            paymentWay.setSelected(true);

        } else {
            paymentWay.setSelected(false);
            viewHolder.checkboxElement.setChecked(false);
        }

        viewHolder.checkboxElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!paymentWay.isSelected()) {
                    selectedPosition = position;
                } else {
                    selectedPosition = -1;
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
        for(ConsultaMetodosPagoResponse.PaymentWay dato: paymentWays){
            if(dato.isSelected())
                return true;
        }
        return false;
    }

    private void setSelected(boolean isSelected){
        for(ConsultaMetodosPagoResponse.PaymentWay state : paymentWays){
            state.setSelected(isSelected);
        }
    }

    public List<ConsultaMetodosPagoResponse.PaymentWay> getStates(){
        return paymentWays;
    }

    public ConsultaMetodosPagoResponse.PaymentWay getSelectedPayment(){

        for(ConsultaMetodosPagoResponse.PaymentWay paymentWay: paymentWays){
            if(paymentWay.isSelected()){
                return paymentWay;
            }
        }

        return null;
    }

    public int getSelectedPositionPayment(){

        for(int i = 0; i< paymentWays.size() ; i++){
            ConsultaMetodosPagoResponse.PaymentWay paymentWay = paymentWays.get(i);
            if(paymentWay.isSelected()){
                return i;
            }
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        return paymentWays.size();
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
