package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherPTUResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.views.customviews.ExpandableTitle;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetailThree;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherPTURecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherPTURecyclerAdapter.ViewHolder> {

    private Context context;
    private List<VoucherResponse.FechasUtilidade> ptuDates;
    private OnVoucherPTUClickListener onVoucherPTUClickListener;
    private OnActionClickListener onActionClickListener;

    public PayrollVoucherPTURecyclerAdapter(Context context, List<VoucherResponse.FechasUtilidade> ptuDates) {
        this.context = context;
        this.ptuDates = ptuDates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_ptu, viewGroup, false);
        return new PayrollVoucherPTURecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setHasTransientState(true);
        viewHolder.bind(ptuDates.get(i));
    }

    @Override
    public int getItemCount() {
        return ptuDates.size();
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final VoucherResponse.FechasUtilidade ptuDate) {
            extHeader.setTexts(ptuDate.getStotalapagar(), "- " + ptuDate.getSfechanomina());
            ctlContainer.setVisibility(View.VISIBLE);
            ctlConnectionError.setVisibility(View.GONE);
            showFail(ptuDate);
            verifyExpandedState(ptuDate.isExpanded());
            generateDetails(ptuDate);
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
                    if (onActionClickListener != null) {
                        onActionClickListener.onMailClick(ptuDate);
                    }
                }

                @Override
                public void onDownloadClick() {
                    if (onActionClickListener != null) {
                        onActionClickListener.onDownloadClick(ptuDate);
                    }
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

        private void generateDetails(VoucherResponse.FechasUtilidade ptuDate) {
            if (ptuDate.getVoucherPTUResponse() != null) {
                VoucherPTUResponse.Response voucherPTUResponse = ptuDate.getVoucherPTUResponse().getData().getResponse();
                TextViewDetailThree textViewDetailThree = new TextViewDetailThree(context);
                TextViewDetail textViewDetail;
                textViewDetailThree.setTexts(context.getString(R.string.concept), context.getString(R.string.income), context.getString(R.string.expenses));
                textViewDetailThree.setStartTextColor(context.getResources().getColor(R.color.colorTextGray));
                textViewDetailThree.setMiddleTextColor(context.getResources().getColor(R.color.colorTextGray));
                textViewDetailThree.setEndTextColor(context.getResources().getColor(R.color.colorTextGray));
                textViewDetailThree.setStartTextSize(13F);
                textViewDetailThree.setMiddleTextSize(13F);
                textViewDetailThree.setEndTextSize(13F);
                textViewDetailThree.hideDivider();
                linDetail.addView(textViewDetailThree);

                if (voucherPTUResponse != null && voucherPTUResponse.getDatosutilidades2() != null && voucherPTUResponse.getDatosutilidades2().length > 0) {
                    for (VoucherPTUResponse.DatosUtilidades2 data : voucherPTUResponse.getDatosutilidades2()) {
                        String desc = data.getDescripcionmovimiento().trim();
                        if (desc.equals("REPARTO UTILIDADES")) {
                            textViewDetailThree = new TextViewDetailThree(context);
                            textViewDetailThree.setStartText(context.getString(R.string.profit_sharing));
                            textViewDetailThree.setMiddleText(data.getImporte2());
                            textViewDetailThree.setFontMiddle(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_medium));
                            linDetail.addView(textViewDetailThree);
                        } else if (desc.equals("LIQ ANTI REPA")) {
                            textViewDetail = new TextViewDetail(context);
                            textViewDetail.setTexts(context.getString(R.string.anti_distribution_liquidation), data.getImporte2());
                            textViewDetail.setEndFont(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_medium));
                            linDetail.addView(textViewDetail);
                        } else if (desc.equals("ISR")) {
                            textViewDetail = new TextViewDetail(context);
                            textViewDetail.setTexts(context.getResources().getStringArray(R.array.uppercase_words)[1], data.getImporte2());
                            textViewDetail.setEndFont(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_medium));
                            linDetail.addView(textViewDetail);
                        }
                    }
                }

                if (voucherPTUResponse != null && voucherPTUResponse.getDatosutilidades() != null) {
                    textViewDetailThree = new TextViewDetailThree(context);
                    textViewDetailThree.setTexts(context.getString(R.string.totals), voucherPTUResponse.getDatosutilidades().getTotalingresos2(),
                            voucherPTUResponse.getDatosutilidades().getTotalegresos2());
                    textViewDetailThree.setFontMiddle(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_medium));
                    textViewDetailThree.setFontEnd(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_medium));
                    linDetail.addView(textViewDetailThree);
                    textViewDetail = new TextViewDetail(context);
                    textViewDetail.setTexts(context.getString(R.string.neto), voucherPTUResponse.getDatosutilidades().getTotalapagar2());
                    textViewDetail.setStartTextSize(17F);
                    textViewDetail.setEndTextSize(17F);
                    textViewDetail.setStartFont(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_bold));
                    textViewDetail.setEndFont(ResourcesCompat.getFont(context, R.font.lineto_circular_pro_bold));
                    textViewDetail.hideDivider();
                    linDetail.addView(textViewDetail);
                }
            }
        }

        private void verifyExpandedState(boolean isExpanded) {
            if (isExpanded) {
                extHeader.setExpandedTrue();
                linDetail.setVisibility(View.VISIBLE);
            }
        }

        private void onDetailExpanded(VoucherResponse.FechasUtilidade ptuDate) {
            linDetail.setVisibility(View.VISIBLE);
            ptuDate.setExpanded(true);
            if (ptuDate.getVoucherPTUResponse() == null) {
                onVoucherPTUClickListener.onItemClick(ptuDate, getAdapterPosition());
                linDetail.setVisibility(View.GONE);
            }
        }

        private void onDetailCollapsed(VoucherResponse.FechasUtilidade ptuDate) {
            linDetail.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(View.GONE);
            ptuDate.setExpanded(false);
        }

    }

    public interface OnVoucherPTUClickListener {
        void onItemClick(VoucherResponse.FechasUtilidade ptuDate, int position);
    }

    public void setOnVoucherPTUClickListener(OnVoucherPTUClickListener onVoucherPTUClickListener) {
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
