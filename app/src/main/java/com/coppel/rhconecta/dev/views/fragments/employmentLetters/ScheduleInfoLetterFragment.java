package com.coppel.rhconecta.dev.views.fragments.employmentLetters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersGenerateRequest;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterJobScheduleData;
import com.coppel.rhconecta.dev.business.models.LetterScheduleData;
import com.coppel.rhconecta.dev.business.models.LetterSchedulesDataVO;
import com.coppel.rhconecta.dev.business.models.LetterSectionScheduleData;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentScheduleData;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.utils.DateTimeUtil.getHoursCount;
import static com.coppel.rhconecta.dev.business.utils.DateTimeUtil.getNumberDays;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;

public class ScheduleInfoLetterFragment extends Fragment implements View.OnClickListener,

        IServicesContract.View,DialogFragmentScheduleData.OnButonOptionClick, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = ScheduleInfoLetterFragment.class.getSimpleName();
    private ConfigLetterActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private LetterConfigResponse letterConfigResponse;

    @BindView(R.id.startDayLayout)
    RelativeLayout startDayLayout;
    @BindView(R.id.endDayLayout)
    RelativeLayout endDayLayout;
    @BindView(R.id.startHourLayout)
    RelativeLayout startHourLayout;
    @BindView(R.id.endHourLayout)
    RelativeLayout endHourLayout;
    @BindView(R.id.eatingTimeLayout)
    RelativeLayout eatingTimeLayout;

    @BindView(R.id.startDay)
    TextView startDay;
    @BindView(R.id.endDay)
    TextView endDay;
    @BindView(R.id.starHour)
    TextView starHour;
    @BindView(R.id.endHour)
    TextView endHour;
    @BindView(R.id.eatingTime)
    TextView eatingTime;
    @BindView(R.id.rdgStartHour)
    RadioGroup rdgStartHour;
    @BindView(R.id.rdgEndHour)
    RadioGroup rdgEndHour;
    @BindView(R.id.rdbAmStart)
    RadioButton rdbAmStart;
    @BindView(R.id.rdbAmEnd)
    RadioButton rdbAmEnd;

    @BindView(R.id.btnNext)
    Button btnNext;

    private DialogFragmentWarning dialogFragmentWarning;

    private long mLastClickTime = 0;
    private DialogFragmentScheduleData dialogFragmentScheduleData;
    private LetterConfigResponse.DatosHorario datosHorario;
    private com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation ILettersNavigation;
    private List<LetterConfigResponse.DiaLaboral> diaLaborales;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ILettersNavigation = (com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation) context;
    }

    public static ScheduleInfoLetterFragment getInstance(int tipoCarta){
        ScheduleInfoLetterFragment fragment = new ScheduleInfoLetterFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_letter, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (ConfigLetterActivity) getActivity();
        //parent.setToolbarTitle(getString(R.string.bonus));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        startDayLayout.setOnClickListener(this);
        endDayLayout.setOnClickListener(this);
        startHourLayout.setOnClickListener(this);
        endHourLayout.setOnClickListener(this);
        eatingTimeLayout.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        btnNext.setVisibility(View.INVISIBLE);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaLaborales = new ArrayList<>();
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Lunes"));
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Martes"));
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Miércoles"));
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Jueves"));
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Viernes"));
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Sábado"));
        diaLaborales.add(new LetterConfigResponse.DiaLaboral("Domingo"));
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.startDayLayout:
                showHDaysJob(this.diaLaborales, DialogFragmentScheduleData.SCHEDULETYPE.START_DAY);
                break;

            case R.id.endDayLayout:
                showHDaysJob(this.diaLaborales, DialogFragmentScheduleData.SCHEDULETYPE.END_DAY);
                break;

            case R.id.startHourLayout:
                showHoursJob(this.datosHorario.getHorario_Laboral(), DialogFragmentScheduleData.SCHEDULETYPE.START_HOUR_WORK);
                break;

            case R.id.endHourLayout:
                showHoursJob(this.datosHorario.getHorario_Laboral(), DialogFragmentScheduleData.SCHEDULETYPE.END_HOUR_WORK);
                break;

            case R.id.eatingTimeLayout:
                showHoursLunch(this.datosHorario.getHorario_Comida());
                break;

            case R.id.btnNext:

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                goNextStep();
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
    }

    @Override
    public void showProgress() {
      //  dialogFragmentLoader = new DialogFragmentLoader();
       // dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
     //   dialogFragmentLoader.close();
    }

    public void setDataSchedule(LetterConfigResponse.DatosHorario datosHorario){
        this.datosHorario = datosHorario;
    }

    private void showHoursJob(List<LetterConfigResponse.HorarioLaboral> horarioLaboral, DialogFragmentScheduleData.SCHEDULETYPE scheduletype){
        LetterSchedulesDataVO letterSchedulesDataVO = new LetterSchedulesDataVO();

        List<LetterConfigResponse.HorarioLaboral> resetSchedule = new ArrayList<>();
        for(LetterConfigResponse.HorarioLaboral schedule : horarioLaboral){
            schedule.setSelected(false);
            resetSchedule.add(schedule);
        }
        letterSchedulesDataVO.setData(horarioLaboral);
        dialogFragmentScheduleData = DialogFragmentScheduleData.newInstance(letterSchedulesDataVO, R.layout.dialog_fragment_scheduledata);
        dialogFragmentScheduleData.setOnButtonClickListener(this);
        dialogFragmentScheduleData.setScheduletypeSelectd(scheduletype);
        dialogFragmentScheduleData.show(parent.getSupportFragmentManager(), DialogFragmentScheduleData.TAG);
    }

    private void showHDaysJob(List<LetterConfigResponse.DiaLaboral> diaLaborales, DialogFragmentScheduleData.SCHEDULETYPE scheduletype){
        LetterSchedulesDataVO letterSchedulesDataVO = new LetterSchedulesDataVO();
        List<LetterConfigResponse.DiaLaboral> resetSchedule = new ArrayList<>();
        for(LetterConfigResponse.DiaLaboral schedule : diaLaborales){
            schedule.setSelected(false);
            resetSchedule.add(schedule);
        }
        letterSchedulesDataVO.setData(resetSchedule);
        dialogFragmentScheduleData = DialogFragmentScheduleData.newInstance(letterSchedulesDataVO, R.layout.dialog_fragment_scheduledata_short);
        dialogFragmentScheduleData.setOnButtonClickListener(this);
        dialogFragmentScheduleData.setScheduletypeSelectd(scheduletype);
        dialogFragmentScheduleData.show(parent.getSupportFragmentManager(), DialogFragmentScheduleData.TAG);
    }

    private void showHoursLunch(List<LetterConfigResponse.HorarioComida> horarioComidas){
        LetterSchedulesDataVO letterSchedulesDataVO = new LetterSchedulesDataVO();

        List<LetterConfigResponse.HorarioComida> resetSchedule = new ArrayList<>();
        for(LetterConfigResponse.HorarioComida schedule : horarioComidas){
            schedule.setSelected(false);
            resetSchedule.add(schedule);
        }


        letterSchedulesDataVO.setData(resetSchedule);
        dialogFragmentScheduleData = DialogFragmentScheduleData.newInstance(letterSchedulesDataVO, R.layout.dialog_fragment_scheduledata_short);
        dialogFragmentScheduleData.setOnButtonClickListener(this);
        dialogFragmentScheduleData.setScheduletypeSelectd(DialogFragmentScheduleData.SCHEDULETYPE.TIME_LUNCH);
        dialogFragmentScheduleData.show(parent.getSupportFragmentManager(), DialogFragmentScheduleData.TAG);
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentScheduleData.close();
    }

    @Override
    public void onRightOptionClick(DialogFragmentScheduleData.SCHEDULETYPE scheduletype, String data) {

        switch (scheduletype){
            case START_DAY:
                startDay.setText(data);
                break;
            case END_DAY:
                endDay.setText(data);
                break;
            case START_HOUR_WORK:
                starHour.setText(data);
                break;
            case END_HOUR_WORK:
                endHour.setText(data);
                break;
            case TIME_LUNCH:
                eatingTime.setText(data);
                break;
        }

        String dayStart =  startDay.getText()!= null ? startDay.getText().toString() : "";
        String dayEnd =  endDay.getText()!= null ? endDay.getText().toString() : "";
        String hourStart =  starHour.getText()!= null && !starHour.getText().toString().isEmpty() ? String.format("%s %s",starHour.getText().toString() ,
                rdbAmStart.isChecked() ? "AM" : "PM"): "";
        String hourEnd =  endHour.getText()!= null && !endHour.getText().toString().isEmpty() ? String.format("%s %s",endHour.getText().toString() ,
                rdbAmEnd.isChecked() ? "AM" : "PM"): "";

        calculateHours(dayStart,dayEnd,hourStart,hourEnd);

        dialogFragmentScheduleData.close();
    }

    private  void goNextStep(){
        if((startDay.getText() != null && !startDay.getText().toString().isEmpty()) &&
           (endDay.getText() != null && !endDay.getText().toString().isEmpty()) &&
           (starHour.getText() != null && !starHour.getText().toString().isEmpty()) &&
           (endHour.getText() != null && !endHour.getText().toString().isEmpty()) &&
           (eatingTime.getText() != null && !eatingTime.getText().toString().isEmpty())){
            ILettersNavigation.showFragmentAtPosition(3,getData());
        }else {
            Toast.makeText(getActivity(),"Debes llenar todos los datos",Toast.LENGTH_SHORT).show();
        }
    }

    private PreviewDataVO getData(){
        PreviewDataVO previewDataVO = parent.getPreviewDataVO();
        CoppelServicesLettersGenerateRequest.Data dataOptional =  previewDataVO.getDataOptional();
        String hourStart = String.format("%s %s",starHour.getText().toString(),rdbAmStart.isChecked() ? "am" : "pm");
        String hourEnd = String.format("%s %s",endHour.getText().toString(), rdbAmEnd.isChecked() ? "am" : "pm");
        LetterJobScheduleData letterJobScheduleData = new LetterJobScheduleData(hourStart,hourEnd);
        LetterScheduleData scheduleData = new LetterScheduleData(eatingTime.getText().toString());
        LetterSectionScheduleData letterSectionScheduleData = new LetterSectionScheduleData(startDay.getText().toString(),endDay.getText().toString());

        dataOptional.setJobScheduleData(letterJobScheduleData);
        dataOptional.setScheduleData(scheduleData);
        dataOptional.setSectionScheduleData(letterSectionScheduleData);
        return previewDataVO;
    }

    private void calculateHours(String startDay,String endDay,String startHour,String endHour){
        if(!startDay.isEmpty() && !endDay.isEmpty() && !startHour.isEmpty() && !endHour.isEmpty()){
            int dayNumbers = getNumberDays(startDay, endDay);
            int numberHours = getHoursCount(startHour, endHour);

            int total =  dayNumbers * numberHours;

            if(total > 48){
                showMessageUser(getString(R.string.max_hours_job));
                btnNext.setVisibility(View.INVISIBLE);
            }else {
                btnNext.setVisibility(View.VISIBLE);
            }
        }

    }

    private void showMessageUser(String msg){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), msg, getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(ScheduleInfoLetterFragment.this);
                dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
            }
        }, 100);
    }

    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();

    }
}