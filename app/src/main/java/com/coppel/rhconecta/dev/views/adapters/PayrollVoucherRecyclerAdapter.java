package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.models.VoucherRosterResponse;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.ExpandableTitle;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableHeader;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherRecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<VoucherResponse.FechasNomina> payrollDates;
    private OnPayrollVoucherClickListener onPayrollVoucherClickListener;
    private OnActionClickListener onActionClickListener;

    public PayrollVoucherRecyclerAdapter(Context context, List<VoucherResponse.FechasNomina> payrollDates) {
        this.context = context;
        this.payrollDates = payrollDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(payrollDates.get(i));
    }

    @Override
    public int getItemCount() {
        return payrollDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.extHeader)
        ExpandableTitle extHeader;
        @BindView(R.id.txvehIncome)
        TextViewExpandableHeader txvehIncome;
        @BindView(R.id.linIncome)
        LinearLayout linIncome;
        @BindView(R.id.txvehDeductions)
        TextViewExpandableHeader txvehDeductions;
        @BindView(R.id.linDeductions)
        LinearLayout linDeductions;
        @BindView(R.id.edtmTotalPay)
        EditTextMoney edtmTotalPay;
        @BindView(R.id.ctlConnectionError)
        ConstraintLayout ctlConnectionError;
        @BindView(R.id.imgvRefresh)
        ImageView imgvRefresh;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final VoucherResponse.FechasNomina payrollDate) {
            extHeader.setTexts(payrollDate.getStotalapagar(), "- " + payrollDate.getSfechanomina());
            ctlContainer.setVisibility(View.VISIBLE);
            ctlConnectionError.setVisibility(View.GONE);
            showFail(payrollDate);
            verifyExpandedState(payrollDate.isExpanded());
            initDetails(payrollDate);
            extHeader.setOnExpadableListener(new ExpandableTitle.OnExpadableListener() {
                @Override
                public void onClick() {
                    if (onPayrollVoucherClickListener != null) {
                        if (extHeader.isExpanded()) {
                            onDetailExpanded(payrollDate);
                        } else {
                            onDetailCollapsed(payrollDate);
                        }
                    }
                }
            });
            extHeader.setOnOptionClickListener(new ExpandableTitle.OnOptionClickListener() {
                @Override
                public void onMailClick() {
                    if(onActionClickListener != null) {
                        onActionClickListener.onMailClick(payrollDate);
                    }
                }

                @Override
                public void onDownloadClick() {
                    if(onActionClickListener != null) {
                        onActionClickListener.onDownloadClick(payrollDate);
                    }
                }
            });
            txvehIncome.setOnExpandableListener(new TextViewExpandableHeader.OnExpandableListener() {
                @Override
                public void onClick() {
                    incomesStateChange();
                }
            });
            txvehDeductions.setOnExpandableListener(new TextViewExpandableHeader.OnExpandableListener() {
                @Override
                public void onClick() {
                    deductionsStateChange();
                }
            });
        }

        private void showFail(final VoucherResponse.FechasNomina payrollDate) {
            if (payrollDate.isFailDetail()) {
                ctlContainer.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                imgvRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onPayrollVoucherClickListener != null) {
                            onPayrollVoucherClickListener.onItemClick(payrollDate, getAdapterPosition());
                        }
                    }
                });
            }
        }

        private void verifyExpandedState(boolean isExpanded) {
            if (isExpanded) {
                extHeader.setExpandedTrue();
                txvehIncome.setVisibility(View.VISIBLE);
                txvehDeductions.setVisibility(View.VISIBLE);
                edtmTotalPay.setVisibility(View.VISIBLE);
            }
        }

        private void initDetails(VoucherResponse.FechasNomina payrollDate) {
            if (payrollDate.getVoucherRosterResponse() != null) {
                txvehIncome.setTexts(context.getString(R.string.income), payrollDate.getVoucherRosterResponse().getData().getResponse().getColaborador().getMtotalingresos());
                txvehDeductions.setTexts(context.getString(R.string.deductions), payrollDate.getVoucherRosterResponse().getData().getResponse().getColaborador().getMtotalegresos());
                edtmTotalPay.setInformativeMode(context.getString(R.string.total_pay), payrollDate.getVoucherRosterResponse().getData().getResponse().getColaborador().getMtotalapagar());
                generateDetailComponents(payrollDate);
            }
        }

        private void generateDetailComponents(VoucherResponse.FechasNomina payrollDate) {
            for (VoucherRosterResponse.IngresosEgreso j : payrollDate.getVoucherRosterResponse().getData().getResponse().getIngresosEgresos()) {
                TextViewDetail textViewDetail = new TextViewDetail(context);
                switch (j.cpercepciondeduccion) {
                    case "P":
                        linIncome.addView(textViewDetail);
                        break;
                    case "D":
                        linDeductions.addView(textViewDetail);
                        break;
                }
                //textViewDetail.setTexts(TextUtilities.capitalizeText(context, j.getCdescripcionmovimiento()), j.getMimporte());
                /**Se muestra el texto sin procesar**/
                textViewDetail.setTexts(j.getCdescripcionmovimiento(), j.getMimporte());

            }
        }

        private void onDetailExpanded(VoucherResponse.FechasNomina payrollDate) {
            txvehIncome.setVisibility(View.VISIBLE);
            txvehDeductions.setVisibility(View.VISIBLE);
            edtmTotalPay.setVisibility(View.VISIBLE);
            payrollDate.setExpanded(true);
            if (payrollDate.getVoucherRosterResponse() == null) {
                onPayrollVoucherClickListener.onItemClick(payrollDate, getAdapterPosition());
                txvehIncome.setVisibility(View.GONE);
                txvehDeductions.setVisibility(View.GONE);
                edtmTotalPay.setVisibility(View.GONE);
            }
        }

        private void onDetailCollapsed(VoucherResponse.FechasNomina payrollDate) {
            txvehIncome.setExpandedFalse();
            txvehDeductions.setExpandedFalse();
            txvehIncome.setVisibility(View.GONE);
            txvehDeductions.setVisibility(View.GONE);
            linIncome.setVisibility(View.GONE);
            linDeductions.setVisibility(View.GONE);
            edtmTotalPay.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(View.GONE);
            payrollDate.setExpanded(false);
        }

        private void incomesStateChange() {
            if (txvehIncome.isExpanded()) {
                linIncome.setVisibility(View.VISIBLE);
            } else {
                linIncome.setVisibility(View.GONE);
            }
        }

        private void deductionsStateChange() {
            if (txvehDeductions.isExpanded()) {
                linDeductions.setVisibility(View.VISIBLE);
            } else {
                linDeductions.setVisibility(View.GONE);
            }
        }
    }

    public interface OnPayrollVoucherClickListener {
        void onItemClick(VoucherResponse.FechasNomina payrollDate, int position);
    }

    public void setOnPayrollVoucherClickListener(OnPayrollVoucherClickListener onPayrollVoucherClickListener) {
        this.onPayrollVoucherClickListener = onPayrollVoucherClickListener;
    }

    public interface OnActionClickListener {
        void onMailClick(VoucherResponse.FechasNomina payrollDate);

        void onDownloadClick(VoucherResponse.FechasNomina payrollDate);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }
}
