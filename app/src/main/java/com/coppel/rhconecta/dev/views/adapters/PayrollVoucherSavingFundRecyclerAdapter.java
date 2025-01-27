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
import com.coppel.rhconecta.dev.business.models.VoucherSavingFundResponse;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSavingFundDetail;
import com.coppel.rhconecta.dev.views.customviews.ExpandableTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherSavingFundRecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherSavingFundRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<VoucherResponse.FechaCorteCuenta> savingFundDates;
    private OnSavingFundVoucherClickListener onSavingFundVoucherClickListener;
    private OnActionClickListener onActionClickListener;

    public PayrollVoucherSavingFundRecyclerAdapter(Context context, List<VoucherResponse.FechaCorteCuenta> savingFundDates) {
        this.context = context;
        this.savingFundDates = savingFundDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_saving_found, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(savingFundDates.get(i));
    }

    @Override
    public int getItemCount() {
        return savingFundDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.extHeader)
        ExpandableTitle extHeader;
        @BindView(R.id.linDetail)
        LinearLayout linDetail;
        @BindView(R.id.ctlConnectionError)
        ConstraintLayout ctlConnectionError;
        @BindView(R.id.imgvRefresh)
        ImageView imgvRefresh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final VoucherResponse.FechaCorteCuenta savingFundDate) {
            extHeader.setSingleText(savingFundDate.getSfechanominanombre());
            ctlContainer.setVisibility(View.VISIBLE);
            ctlConnectionError.setVisibility(View.GONE);
            showFail(savingFundDate);
            verifyExpandedState(savingFundDate.isExpanded());
            generateDetails(savingFundDate);
            extHeader.setOnExpadableListener(new ExpandableTitle.OnExpadableListener() {
                @Override
                public void onClick() {
                    if (onSavingFundVoucherClickListener != null) {
                        if (extHeader.isExpanded()) {
                            onDetailExpanded(savingFundDate);
                        } else {
                            onDetailCollapsed(savingFundDate);
                        }
                    }
                }
            });
            extHeader.setOnOptionClickListener(new ExpandableTitle.OnOptionClickListener() {
                @Override
                public void onMailClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onMailClick(savingFundDate);
                    }
                }

                @Override
                public void onDownloadClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onDownloadClick(savingFundDate);
                    }
                }
            });
        }

        private void showFail(final VoucherResponse.FechaCorteCuenta savingFundDate) {
            if (savingFundDate.isFailDetail()) {
                ctlContainer.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                imgvRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onSavingFundVoucherClickListener != null) {
                            onSavingFundVoucherClickListener.onItemClick(savingFundDate, getAdapterPosition());
                        }
                    }
                });
            }
        }

        private void generateDetails(VoucherResponse.FechaCorteCuenta savingFundDate) {
            if (savingFundDate.getVoucherSavingFundResponse() != null) {
                VoucherSavingFundResponse.Response voucherSavingFundResponse = savingFundDate.getVoucherSavingFundResponse().getData().getResponse();
                ExpandableSavingFundDetail expandableSavingFundDetail;

                if (voucherSavingFundResponse.getMovimientos().getCuentaCorriente() != null && voucherSavingFundResponse.getMovimientos().getCuentaCorriente().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setCurrentCount(voucherSavingFundResponse.getMovimientos().getCuentaCorriente());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if (voucherSavingFundResponse.getMovimientos().getFondoEmpresa() != null && voucherSavingFundResponse.getMovimientos().getFondoEmpresa().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setEnterpriseFund(voucherSavingFundResponse.getMovimientos().getFondoEmpresa());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getFondotrabajadores() != null && voucherSavingFundResponse.getMovimientos().getFondotrabajadores().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setEmployeFund(voucherSavingFundResponse.getMovimientos().getFondotrabajadores());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getAhorroadicional() != null && voucherSavingFundResponse.getMovimientos().getAhorroadicional().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setAdditionalSaving(voucherSavingFundResponse.getMovimientos().getAhorroadicional());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getAhorroadicional2() != null && voucherSavingFundResponse.getMovimientos().getAhorroadicional2().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setSpecialAdditionalSaving(voucherSavingFundResponse.getMovimientos().getAhorroadicional2());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getMargenes() != null && voucherSavingFundResponse.getMovimientos().getMargenes().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setMargins(voucherSavingFundResponse.getMovimientos().getMargenes());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getAutoCop() != null && voucherSavingFundResponse.getMovimientos().getAutoCop().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setAutoCop(voucherSavingFundResponse.getMovimientos().getAutoCop());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getFaerColaborador() != null && voucherSavingFundResponse.getMovimientos().getFaerColaborador().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setEmployeFundFaer(voucherSavingFundResponse.getMovimientos().getFaerColaborador());
                    linDetail.addView(expandableSavingFundDetail);
                }

                if(voucherSavingFundResponse.getMovimientos().getFaerEmpresa() != null && voucherSavingFundResponse.getMovimientos().getFaerEmpresa().size() > 0) {
                    expandableSavingFundDetail = new ExpandableSavingFundDetail(context);
                    expandableSavingFundDetail.setEnterpriseFundFaer(voucherSavingFundResponse.getMovimientos().getFaerEmpresa());
                    linDetail.addView(expandableSavingFundDetail);
                }
            }
        }

        private void verifyExpandedState(boolean isExpanded) {
            if (isExpanded) {
                extHeader.setExpandedTrue();
                linDetail.setVisibility(View.VISIBLE);
            }
        }

        private void onDetailExpanded(VoucherResponse.FechaCorteCuenta savingFundDate) {
            linDetail.setVisibility(View.VISIBLE);
            savingFundDate.setExpanded(true);
            if (savingFundDate.getVoucherSavingFundResponse() == null) {
                onSavingFundVoucherClickListener.onItemClick(savingFundDate, getAdapterPosition());
                linDetail.setVisibility(View.GONE);
            }
        }

        private void onDetailCollapsed(VoucherResponse.FechaCorteCuenta savingFundDate) {
            linDetail.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(View.GONE);
            savingFundDate.setExpanded(false);
        }
    }

    public interface OnSavingFundVoucherClickListener {
        void onItemClick(VoucherResponse.FechaCorteCuenta savingFundDate, int position);
    }

    public void setOnSavingFundVoucherClickListener(OnSavingFundVoucherClickListener onSavingFundVoucherClickListener) {
        this.onSavingFundVoucherClickListener = onSavingFundVoucherClickListener;
    }

    public interface OnActionClickListener {
        void onMailClick(VoucherResponse.FechaCorteCuenta savingFundDate);

        void onDownloadClick(VoucherResponse.FechaCorteCuenta savingFundDate);
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }
}
