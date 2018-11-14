package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.VoucherSavingFundResponse;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableSavingFundDetail extends ConstraintLayout implements View.OnClickListener {

    private boolean isExpanded;
    @BindView(R.id.ctlContainer)
    ConstraintLayout ctlContainer;
    @BindView(R.id.imgvArrow)
    ImageView imgvArrow;
    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.txvValue)
    TextView txvValue;
    @BindView(R.id.linDetail)
    LinearLayout linDetail;

    public ExpandableSavingFundDetail(Context context) {
        super(context);
        initViews();
    }

    public ExpandableSavingFundDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.expandable_saving_fund_detail, this);
        ButterKnife.bind(this);
        ctlContainer.setOnClickListener(this);
        imgvArrow.setOnClickListener(this);
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setCurrentCount(List<VoucherSavingFundResponse.CuentaCorriente> currentCount) {
        if (currentCount != null && currentCount.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.current_count));
            txvValue.setText(currentCount.get(currentCount.size() - 1).getCuentaCorrienteimp());
            for (VoucherSavingFundResponse.CuentaCorriente count : currentCount) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                /*if( count.getCuentaCorrientedes() != null &&  (count.getCuentaCorrientedes().contains("NOMINA") || count.getCuentaCorrientedes().contains("nomina"))){
                    count.setCuentaCorrientedes(count.getCuentaCorrientedes().contains("NOMINA"));
                }*/
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), count.getCuentaCorrientedes()), count.getCuentaCorrienteimp());
                linDetail.addView(textViewDetail);
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setEnterpriseFund(List<VoucherSavingFundResponse.FondoEmpresa> enterpriseFund) {
        if (enterpriseFund != null && enterpriseFund.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.enterprise_funds));
            txvValue.setText(enterpriseFund.get(enterpriseFund.size() - 1).getFondoEmpimp());
            for (VoucherSavingFundResponse.FondoEmpresa fund : enterpriseFund) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), fund.getFondoEmpdes()), fund.getFondoEmpimp());
                linDetail.addView(textViewDetail);
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setEmployeFund(List<VoucherSavingFundResponse.Fondotrabajadore> employeFund) {
        if (employeFund != null && employeFund.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.employe_fund));
            txvValue.setText(employeFund.get(employeFund.size() - 1).getFondotrabajado());
            for (VoucherSavingFundResponse.Fondotrabajadore fund : employeFund) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), fund.getFondotrabajadordes()), fund.getFondotrabajado());
                linDetail.addView(textViewDetail);
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setAdditionalSaving(List<VoucherSavingFundResponse.Ahorroadicional> additionalSaving) {
        if (additionalSaving != null && additionalSaving.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.additional_saving_fund));
            txvValue.setText(additionalSaving.get(additionalSaving.size() - 1).getAhorroadicional());
            for (VoucherSavingFundResponse.Ahorroadicional saving : additionalSaving) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), saving.getAhorroadicionaldes()), saving.getAhorroadicional());
                linDetail.addView(textViewDetail);
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setSpecialAdditionalSaving(List<VoucherSavingFundResponse.Ahorroadicional2> specialAdditionalSaving) {
        if (specialAdditionalSaving != null && specialAdditionalSaving.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.additional_special_saving_fund));
            txvValue.setText(specialAdditionalSaving.get(specialAdditionalSaving.size() - 1).getAhorroadicional2());
            for (VoucherSavingFundResponse.Ahorroadicional2 saving : specialAdditionalSaving) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), saving.getAhorroadicional2des()), saving.getAhorroadicional2());
                linDetail.addView(textViewDetail);
            }
        }
    }

    public void setMargins(List<VoucherSavingFundResponse.Margene> margins) {
        if (margins != null && margins.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.margins));
            for (VoucherSavingFundResponse.Margene margin : margins) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                if (margin.getMargen_Credito_des() != null) {
                    textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), "Margen de Crédito"), margin.getMargen_Credito_imp());
                    linDetail.addView(textViewDetail);
                } else if (margin.getMargen_Compra_des() != null) {
                    textViewDetail = new TextViewDetail(getContext());
                    textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), "Margen de Compra"), margin.getMargen_Compra_imp());
                    linDetail.addView(textViewDetail);
                }
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setAutoCop(List<VoucherSavingFundResponse.AutoCop> autoCop) {
        if (autoCop != null && autoCop.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.autocop));
            txvValue.setText(autoCop.get(autoCop.size() - 1).getAuntocopimp());
            for (VoucherSavingFundResponse.AutoCop auto : autoCop) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), auto.getAuntocopdes()), auto.getAuntocopimp());
                linDetail.addView(textViewDetail);
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setEmployeFundFaer(List<VoucherSavingFundResponse.FaerColaborador> employeFaer) {
        if (employeFaer != null && employeFaer.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.employe_fund_faer));
            txvValue.setText(employeFaer.get(employeFaer.size() - 1).getFaerdesimpempleado());
            for (VoucherSavingFundResponse.FaerColaborador faer : employeFaer) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), faer.getFaerColaboradorDes()), faer.getFaerdesimpempleado());
                linDetail.addView(textViewDetail);
            }
        }
    }

    /**
     * 1 Noviembre 2018
     * Modificacion: Se toma el ultimo valor (=FINAL) para mostrar en la cantidad del comprobante.---
     *
     * **/
    public void setEnterpriseFundFaer(List<VoucherSavingFundResponse.FaerEmpresa> enterpriseFaer) {
        if (enterpriseFaer != null && enterpriseFaer.size() > 0) {
            txvTitle.setText(getContext().getString(R.string.enterprise_fund_faer));
            txvValue.setText(enterpriseFaer.get(enterpriseFaer.size() - 1).getFaerdesimpempresa());
            for (VoucherSavingFundResponse.FaerEmpresa faer : enterpriseFaer) {
                TextViewDetail textViewDetail = new TextViewDetail(getContext());
                textViewDetail.setTexts(TextUtilities.capitalizeText(getContext(), faer.getFaerEmpresaDes()), faer.getFaerdesimpempresa());
                linDetail.addView(textViewDetail);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ctlContainer:
            case R.id.imgvArrow:
                if (isExpanded) {
                    imgvArrow.setImageResource(R.drawable.ic_down_arrow_blue);
                    linDetail.setVisibility(View.GONE);
                    isExpanded = false;
                } else {
                    imgvArrow.setImageResource(R.drawable.ic_up_arrow_blue);
                    linDetail.setVisibility(View.VISIBLE);
                    isExpanded = true;
                }
                break;
        }
    }
}
