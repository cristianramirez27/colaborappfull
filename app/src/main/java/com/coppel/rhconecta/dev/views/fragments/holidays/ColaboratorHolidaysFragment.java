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
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.ConfigurationHolidaysData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeDetalleActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaborator;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableRightArrowHeader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;
//import com.wdullaer.materialdatetimepicker.date.DatePickerHolidayDialog;
//import com.wdullaer.materialdatepicker.date.DatePickerHolidayDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CONSULTA_VACACIONES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_COLABORATOR_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorHolidaysFragment extends Fragment implements  View.OnClickListener, DialogFragmentWarning.OnOptionClick,
        IServicesContract.View,HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener
      {

    public static final String TAG = ColaboratorHolidaysFragment.class.getSimpleName();
    private AppCompatActivity parent;
          private DialogFragmentWarning dialogFragmentWarning;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaborator headerHoliday;

    private IScheduleOptions IScheduleOptions;
    private VacacionesActivity vacacionesActivity;



    private HolidaysPeriodsResponse holidaysPeriodsResponse;


    @BindView(R.id.btnSchedule)
    Button btnSchedule;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestRecyclerAdapter holidayRequestRecyclerAdapter;
    private long mLastClickTime = 0;

          private boolean EXPIRED_SESSION;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions= (IScheduleOptions)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
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
        }

        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        btnSchedule.setOnClickListener(this);
        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList, IScheduleOptions,false);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData  holidayRequestData = new HolidayRequestData(CONSULTA_VACACIONES, 2,numEmployer);
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

                if(holidaysPeriodsResponse != null && holidaysPeriodsResponse.getData().getResponse().getClv_mensaje() == 1 &&
                        !holidaysPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                    showWarningDialog(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());

                    return;
                }

                openCalendar();

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
                if(response.getResponse() instanceof HolidaysPeriodsResponse) {
                    holidaysPeriodsResponse = (HolidaysPeriodsResponse) response.getResponse();


                    headerHoliday.setDetailData(holidaysPeriodsResponse);
                    if(holidaysPeriodsResponse.getData().getResponse().getPeriodos().size() > 0){

                        holidayPeriodList.clear();
                        for (HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                            holidayPeriodList.add(period);
                        }

                        holidayRequestRecyclerAdapter.notifyDataSetChanged();
                    }
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
          datePickerDialog.setNum_diasagendados(holidaysPeriodsResponse.getData().getResponse().getNum_diasagendados());
          datePickerDialog.setNum_total_vacaciones(holidaysPeriodsResponse.getData().getResponse().getNum_totalvacaciones());
          datePickerDialog.setShowHalfDaysOption(holidaysPeriodsResponse.getData().getResponse().getClv_mediodia() == 1 ? true : false);
          datePickerDialog.setDes_mensaje(holidaysPeriodsResponse.getData().getResponse().getDes_mensaje());
          Calendar today = Calendar.getInstance();
          datePickerDialog.setMinDate( today);
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

                  vacacionesActivity.onEvent(BUNDLE_OPTION_COLABORATOR_SCHEDULE,configurationHolidaysData);

                 /* NavigationUtil.openActivityParamsSerializable(getActivity(), VacacionesActivity.class,
                          BUNDLE_OPTION_DATA_HOLIDAYS, configurationHolidaysData,
                          BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_COLABORATOR_SCHEDULE);*/
              }


              @Override
              public void onInvalidMaxSelectedDays(String msg) {

                  showWarningDialog(msg);
              }
          };

      }
