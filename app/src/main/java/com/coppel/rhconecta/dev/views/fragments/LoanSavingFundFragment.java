package com.coppel.rhconecta.dev.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISelectedOption;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.coppel.rhconecta.dev.views.fragments.LoanSavingFundMainChildFragment.REQUEST_SAVING;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;

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
                parent.getLoginResponse().getToken());
        childFragmentManager = getChildFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(LoanSavingFundMainChildFragment.TAG);
        //LoanSavingFundMainChildFragment loanSavingFundMainChildFragment = LoanSavingFundMainChildFragment.getInstance(loanSavingFundResponse);
        //fragmentTransaction.add(R.id.flChildFragmentContainer,loanSavingFundMainChildFragment, LoanSavingFundMainChildFragment.TAG).commit();
        imgvRefresh.setOnClickListener(this);
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

                loanSavingFundResponse = (LoanSavingFundResponse) response.getResponse();
                txvLoanMarginValue.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getMargenCredito()))));
                txvBuyMarginValue.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getMargenCompra()))));
                txvEnterpriseFoundValue.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getFondoEmpresa()))));
                txvCurrentAccountValue.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getCuentaCorriente()))));
                txvAdditionalSavingValue.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getAhorroAdicional()))));
                txvEmployeeFoundValue.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(TextUtilities.insertDecimalPoint(loanSavingFundResponse.getData().getResponse().getFondoTrabajador()))));

                if(firstTime){
                    firstTime = false;
                    LoanSavingFundMainChildFragment loanSavingFundMainChildFragment = LoanSavingFundMainChildFragment.getInstance(loanSavingFundResponse);
                    loanSavingFundMainChildFragment.setISelectedOption(this);
                    fragmentTransaction.add(R.id.flChildFragmentContainer,loanSavingFundMainChildFragment, LoanSavingFundMainChildFragment.TAG).commit();

                }


                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.INVALID_TOKEN:
                DialogFragmentWarning dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.expired_session), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(this);
                dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
                break;
            default:
                ctlConnectionError.setVisibility(View.VISIBLE);
                ctlHeader.setVisibility(View.GONE);
                flChildFragmentContainer.setVisibility(View.GONE);
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
                coppelServicesPresenter.requestLoansSavingFund(parent.getProfileResponse().getColaborador(),
                        parent.getLoginResponse().getToken());
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**Actualizamos la informacion */
       // if(requestCode == REQUEST_SAVING && requestCode == RESULT_OK){
            coppelServicesPresenter.requestLoansSavingFund(parent.getProfileResponse().getColaborador(),
                    parent.getLoginResponse().getToken());
        //}

    }

    @Override
    public void onLeftOptionClick() {
    }

    @Override
    public void onRightOptionClick() {
        AppUtilities.closeApp(parent);
    }


    @Override
    public void openOption(int optionSelected) {
        Intent intentFondo = new Intent(getActivity(), FondoAhorroActivity.class);
        intentFondo.putExtra(BUNDLE_TYPE_SAVING_OPTION,optionSelected);
        intentFondo.putExtra(BUNDLE_SAVINFOUND,loanSavingFundResponse);
        startActivityForResult(intentFondo,REQUEST_SAVING);
    }


}
