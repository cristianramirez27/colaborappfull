package com.coppel.rhconecta.dev.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import com.coppel.rhconecta.dev.business.models.VoucherRosterResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
public class PayrollVoucherFragment extends Fragment implements IServicesContract.View, PayrollVoucherRecyclerAdapter.OnPayrollVoucherClickListener,
        PayrollVoucherRecyclerAdapter.OnActionClickListener, DialogFragmentGetDocument.OnButtonClickListener, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = PayrollVoucherFragment.class.getSimpleName();
    private HomeActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<VoucherResponse.FechasNomina> payrolls;
    private VoucherResponse.FechasNomina payrollToGet;
    private PayrollVoucherRecyclerAdapter payrollVoucherRecyclerAdapter;
    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentWarning dialogFragmentWarning;
    private int payrollDateRequested;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;
    private boolean WARNING_PERMISSIONS;
    private boolean SHARE_PDF;
    private boolean EXPIRED_SESSION;
    private File pdf;
    @BindView(R.id.rcvPayroll)
    RecyclerView rcvPayroll;

    private ISurveyNotification ISurveyNotification;
    private boolean GO_BACK;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payroll_voucher, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.payroll));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvPayroll.setHasFixedSize(true);
        rcvPayroll.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_PAYROLL_DATES) != null) {
            payrolls = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_PAYROLL_DATES), new TypeToken<List<VoucherResponse.FechasNomina>>() {
            }.getType());
        } else {
            payrolls = new ArrayList<>();
        }

        ISurveyNotification.getSurveyIcon().setVisibility(View.INVISIBLE);
        payrollVoucherRecyclerAdapter = new PayrollVoucherRecyclerAdapter(getContext(), payrolls);
        payrollVoucherRecyclerAdapter.setOnPayrollVoucherClickListener(this);
        payrollVoucherRecyclerAdapter.setOnActionClickListener(this);
        rcvPayroll.setAdapter(payrollVoucherRecyclerAdapter);
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
                    requestPayrollVoucherDetails(payrollToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
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
            case ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DETAIL:
                VoucherRosterResponse voucherRosterResponse = (VoucherRosterResponse) response.getResponse();
                VoucherResponse.FechasNomina payrrollDate = payrolls.get(payrollDateRequested);
                payrrollDate.setFailDetail(false);
                payrrollDate.setVoucherRosterResponse(voucherRosterResponse);
                payrolls.set(payrollDateRequested, payrrollDate);
                payrollVoucherRecyclerAdapter.notifyDataSetChanged();
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DOWNLOAD_DETAIL:
                VoucherDownloadResponse voucherDownloadResponse = (VoucherDownloadResponse) response.getResponse();
                pdf = AppUtilities.savePDFFile(
                        requireContext(),
                        getString(R.string.payroll_voucher).replace(" ", "_"),
                        voucherDownloadResponse.getData().getResponse().getPdf()
                );
                if (pdf != null) {
                    SHARE_PDF = true;
                    showGetVoucherDialog(DialogFragmentGetDocument.VOUCHER_DOWNLOADED);
                } else {
                    showWarningDialog(getString(R.string.error_save_file));
                }
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_ROSTER_SENDMAIL_DETAIL:
                showGetVoucherDialog(DialogFragmentGetDocument.VOUCHER_SENT);
                break;
        }
    }

    @Override
    public void showError(final ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DETAIL:
                VoucherResponse.FechasNomina payrrollDate = payrolls.get(payrollDateRequested);
                payrrollDate.setFailDetail(true);
                payrolls.set(payrollDateRequested, payrrollDate);
                payrollVoucherRecyclerAdapter.notifyDataSetChanged();
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_ROSTER_DOWNLOAD_DETAIL:
                showGetVoucherDialog(DialogFragmentGetDocument.VOUCHER_DOWNLOAD_FAIL);
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_ROSTER_SENDMAIL_DETAIL:
                showGetVoucherDialog(DialogFragmentGetDocument.VOUCHER_SEND_FAIL);
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
                        dialogFragmentWarning.setOnOptionClick(PayrollVoucherFragment.this);
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
    public void onItemClick(VoucherResponse.FechasNomina payrollDate, int position) {
        payrollDateRequested = position;
        requestPayrollVoucherDetails(payrollDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_GET);
    }

    @Override
    public void onMailClick(VoucherResponse.FechasNomina payrollDate) {
        payrollToGet = payrollDate;
        showGetVoucherDialog(DialogFragmentGetDocument.SEND_TO);
    }

    @Override
    public void onDownloadClick(VoucherResponse.FechasNomina payrollDate) {
        payrollToGet = payrollDate;
        requestPermissions();
    }

    @Override
    public void onSend(String email) {
        if (email != null && !email.isEmpty()) {
            requestPayrollVoucherDetails(payrollToGet, email, ServicesConstants.SHIPPING_OPTION_SEND_EMAIL);
            dialogFragmentGetDocument.close();
        } else {
            dialogFragmentGetDocument.setWarningMessage(getString(R.string.error_email));
        }
    }

    /**
     * 7 Noviembre 2018
     * Modificacion: Se cambia el share por intent para abrir el PDF.---
     **/
    @Override
    public void onAccept() {
        if (SHARE_PDF) {
            AppUtilities.openPDF(parent, pdf);
            //AppUtilities.sharePDF(parent, pdf);
        }
        SHARE_PDF = false;
        dialogFragmentGetDocument.close();
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        } else if (GO_BACK) {
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

    private void requestPayrollVoucherDetails(VoucherResponse.FechasNomina payrollDate, String email, int shippingType) {
        coppelServicesPresenter.requestPayrollVoucherDetail(parent.getProfileResponse().getColaborador(),
                email, ServicesConstants.CONSTANCE_TYPE_PAYROLL, 2, shippingType,
                TextUtilities.dateFormatter(payrollDate.sfechanomina, AppConstants.DATE_FORMAT_DD_MM_YYYY_MIDDLE,
                        AppConstants.DATE_FORMAT_YYYY_MM_DD_MIDDLE), new CoppelServicesPayrollVoucherDetailRequest.PayrollVoucherDetailGenericData(), parent.getLoginResponse().getToken());
    }

    private void showWarningDialog(String message) {
        if (parent.getSupportFragmentManager() != null){
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(this);
            dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
        }else{
            showDialogAlt(message);
        }
    }

    private  void showDialogAlt(String message){
        new MaterialAlertDialogBuilder(this.getContext())
                .setTitle("Aviso!")
                .setPositiveButton("Aceptar",(dialog, which) -> {
                    // Respond to positive button press
                })
                .setMessage(message).show();
    }

    private void showGetVoucherDialog(int type) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(type, parent);
        if (type == DialogFragmentGetDocument.SEND_TO) {
            dialogFragmentGetDocument.setPreloadedText(parent.getProfileResponse().getCorreo());
        }
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(parent, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parent, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        } else {
            requestPayrollVoucherDetails(payrollToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
        }
    }
}
