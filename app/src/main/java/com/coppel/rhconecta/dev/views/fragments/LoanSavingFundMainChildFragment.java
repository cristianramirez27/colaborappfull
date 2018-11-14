package com.coppel.rhconecta.dev.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanSavingFundMainChildFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = LoanSavingFundMainChildFragment.class.getSimpleName();
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnAdditionalSaving)
    Button btnAdditionalSaving;

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
        switch (view.getId()) {
            case R.id.btnRemove:
                break;
            case R.id.btnAdd:
                break;
            case R.id.btnAdditionalSaving:
                break;
        }
    }
}
