package com.coppel.rhconecta.dev.views.dialogs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.BenefitsAdvertisingResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCitiesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

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
    private OnSelectLocationsButtonsClickListener onSelectLocationsButtonsClickListener;
    private  BenefitsStatesResponse.States stateSelected;


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
        switch (view.getId()) {
            case R.id.btnLeftOption:
                close();
                break;
            case R.id.btnRightOption:
                onSelectLocationsButtonsClickListener.onSelectLocation();
                break;

            case R.id.viewState:
                onSelectLocationsButtonsClickListener.onSelectState();
                break;

            case R.id.viewCity:
                onSelectLocationsButtonsClickListener.onSelectCity(this.stateSelected);
                break;
        }
    }

    public void setState(BenefitsStatesResponse.States stateSelected){
        this.stateSelected =stateSelected;
        state.setText(stateSelected.getNombre());
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