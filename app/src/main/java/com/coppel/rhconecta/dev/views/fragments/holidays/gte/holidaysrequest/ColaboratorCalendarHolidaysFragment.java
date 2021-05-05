package com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestColaboratorsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaboratorGte;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_CALENDAR_DAYS_PROPOSED;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.capitalizeText;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidaysInverse;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDayNameFromDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorCalendarHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View, DialogFragmentWarning.OnOptionClick,HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener {

    public static final String TAG = ColaboratorCalendarHolidaysFragment.class.getSimpleName();

    public static final String BUNDLE_OPTION_DATA_COLABORATOR = "BUNDLE_OPTION_DATA_COLABORATOR";
    public static final String BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS = "BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS";
    public static final String BUNDLE_OPTION_DATA_LABEL_SHOW = "BUNDLE_OPTION_DATA_LABEL_SHOW";
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    @BindView(R.id.layoutSplice)
    RelativeLayout layoutSplice;


    private DialogFragmentWarning dialogFragmentWarning;

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
    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaboratorGte headerHoliday;
    @BindView(R.id.monthName)
    TextView monthName;
    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private ColaboratorHoliday colaboratorHoliday;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestColaboratorsRecyclerAdapter holidayRequestRecyclerAdapter;
    private VacacionesActivity vacacionesActivity;
    private boolean showLayoutSplice;

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
        args.putBoolean(BUNDLE_OPTION_DATA_LABEL_SHOW,data.isShowLabel());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.colaboratorHoliday = (ColaboratorHoliday) getArguments().getSerializable(BUNDLE_OPTION_DATA_COLABORATOR);
        this.periodSelected = (HolidayPeriod) getArguments().getSerializable(BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS);
        showLayoutSplice =  getArguments().getBoolean(BUNDLE_OPTION_DATA_LABEL_SHOW);
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
        iconList.setImageResource(R.drawable.ic_calendar_list);
        iconCalendar.setImageResource(R.drawable.ic_icn_calendar_disable);
        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_holidays_colaborator_calendar));
        }
        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        this.periodSelected.setNom_empleado(colaboratorHoliday.getNom_empleado());
        this.periodSelected.setFotoperfil(colaboratorHoliday.getFotoperfil());
//        headerView.setDetailData(this.periodSelected,true);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestColaboratorsRecyclerAdapter(holidayPeriodList,IScheduleOptions);
        holidayRequestRecyclerAdapter.setEnableThemeHoliday(true);
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
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSingle(getResources().getDrawable(R.drawable.circle_green_solid_background));
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSplice(getResources().getDrawable(R.drawable.circle_melon_solid_background));
        collapsibleCalendar.setTitleMonthVisible(false);
        monthName.setText(collapsibleCalendar.getMonthCurrentTitle());
        layoutSplice.setVisibility(showLayoutSplice ? VISIBLE: GONE);
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
                if(response.getResponse() instanceof HolidaysPeriodsResponse) {
                    HolidaysPeriodsResponse holidaysPeriodsResponse = (HolidaysPeriodsResponse)response.getResponse();
                    headerHoliday.setDetailData(this.colaboratorHoliday,holidaysPeriodsResponse);
                    holidayPeriodList.clear();
                    for(HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                        holidayPeriodList.add(period);
                    }
                    holidayRequestRecyclerAdapter.notifyDataSetChanged();
                    setColaboratorMarkInCalendar(holidayPeriodList);
                    formatedDatesList();

                    layoutContainer.setVisibility(VISIBLE);

                    monthName.setText(collapsibleCalendar.getMonthCurrentTitle());
                }

                break;
        }
    }

    private void formatedDatesList() {
        for(HolidayPeriod period : holidayRequestRecyclerAdapter.getAllItems()){
            /***Formateamos las fechas**/
            String[] dateFormat = null;
            dateFormat =  period.getFec_ini().split("-");
            period.setFec_ini(String.format("%s-%s-%s",
                    dateFormat[2],dateFormat[1],dateFormat[0]));
            period.setFec_ini(String.format("%s, %s",capitalizeText(getContext(),getDayNameFromDate(period.getFec_ini())),period.getFec_ini()));


            dateFormat =  period.getFec_fin().split("-");
            period.setFec_fin(String.format("%s-%s-%s",
                    dateFormat[2],dateFormat[1],dateFormat[0]));
            period.setFec_fin(String.format("%s, %s",capitalizeText(getContext(),getDayNameFromDate(period.getFec_fin())),period.getFec_fin()));



            holidayRequestRecyclerAdapter.notifyDataSetChanged();
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
    public void showLabelSplice(boolean enable) {
        if (enable){
            layoutSplice.setVisibility(VISIBLE);
            showLayoutSplice = enable;
        }
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

        String dateStart = period.getFec_ini().split("T")[0];
        String dateEnd = period.getFec_fin().split("T")[0];

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
}
