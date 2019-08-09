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




    public RequestHolidaysColaboratorView(Context context) {
        super(context);
        initViews();
    }

    public RequestHolidaysColaboratorView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        layoutTotalDays.setVisibility(showTotalDays ? VISIBLE: GONE);
        diasVacaciones.setText(period.getNum_dias());

        markerSpliceLeft.setVisibility(period.isShowMarker() ? VISIBLE : GONE);

    }


}
