package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class RequestHolidaysColaboratorView extends RelativeLayout {

    @BindView(R.id.imgColaborador)
    CircleImageView imgColaborador;
    @BindView(R.id.nameColaborator)
    TextView nameColaborator;

    @BindView(R.id.layoutInicial)
    RelativeLayout layoutInicial;
    @BindView(R.id.layoutFinal)
    RelativeLayout layoutFinal;

    @BindView(R.id.fechaInicio)
    TextView fechaInicio;
    @BindView(R.id.fechaFin)
    TextView fechaFin;
    @BindView(R.id.diasVacaciones)
    TextView diasVacaciones;
    @BindView(R.id.layoutTotalDays)
    RelativeLayout layoutTotalDays;
    @BindView(R.id.markerSpliceLeft)
    View markerSpliceLeft;
    @BindView(R.id.container_main)
    RelativeLayout relativeLayout;
    private @ColorRes int black ;

    public void setThemeHoliday() {
        layoutInicial.setPadding(0,8,0,8);
        layoutFinal.setPadding(0,8,0,8);
        relativeLayout.setPadding(0,30,0,10);
        nameColaborator.setPadding(0,16,0,4);
        fechaInicio.setTextColor(black);
        fechaFin.setTextColor(black);
        fechaFin.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        fechaInicio.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }


    public RequestHolidaysColaboratorView(Context context) {
        super(context);
        black = context.getColor(R.color.dark_holiday);
        initViews();
    }

    public RequestHolidaysColaboratorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        black = context.getColor(R.color.dark_holiday);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.item_solicitud_vacaciones_colaborador, this);
        ButterKnife.bind(this);
    }


    public void setDetailData(HolidayPeriod period,boolean showTotalDays){
        nameColaborator.setText(TextUtilities.capitalizeText(getContext(),period.getNom_empleado()));
        if(period.getFotoperfil() != null && !period.getFotoperfil().isEmpty()){
            Glide.with(getApplicationContext()).load(period.getFotoperfil())
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .placeholder(R.drawable.ic_account_white)
                    .into(imgColaborador);
        }

        fechaInicio.setText(period.getFec_ini());
        fechaFin.setText(period.getFec_fin());

        layoutInicial.setVisibility(period.getFec_ini().isEmpty() ? GONE : VISIBLE);
        layoutFinal.setVisibility(period.getFec_fin().isEmpty() ? GONE : VISIBLE);

        layoutTotalDays.setVisibility(showTotalDays ? VISIBLE: GONE);


        if(period.getNum_dias() != null &&!period.getNum_dias().isEmpty()){
            /*Validamos punto decimal*/
            String daysTotal = period.getNum_dias();
            if(!daysTotal.contains(".5")){
                daysTotal = daysTotal.substring(0,daysTotal.indexOf("."));

                diasVacaciones.setText(String.format("%s días",daysTotal));
            }else {
                diasVacaciones.setText(daysTotal);
            }

        }


        markerSpliceLeft.setBackgroundResource(period.getIdu_marca() == 1 ?  R.drawable.backgroud_circle_melon :  R.drawable.backgroud_circle_green);
        markerSpliceLeft.setVisibility(period.isShowMarker() ? VISIBLE : GONE);

    }


}
