package com.coppel.rhconecta.dev.views.fragments.holidays;


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
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
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
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaboratorGte;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
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
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIODS_COLABORATORS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_HOLIDAY_REQUEST;
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
public class ColaboratorRequestHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View, DialogFragmentWarning.OnOptionClick,HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener {

    public static final String TAG = ColaboratorRequestHolidaysFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentWarning dialogFragmentWarning;
    @BindView(R.id.totalSolicitados)
    TextViewDetail totalSolicitados;
    @BindView(R.id.diasPorAgendar)
    TextViewDetail diasPorAgendar;
    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaboratorGte headerHoliday;
    @BindView(R.id.showCalendar)
    TextView showCalendar;


    @BindView(R.id.btnSchedule)
    Button btnSchedule;


    private boolean sendRequestSuccess;


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
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }

        btnSchedule.setOnClickListener(this);
        showCalendar.setOnClickListener(this);

        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList,IScheduleOptions,false);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);
        //setValuesPeriods(configurationHolidaysData.getTotalDays());


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


    private void setValuesPeriods(){

        holidayPeriodList.clear();
        for (String key : periods.keySet()){
            List<DaySelectedHoliday> daysInPeriod = periods.get(key);
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

            holidayPeriodList.add(new HolidayPeriod(key,numDays,dateStart,dateEnd));
        }

        holidayRequestRecyclerAdapter.notifyDataSetChanged();


    }

    private void setTotales(double totalDays){
        totalSolicitados.setTextsSize(14,18);
        totalSolicitados.setTexts(getString(R.string.title_total_request_days), String.format("%s días", totalDays));
        totalSolicitados.setStartTextColor(getResources().getColor(R.color.colorBackgroundCoppelNegro));
        totalSolicitados.setEndTextColor(getResources().getColor(R.color.colorBackgroundCoppelNegro));

        daysToSchedule =  holidaysPeriodsResponse.getData().getResponse().getNum_diasvacaciones() - totalDays;
        diasPorAgendar.setTextsSize(14,14);
        diasPorAgendar.hideDivider();
        diasPorAgendar.setTexts(getString(R.string.title_days_to_schedule), String.format("%s días", daysToSchedule));
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

                openCalendar();

                break;

        }
    }

    private void sendRequest(){
        List<HolidayPeriod> periodsList = holidayRequestRecyclerAdapter.getDataItemsSelected();
        if(periodsList.size() > 0){
            sendRequestHolidays(periodsList);
        }else {
            sendRequestHolidays(holidayRequestRecyclerAdapter.getAllItems());
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
                    holidaysPeriodsResponse = (HolidaysPeriodsResponse) response.getResponse();
                    headerHoliday.setDetailData(this.colaboratorHoliday,holidaysPeriodsResponse);
                    if(holidaysPeriodsResponse.getData().getResponse().getPeriodos().size() > 0){
                       periods = new HashMap<>();
                        holidayPeriodList.clear();
                        for (HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                            holidayPeriodList.add(period);
                        }
                        holidayRequestRecyclerAdapter.notifyDataSetChanged();
                    }

                    setTotales(0);

                } else if(response.getResponse() instanceof HolidaySendPeriodsResponse){
                    HolidaySendPeriodsResponse sendPeriodsResponse = (HolidaySendPeriodsResponse) response.getResponse();
                    if(sendPeriodsResponse.getData().getResponse().get(0).getClv_estado() == 1){
                        sendRequestSuccess = true;
                        showWarningDialog(sendPeriodsResponse.getData().getResponse().get(0).getDes_mensaje(),
                                sendPeriodsResponse.getData().getResponse().get(0).getDes_otromensaje());
                    }else {
                        showWarningDialog("",sendPeriodsResponse.getData().getResponse().get(0).getDes_mensaje());
                    }


                }
                break;
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


    private void openCalendar(){

       /*
        DatePickerHolidayDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(dateSetListenerStart);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(holidaysPeriodsResponse.getData().getResponse().getDes_marca() != null ?
                holidaysPeriodsResponse.getData().getResponse().getDes_marca() : "");
        datePickerDialog.setNum_diasagendados(holidaysPeriodsResponse.getData().getResponse().getNum_diasagendados());
        datePickerDialog.setNum_total_vacaciones(holidaysPeriodsResponse.getData().getResponse().getNum_totalvacaciones());
        datePickerDialog.setShowHalfDaysOption(holidaysPeriodsResponse.getData().getResponse().getClv_mediodia() == 1 ? true : false);
        datePickerDialog.setDes_mensaje(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
        Calendar today = Calendar.getInstance();
        datePickerDialog.setMinDate( today);

        datePickerDialog.setInitDaysSelectedHolidays(periods);
        datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerHolidayDialog");
*/
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
                  setValuesPeriods();
                  setTotales(totalDays);
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
            if(sendRequestSuccess){
                NavigationUtil.openActivityWithStringParam(getActivity(), VacacionesActivity.class,
                        BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS);
                getActivity().finish();
                //vacacionesActivity.onEvent(,null);
            }
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

                setValuesPeriods();
                setTotales(totalDays);
                IScheduleOptions.showEliminatedOption(false,"");

                dialogFragmentDeletePeriods.close();

                showWarningDialog("",getString(R.string.msg_holiday_request_deleted));

            }

            @Override
            public void onCancel() {

                dialogFragmentDeletePeriods.close();

            }
        });
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
            String fechaInicio = period.getFec_ini();
            fechaInicio = fechaInicio.replace("-","").trim();
            String fechaFin = period.getFec_fin();
            fechaFin = fechaFin.replace("-","").trim();
            periodsToSend.add(new HolidayPeriodData(Double.parseDouble(period.getNum_dias()),fechaInicio,fechaFin));
        }

        holidayRequestData.setPeriodos(periodsToSend);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

}
