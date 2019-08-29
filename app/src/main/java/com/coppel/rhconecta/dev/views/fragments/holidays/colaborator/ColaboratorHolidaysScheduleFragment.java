package com.coppel.rhconecta.dev.views.fragments.holidays.colaborator;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ConfigurationHolidaysData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaySendPeriodsResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaborator;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_EMAIL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.capitalizeText;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidays;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidaysSchedule;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDayNameFromDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorHolidaysScheduleFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener ,DialogFragmentGetDocument.OnButtonClickListener{

    public static final String TAG = ColaboratorHolidaysScheduleFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
    private DialogFragmentWarning dialogFragmentWarning;
    @BindView(R.id.totalSolicitados)
    TextViewDetail totalSolicitados;
    @BindView(R.id.diasPorAgendar)
    TextViewDetail diasPorAgendar;
    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaborator headerHoliday;
    @BindView(R.id.btnSchedule)
    Button btnSchedule;
    @BindView(R.id.btnSendRequest)
    Button btnSendRequest;
    private boolean sendRequestSuccess;
    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private ConfigurationHolidaysData configurationHolidaysData;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    double daysToSchedule = 0.0;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestRecyclerAdapter holidayRequestRecyclerAdapter;

    private Map<String, List<DaySelectedHoliday>> periods;

    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private DialogFragmentGetDocument dialogFragmentGetDocument;

    private VacacionesActivity vacacionesActivity;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorHolidaysScheduleFragment getInstance(ConfigurationHolidaysData configurationHolidaysData){
        ColaboratorHolidaysScheduleFragment fragment = new ColaboratorHolidaysScheduleFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_HOLIDAYS,configurationHolidaysData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configurationHolidaysData = (ConfigurationHolidaysData) getArguments().getSerializable(BUNDLE_OPTION_DATA_HOLIDAYS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_agendar, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }

        btnSchedule.setOnClickListener(this);
       btnSendRequest.setOnClickListener(this);
        holidaysPeriodsResponse = configurationHolidaysData.getHolidaysPeriodsResponse();
        periods = configurationHolidaysData.getDaysConfiguration();

        headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList,IScheduleOptions,true,false);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);
        setValuesPeriods(configurationHolidaysData.getTotalDays());


        IScheduleOptions.setActionEliminatedOption(new Command() {
            @Override
            public void execute(Object... params) {
                //Accion al eliminar un periodo


                List<HolidayPeriod> periodsSelected =holidayRequestRecyclerAdapter.getDataItemsSelected();
                showAlertDialogDeletePeriods(periodsSelected);


            }
        });

        return view;
    }


    private void setValuesPeriods(double totalDays){

        holidayPeriodList.clear();
        for (String key : periods.keySet()){
            List<DaySelectedHoliday> daysInPeriod = periods.get(key);
            String dateStart = String.format("%s-%s-%s",
                    String.valueOf(daysInPeriod.get(0).getDay() > 9 ? daysInPeriod.get(0).getDay() : "0"+ daysInPeriod.get(0).getDay()),
                    String.valueOf(daysInPeriod.get(0).getMonth() + 1 > 9 ? daysInPeriod.get(0).getMonth() +1 : "0"+ (daysInPeriod.get(0).getMonth()+1)),
                    String.valueOf(daysInPeriod.get(0).getYear()));

            dateStart =  String.format("%s, %s",capitalizeText(getContext(),getDayNameFromDate(dateStart)),dateStart);

            int indexEndDate = 0;
            if(daysInPeriod.size() > 1){
                indexEndDate = daysInPeriod.size() - 1 ;
            }

            String dateEnd = String.format("%s-%s-%s",
                    String.valueOf(daysInPeriod.get(indexEndDate).getDay() > 9 ? daysInPeriod.get(indexEndDate).getDay() : "0"+ daysInPeriod.get(indexEndDate).getDay()),
                    String.valueOf(daysInPeriod.get(indexEndDate).getMonth() + 1 > 9 ? daysInPeriod.get(indexEndDate).getMonth() + 1 : "0"+ (daysInPeriod.get(indexEndDate).getMonth() +1 )),
                    String.valueOf(daysInPeriod.get(indexEndDate).getYear()));

            dateEnd =  String.format("%s, %s",capitalizeText(getContext(),getDayNameFromDate(dateEnd)),dateEnd);


            String numDays = daysInPeriod.size() > 1 ? String.valueOf(daysInPeriod.size()) :
                    (daysInPeriod.size() == 1 && daysInPeriod.get(0).isHalfDay() ? "0.5" : String.valueOf(daysInPeriod.size()));

            holidayPeriodList.add(new HolidayPeriod(key,numDays,dateStart,dateEnd));
        }

        holidayRequestRecyclerAdapter.notifyDataSetChanged();

        totalSolicitados.setTextsSize(14,18);


        String totalDaysAsString = String.valueOf(totalDays);
        if(totalDays % 1 == 0){
            totalDaysAsString = totalDaysAsString.substring(0,totalDaysAsString.indexOf("."));
            totalDaysAsString = String.valueOf(Integer.parseInt(totalDaysAsString));
        }

        totalSolicitados.setTexts(getString(R.string.title_total_request_days), String.format("%s días", totalDaysAsString));
        totalSolicitados.setStartTextColor(getResources().getColor(R.color.colorBackgroundCoppelNegro));
        totalSolicitados.setEndTextColor(getResources().getColor(R.color.colorBackgroundCoppelNegro));

        daysToSchedule =  holidaysPeriodsResponse.getData().getResponse().getNum_diasvacaciones() - totalDays;
        diasPorAgendar.setTextsSize(14,14);
        diasPorAgendar.hideDivider();

        String daysToScheduleAsString = String.valueOf(daysToSchedule);
        if(daysToSchedule % 1 == 0){
            daysToScheduleAsString = daysToScheduleAsString.substring(0,daysToScheduleAsString.indexOf("."));
            daysToScheduleAsString = String.valueOf(Integer.parseInt(daysToScheduleAsString));
        }

        diasPorAgendar.setTexts(getString(R.string.title_days_to_schedule), String.format("%s días", daysToScheduleAsString));
        diasPorAgendar.setStartTextColor(getResources().getColor(R.color.disable_text_color));
        diasPorAgendar.setEndTextColor(getResources().getColor(R.color.disable_text_color));

        if(periods.size() == 0){
            btnSendRequest.setEnabled(false);
            btnSendRequest.setBackgroundResource(R.drawable.backgroud_rounder_grey);
        }else {
            btnSendRequest.setEnabled(true);
            btnSendRequest.setBackgroundResource(R.drawable.backgroud_rounder_green);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
       // coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
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

                if(holidaysPeriodsResponse != null && holidaysPeriodsResponse.getData().getResponse().getClv_mensaje() == 1 &&
                        !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                    showWarningDialog("",holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());

                    return;
                }

                openCalendar();

                break;

            case R.id.btnSendRequest:
                sendRequest();
                break;

        }
    }

    private void sendRequest(){
       /* List<HolidayPeriod> periodsList = holidayRequestRecyclerAdapter.getAllItems();
        if(periodsList.size() > 0){
            sendRequestHolidays(periodsList);
        }else {*/
            sendRequestHolidays(holidayRequestRecyclerAdapter.getAllItems());
       // }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof HolidaySendPeriodsResponse){
                    HolidaySendPeriodsResponse sendPeriodsResponse = (HolidaySendPeriodsResponse) response.getResponse();
                    if(sendPeriodsResponse.getData().getResponse().get(0).getClv_estado() == 1){
                        sendRequestSuccess = true;
                        showSuccessDialog(MSG_HOLIDAYS_OK,sendPeriodsResponse.getData().getResponse().get(0).getDes_mensaje(),
                                sendPeriodsResponse.getData().getResponse().get(0).getDes_otromensaje());
                    }else {
                        showWarningDialog("",sendPeriodsResponse.getData().getResponse().get(0).getDes_mensaje());
                    }


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

            if(configurationHolidaysData.isColaborator()){
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }else {
                //Se valida si se ingreso como colaborador
                NavigationUtil.openActivityWithStringParam(getActivity(), VacacionesActivity.class,
                        BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS);
                getActivity().finish();
            }

            //vacacionesActivity.onEvent(,null);
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

          }


    private void openCalendar(){
        DatePickerHolidayDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(dateSetListenerStart);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(holidaysPeriodsResponse.getData().getResponse().getDes_marca() != null ?
                holidaysPeriodsResponse.getData().getResponse().getDes_marca() : "");
        //TODO Validar que esta suma sea correcta
        double holidayDaysTotal = holidaysPeriodsResponse.getData().getResponse().getNum_adicionales() +
                holidaysPeriodsResponse.getData().getResponse().getNum_decision() +
                holidaysPeriodsResponse.getData().getResponse().getNum_decisionanterior();

        datePickerDialog.setNum_diasagendados(holidaysPeriodsResponse.getData().getResponse().getNum_diasagendados());
        datePickerDialog.setNum_total_vacaciones(holidayDaysTotal);
        double limitDay = holidaysPeriodsResponse.getData().getResponse().getNum_totalvacaciones() - holidaysPeriodsResponse.getData().getResponse().getNum_diasagendados();
        datePickerDialog.setLimite_dias(limitDay);

        datePickerDialog.setShowHalfDaysOption(holidaysPeriodsResponse.getData().getResponse().getClv_mediodia() == 1 ? true : false);
        datePickerDialog.setDes_mensaje(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
        Calendar today = Calendar.getInstance();
        datePickerDialog.setMinDate( today);
        //Setear el maximo de 18 meses para seleccionar periodos
        today.add(Calendar.MONTH,18);
        datePickerDialog.setMaxDate(today);

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
            setValuesPeriods(totalDays);
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
            getActivity().onBackPressed();
        }
    }

    private void showAlertDialogDeletePeriods(List<HolidayPeriod> periodsSelected) {
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {

                for(HolidayPeriod period : periodsSelected){
                    periods.remove(period.getIdPeriod());
                }

                double totalDays= 0.0;
                for(String key : periods.keySet()){
                    List<DaySelectedHoliday> daysInPeriod = periods.get(key);
                    for(DaySelectedHoliday day : daysInPeriod){
                        totalDays+= day.isHalfDay() ? 0.5 : 1;
                    }
                }

                setValuesPeriods(totalDays);
                IScheduleOptions.showEliminatedOption(false,"");
                dialogFragmentDeletePeriods.close();

                IScheduleOptions.showTitle(true);
                IScheduleOptions.showEliminatedOption(false,"");

                showSuccessDialog(MSG_HOLIDAYS_OK,getString(R.string.msg_holiday_request_deleted),"");


            }

            @Override
            public void onCancel() {

                dialogFragmentDeletePeriods.close();

            }
        });

        dialogFragmentDeletePeriods.setTitle("Atención");
        dialogFragmentDeletePeriods.setMsg("¿Quieres eliminar estas fechas?");
        dialogFragmentDeletePeriods.setVisibleCancelButton(VISIBLE);
        dialogFragmentDeletePeriods.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void sendRequestHolidays(List<HolidayPeriod> periodsSelected){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_GTE);
        String numSuplente = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_SUPLENTE);

        HolidayRequestData holidayRequestData = new HolidayRequestData(SEND_HOLIDAY_REQUEST, 3,numEmployer);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_suplente(Integer.parseInt(numSuplente));
        List<HolidayPeriodData> periodsToSend = new ArrayList<>();

        for(HolidayPeriod period : periodsSelected){
            String fechaInicio = getDateFormatToHolidaysSchedule(period.getFec_ini().split(",")[1].trim(),false);
            String fechaFin =  getDateFormatToHolidaysSchedule(period.getFec_fin().split(",")[1].trim(),false);
            periodsToSend.add(new HolidayPeriodData(Double.parseDouble(period.getNum_dias()),fechaInicio,fechaFin));
        }

        holidayRequestData.setPeriodos(periodsToSend);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

}
