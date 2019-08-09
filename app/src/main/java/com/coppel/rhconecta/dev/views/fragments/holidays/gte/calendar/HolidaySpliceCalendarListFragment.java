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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.SpliceSelectedVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestColaboratorsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.capitalizeText;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDayNameFromDate;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getMonths;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidaySpliceCalendarListFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        HolidayRequestColaboratorsRecyclerAdapter.OnRequestSelectedClickListener,
      DialogFragmentWarning.OnOptionClick {

    public static final String TAG = HolidaySpliceCalendarListFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.layoutList)
    LinearLayout layoutList;
    @BindView(R.id.layoutCalendar)
    LinearLayout layoutCalendar;
   // @BindView(R.id.collapsibleCalendarView)
   // CollapsibleCalendar collapsibleCalendar;

    @BindView(R.id.weekCalendar)
    WeekCalendar weekCalendar;

    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
    @BindView(R.id.textDate)
    TextView textDate;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<HolidayPeriod> colaboratorsPeriodsHolidays;
    private HolidayRequestColaboratorsRecyclerAdapter holidayRequestRecyclerAdapter;
    private boolean EXPIRED_SESSION;
    private DialogFragmentWarning dialogFragmentWarning;
    private long mLastClickTime = 0;

    private Calendar currentDate;

    private SpliceSelectedVO spliceSelectedVO;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static HolidaySpliceCalendarListFragment getInstance(SpliceSelectedVO data){
        HolidaySpliceCalendarListFragment fragment = new HolidaySpliceCalendarListFragment();
        Bundle args = new Bundle();
        args.putSerializable("DATA",data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.spliceSelectedVO = (SpliceSelectedVO)getArguments().getSerializable("DATA");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empalme_calendario_vacaciones, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        colaboratorsPeriodsHolidays = new ArrayList<>();
        holidayRequestRecyclerAdapter = new HolidayRequestColaboratorsRecyclerAdapter(colaboratorsPeriodsHolidays,true);
        holidayRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(holidayRequestRecyclerAdapter);
        initializeCalendar();

        return view;
    }


    private void setTitlesDate(DateTime date){

        String month = getMonths()[date.getMonthOfYear() - 1];
        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(month);
        }

        textDate.setText(String.format("%s %s %s",capitalizeText(getContext(),getDayNameFromDate(date)),String.valueOf(date.getDayOfMonth()),month.substring(0,3)));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeCalendar(){

        DateTime startDate = new DateTime()
                .withDate(spliceSelectedVO.getDaySelected().getYear(),
                        spliceSelectedVO.getDaySelected().getMonth() + 1,
                        spliceSelectedVO.getDaySelected().getDay());
        weekCalendar.setStartDate(startDate);
        weekCalendar.setSelectedDate(startDate);
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                showPeriodsWithDaySelected(dateTime);
                setTitlesDate(dateTime);
            }

        });

        setTitlesDate(startDate);
        showPeriodsWithDaySelected(startDate);
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

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.HOLIDAYS:

                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.EXPENSESTRAVEL:
                    showWarningDialog(coppelServicesError.getMessage());
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
            }

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
        }else {
            dialogFragmentWarning.close();
           // getActivity().onBackPressed();
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
        ColaboratorHoliday colaboratorHoliday = new ColaboratorHoliday(holidayPeriod.getNom_empleado(),
                holidayPeriod.getFotoperfil(),
                holidayPeriod.getNum_empleado());
        ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR,colaboratorHoliday);
    }

    private void showPeriodsWithDaySelected(DateTime dateTime){
        this.colaboratorsPeriodsHolidays.clear();
        Calendar dateSelected = Calendar.getInstance();
        dateSelected.set(Calendar.YEAR,dateTime.getYear());
        dateSelected.set(Calendar.MONTH,dateTime.getMonthOfYear()-1);
        dateSelected.set(Calendar.DAY_OF_MONTH,dateTime.getDayOfMonth());

        for(HolidayPeriod period : spliceSelectedVO.getPeriodos()){
            if(validateDateInPeriod(dateSelected,period.getFec_ini(),period.getFec_fin())){
                this.colaboratorsPeriodsHolidays.add(period);
            }
        }

        holidayRequestRecyclerAdapter.notifyDataSetChanged();
    }

    private  boolean validateDateInPeriod( Calendar dateSelected ,String start,String end){
        String[] dateStartParts = null;
        String[] dateEndParts = null;

        dateStartParts = !start.contains(",") ? start.split("-") : start.split(",")[1].trim().split("-");
        dateEndParts = !end.contains(",") ? end.split("-") : end.split(",")[1].trim().split("-");

        Calendar dateStart = Calendar.getInstance();
        dateStart.set(
                Integer.parseInt(dateStartParts[2]),
               Integer.parseInt(dateStartParts[1]) - 1,
              Integer.parseInt(dateStartParts[0]),0,0,0);

        dateStart.set(Calendar.MILLISECOND,0);
        Date startDate = dateStart.getTime();

        Calendar dateEnd = Calendar.getInstance();
        dateEnd.set(
                Integer.parseInt(dateEndParts[2]),
                Integer.parseInt(dateEndParts[1]) - 1,
                Integer.parseInt(dateEndParts[0]),0,0,0);

        dateEnd.set(Calendar.MILLISECOND,0);

        Date endDate = dateEnd.getTime();
        dateSelected.set(Calendar.HOUR_OF_DAY,0);
        dateSelected.set(Calendar.MINUTE,0);
        dateSelected.set(Calendar.SECOND,0);
        dateSelected.set(Calendar.MILLISECOND,0);
        Date selectedDate = dateSelected.getTime();

        int i = startDate.compareTo(selectedDate);
        int endd = endDate.compareTo(selectedDate);

        if((startDate.compareTo(selectedDate) <= 0 && endDate.compareTo(selectedDate) >= 0)  ){
            return true;
        }

        return false;
    }

}
