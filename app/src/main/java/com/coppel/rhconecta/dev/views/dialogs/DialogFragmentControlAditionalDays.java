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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IControlViews;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_REASON_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.SEND_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.VALIDATION_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_SUPLENTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class DialogFragmentControlAditionalDays extends DialogFragment implements View.OnClickListener ,
        IServicesContract.View, IControlViews {

    public static final String TAG = DialogFragmentControlAditionalDays.class.getSimpleName();
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_DATA_PENDING = "KEY_DATA_PENDING";
    private OnButonOptionReasonClick OnButonOptionReasonClick;
    private Context context;
    private String email;
    /*Front Views*/
    @BindView(R.id.pendigDays)
    TextView pendigDays;
    @BindView(R.id.btnMinus)
    TextView btnMinus;
    @BindView(R.id.btnPlus)
    TextView btnPlus;
    @BindView(R.id.currentDays)
    EditText currentDays;
    @BindView(R.id.btnCancelControl)
    Button btnCancelControl;
    @BindView(R.id.btnNextControl)
    Button btnNextControl;

    /*Back Views*/
    @BindView(R.id.rcvFields)
    RecyclerView rcvFields;
    @BindView(R.id.reason)
    EditText reason;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;

    private CatalogueData catalogueData;
    private ReasonAditionalDayRecyclerAdapter reasonAditionalDayRecyclerAdapter;
    ///Animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    @BindView(R.id.card_front)
    View mCardFrontLayout;
    @BindView(R.id.card_back)
    View mCardBackLayout;


    private CoppelServicesPresenter coppelServicesPresenter;
    private  int maxDays;
    private int currentAditionalDays;
    private int pendingAditionalDays;


    private String numEmpleado;


    public static DialogFragmentControlAditionalDays newInstance(int maxDays,int pendingAditionalDays){
        DialogFragmentControlAditionalDays fragmentScheduleData = new DialogFragmentControlAditionalDays();
        Bundle args = new Bundle();
        args.putInt(KEY_DATA, maxDays);
        args.putInt(KEY_DATA_PENDING, pendingAditionalDays);
        fragmentScheduleData.setArguments(args);
        return fragmentScheduleData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
        this.maxDays = getArguments().getInt(KEY_DATA);
        this.pendingAditionalDays = getArguments().getInt(KEY_DATA_PENDING);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_aditional_days, container, false);
        ButterKnife.bind(this, view);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        initViews();
        return view;
    }

    private void initViews() {
        btnNextControl.setOnClickListener(this);
        btnCancelControl.setOnClickListener(this);
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);

        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);

        btnNextControl.setBackgroundResource(R.drawable.backgroud_rounder_grey);
        btnNextControl.setEnabled(false);
        pendigDays.setText(String.valueOf(this.pendingAditionalDays));
        currentDays.setText(String.valueOf(this.pendingAditionalDays));


        reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains("\n")){
                    String text = s.toString().replace("\n","");
                    reason.setText(text);
                    reason.setSelection(text.length());
                    hide_keyboard_from(getActivity(),reason);
                    //IDialogControlKeboard.showKeyboard(false,txtTitle);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        coppelServicesPresenter = new CoppelServicesPresenter(this, getActivity());

        //reason.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        reason.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        reason.setText("");
        reason.setEnabled(false);


        if(this.maxDays == 0 ){
            btnMinus.setEnabled(false);
            btnMinus.setTextColor(getResources().getColor(R.color.disable_text_color));
        }
    }

    public static void hide_keyboard_from(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (OnButonOptionReasonClick != null) {
            switch (view.getId()) {
                case R.id.btnCancelControl:
                    close();
                    break;
                case R.id.btnNextControl:
                   getReasonAditionalDays();
                    break;
                case R.id.btnActionLeft:
                    changeView();
                    OnButonOptionReasonClick.onLeftOptionReasonClick();
                    break;
                case R.id.btnActionRight:

                    if(!reason.isEnabled() || (reason.isEnabled() &&  reason.getText() != null && !reason.getText().toString().isEmpty())){
                        OnButonOptionReasonClick.onRightOptionReasonClick(getAditionalDays(),this);
                    }

                    break;

                case R.id.btnMinus:
                    addAditionalDay(false);
                    break;

                case R.id.btnPlus:
                    addAditionalDay(true);
                    break;
            }
        }
    }

    private void addAditionalDay(boolean add){
        currentAditionalDays= add ? currentAditionalDays +1 : currentAditionalDays - 1;
        int currentTotalDays = this.pendingAditionalDays + currentAditionalDays;
        currentDays.setText(String.valueOf(currentTotalDays));
        if(!add && ((currentTotalDays == (pendingAditionalDays - maxDays)))){
            btnMinus.setEnabled(false);
            btnMinus.setTextColor(getResources().getColor(R.color.disable_text_color));
            //return;
        }else {
            btnMinus.setEnabled(true);
            btnMinus.setTextColor(getResources().getColor(R.color.colorSecondBlueCoppel));
        }

        if(currentTotalDays == this.pendingAditionalDays){
            btnNextControl.setBackgroundResource(R.drawable.backgroud_rounder_grey);
            btnNextControl.setEnabled(false);
        }else {
            btnNextControl.setEnabled(true);
            btnNextControl.setBackgroundResource(R.drawable.background_blue_rounded);
        }
    }

    public interface OnButonOptionReasonClick {
        void onLeftOptionReasonClick();
        void onRightOptionReasonClick(HolidayRequestData data,DialogFragment dialogFragment);
    }

    public void setOnButtonClickListener(OnButonOptionReasonClick OnButonOptionReasonClick) {
        this.OnButonOptionReasonClick = OnButonOptionReasonClick;
    }

    /*Animation*/
    private void changeView() {
        loadAnimations();
        changeCameraDistance();
        flipCard();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mIsBackVisible) {
                    mCardFrontLayout.setVisibility(View.VISIBLE);
                    mCardBackLayout.setVisibility(View.GONE);
                } else {
                    mCardFrontLayout.setVisibility(View.GONE);
                    mCardBackLayout.setVisibility(View.VISIBLE);
                }
            }
        }, 1800);
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.in_animation);
    }

    public void flipCard() {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof HolidaysReasonsAditionalDaysResponse) {
                    HolidaysReasonsAditionalDaysResponse responseReasons = (HolidaysReasonsAditionalDaysResponse) response.getResponse();
                    initViewsReasons(responseReasons.getData().getResponse().get(0).getMotivos());
                }
                break;
        }
    }

    private void initViewsReasons(List<ReasonAditionaDay> reasonAditionaDays){
            rcvFields.setHasFixedSize(true);
            rcvFields.setLayoutManager(new LinearLayoutManager(getContext()));
            reasonAditionalDayRecyclerAdapter = new ReasonAditionalDayRecyclerAdapter(getContext(), reasonAditionaDays,this);
            rcvFields.setAdapter(reasonAditionalDayRecyclerAdapter);
            btnActionLeft.setOnClickListener(this);
            btnActionRight.setOnClickListener(this);
            changeView();
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

    private void getReasonAditionalDays(){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_REASON_ADITIONAL_DAYS, 16);
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    private HolidayRequestData getAditionalDays(){
        ReasonAditionaDay reasonAditionaDay = reasonAditionalDayRecyclerAdapter.getReasonAditionalDaySelected();
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        HolidayRequestData holidayRequestData = new HolidayRequestData(SEND_ADITIONAL_DAYS, 17);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setIdu_motivo(reasonAditionaDay.getIdu_motivo());
        holidayRequestData.setDes_otromotivo(reason.getText().toString());
        holidayRequestData.setNum_adicionales(currentAditionalDays);

        return holidayRequestData;
    }

    @Override
    public void enabledAuthorized(boolean enable) {
        btnActionRight.setEnabled(enable);
        btnActionRight.setBackgroundResource(enable ? R.drawable.background_blue_rounded :R.drawable.backgroud_rounder_grey );
    }

    @Override
    public void enabledOtherReason(boolean enable) {
        reason.setText("");
        reason.setEnabled(enable);
    }

    public EditText getReason() {
        return reason;
    }
}
