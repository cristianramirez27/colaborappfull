package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherPTUResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.ExpandableTitle;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableHeader;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherPTUV2RecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherPTUV2RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<VoucherResponse.FechasUtilidade> ptuDates;
    private PayrollVoucherPTUV2RecyclerAdapter.OnVoucherPTUClickListener onVoucherPTUClickListener;
    private PayrollVoucherPTUV2RecyclerAdapter.OnActionClickListener onActionClickListener;

    public PayrollVoucherPTUV2RecyclerAdapter(Context context, List<VoucherResponse.FechasUtilidade> ptuDates) {
        this.context = context;
        this.ptuDates = ptuDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_ptu_v2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(ptuDates.get(i));
    }

    @Override
    public int getItemCount() {
        return ptuDates.size();
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
        @BindView(R.id.txvehExpenses)
        TextViewExpandableHeader txvehExpenses;
        @BindView(R.id.linExpenses)
        LinearLayout linExpenses;
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

        private void bind(final VoucherResponse.FechasUtilidade  ptuDate) {
            extHeader.setTexts(ptuDate.getStotalapagar(), "- " + ptuDate.getSfechanomina());
            ctlContainer.setVisibility(View.VISIBLE);
            ctlConnectionError.setVisibility(View.GONE);
            showFail(ptuDate);
            verifyExpandedState(ptuDate.isExpanded());
            initDetails(ptuDate);
            extHeader.setOnExpadableListener(new ExpandableTitle.OnExpadableListener() {
                @Override
                public void onClick() {
                    if (onVoucherPTUClickListener != null) {
                        if (extHeader.isExpanded()) {
                            onDetailExpanded(ptuDate);
                        } else {
                            onDetailCollapsed(ptuDate);
                        }
                    }
                }
            });
            extHeader.setOnOptionClickListener(new ExpandableTitle.OnOptionClickListener() {
                @Override
                public void onMailClick() {
                    if(onActionClickListener != null) {
                        onActionClickListener.onMailClick(ptuDate);
                    }
                }

                @Override
                public void onDownloadClick() {
                    if(onActionClickListener != null) {
                        onActionClickListener.onDownloadClick(ptuDate);
                    }
                }
            });
            txvehIncome.setOnExpandableListener(new TextViewExpandableHeader.OnExpandableListener() {
                @Override
                public void onClick() {
                    incomesStateChange();
                }
            });
            txvehExpenses.setOnExpandableListener(new TextViewExpandableHeader.OnExpandableListener() {
                @Override
                public void onClick() {
                    deductionsStateChange();
                }
            });
        }

        private void showFail(final VoucherResponse.FechasUtilidade ptuDate) {
            if (ptuDate.isFailDetail()) {
                ctlContainer.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                imgvRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onVoucherPTUClickListener != null) {
                            onVoucherPTUClickListener.onItemClick(ptuDate, getAdapterPosition());
                        }
                    }
                });
            }
        }

        private void verifyExpandedState(boolean isExpanded) {
            if (isExpanded) {
                extHeader.setExpandedTrue();
                txvehIncome.setVisibility(View.VISIBLE);
                txvehExpenses.setVisibility(View.VISIBLE);
                edtmTotalPay.setVisibility(View.VISIBLE);
            }
        }

        private void initDetails(VoucherResponse.FechasUtilidade ptuDate) {
            if (ptuDate.getVoucherPTUResponse() != null) {
                txvehIncome.setTexts(context.getString(R.string.income), ptuDate.getVoucherPTUResponse().getData().getResponse().getDatosutilidades().getTotalingresos2());
                txvehExpenses.setTexts(context.getString(R.string.expenses), ptuDate.getVoucherPTUResponse().getData().getResponse().getDatosutilidades().getTotalegresos2());
                edtmTotalPay.setInformativeMode(context.getString(R.string.neto), ptuDate.getVoucherPTUResponse().getData().getResponse().getDatosutilidades().getTotalapagar2());
                edtmTotalPay.setTitleGravity(Gravity.LEFT);
                generateDetailComponents(ptuDate);
            }
        }

        private void generateDetailComponents(VoucherResponse.FechasUtilidade ptuDate) {

            if (ptuDate.getVoucherPTUResponse() != null) {
                VoucherPTUResponse.Response voucherPTUResponse = ptuDate.getVoucherPTUResponse().getData().getResponse();

                if (voucherPTUResponse != null && voucherPTUResponse.getDatosutilidades2() != null && voucherPTUResponse.getDatosutilidades2().length > 0) {
                    for (VoucherPTUResponse.DatosUtilidades2 data : voucherPTUResponse.getDatosutilidades2()) {
                        TextViewDetail textViewDetail = new TextViewDetail(context);
                        String desc = data.getDescripcionmovimiento().trim();
                        if (desc.equals("REPARTO UTILIDADES")) {
                            textViewDetail.setTexts(TextUtilities.capitalizeText(context, context.getString(R.string.profit_sharing)), data.getImporte2());
                            linIncome.addView(textViewDetail);
                        } else if (desc.equals("LIQ ANTI REPA")) {
                            textViewDetail.setTexts(TextUtilities.capitalizeText(context,context.getString(R.string.anti_distribution_liquidation)), data.getImporte2());
                            linExpenses.addView(textViewDetail);
                        } else if (desc.equals("ISR")) {
                            textViewDetail.setTexts(TextUtilities.capitalizeText(context, context.getResources().getStringArray(R.array.uppercase_words)[1]),data.getImporte2());
                            linExpenses.addView(textViewDetail);
                        }


                    }
                }

            }

        }

        private void onDetailExpanded(VoucherResponse.FechasUtilidade ptuDate) {
            txvehIncome.setVisibility(View.VISIBLE);
            txvehExpenses.setVisibility(View.VISIBLE);
            edtmTotalPay.setVisibility(View.VISIBLE);
            ptuDate.setExpanded(true);
            if (ptuDate.getVoucherPTUResponse() == null) {
                onVoucherPTUClickListener.onItemClick(ptuDate, getAdapterPosition());
                txvehIncome.setVisibility(View.GONE);
                txvehExpenses.setVisibility(View.GONE);
                edtmTotalPay.setVisibility(View.GONE);
            }
        }

        private void onDetailCollapsed(VoucherResponse.FechasUtilidade ptuDate) {
            txvehIncome.setExpandedFalse();
            txvehExpenses.setExpandedFalse();
            txvehIncome.setVisibility(View.GONE);
            txvehExpenses.setVisibility(View.GONE);
            linIncome.setVisibility(View.GONE);
            linExpenses.setVisibility(View.GONE);
            edtmTotalPay.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(View.GONE);
            ptuDate.setExpanded(false);
        }

        private void incomesStateChange() {
            if (txvehIncome.isExpanded()) {
                linIncome.setVisibility(View.VISIBLE);
            } else {
                linIncome.setVisibility(View.GONE);
            }
        }

        private void deductionsStateChange() {
            if (txvehExpenses.isExpanded()) {
                linExpenses.setVisibility(View.VISIBLE);
            } else {
                linExpenses.setVisibility(View.GONE);
            }
        }
    }


    public interface OnVoucherPTUClickListener {
        void onItemClick(VoucherResponse.FechasUtilidade ptuDate, int position);
    }

    public void setOnVoucherPTUClickListener(PayrollVoucherPTUV2RecyclerAdapter.OnVoucherPTUClickListener onVoucherPTUClickListener) {
        this.onVoucherPTUClickListener = onVoucherPTUClickListener;
    }

    public interface OnActionClickListener {
        void onMailClick(VoucherResponse.FechasUtilidade ptuDate);

        void onDownloadClick(VoucherResponse.FechasUtilidade ptuDate);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }


}
