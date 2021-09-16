package com.coppel.rhconecta.dev.views.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.VoucherResponse;
import com.coppel.rhconecta.dev.business.models.VoucherResponseV2;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.PayrollVoucherMenuRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_PAYROLL;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_PAYROLL_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_FUND;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_FUND_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_GAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_GAS_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_PTU;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_PTU_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_ALLOWANCE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_ALLOWANCE_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_BONUS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VOUCHER_BONUS_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.CONSTANCE_TYPE_ALIMONY;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.CONSTANCE_TYPE_BONUS;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.CONSTANCE_TYPE_GAS;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.CONSTANCE_TYPE_PAYROLL;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.CONSTANCE_TYPE_PAYROLL_PTU;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.CONSTANCE_TYPE_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_AGUINALDO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_FONDOAHORRO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_GASOLINA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_NOMINA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_PENSION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ICON_PTU;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayrollVoucherMenuFragment extends Fragment implements IServicesContract.View,
        PayrollVoucherMenuRecyclerAdapter.OnItemClick, View.OnClickListener, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = PayrollVoucherMenuFragment.class.getSimpleName();
    private HomeActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private VoucherResponseV2 voucherResponse;
    private PayrollVoucherMenuRecyclerAdapter payrollVoucherMenuRecyclerAdapter;
    private List<HomeMenuItem> menuItems;
    private Gson gson;
    @BindView(R.id.rcvOptions)
    RecyclerView rcvOptions;
    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.imgvRefresh)
    ImageView imgvRefresh;

    private ISurveyNotification ISurveyNotification;
    private final String[] key_block = {BLOCK_VOUCHER_PAYROLL, BLOCK_VOUCHER_FUND, BLOCK_VOUCHER_GAS, BLOCK_VOUCHER_PTU, BLOCK_VOUCHER_ALLOWANCE, BLOCK_VOUCHER_BONUS};
    private final String[] msg_block = {BLOCK_VOUCHER_PAYROLL_MESSAGE, BLOCK_VOUCHER_FUND_MESSAGE, BLOCK_VOUCHER_GAS_MESSAGE, BLOCK_VOUCHER_PTU_MESSAGE, BLOCK_VOUCHER_ALLOWANCE_MESSAGE, BLOCK_VOUCHER_BONUS_MESSAGE};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payroll_voucher_menu, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.payroll_voucher));
        gson = new Gson();
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        if (voucherResponse == null) {
            menuItems = new ArrayList<>();
            coppelServicesPresenter.requestPayrollVoucher(parent.getProfileResponse().getColaborador(), ServicesConstants.PETITION_PAYROLL_VOUCHER_LIST_V2, parent.getLoginResponse().getToken());
        }
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvOptions.setLayoutManager(gridLayoutManager);
        payrollVoucherMenuRecyclerAdapter = new PayrollVoucherMenuRecyclerAdapter(parent, menuItems, gridLayoutManager.getSpanCount());
        payrollVoucherMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(payrollVoucherMenuRecyclerAdapter);
        imgvRefresh.setOnClickListener(this);

        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);
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
    public void onItemClick(String tag) {
        int typeSelected = 0;
        switch (tag) {
            case AppConstants.OPTION_PAYROLL_VOUCHER:
                typeSelected = CONSTANCE_TYPE_PAYROLL;
                break;
            case AppConstants.OPTION_BONUS:
                typeSelected =  CONSTANCE_TYPE_BONUS ;
                break;
            case AppConstants.OPTION_SAVING_FUND:
                typeSelected =  CONSTANCE_TYPE_SAVING_FUND;
                break;
            case AppConstants.OPTION_GAS:
                typeSelected =  CONSTANCE_TYPE_GAS;
                break;
            case AppConstants.OPTION_PTU:
                typeSelected =  CONSTANCE_TYPE_PAYROLL_PTU;
                break;
            case AppConstants.OPTION_ALIMONY:
                typeSelected =  CONSTANCE_TYPE_ALIMONY;
                break;
        }
        int key = typeSelected - 1;
        if (AppUtilities.getStringFromSharedPreferences(requireContext(), key_block[key]).equals(YES)) {
            AppUtilities.showBlockDialog(AppUtilities.getStringFromSharedPreferences(requireContext(), msg_block[key]), getString(R.string.attention), getString(R.string.accept), getChildFragmentManager());
        } else {
            getVoucherSelected(typeSelected);
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        rcvOptions.setVisibility(View.VISIBLE);
        ctlConnectionError.setVisibility(View.GONE);
        switch (response.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER:

                if(response.getResponse() instanceof VoucherResponseV2){

                    voucherResponse = (VoucherResponseV2) response.getResponse();
                    menuItems.addAll(MenuUtilities.getPayrollMenu(parent, voucherResponse.getData().getResponse()));
                    payrollVoucherMenuRecyclerAdapter.notifyItemRangeInserted(payrollVoucherMenuRecyclerAdapter.getItemCount(), menuItems.size());

                }else if(response.getResponse() instanceof VoucherResponse){
                    showDetail((VoucherResponse)response.getResponse());
                }

                 break;
        }
    }


    public void showDetail(VoucherResponse voucherResponse) {
        Bundle bundle = new Bundle();
        switch (voucherResponse.getTypeSelected()) {
            case CONSTANCE_TYPE_PAYROLL:
                PayrollVoucherFragment payrollVoucherFragment = new PayrollVoucherFragment();
                bundle.putString(AppConstants.BUNDLE_PAYROLL_DATES, gson.toJson(voucherResponse.getData().getResponse().getFechas_nominas()));
                bundle.putInt(AppConstants.BUNDLE_PAYROLL_OPTION,ICON_NOMINA);
                payrollVoucherFragment.setArguments(bundle);
                parent.replaceFragment(payrollVoucherFragment, PayrollVoucherFragment.TAG);
                break;
            case CONSTANCE_TYPE_BONUS:
                PayrollVoucherBonusFragment payrollVoucherBonusFragment = new PayrollVoucherBonusFragment();
                bundle.putString(AppConstants.BUNDLE_PAYROLL_DATES_BONUS, gson.toJson(voucherResponse.getData().getResponse().getFechas_Aguinaldo()));
                bundle.putInt(AppConstants.BUNDLE_PAYROLL_OPTION,ICON_AGUINALDO);
                payrollVoucherBonusFragment.setArguments(bundle);
                parent.replaceFragment(payrollVoucherBonusFragment, PayrollVoucherBonusFragment.TAG);
                break;
            case CONSTANCE_TYPE_SAVING_FUND:
                PayrollVoucherSavingFundFragment payrollVoucherSavingFundFragment = new PayrollVoucherSavingFundFragment();
                bundle.putString(AppConstants.BUNDLE_SAVING_FUND_DATES, gson.toJson(voucherResponse.getData().getResponse().getFecha_Corte_Cuenta()));
                bundle.putInt(AppConstants.BUNDLE_PAYROLL_OPTION,ICON_FONDOAHORRO);
                payrollVoucherSavingFundFragment.setArguments(bundle);
                parent.replaceFragment(payrollVoucherSavingFundFragment, PayrollVoucherSavingFundFragment.TAG);
                break;
            case CONSTANCE_TYPE_GAS:
                PayrollVoucherGasFragment payrollVoucherGasFragment = new PayrollVoucherGasFragment();
                bundle.putString(AppConstants.BUNDLE_GAS_DATES, gson.toJson(voucherResponse.getData().getResponse().getFecha_Gasolina()));
                bundle.putInt(AppConstants.BUNDLE_PAYROLL_OPTION,ICON_GASOLINA);
                payrollVoucherGasFragment.setArguments(bundle);
                parent.replaceFragment(payrollVoucherGasFragment, PayrollVoucherGasFragment.TAG);
                break;
            case CONSTANCE_TYPE_PAYROLL_PTU:
               PayrollVoucherPTUFragment payrollVoucherPTUFragment = new PayrollVoucherPTUFragment();
                bundle.putString(AppConstants.BUNDLE_PAYROLL_DATES_PTU, gson.toJson(voucherResponse.getData().getResponse().getFechas_Utilidades()));
                bundle.putInt(AppConstants.BUNDLE_PAYROLL_OPTION,ICON_PTU);
                payrollVoucherPTUFragment.setArguments(bundle);
                parent.replaceFragment(payrollVoucherPTUFragment, PayrollVoucherPTUFragment.TAG);
                break;
            case CONSTANCE_TYPE_ALIMONY:
                PayrollVoucherAlimonyFragment payrollVoucherAlimonyFragment = new PayrollVoucherAlimonyFragment();
                bundle.putString(AppConstants.BUNDLE_PAYROLL_BENEFICIARIES_ALIMONY, gson.toJson(voucherResponse.getData().getResponse().getDatosPencion()));
                bundle.putString(AppConstants.BUNDLE_BENEFICIARY_DATES, gson.toJson(voucherResponse.getData().getResponse().getFechasPensiones()));
                bundle.putInt(AppConstants.BUNDLE_PAYROLL_OPTION,ICON_PENSION);
                payrollVoucherAlimonyFragment.setArguments(bundle);
                parent.replaceFragment(payrollVoucherAlimonyFragment, PayrollVoucherAlimonyFragment.TAG);
                break;
        }
    }


    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.PAYROLL_VOUCHER:
                rcvOptions.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                SingleActionDialog dialog = new SingleActionDialog(
                        getContext(),
                        getString(R.string.visionaries_failure_default_title),
                        getString(R.string.visionaries_failure_default_content),
                        getString(R.string.visionaries_failure_default_action),
                        v -> parent.onBackPressed()
                );
                dialog.setCancelable(false);
                dialog.show();
                break;
            case ServicesRequestType.INVALID_TOKEN:
                DialogFragmentWarning dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.expired_session), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(this);
                dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvRefresh:
                coppelServicesPresenter.requestPayrollVoucher(parent.getProfileResponse().getColaborador(), ServicesConstants.PETITION_PAYROLL_VOUCHER_LIST_V2, parent.getLoginResponse().getToken());
                break;
        }
    }

    @Override
    public void onLeftOptionClick() {
    }

    @Override
    public void onRightOptionClick() {
        AppUtilities.closeApp(parent);
    }



    private void getVoucherSelected(int typeVoucher){
        coppelServicesPresenter.requestPayrollVoucherSelected(parent.getProfileResponse().getColaborador(),
                ServicesConstants.PETITION_PAYROLL_VOUCHER_SELECTED,typeVoucher, parent.getLoginResponse().getToken());

    }
}
