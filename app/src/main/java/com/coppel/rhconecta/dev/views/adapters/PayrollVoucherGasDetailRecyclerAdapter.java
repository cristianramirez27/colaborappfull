package com.coppel.rhconecta.dev.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.ExpandableGasTicketDetail;
import com.coppel.rhconecta.dev.views.modelview.GasUnit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherGasDetailRecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherGasDetailRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<GasUnit> units;

    public PayrollVoucherGasDetailRecyclerAdapter(Context context, List<GasUnit> units) {
        this.context = context;
        this.units = units;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_gas_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(units.get(i));
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private boolean isExpanded;
        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.imgvArrow)
        ImageView imgvArrow;
        @BindView(R.id.txvTitle)
        TextView txvTitle;
        @BindView(R.id.ctlTicketsContainer)
        ConstraintLayout ctlTicketsContainer;
        @BindView(R.id.linTickets)
        LinearLayout linTickets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        private void bind(final GasUnit unit) {
            txvTitle.setText(context.getString(R.string.unit) + ": " + unit.getUnitNumber());
            ctlContainer.setOnClickListener(this);
            imgvArrow.setOnClickListener(this);
            generateComponents(unit);
        }

        private void generateComponents(GasUnit unit) {
            for (GasUnit.GasTicket ticket : unit.getTickets()) {
                ExpandableGasTicketDetail expandableGasTicketDetail = new ExpandableGasTicketDetail(context);
                linTickets.addView(expandableGasTicketDetail);
                expandableGasTicketDetail.setTicketDetail(ticket);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ctlContainer:
                case R.id.imgvArrow:
                    if (isExpanded) {
                        imgvArrow.setImageResource(R.drawable.ic_down_arrow_blue);
                        ctlTicketsContainer.setVisibility(View.GONE);
                        isExpanded = false;
                    } else {
                        imgvArrow.setImageResource(R.drawable.ic_up_arrow_blue);
                        ctlTicketsContainer.setVisibility(View.VISIBLE);
                        isExpanded = true;
                    }
                    break;
            }
        }
    }
}
