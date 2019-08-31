package com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CalendarProposedData;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaySchedulePeriodsResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.models.SpliceSelectedVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaboratorGte;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAuthorizeHoliday;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.shrikanthravi.collapsiblecalendarview.widget.CommandSplice;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

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
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_CALENDAR_DAYS_PROPOSED;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SCHEDULE_GTE_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_PERIODS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.formatMonthNameFormat;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorCalendarGralHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentGetDocument.OnButtonClickListener,DialogFragmentAuthorizeHoliday.OnButonOptionObservationClick  {

    public static final String TAG = ColaboratorCalendarGralHolidaysFragment.class.getSimpleName();

    public static final String BUNDLE_OPTION_DATA_COLABORATOR = "BUNDLE_OPTION_DATA_COLABORATOR";
    public static final String BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS = "BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS";
    private AppCompatActivity parent;


    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentWarning dialogFragmentWarning;

    @BindView(R.id.headerColaborator)
    HeaderHolidaysColaboratorGte headerView;

    @BindView(R.id.iconList)
    ImageView iconList;
    @BindView(R.id.iconCalendarTool)
    ImageView iconCalendarTool;
    @BindView(R.id.layoutList)
    LinearLayout layoutList;
    @BindView(R.id.layoutCalendar)
    LinearLayout layoutCalendar;
    @BindView(R.id.collapsibleCalendarView)
    CollapsibleCalendar collapsibleCalendar;
    @BindView(R.id.btnSchedule)
    Button btnSchedule;

    @BindView(R.id.monthName)
    TextView monthName;
    @BindView(R.id.lastmonth)
    ImageView lastmonth;
    @BindView(R.id.nextMonth)
    ImageView nextMonth;
    @BindView(R.id.layoutOptions)
    RelativeLayout layoutOptions;



    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private ColaboratorHoliday colaboratorHoliday;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestRecyclerAdapter holidayRequestRecyclerAdapter;
    private VacacionesActivity vacacionesActivity;
    private List<Day> dayListSelected;


    private boolean sendRequestSuccess;
    private Map<String, List<DaySelectedHoliday>> periods;
    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorCalendarGralHolidaysFragment getInstance(CalendarProposedData data){
        ColaboratorCalendarGralHolidaysFragment fragment = new ColaboratorCalendarGralHolidaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_COLABORATOR,data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.colaboratorHoliday = ((CalendarProposedData) getArguments().getSerializable(BUNDLE_OPTION_DATA_COLABORATOR)).getColaborator();
        this.dayListSelected = ((CalendarProposedData) getArguments().getSerializable(BUNDLE_OPTION_DATA_COLABORATOR)).getListDaySelected();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_calendario_gral_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        iconList.setOnClickListener(this);
        iconCalendarTool.setOnClickListener(this);
        lastmonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);

        iconList.setImageResource(R.drawable.ic_calendar_list);
        iconCalendarTool.setImageResource(R.drawable.ic_icn_calendar_disable);

        btnSchedule.setOnClickListener(this);
        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_holidays_colaborator_calendar_gral));
        }
        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        //headerView.setDetailData(this.colaboratorHoliday,true);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList,IScheduleOptions,false,true);
        holidayRequestRecyclerAdapter.setHideCheckBox(true);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);
        //setValuesPeriods(configurationHolidaysData.getTotalDays());
        //Inicializar Calendario
        //To hide or show expand icon
        initializeCalendar();

        return view;
    }

    private void initializeCalendar(){
        Calendar today=new GregorianCalendar();
        today.add(Calendar.DATE,1);
        collapsibleCalendar.setExpandIconVisible(true);
        collapsibleCalendar.expand(100);
        collapsibleCalendar.setVisibilityExpandIcon(View.INVISIBLE);
        collapsibleCalendar.setVisibilityBtnNext(View.INVISIBLE);
        collapsibleCalendar.setVisibilityBtnPrev(View.INVISIBLE);
        collapsibleCalendar.setExpandIconVisible(false);
        collapsibleCalendar.setMultipleDays(true);
        collapsibleCalendar.setEnable(true);
        collapsibleCalendar.setSpliceActionEnable(true);
        collapsibleCalendar.setTitleMonthVisible(false);
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSingle(getResources().getDrawable(R.drawable.circle_green_solid_background));
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSplice(getResources().getDrawable(R.drawable.circle_melon_solid_background));
        collapsibleCalendar.setActionSplice(new CommandSplice() {
            @Override
            public void action(Day daySelected) {
                SpliceSelectedVO data = new SpliceSelectedVO( holidaysPeriodsResponse.getData().getResponse().getPeriodos(),daySelected);
                data.setAddSpliceMarks(true);
                ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR,data);

            }
        });

        formatMonthNameFormat(collapsibleCalendar.getMonthCurrentTitle(),monthName);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPeriodsColaborators(this.colaboratorHoliday.getNum_empleado());
    }

    private void getPeriodsColaborators(String numEmployer){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //Este colaborador es el gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_CALENDAR_DAYS_PROPOSED, 18);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_empconsulta(numEmployer);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);

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

            case R.id.iconCalendarTool:
                switchView(true);
                break;
            case R.id.btnSchedule:
                /**Si el campo clv_mensaje es 1 mostrar des_mensaje en un mensaje informativo.**/
                if(holidaysPeriodsResponse != null && holidaysPeriodsResponse.getData().getResponse().getClv_mensaje() == 1 &&
                        !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                    showWarningDialog("",holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
                    return;
                }

                openCalendar();
                break;
            case R.id.lastmonth:
                changeMonth(false);
                break;
            case R.id.nextMonth:
                changeMonth(true);
                break;
        }
    }

    private void changeMonth(boolean isNext){
        if(isNext){
            collapsibleCalendar.nextMonth();
        }else {
            collapsibleCalendar.prevMonth();
        }

        formatMonthNameFormat(collapsibleCalendar.getMonthCurrentTitle(),monthName);
    }

    private void openCalendar(){
        DatePickerHolidayDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(dateSetListenerStart);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(holidaysPeriodsResponse.getData().getResponse().getDes_marca() != null
                && !holidaysPeriodsResponse.getData().getResponse().getDes_marca().isEmpty() ?
                holidaysPeriodsResponse.getData().getResponse().getDes_marca() : "");

        datePickerDialog.setDes_mensaje(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje()!= null &&
                !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty() ?
                holidaysPeriodsResponse.getData().getResponse().getDes_mensaje() : getString(R.string.msg_no_days_available));
        datePickerDialog.setNum_diasagendados(holidaysPeriodsResponse.getData().getResponse().getNum_diasagendados());

        double holidayDaysTotal = holidaysPeriodsResponse.getData().getResponse().getNum_adicionales()
                + holidaysPeriodsResponse.getData().getResponse().getNum_decision()
                + holidaysPeriodsResponse.getData().getResponse().getNum_decisionanterior();

        datePickerDialog.setNum_total_vacaciones(holidayDaysTotal);

        //datePickerDialog.setNum_total_vacaciones(holidaysPeriodsResponse.getData().getResponse().getNum_totalvacaciones());

        double limitDay = holidaysPeriodsResponse.getData().getResponse().getNum_diasvacaciones();
        datePickerDialog.setLimite_dias(limitDay);
        datePickerDialog.setShowHalfDaysOption(holidaysPeriodsResponse.getData().getResponse().getClv_mediodia() == 1 ? true : false);
        datePickerDialog.setDes_mensaje(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
        Calendar dateMin = Calendar.getInstance();
        Calendar dateMax = Calendar.getInstance();
        /**Se permiten días anteriores por ser Gte**/
        dateMin.set(Calendar.MONTH,0);
        dateMin.set(Calendar.DAY_OF_MONTH,1);
        datePickerDialog.setMinDate(dateMin);
        //Setear el maximo de 18 meses para seleccionar periodos
        dateMax.add(Calendar.MONTH,18);
        datePickerDialog.setMaxDate(dateMax);
        datePickerDialog.setInitDaysSelectedHolidays(periods);
        datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerHolidayDialog");
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
            //Recalculamos los totales
            proccessPeriods(periodsUpdate);
            //setTotales(totalDays);
        }

        @Override
        public void onInvalidMaxSelectedDays(String msg) {
            showWarningDialog("",msg);
        }
    };

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

        openObservationsAuthorize(holidayPeriodSchedule);
    }

    @Override
    public void onLeftOptionObservationClick() {

    }

    @Override
    public void onRightOptionObservationClick(List<HolidayPeriod> holidayPeriodSchedule, String observations, DialogFragment dialogFragment) {
        sendRequestHolidays(holidayPeriodSchedule,observations);
        dialogFragment.dismiss();
    }

    private void sendRequestHolidays(List<HolidayPeriod> periodsSelected,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //El usuario en sesion es el Gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(SCHEDULE_GTE_HOLIDAY_REQUEST, 14,this.colaboratorHoliday.getNum_empleado());
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_observaciones(observations);
        List<HolidayPeriodData> periodsToSend = new ArrayList<>();

        for(HolidayPeriod period : periodsSelected){
            String fechaInicio = getDateFormatToHolidays(period.getFec_ini(),true);
            String fechaFin =  getDateFormatToHolidays(period.getFec_fin(),true);
            periodsToSend.add(new HolidayPeriodData(Double.parseDouble(period.getNum_dias()),fechaInicio,fechaFin));
        }

        holidayRequestData.setPeriodos(periodsToSend);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }


    private void openObservationsAuthorize(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setTitle("Observaciones (Opcional)");
        authorizeHoliday.setDescription("Escribe el motivo por el cual agenda las vacaciones al colaborador");
        authorizeHoliday.setHint("Ingresa tus observaciones aquí");
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }


    private void switchView(boolean showCalendar){
        if(showCalendar){
            layoutOptions.setVisibility(VISIBLE);
            iconCalendarTool.setImageResource(R.drawable.ic_icn_calendar);
            iconList.setImageResource(R.drawable.ic_calendar_list_disable);
            layoutCalendar.setVisibility(VISIBLE);
            layoutList.setVisibility(GONE);
        }else {
            layoutOptions.setVisibility(GONE);
            iconCalendarTool.setImageResource(R.drawable.ic_icn_calendar_disable);
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
                if(response.getResponse() instanceof HolidaysPeriodsResponse) {
                    holidaysPeriodsResponse = (HolidaysPeriodsResponse)response.getResponse();
                    periods = new HashMap<>();

                    holidayPeriodList.clear();
                    for(HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                        holidayPeriodList.add(period);
                    }
                    holidayRequestRecyclerAdapter.notifyDataSetChanged();
                    headerView.setDetailData(this.colaboratorHoliday,holidaysPeriodsResponse);
                    setColaboratorMarkInCalendar(holidayPeriodList);

                    layoutContainer.setVisibility(VISIBLE);
                } else if(response.getResponse() instanceof HolidaySchedulePeriodsResponse){
                    HolidaySchedulePeriodsResponse schedulePeriodsResponse = (HolidaySchedulePeriodsResponse) response.getResponse();
                    //  if(schedulePeriodsResponse.getData().getResponse().getClv_estado() == 1){
                    sendRequestSuccess = true;
                    showSuccessDialog(MSG_HOLIDAYS_OK,schedulePeriodsResponse.getData().getResponse().getDes_mensaje(),"");
                    // }
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
        if(sendRequestSuccess){
            getPeriodsColaborators(this.colaboratorHoliday.getNum_empleado());
        }

        dialogFragmentGetDocument.close();
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
              ColaboratorHoliday colaboratorHoliday = new ColaboratorHoliday(holidayPeriod.getNom_empleado(),
                      holidayPeriod.getFotoperfil(),
                      holidayPeriod.getNum_empleado());


              CalendarProposedData calendarProposedData = new CalendarProposedData(holidaysPeriodsResponse,holidayPeriod);

              calendarProposedData.setListDaySelected( this.dayListSelected);

              ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR_PERIODS,calendarProposedData);
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
            getActivity().onBackPressed();
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
        dateParts = dateStart.split(",")[1].trim().split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.set(year,month,day);
        //Obtenemos la fecha final
        dateParts = dateEnd.split(",")[1].trim().split("-");
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
                    String.valueOf(calendar.get(Calendar.MONTH) > 0 ? calendar.get(Calendar.MONTH) : "0"+calendar.get(Calendar.MONTH)),  String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            //Si ya esta en el map, lo marcamos como con empalme
            if(daysInCalendar.containsKey(dateAsString)){
                daysInCalendar.get(dateAsString).setHasSplice(period.getIdu_marca() == 1 ? period.getIdu_marca() : -1 );
            }else {
                Day dayToAdd = new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
                        period.getIdu_marca() == 1 ? period.getIdu_marca() : -1 );
                daysInCalendar.put(dateAsString,dayToAdd);
            }
            calendar.add(Calendar.DATE,1);
        }while (!calendar.after(calendar_Fin));

    }

}
