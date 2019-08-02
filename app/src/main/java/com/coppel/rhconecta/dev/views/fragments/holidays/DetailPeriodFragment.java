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
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodFolio;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysCancelResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaborator;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
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
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CONSULTA_VACACIONES;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIOD_DETAIL;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_COLABORATOR_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPeriodFragment extends Fragment implements  View.OnClickListener, DialogFragmentWarning.OnOptionClick,
        IServicesContract.View{

    public static final String TAG = DetailPeriodFragment.class.getSimpleName();
    private AppCompatActivity parent;
    private DialogFragmentWarning dialogFragmentWarning;



    @BindView(R.id.bordeAmarillo)
    View bordeAmarillo;
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

    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtReason)
    TextView txtReason;


    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.collapsibleCalendarView)
    CollapsibleCalendar collapsibleCalendar;

    private IScheduleOptions IScheduleOptions;
    private VacacionesActivity vacacionesActivity;


    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private HolidayPeriod holidayPeriod;

    private boolean isCanceled;

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
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_period_holidays));
        }

        btnCancel.setOnClickListener(this);
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
        collapsibleCalendar.setMultipleDays(true);
        collapsibleCalendar.setEnable(false);
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
        gd.setCornerRadius(40);
        estatus.setBackgroundDrawable(gd);

        txtDate.setText(getDateTitle(holidayPeriod.getIdu_estatus()));
        txtReason.setText(getReasonTitle(holidayPeriod.getIdu_estatus()));

        nombreGte.setText(detail.getNom_gerente());
        fechaRechazo.setText(detail.getFec_estatus());
        motivoRechazo.setText(detail.getDes_comentario());


        if(response.getData().getResponse().get(0).getIdu_estatus() >= 2 &&
                response.getData().getResponse().get(0).getIdu_estatus() <= 4 ){
            hideCalendar();
        }else {
            setSelectedDays(response);
        }

    }

    private String getDateTitle(int status){
        switch (status){
            case  1:
                return "Fecha";

            case  2:
                return "Fecha de Rechazo";
            case  3:
                return "Fecha de Cancelación";
        }
        return "Fecha";
    }

    private String getReasonTitle(int status){
        switch (status){
            case  1:
                return "";

            case  2:
                return "Motivo de Rechazo";
            case  3:
                return "Motivo de Cancelación";
        }
        return "";
    }

    private void hideCalendar(){
        expObservaciones.setVisibility(View.GONE);
        layoutObservaciones.setVisibility(VISIBLE);
        layoutObservaciones.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        bordeAmarillo.setVisibility(View.GONE);
        collapsibleCalendar.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
    }

    private void setSelectedDays(HolidayGetDetailPeriodResponse response){

        String dateStart = response.getData().getResponse().get(0).getFec_ini();
        String dateEnd = response.getData().getResponse().get(0).getFec_fin();

        String[] dateProcess = null;
        String[] dateParts = null;
        int year;
        int month;
        int day;
        //Obtenemos la fecha inicial
        dateProcess = dateStart.split(",");
        dateParts = dateProcess[1].trim().split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.set(year,month,day);
        //Obtenemos la fecha final
        dateProcess = dateEnd.split(",");
        dateParts = dateProcess[1].trim().split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Fin = Calendar.getInstance();
        calendar_Fin.set(year,month,day);

        Calendar  calendar = Calendar.getInstance();
        calendar.set(calendar_Ini.get(Calendar.YEAR),calendar_Ini.get(Calendar.MONTH),calendar_Ini.get(Calendar.DAY_OF_MONTH));

        CalendarAdapter adapter = new CalendarAdapter(getActivity(), calendar);
        collapsibleCalendar.setAdapter(adapter);

        List<Day> listDaySelected = new ArrayList<>();

        do{
            listDaySelected.add(new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));
            calendar.add(Calendar.DATE,1);

        }while (!calendar.after(calendar_Fin));


        collapsibleCalendar.select(listDaySelected);

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
            case R.id.btnCancel:

                cancelRequest();


                break;
        }
    }


    private void cancelRequest(){
        showAlertDialogDeletePeriods();
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

                }else if(response.getResponse() instanceof HolidaysCancelResponse) {
                    HolidaysCancelResponse responseDetail = (HolidaysCancelResponse)response.getResponse();
                    showWarningDialog(responseDetail.getData().getResponse().get(0).getDes_mensaje());
                    isCanceled = true;
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

              if(isCanceled){
                  NavigationUtil.openActivityWithStringParam(getActivity(), VacacionesActivity.class,
                          BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS);
                  getActivity().finish();
              }
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


    private void showAlertDialogDeletePeriods() {
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {
                cancelHolidayRequest();
                IScheduleOptions.showEliminatedOption(false,"");
                dialogFragmentDeletePeriods.close();
            }

            @Override
            public void onCancel() {

                dialogFragmentDeletePeriods.close();

            }
        });
        dialogFragmentDeletePeriods.setVisibleCancelButton(VISIBLE);
        dialogFragmentDeletePeriods.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void cancelHolidayRequest(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_GTE);
        String numSuplente = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_SUPLENTE);

        HolidayRequestData holidayRequestData = new HolidayRequestData(CANCEL_HOLIDAYS, 5,numEmployer);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_suplente(Integer.parseInt(numSuplente));
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();
        periodsToCancel.add(new HolidayPeriodFolio(holidayPeriod.getIdu_folio()));
        holidayRequestData.setPeriodsToCancel(periodsToCancel);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);

    }


}
