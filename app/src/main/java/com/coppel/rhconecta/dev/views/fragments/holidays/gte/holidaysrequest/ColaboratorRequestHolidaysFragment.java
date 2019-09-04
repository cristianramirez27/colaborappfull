package com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest;


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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IDialogControlKeboard;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CalendarProposedData;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayChangeStatusResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodFolio;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaySchedulePeriodsResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaboratorGte;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAuthorizeHoliday;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.AUTHORIZE_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_GTE_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIODS_COLABORATORS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SCHEDULE_GTE_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_WARNING;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_PROPOSED;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorRequestHolidaysFragment extends Fragment implements  View.OnClickListener,
        HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentAuthorizeHoliday.OnButonOptionObservationClick,
        DialogFragmentGetDocument.OnButtonClickListener, IDialogControlKeboard {

    public static final String TAG = ColaboratorRequestHolidaysFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;

    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentWarning dialogFragmentWarning;
    @BindView(R.id.totalSolicitados)
    TextViewDetail totalSolicitados;
    @BindView(R.id.diasPorAgendar)
    TextViewDetail diasPorAgendar;
    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaboratorGte headerHoliday;
    @BindView(R.id.layoutShowCalendar)
    RelativeLayout layoutShowCalendar;
    @BindView(R.id.showCalendar)
    TextView showCalendar;
    @BindView(R.id.btnSchedule)
    Button btnSchedule;

    private int optionSelected;

    private boolean updateContent;


    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private ColaboratorHoliday colaboratorHoliday;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    double daysToSchedule = 0.0;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestRecyclerAdapter holidayRequestRecyclerAdapter;

    private Map<String, List<DaySelectedHoliday>> periods;

    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;

    private VacacionesActivity vacacionesActivity;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorRequestHolidaysFragment getInstance(ColaboratorHoliday colaboratorHoliday){
        ColaboratorRequestHolidaysFragment fragment = new ColaboratorRequestHolidaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_HOLIDAYS,colaboratorHoliday);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.colaboratorHoliday = (ColaboratorHoliday) getArguments().getSerializable(BUNDLE_OPTION_DATA_HOLIDAYS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_solicitudes_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_colaborator_request));
        }

        btnSchedule.setOnClickListener(this);
        layoutShowCalendar.setOnClickListener(this);

        SpannableString content = new SpannableString(getString(R.string.title_calendar_proposed));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        showCalendar.setText(content);

        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList,IScheduleOptions,false,false);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        holidayRequestRecyclerAdapter.setLayoutItem(R.layout.item_solicitud_vacaciones_gte);
        holidayRequestRecyclerAdapter.setChangeStyleCheckbox(true);

        holidayRequestRecyclerAdapter.setGte(true);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);
        //setValuesPeriods(configurationHolidaysData.getTotalDays());
        holidayRequestRecyclerAdapter.setICalendarView(ICalendarView);
        ICalendarView.enableCalendarOption(false);
        IScheduleOptions.setActionEliminatedOption(new Command() {
            @Override
            public void execute(Object... params) {
                //Accion al eliminar un periodo
                showAlertDialogChangeStatusPeriods(2);
                hideOptionToolbar();
            }
        });


        IScheduleOptions.setActionAuthorizeOption(new Command() {
            @Override
            public void execute(Object... params) {
                //Accion al eliminar un periodo
                showAlertDialogChangeStatusPeriods(3);
                hideOptionToolbar();
            }
        });

        return view;
    }


    private void hideOptionToolbar(){
        IScheduleOptions.showTitle(true);
        IScheduleOptions.showAuthorizeOption(false);
        IScheduleOptions.showEliminatedOption(false,"");
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
        optionSelected = 1;
        openObservationsSchedule(holidayPeriodSchedule);

    }

    private void setTotales(){
        double totalDays  = 0.0;

       for(HolidayPeriod period : holidayPeriodList){
           totalDays+= Double.parseDouble(period.getNum_dias().split(" ")[0]);
       }


        String totalDaysAsString = String.valueOf(totalDays);
        if(totalDays % 1 == 0){
            totalDaysAsString = totalDaysAsString.substring(0,totalDaysAsString.indexOf("."));
            totalDaysAsString = String.valueOf(Integer.parseInt(totalDaysAsString));
        }

        totalSolicitados.setTextsSize(14,18);
        totalSolicitados.setTexts(getString(R.string.title_total_request_days), String.format("%s días", totalDaysAsString));
        totalSolicitados.setStartTextColor(getResources().getColor(R.color.colorBackgroundCoppelNegro));
        totalSolicitados.setEndTextColor(getResources().getColor(R.color.colorBackgroundCoppelNegro));

        daysToSchedule =  holidaysPeriodsResponse.getData().getResponse().getNum_diasvacaciones() - totalDays;

        String daysToScheduleAsString = String.valueOf(daysToSchedule);
        if(daysToSchedule % 1 == 0){
            daysToScheduleAsString = daysToScheduleAsString.substring(0,daysToScheduleAsString.indexOf("."));
            daysToScheduleAsString = String.valueOf(Integer.parseInt(daysToScheduleAsString));
        }


        diasPorAgendar.setTextsSize(14,14);
        diasPorAgendar.hideDivider();
        diasPorAgendar.setTexts(getString(R.string.title_days_to_schedule), String.format("%s días", daysToScheduleAsString));
        diasPorAgendar.setStartTextColor(getResources().getColor(R.color.disable_text_color));
        diasPorAgendar.setEndTextColor(getResources().getColor(R.color.disable_text_color));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPeriods(this.colaboratorHoliday.getNum_empleado());

    }

    private void getPeriods(String numEmployer ){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //Este colaborador es el gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_PERIODS_COLABORATORS, 8,numEmployer);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                holidayRequestRecyclerAdapter.unCheckedAll();
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

                /**Si el campo clv_mensaje es 1 mostrar des_mensaje en un mensaje informativo.**/
                if(holidaysPeriodsResponse != null && holidaysPeriodsResponse.getData().getResponse().getClv_mensaje() == 1 &&
                        !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                    showWarningDialog("",holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
                    return;
                }


                IScheduleOptions.showEliminatedOption(false,"");
                IScheduleOptions.showAuthorizeOption(false);
                IScheduleOptions.showTitle(true);
                holidayRequestRecyclerAdapter.unCheckedAll();
                openCalendar();

                break;
            case R.id.layoutShowCalendar:
                openCalendarDaysProposed();

                break;

        }
    }

    private void openCalendarDaysProposed(){
        IScheduleOptions.showEliminatedOption(false,"");
        IScheduleOptions.showAuthorizeOption(false);
        IScheduleOptions.showTitle(true);
        HolidayPeriod period = holidayRequestRecyclerAdapter.getDataItemsSelected().get(0);
        CalendarProposedData calendarProposedData = new CalendarProposedData(this.colaboratorHoliday,period);
        ((VacacionesActivity)parent).onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR_PROPOSED,calendarProposedData);
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
                    holidaysPeriodsResponse = (HolidaysPeriodsResponse) response.getResponse();
                    headerHoliday.setDetailData(this.colaboratorHoliday,holidaysPeriodsResponse);
                    periods = new HashMap<>();
                   // if(holidaysPeriodsResponse.getData().getResponse().getPeriodos().size() > 0){
                        holidayPeriodList.clear();
                        for (HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                            holidayPeriodList.add(period);
                        }
                        holidayRequestRecyclerAdapter.notifyDataSetChanged();
                    //}

                    setTotales();

                    layoutContainer.setVisibility(VISIBLE);

                } else if(response.getResponse() instanceof HolidaySchedulePeriodsResponse){
                    HolidaySchedulePeriodsResponse schedulePeriodsResponse = (HolidaySchedulePeriodsResponse) response.getResponse();
                  //  if(schedulePeriodsResponse.getData().getResponse().getClv_estado() == 1){
                    updateContent = true;
                    showSuccessDialog(MSG_HOLIDAYS_OK,schedulePeriodsResponse.getData().getResponse().getDes_mensaje(),"");
                   // }
                }else if(response.getResponse() instanceof HolidayChangeStatusResponse){
                    HolidayChangeStatusResponse changeStatusResponse = (HolidayChangeStatusResponse)response.getResponse();
                    if(changeStatusResponse.getData().getResponse().getClv_estado() == 1){
                        showSuccessDialog(MSG_HOLIDAYS_OK,changeStatusResponse.getData().getResponse().getDes_mensaje(),"");
                    }
                    updateContent = true;
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

        if(updateContent){
            getPeriods(this.colaboratorHoliday.getNum_empleado());
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


    private void openCalendar(){

        DatePickerHolidayDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(dateSetListenerStart);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(holidaysPeriodsResponse.getData().getResponse().getDes_marca() != null &&
                !holidaysPeriodsResponse.getData().getResponse().getDes_marca().isEmpty()?
                holidaysPeriodsResponse.getData().getResponse().getDes_marca() : "");
        datePickerDialog.setDes_mensaje(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje()!= null &&
              !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty() ?
                holidaysPeriodsResponse.getData().getResponse().getDes_mensaje() : getString(R.string.msg_no_days_available));
        datePickerDialog.setNum_diasagendados(holidaysPeriodsResponse.getData().getResponse().getNum_diasagendados());
        datePickerDialog.setNum_total_vacaciones(holidaysPeriodsResponse.getData().getResponse().getNum_totalvacaciones());

        double limitDay = holidaysPeriodsResponse.getData().getResponse().getNum_diasvacaciones();

        //TODO Validar que esta suma sea correcta
        double holidayDaysTotal = holidaysPeriodsResponse.getData().getResponse().getNum_adicionales()
                + holidaysPeriodsResponse.getData().getResponse().getNum_decision()
                + holidaysPeriodsResponse.getData().getResponse().getNum_decisionanterior();
        String numHolidays = String.valueOf(holidayDaysTotal);
        if(holidayDaysTotal % 1 == 0){
            numHolidays = numHolidays.substring(0,numHolidays.indexOf("."));
            numHolidays = String.valueOf(Integer.parseInt(numHolidays));
        }


        datePickerDialog.setNum_total_vacaciones(holidayDaysTotal);

        datePickerDialog.setLimite_dias(holidayDaysTotal/*limitDay*/);

        datePickerDialog.setShowHalfDaysOption(holidaysPeriodsResponse.getData().getResponse().getClv_mediodia() == 1 ? true : false);
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
            //getActivity().onBackPressed();
        }
    }

    private void showAlertDialogChangeStatusPeriods(int option) {

        optionSelected = option;
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {
                if(option == 2){
                    cancelHoliday();
                }else if(option == 3){
                    authorizeHoliday();
                }

                dialogFragmentDeletePeriods.close();

            }

            @Override
            public void onCancel() {

                holidayRequestRecyclerAdapter.unCheckedAll();
                dialogFragmentDeletePeriods.close();

            }
        });

        dialogFragmentDeletePeriods.setTitle("Atención");
        dialogFragmentDeletePeriods.setMsg(option == 2 ?  "¿Quieres rechazar las vacaciones?" :"¿Quieres autorizar las vacaciones?");
        dialogFragmentDeletePeriods.setVisibleCancelButton(VISIBLE);
        dialogFragmentDeletePeriods.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void cancelHoliday(){
        openObservationsCancel(holidayRequestRecyclerAdapter.getDataItemsSelected());
    }

    private void authorizeHoliday(){
        authorizePeriod(holidayRequestRecyclerAdapter.getDataItemsSelected());
    }


    private void openObservationsSchedule(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setTitle("Observaciones (Opcional)");
        authorizeHoliday.setDescription("Escribe el motivo por el cual agenda las vacaciones al colaborador");
        authorizeHoliday.setHint("Ingresa tus observaciones aquí");
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }

    private void openObservationsCancel(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setIDialogControlKeboard(this);
        authorizeHoliday.setTitle("Justificar rechazo");
        authorizeHoliday.setDescription("Escribe el motivo de rechazo");
        authorizeHoliday.setHint("Ingresa tus razones aquí");
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }

    @Override
    public void onLeftOptionObservationClick() {
        holidayRequestRecyclerAdapter.unCheckedAll();
    }

    @Override
    public void onRightOptionObservationClick(List<HolidayPeriod> holidayPeriodSchedule,String observations, DialogFragment dialogFragment) {
        if(optionSelected == 1){
            sendRequestHolidays(holidayPeriodSchedule,observations);
        }else if(optionSelected == 2){
            cancelPeriod(holidayPeriodSchedule,observations);
        }

        optionSelected = 0;
        dialogFragment.dismiss();
    }

    private void sendRequestHolidays(List<HolidayPeriod> periodsSelected,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
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


    public interface ICalendarView{
       void enableCalendarOption(boolean enable);
    }

    ICalendarView ICalendarView = new ICalendarView(){

        @Override
        public void enableCalendarOption(boolean enable) {
            layoutShowCalendar.setEnabled(enable);
        }
    };

    @Override
    public void onRequestSelectedClick(HolidayPeriod holidayPeriod) {

    }

    private void cancelPeriod(List<HolidayPeriod> holidayPeriodSchedule,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //Se toma el numero del colaborador en sesion como gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(CANCEL_GTE_HOLIDAY, 9,this.colaboratorHoliday.getNum_empleado());
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_comentario(observations);
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();

        for(HolidayPeriod period : holidayPeriodSchedule){
            periodsToCancel.add(new HolidayPeriodFolio(period.getIdu_folio()));
        }

        holidayRequestData.setPeriodsChangeStatus(periodsToCancel);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    private void authorizePeriod(List<HolidayPeriod> holidayPeriodSchedule){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //Se toma el numero del colaborador en sesion como gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(AUTHORIZE_HOLIDAY, 10,this.colaboratorHoliday.getNum_empleado());
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_comentario("");
        holidayRequestData.setIdu_autorizo(1);
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();

        for(HolidayPeriod period : holidayPeriodSchedule){
            periodsToCancel.add(new HolidayPeriodFolio(period.getIdu_folio()));
        }

        holidayRequestData.setPeriodsChangeStatus(periodsToCancel);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    @Override
    public void showKeyboard(boolean show, View view) {

        if(!show) {
            //getActivity().onBackPressed();
            view.requestFocus();
            DeviceManager.hideKeyBoard(getActivity());
        }
    }
}
