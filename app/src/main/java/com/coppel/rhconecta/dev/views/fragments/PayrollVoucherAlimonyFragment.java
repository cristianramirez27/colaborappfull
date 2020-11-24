package com.coppel.rhconecta.dev.views.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherBeneficiariesAdapter;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherAlimonyFragment extends Fragment implements View.OnClickListener, PayrollVoucherBeneficiariesAdapter.OnBeneficiaryClickListener {

    public static final String TAG = PayrollVoucherAlimonyFragment.class.getSimpleName();
    private HomeActivity parent;
    private VoucherResponse.DatosPencion beneficiarySelected;
    private List<VoucherResponse.FechasPensione> beneficiaryDates;
    private Gson gson;
    @BindView(R.id.rcvBeneficiaries)
    RecyclerView rcvBeneficiaries;
    @BindView(R.id.btnNext)
    Button btnNext;

    private com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payroll_voucher_alimony, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.alimony));
        beneficiarySelected = null;
        gson = new Gson();
        rcvBeneficiaries.setHasFixedSize(true);
        rcvBeneficiaries.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        List<VoucherResponse.DatosPencion> beneficiaries;
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_PAYROLL_BENEFICIARIES_ALIMONY) != null &&
                bundle.getString(AppConstants.BUNDLE_BENEFICIARY_DATES) != null) {
            beneficiaries = gson.fromJson(bundle.getString(AppConstants.BUNDLE_PAYROLL_BENEFICIARIES_ALIMONY), new TypeToken<List<VoucherResponse.DatosPencion>>() {
            }.getType());
            beneficiaryDates = gson.fromJson(bundle.getString(AppConstants.BUNDLE_BENEFICIARY_DATES), new TypeToken<List<VoucherResponse.FechasPensione>>() {
            }.getType());
        } else {
            beneficiaries = new ArrayList<>();
        }
        ISurveyNotification.getSurveyIcon().setVisibility(View.INVISIBLE);
        PayrollVoucherBeneficiariesAdapter payrollVoucherBeneficiariesAdapter = new PayrollVoucherBeneficiariesAdapter(beneficiaries,getActivity());
        payrollVoucherBeneficiariesAdapter.setOnBeneficiaryClickListener(this);
        rcvBeneficiaries.setAdapter(payrollVoucherBeneficiariesAdapter);
        btnNext.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                if (beneficiarySelected != null) {
                    PayrollVoucherAlimonyBeneficiaryFragment payrollVoucherAlimonyBeneficiaryFragment = new PayrollVoucherAlimonyBeneficiaryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.BUNDLE_BENEFICIARY, gson.toJson(beneficiarySelected));
                    bundle.putString(AppConstants.BUNDLE_BENEFICIARY_DATES, gson.toJson(getBeneficiaryDates()));
                    payrollVoucherAlimonyBeneficiaryFragment.setArguments(bundle);
                    parent.replaceFragment(payrollVoucherAlimonyBeneficiaryFragment, PayrollVoucherAlimonyBeneficiaryFragment.TAG);
                } else {
                    Toast.makeText(parent, getString(R.string.select_beneficiary_name), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(VoucherResponse.DatosPencion beneficiary) {
        beneficiarySelected = beneficiary;
    }

    private List<VoucherResponse.FechasPensione> getBeneficiaryDates() {
        List<VoucherResponse.FechasPensione> dates = new ArrayList<>();
        for(VoucherResponse.FechasPensione p : beneficiaryDates) {
            if(p.getNum_beneficiario() == beneficiarySelected.getNum_beneficiario()) {
                dates.add(p);
            }
        }
        return dates;
    }
}
