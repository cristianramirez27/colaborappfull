package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.view.Gravity;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderHolidaysColaborator extends RelativeLayout {

    @BindView(R.id.titleDetail)
    TextViewExpandableRightArrowHeader titleDetail;
    @BindView(R.id.diasDecision)
    TextViewDetail diasDecision;
    @BindView(R.id.diasPendientesAnterior)
    TextViewDetail diasPendientesAnterior;
    @BindView(R.id.diasAdicionalesPendientes)
    TextViewDetail diasAdicionalesPendientes;
    @BindView(R.id.diasAdicionalesRegistrados)
    TextViewDetail diasAdicionalesRegistrados;
    @BindView(R.id.fechaPrimaVacacional)
    TextViewDetail fechaPrimaVacacional;
    @BindView(R.id.imageView2)
    ImageView iconPrimaVacacional;


    @BindView(R.id.layoutDetail)
    LinearLayout layoutDetail;


    public HeaderHolidaysColaborator(Context context) {
        super(context);
        initViews();
    }

    public HeaderHolidaysColaborator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_detail_holidays, this);
        ButterKnife.bind(this);

        titleDetail.setOnExpandableListener(new TextViewExpandableRightArrowHeader.OnExpandableListener() {
            @Override
            public void onClick() {
                if (titleDetail.isExpanded()) {
                    layoutDetail.setVisibility(View.VISIBLE);
                } else {
                    layoutDetail.setVisibility(View.GONE);
                }
            }
        });

    }


    public void setDetailData(HolidaysPeriodsResponse detailData){
        HolidaysPeriodsResponse.Response response = detailData.getData().getResponse();
        titleDetail.setTitleTextSize(17);
        titleDetail.setValueTextSize(20);

        //TODO Validar que esta suma sea correcta
        double holidayDaysTotal = response.getNum_adicionales() + response.getNum_decision() + response.getNum_decisionanterior();
        String numHolidays = String.valueOf(holidayDaysTotal);
        //String numHolidays = String.valueOf(response.getNum_diasvacaciones());
        if(holidayDaysTotal % 1 == 0){
            numHolidays = numHolidays.substring(0,numHolidays.indexOf("."));
            numHolidays = String.valueOf(Integer.parseInt(numHolidays));
        }

        titleDetail.setTexts(getContext().getString(R.string.title_holidays_days),String.format("%s %s",numHolidays,getContext().getString(R.string.title_days)));

        diasDecision.setSingleLine(true);
        diasDecision.setGuideline73(0.70f);
        String daysDesition = String.valueOf(response.getNum_decision());
        if(response.getNum_decision() % 1 == 0){
            daysDesition = daysDesition.substring(0,daysDesition.indexOf("."));
            daysDesition = String.valueOf(Integer.parseInt(daysDesition));
        }
        diasDecision.setTexts(getContext().getString(R.string.title_day_availables),String.format("%s %s",daysDesition,getContext().getString(R.string.title_days)));
        diasDecision.setTextsSize(12,12);
        diasDecision.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasDecision.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        //La etiqueta “Días Pendientes Año anterior” solo se mostrara si el campo num_decisionanterior es
        //mayor a 0.
        if(response.getNum_decisionanterior() > 0){
            diasPendientesAnterior.setVisibility(VISIBLE);
            diasPendientesAnterior.setGuideline73(0.70f);
            diasPendientesAnterior.setSingleLine(true);

            String daysPending = String.valueOf(response.getNum_decisionanterior());
            if(response.getNum_decisionanterior() % 1 == 0){
                daysPending = daysPending.substring(0,daysPending.indexOf("."));
                daysPending = String.valueOf(Integer.parseInt(daysPending));
            }

            diasPendientesAnterior.setTexts(getContext().getString(R.string.title_days_pending_lastyear),String.format("%s %s",daysPending,getContext().getString(R.string.title_days)));
            diasPendientesAnterior.setTextsSize(12,12);
            diasPendientesAnterior.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
            diasPendientesAnterior.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        }else {
            diasPendientesAnterior.setVisibility(GONE);
        }

        diasAdicionalesPendientes.setGuideline73(0.70f);
        diasAdicionalesPendientes.setSingleLine(true);

        String daysPendingAditional = String.valueOf(response.getNum_adicionales());
        if(response.getNum_adicionales() % 1 == 0){
            daysPendingAditional = daysPendingAditional.substring(0,daysPendingAditional.indexOf("."));
            daysPendingAditional = String.valueOf(Integer.parseInt(daysPendingAditional));
        }

        diasAdicionalesPendientes.setTexts(getContext().getString(R.string.title_days_aditionals),String.format("%s %s",daysPendingAditional,getContext().getString(R.string.title_days)));
        diasAdicionalesPendientes.setTextsSize(12,12);
        diasAdicionalesPendientes.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasAdicionalesPendientes.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasAdicionalesRegistrados.setGuideline73(0.85f);
        diasAdicionalesRegistrados.setSingleLine(true);
        String daysRegisteredAditional = String.valueOf(response.getNum_adicionalesagregadas());
        if(response.getNum_adicionalesagregadas() % 1 == 0){
            daysRegisteredAditional = daysRegisteredAditional.substring(0,daysRegisteredAditional.indexOf("."));
            daysRegisteredAditional = String.valueOf(Integer.parseInt(daysRegisteredAditional));
        }

        diasAdicionalesRegistrados.setTexts(getContext().getString(R.string.title_days_aditionals_register),String.format("%s %s",daysRegisteredAditional,getContext().getString(R.string.title_days)));
        diasAdicionalesRegistrados.setTextsSize(12,12);
        diasAdicionalesRegistrados.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasAdicionalesRegistrados.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        setDataHolidayBonus(response.getFec_primavacacional());

    }

    public void iconPrimaVacacionalOnClickListener(View.OnClickListener l) {
        iconPrimaVacacional.setOnClickListener(l);
    }

    public void setDataHolidayBonus(String date) {
        fechaPrimaVacacional.setSingleLine(true);
        fechaPrimaVacacional.setGuideline73(0.47f);
        fechaPrimaVacacional.setTexts(getContext().getString(R.string.title_bonus_date), String.valueOf(date));
        fechaPrimaVacacional.setTextsSize(12, 12);
        fechaPrimaVacacional.setGravityValue(Gravity.CENTER);
        int color = getContext().getResources().getColor(android.R.color.black);
        fechaPrimaVacacional.setStartTextColor(color);
        fechaPrimaVacacional.setEndTextColor(color);
        fechaPrimaVacacional.txvTitle.setPadding(24, 0, 0, 0);
        fechaPrimaVacacional.txvValue.setPadding(0, 0, 136, 0);
    }

}
