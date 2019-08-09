package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class HeaderHolidaysColaboratorGte extends RelativeLayout {

    @BindView(R.id.imgColaborador)
    CircleImageView imgColaborador;
    @BindView(R.id.nameColaborator)
    TextView nameColaborator;
    @BindView(R.id.vacacionesAgendadas)
    TextViewDetail vacacionesAgendadas;
    @BindView(R.id.vacacionesAutorizadas)
    TextViewDetail vacacionesAutorizadas;
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


    public HeaderHolidaysColaboratorGte(Context context) {
        super(context);
        initViews();
    }

    public HeaderHolidaysColaboratorGte(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_colaborator_header_holidays, this);
        ButterKnife.bind(this);

        vacacionesAgendadas.hideDivider();
        vacacionesAutorizadas.hideDivider();

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


    public void setDetailData(ColaboratorHoliday colaborator,HolidaysPeriodsResponse detailData){
        HolidaysPeriodsResponse.Response response = detailData.getData().getResponse();

        if(detailData.getData().getResponse().getNum_diasporautorizar() > 0){
            String numDaysToAuthorized= String.valueOf(response.getNum_diasporautorizar());
            if(response.getNum_diasporautorizar() % 1 == 0){
                numDaysToAuthorized = numDaysToAuthorized.substring(0,numDaysToAuthorized.indexOf("."));
                numDaysToAuthorized = String.valueOf(Integer.parseInt(numDaysToAuthorized));
            }

            vacacionesAutorizadas.setTexts(getContext().getString(R.string.holidays_to_authorized),
                    String.format("%s %s",numDaysToAuthorized, detailData.getData().getResponse().getNum_diasporautorizar() > 0 ? "días" : "día"));

            vacacionesAutorizadas.setTextsSize(12,12);
            vacacionesAutorizadas.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
            vacacionesAutorizadas.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
            vacacionesAutorizadas.setVisibility(VISIBLE);

           /* RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 20, 20, 0);
            vacacionesAgendadas.setLayoutParams(lp);
*/
            MarginLayoutParams marginParams = (MarginLayoutParams) vacacionesAgendadas.getLayoutParams();
            marginParams.setMargins(marginParams.leftMargin,
                    -80,
                    marginParams.rightMargin,
                    marginParams.bottomMargin);

        }


        if(detailData.getData().getResponse().getNum_diasautorizados() > 0){

            String numDaysAuthorized= String.valueOf(response.getNum_diasautorizados());
            if(response.getNum_diasvacaciones() % 1 == 0){
                numDaysAuthorized = numDaysAuthorized.substring(0,numDaysAuthorized.indexOf("."));
                numDaysAuthorized = String.valueOf(Integer.parseInt(numDaysAuthorized));
            }

            vacacionesAgendadas.setTexts(getContext().getString(R.string.holidays_scheduled),
                    String.format("%s %s",numDaysAuthorized, detailData.getData().getResponse().getNum_diasautorizados() > 0 ? "días" : "día"));
            vacacionesAgendadas.setTextsSize(12,12);
            vacacionesAgendadas.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
            vacacionesAgendadas.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
            vacacionesAgendadas.setVisibility(VISIBLE);
        }


        nameColaborator.setText(TextUtilities.capitalizeText(getContext(),colaborator.getNom_empleado()));

        if(colaborator.getFotoperfil() != null && !colaborator.getFotoperfil().isEmpty()){
            Glide.with(getApplicationContext()).load(colaborator.getFotoperfil())
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .placeholder(R.drawable.ic_account_white)
                    .into(imgColaborador);
        }

        titleDetail.hideDivider(true);
        titleDetail.setTitleTextSize(14);
        titleDetail.setValueTextSize(14);

        //TODO Validar que esta suma sea correcta
        double holidayDaysTotal = response.getNum_adicionales() + response.getNum_decision() + response.getNum_decisionanterior();
        String numHolidays = String.valueOf(holidayDaysTotal);
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

        fechaPrimaVacacional.setSingleLine(true);
        fechaPrimaVacacional.hideDivider();
        fechaPrimaVacacional.setGuideline73(0.50f);
        fechaPrimaVacacional.setTexts(getContext().getString(R.string.title_bonus_date),response.getFec_primavacacional());
        fechaPrimaVacacional.setTextsSize(12,12);
        fechaPrimaVacacional.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        fechaPrimaVacacional.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

    }


}
