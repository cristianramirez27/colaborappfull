package com.coppel.rhconecta.dev.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISelectedOption;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.RemoveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_RESPONSE_CONFIG_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanSavingFundMainChildFragment extends Fragment implements View.OnClickListener {
    public static final int REQUEST_SAVING = 258;
    public static final String TAG = LoanSavingFundMainChildFragment.class.getSimpleName();
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnAdditionalSaving)
    Button btnAdditionalSaving;
    private long mLastClickTime = 0;
    private ISelectedOption ISelectedOption;

    private LoanSavingFundResponse loanSavingFundResponse;


    public static LoanSavingFundMainChildFragment getInstance(LoanSavingFundResponse loanSavingFundResponse){
        LoanSavingFundMainChildFragment fragment = new LoanSavingFundMainChildFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_SAVINFOUND,loanSavingFundResponse);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanSavingFundResponse = (LoanSavingFundResponse) getArguments().getSerializable(BUNDLE_SAVINFOUND);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loan_fragment_saving_fund_main_child, container, false);
        ButterKnife.bind(this, view);
        btnRemove.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnAdditionalSaving.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        int optionSelected = 0;
        switch (view.getId()) {
            case R.id.btnRemove:
                optionSelected = 1;
                break;
            case R.id.btnAdd:
                optionSelected = 2;
                break;
            case R.id.btnAdditionalSaving:
                optionSelected = 3;
                break;
        }

        ISelectedOption.openOption(optionSelected);
    }

    public void setISelectedOption(ISelectedOption ISelectedOption) {
        this.ISelectedOption = ISelectedOption;
    }
}
