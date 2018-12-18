package com.coppel.rhconecta.dev.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LetterSignatureResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.EmploymentLettersMenuRecyclerAdapter;
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

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_BANK_CREDIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_IMSS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_INFONAVIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_VISA_PASSPORT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_WORK_RECORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmploymentLettersMenuFragment extends Fragment implements IServicesContract.View,
        EmploymentLettersMenuRecyclerAdapter.OnItemClick, View.OnClickListener, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = EmploymentLettersMenuFragment.class.getSimpleName();
    private HomeActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private LetterSignatureResponse letterSignatureResponse;
    private EmploymentLettersMenuRecyclerAdapter employmentLettersMenuRecyclerAdapter;
    private List<HomeMenuItem> menuItems;
    private Gson gson;
    @BindView(R.id.rcvOptions)
    RecyclerView rcvOptions;
    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.imgvRefresh)
    ImageView imgvRefresh;

    private com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification ISurveyNotification;
    private DialogFragmentWarning dialogFragmentWarning;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.employment_letters_menu, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.employment_letters_title));
        gson = new Gson();
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        menuItems = new ArrayList<>();
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvOptions.setLayoutManager(gridLayoutManager);
        employmentLettersMenuRecyclerAdapter = new EmploymentLettersMenuRecyclerAdapter(parent, menuItems, gridLayoutManager.getSpanCount());
        employmentLettersMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(employmentLettersMenuRecyclerAdapter);
        imgvRefresh.setOnClickListener(this);


        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (letterSignatureResponse == null) {
            coppelServicesPresenter.requestLettersValidationSignature(parent.getProfileResponse().getColaborador(), parent.getLoginResponse().getToken());
        }
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
        int typeLetter = 0;
        switch (tag) {

            case AppConstants.OPTION_WORK_RECORD:
                typeLetter = TYPE_WORK_RECORD;
                break;
            case AppConstants.OPTION_VISA_PASSPORT:
                typeLetter = TYPE_VISA_PASSPORT;
                break;
            case AppConstants.OPTION_BANK_CREDIT:
                typeLetter = TYPE_BANK_CREDIT;
                break;
            case AppConstants.OPTION_IMSS:
                typeLetter = TYPE_IMSS;
                break;
            case AppConstants.OPTION_INFONAVIT:
                typeLetter = TYPE_INFONAVIT;
                break;
            case AppConstants.OPTION_KINDERGARTEN:
                typeLetter = TYPE_KINDERGARTEN;
                break;
        }

        if(!hasAvailablePrints(TYPE_WORK_RECORD)){
            showAlertPrints();
            return;
        }

        Intent intentConfigLetter = new Intent(getActivity(), ConfigLetterActivity.class);
        intentConfigLetter.putExtra(BUNDLE_LETTER,typeLetter);
        startActivity(intentConfigLetter);
    }

    @Override
    public void showResponse(ServicesResponse response) {
        rcvOptions.setVisibility(View.VISIBLE);
        ctlConnectionError.setVisibility(View.GONE);
        switch (response.getType()) {
            case ServicesRequestType.LETTERSVALIDATIONSIGNATURE:
                letterSignatureResponse = (LetterSignatureResponse) response.getResponse();
                menuItems.addAll(MenuUtilities.getEmploymentLettersMenu(parent));
                employmentLettersMenuRecyclerAdapter.notifyItemRangeInserted(employmentLettersMenuRecyclerAdapter.getItemCount(), menuItems.size());
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSVALIDATIONSIGNATURE:
                rcvOptions.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
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
                coppelServicesPresenter.requestPayrollVoucher(parent.getProfileResponse().getColaborador(), ServicesConstants.PETITION_PAYROLL_VOUCHER_LIST, parent.getLoginResponse().getToken());
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


    private void showAlertPrints(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.max_letters_print), getString(R.string.accept));
                dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                    @Override
                    public void onLeftOptionClick() {

                    }

                    @Override
                    public void onRightOptionClick() {
                        dialogFragmentWarning.close();

                    }
                });
                dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
                dialogFragmentLoader.close();
            }
        }, 1500);
    }

    private boolean hasAvailablePrints(int typeLetter){
        for(LetterSignatureResponse.RemainingLetters remainingLetters :
                letterSignatureResponse.getData().getResponse().getCartasRestantes()){
            if(remainingLetters.getTipoCarta() == typeLetter){
                return remainingLetters.getRestantes() <= 0  ? false : true;
            }
        }

        return false;
    }
}
