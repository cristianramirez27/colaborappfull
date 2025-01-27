package com.coppel.rhconecta.dev.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.VoucherDownloadResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.models.VoucherSavingFundResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherSavingFundRecyclerAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PayrollVoucherSavingFundFragment extends Fragment implements IServicesContract.View, PayrollVoucherSavingFundRecyclerAdapter.OnSavingFundVoucherClickListener,
        PayrollVoucherSavingFundRecyclerAdapter.OnActionClickListener, DialogFragmentWarning.OnOptionClick, DialogFragmentGetDocument.OnButtonClickListener {

    public static final String TAG = PayrollVoucherSavingFundFragment.class.getSimpleName();
    private HomeActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<VoucherResponse.FechaCorteCuenta> savingFunds;
    private PayrollVoucherSavingFundRecyclerAdapter payrollVoucherSavingFundRecyclerAdapter;
    private VoucherResponse.FechaCorteCuenta savingToGet;
    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentWarning dialogFragmentWarning;
    private int savingFundRequested;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;
    private boolean WARNING_PERMISSIONS;
    private boolean SHARE_PDF;
    private boolean EXPIRED_SESSION;
    private boolean GO_BACK;
    private File pdf;
    @BindView(R.id.rcvSavingFund)
    RecyclerView rcvSavingFund;
    private ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payroll_voucher_saving_found, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.saving_fund));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSavingFund.setHasFixedSize(true);
        rcvSavingFund.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_SAVING_FUND_DATES) != null) {
            savingFunds = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_SAVING_FUND_DATES), new TypeToken<List<VoucherResponse.FechaCorteCuenta>>() {
            }.getType());
        } else {
            savingFunds = new ArrayList<>();
        }
        ISurveyNotification.getSurveyIcon().setVisibility(View.INVISIBLE);
        payrollVoucherSavingFundRecyclerAdapter = new PayrollVoucherSavingFundRecyclerAdapter(getContext(), savingFunds);
        payrollVoucherSavingFundRecyclerAdapter.setOnSavingFundVoucherClickListener(this);
        payrollVoucherSavingFundRecyclerAdapter.setOnActionClickListener(this);
        rcvSavingFund.setAdapter(payrollVoucherSavingFundRecyclerAdapter);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (AppUtilities.validatePermissions(permissions.length, grantResults)) {
                    requestSavingFoundVoucherDetail(savingToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
                } else {
                    showWarningDialog(getString(R.string.permissions_needed));
                    WARNING_PERMISSIONS = true;
                }
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DETAIL:
                VoucherSavingFundResponse savingFundResponse = (VoucherSavingFundResponse) response.getResponse();
                VoucherResponse.FechaCorteCuenta savingFundDate = savingFunds.get(savingFundRequested);
                savingFundDate.setFailDetail(false);
                savingFundDate.setVoucherSavingFundResponse(savingFundResponse);
                savingFunds.set(savingFundRequested, savingFundDate);
                payrollVoucherSavingFundRecyclerAdapter.notifyDataSetChanged();
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DOWNLOAD_DETAIL:
                VoucherDownloadResponse voucherDownloadResponse = (VoucherDownloadResponse) response.getResponse();
                pdf = AppUtilities.savePDFFile(
                        requireContext(),
                        getString(R.string.saving_fund).replace(" ", "_"),
                        voucherDownloadResponse.getData().getResponse().getPdf()
                );
                if (pdf != null) {
                    SHARE_PDF = true;
                    showGetVoucherReadyDialog(DialogFragmentGetDocument.VOUCHER_DOWNLOADED);
                } else {
                    showWarningDialog(getString(R.string.error_save_file));
                }
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_SENDMAIL_DETAIL:
                showGetVoucherReadyDialog(DialogFragmentGetDocument.VOUCHER_SENT);
                break;
        }
    }

    @Override
    public void showError(final ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DETAIL:
                VoucherResponse.FechaCorteCuenta savingFundDate = savingFunds.get(savingFundRequested);
                savingFundDate.setFailDetail(true);
                savingFunds.set(savingFundRequested, savingFundDate);
                payrollVoucherSavingFundRecyclerAdapter.notifyDataSetChanged();
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_DOWNLOAD_DETAIL:
                showGetVoucherReadyDialog(DialogFragmentGetDocument.VOUCHER_DOWNLOAD_FAIL);
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_SAVINGFUND_SENDMAIL_DETAIL:
                showGetVoucherReadyDialog(DialogFragmentGetDocument.VOUCHER_SEND_FAIL);
                break;
            case ServicesRequestType.INVALID_TOKEN:
                EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
                break;

            case ServicesRequestType.PAYROLL_VOUCHER_DETAIL:

                GO_BACK = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragmentWarning = new DialogFragmentWarning();
                        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                        dialogFragmentWarning.setOnOptionClick(PayrollVoucherSavingFundFragment.this);
                        dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
                        dialogFragmentLoader.close();
                    }
                }, 1500);

                break;
        }
    }

    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
        dialogFragmentLoader.close();
    }

    @Override
    public void onItemClick(VoucherResponse.FechaCorteCuenta savingFundDate, int position) {
        savingFundRequested = position;
        requestSavingFoundVoucherDetail(savingFundDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_GET);
    }

    @Override
    public void onMailClick(VoucherResponse.FechaCorteCuenta savingFundDate) {
        savingToGet = savingFundDate;
        showGetVoucherReadyDialog(DialogFragmentGetDocument.SEND_TO);
    }

    @Override
    public void onDownloadClick(VoucherResponse.FechaCorteCuenta savingFundDate) {
        savingToGet = savingFundDate;
        requestPermissions();
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        }else if(GO_BACK) {
            getActivity().onBackPressed();

        } else if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1])) {
                AppUtilities.openAppSettings(parent);
            }
        }
        WARNING_PERMISSIONS = false;
        dialogFragmentWarning.close();
    }

    @Override
    public void onSend(String email) {
        if (email != null && !email.isEmpty()) {
            requestSavingFoundVoucherDetail(savingToGet, email, ServicesConstants.SHIPPING_OPTION_SEND_EMAIL);
            dialogFragmentGetDocument.close();
        } else {
            dialogFragmentGetDocument.setWarningMessage(getString(R.string.error_email));
        }
    }

    /**
     * 7 Noviembre 2018
     * Modificacion: Se cambia el share por intent para abrir el PDF.---
     *
     * **/
    @Override
    public void onAccept() {
        if (SHARE_PDF) {
            AppUtilities.openPDF(parent, pdf);
            //AppUtilities.sharePDF(parent, pdf);
        }
        SHARE_PDF = false;
        dialogFragmentGetDocument.close();
    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    private void showGetVoucherReadyDialog(int type) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(type, parent);
        if (type == DialogFragmentGetDocument.SEND_TO) {
            dialogFragmentGetDocument.setPreloadedText(parent.getProfileResponse().getCorreo());
        }
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    private void requestPermissions() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(parent, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(parent, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            } else {
                requestSavingFoundVoucherDetail(savingToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
            }
        }else {
            requestSavingFoundVoucherDetail(savingToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
        }
    }

    private void requestSavingFoundVoucherDetail(VoucherResponse.FechaCorteCuenta savingFundDate, String email, int shippingType) {
        coppelServicesPresenter.requestPayrollVoucherDetail(parent.getProfileResponse().getColaborador(),
                email, ServicesConstants.CONSTANCE_TYPE_SAVING_FUND, 2, shippingType,
                TextUtilities.dateFormatter(savingFundDate.sfechanomina, AppConstants.DATE_FORMAT_DD_MM_YYYY_MIDDLE,
                        AppConstants.DATE_FORMAT_YYYY_MM_DD_MIDDLE), new CoppelServicesPayrollVoucherDetailRequest.Datos(), AppUtilities.getAuthHeader(requireContext()));
    }
}
