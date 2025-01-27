package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.ExpandableTitle;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherBonusRecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherBonusRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<VoucherResponse.FechasAguinaldo> bonusDates;
    private OnBonusVoucherClickListener onBonusVoucherClickListener;
    private OnActionClickListener onActionClickListener;

    public PayrollVoucherBonusRecyclerAdapter(Context context, List<VoucherResponse.FechasAguinaldo> bonusDates) {
        this.context = context;
        this.bonusDates = bonusDates;
    }

    @NonNull
    @Override
    public PayrollVoucherBonusRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_bonus, viewGroup, false);
        return new PayrollVoucherBonusRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(bonusDates.get(i));
    }

    @Override
    public int getItemCount() {
        return bonusDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.extHeader)
        ExpandableTitle extHeader;
        @BindView(R.id.linDetail)
        LinearLayout linDetail;
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

        private void bind(final VoucherResponse.FechasAguinaldo bonusDate) {
            extHeader.setTexts(bonusDate.getStotalapagar(), "- " + bonusDate.getSfechanomina());
            ctlContainer.setVisibility(View.VISIBLE);
            ctlConnectionError.setVisibility(View.GONE);
            showFail(bonusDate);
            verifyExpandedState(bonusDate.isExpanded());
            initDetails(bonusDate);
            extHeader.setOnExpadableListener(new ExpandableTitle.OnExpadableListener() {
                @Override
                public void onClick() {
                    if (onBonusVoucherClickListener != null) {
                        if (extHeader.isExpanded()) {
                            onDetailExpanded(bonusDate);
                        } else {
                            onDetailCollapsed(bonusDate);
                        }
                    }
                }
            });
            extHeader.setOnOptionClickListener(new ExpandableTitle.OnOptionClickListener() {
                @Override
                public void onMailClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onMailClick(bonusDate);
                    }
                }

                @Override
                public void onDownloadClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onDownloadClick(bonusDate);
                    }
                }
            });
        }

        private void showFail(final VoucherResponse.FechasAguinaldo bonusDate) {
            if (bonusDate.isFailDetail()) {
                ctlContainer.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                imgvRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onBonusVoucherClickListener != null) {
                            onBonusVoucherClickListener.onItemClick(bonusDate, getAdapterPosition());
                        }
                    }
                });
            }
        }

        private void verifyExpandedState(boolean isExpanded) {
            if (isExpanded) {
                extHeader.setExpandedTrue();
                linDetail.setVisibility(View.VISIBLE);
                edtmTotalPay.setVisibility(View.VISIBLE);
            }
        }

        private void initDetails(VoucherResponse.FechasAguinaldo payrollDate) {
            if (payrollDate.getVoucherBonusResponse() != null) {
                TextViewDetail textViewDetail = new TextViewDetail(context);
                linDetail.addView(textViewDetail);
                textViewDetail.setTexts(context.getString(R.string.bonus_capitalize),
                        payrollDate.getVoucherBonusResponse().getData().getResponse().getDatosaguinaldo().getTotalingresos2());
                textViewDetail = new TextViewDetail(context);
                linDetail.addView(textViewDetail);
                textViewDetail.setTexts(context.getString(R.string.isr),
                        payrollDate.getVoucherBonusResponse().getData().getResponse().getDatosaguinaldo().getTotalegresos2());
                //textViewDetail.setEndTextColor(context.getResources().getColor(R.color.colorRed));
                edtmTotalPay.setInformativeMode(context.getString(R.string.total_pay),
                        payrollDate.getVoucherBonusResponse().getData().getResponse().getDatosaguinaldo().getTotalapagar2());
            }
        }

        private void onDetailExpanded(VoucherResponse.FechasAguinaldo bonusDate) {
            linDetail.setVisibility(View.VISIBLE);
            edtmTotalPay.setVisibility(View.VISIBLE);
            bonusDate.setExpanded(true);
            if (bonusDate.getVoucherBonusResponse() == null) {
                onBonusVoucherClickListener.onItemClick(bonusDate, getAdapterPosition());
                linDetail.setVisibility(View.GONE);
                edtmTotalPay.setVisibility(View.GONE);
            }
        }

        private void onDetailCollapsed(VoucherResponse.FechasAguinaldo payrollDate) {
            linDetail.setVisibility(View.GONE);
            edtmTotalPay.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(View.GONE);
            payrollDate.setExpanded(false);
        }
    }

    public interface OnBonusVoucherClickListener {
        void onItemClick(VoucherResponse.FechasAguinaldo bonusDate, int position);
    }

    public void setOnBonusVoucherClickListener(OnBonusVoucherClickListener onBonusVoucherClickListener) {
        this.onBonusVoucherClickListener = onBonusVoucherClickListener;
    }

    public interface OnActionClickListener {
        void onMailClick(VoucherResponse.FechasAguinaldo bonusDate);

        void onDownloadClick(VoucherResponse.FechasAguinaldo bonusDate);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }
}
