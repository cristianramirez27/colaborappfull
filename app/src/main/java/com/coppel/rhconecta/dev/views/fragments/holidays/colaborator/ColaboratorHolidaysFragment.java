package com.coppel.rhconecta.dev.views.fragments.holidays.colaborator;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.ConfigurationHolidaysData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodFolio;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysCancelResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
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
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;
//import com.wdullaer.materialdatetimepicker.date.DatePickerHolidayDialog;
//import com.wdullaer.materialdatepicker.date.DatePickerHolidayDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CONSULTA_VACACIONES;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_COLABORATOR_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorHolidaysFragment extends Fragment implements  View.OnClickListener, DialogFragmentWarning.OnOptionClick,
        IServicesContract.View,HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener,DialogFragmentGetDocument.OnButtonClickListener
      {

    public static final String TAG = ColaboratorHolidaysFragment.class.getSimpleName();
    private AppCompatActivity parent;

    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaborator headerHoliday;
    @BindView(R.id.vacacionesAgendadas)
    TextViewDetail vacacionesAgendadas;
    @BindView(R.id.btnSchedule)
    Button btnSchedule;
    @BindView(R.id.tituloSolicitudes)
    TextView tituloSolicitudes;


    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentWarning dialogFragmentWarning;
    private IScheduleOptions IScheduleOptions;
    private VacacionesActivity vacacionesActivity;
    private boolean isCanceled;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;
    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestRecyclerAdapter holidayRequestRecyclerAdapter;
    private long mLastClickTime = 0;
    private boolean EXPIRED_SESSION;

    private ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions= (IScheduleOptions)getActivity();
        if(getActivity() instanceof VacacionesActivity){
            vacacionesActivity = (VacacionesActivity)getActivity();
        }else if(getActivity() instanceof HomeActivity){
            ISurveyNotification = (HomeActivity)getActivity();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }else if(getActivity() instanceof HomeActivity){
            parent = (HomeActivity) getActivity();
            ( (HomeActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }

        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        btnSchedule.setOnClickListener(this);
        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList, IScheduleOptions,false,true);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);

        IScheduleOptions.setActionEliminatedOption(new Command() {
            @Override
            public void execute(Object... params) {
                //Accion al eliminar un periodo
                showAlertDialogDeletePeriods( holidayRequestRecyclerAdapter.getDataItemsSelected());
            }
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHolidaysPeriods();
    }

    private void getHolidaysPeriods(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData  holidayRequestData = new HolidayRequestData(CONSULTA_VACACIONES, 2,numEmployer);
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

                if(holidaysPeriodsResponse != null && holidaysPeriodsResponse.getData().getResponse().getClv_mensaje() == 1 &&
                        !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                    showWarningDialog(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
                    return;
                }

                holidayRequestRecyclerAdapter.unCheckedAll();
                openCalendar();

                IScheduleOptions.showEliminatedOption(false,"");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 989 && resultCode == RESULT_OK){
            getHolidaysPeriods();
        }

    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof HolidaysPeriodsResponse) {
                    holidaysPeriodsResponse = (HolidaysPeriodsResponse) response.getResponse();
                    headerHoliday.setDetailData(holidaysPeriodsResponse);
                    if(holidaysPeriodsResponse.getData().getResponse().getPeriodos().size() > 0){
                        holidayPeriodList.clear();
                        tituloSolicitudes.setVisibility(VISIBLE);
                        for (HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                            holidayPeriodList.add(period);
                        }
                        holidayRequestRecyclerAdapter.notifyDataSetChanged();
                    }else {

                        tituloSolicitudes.setVisibility(View.INVISIBLE);
                    }

                    if(holidaysPeriodsResponse.getData().getResponse().getNum_diasautorizados() > 0){
                        String daysSchedule = String.valueOf(holidaysPeriodsResponse.getData().getResponse().getNum_diasautorizados());
                        if(holidaysPeriodsResponse.getData().getResponse().getNum_diasautorizados() % 1 == 0){
                            daysSchedule = daysSchedule.substring(0,daysSchedule.indexOf("."));
                            daysSchedule = String.valueOf(Integer.parseInt(daysSchedule));
                        }
                        vacacionesAgendadas.setTexts("Vacaciones agendadas",String.format("%s %s",
                                String.valueOf(daysSchedule),
                                holidaysPeriodsResponse.getData().getResponse().getNum_diasautorizados() > 1 ? "días" : "día"));
                        vacacionesAgendadas.hideDivider();
                        vacacionesAgendadas.setStartTextColor(getResources().getColor(R.color.disable_text_color));
                        vacacionesAgendadas.setEndTextColor(getResources().getColor(R.color.colorTextGrayDark));
                        vacacionesAgendadas.setTextsSize(12,12);
                        vacacionesAgendadas.setVisibility(VISIBLE);
                    }else {
                        vacacionesAgendadas.setVisibility(GONE);
                    }

                    layoutContainer.setVisibility(VISIBLE);

                } else if(response.getResponse() instanceof HolidaysCancelResponse) {
                    HolidaysCancelResponse responseDetail = (HolidaysCancelResponse)response.getResponse();
                    showSuccessDialog(MSG_HOLIDAYS_OK,responseDetail.getData().getResponse().get(0).getDes_mensaje(), "");
                    isCanceled = true;
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
              if(isCanceled){
                  getHolidaysPeriods();
              }

              dialogFragmentGetDocument.close();
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
        //getActivity().onBackPressed();

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
        IScheduleOptions.showEliminatedOption(false,"");
        if(vacacionesActivity != null){
            vacacionesActivity.onEvent(BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL,holidayPeriod);
        }else {
            NavigationUtil.openActivityParamsSerializable(getActivity(), VacacionesActivity.class,
                    BUNDLE_OPTION_DATA_HOLIDAYS,holidayPeriod,
                    BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL);
        }

        /*  NavigationUtil.openActivityWithStringParam(getActivity(), VacacionesActivity.class,
                     BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL);
        */
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
        today.add(Calendar.DAY_OF_MONTH,1);
        datePickerDialog.setMinDate(today);



        //Setear el maximo de 18 meses para seleccionar periodos
        today.add(Calendar.MONTH,18);
        datePickerDialog.setMaxDate(today);

        datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerHolidayDialog");
      }


      DatePickerHolidayDialog.OnDateSetListener dateSetListenerStart = new DatePickerHolidayDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePickerHolidayDialog view, int year, int month, int dayOfMonth) {

        }

        @Override
        public void onDatesSelectedHolidays(Map<String, List<DaySelectedHoliday>> periods, double totalDays) {
            ConfigurationHolidaysData configurationHolidaysData = new ConfigurationHolidaysData();
            configurationHolidaysData.setHolidaysPeriodsResponse(holidaysPeriodsResponse);
            configurationHolidaysData.setDaysConfiguration(periods);
            configurationHolidaysData.setTotalDays(totalDays);

            if(vacacionesActivity != null){
                vacacionesActivity.onEvent(BUNDLE_OPTION_COLABORATOR_SCHEDULE,configurationHolidaysData);

            }else{

                configurationHolidaysData.setColaborator(true);
                NavigationUtil.openActivityParamsSerializableRequestCode(getActivity(), VacacionesActivity.class,
                        BUNDLE_OPTION_DATA_HOLIDAYS,configurationHolidaysData,
                        BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_COLABORATOR_SCHEDULE,989);
            }

                 /* NavigationUtil.openActivityParamsSerializable(getActivity(), VacacionesActivity.class,
                          BUNDLE_OPTION_DATA_HOLIDAYS, configurationHolidaysData,
                          BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_COLABORATOR_SCHEDULE);*/
        }


        @Override
        public void onInvalidMaxSelectedDays(String msg) {
            showWarningDialog(msg);
        }
    };

    private void showAlertDialogDeletePeriods( List<HolidayPeriod> periods) {
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {
                cancelHolidayRequest(periods);
                IScheduleOptions.showEliminatedOption(false,"");
                IScheduleOptions.showTitle(true);
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


    private void cancelHolidayRequest(List<HolidayPeriod> periodsSelected){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_GTE);
        String numSuplente = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_SUPLENTE);

        HolidayRequestData holidayRequestData = new HolidayRequestData(CANCEL_HOLIDAYS, 5,numEmployer);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_suplente(Integer.parseInt(numSuplente));
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();
        for(HolidayPeriod holidayPeriod : periodsSelected){
            periodsToCancel.add(new HolidayPeriodFolio(holidayPeriod.getIdu_folio()));
        }

        holidayRequestData.setPeriodsChangeStatus(periodsToCancel);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
        }

      }
