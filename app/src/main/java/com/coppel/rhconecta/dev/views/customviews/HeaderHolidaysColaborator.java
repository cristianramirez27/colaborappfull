package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.views.adapters.DevolutionsRecyclerAdapter;

import java.util.List;

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
        titleDetail.setTitleTextSize(17);
        titleDetail.setValueTextSize(20);
        titleDetail.setTexts(getContext().getString(R.string.title_holidays_days),String.format("%s %s","10",getContext().getString(R.string.title_days)));

        diasDecision.setSingleLine(true);
        diasDecision.setGuideline73(0.70f);
        diasDecision.setTexts(getContext().getString(R.string.title_day_availables),"8.5 dias");
        diasDecision.setTextsSize(12,12);
        diasDecision.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasDecision.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasPendientesAnterior.setGuideline73(0.70f);
        diasPendientesAnterior.setSingleLine(true);
        diasPendientesAnterior.setTexts(getContext().getString(R.string.title_days_pending_lastyear),"1 día");
        diasPendientesAnterior.setTextsSize(12,12);
        diasPendientesAnterior.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasPendientesAnterior.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasAdicionalesPendientes.setGuideline73(0.70f);
        diasAdicionalesPendientes.setSingleLine(true);
        diasAdicionalesPendientes.setTexts(getContext().getString(R.string.title_days_aditionals),"0 días");
        diasAdicionalesPendientes.setTextsSize(12,12);
        diasAdicionalesPendientes.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasAdicionalesPendientes.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasAdicionalesRegistrados.setGuideline73(0.70f);
        diasAdicionalesRegistrados.setSingleLine(true);
        diasAdicionalesRegistrados.setTexts(getContext().getString(R.string.title_days_aditionals_register),"0 días");
        diasAdicionalesRegistrados.setTextsSize(12,12);
        diasAdicionalesRegistrados.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasAdicionalesRegistrados.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        fechaPrimaVacacional.setGuideline73(0.70f);
        fechaPrimaVacacional.setSingleLine(true);
        fechaPrimaVacacional.setTexts(getContext().getString(R.string.title_bonus_date),"Viernes, 30-07-2019");
        fechaPrimaVacacional.setTextsSize(12,12);
        fechaPrimaVacacional.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        fechaPrimaVacacional.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

    }


}
