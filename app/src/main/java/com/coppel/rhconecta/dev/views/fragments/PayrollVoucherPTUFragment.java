package com.coppel.rhconecta.dev.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.coppel.rhconecta.dev.business.models.VoucherDownloadResponse;
import com.coppel.rhconecta.dev.business.models.VoucherPTUResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherPTURecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherPTUV2RecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetVoucher;
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

public class PayrollVoucherPTUFragment extends Fragment implements IServicesContract.View, PayrollVoucherPTUV2RecyclerAdapter.OnVoucherPTUClickListener,
        PayrollVoucherPTUV2RecyclerAdapter.OnActionClickListener, DialogFragmentGetVoucher.OnButtonClickListener, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = PayrollVoucherPTUFragment.class.getSimpleName();
    private HomeActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<VoucherResponse.FechasUtilidade> ptuDates;
    private PayrollVoucherPTUV2RecyclerAdapter payrollVoucherPTURecyclerAdapter;
    private VoucherResponse.FechasUtilidade ptuDateToGet;
    private DialogFragmentGetVoucher dialogFragmentGetVoucher;
    private DialogFragmentWarning dialogFragmentWarning;
    private int ptuDateRequested;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;
    private boolean WARNING_PERMISSIONS;
    private boolean SHARE_PDF;
    private boolean EXPIRED_SESSION;
    private boolean NETWORK_ERROR;
    private File pdf;
    @BindView(R.id.rcvPayroll)
    RecyclerView rcvPayroll;

    private ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payroll_voucher_ptu, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.ptu));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvPayroll.setHasFixedSize(true);
        rcvPayroll.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_PAYROLL_DATES_PTU) != null) {
            ptuDates = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_PAYROLL_DATES_PTU), new TypeToken<List<VoucherResponse.FechasUtilidade>>() {
            }.getType());
        } else {
            ptuDates = new ArrayList<>();
        }
        ISurveyNotification.getSurveyIcon().setVisibility(View.INVISIBLE);
        payrollVoucherPTURecyclerAdapter = new PayrollVoucherPTUV2RecyclerAdapter(getContext(), ptuDates);
        payrollVoucherPTURecyclerAdapter.setOnVoucherPTUClickListener(this);
        payrollVoucherPTURecyclerAdapter.setOnActionClickListener(this);
        rcvPayroll.setAdapter(payrollVoucherPTURecyclerAdapter);
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
                    requestPTUVoucherDetail(ptuDateToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
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
            case ServicesRequestType.PAYROLL_VOUCHER_PTU_DETAIL:
                VoucherPTUResponse voucherPTUResponse = (VoucherPTUResponse) response.getResponse();
                VoucherResponse.FechasUtilidade ptuDate = ptuDates.get(ptuDateRequested);
                ptuDate.setFailDetail(false);
                ptuDate.setVoucherPTUResponse(voucherPTUResponse);
                ptuDates.set(ptuDateRequested, ptuDate);
                payrollVoucherPTURecyclerAdapter.notifyDataSetChanged();
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_PTU_DOWNLOAD_DETAIL:
                VoucherDownloadResponse voucherDownloadResponse = (VoucherDownloadResponse) response.getResponse();
                pdf = AppUtilities.savePDFFile(getString(R.string.ptu).replace(" ", "_"),
                        voucherDownloadResponse.getData().getResponse().getPdf());
                if (pdf != null) {
                    SHARE_PDF = true;
                    showGetVoucherReadyDialog(DialogFragmentGetVoucher.VOUCHER_DOWNLOADED);
                } else {
                    showWarningDialog(getString(R.string.error_save_file));
                }
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_PTU_SENDMAIL_DETAIL:
                showGetVoucherReadyDialog(DialogFragmentGetVoucher.VOUCHER_SENT);
                break;
        }
    }

    @Override
    public void showError(final ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER_PTU_DETAIL:
                VoucherResponse.FechasUtilidade ptuDate = ptuDates.get(ptuDateRequested);
                ptuDate.setFailDetail(false);
                ptuDates.set(ptuDateRequested, ptuDate);
                payrollVoucherPTURecyclerAdapter.notifyDataSetChanged();
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_PTU_DOWNLOAD_DETAIL:
                showGetVoucherReadyDialog(DialogFragmentGetVoucher.VOUCHER_DOWNLOAD_FAIL);
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_PTU_SENDMAIL_DETAIL:
                showGetVoucherReadyDialog(DialogFragmentGetVoucher.VOUCHER_SEND_FAIL);
                break;

            case ServicesRequestType.PAYROLL_VOUCHER_DETAIL:

                NETWORK_ERROR = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragmentWarning = new DialogFragmentWarning();
                        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                        dialogFragmentWarning.setOnOptionClick(PayrollVoucherPTUFragment.this);
                        dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
                        dialogFragmentLoader.close();
                    }
                }, 1500);

                break;

            case ServicesRequestType.INVALID_TOKEN:
                EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
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
    public void onItemClick(VoucherResponse.FechasUtilidade ptuDate, int position) {
        ptuDateRequested = position;
        requestPTUVoucherDetail(ptuDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_GET);
    }

    @Override
    public void onMailClick(VoucherResponse.FechasUtilidade ptuDate) {
        ptuDateToGet = ptuDate;
        showGetVoucherReadyDialog(DialogFragmentGetVoucher.SEND_TO);
    }

    @Override
    public void onDownloadClick(VoucherResponse.FechasUtilidade ptuDate) {
        ptuDateToGet = ptuDate;
        requestPermissions();
    }

    @Override
    public void onSend(String email) {
        if (email != null && !email.isEmpty()) {
            requestPTUVoucherDetail(ptuDateToGet, email, ServicesConstants.SHIPPING_OPTION_SEND_EMAIL);
            dialogFragmentGetVoucher.close();
        } else {
            dialogFragmentGetVoucher.setWarningMessage(getString(R.string.error_email));
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
        dialogFragmentGetVoucher.close();
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        }else if(NETWORK_ERROR) {
            getActivity().onBackPressed();

        }else if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1])) {
                AppUtilities.openAppSettings(parent);
            }
        }
        WARNING_PERMISSIONS = false;
        dialogFragmentWarning.close();
    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    private void showGetVoucherReadyDialog(int type) {
        dialogFragmentGetVoucher = new DialogFragmentGetVoucher();
        dialogFragmentGetVoucher.setType(type, parent);
        if (type == DialogFragmentGetVoucher.SEND_TO) {
            dialogFragmentGetVoucher.setPreloadedText(parent.getProfileResponse().getCorreo());
        }
        dialogFragmentGetVoucher.setOnButtonClickListener(this);
        dialogFragmentGetVoucher.show(parent.getSupportFragmentManager(), DialogFragmentGetVoucher.TAG);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(parent, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parent, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        } else {
            requestPTUVoucherDetail(ptuDateToGet, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
        }
    }

    private void requestPTUVoucherDetail(VoucherResponse.FechasUtilidade ptuDate, String email, int shippingType) {
        coppelServicesPresenter.requestPayrollVoucherDetail(
                parent.getProfileResponse().getColaborador(),
                email,
                ServicesConstants.CONSTANCE_TYPE_PAYROLL_PTU,
                2,
                shippingType,
                TextUtilities.dateFormatter(ptuDate.sfechanomina,
                        AppConstants.DATE_FORMAT_DD_MM_YYYY_MIDDLE,
                        AppConstants.DATE_FORMAT_YYYY_MM_DD_MIDDLE),
                new CoppelServicesPayrollVoucherDetailRequest.Datos(),
                parent.getLoginResponse().getToken());
    }
}
