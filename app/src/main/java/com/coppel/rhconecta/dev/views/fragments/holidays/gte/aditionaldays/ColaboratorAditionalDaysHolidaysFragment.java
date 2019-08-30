package com.coppel.rhconecta.dev.views.fragments.holidays.gte.aditionaldays;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaySendAditionalDaysResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysPeriodsResponse;
import com.coppel.rhconecta.dev.business.models.HolidaysValidationAditionalDaysResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.HolidayRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderHolidaysColaboratorGte;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentControlAditionalDays;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentDeletePeriods;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.CONSULTA_VACACIONES;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_PERIODS_COLABORATORS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.VALIDATION_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboratorAditionalDaysHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,HolidayRequestRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentControlAditionalDays.OnButonOptionReasonClick,DialogFragmentGetDocument.OnButtonClickListener{

    public static final String TAG = ColaboratorAditionalDaysHolidaysFragment.class.getSimpleName();
    private AppCompatActivity parent;

    @BindView(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    @BindView(R.id.headerHoliday)
    HeaderHolidaysColaboratorGte headerHoliday;
    @BindView(R.id.btnAditionalDays)
    Button btnAditionalDays;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentWarning dialogFragmentWarning;
    private boolean EXPIRED_SESSION;
    private IScheduleOptions IScheduleOptions;
    private ColaboratorHoliday colaboratorHoliday;
    private HolidaysPeriodsResponse holidaysPeriodsResponse;
    private HolidaysValidationAditionalDaysResponse holidaysValidationAditionalDaysResponse;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private DialogFragmentDeletePeriods dialogFragmentDeletePeriods;
    private VacacionesActivity vacacionesActivity;
    private long mLastClickTime = 0;

    private boolean updateInfoColaborator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IScheduleOptions = (VacacionesActivity)getActivity();
        vacacionesActivity = (VacacionesActivity)getActivity();
    }

    public static ColaboratorAditionalDaysHolidaysFragment getInstance(ColaboratorHoliday colaboratorHoliday){
        ColaboratorAditionalDaysHolidaysFragment fragment = new ColaboratorAditionalDaysHolidaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_OPTION_DATA_HOLIDAYS,colaboratorHoliday);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.colaboratorHoliday = (ColaboratorHoliday) getArguments().getSerializable(BUNDLE_OPTION_DATA_HOLIDAYS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_adicionales_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_add_aditional_days_toolbar));
        }

        btnAditionalDays.setOnClickListener(this);

        //headerHoliday.setDetailData(configurationHolidaysData.getHolidaysPeriodsResponse());
        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHolidaysPeriods(this.colaboratorHoliday.getNum_empleado());
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
            case R.id.btnAditionalDays:
                validateAditionalDays(this.colaboratorHoliday.getNum_empleado());
                break;
        }
    }

    private void openControlAditionalDays(int maxDays){
        String days = String.valueOf(holidaysPeriodsResponse.getData().getResponse().getNum_adicionales());
        days = days.substring(0,days.indexOf("."));
        int pendingAditionalDays = Integer.parseInt(days);
        DialogFragmentControlAditionalDays controlAditionalDays = DialogFragmentControlAditionalDays.newInstance(maxDays,pendingAditionalDays);
        controlAditionalDays.setOnButtonClickListener(this);
        controlAditionalDays.show(parent.getSupportFragmentManager(), DialogFragmentControlAditionalDays.TAG);


        /*TEclado no se oculta*/
        /*controlAditionalDays.getReason().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if(s.toString().contains("\n")){
                    String text = s.toString().replace("\n","");
                    controlAditionalDays.getReason().setText(text);
                    controlAditionalDays.getReason().setSelection(text.length());

                    DeviceManager.hideKeyBoard(getActivity());
                    return;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

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
                    headerHoliday.setDetailData(this.colaboratorHoliday,holidaysPeriodsResponse);
                    layoutContainer.setVisibility(VISIBLE);
                }else if(response.getResponse() instanceof HolidaysValidationAditionalDaysResponse) {
                    holidaysValidationAditionalDaysResponse = (HolidaysValidationAditionalDaysResponse) response.getResponse();
                    //Mostramos control de dias adicionales
                    openControlAditionalDays(holidaysValidationAditionalDaysResponse.getData().getResponse().getNun_diasadicionales());

                    /**Si el campo nun_diasadicionales es 0, mostrar en un mensaje la variable des_mensaje.*/
                    if(holidaysValidationAditionalDaysResponse.getData().getResponse().getNun_diasadicionales() == 0){
                        showWarningDialog("",holidaysValidationAditionalDaysResponse.getData().getResponse().getDes_mensaje());
                    }
                }else if(response.getResponse() instanceof HolidaySendAditionalDaysResponse) {
                    HolidaySendAditionalDaysResponse aditionalDaysResponse = (HolidaySendAditionalDaysResponse)response.getResponse();
                    showSuccessDialog(MSG_HOLIDAYS_OK,aditionalDaysResponse.getData().getResponse().getDes_mensaje(), "");
                    updateInfoColaborator = true;
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
        if(updateInfoColaborator){
            //Actualizamos la información de Dias adicionales
            getHolidaysPeriods(this.colaboratorHoliday.getNum_empleado());
        }

        dialogFragmentGetDocument.close();
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

    @Override
    public void onLeftOptionReasonClick() {

    }

    @Override
    public void onRightOptionReasonClick(HolidayRequestData data, DialogFragment dialogFragment) {
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        data.setNum_empleado(this.colaboratorHoliday.getNum_empleado());//Agregamos el número de empleado
        coppelServicesPresenter.getHolidays(data,token);

        dialogFragment.dismiss();
    }

    private void getHolidaysPeriods(String numEmployer ){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        //Este colaborador es el gte
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData  holidayRequestData = new HolidayRequestData(GET_PERIODS_COLABORATORS, 8,numEmployer);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    private void validateAditionalDays(String numEmployer){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData  holidayRequestData = new HolidayRequestData(VALIDATION_ADITIONAL_DAYS, 15,numEmployer);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }



}
