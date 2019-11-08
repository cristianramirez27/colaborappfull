package com.coppel.rhconecta.dev.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.VoucherBonusResponse;
import com.coppel.rhconecta.dev.business.models.VoucherDownloadResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherBonusRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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