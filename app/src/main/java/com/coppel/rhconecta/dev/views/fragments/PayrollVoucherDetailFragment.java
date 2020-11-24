package com.coppel.rhconecta.dev.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_PAYROLL_OPTION;

public abstract class PayrollVoucherDetailFragment extends Fragment {

    protected HomeActivity parent;
    protected CoppelServicesPresenter coppelServicesPresenter;
    protected int option;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_PAYROLL_OPTION) != null) {
            option = bundle.getInt(BUNDLE_PAYROLL_OPTION);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void requestPayrollVoucherDetail(VoucherResponse.FechasAguinaldo bonusDate, String email, int shippingType) {
        coppelServicesPresenter.requestPayrollVoucherDetail(parent.getProfileResponse().getColaborador(),
                email,
                ServicesConstants.CONSTANCE_TYPE_BONUS,
                2,
                shippingType,
                TextUtilities.dateFormatter(bonusDate.sfechanomina,
                        AppConstants.DATE_FORMAT_DD_MM_YYYY_MIDDLE,
                        AppConstants.DATE_FORMAT_YYYY_MM_DD_MIDDLE),
                new CoppelServicesPayrollVoucherDetailRequest.Datos(), parent.getLoginResponse().getToken());
    }
}