package com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar;


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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CalendarProposedData;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.Center;
import com.coppel.rhconecta.dev.business.models.CentersHolidayResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysCalendarPeriodsResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysColaboratorsResponse;
import com.coppel.rhconecta.dev.business.models.SpliceSelectedVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.ColaboratorHolidayRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCenter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentEstatus;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.shrikanthravi.collapsiblecalendarview.widget.CommandSplice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_CALENDAR_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_CENTERS;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_WARNING;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.formatMonthNameFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayCalendarListFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        ColaboratorHolidayRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentCenter.OnButonOptionClick, DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener{

    public static final String TAG = HolidayCalendarListFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.seleccionCentroLayout)
    RelativeLayout seleccionCentroLayout;
    @BindView(R.id.centro)
    TextView centro;


    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
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
    @BindView(R.id.monthName)
    TextView monthName;
    @BindView(R.id.lastmonth)
    ImageView lastmonth;
    @BindView(R.id.nextMonth)
    ImageView nextMonth;


    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private HolidaysCalendarPeriodsResponse calendarPeriodsResponse;

    private DialogFragmentCenter dialogFragmentCenter;
    private Center centerSelected;
    private DialogFragmentEstatus dialogFragmentEstatus;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<ColaboratorHoliday> colaboratorHolidays;
    private List<HolidayPeriod> colaboratorsPeriodsHolidays;
    private String searchName = "";
    private ColaboratorHolidayRecyclerAdapter colaboratorHolidayRecyclerAdapter;
    private boolean EXPIRED_SESSION;
    private DialogFragmentWarning dialogFragmentWarning;
    private CentersHolidayResponse centersResponse;
    private long mLastClickTime = 0;
    private boolean doSearch;
    private Calendar currentDate;

    private List<Day>  listDaySelectedCurrent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static HolidayCalendarListFragment getInstance(){
        HolidayCalendarListFragment fragment = new HolidayCalendarListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario_vacaciones, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        seleccionCentroLayout.setOnClickListener(this);
        iconList.setOnClickListener(this);
        iconCalendar.setOnClickListener(this);
        lastmonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);


        iconList.setImageResource(R.drawable.ic_calendar_list);
        iconCalendar.setImageResource(R.drawable.ic_icn_calendar_disable);


        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_colaborator_calendar_gral));
        }

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        colaboratorHolidays = new ArrayList<>();
        colaboratorsPeriodsHolidays = new ArrayList<>();
        colaboratorHolidayRecyclerAdapter = new ColaboratorHolidayRecyclerAdapter(getActivity(),colaboratorHolidays,true);
        colaboratorHolidayRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(colaboratorHolidayRecyclerAdapter);
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 0){
                    if(doSearch && s.toString().length() == 0){
                        searchName = edtSearch.getText() != null && !edtSearch.getText().toString().isEmpty() ?
                                edtSearch.getText().toString() : "";
                        getCalendarPeriods(currentDate.get(Calendar.MONTH),currentDate.get(Calendar.YEAR));
                    }
                }else {
                    doSearch = true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        initializeCalendar();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCenters();
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
        collapsibleCalendar.setNameDayGray(true);
        collapsibleCalendar.setMultipleDays(true);
        collapsibleCalendar.setEnable(true);
        collapsibleCalendar.setSpliceActionEnable(true);
        collapsibleCalendar.setTitleMonthVisible(false);
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSingle(getResources().getDrawable(R.drawable.circle_green_solid_background));
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSplice(getResources().getDrawable(R.drawable.circle_melon_solid_background));
        collapsibleCalendar.setActionSplice(new CommandSplice() {
            @Override
            public void action(Day daySelected) {
                SpliceSelectedVO data = new SpliceSelectedVO( calendarPeriodsResponse.getData().getResponse().getPeriodos(),daySelected);
                data.setListDaySelectedCurrent(listDaySelectedCurrent);
                data.setShowDetail(true);
                ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR,data);
            }
        });

        formatMonthNameFormat(collapsibleCalendar.getMonthCurrentTitle(),monthName);
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
            case R.id.seleccionCentroLayout:
                if(centersResponse == null){
                    getCenters();
                }else {
                    showCenters(centersResponse.getData().getResponse().getCentros());
                }
            break;


            case R.id.iconList:
                switchView(false);
                break;


            case R.id.iconCalendar:
                switchView(true);
                break;

            case R.id.lastmonth:
                changeMonth(false);
                break;

            case R.id.nextMonth:
                changeMonth(true);
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
                if(response.getResponse() instanceof CentersHolidayResponse){
                    //Centros
                    centersResponse = (CentersHolidayResponse)response.getResponse();
                    if (centersResponse.getData().getResponse().getCentros() != null &&
                            !centersResponse.getData().getResponse().getCentros().isEmpty()) {
                        //Se toma el primero por default si solo regresa 1 centro
                            centerSelected = centersResponse.getData().getResponse().getCentros().get(0);
                            centro.setText(centersResponse.getData().getResponse().getCentros().get(0).getNom_centro());
                        if (centersResponse.getData().getResponse().getCentros().size() == 1) {
                            seleccionCentroLayout.setEnabled(false);
                            seleccionCentroLayout.setClickable(false);
                        }
                    }
                    /**Si clv_mensaje = 1 entonces mostrar el mensaje de des_mensaje**/
                    if(centersResponse.getData().getResponse().getClv_mensaje() == 1){
                        showWarningDialog(centersResponse.getData().getResponse().getDes_mensaje());
                    }
                    //Buscamos los colaboradores sin importar el centro
                  //  getColaborators(centerSelected.getNum_centro(),searchName);
                    getCalendarInfo();

                    layoutContainer.setVisibility(VISIBLE);

                } if(response.getResponse() instanceof HolidaysColaboratorsResponse) {
                    HolidaysColaboratorsResponse colaboratorsResponse = (HolidaysColaboratorsResponse)response.getResponse();
                    /**Si clv_mensaje = 1 entonces mostrar el mensaje de des_mensaje**/
                        colaboratorHolidays.clear();
                    if (colaboratorsResponse.getData().getResponse().getClv_mensaje() == 1) {
                        showWarningDialog(colaboratorsResponse.getData().getResponse().getDes_mensaje());
                    } else if(colaboratorsResponse.getData().getResponse().getClv_mensaje() == 0) {

                        for (ColaboratorHoliday colaborator : colaboratorsResponse.getData().getResponse().getEmpleados()) {
                            colaboratorHolidays.add(colaborator);
                        }
                         colaboratorHolidayRecyclerAdapter.notifyDataSetChanged();
                    }

                    getCalendarInfo();


                }else if(response.getResponse() instanceof HolidaysCalendarPeriodsResponse) {
                    calendarPeriodsResponse = (HolidaysCalendarPeriodsResponse)response.getResponse();
                    HashMap<String,ColaboratorHoliday> colaboratorHolidayHashMap = new HashMap<>();
                    List<String> numberColaborators = new ArrayList<>();
                colaboratorsPeriodsHolidays.clear();
                colaboratorHolidays.clear();
                if(calendarPeriodsResponse.getData().getResponse().getDes_mensaje() != null && !calendarPeriodsResponse.getData().getResponse().getDes_mensaje().isEmpty()) {
                    showSuccessDialog(MSG_HOLIDAYS_WARNING, calendarPeriodsResponse.getData().getResponse().getDes_mensaje(), "");

                    setColaboratorMarkInCalendar(new ArrayList<>());

                }else{
                    for (HolidayPeriod period : calendarPeriodsResponse.getData().getResponse().getPeriodos()) {
                        colaboratorsPeriodsHolidays.add(period);

                        if(!numberColaborators.contains(period.getNum_empleado())){
                            numberColaborators.add(period.getNum_empleado());
                        }

                        if(!colaboratorHolidayHashMap.containsKey(period.getNum_empleado())){
                            colaboratorHolidayHashMap.put(period.getNum_empleado(),new ColaboratorHoliday(period.getNom_empleado(),period.getFotoperfil(),
                                    period.getNum_empleado(),centerSelected.getNum_centro(),
                                    period.getVer_marca() != null && !period.getVer_marca().isEmpty() ? true :false));
                        }
                    }

                        setCalendarData();
                        //colaboratorHolidays.clear();
                        for (String colaboratorNumber : numberColaborators) {
                            colaboratorHolidays.add(colaboratorHolidayHashMap.get(colaboratorNumber));
                        }
                    }
                colaboratorHolidayRecyclerAdapter.notifyDataSetChanged();


                Calendar  calendar = Calendar.getInstance();
                calendar.set(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH),1);
                CalendarAdapter adapter = new CalendarAdapter(getActivity(), calendar);
                collapsibleCalendar.setAdapter(adapter);

                formatMonthNameFormat(collapsibleCalendar.getMonthCurrentTitle(),monthName);
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


    private void setCalendarData(){
        if(this.colaboratorsPeriodsHolidays != null && !this.colaboratorsPeriodsHolidays.isEmpty()){
            setColaboratorMarkInCalendar(this.colaboratorsPeriodsHolidays);
        }
    }


    private void getCalendarInfo(){
        if(currentDate == null){
            currentDate = Calendar.getInstance();
        }

        getCalendarPeriods(currentDate.get(Calendar.MONTH),currentDate.get(Calendar.YEAR));
    }

    private void changeMonth(boolean isNext){
        currentDate.set(Calendar.MONTH, isNext ? currentDate.get(Calendar.MONTH) + 1 :  currentDate.get(Calendar.MONTH) - 1);
        if(isNext){
            collapsibleCalendar.nextMonth();
        }else {
            collapsibleCalendar.prevMonth();
        }

        formatMonthNameFormat(collapsibleCalendar.getMonthCurrentTitle(),monthName);
        getCalendarInfo();

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
        }else {
            dialogFragmentWarning.close();
           getActivity().onBackPressed();
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
    public void onLeftOptionStateClick() {

        if(dialogFragmentCenter != null && dialogFragmentCenter.isVisible())
            dialogFragmentCenter.close();
        else if(dialogFragmentEstatus != null && dialogFragmentEstatus.isVisible())
            dialogFragmentEstatus.close();
    }

    @Override
    public void onRightOptionStateClick(Center data) {
        if(data != null) {
            centerSelected = data;
            dialogFragmentCenter.close();
            centro.setText(centerSelected.getNom_centro());
           // getColaborators(centerSelected.getNum_centro(),searchName);
            getCalendarPeriods(currentDate.get(Calendar.MONTH),currentDate.get(Calendar.YEAR));
        }
    }

    private void performSearch(String search) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if(search != null && !search.isEmpty()){
            edtSearch.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
            searchName = search;
            getCalendarPeriods(currentDate.get(Calendar.MONTH),currentDate.get(Calendar.YEAR));
        }
    }

    private void showCenters(List<Center> centers){
        CatalogueData statesData = new CatalogueData();
        statesData.setData(centers);
        dialogFragmentCenter = DialogFragmentCenter.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentCenter.setOnButtonClickListener(this);
        dialogFragmentCenter.show(parent.getSupportFragmentManager(), DialogFragmentCenter.TAG);
    }

    private void getCenters(){
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_CENTERS, 6);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    /*private void getColaborators(int centerSelectedId,String search){
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_COLABORATORS, 7);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setClv_estatus(-1);//0
        holidayRequestData.setNum_centro(centerSelectedId);
        holidayRequestData.setNom_empleado(search);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }*/



    private void getCalendarPeriods(int month,int year){
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_CALENDAR_HOLIDAY, 19);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_anio(year);
        holidayRequestData.setNum_mes(month+1);//Se agrega 1 por el formato del calendario resta 1
        holidayRequestData.setNum_centro(centerSelected.getNum_centro());
        holidayRequestData.setNom_empleado(searchName);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    @Override
    public void onRequestSelectedClick(ColaboratorHoliday colaboratorHoliday) {
        doSearch = false;

        CalendarProposedData calendarProposedData = new CalendarProposedData(colaboratorHoliday,listDaySelectedCurrent);
        calendarProposedData.setNum_mes(currentDate.get(Calendar.MONTH)+1);
        calendarProposedData.setNum_anio(currentDate.get(Calendar.YEAR));
        ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR,calendarProposedData);
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

        listDaySelectedCurrent = listDaySelected;
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
                Day dayToAdd = new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), -1);
                daysInCalendar.put(dateAsString,dayToAdd);
            }


            calendar.add(Calendar.DATE,1);

        }while (!calendar.after(calendar_Fin));
    }

}
