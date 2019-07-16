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
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableRightArrowHeader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
//import com.wdullaer.materialdatetimepicker.date.DatePickerHolidayDialog;
//import com.wdullaer.materialdatepicker.date.DatePickerHolidayDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CONSULTA_VACACIONES;
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

    @BindView(R.id.titleDetail)
    TextViewExpandableRightArrowHeader titleDetail;

    @BindView(R.id.layoutDetail)
    LinearLayout layoutDetail;


    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    @BindView(R.id.diasDecision)
    TextViewDetail diasDecision;
    @BindView(R.id.diasPendientesAnterior)
    TextViewDetail diasPendientesAnterior;
    @BindView(R.id.diasAdicionalesPendientes)
    TextViewDetail diasAdicionalesPendientes;
    @BindView(R.id.diasAdicionalesRegistrados)
    TextViewDetail diasAdicionalesRegistrados;
    @BindView(R.id.fechaPrimaVacacional)
    TextViewDetail fechaPrimaVacacional;

    @BindView(R.id.btnSchedule)
    Button btnSchedule;


    private ColaboratorRequestsListExpensesResponse.Months monthSelected;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestRecyclerAdapter holidayRequestRecyclerAdapter;
    private long mLastClickTime = 0;

          private boolean EXPIRED_SESSION;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof HomeActivity){
            parent = (HomeActivity) getActivity();
            ( (HomeActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }

        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        btnSchedule.setOnClickListener(this);

        titleDetail.setOnExpandableListener(new TextViewExpandableRightArrowHeader.OnExpandableListener() {
            @Override
            public void onClick() {
                if (titleDetail.isExpanded()) {
                    layoutDetail.setVisibility(View.VISIBLE);
                } else {
                    layoutDetail.setVisibility(View.GONE);
                }
            }
        });


        initValues();

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestRecyclerAdapter(holidayPeriodList);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);


        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);

        return view;
    }

    private void initValues(){
        titleDetail.setTitleTextSize(17);
        titleDetail.setValueTextSize(20);
        titleDetail.setTexts(getString(R.string.title_holidays_days),String.format("%s %s","10",getString(R.string.title_days)));

        diasDecision.setSingleLine(true);
        diasDecision.setGuideline73(0.70f);
        diasDecision.setTexts(getString(R.string.title_day_availables),"8.5 dias");
        diasDecision.setTextsSize(12,12);
        diasDecision.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasDecision.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasPendientesAnterior.setGuideline73(0.70f);
        diasPendientesAnterior.setSingleLine(true);
        diasPendientesAnterior.setTexts(getString(R.string.title_days_pending_lastyear),"1 día");
        diasPendientesAnterior.setTextsSize(12,12);
        diasPendientesAnterior.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasPendientesAnterior.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasAdicionalesPendientes.setGuideline73(0.70f);
        diasAdicionalesPendientes.setSingleLine(true);
        diasAdicionalesPendientes.setTexts(getString(R.string.title_days_aditionals),"0 días");
        diasAdicionalesPendientes.setTextsSize(12,12);
        diasAdicionalesPendientes.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasAdicionalesPendientes.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        diasAdicionalesRegistrados.setGuideline73(0.70f);
        diasAdicionalesRegistrados.setSingleLine(true);
        diasAdicionalesRegistrados.setTexts(getString(R.string.title_days_aditionals_register),"0 días");
        diasAdicionalesRegistrados.setTextsSize(12,12);
        diasAdicionalesRegistrados.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        diasAdicionalesRegistrados.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

        fechaPrimaVacacional.setGuideline73(0.70f);
        fechaPrimaVacacional.setSingleLine(true);
        fechaPrimaVacacional.setTexts(getString(R.string.title_bonus_date),"Viernes, 30-07-2019");
        fechaPrimaVacacional.setTextsSize(12,12);
        fechaPrimaVacacional.setStartTextColor(getContext().getResources().getColor(R.color.disable_text_color));
        fechaPrimaVacacional.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));

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
                if(response.getResponse() instanceof ColaboratorRequestsListExpensesResponse) {
                    holidaysPeriodsResponse = (HolidaysPeriodsResponse) response.getResponse();
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
                  datePickerDialog.setCustomTitle("Inicio de vacaciones" );
                  Calendar today = Calendar.getInstance();
                  datePickerDialog.setMinDate( today);
                  datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerHolidayDialog");

              }


          DatePickerHolidayDialog.OnDateSetListener dateSetListenerStart = new DatePickerHolidayDialog.OnDateSetListener() {


              @Override
              public void onDateSet(DatePickerHolidayDialog view, int year, int month, int dayOfMonth) {



              }
          };

      }
