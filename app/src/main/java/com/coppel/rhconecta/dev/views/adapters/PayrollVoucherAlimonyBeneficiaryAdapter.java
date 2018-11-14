package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.ExpandableTitle;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherAlimonyBeneficiaryAdapter extends RecyclerView.Adapter<PayrollVoucherAlimonyBeneficiaryAdapter.ViewHolder> {

    private Context context;
    private List<VoucherResponse.FechasPensione> beneficiaryDates;
    private OnVoucherAlimonyBeneficiaryClickListener onVoucherAlimonyBeneficiaryClickListener;
    private OnActionClickListener onActionClickListener;

    public PayrollVoucherAlimonyBeneficiaryAdapter(Context context, List<VoucherResponse.FechasPensione> beneficiaryDates) {
        this.context = context;
        this.beneficiaryDates = beneficiaryDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_alimony_beneficiary, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(beneficiaryDates.get(i));
    }

    @Override
    public int getItemCount() {
        return beneficiaryDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.extHeader)
        ExpandableTitle extHeader;
        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.txvdBaseRetention)
        TextViewDetail txvdBaseRetention;
        @BindView(R.id.txvdPercent)
        TextViewDetail txvdPercent;
        @BindView(R.id.edtmRetention)
        EditTextMoney edtmRetention;
        @BindView(R.id.ctlConnectionError)
        ConstraintLayout ctlConnectionError;
        @BindView(R.id.imgvRefresh)
        ImageView imgvRefresh;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final VoucherResponse.FechasPensione beneficiaryDate) {
            extHeader.setTexts(beneficiaryDate.getStotalapagar(), "- " + beneficiaryDate.getSfechanomina());
            ctlContainer.setVisibility(View.VISIBLE);
            ctlConnectionError.setVisibility(View.GONE);
            showFail(beneficiaryDate);
            verifyExpandedState(beneficiaryDate.isExpanded());
            initDetails(beneficiaryDate);
            extHeader.setOnExpadableListener(new ExpandableTitle.OnExpadableListener() {
                @Override
                public void onClick() {
                    if (onVoucherAlimonyBeneficiaryClickListener != null) {
                        if (extHeader.isExpanded()) {
                            onDetailExpanded(beneficiaryDate);
                        } else {
                            onDetailCollapsed(beneficiaryDate);
                        }
                    }
                }
            });
            extHeader.setOnOptionClickListener(new ExpandableTitle.OnOptionClickListener() {
                @Override
                public void onMailClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onMailClick(beneficiaryDate);
                    }
                }

                @Override
                public void onDownloadClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onDownloadClick(beneficiaryDate);
                    }
                }
            });
        }

        private void showFail(final VoucherResponse.FechasPensione beneficiaryDate) {
            if (beneficiaryDate.isFailDetail()) {
                ctlContainer.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                imgvRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onVoucherAlimonyBeneficiaryClickListener != null) {
                            onVoucherAlimonyBeneficiaryClickListener.onItemClick(beneficiaryDate, getAdapterPosition());
                        }
                    }
                });
            }
        }

        private void verifyExpandedState(boolean isExpanded) {
            if (isExpanded) {
                extHeader.setExpandedTrue();
                txvdBaseRetention.setVisibility(View.VISIBLE);
                txvdPercent.setVisibility(View.VISIBLE);
                edtmRetention.setVisibility(View.VISIBLE);
            }
        }

        private void initDetails(VoucherResponse.FechasPensione beneficiaryDate) {
            if (beneficiaryDate.getVoucherAlimonyResponse() != null) {
                txvdBaseRetention.setTexts(context.getString(R.string.retention_base), beneficiaryDate.getVoucherAlimonyResponse().getData().getResponse().getDatosPension().getBaseretencion());
                txvdPercent.setTexts(context.getString(R.string.alimony_percent), beneficiaryDate.getVoucherAlimonyResponse().getData().getResponse().getDatosPension().getPorcentaje() + "%");
                edtmRetention.setInformativeMode(context.getString(R.string.alimony_retention), beneficiaryDate.getVoucherAlimonyResponse().getData().getResponse().getDatosPension().getRetencionpension());
            }
        }

        private void onDetailExpanded(VoucherResponse.FechasPensione beneficiaryDate) {
            txvdBaseRetention.setVisibility(View.VISIBLE);
            txvdPercent.setVisibility(View.VISIBLE);
            edtmRetention.setVisibility(View.VISIBLE);
            beneficiaryDate.setExpanded(true);
            if (beneficiaryDate.getVoucherAlimonyResponse() == null) {
                onVoucherAlimonyBeneficiaryClickListener.onItemClick(beneficiaryDate, getAdapterPosition());
                txvdBaseRetention.setVisibility(View.GONE);
                txvdPercent.setVisibility(View.GONE);
                edtmRetention.setVisibility(View.GONE);
            }
        }

        private void onDetailCollapsed(VoucherResponse.FechasPensione beneficiaryDate) {
            txvdBaseRetention.setVisibility(View.GONE);
            txvdPercent.setVisibility(View.GONE);
            edtmRetention.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(View.GONE);
            beneficiaryDate.setExpanded(false);
        }
    }

    public interface OnVoucherAlimonyBeneficiaryClickListener {
        void onItemClick(VoucherResponse.FechasPensione beneficiaryDate, int position);
    }

    public void setOnVoucherAlimonyBeneficiaryClickListener(OnVoucherAlimonyBeneficiaryClickListener onVoucherAlimonyBeneficiaryClickListener) {
        this.onVoucherAlimonyBeneficiaryClickListener = onVoucherAlimonyBeneficiaryClickListener;
    }

    public interface OnActionClickListener {
        void onMailClick(VoucherResponse.FechasPensione beneficiaryDate);

        void onDownloadClick(VoucherResponse.FechasPensione beneficiaryDate);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }
}
