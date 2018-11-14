package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherBeneficiariesAdapter extends RecyclerView.Adapter<PayrollVoucherBeneficiariesAdapter.ViewHolder> {

    private List<VoucherResponse.DatosPencion> beneficiaries;
    private OnBeneficiaryClickListener onBeneficiaryClickListener;
    private int selectedPosition = -1;

    public PayrollVoucherBeneficiariesAdapter(List<VoucherResponse.DatosPencion> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_beneficiary, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txtName.setText(beneficiaries.get(i).getNom_beneficiario());
        viewHolder.cbBeneficiary.setChecked(i == selectedPosition);
        viewHolder.cbBeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = viewHolder.getAdapterPosition();
                if(onBeneficiaryClickListener != null) {
                    onBeneficiaryClickListener.onItemClick(beneficiaries.get(viewHolder.getAdapterPosition()));
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return beneficiaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.cbBeneficiary)
        CheckBox cbBeneficiary;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBeneficiaryClickListener {
        void onItemClick(VoucherResponse.DatosPencion beneficiary);
    }

    public void setOnBeneficiaryClickListener(OnBeneficiaryClickListener onBeneficiaryClickListener) {
        this.onBeneficiaryClickListener = onBeneficiaryClickListener;
    }
}
