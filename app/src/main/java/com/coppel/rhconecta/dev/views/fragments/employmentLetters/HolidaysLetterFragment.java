package com.coppel.rhconecta.dev.views.fragments.employmentLetters;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersGenerateRequest;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterHolidayData;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DateTimeUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.dialogs.MyDatePickerFragment;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;

public class HolidaysLetterFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick{

    public static final String TAG = HolidaysLetterFragment.class.getSimpleName();
    private ConfigLetterActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private LetterConfigResponse letterConfigResponse;

    @BindView(R.id.dateStartHolidayLayout)
    RelativeLayout dateStartHolidayLayout;
    @BindView(R.id.dateEndHolidayLayout)
    RelativeLayout dateEndHolidayLayout;
    private long mLastClickTime = 0;

    @BindView(R.id.dateStartHoliday)
    TextView dateStartHoliday;
    @BindView(R.id.dateEndHoliday)
    TextView dateEndHoliday;
    @BindView(R.id.btnNext)
    Button btnNext;

    private boolean hasStamp;
    private DialogFragmentWarning dialogFragmentWarning;
    private com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation ILettersNavigation;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;
    private boolean WARNING_PERMISSIONS;

    private boolean EXPIRED_SESSION;

    private Calendar startDate;
    private Calendar endDate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ILettersNavigation = (com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation) context;
    }

    public static HolidaysLetterFragment getInstance(int tipoCarta){
        HolidaysLetterFragment fragment = new HolidaysLetterFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays_letter, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (ConfigLetterActivity) getActivity();
        //parent.setToolbarTitle(getString(R.string.bonus));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        dateStartHolidayLayout.setOnClickListener(this);
        dateEndHolidayLayout.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

            case R.id.dateStartHolidayLayout:

                showDatePicker(true);
                break;

            case R.id.dateEndHolidayLayout:

                if(dateStartHoliday.getText() != null || !dateStartHoliday.getText().toString().isEmpty()) {
                    showDatePicker(false);
                }else {
                    Toast.makeText(getActivity(),"Debes seleccionar una fecha de salida.",Toast.LENGTH_SHORT).show();
                }

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


    private  void goNextStep(){
        if((dateStartHoliday.getText() != null && !dateStartHoliday.getText().toString().isEmpty()) &&
                (dateEndHoliday.getText() != null && !dateEndHoliday.getText().toString().isEmpty())){

            showAlertStampLetter();

        }else {
            Toast.makeText(getActivity(),"Debes llenar todos los datos",Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePicker(boolean isStart){
        //MyDatePickerFragment newFragment = new MyDatePickerFragment();
        DatePickerDialog datePickerDialog = DateTimeUtil.getMaterialDatePicker(isStart ? dateSetListenerStart : dateSetListenerEnd);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorDaySelect));
        datePickerDialog.setCustomTitle(isStart ? "Inicio de vacaciones" : "Regreso de vacaciones");
        Calendar today = Calendar.getInstance();
        datePickerDialog.setMinDate( isStart ?  today : (startDate != null ? startDate : today));
        datePickerDialog.show(getActivity().getFragmentManager(),"DatePickerDialog");

    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LETTERPREVIEW:
                LetterPreviewResponse letterPreviewResponse = (LetterPreviewResponse) response.getResponse();
                PreviewDataVO previewDataVO = new PreviewDataVO();
                previewDataVO.setDataLetter(letterPreviewResponse.getData());
                previewDataVO.setFieldsLetter(parent.getPreviewDataVO().getFieldsLetter());
                previewDataVO.setHasStamp(hasStamp);

                /***/
                PreviewDataVO previewDataVOOParent = parent.getPreviewDataVO();
                CoppelServicesLettersGenerateRequest.Data dataOptional =  previewDataVOOParent.getDataOptional();

                previewDataVO.setDataOptional(dataOptional);
                ILettersNavigation.showFragmentAtPosition(4,previewDataVO);
                break;
        }

    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSCONFIG:

                break;
            case ServicesRequestType.INVALID_TOKEN:
                EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
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
    public void showProgress() {
   //    dialogFragmentLoader = new DialogFragmentLoader();
    //   dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
   //    dialogFragmentLoader.close();
    }



    DatePickerDialog.OnDateSetListener dateSetListenerStart = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {

            dateEndHolidayLayout.setEnabled(true);
            dateEndHoliday.setText("");

            if(startDate == null)
                startDate = Calendar.getInstance();
            startDate.set(Calendar.YEAR ,year);
            startDate.set(Calendar.MONTH ,month);
            startDate.set(Calendar.DAY_OF_MONTH ,dayOfMonth);

            dateStartHoliday.setText(DateTimeUtil.getDateAsSttring(dayOfMonth,month,year));
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListenerEnd= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {

            if(endDate == null)
                endDate = Calendar.getInstance();

            endDate.set(Calendar.YEAR ,year);
            endDate.set(Calendar.MONTH ,month);
            endDate.set(Calendar.DAY_OF_MONTH ,dayOfMonth);


            dateEndHoliday.setText(DateTimeUtil.getDateAsSttring(dayOfMonth,month,year));



        }
    };


    private PreviewDataVO getData(){
        PreviewDataVO previewDataVO = parent.getPreviewDataVO();
        CoppelServicesLettersGenerateRequest.Data dataOptional =  previewDataVO.getDataOptional();
         LetterHolidayData letterHolidayData = new LetterHolidayData(dateStartHoliday.getText().toString(),
                 dateEndHoliday.getText().toString());

        dataOptional.setLetterHolidayData(letterHolidayData);
        return previewDataVO;
    }

    private void showAlertStampLetter(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setTwoOptionsData(getString(R.string.title_stamp_letter), getString(R.string.question_stamp_letter), getString(R.string.no),getString(R.string.yes));
                dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                    @Override
                    public void onLeftOptionClick() {
                        getPreviewLetter(false);
                        dialogFragmentWarning.close();
                    }

                    @Override
                    public void onRightOptionClick() {
                        getPreviewLetter(true);
                        dialogFragmentWarning.close();
                    }
                });
                dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
            }
        }, 200);
    }


    private void getPreviewLetter(boolean stampLetter){
        this.hasStamp = stampLetter;
        PreviewDataVO previewDataVO = getData();
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        coppelServicesPresenter.requestLettersPreviewView(numEmployer,TYPE_KINDERGARTEN,previewDataVO.getFieldsLetter(),
                previewDataVO.getDataOptional(), stampLetter,token);
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        } else if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1])) {
                AppUtilities.openAppSettings(parent);
            }
        }
        WARNING_PERMISSIONS = false;
        dialogFragmentWarning.close();

    }
}