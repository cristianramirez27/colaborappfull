package com.coppel.rhconecta.dev.views.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitsCitiesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentSelectLocation extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentSelectLocation.class.getSimpleName();
    public static final String KEY_COMPANY = "KEY_COMPANY";
    @BindView(R.id.viewState)
    View viewState;
    @BindView(R.id.viewCity)
    View viewCity;
    @BindView(R.id.state)
    EditText state;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.btnLeftOption)
    Button btnLeftOption;
    @BindView(R.id.btnRightOption)
    Button btnRightOption;

    @BindView(R.id.stateError)
    TextView stateError;
    @BindView(R.id.cityError)
    TextView cityError;

    private OnSelectLocationsButtonsClickListener onSelectLocationsButtonsClickListener;
    private  BenefitsStatesResponse.States stateSelected;

    private long mLastClickTime = 0;

    public static DialogFragmentSelectLocation getInstance(){
        DialogFragmentSelectLocation dialogFragmentCompany = new DialogFragmentSelectLocation();
        return dialogFragmentCompany;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_location, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        initView();
        return view;
    }

    private void initView() {
        btnRightOption.setOnClickListener(this);
        btnLeftOption.setOnClickListener(this);
        viewState.setOnClickListener(this);
        viewCity.setOnClickListener(this);
        viewCity.setEnabled(false);
        viewCity.setAlpha(0.2f);
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset);
        return dialog;
    }

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 800){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnLeftOption:


                close();
                break;
            case R.id.btnRightOption:

               /* if(state.getText() == null || !state.getText().toString().isEmpty()){
                    stateError.setVisibility(View.VISIBLE);
                    return;
                }

                if(city.getText() == null || !city.getText().toString().isEmpty()){
                    cityError.setVisibility(View.VISIBLE);
                    return;
                }

                stateError.setVisibility(View.INVISIBLE);
                cityError.setVisibility(View.INVISIBLE);*/
                if(state.getText().toString().isEmpty()
                   || city.getText().toString().isEmpty()){
                    return;
                }else {
                    onSelectLocationsButtonsClickListener.onSelectLocation();
                }


                break;

            case R.id.viewState:
                onSelectLocationsButtonsClickListener.onSelectState();
                break;

            case R.id.viewCity:
                if(this.stateSelected != null ) {
                    onSelectLocationsButtonsClickListener.onSelectCity(this.stateSelected);
                }
                break;
        }
    }

    public void setState(BenefitsStatesResponse.States stateSelected){
        this.stateSelected =stateSelected;
        state.setText(stateSelected.getNombre());

        viewCity.setEnabled(true);
        viewCity.setAlpha(1f);
    }


    public void setCity(BenefitsCitiesResponse.City citySelected){
        //this.stateSelected =stateSelected;
        city.setText(citySelected.getNombre());
    }

    public interface OnSelectLocationsButtonsClickListener {
        void onSelectLocation();
        void onSelectState();
        void onSelectCity(BenefitsStatesResponse.States state);
        void closeSelectLocationDialog();
    }

    public void setOnSelectLocationsButtonsClickListener(OnSelectLocationsButtonsClickListener onSelectLocationsButtonsClickListener) {
        this.onSelectLocationsButtonsClickListener = onSelectLocationsButtonsClickListener;
    }
}