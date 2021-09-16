package com.coppel.rhconecta.dev.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterSignatureResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
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

import static android.app.Activity.RESULT_OK;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_PROOF;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_PROOF_MEESAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_VISA;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_VISA_MEESAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_CREDIT;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_CREDIT_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_IMSS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_IMSS_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_INFONAVIT;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_INFONAVIT_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_KINDER;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_LETTER_KINDER_MESSAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.CLAVE_LETTER_MAX;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_RESPONSE_CONFIG_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
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

    public static final int REQUEST_LETTER = 839;
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

    @BindView(R.id.txvConnectionError)
    TextView txvConnectionError;
    @BindView(R.id.txvPressToRefresh)
    TextView txvPressToRefresh;

    private long mLastClickTime = 0;
    private int typeLetterSelected = 0;

    private com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification ISurveyNotification;
    private DialogFragmentWarning dialogFragmentWarning;

    private LetterConfigResponse letterConfigResponse;
    private final String[] key_block = {BLOCK_LETTER_PROOF, BLOCK_LETTER_VISA, BLOCK_LETTER_CREDIT, BLOCK_LETTER_IMSS, BLOCK_LETTER_INFONAVIT, BLOCK_LETTER_KINDER};
    private final String[] msg_block = {BLOCK_LETTER_PROOF_MEESAGE, BLOCK_LETTER_VISA_MEESAGE, BLOCK_LETTER_CREDIT_MESSAGE, BLOCK_LETTER_IMSS_MESSAGE, BLOCK_LETTER_INFONAVIT_MESSAGE, BLOCK_LETTER_KINDER_MESSAGE};

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

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        int key = -1;
        switch (tag) {

            case AppConstants.OPTION_WORK_RECORD:
                typeLetter = TYPE_WORK_RECORD;
                key = 0;
                break;
            case AppConstants.OPTION_VISA_PASSPORT:
                typeLetter = TYPE_VISA_PASSPORT;
                key = 1;
                break;
            case AppConstants.OPTION_BANK_CREDIT:
                typeLetter = TYPE_BANK_CREDIT;
                key = 2;
                break;
            case AppConstants.OPTION_IMSS:
                typeLetter = TYPE_IMSS;
                key = 3;
                break;
            case AppConstants.OPTION_INFONAVIT:
                typeLetter = TYPE_INFONAVIT;
                key = 4;
                break;
            case AppConstants.OPTION_KINDERGARTEN:
                typeLetter = TYPE_KINDERGARTEN;
                key = 5;
                break;
        }

        /*Se invoca el servicio para ver el detalle de cada carta laboral*/
        if (AppUtilities.getStringFromSharedPreferences(requireContext(), key_block[key]).equals(YES)) {
            AppUtilities.showBlockDialog(AppUtilities.getStringFromSharedPreferences(requireContext(), msg_block[key]), getString(R.string.attention), getString(R.string.accept), getChildFragmentManager());
        } else {
            requestDetailLetter(typeLetter);
        }
       /* if(!hasAvailablePrints(typeLetter)){
            showAlertPrints();
            return;
        }

        Intent intentConfigLetter = new Intent(getActivity(), ConfigLetterActivity.class);
        intentConfigLetter.putExtra(BUNDLE_LETTER,typeLetter);
        startActivityForResult(intentConfigLetter,REQUEST_LETTER);*/
    }

    @Override
    public void showResponse(ServicesResponse response) {

        switch (response.getType()) {
            case ServicesRequestType.LETTERSVALIDATIONSIGNATURE:

                if(((LetterSignatureResponse)response.getResponse()).getData().getResponse().getClave() == 5 ||
                        ((LetterSignatureResponse)response.getResponse()).getData().getResponse().getClave() == 9){

                    imgvRefresh.setVisibility(View.GONE);
                    txvConnectionError.setVisibility(View.GONE);
                    txvPressToRefresh.setText(((LetterSignatureResponse)response.getResponse()).getData().getResponse().getMensaje());
                    rcvOptions.setVisibility(View.GONE);
                    ctlConnectionError.setVisibility(View.VISIBLE);
                }else {

                    rcvOptions.setVisibility(View.VISIBLE);
                    ctlConnectionError.setVisibility(View.GONE);
                    letterSignatureResponse = (LetterSignatureResponse) response.getResponse();
                    menuItems.clear();

                    menuItems.addAll(MenuUtilities.getEmploymentLettersMenu(parent));
                    employmentLettersMenuRecyclerAdapter.notifyItemRangeInserted(employmentLettersMenuRecyclerAdapter.getItemCount(), menuItems.size());


                }
                 break;
            /**Se valida la respuesta del detalle de la carta*/
            case ServicesRequestType.LETTERSCONFIG:
                letterConfigResponse = (LetterConfigResponse) response.getResponse();
                if(letterConfigResponse.getData().getResponse().getClave() != null &&
                        (letterConfigResponse.getData().getResponse().getClave().equals(String.valueOf(CLAVE_LETTER_MAX)))){
                    showAlertPrints(letterConfigResponse.getData().getResponse().getMensaje());
                }else {
                    String message = letterConfigResponse.getData().getResponse().getMensaje();
                    if (message == null) {
                        Intent intentConfigLetter = new IntentBuilder(new Intent(getActivity(), ConfigLetterActivity.class))
                                .putSerializableExtra(BUNDLE_RESPONSE_CONFIG_LETTER, letterConfigResponse)
                                .putIntExtra(BUNDLE_LETTER, typeLetterSelected)
                                .build();
                        startActivityForResult(intentConfigLetter, REQUEST_LETTER);
                    } else {
                        DialogFragmentWarning dialogFragmentWarning = new DialogFragmentWarning();
                        dialogFragmentWarning.setSinlgeOptionData(
                                getString(R.string.attention),
                                message,
                                getString(R.string.accept)
                        );
                        dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                            @Override
                            public void onLeftOptionClick() { }
                            @Override
                            public void onRightOptionClick() { dialogFragmentWarning.close(); }
                        });
                        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
                    }
                }
                hideProgress();
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        DialogFragmentWarning dialogFragmentWarning = new DialogFragmentWarning();
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSCONFIG:
                dialogFragmentWarning.setSinlgeOptionData(
                        getString(R.string.alert),
                        getString(R.string.error_generic_service),
                        getString(R.string.accept)
                );
                dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                    @Override
                    public void onLeftOptionClick() { }
                    @Override
                    public void onRightOptionClick() { dialogFragmentWarning.close(); }
                });
                dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
                break;
            case ServicesRequestType.LETTERSVALIDATIONSIGNATURE:
                rcvOptions.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);
                break;
            case ServicesRequestType.INVALID_TOKEN:
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


    private void showAlertPrints(String message){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message/*getString(R.string.max_letters_print)*/, getString(R.string.accept));
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

            }
        }, 200);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_LETTER && resultCode == RESULT_OK){
            if (coppelServicesPresenter != null) {
                coppelServicesPresenter.requestLettersValidationSignature(parent.getProfileResponse().getColaborador(), parent.getLoginResponse().getToken());
            }
        }
    }

    /*Se invoca el servicio antes de abrir el detalle de la carta*/
    private void requestDetailLetter(int typeLetter){
        letterConfigResponse = null;
        this.typeLetterSelected = typeLetter;
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        coppelServicesPresenter.requestLettersConfig(numEmployer,typeLetter,token);
    }
}
