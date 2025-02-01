package com.coppel.rhconecta.dev.views.fragments;


import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_FUND_ADDITIONALS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_FUND_ADDITIONALS_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_FUND_PAY;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_FUND_PAY_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_FUND_WITHDRAW;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_FUND_WITHDRAW_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MYMOVEMENTS_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MY_MOVEMENTS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.views.fragments.LoanSavingFundMainChildFragment.REQUEST_SAVING;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISelectedOption;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.data.analytics.AnalyticsRepositoryImpl;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanSavingFundFragment extends Fragment implements IServicesContract.View, View.OnClickListener,
        DialogFragmentWarning.OnOptionClick,ISelectedOption {

    public static final String TAG = LoanSavingFundFragment.class.getSimpleName();
    private HomeActivity parent;
    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private CoppelServicesPresenter coppelServicesPresenter;
    private DialogFragmentLoader dialogFragmentLoader;
    private LoanSavingFundResponse loanSavingFundResponse;
    DialogFragmentWarning dialogFragmentWarning;
    private int requestorFlowError = -1;
    @BindView(R.id.ctlHeader)
    ConstraintLayout ctlHeader;
    @BindView(R.id.txvLoanMargin)
    TextView txvLoanMargin;
    @BindView(R.id.txvLoanMarginValue)
    TextView txvLoanMarginValue;
    @BindView(R.id.txvBuyMargin)
    TextView txvBuyMargin;
    @BindView(R.id.txvBuyMarginValue)
    TextView txvBuyMarginValue;
    @BindView(R.id.txvEnterpriseFound)
    TextView txvEnterpriseFound;
    @BindView(R.id.txvEnterpriseFoundValue)
    TextView txvEnterpriseFoundValue;
    @BindView(R.id.txvCurrentAccount)
    TextView txvCurrentAccount;
    @BindView(R.id.txvCurrentAccountValue)
    TextView txvCurrentAccountValue;
    @BindView(R.id.txvAdditionalSaving)
    TextView txvAdditionalSaving;
    @BindView(R.id.txvAdditionalSavingValue)
    TextView txvAdditionalSavingValue;
    @BindView(R.id.txvEmployeeFound)
    TextView txvEmployeeFound;
    @BindView(R.id.txvEmployeeFoundValue)
    TextView txvEmployeeFoundValue;
    @BindView(R.id.flChildFragmentContainer)
    FrameLayout flChildFragmentContainer;
    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.imgvRefresh)
    ImageView imgvRefresh;


    private boolean firstTime = true;
    private final String[] key_block = {BLOCK_FUND_WITHDRAW, BLOCK_FUND_PAY, BLOCK_FUND_ADDITIONALS, BLOCK_MY_MOVEMENTS};
    private final String[] msg_block = {BLOCK_FUND_WITHDRAW_MESSAGE, BLOCK_FUND_PAY_MESSAGE, BLOCK_FUND_ADDITIONALS_MESSAGE, BLOCK_MYMOVEMENTS_MESSAGE};
    AnalyticsRepositoryImpl analyticsRepositoryImpl = new AnalyticsRepositoryImpl();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loan_fragment_saving_found, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.loan_saving_fund));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        coppelServicesPresenter.requestLoansSavingFund(parent.getProfileResponse().getColaborador(),
                AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN));
        childFragmentManager = getChildFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(LoanSavingFundMainChildFragment.TAG);
        //LoanSavingFundMainChildFragment loanSavingFundMainChildFragment = LoanSavingFundMainChildFragment.getInstance(loanSavingFundResponse);
        //fragmentTransaction.add(R.id.flChildFragmentContainer,loanSavingFundMainChildFragment, LoanSavingFundMainChildFragment.TAG).commit();
        imgvRefresh.setOnClickListener(this);

        if(getActivity() instanceof  HomeActivity){
            ((HomeActivity) getActivity()).forceHideProgress();
        }
        analyticsRepositoryImpl.sendVisitFlow(9, 0);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                parent.onBackPressed();
                return true;
            case R.id.menuMessage:
                Toast.makeText(parent, "Under Development", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public HomeActivity getParent() {
        return parent;
    }

    public void replaceChildFragment(Fragment fragment, String tag) {
        fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(LoanSavingFundMainChildFragment.TAG);
        fragmentTransaction.replace(R.id.flChildFragmentContainer, fragment, tag).commit();
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ctlConnectionError.setVisibility(View.GONE);
        ctlHeader.setVisibility(View.VISIBLE);
        flChildFragmentContainer.setVisibility(View.VISIBLE);
        switch (response.getType()) {
            case ServicesRequestType.LOAN_SAVINGFUND:

                try {

                    loanSavingFundResponse = (LoanSavingFundResponse) response.getResponse();
                    txvLoanMarginValue.setText(TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getMargenCredito()))));
                    txvBuyMarginValue.setText(TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getMargenCompra()))));
                    txvEnterpriseFoundValue.setText(TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getFondoEmpresa()))));

                    String n = TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getCuentaCorriente());
                    txvCurrentAccountValue.setText(TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble(n)));
                    txvAdditionalSavingValue.setText(TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getAhorroAdicional()))));
                    txvEmployeeFoundValue.setText(TextUtilities.getNumberInCurrencyFormat(
                            Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getFondoTrabajador()))));

                    if (firstTime) {
                        firstTime = false;
                        LoanSavingFundMainChildFragment loanSavingFundMainChildFragment = LoanSavingFundMainChildFragment.getInstance(loanSavingFundResponse);
                        loanSavingFundMainChildFragment.setISelectedOption(this);
                        fragmentTransaction.add(R.id.flChildFragmentContainer, loanSavingFundMainChildFragment, LoanSavingFundMainChildFragment.TAG).commit();

                    }

                }catch (Exception e){
                    //e.printStackTrace();
                }


                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        requestorFlowError = coppelServicesError.getType();
        switch (requestorFlowError) {
            case ServicesRequestType.INVALID_TOKEN:
                showWarningDialog(getString(R.string.attention), getString(R.string.expired_session));
                break;
            case ServicesRequestType.TIME_OUT_REQUEST:
                showWarningDialog(getString(R.string.attention), coppelServicesError.getMessage());
                break;
            default:
                ctlConnectionError.setVisibility(View.VISIBLE);
                ctlHeader.setVisibility(View.GONE);
                flChildFragmentContainer.setVisibility(View.GONE);
                break;
        }
    }

    private void showWarningDialog(String title, String message) {
        if (dialogFragmentWarning == null) {
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(title, message, getString(R.string.accept));
            dialogFragmentWarning.setSinlgeOptionData(title, message, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(this);
            dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
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
                coppelServicesPresenter.requestLoansSavingFund(parent.getProfileResponse().getColaborador(),
                        AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN));
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**Actualizamos la informacion */
       // if(requestCode == REQUEST_SAVING && requestCode == RESULT_OK){
            coppelServicesPresenter.requestLoansSavingFund(parent.getProfileResponse().getColaborador(),
                    AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN));
        //}

    }

    @Override
    public void onLeftOptionClick() {
    }

    @Override
    public void onRightOptionClick() {
        if (requestorFlowError == ServicesRequestType.INVALID_TOKEN) {
            AppUtilities.closeApp(parent);
        } else {
            dialogFragmentWarning.close();
        }
    }

    @Override
    public void openOption(int optionSelected) {
        int key = optionSelected - 1;
        if (AppUtilities.getStringFromSharedPreferences(requireContext(), key_block[key]).equals(YES)) {
            AppUtilities.showBlockDialog(AppUtilities.getStringFromSharedPreferences(requireContext(), msg_block[key]), getString(R.string.attention), getString(R.string.accept), getChildFragmentManager());
        } else {
            Intent intentFondo = new IntentBuilder(new Intent(getActivity(), FondoAhorroActivity.class))
                    .putIntExtra(BUNDLE_TYPE_SAVING_OPTION, optionSelected)
                    .putSerializableExtra(BUNDLE_SAVINFOUND, loanSavingFundResponse)
                    .build();
            startActivityForResult(intentFondo, REQUEST_SAVING);
        }
    }


}
