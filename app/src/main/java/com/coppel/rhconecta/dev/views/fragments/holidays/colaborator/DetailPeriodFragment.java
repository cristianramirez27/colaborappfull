package com.coppel.rhconecta.dev.views.fragments.holidays.colaborator;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.HolidayGetDetailPeriodResponse;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodFolio;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysCancelResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CANCEL_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIOD_DETAIL;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
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
public class DetailPeriodFragment extends Fragment implements  View.OnClickListener, DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener,
        IServicesContract.View{

    public static final String TAG = DetailPeriodFragment.class.getSimpleName();
    private AppCompatActivity parent;
    private DialogFragmentWarning dialogFragmentWarning;


    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    @BindView(R.id.bordeAmarillo)
    View bordeAmarillo;
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
    @BindView(R.id.fechaRechazo)
    TextView fechaRechazo;
    @BindView(R.id.motivoRechazo)
    TextView motivoRechazo;

    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtReason)
    TextView txtReason;


    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.collapsibleCalendarView)
    CollapsibleCalendar collapsibleCalendar;

    private IScheduleOptions IScheduleOptions;
    private VacacionesActivity vacacionesActivity;


    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private HolidayPeriod holidayPeriod;

    private boolean isCanceled;

    private long mLastClickTime = 0;

          private boolean EXPIRED_SESSION;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions= (IScheduleOptions)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static DetailPeriodFragment getInstance(HolidayPeriod holidayPeriod){
        DetailPeriodFragment fragment = new DetailPeriodFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_HOLIDAYS,holidayPeriod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holidayPeriod = (HolidayPeriod)getArguments().getSerializable(BUNDLE_OPTION_DATA_HOLIDAYS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_periodo, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_period_holidays));
        }

        btnCancel.setOnClickListener(this);
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);


        //To hide or show expand icon
       initializeCalendar();
       expObservaciones.setColorText(getResources().getColor(R.color.colorTextGrayDark));
        expObservaciones.setText("Observaciones");
        expObservaciones.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                observacionesStateChange(expObservaciones,layoutObservaciones);
            }
        });

        return view;
    }

    private void initializeCalendar(){
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
    }

    private void observacionesStateChange(ExpandableSimpleTitle expandable, LinearLayout layoutToExpand) {
        if (expandable.isExpanded()) {
            layoutToExpand.setVisibility(VISIBLE);
        } else {
            layoutToExpand.setVisibility(View.GONE);
        }
    }


    private void setDataHeader(HolidayGetDetailPeriodResponse response){
        HolidayGetDetailPeriodResponse.Response detail = response.getData().getResponse().get(0);
         fechaInicio.setText(detail.getFec_ini());
         fechaFin.setText(detail.getFec_fin());

        String days = detail.getNum_dias().split(" ")[0];
        double daysNumber = Double.parseDouble(days);
        if(daysNumber % 1 == 0){
            days = days.substring(0,days.indexOf("."));
            days = String.valueOf(Integer.parseInt(days));
        }

        diasVacaciones.setText(String.format("%s %s" , days , daysNumber > 1 ? "días" : "día"));
        estatus.setText(detail.getDes_estatus());
        if( this.holidayPeriod.getColorletra() != null && ! this.holidayPeriod.getColorletra().isEmpty())
            estatus.setTextColor( Color.parseColor( this.holidayPeriod.getColorletra()));


        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(detail.getColor()));
        gd.setCornerRadius(40);
        estatus.setBackgroundDrawable(gd);

        txtDate.setText(getDateTitle(holidayPeriod.getIdu_estatus()));
        txtReason.setText(getReasonTitle(holidayPeriod.getIdu_estatus()));

        nombreGte.setText(detail.getNom_gerente());
        fechaRechazo.setText(detail.getFec_estatus());
        motivoRechazo.setText(detail.getDes_comentario());
        btnCancel.setVisibility(showCheckOption(holidayPeriod) ? VISIBLE : View.GONE);

        if(response.getData().getResponse().get(0).getIdu_estatus() >= 2 &&
                response.getData().getResponse().get(0).getIdu_estatus() <= 4 ){
            hideCalendar();
        }else {
            setSelectedDays(response);
        }

    }

    private String getDateTitle(int status){
        switch (status){
            case  1:
                return "Fecha";

            case  2:
                return "Fecha de Rechazo";
            case  3:
                return "Fecha de Cancelación";
        }
        return "Fecha";
    }

    private String getReasonTitle(int status){
        switch (status){
            case  1:
                return "Motivo";
            case  2:
                return "Motivo de Rechazo";
            case  3:
                return "Motivo de Cancelación";
        }

        return "Motivo";
    }

    private void hideCalendar(){
        expObservaciones.setVisibility(View.GONE);
        layoutObservaciones.setVisibility(VISIBLE);
        layoutObservaciones.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        bordeAmarillo.setVisibility(View.GONE);
        collapsibleCalendar.setVisibility(View.GONE);

        btnCancel.setVisibility(View.GONE);
    }

    private void setSelectedDays(HolidayGetDetailPeriodResponse response){

        String dateStart = response.getData().getResponse().get(0).getFec_ini();
        String dateEnd = response.getData().getResponse().get(0).getFec_fin();

        String[] dateProcess = null;
        String[] dateParts = null;
        int year;
        int month;
        int day;
        //Obtenemos la fecha inicial
        dateProcess = dateStart.split(",");
        dateParts = dateProcess[1].trim().split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.set(year,month,day);
        //Obtenemos la fecha final
        dateProcess = dateEnd.split(",");
        dateParts = dateProcess[1].trim().split("-");
        year = Integer.parseInt(dateParts[2]);
        month = Integer.parseInt(dateParts[1])-1;
        day = Integer.parseInt(dateParts[0]);
        Calendar calendar_Fin = Calendar.getInstance();
        calendar_Fin.set(year,month,day);

        Calendar  calendar = Calendar.getInstance();
        calendar.set(calendar_Ini.get(Calendar.YEAR),calendar_Ini.get(Calendar.MONTH),calendar_Ini.get(Calendar.DAY_OF_MONTH));

        CalendarAdapter adapter = new CalendarAdapter(getActivity(), calendar);
        collapsibleCalendar.setAdapter(adapter);

        List<Day> listDaySelected = new ArrayList<>();

        do{
            listDaySelected.add(new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));
            calendar.add(Calendar.DATE,1);

        }while (!calendar.after(calendar_Fin));


        collapsibleCalendar.select(listDaySelected);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDetailPeriod();
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
            case R.id.btnCancel:

                cancelRequest();


                break;
        }
    }


    private void cancelRequest(){
        showAlertDialogDeletePeriods();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof HolidayGetDetailPeriodResponse) {
                    HolidayGetDetailPeriodResponse responseDetail = (HolidayGetDetailPeriodResponse)response.getResponse();
                    setDataHeader(responseDetail);
                    layoutContainer.setVisibility(VISIBLE);
                }else if(response.getResponse() instanceof HolidaysCancelResponse) {
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
            NavigationUtil.openActivityWithStringParam(getActivity(), VacacionesActivity.class,
                    BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS);
            getActivity().finish();
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

    private void getDetailPeriod(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData  holidayRequestData = new HolidayRequestData(GET_PERIOD_DETAIL, 4,numEmployer);
        holidayRequestData.setIdu_folio(holidayPeriod.getIdu_folio());
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
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


    private void showAlertDialogDeletePeriods() {
        dialogFragmentDeletePeriods = new DialogFragmentDeletePeriods();
        dialogFragmentDeletePeriods.setOnOptionClick(new DialogFragmentDeletePeriods.OnOptionClick() {
            @Override
            public void onAccept() {
                cancelHolidayRequest();
                IScheduleOptions.showEliminatedOption(false,"");
                dialogFragmentDeletePeriods.close();
            }

            @Override
            public void onCancel() {

                dialogFragmentDeletePeriods.close();

            }
        });

        dialogFragmentDeletePeriods.setTitle("Atención");
        dialogFragmentDeletePeriods.setMsg("¿Quieres cancelar las vacaciones?");
        dialogFragmentDeletePeriods.setVisibleCancelButton(VISIBLE);
        dialogFragmentDeletePeriods.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void cancelHolidayRequest(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_GTE);
        String numSuplente = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_SUPLENTE);

        HolidayRequestData holidayRequestData = new HolidayRequestData(CANCEL_HOLIDAYS, 5,numEmployer);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setNum_suplente(Integer.parseInt(numSuplente));
        List<HolidayPeriodFolio> periodsToCancel = new ArrayList<>();
        periodsToCancel.add(new HolidayPeriodFolio(holidayPeriod.getIdu_folio()));
        holidayRequestData.setPeriodsChangeStatus(periodsToCancel);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);

    }

    private boolean showCheckOption(HolidayPeriod period){

        /**Validar que la fecha de inicio sea mayor a la fecha actual*/
        String[] dateFormat =  period.getFec_ini().split(",")[1].split("-");
        Calendar datePeriod = Calendar.getInstance();
        datePeriod.set(Calendar.YEAR,Integer.parseInt(dateFormat[2].trim()));
        datePeriod.set(Calendar.MONTH,Integer.parseInt(dateFormat[1].trim())-1);
        datePeriod.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateFormat[0].trim()));
        Calendar today = Calendar.getInstance();
        if(!datePeriod.after(today)){
            return false;
        }


        return true;
    }


}
