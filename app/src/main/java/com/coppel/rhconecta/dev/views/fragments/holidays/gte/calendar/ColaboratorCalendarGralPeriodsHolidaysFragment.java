package com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CalendarProposedData;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayChangeStatusResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodFolio;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.models.MarkHoliday;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestColaboratorsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAuthorizeHoliday;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.wdullaer.datetimepickerholiday.date.DatePickerHolidayDialog;
import com.wdullaer.datetimepickerholiday.date.DaySelectedHoliday;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_GTE_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.EDIT_PERIOD_HOLIDAY;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_CALENDAR_DAYS_PROPOSED;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.formatMonthNameFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorCalendarGralPeriodsHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentAuthorizeHoliday.OnButonOptionObservationClick,DialogFragmentGetDocument.OnButtonClickListener{
    public static final String TAG = ColaboratorCalendarGralPeriodsHolidaysFragment.class.getSimpleName();

    public static final String BUNDLE_OPTION_DATA_COLABORATOR = "BUNDLE_OPTION_DATA_COLABORATOR";
    public static final String BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS = "BUNDLE_OPTION_DATA_PERIOD_HOLIDAYS";
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentWarning dialogFragmentWarning;


    /*Header*/
    @BindView(R.id.layoutObservaciones)
    LinearLayout layoutObservaciones;
    @BindView(R.id.estatus)
    TextView estatus;
    @BindView(R.id.fechaInicio)
    TextView fechaInicio;
    @BindView(R.id.fechaFin)
    TextView fechaFin;
    @BindView(R.id.diasVacaciones)
    TextView diasVacaciones;
    @BindView(R.id.expObservaciones)
    ExpandableSimpleTitle expObservaciones;
    @BindView(R.id.nombreGte)
    TextView nombreGte;
    @BindView(R.id.txtReason)
    TextView txtReason;
    @BindView(R.id.gteTitle)
    TextView gteTitle;
    @BindView(R.id.txtDate)
    TextView txtDate;

    @BindView(R.id.layoutGte)
    LinearLayout layoutGte;
    @BindView(R.id.dateLayout)
    LinearLayout dateLayout;

    @BindView(R.id.fechaRechazo)
    TextView fechaRechazo;
    @BindView(R.id.motivoRechazo)
    TextView motivoRechazo;

    @BindView(R.id.divider1)
    View divider1;
    @BindView(R.id.divider2)
    View divider2;


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
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.monthName)
    TextView monthName;
    @BindView(R.id.layoutSplice)
    RelativeLayout layoutSplice;


    private Day maxDayToCalendar;

    private Day minDayToCalendar;

    private double numDaysQuantity;

    private int action;
    private Map<String, List<DaySelectedHoliday>> periods;

    private boolean changeStatusPeriod;
    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;

    private HolidayPeriod holidayPeriod;

    private boolean startedPeriod = false;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> holidayPeriodList;
    private HolidayRequestColaboratorsRecyclerAdapter holidayRequestRecyclerAdapter;
    private VacacionesActivity vacacionesActivity;

    private CalendarProposedData calendarProposedData;
    private List<Day> daySelectedOtherSplices;

    private boolean finishApp = false;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorCalendarGralPeriodsHolidaysFragment getInstance(CalendarProposedData data){
        ColaboratorCalendarGralPeriodsHolidaysFragment fragment = new ColaboratorCalendarGralPeriodsHolidaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_COLABORATOR,data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.calendarProposedData = (CalendarProposedData) getArguments().getSerializable(BUNDLE_OPTION_DATA_COLABORATOR);
        this.holidayPeriod = calendarProposedData.getPeriod();
        this.daySelectedOtherSplices = calendarProposedData.getListDaySelected();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_calendario_gral_periods, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        iconList.setOnClickListener(this);
        iconCalendar.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        periods = new HashMap<>();
        iconList.setImageResource(R.drawable.ic_calendar_list);
        iconCalendar.setImageResource(R.drawable.ic_icn_calendar_disable);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_period_holidays));
        }
        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        //headerView.setDetailData(this.colaboratorHoliday,true);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));

        holidayPeriodList = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestColaboratorsRecyclerAdapter(holidayPeriodList,IScheduleOptions);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);

        initializeCalendar();
        setDataPeriod();


        validateButton();


        expObservaciones.setText("Observaciones");


        expObservaciones.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                observacionesStateChange(expObservaciones,layoutObservaciones);
            }
        });


        return view;
    }

    private void validateButton(){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dtStart = formatter.parseDateTime(this.holidayPeriod.getFec_ini());
        DateTime dtEnd = formatter.parseDateTime(this.holidayPeriod.getFec_fin());

        DateTime now = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);

        if(now.isAfter(dtEnd)){ // Ya termino
            btnEdit.setEnabled(false);
            btnEdit.setBackgroundResource(R.drawable.backgroud_rounder_grey);
            btnCancel.setEnabled(false);
            btnCancel.setBackgroundResource(R.drawable.backgroud_rounder_grey);
        }else if(dtStart.isBefore(now) && !now.isAfter(dtEnd)){// Ya empezo y no ha terminado
            startedPeriod = true;
            btnEdit.setEnabled(false);
            btnEdit.setBackgroundResource(R.drawable.backgroud_rounder_grey);
            btnCancel.setEnabled(true);
            btnCancel.setText(getString(R.string.btn_cancel_holidays_));
            btnCancel.setBackgroundResource(R.drawable.background_blue_rounded);
        }else if(dtStart.isAfter(now)){ // No ha comenzado
            btnEdit.setEnabled(true);
            btnEdit.setBackgroundResource(R.drawable.background_blue_rounded);
            btnCancel.setEnabled(true);
            btnCancel.setText(getString(R.string.btn_refuse_holidays_));
            btnCancel.setBackgroundResource(R.drawable.background_blue_rounded);
        }else if(dtStart.compareTo(now) == 0){ // Empieza hoy
            startedPeriod = true;
            btnEdit.setEnabled(false);
            btnEdit.setBackgroundResource(R.drawable.backgroud_rounder_grey);
            btnCancel.setEnabled(true);
            btnCancel.setText(getString(R.string.btn_cancel_holidays_));
            btnCancel.setBackgroundResource(R.drawable.background_blue_rounded);
        }

    }

    private void initializeCalendar(){
        //setValuesPeriods(configurationHolidaysData.getTotalDays());
        //Inicializar Calendario
        //To hide or show expand icon
        collapsibleCalendar.setExpandIconVisible(true);
        Calendar today=new GregorianCalendar();
        today.add(Calendar.DATE,1);
        collapsibleCalendar.expand(100);
        collapsibleCalendar.setVisibilityExpandIcon(View.INVISIBLE);
        collapsibleCalendar.setVisibilityBtnNext(View.VISIBLE);
        collapsibleCalendar.setVisibilityBtnPrev(View.VISIBLE);
        collapsibleCalendar.setExpandIconVisible(false);
        collapsibleCalendar.setMultipleDays(true);
        collapsibleCalendar.setEnable(false);
        collapsibleCalendar.setTitleMonthVisible(false);
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSingle(getResources().getDrawable(R.drawable.circle_green_solid_background));
        collapsibleCalendar.setmSelectedItemBackgroundDrawableSplice(getResources().getDrawable(R.drawable.circle_melon_solid_background));

        collapsibleCalendar.getmBtnPrevMonth().setImageResource(R.drawable.arrow_left_calendar);
        collapsibleCalendar.getmBtnNextMonth().setImageResource(R.drawable.arrow_right_calendar);
        collapsibleCalendar.getmBtnNextMonth().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMonth(true);
            }
        });

        collapsibleCalendar.getmBtnPrevMonth().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMonth(false);
            }
        });
        String monthNameCalendar = collapsibleCalendar.getMonthCurrentTitle();
        monthName.setText(collapsibleCalendar.getMonthCurrentTitle());
    }

    private void changeMonth(boolean isNext){
        if(isNext){
            collapsibleCalendar.nextMonth();
        }else {
            collapsibleCalendar.prevMonth();
        }

        monthName.setText(collapsibleCalendar.getMonthCurrentTitle());

        setVisibilityNextMonth();
        setVisibilityLasttMonth();
    }


    private void setVisibilityNextMonth(){

        int m = collapsibleCalendar.getMonth()+1;

        if(maxDayToCalendar.getYear() == collapsibleCalendar.getYear() &&
                maxDayToCalendar.getMonth() == m){
            collapsibleCalendar.getmBtnNextMonth().setVisibility(View.INVISIBLE);
        }else {
            collapsibleCalendar.getmBtnNextMonth().setVisibility(VISIBLE);
        }
    }

    private void setVisibilityLasttMonth(){
        int m = collapsibleCalendar.getMonth()+1;

        if(minDayToCalendar.getYear() == collapsibleCalendar.getYear()&&
                minDayToCalendar.getMonth() == m ){
            collapsibleCalendar.getmBtnPrevMonth().setVisibility(View.INVISIBLE);
        }else {
            collapsibleCalendar.getmBtnPrevMonth().setVisibility(VISIBLE);
        }
    }

    private void setMaxMonthCalendar(HolidayPeriod period){

        TreeSet<String> datesStartSorter = new TreeSet<>();
        TreeSet<String> datesEndSorter = new TreeSet<>();

            String[] partsStart = period.getFec_ini().split(",")[1].trim().split("-");
            datesStartSorter.add(String.format("%s%s%s",partsStart[2],partsStart[1],partsStart[0]));

            String[] partsEnd = period.getFec_fin().split(",")[1].trim().split("-");
            datesEndSorter.add(String.format("%s%s%s",partsEnd[2],partsEnd[1],partsEnd[0]));


        if(!datesStartSorter.isEmpty()){
            String dMin = datesStartSorter.first();
            int yearMin = Integer.parseInt(dMin.substring(0,4));
            int monthMin = Integer.parseInt(dMin.substring(4,6));
            minDayToCalendar = new Day(yearMin,monthMin,30);
        }

        if(!datesEndSorter.isEmpty()){
            String d = datesEndSorter.last();
            int year = Integer.parseInt(d.substring(0,4));
            int month = Integer.parseInt(d.substring(4,6));
            maxDayToCalendar = new Day(year,month,30);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibilityLasttMonth();
                setVisibilityNextMonth();
            }
        },300);


    }



    private void setDataPeriod(){
        setDataHeader();
        holidayPeriodList.clear();
       /* for(HolidayPeriod period : this.holidayPeriod.getVer_marca()){
            holidayPeriodList.add(period);
        }*/
        holidayRequestRecyclerAdapter.notifyDataSetChanged();
        if(holidayPeriod.getIdu_marca() == 1){
            layoutSplice.setVisibility(VISIBLE);
            //Agregamos los empalmes
                for(MarkHoliday markHoliday : holidayPeriod.getVer_marca()){
                    HolidayPeriod hperiodMark = new HolidayPeriod();
                    hperiodMark.setFotoperfil(markHoliday.getFotoperfil());
                    hperiodMark.setNom_empleado(markHoliday.getNom_empmarca());

                    hperiodMark.setIdu_marca(1);
                    this.holidayPeriodList.add(hperiodMark);
                }

        }else {
            layoutSplice.setVisibility(GONE);
            iconCalendar.callOnClick();
            iconList.setEnabled(false);
        }

        this.holidayPeriod.setFec_ini(this.holidayPeriod.getFec_ini().split(",")[1].trim());
        this.holidayPeriod.setFec_fin(this.holidayPeriod.getFec_fin().split(",")[1].trim());



        if( this.holidayPeriod.getColorletra() != null && ! this.holidayPeriod.getColorletra().isEmpty())
            estatus.setTextColor( Color.parseColor( this.holidayPeriod.getColorletra()));

        estatus.setText( this.holidayPeriod.getNom_estatus() != null ?  this.holidayPeriod.getNom_estatus() :
                ( this.holidayPeriod.getNom_estaus() != null ?  this.holidayPeriod.getNom_estaus() : ""));
        //estatus.setTextColor(Color.parseColor( this.holidayPeriod.getDes_colorletra()));
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor( this.holidayPeriod.getColor()));
        gd.setCornerRadius(80);
        estatus.setBackgroundDrawable(gd);


        //Mostramos en calendario
        //List<HolidayPeriod> holidayPeriodOwn = new ArrayList<>();
        //holidayPeriodOwn.add(this.holidayPeriod);
        //setColaboratorMarkInCalendar(holidayPeriodOwn);

        Day dayStart = getDayStartPeriod(this.calendarProposedData.getPeriod().getFec_ini(),false);
        getPeriodsColaborators(this.calendarProposedData.getColaborator().getNum_empleado(),1,dayStart.getMonth(), dayStart.getYear());
    }



    private Day getDayStartPeriod(String date, boolean adjustMonth){
        String[] datePart = date.split("-");
        return new Day(Integer.parseInt(datePart[2]),!adjustMonth ? Integer.parseInt(datePart[1]) : Integer.parseInt(datePart[1]) - 1,Integer.parseInt(datePart[0].trim()));
    }

    private void setDataHeader(){

        fechaInicio.setText(this.holidayPeriod.getFec_ini());
        fechaFin.setText(this.holidayPeriod.getFec_fin());

         numDaysQuantity = Double.parseDouble(this.holidayPeriod.getNum_dias().split(" ")[0]);
        String numDayASString= String.valueOf(numDaysQuantity);
        if(numDaysQuantity % 1 == 0){
            numDayASString = numDayASString.substring(0,numDayASString.indexOf("."));
            numDayASString = String.valueOf(Integer.parseInt(numDayASString));
        }

        diasVacaciones.setText(String.format("%s %s",numDayASString, numDaysQuantity > 1 ? "días" : "día"));

        expObservaciones.setColorText(getContext().getResources().getColor(R.color.colorTextGrayDark));
        estatus.setText(this.holidayPeriod.getNom_estatus());
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(this.holidayPeriod.getColor()));
        gd.setCornerRadius(40);
        estatus.setBackgroundDrawable(gd);
        layoutGte.setVisibility(GONE);
        dateLayout.setVisibility(GONE);
        divider1.setVisibility(GONE);
        divider2.setVisibility(GONE);
        txtReason.setText("Observaciones");
        motivoRechazo.setText(this.holidayPeriod.getDes_comentario());
    }



    private void observacionesStateChange(ExpandableSimpleTitle expandable, LinearLayout layoutToExpand) {
        if (expandable.isExpanded()) {
            layoutToExpand.setVisibility(VISIBLE);
        } else {
            layoutToExpand.setVisibility(View.GONE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void showLabelSplice(boolean enable) { }

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

            case R.id.btnCancel:
                showAlertDialogCancel();
                break;

            case R.id.btnEdit:

                openCalendar();

                break;
        }
    }


    private void showAlertDialogCancel() {
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {
                cancelHoliday();
                dialogFragmentDeletePeriods.close();
            }

            @Override
            public void onCancel() {
                dialogFragmentDeletePeriods.close();
            }
        });
        dialogFragmentDeletePeriods.setVisibleCancelButton(VISIBLE);
        dialogFragmentDeletePeriods.setTitle("Atención");
        String msg = btnCancel.getText().toString().contains("Rechazar") ?
                "¿Quieres rechazar las vacaciones autorizadas del colaborador?" :
                (startedPeriod ?
                        "¿Quieres cancelar las vacaciones iniciadas por el colaborador?" :
                        "¿Quieres cancelar las vacaciones autorizadas del colaborador?");
        dialogFragmentDeletePeriods.setMsg(msg);
        dialogFragmentDeletePeriods.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void cancelHoliday(){
        List<HolidayPeriod> data = new ArrayList<>();
        data.add( this.holidayPeriod);
        this.action = 1;
        openObservationsCancel(data);
    }

    private void editHoliday(List<HolidayPeriod> holidayPeriodSchedule){
        List<HolidayPeriod> data = new ArrayList<>();
        /*HolidayPeriod periodUpdated = new HolidayPeriod();
        periodUpdated.setIdu_folio(this.holidayPeriod.getIdu_folio());
        periodUpdated.setFec_ini(holidayPeriodSchedule.get(0).getFec_ini());
        periodUpdated.setFec_fin(holidayPeriodSchedule.get(0).getFec_fin());
        periodUpdated.setNum_dias(holidayPeriodSchedule.get(0).getNum_dias());
        data.add(periodUpdated);*/
        /**Se establece el IdFolio al primer periodo y los demas en 0**/
        if(holidayPeriodSchedule.size() > 0){
            holidayPeriodSchedule.get(0).setIdu_folio(this.holidayPeriod.getIdu_folio());
        }

        openObservationsEdit(holidayPeriodSchedule);
    }

    //TODO Validar
    private void openCalendar(){
        DatePickerHolidayDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(dateSetListenerStart);
        datePickerDialog.setThemeHolday(true);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(getString(R.string.title_calendar_periods_2));
        datePickerDialog.setTextButtonCalendar(this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getDes_config());

        double holidayDaysTotal =  this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getNum_adicionales() +
                this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getNum_decision()
                +  this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getNum_decisionanterior();


        datePickerDialog.setNum_diasagendados( this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getNum_diasagendados());
        datePickerDialog.setNum_total_vacaciones(holidayDaysTotal);
        double limitDay = this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getNum_totalvacaciones() - this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getNum_diasagendados();
        datePickerDialog.setLimite_dias(holidayDaysTotal/*limitDay*/);
        datePickerDialog.setShowHalfDaysOption(this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getClv_mediodia() == 1);

        datePickerDialog.setDes_mensaje(this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getDes_mensaje()!= null &&
                !this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getDes_mensaje().isEmpty() ?
                this.calendarProposedData.getHolidaysPeriodsResponse().getData().getResponse().getDes_mensaje() : getString(R.string.msg_no_days_available));

        /*Se establece el numero de dias agendados*/
        datePickerDialog.setCurrentDaysScheduled(numDaysQuantity);

        Calendar dateMin = Calendar.getInstance();
        Calendar dateMax = Calendar.getInstance();
        /**Se permiten días anteriores por ser Gte**/
        dateMin.set(Calendar.MONTH,0);
        dateMin.set(Calendar.DAY_OF_MONTH,1);
        datePickerDialog.setMinDate(dateMin);
        //Setear el maximo de 18 meses para seleccionar periodos
        dateMax.add(Calendar.MONTH,18);
        datePickerDialog.setMaxDate(dateMax);
        datePickerDialog.setInitDaysSelectedHolidays(datePickerDialog.getPeriods(getDaysSelectedSource()));
        datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerHolidayDialog");
    }


    private Map<Long, DaySelectedHoliday> getDaysSelectedSource(){
        Map<Long, DaySelectedHoliday> selectionSorted = new HashMap<>();
        double numberDays = Double.parseDouble(this.holidayPeriod.getNum_dias().split(" ")[0]);
        String dateStart = this.holidayPeriod.getFec_ini();
        String dateEnd = this.holidayPeriod.getFec_fin();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime start = formatter.parseDateTime(dateStart);
        DateTime end = formatter.parseDateTime(dateEnd);

        if(numberDays < 1){ 
            Long IdPeriodHalf  = Long.parseLong(String.format("%s%s%s",String.valueOf(start.getYear()),
                    String.valueOf(start.getMonthOfYear()-1 > 9 ? start.getMonthOfYear()-1 : "0"+ (start.getMonthOfYear()-1)),
                    String.valueOf(start.getDayOfMonth() > 9 ? start.getDayOfMonth() : "0"+start.getDayOfMonth())));

            selectionSorted.put(IdPeriodHalf,new DaySelectedHoliday(String.valueOf(IdPeriodHalf),start.getDayOfMonth(),start.getMonthOfYear()-1,start.getYear(),true));
        }else {

            do{

                Long IdPeriod  = Long.parseLong(String.format("%s%s%s",String.valueOf(start.getYear()),
                        String.valueOf(start.getMonthOfYear()-1 > 9 ? start.getMonthOfYear()-1 : "0"+(start.getMonthOfYear()-1)),
                        String.valueOf(start.getDayOfMonth() > 9 ? start.getDayOfMonth() : "0"+start.getDayOfMonth())));

                selectionSorted.put(IdPeriod,new DaySelectedHoliday(String.valueOf(IdPeriod),start.getDayOfMonth(),
                        start.getMonthOfYear()-1,start.getYear(),false));

                start = start.plusDays(1);

            }while (!start.isAfter(end));
        }


        return selectionSorted;
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
            proccessPeriods(periods);
        }

        private void proccessPeriods(Map<String, List<DaySelectedHoliday>> periodsUpdate){

            List<HolidayPeriod> holidayPeriodSchedule = new ArrayList<>();
            for (String key : periodsUpdate.keySet()){
                List<DaySelectedHoliday> daysInPeriod = periodsUpdate.get(key);
                String dateStart = String.format("%s-%s-%s",
                        String.valueOf(daysInPeriod.get(0).getYear()),
                        String.valueOf(daysInPeriod.get(0).getMonth() > 9 ? daysInPeriod.get(0).getMonth() : "0"+ daysInPeriod.get(0).getMonth()),
                        String.valueOf(daysInPeriod.get(0).getDay() > 9 ? daysInPeriod.get(0).getDay() : "0"+ daysInPeriod.get(0).getDay()));

                int indexEndDate = 0;
                if(daysInPeriod.size() > 1){
                    indexEndDate = daysInPeriod.size() - 1 ;
                }

                String dateEnd = String.format("%s-%s-%s",
                        String.valueOf(daysInPeriod.get(indexEndDate).getYear()),
                        String.valueOf(daysInPeriod.get(indexEndDate).getMonth() > 9 ? daysInPeriod.get(indexEndDate).getMonth() : "0"+ daysInPeriod.get(indexEndDate).getMonth()),
                        String.valueOf(daysInPeriod.get(indexEndDate).getDay() > 9 ? daysInPeriod.get(indexEndDate).getDay() : "0"+ daysInPeriod.get(indexEndDate).getDay()));

                String numDays = daysInPeriod.size() > 1 ? String.valueOf(daysInPeriod.size()) :
                        (daysInPeriod.size() == 1 && daysInPeriod.get(0).isHalfDay() ? "0.5" : String.valueOf(daysInPeriod.size()));

                holidayPeriodSchedule.add(new HolidayPeriod(key,numDays,dateStart,dateEnd));
            }

            //TODO Validar que solo sea un periodo
            action = 2;
            editHoliday(holidayPeriodSchedule);
        }


        @Override
        public void onInvalidMaxSelectedDays(String msg) {

            showWarningDialog("",msg);
        }
    };





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
                if(response.getResponse() instanceof HolidayChangeStatusResponse){
                    HolidayChangeStatusResponse changeStatusResponse = (HolidayChangeStatusResponse)response.getResponse();

                    changeStatusPeriod = true;
                    if(!changeStatusResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                        showSuccessDialog(MSG_HOLIDAYS_OK,changeStatusResponse.getData().getResponse().getDes_mensaje(), "");

                        changeStatusPeriod = changeStatusResponse.getData().getResponse().getClv_estado() == 1 ? true : false;
                    }


                    /*changeStatusPeriod = true;
                    if(changeStatusResponse.getData().getResponse().getClv_estado() == 1 || !changeStatusResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                        showSuccessDialog(MSG_HOLIDAYS_OK,changeStatusResponse.getData().getResponse().getDes_mensaje(), "");

                        changeStatusPeriod = false;
                    }*/
                }

                else if(response.getResponse() instanceof HolidaysPeriodsResponse) {
                    HolidaysPeriodsResponse holidaysPeriodsResponse = (HolidaysPeriodsResponse)response.getResponse();
                    for(HolidayPeriod period : holidaysPeriodsResponse.getData().getResponse().getPeriodos()){
                        if(period.getIdu_folio() == (this.calendarProposedData.getPeriod().getIdu_folio())){
                            setMaxMonthCalendar(period);
                            setColaboratorMarkInCalendar(period);
                        }
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

        dialogFragmentGetDocument.close();
        if(changeStatusPeriod){
            ColaboratorHoliday colaboratorHoliday = new ColaboratorHoliday(this.holidayPeriod.getNom_empleado(),
                    this.holidayPeriod.getFotoperfil(),
                    this.holidayPeriod.getNum_empleado());

            CalendarProposedData data = new CalendarProposedData(calendarProposedData.getHolidaysPeriodsResponse(),holidayPeriod);
            data.setColaborator(colaboratorHoliday);
            data.setListDaySelected(calendarProposedData.getListDaySelected());

            data.setNum_anio(calendarProposedData.getNum_anio());
            data.setNum_mes(calendarProposedData.getNum_mes());
            // ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR,colaboratorHoliday);
            NavigationUtil.openActivityParamsSerializable(getActivity(), VacacionesActivity.class,
                    BUNDLE_OPTION_DATA_HOLIDAYS,data,
                    BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR);
            getActivity().finish();
            //vacacionesActivity.onEvent(,null);
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
            //getActivity().onBackPressed();
        }
    }

    /**Setear los dias en el calednario***/
    private void setColaboratorMarkInCalendar(List<HolidayPeriod> periods){

        HashMap<String,Day> daysInCalendar = new HashMap<>();
        List<Day>  listDaySelected = new ArrayList<>();
        for(HolidayPeriod period : periods){
            if(!period.getFec_ini().isEmpty() && !period.getFec_fin().isEmpty())
                setSelectedDays(period,daysInCalendar);
        }
        //Llenamos la lista con los dias con y sin empalmes
        for(String dateAsString : daysInCalendar.keySet()){
            listDaySelected.add(daysInCalendar.get(dateAsString));
        }


        HashMap<String,Day> daysCurrentOthersColaborators = new HashMap<>();
        HashMap<String,Day> daysCurrent = new HashMap<>();


        for(Day daySplice : daySelectedOtherSplices){
            daysCurrentOthersColaborators.put(String.format("%d%d%d",daySplice.getYear(),daySplice.getMonth(),daySplice.getDay()),daySplice);
        }

        for(Day dayCurrent : listDaySelected){
            daysCurrent.put(String.format("%d%d%d",dayCurrent.getYear(),dayCurrent.getMonth(),dayCurrent.getDay()),dayCurrent);
        }

        for(String key : daysCurrent.keySet()){
            daysCurrent.get(key).setHasSplice(0);
            if(daysCurrentOthersColaborators.containsKey(key)){
                daysCurrent.get(key).setHasSplice(daysCurrentOthersColaborators.get(key).getHasSplice());
            }
        }

        collapsibleCalendar.select(listDaySelected);

        String monthNameCalendar = collapsibleCalendar.getMonthCurrentTitle();
        monthName.setText(collapsibleCalendar.getMonthCurrentTitle());
    }

    private void setColaboratorMarkInCalendar(HolidayPeriod period){

        try {

            HashMap<String, Day> daysInCalendar = new HashMap<>();
            List<Day> listDaySelected = new ArrayList<>();
            //  for(HolidayPeriod period : periods){
            if (!period.getFec_ini().isEmpty() && !period.getFec_fin().isEmpty())
                setSelectedDays(period, daysInCalendar);
            //  }
            //Llenamos la lista con los dias con y sin empalmes
            for (String dateAsString : daysInCalendar.keySet()) {
                listDaySelected.add(daysInCalendar.get(dateAsString));
            }


            HashMap<String, Day> daysCurrentOthersColaborators = new HashMap<>();
            HashMap<String, Day> daysCurrent = new HashMap<>();


            for (Day daySplice : daySelectedOtherSplices) {
                daysCurrentOthersColaborators.put(String.format("%s%s%s", String.valueOf(daySplice.getYear()), daySplice.getMonth() > 9 ?
                        String.valueOf( daySplice.getMonth()) : "0" + daySplice.getMonth(),
                        daySplice.getDay() > 9 ? String.valueOf(daySplice.getDay()) : "0" + daySplice.getDay()), daySplice);
            }

            for (Day dayCurrent : listDaySelected) {
                daysCurrent.put(String.format("%s%s%s", String.valueOf(dayCurrent.getYear()), dayCurrent.getMonth() > 9 ?
                        String.valueOf(dayCurrent.getMonth()) : "0" + dayCurrent.getMonth(),
                        dayCurrent.getDay() > 9 ? String.valueOf(dayCurrent.getDay()) : "0" + dayCurrent.getDay()), dayCurrent);
            }

            for (String key : daysCurrent.keySet()) {
                daysCurrent.get(key).setHasSplice(0);
                if (daysCurrentOthersColaborators.containsKey(key)) {
                    daysCurrent.get(key).setHasSplice(daysCurrentOthersColaborators.get(key).getHasSplice());
                }
            }


            for (Day day : listDaySelected) {
                day.setHasSplice(-1);
                if (hasSplice(period.getVer_marca(), day)) {
                    day.setHasSplice(1);
                }
            }

            collapsibleCalendar.select(listDaySelected);

            String monthNameCalendar = collapsibleCalendar.getMonthCurrentTitle();
            monthName.setText(collapsibleCalendar.getMonthCurrentTitle());

        }catch (Exception e){
            //e.printStackTrace();
        }
    }


    private boolean hasSplice(List<MarkHoliday> markHolidays,Day day){
        for(MarkHoliday mark : markHolidays){
            for (String s : mark.getRango_dias()){
                if(Integer.parseInt(s) == day.getDay()){
                    return true;
                }
            }
        }

        return false;
    }

    private void setSelectedDays(HolidayPeriod period,HashMap<String,Day> daysInCalendar){

        String dateStart = period.getFec_ini().substring( period.getFec_ini().indexOf(" "),period.getFec_ini().length()).trim();
        String dateEnd = period.getFec_fin().substring(period.getFec_fin().indexOf(" "),period.getFec_fin().length()).trim();

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
                daysInCalendar.get(dateAsString).setHasSplice(holidayPeriod.getIdu_marca() == 1 ?  this.holidayPeriod.getIdu_marca() : -1);
            }else {
                Day dayToAdd = new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), holidayPeriod.getIdu_marca() == 1 ?  this.holidayPeriod.getIdu_marca() : -1);
                daysInCalendar.put(dateAsString,dayToAdd);
            }


            calendar.add(Calendar.DATE,1);

        }while (!calendar.after(calendar_Fin));
    }


    private void openObservationsCancel(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.setTitle("Justificar rechazo");
        authorizeHoliday.setDescription("Escribe el motivo de rechazo");
        authorizeHoliday.setHint("Ingresa tus razones aquí");
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }


    private void openObservationsEdit(List<HolidayPeriod> holidayPeriodSchedule) {
        DialogFragmentAuthorizeHoliday authorizeHoliday = DialogFragmentAuthorizeHoliday.newInstance();
        authorizeHoliday.setOnButtonClickListener(this);
        authorizeHoliday.setHolidayPeriodSchedule(holidayPeriodSchedule);
        authorizeHoliday.setTitle("Observaciones (Opcional)");
        authorizeHoliday.setDescription("Escribe el motivo por el cual modifica las vacaciones del colaborador");
        authorizeHoliday.setHint("Ingresa tus observaciones aquí");
        authorizeHoliday.show(parent.getSupportFragmentManager(), DialogFragmentAuthorizeHoliday.TAG);
    }

    @Override
    public void onLeftOptionObservationClick() {

    }

    @Override
    public void onRightOptionObservationClick(List<HolidayPeriod> holidayPeriodSchedule, String observations, DialogFragment dialogFragment) {
        if(action == 1){
            cancelPeriod(holidayPeriodSchedule,observations);
        }else if(action == 2){
            editPeriod(holidayPeriodSchedule,observations);
        }

        dialogFragment.dismiss();
    }

    private void cancelPeriod(List<HolidayPeriod> holidayPeriodSchedule,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(CANCEL_GTE_HOLIDAY, 9,Integer.parseInt(this.holidayPeriod.getNum_empleado()));
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_comentario(observations);
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();

        for(HolidayPeriod period : holidayPeriodSchedule){
            periodsToCancel.add(new HolidayPeriodFolio(period.getIdu_folio()));
        }

        holidayRequestData.setPeriodsChangeStatus(periodsToCancel);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }


    private void editPeriod(List<HolidayPeriod> holidayPeriodSchedule,String observations){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(EDIT_PERIOD_HOLIDAY, 12,Integer.parseInt(this.holidayPeriod.getNum_empleado()));
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setDes_observaciones(observations);
        List<HolidayPeriodData> periodsToEdit = new ArrayList<>();

        /*Formatear fecha*/


        for(HolidayPeriod period : holidayPeriodSchedule){

            String[] dateParts = period.getFec_ini().split("-");
            int month = Integer.parseInt(dateParts[1]) +1 ;
            period.setFec_ini(String.format("%s%s%s",dateParts[0],month > 9 ? month : "0"+month ,dateParts[2]));
            dateParts = period.getFec_fin().split("-");
            month = Integer.parseInt(dateParts[1]) +1 ;
            period.setFec_fin(String.format("%s%s%s",dateParts[0],month > 9 ? month : "0"+month ,dateParts[2]));

            periodsToEdit.add(new HolidayPeriodData(Double.parseDouble(period.getNum_dias()),period.getFec_ini(),period.getFec_fin(),period.getIdu_folio()));
        }

        holidayRequestData.setPeriodos(periodsToEdit);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }


    private void getPeriodsColaborators(String numEmployer, int tipo_consulta,int num_mes,int num_anio){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //Este colaborador es el gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_CALENDAR_DAYS_PROPOSED, 18);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_empconsulta(Integer.parseInt(numEmployer));
        holidayRequestData.setNum_mes(num_mes);
        holidayRequestData.setNum_anio(num_anio);
        /*Tipo de consulta*/
        holidayRequestData.setTipo_consulta(tipo_consulta);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }
}
