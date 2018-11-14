package com.coppel.rhconecta.dev.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherGasRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayrollVoucherGasFragment extends Fragment implements PayrollVoucherGasRecyclerAdapter.OnGasVoucherClickListener {

    public static final String TAG = PayrollVoucherGasFragment.class.getSimpleName();
    private HomeActivity parent;
    private List<VoucherResponse.FechaGasolina> gasDates;
    private PayrollVoucherGasRecyclerAdapter payrollVoucherGasRecyclerAdapter;
    @BindView(R.id.rcvGasDates)
    RecyclerView rcvGas;
    private ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification)context;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payroll_voucher_gas, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.gas));
        rcvGas.setHasFixedSize(true);
        rcvGas.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_GAS_DATES) != null) {
            gasDates = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_GAS_DATES), new TypeToken<List<VoucherResponse.FechaGasolina>>() {
            }.getType());
        } else {
            gasDates = new ArrayList<>();
        }
        ISurveyNotification.getSurveyIcon().setVisibility(View.INVISIBLE);
        payrollVoucherGasRecyclerAdapter = new PayrollVoucherGasRecyclerAdapter(gasDates);
        payrollVoucherGasRecyclerAdapter.setOnGasVoucherClickListener(this);
        rcvGas.setAdapter(payrollVoucherGasRecyclerAdapter);
        return view;
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
    public void onDateClick(VoucherResponse.FechaGasolina gasDate) {
        PayrollVoucherGasDetailFragment payrollVoucherGasDetailFragment = new PayrollVoucherGasDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_SELECTED_GAS_DATE, new Gson().toJson(gasDate));
        payrollVoucherGasDetailFragment.setArguments(bundle);
        parent.replaceFragment(payrollVoucherGasDetailFragment, PayrollVoucherGasDetailFragment.TAG);
    }
}
