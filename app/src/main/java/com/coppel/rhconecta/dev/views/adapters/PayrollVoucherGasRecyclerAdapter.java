package com.coppel.rhconecta.dev.views.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherGasRecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherGasRecyclerAdapter.ViewHolder> {

    private List<VoucherResponse.FechaGasolina> gasDates;
    private OnGasVoucherClickListener onGasVoucherClickListener;

    public PayrollVoucherGasRecyclerAdapter(List<VoucherResponse.FechaGasolina> gasDates) {
        this.gasDates = gasDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_gas, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.txvDate.setText( gasDates.get(i).getSfechanominanombre2());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onGasVoucherClickListener != null) {
                    onGasVoucherClickListener.onDateClick(gasDates.get(viewHolder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gasDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txvDate)
        TextView txvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnGasVoucherClickListener {
        void onDateClick(VoucherResponse.FechaGasolina gasDate);
    }

    public void setOnGasVoucherClickListener(OnGasVoucherClickListener onGasVoucherClickListener) {
        this.onGasVoucherClickListener = onGasVoucherClickListener;
    }
}
