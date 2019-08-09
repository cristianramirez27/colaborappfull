package com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayChangeStatusResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodFolio;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestColaboratorsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAuthorizeHoliday;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_GTE_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.EDIT_PERIOD_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SCHEDULE_GTE_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.formatMonthNameFormat;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorCalendarGralPeriodsHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentAuthorizeHoliday.OnButonOptionObservationClick,DialogFragmentGetDocument.OnButtonClickListener{
    public static final String TAG = ColaboratorCalendarGralPeriodsHolidaysFragment.class.getSimpleName();

    public static final String BUNDLE_OPTION_DATA_COLABORATOR = "BUNDLE_OPTION_DATA_COLABORATOR";
    public static final String BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS = "BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS";
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentWarning dialogFragmentWarning;


    /*Header*/
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

    @BindView(R.id.iconList)
    ImageView iconList;
    @BindView(R.id.iconCalendar)
    ImageView iconCalendar;
    @BindView(R.id.layoutList)
    LinearLayout layoutList;
    @BindView(R.id.layoutCalendar)
    LinearLayout layoutCalendar;
    @BindView(R.id.collapsibleCalendarView)
    CollapsibleCalendar collapsibleCalendar;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.monthName)
    TextView monthName;

    private int action;
    private Map<String, List<DaySelectedHoliday>> periods;

    private boolean changeStatusPeriod;
    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private HolidayPeriod holidayPeriod;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestColaboratorsRecyclerAdapter holidayRequestRecyclerAdapter;
    private VacacionesActivity vacacionesActivity;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorCalendarGralPeriodsHolidaysFragment getInstance(HolidayPeriod data){
        ColaboratorCalendarGralPeriodsHolidaysFragment fragment = new ColaboratorCalendarGralPeriodsHolidaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_COLABORATOR,data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.holidayPeriod = (HolidayPeriod) getArguments().getSerializable(BUNDLE_OPTION_DATA_COLABORATOR);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_calendario_gral_periods, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        iconList.setOnClickListener(this);
        iconCalendar.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        periods = new HashMap<>();
        iconList.setImageResource(R.drawable.ic_calendar_list);
        iconCalendar.setImageResource(R.drawable.ic_icn_calendar_disable);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_period_holidays));
        }
        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        //headerView.setDetailData(this.colaboratorHoliday,true);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestColaboratorsRecyclerAdapter(holidayPeriodList,IScheduleOptions);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);

        initializeCalendar();
        setDataPeriod();



        expObservaciones.setText("Observaciones");
        expObservaciones.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                observacionesStateChange(expObservaciones,layoutObservaciones);
            }
        });


        return view;
    }

    private void initializeCalendar(){
        //setValuesPeriods(configurationHolidaysData.getTotalDays());
        //Inicializar Calendario
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
        collapsibleCalendar.setTitleMonthVisible(false);
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSingle(getResources().getDrawable(R.drawable.circle_green_solid_background));
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSplice(getResources().getDrawable(R.drawable.circle_melon_solid_background));


        formatMonthNameFormat(collapsibleCalendar.getMonthCurrentTitle(),monthName);
    }

    private void setDataPeriod(){
        setDataHeader();
        holidayPeriodList.clear();
       /* for(HolidayPeriod period : this.holidayPeriod.getVer_marca()){
            holidayPeriodList.add(period);
        }*/
        holidayRequestRecyclerAdapter.notifyDataSetChanged();
        holidayPeriodList.add(this.holidayPeriod);

        this.holidayPeriod.setFec_ini(this.holidayPeriod.getFec_ini().split(",")[1].trim());
        this.holidayPeriod.setFec_fin(this.holidayPeriod.getFec_fin().split(",")[1].trim());
        setColaboratorMarkInCalendar(holidayPeriodList);
    }

    private void setDataHeader(){

        fechaInicio.setText(this.holidayPeriod.getFec_ini());
        fechaFin.setText(this.holidayPeriod.getFec_fin());

        double numDaysQuantity = Double.parseDouble(this.holidayPeriod.getNum_dias().split(" ")[0]);
        String numDayASString= String.valueOf(numDaysQuantity);
        if(numDaysQuantity % 1 == 0){
            numDayASString = numDayASString.substring(0,numDayASString.indexOf("."));
            numDayASString = String.valueOf(Integer.parseInt(numDayASString));
        }

        diasVacaciones.setText(String.format("%s %s",numDayASString, numDaysQuantity > 1 ? "días" : "día"));

        expObservaciones.setColorText(getContext().getResources().getColor(R.color.colorTextGrayDark));
        estatus.setText(this.holidayPeriod.getNom_estatus());
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(this.holidayPeriod.getColor()));
        gd.setCornerRadius(40);
        estatus.setBackgroundDrawable(gd);
        /*nombreGte.setText(this.holidayPeriod.getNom_gerente());
        fechaRechazo.setText(this.holidayPeriod.getFec_estatus());
        motivoRechazo.setText(this.holidayPeriod.getDes_comentario());*/
    }



    private void observacionesStateChange(ExpandableSimpleTitle expandable, LinearLayout layoutToExpand) {
        if (expandable.isExpanded()) {
            layoutToExpand.setVisibility(VISIBLE);
        } else {
            layoutToExpand.setVisibility(View.GONE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

            case R.id.iconList:
                switchView(false);
                break;


            case R.id.iconCalendar:
                switchView(true);
                break;

            case R.id.btnCancel:
                showAlertDialogCancel();
                break;

            case R.id.btnEdit:

                openCalendar();

                break;
        }
    }


    private void showAlertDialogCancel() {
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {
                cancelHoliday();
                dialogFragmentDeletePeriods.close();
            }

            @Override
            public void onCancel() {
                dialogFragmentDeletePeriods.close();
            }
        });
        dialogFragmentDeletePeriods.setVisibleCancelButton(VISIBLE);
        dialogFragmentDeletePeriods.setTitle("Atención");
        dialogFragmentDeletePeriods.setMsg("¿Quieres cancelar las vacaciones?");
        dialogFragmentDeletePeriods.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void cancelHoliday(){
        List<HolidayPeriod> data = new ArrayList<>();
        data.add( this.holidayPeriod);
        this.action = 1;
        openObservationsCancel(data);
    }

    private void editHoliday(List<HolidayPeriod> holidayPeriodSchedule){
        List<HolidayPeriod> data = new ArrayList<>();
        HolidayPeriod periodUpdated = new HolidayPeriod();
        periodUpdated.setIdu_folio(this.holidayPeriod.getIdu_folio());
        periodUpdated.setFec_ini(holidayPeriodSchedule.get(0).getFec_ini());
        periodUpdated.setFec_fin(holidayPeriodSchedule.get(0).getFec_fin());
        periodUpdated.setNum_dias(holidayPeriodSchedule.get(0).getNum_dias());
        data.add(periodUpdated);
        openObservationsEdit(data);
    }

    //TODO Validar
    private void openCalendar(){
        DatePickerHolidayDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(dateSetListenerStart);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(getString(R.string.title_calendar_periods));
        datePickerDialog.setNum_diasagendados(0);
        datePickerDialog.setNum_total_vacaciones(0);
        double limitDay = 10;
        datePickerDialog.setLimite_dias(limitDay);
        datePickerDialog.setShowHalfDaysOption(true);//TODO Validar
        datePickerDialog.setDes_mensaje("");
        Calendar today = Calendar.getInstance();
        datePickerDialog.setMinDate( today);
        //Setear el maximo de 18 meses para seleccionar periodos
        today.add(Calendar.MONTH,18);
        datePickerDialog.setMaxDate(today);
        datePickerDialog.setInitDaysSelectedHolidays(datePickerDialog.getPeriods(getDaysSelectedSource()));
        datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerHolidayDialog");
    }


    private Map<Long, DaySelectedHoliday> getDaysSelectedSource(){
        Map<Long, DaySelectedHoliday> selectionSorted = new HashMap<>();
        double numberDays = Double.parseDouble(this.holidayPeriod.getNum_dias().split(" ")[0]);
        String dateStart = this.holidayPeriod.getFec_ini();
        String dateEnd = this.holidayPeriod.getFec_fin();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime start = formatter.parseDateTime(dateStart);
        DateTime end = formatter.parseDateTime(dateEnd);

        if(numberDays < 1){ 
            Long IdPeriodHalf  = Long.parseLong(String.format("%s%s%s",String.valueOf(start.getYear()),
                    String.valueOf(start.getMonthOfYear()-1 > 9 ? start.getMonthOfYear()-1 : "0"+ (start.getMonthOfYear()-1)),
                    String.valueOf(start.getDayOfMonth() > 9 ? start.getDayOfMonth() : "0"+start.getDayOfMonth())));

            selectionSorted.put(IdPeriodHalf,new DaySelectedHoliday(String.valueOf(IdPeriodHalf),start.getDayOfMonth(),start.getMonthOfYear()-1,start.getYear(),true));
        }else {

            do{

                Long IdPeriod  = Long.parseLong(String.format("%s%s%s",String.valueOf(start.getYear()),
                        String.valueOf(start.getMonthOfYear()-1 > 9 ? start.getMonthOfYear()-1 : "0"+(start.getMonthOfYear()-1)),
                        String.valueOf(start.getDayOfMonth() > 9 ? start.getDayOfMonth() : "0"+start.getDayOfMonth())));

                selectionSorted.put(IdPeriod,new DaySelectedHoliday(String.valueOf(IdPeriod),start.getDayOfMonth(),
                        start.getMonthOfYear()-1,start.getYear(),false));

                start = start.plusDays(1);

            }while (!start.isAfter(end));
        }


        return selectionSorted;
    }

    DatePickerHolidayDialog.OnDateSetListener dateSetListenerStart = new DatePickerHolidayDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePickerHolidayDialog view, int year, int month, int dayOfMonth) {
        }

        @Override
        public void onDatesSelectedHolidays(Map<String, List<DaySelectedHoliday>> periodsUpdate, double totalDays) {
            periods.clear();
            for (String key : periodsUpdate.keySet()){
                periods.put(key,periodsUpdate.get(key));
            }
            proccessPeriods(periods);
        }

        private void proccessPeriods(Map<String, List<DaySelectedHoliday>> periodsUpdate){

            List<HolidayPeriod> holidayPeriodSchedule = new ArrayList<>();
            for (String key : periodsUpdate.keySet()){
                List<DaySelectedHoliday> daysInPeriod = periodsUpdate.get(key);
                String dateStart = String.format("%s-%s-%s",
                        String.valueOf(daysInPeriod.get(0).getYear()),
                        String.valueOf(daysInPeriod.get(0).getMonth() > 10 ? daysInPeriod.get(0).getMonth() : "0"+ daysInPeriod.get(0).getMonth()),
                        String.valueOf(daysInPeriod.get(0).getDay() > 10 ? daysInPeriod.get(0).getDay() : "0"+ daysInPeriod.get(0).getDay()));

                int indexEndDate = 0;
                if(daysInPeriod.size() > 1){
                    indexEndDate = daysInPeriod.size() - 1 ;
                }

                String dateEnd = String.format("%s-%s-%s",
                        String.valueOf(daysInPeriod.get(indexEndDate).getYear()),
                        String.valueOf(daysInPeriod.get(indexEndDate).getMonth() > 10 ? daysInPeriod.get(indexEndDate).getMonth() : "0"+ daysInPeriod.get(indexEndDate).getMonth()),
                        String.valueOf(daysInPeriod.get(indexEndDate).getDay() > 10 ? daysInPeriod.get(indexEndDate).getDay() : "0"+ daysInPeriod.get(indexEndDate).getDay()));

                String numDays = daysInPeriod.size() > 1 ? String.valueOf(daysInPeriod.size()) :
                        (daysInPeriod.size() == 1 && daysInPeriod.get(0).isHalfDay() ? "0.5" : String.valueOf(daysInPeriod.size()));

                holidayPeriodSchedule.add(new HolidayPeriod(key,numDays,dateStart,dateEnd));
            }

            //TODO Validar que solo sea un periodo
            action = 2;
            editHoliday(holidayPeriodSchedule);
        }


        @Override
        public void onInvalidMaxSelectedDays(String msg) {

            showWarningDialog("",msg);
        }
    };





    private void switchView(boolean showCalendar){
        if(showCalendar){
            iconCalendar.setImageResource(R.drawable.ic_icn_calendar);
            iconList.setImageResource(R.drawable.ic_calendar_list_disable);
            layoutCalendar.setVisibility(VISIBLE);
            layoutList.setVisibility(GONE);
        }else {
            iconCalendar.setImageResource(R.drawable.ic_icn_calendar_disable);
            iconList.setImageResource(R.drawable.ic_calendar_list);
            layoutCalendar.setVisibility(GONE);
            layoutList.setVisibility(View.VISIBLE);
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
                if(response.getResponse() instanceof HolidayChangeStatusResponse){
                    HolidayChangeStatusResponse changeStatusResponse = (HolidayChangeStatusResponse)response.getResponse();
                    if(changeStatusResponse.getData().getResponse().getClv_estado() == 1){
                        showSuccessDialog(MSG_HOLIDAYS_OK,changeStatusResponse.getData().getResponse().getDes_mensaje(), "");
                    }
                    changeStatusPeriod = true;
                }
                break;
        }
    }

    private void showSuccessDialog(int type,String title,String content) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setContentText(title);
        dialogFragmentGetDocument.setMsgText(content);
        dialogFragmentGetDocument.setType(type, parent);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }


    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {

        dialogFragmentGetDocument.close();
        if(changeStatusPeriod){
            ColaboratorHoliday colaboratorHoliday = new ColaboratorHoliday(this.holidayPeriod.getNom_empleado(),
                    this.holidayPeriod.getFotoperfil(),
                    this.holidayPeriod.getNum_empleado());

            // ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR,colaboratorHoliday);
            NavigationUtil.openActivityParamsSerializable(getActivity(), VacacionesActivity.class,
                    BUNDLE_OPTION_DATA_HOLIDAYS,colaboratorHoliday,
                    BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR);
            getActivity().finish();
            //vacacionesActivity.onEvent(,null);
        }

    }


    @Override
    public void showError(ServicesError coppelServicesError) {
        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.HOLIDAYS:
                    showWarningDialog("",coppelServicesError.getMessage());
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog("",getString(R.string.expired_session));
                    break;
            }

        }
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


          @Override
          public void onRequestSelectedClick(HolidayPeriod holidayPeriod) {

          }


          private void showWarningDialog(String title,String message) {
              if(title.isEmpty())
                  title = getString(R.string.attention);

              dialogFragmentWarning = new DialogFragmentWarning();
              dialogFragmentWarning.setSinlgeOptionData(title, message, getString(R.string.accept));
              dialogFragmentWarning.setOnOptionClick(this);
              dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
          }

    @Override
    public void onLeftOptionClick() {

    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        }else {
            dialogFragmentWarning.close();
        }
    }

    /**Setear los dias en el calednario***/
    private void setColaboratorMarkInCalendar(List<HolidayPeriod> periods){

        HashMap<String,Day> daysInCalendar = new HashMap<>();
        List<Day>  listDaySelected = new ArrayList<>();
        for(HolidayPeriod period : periods){
            setSelectedDays(period,daysInCalendar);
        }
        //Llenamos la lista con los dias con y sin empalmes
        for(String dateAsString : daysInCalendar.keySet()){
            listDaySelected.add(daysInCalendar.get(dateAsString));
        }

        collapsibleCalendar.select(listDaySelected);
    }

    private void setSelectedDays(HolidayPeriod period,HashMap<String,Day> daysInCalendar){

        String dateStart = period.getFec_ini();
        String dateEnd = period.getFec_fin();

        String[] dateParts = null;
        int year;
        int month;
        int day;

        dateParts = dateStart.split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.set(year,month,day);
        //Obtenemos la fecha final
        dateParts = dateEnd.split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Fin = Calendar.getInstance();
        calendar_Fin.set(year,month,day);

        Calendar  calendar = Calendar.getInstance();
        calendar.set(calendar_Ini.get(Calendar.YEAR),calendar_Ini.get(Calendar.MONTH),calendar_Ini.get(Calendar.DAY_OF_MONTH));

        CalendarAdapter adapter = new CalendarAdapter(getActivity(), calendar);
        collapsibleCalendar.setAdapter(adapter);


        do{
            String dateAsString = String.format("%s%s%s",String.valueOf(calendar.get(Calendar.YEAR)),
                    String.valueOf(calendar.get(Calendar.MONTH)),  String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            //Si ya esta en el map, lo marcamos como con empalme
            if(daysInCalendar.containsKey(dateAsString)){
                daysInCalendar.get(dateAsString).setHasSplice(1);
            }else {
                Day dayToAdd = new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), 0);
                daysInCalendar.put(dateAsString,dayToAdd);
            }


            calendar.add(Calendar.DATE,1);

        }while (!calendar.after(calendar_Fin));
    }


    private void openObservationsCancel(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.setTitle("Justificar Rechazo");
        authorizeHoliday.setDescription("Escribe el motivo de rechazo");
        authorizeHoliday.setHint("Ingresa tus razones aquí");
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }


    private void openObservationsEdit(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.setTitle("Observaciones (Opcional)");
        authorizeHoliday.setDescription("Escribe el motivo por el cual modifica las vacaciones del colaborador");
        authorizeHoliday.setHint("Ingresa tus observaciones aquí");
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }

    @Override
    public void onLeftOptionObservationClick() {

    }

    @Override
    public void onRightOptionObservationClick(List<HolidayPeriod> holidayPeriodSchedule, String observations, DialogFragment dialogFragment) {
        if(action == 1){
            cancelPeriod(holidayPeriodSchedule,observations);
        }else if(action == 2){
            editPeriod(holidayPeriodSchedule,observations);
        }

        dialogFragment.dismiss();
    }

    private void cancelPeriod(List<HolidayPeriod> holidayPeriodSchedule,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(CANCEL_GTE_HOLIDAY, 9,this.holidayPeriod.getNum_empleado());
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_comentario(observations);
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();

        for(HolidayPeriod period : holidayPeriodSchedule){
            periodsToCancel.add(new HolidayPeriodFolio(period.getIdu_folio()));
        }

        holidayRequestData.setPeriodsChangeStatus(periodsToCancel);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }


    private void editPeriod(List<HolidayPeriod> holidayPeriodSchedule,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(EDIT_PERIOD_HOLIDAY, 12,this.holidayPeriod.getNum_empleado());
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_observaciones(observations);
        List<HolidayPeriodData> periodsToEdit = new ArrayList<>();

        for(HolidayPeriod period : holidayPeriodSchedule){
            periodsToEdit.add(new HolidayPeriodData(Double.parseDouble(period.getNum_dias()),period.getFec_ini(),period.getFec_fin(),period.getIdu_folio()));
        }

        holidayRequestData.setPeriodos(periodsToEdit);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }
}
