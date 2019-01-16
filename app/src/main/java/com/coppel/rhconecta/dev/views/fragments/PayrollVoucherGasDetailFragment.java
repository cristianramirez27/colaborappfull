package com.coppel.rhconecta.dev.views.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CoppelServicesPayrollVoucherDetailRequest;
import com.coppel.rhconecta.dev.business.models.VoucherDownloadResponse;
import com.coppel.rhconecta.dev.business.models.VoucherGasResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherGasDetailRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetVoucher;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.modelview.GasUnit;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayrollVoucherGasDetailFragment extends Fragment implements IServicesContract.View, View.OnClickListener,
        DialogFragmentGetVoucher.OnButtonClickListener, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = PayrollVoucherGasDetailFragment.class.getSimpleName();
    private HomeActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private VoucherResponse.FechaGasolina gasDate;
    private DialogFragmentGetVoucher dialogFragmentGetVoucher;
    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int PERMISSIONS_REQUEST_CODE = 10;
    private boolean WARNING_PERMISSIONS;
    private boolean SHARE_PDF;
    private boolean EXPIRED_SESSION;
    private File pdf;
    private List<GasUnit> units;
    private PayrollVoucherGasDetailRecyclerAdapter payrollVoucherGasDetailRecyclerAdapter;
    @BindView(R.id.ctlContainer)
    ConstraintLayout ctlContainer;
    @BindView(R.id.txvDate)
    TextView txvDate;
    @BindView(R.id.imgvMail)
    ImageView imgvMail;
    @BindView(R.id.imgvDownload)
    ImageView imgvDownload;
    @BindView(R.id.rcvUnits)
    RecyclerView rcvUnits;
    @BindView(R.id.txvdSubtotal)
    TextViewDetail txvdSubtotal;
    @BindView(R.id.txvdFactor)
    TextViewDetail txvdFactor;
    @BindView(R.id.txvdAdditionalSaving)
    TextViewDetail txvdAdditionalSaving;
    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.imgvRefresh)
    ImageView imgvRefresh;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payroll_voucher_gas_detail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        units = new ArrayList<>();
        parent = ((HomeActivity) getActivity());
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        txvdSubtotal.setStartText(getString(R.string.subtotal));
        txvdFactor.setStartText("(-) " + getString(R.string.factor));
        txvdAdditionalSaving.setStartText("(=) " + getString(R.string.additional_saving));
        txvdSubtotal.setEndTextSize(18F);
        txvdFactor.setEndTextSize(16F);
        txvdAdditionalSaving.setEndTextSize(16F);

        /*Se cambia estilo de totales*/
        txvdFactor.setStartFont(ResourcesCompat.getFont(parent, R.font.lineto_circular_pro_book));
        txvdFactor.setEndFont(ResourcesCompat.getFont(parent, R.font.lineto_circular_pro_book));
        txvdAdditionalSaving.setStartFont(ResourcesCompat.getFont(parent, R.font.lineto_circular_pro_book));
        txvdAdditionalSaving.setEndFont(ResourcesCompat.getFont(parent, R.font.lineto_circular_pro_book));

        txvdSubtotal.setStartFont(ResourcesCompat.getFont(parent, R.font.lineto_circular_pro_bold));
        txvdSubtotal.setEndFont(ResourcesCompat.getFont(parent, R.font.lineto_circular_pro_bold));
        rcvUnits.setHasFixedSize(true);
        rcvUnits.setLayoutManager(new LinearLayoutManager(getContext()));
        payrollVoucherGasDetailRecyclerAdapter = new PayrollVoucherGasDetailRecyclerAdapter(getContext(), units);
        rcvUnits.setAdapter(payrollVoucherGasDetailRecyclerAdapter);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_SELECTED_GAS_DATE) != null) {
            gasDate = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_SELECTED_GAS_DATE), VoucherResponse.FechaGasolina.class);
            txvDate.setText(gasDate.getSfechanominanombre2());
            imgvMail.setOnClickListener(this);
            imgvDownload.setOnClickListener(this);
            imgvRefresh.setOnClickListener(this);
            requestGasVoucherDetail(gasDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_GET);
        }
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
                    requestGasVoucherDetail(gasDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
                } else {
                    showWarningDialog(getString(R.string.permissions_needed));
                    WARNING_PERMISSIONS = true;
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvMail:
                showGetVoucherDialog(DialogFragmentGetVoucher.SEND_TO);
                break;
            case R.id.imgvDownload:
                requestPermissions();
                break;
            case R.id.imgvRefresh:
                requestGasVoucherDetail(gasDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_GET);
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ctlContainer.setVisibility(View.VISIBLE);
        ctlConnectionError.setVisibility(View.GONE);
        switch (response.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER_GAS_DETAIL:
                setDetails((VoucherGasResponse) response.getResponse());
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_GAS_DOWNLOAD_DETAIL:
                VoucherDownloadResponse voucherDownloadResponse = (VoucherDownloadResponse) response.getResponse();
                pdf = AppUtilities.savePDFFile(getString(R.string.gas).replace(" ", "_"),
                        voucherDownloadResponse.getData().getResponse().getPdf());
                if (pdf != null) {
                    SHARE_PDF = true;
                    showGetVoucherDialog(DialogFragmentGetVoucher.VOUCHER_DOWNLOADED);
                } else {
                    showWarningDialog(getString(R.string.error_save_file));
                }
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_GAS_SENDMAIL_DETAIL:
                showGetVoucherDialog(DialogFragmentGetVoucher.VOUCHER_SENT);
                break;
        }
    }

    /**
     * 1 Noviembre 2018
     * Correción: Se muestran iniciales de la ciudad en lugar del número de ciudad. ---
     *
     * **/
    private void setDetails(VoucherGasResponse voucherGasResponse) {
        if (voucherGasResponse.getData().getResponse().getDatosgasolina() != null
                && voucherGasResponse.getData().getResponse().getDatosgasolina().size() > 0
                && voucherGasResponse.getData().getResponse().getDatosgasolina().get(0) != null
                && voucherGasResponse.getData().getResponse().getDatosgasolina().get(0).size() > 0) {
            txvdSubtotal.setEndText(voucherGasResponse.getData().getResponse().getDatosgasolina().get(0).get(0).getTotal2());
            txvdFactor.setEndText(voucherGasResponse.getData().getResponse().getDatosgasolina().get(0).get(0).getFactor2());
            txvdAdditionalSaving.setEndText(voucherGasResponse.getData().getResponse().getDatosgasolina().get(0).get(0).getRetencion2());
            for (List<VoucherGasResponse.DatosGas> list : voucherGasResponse.getData().getResponse().getDatosgasolina()) {
                GasUnit gasUnit = new GasUnit(list.get(0).getUnidad() + "");
                List<GasUnit.GasTicket> tickets = new ArrayList<>();
                for (VoucherGasResponse.DatosGas unit : list) {
                    tickets.add(gasUnit.new GasTicket(
                            unit.getValen(),
                            unit.getFecha_vale(),
                            unit.getImporte2(),
                            unit.getNombreproveedor(),
                            unit.getFactura(),
                            unit.getIniciales(),
                            unit.getFecha_vale(),
                            unit.getImporte2()));
                }
                gasUnit.setTickets(tickets);
                units.add(gasUnit);
            }
            payrollVoucherGasDetailRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER_GAS_DETAIL:
                ctlContainer.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_GAS_DOWNLOAD_DETAIL:
                showGetVoucherDialog(DialogFragmentGetVoucher.VOUCHER_DOWNLOAD_FAIL);
                break;
            case ServicesRequestType.PAYROLL_VOUCHER_GAS_SENDMAIL_DETAIL:
                showGetVoucherDialog(DialogFragmentGetVoucher.VOUCHER_SEND_FAIL);
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
    public void onSend(String email) {
        if (email != null && !email.isEmpty()) {
            requestGasVoucherDetail(gasDate, email, ServicesConstants.SHIPPING_OPTION_SEND_EMAIL);
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
        } else if (WARNING_PERMISSIONS) {
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

    private void showGetVoucherDialog(int type) {
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
            requestGasVoucherDetail(gasDate, parent.getProfileResponse().getCorreo(), ServicesConstants.SHIPPING_OPTION_DOWNLOAD_PDF);
        }
    }

    private void requestGasVoucherDetail(VoucherResponse.FechaGasolina gasDate, String email, int shippingType) {
        coppelServicesPresenter.requestPayrollVoucherDetail(parent.getProfileResponse().getColaborador(),
                email, ServicesConstants.CONSTANCE_TYPE_GAS, 2, shippingType,
                TextUtilities.dateFormatter(gasDate.sfechanomina, AppConstants.DATE_FORMAT_DD_MM_YYYY_MIDDLE,
                        AppConstants.DATE_FORMAT_YYYY_MM_DD_MIDDLE), new CoppelServicesPayrollVoucherDetailRequest.Datos(), parent.getLoginResponse().getToken());
    }
}
