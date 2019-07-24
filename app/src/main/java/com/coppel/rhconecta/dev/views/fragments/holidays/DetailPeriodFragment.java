package com.coppel.rhconecta.dev.views.fragments.holidays;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ConfigurationHolidaysData;
import com.coppel.rhconecta.dev.business.models.HolidayGetDetailPeriodResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaborator;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CONSULTA_VACACIONES;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIOD_DETAIL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_COLABORATOR_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPeriodFragment extends Fragment implements  View.OnClickListener, DialogFragmentWarning.OnOptionClick,
        IServicesContract.View{

    public static final String TAG = DetailPeriodFragment.class.getSimpleName();
    private AppCompatActivity parent;
    private DialogFragmentWarning dialogFragmentWarning;


    @BindView(R.id.layoutObservaciones)
    LinearLayout layoutObservaciones;
    @BindView(R.id.estatus)
    TextView estatus;
    @BindView(R.id.fechaInicio)
    TextView fechaInicio;
    @BindView(R.id.fechaFin)
    TextView fechaFin;
    @BindView(R.id.diasVacaciones)
    TextView diasVacaciones;
    @BindView(R.id.expObservaciones)
    ExpandableSimpleTitle expObservaciones;
    @BindView(R.id.nombreGte)
    TextView nombreGte;
    @BindView(R.id.fechaRechazo)
    TextView fechaRechazo;
    @BindView(R.id.motivoRechazo)
    TextView motivoRechazo;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.collapsibleCalendarView)
    CollapsibleCalendar collapsibleCalendar;

    private IScheduleOptions IScheduleOptions;
    private VacacionesActivity vacacionesActivity;


    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private HolidayPeriod holidayPeriod;

    private long mLastClickTime = 0;

          private boolean EXPIRED_SESSION;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions= (IScheduleOptions)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static DetailPeriodFragment getInstance(HolidayPeriod holidayPeriod){
        DetailPeriodFragment fragment = new DetailPeriodFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_HOLIDAYS,holidayPeriod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holidayPeriod = (HolidayPeriod)getArguments().getSerializable(BUNDLE_OPTION_DATA_HOLIDAYS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_periodo, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }

        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

        //To hide or show expand icon
        collapsibleCalendar.setExpandIconVisible(true);
        Calendar today=new GregorianCalendar();
        today.add(Calendar.DATE,1);
        collapsibleCalendar.expand(100);

        collapsibleCalendar.setVisibilityExpandIcon(View.INVISIBLE);
        collapsibleCalendar.setVisibilityBtnNext(View.INVISIBLE);
        collapsibleCalendar.setVisibilityBtnPrev(View.INVISIBLE);
        collapsibleCalendar.setExpandIconVisible(false);

        expObservaciones.setText("Observaciones");
        expObservaciones.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                observacionesStateChange(expObservaciones,layoutObservaciones);
            }
        });

        return view;
    }


    private void observacionesStateChange(ExpandableSimpleTitle expandable, LinearLayout layoutToExpand) {
        if (expandable.isExpanded()) {
            layoutToExpand.setVisibility(VISIBLE);
        } else {
            layoutToExpand.setVisibility(View.GONE);
        }
    }


    private void setDataHeader(HolidayGetDetailPeriodResponse response){
        HolidayGetDetailPeriodResponse.Response detail = response.getData().getResponse().get(0);
         fechaInicio.setText(detail.getFec_ini());
         fechaFin.setText(detail.getFec_fin());
         diasVacaciones.setText(detail.getNum_dias());
        estatus.setText(detail.getDes_estatus());

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(detail.getColor()));
        gd.setCornerRadius(20);
        estatus.setBackgroundDrawable(gd);

        nombreGte.setText(detail.getNom_gerente());
        fechaRechazo.setText(detail.getFec_estatus());
        motivoRechazo.setText(detail.getDes_comentario());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDetailPeriod();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                parent.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnSchedule:


                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof HolidayGetDetailPeriodResponse) {
                    HolidayGetDetailPeriodResponse responseDetail = (HolidayGetDetailPeriodResponse)response.getResponse();
                    setDataHeader(responseDetail);

                }

                break;
        }
    }


          private void showWarningDialog(String message) {
              dialogFragmentWarning = new DialogFragmentWarning();
              dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
              dialogFragmentWarning.setOnOptionClick(this);
              dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
          }

          @Override
          public void onLeftOptionClick() {
              dialogFragmentWarning.close();
          }

          @Override
          public void onRightOptionClick() {
              if (EXPIRED_SESSION) {
                  AppUtilities.closeApp(parent);
              }

              dialogFragmentWarning.close();
          }

          @Override
    public void showError(ServicesError coppelServicesError) {
        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.HOLIDAYS:
                    showWarningDialog(coppelServicesError.getMessage());
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
            }
        }
    }

    private void getDetailPeriod(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData  holidayRequestData = new HolidayRequestData(GET_PERIOD_DETAIL, 4,numEmployer);
        holidayRequestData.setIdu_folio(holidayPeriod.getIdu_folio());
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
        if(dialogFragmentLoader != null) dialogFragmentLoader.close();
    }

}
