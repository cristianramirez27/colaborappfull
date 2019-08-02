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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CalendarProposedData;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayGetDetailPeriodResponse;
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
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestColaboratorsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaboratorGte;
import com.coppel.rhconecta.dev.views.customviews.RequestHolidaysColaboratorView;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
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
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIODS_COLABORATORS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidays;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidaysInverse;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorCalendarHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View, DialogFragmentWarning.OnOptionClick,HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener {

    public static final String TAG = ColaboratorCalendarHolidaysFragment.class.getSimpleName();

    public static final String BUNDLE_OPTION_DATA_COLABORATOR = "BUNDLE_OPTION_DATA_COLABORATOR";
    public static final String BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS = "BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS";
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentWarning dialogFragmentWarning;

    @BindView(R.id.headerColaborator)
    RequestHolidaysColaboratorView headerView;

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


    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private ColaboratorHoliday colaboratorHoliday;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestColaboratorsRecyclerAdapter holidayRequestRecyclerAdapter;

    private Map<String, List<DaySelectedHoliday>> periods;

    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;

    private VacacionesActivity vacacionesActivity;

    private HolidayPeriod periodSelected;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorCalendarHolidaysFragment getInstance(CalendarProposedData data){
        ColaboratorCalendarHolidaysFragment fragment = new ColaboratorCalendarHolidaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_COLABORATOR,data.getColaborator());
        args.putSerializable(BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS,data.getPeriod());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.colaboratorHoliday = (ColaboratorHoliday) getArguments().getSerializable(BUNDLE_OPTION_DATA_COLABORATOR);
        this.periodSelected = (HolidayPeriod) getArguments().getSerializable(BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_calendario_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        iconList.setOnClickListener(this);
        iconCalendar.setOnClickListener(this);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }
        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        headerView.setDetailData(this.periodSelected,true);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestColaboratorsRecyclerAdapter(holidayPeriodList,IScheduleOptions);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);
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


        return view;
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
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_CALENDAR_DAYS_PROPOSED, 11);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_empconsulta(numEmployer);

        holidayRequestData.setFec_ini(getDateFormatToHolidaysInverse(this.periodSelected.getFec_ini(),false));
        holidayRequestData.setFec_fin(getDateFormatToHolidaysInverse(this.periodSelected.getFec_fin(),false));
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


            case R.id.iconCalendar:
                switchView(true);
                break;
        }
    }


    private void switchView(boolean showCalendar){
        if(showCalendar){
            layoutCalendar.setVisibility(VISIBLE);
            layoutList.setVisibility(GONE);
        }else {
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
                    HolidaysPeriodsResponse holidaysPeriodsResponse = (HolidaysPeriodsResponse)response.getResponse();
                    holidayPeriodList.clear();
                    for(HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                        holidayPeriodList.add(period);
                    }
                    holidayRequestRecyclerAdapter.notifyDataSetChanged();

                    setColaboratorMarkInCalendar(holidayPeriodList);
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

    private void setColaboratorMarkInCalendar(List<HolidayPeriod> periods){

        List<Day> listDaySelected = new ArrayList<>();
        for(HolidayPeriod period : periods){
            setSelectedDays(period,listDaySelected);
        }

        collapsibleCalendar.select(listDaySelected);
    }

    private void setSelectedDays(HolidayPeriod period,List<Day> listDaySelected ){

        String dateStart = period.getFec_ini();
        String dateEnd = period.getFec_fin();

        String[] dateParts = null;
        int year;
        int month;
        int day;

        dateParts = dateStart.split("-");
        year = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[2]);
        Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.set(year,month,day);
        //Obtenemos la fecha final
        dateParts = dateEnd.split("-");
        year = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[2]);
        Calendar calendar_Fin = Calendar.getInstance();
        calendar_Fin.set(year,month,day);

        Calendar  calendar = Calendar.getInstance();
        calendar.set(calendar_Ini.get(Calendar.YEAR),calendar_Ini.get(Calendar.MONTH),calendar_Ini.get(Calendar.DAY_OF_MONTH));

        CalendarAdapter adapter = new CalendarAdapter(getActivity(), calendar);
        collapsibleCalendar.setAdapter(adapter);


        do{
            listDaySelected.add(new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));
            calendar.add(Calendar.DATE,1);

        }while (!calendar.after(calendar_Fin));




    }


}
