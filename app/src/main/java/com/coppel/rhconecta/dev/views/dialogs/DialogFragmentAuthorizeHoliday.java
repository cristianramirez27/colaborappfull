package com.coppel.rhconecta.dev.views.dialogs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IControlViews;
import com.coppel.rhconecta.dev.business.interfaces.IDialogControlKeboard;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.HolidayPeriodData;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysReasonsAditionalDaysResponse;
import com.coppel.rhconecta.dev.business.models.ReasonAditionaDay;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.adapters.ReasonAditionalDayRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_REASON_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SCHEDULE_GTE_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_HOLIDAY_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getDateFormatToHolidays;

public class DialogFragmentAuthorizeHoliday extends DialogFragment implements View.OnClickListener ,
        IServicesContract.View {

    public static final String TAG = DialogFragmentAuthorizeHoliday.class.getSimpleName();
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_DATA_PENDING = "KEY_DATA_PENDING";
    private OnButonOptionObservationClick OnButonOptionObservationClick;
    private Context context;
    private String title;
    private String description;
    private String hint;
    private IDialogControlKeboard IDialogControlKeboard;

    /*Back Views*/
    @BindView(R.id.title)
    TextView txtTitle;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.observations)
    EditText observations;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;
    List<HolidayPeriod> holidayPeriodSchedule;


    public static DialogFragmentAuthorizeHoliday newInstance(){
        DialogFragmentAuthorizeHoliday fragmentScheduleData = new DialogFragmentAuthorizeHoliday();
        Bundle args = new Bundle();
        //args.putInt(KEY_DATA_PENDING, pendingAditionalDays);
        fragmentScheduleData.setArguments(args);
        return fragmentScheduleData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_observation_authorize_holiday, container, false);
        ButterKnife.bind(this, view);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        initViews();
        return view;
    }

    public void setHolidayPeriodSchedule(List<HolidayPeriod> holidayPeriodSchedule) {
        this.holidayPeriodSchedule = holidayPeriodSchedule;
    }

    private void initViews() {
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);
        btnActionRight.setEnabled(false);


        if(title.contains("(Opcional)")){
            SpannableString ss1=  new SpannableString(title);
            ss1.setSpan(new RelativeSizeSpan(0.60f), title.indexOf("("),title.length(), 0); // set size
            txtTitle.setText(ss1);
            btnActionRight.setBackgroundResource(R.drawable.background_blue_rounded);
            btnActionRight.setEnabled(true);
        }else {
            txtTitle.setText(title);
        }

        subtitle.setText(description);
        observations.setHint(hint);
        //reason.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        observations.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        observations.setText("");
        observations.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId==EditorInfo.IME_ACTION_NEXT){

                }
                return false;
            }
        });



        observations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains("\n")){
                    String text = s.toString().replace("\n","");
                    observations.setText(text);
                    observations.setSelection(text.length());
                    hide_keyboard_from(getActivity(),observations);
                    //IDialogControlKeboard.showKeyboard(false,txtTitle);
                    return;
                }


                if(!title.contains("(Opcional)")){

                    btnActionRight.setEnabled(s.toString().trim().length() > 4 ? true : false);
                    btnActionRight.setBackgroundResource(s.toString().trim().length() > 4 ?
                            R.drawable.background_blue_rounded :  R.drawable.backgroud_rounder_grey);
                }

                //hideKeyboard();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void hide_keyboard_from(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (OnButonOptionObservationClick != null) {
            switch (view.getId()) {
                case R.id.btnActionLeft:
                    close();
                    OnButonOptionObservationClick.onLeftOptionObservationClick();
                    break;
                case R.id.btnActionRight:

                    OnButonOptionObservationClick.onRightOptionObservationClick(this.holidayPeriodSchedule,observations.getText().toString(),this);
                    break;
            }
        }
    }

    public interface OnButonOptionObservationClick {
        void onLeftOptionObservationClick();
        void onRightOptionObservationClick(List<HolidayPeriod> holidayPeriodSchedule,String observations, DialogFragment dialogFragment);
    }

    public void setOnButtonClickListener(OnButonOptionObservationClick OnButonOptionObservationClick) {
        this.OnButonOptionObservationClick = OnButonOptionObservationClick;
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof HolidaysReasonsAditionalDaysResponse) {
                    HolidaysReasonsAditionalDaysResponse responseReasons = (HolidaysReasonsAditionalDaysResponse) response.getResponse();

                }
                break;
        }
    }


    public void setIDialogControlKeboard(com.coppel.rhconecta.dev.business.interfaces.IDialogControlKeboard IDialogControlKeboard) {
        this.IDialogControlKeboard = IDialogControlKeboard;
    }

    @Override
    public void showError(ServicesError coppelServicesError) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


}
