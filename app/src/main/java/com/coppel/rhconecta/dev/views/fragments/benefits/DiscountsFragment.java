package com.coppel.rhconecta.dev.views.fragments.benefits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsDiscountsResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsEmptyResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.views.activities.BenefitsActivity;
import com.coppel.rhconecta.dev.views.activities.DialogAlertActivity;
import com.coppel.rhconecta.dev.views.adapters.DiscountsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_COMPANY;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_DISCOUNTS;
import static com.coppel.rhconecta.dev.views.activities.DialogAlertActivity.KEY_COMPANY;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_RESULT_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class DiscountsFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick , DiscountsRecyclerAdapter.OnBenefitsDiscountsClickListener, DialogFragmentGetDocument.OnButtonClickListener {

    public static final String TAG = DiscountsFragment.class.getSimpleName();
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    //private LetterConfigResponse letterConfigResponse;
    private DiscountsRecyclerAdapter discountsRecyclerAdapter;

    private DialogFragmentWarning dialogFragmentWarning;
    private long mLastClickTime = 0;

    private BenefitsActivity parent;

    @BindView(R.id.rcvDiscounts)
    RecyclerView rcvDiscounts;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    private DialogFragmentGetDocument dialogFragmentGetDocument;
    BenefitsCategoriesResponse.CategoryResult categorySelected;
    private BenefitsRequestData benefitsRequestData;

    private List<BenefitsDiscountsResponse.Discount> discounts;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static DiscountsFragment getInstance(int tipoCarta){
        DiscountsFragment fragment = new DiscountsFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discounts, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (BenefitsActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.benefits));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvDiscounts.setHasFixedSize(true);
        rcvDiscounts.setLayoutManager(new LinearLayoutManager(getContext()));
        discounts = new ArrayList<>();
        discountsRecyclerAdapter = new DiscountsRecyclerAdapter(getContext(), discounts);
        discountsRecyclerAdapter.setOnBenefitsDiscountsClickListener(this);
        rcvDiscounts.setAdapter(discountsRecyclerAdapter);
        rcvDiscounts.setOnClickListener(this);

        rcvDiscounts.getRecycledViewPool().setMaxRecycledViews(0, 0);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.BUNDLE_SELECTED_CATEGORY_BENEFITS) != null){
            categorySelected = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_SELECTED_CATEGORY_BENEFITS), BenefitsCategoriesResponse.CategoryResult.class);
            benefitsRequestData = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_SELECTED_BENEFIT_DATA), BenefitsRequestData.class);
            title.setText(categorySelected.getNombre());
            description.setText(categorySelected.getDescripcion());
            /**Se agrega validación para realizar la petición o mostrar los resultados de la busqueda*/
            if(categorySelected.getServicios() != null && !categorySelected.getServicios().isEmpty()){
                showResultDiscounts(categorySelected.getServicios());
            }else {
                requestDiscounts(categorySelected);
            }
        }

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //if (letterConfigResponse == null) {
        //  coppelServicesPresenter.requestLettersConfig(numEmployer,typeLetter,token);
        // }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                parent.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titleChangeCity:

                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.BENEFITS:
                if (response.getResponse() instanceof BenefitsDiscountsResponse){

                    showResultDiscounts(((BenefitsDiscountsResponse)response.getResponse()).getData().getResponse());

                }else if (response.getResponse() instanceof BenefitsCompaniesResponse){
                    List<BenefitsCompaniesResponse.Company> listCompany = ((BenefitsCompaniesResponse)response.getResponse() ).getData().getResponse();
                    if(!listCompany.isEmpty())
                        showCompanyDialog(listCompany.get(0));
                }else if(response.getResponse() instanceof BenefitsEmptyResponse){

                    showEmptyDialog(((BenefitsEmptyResponse)response.getResponse()).getData().getResponse());

                }
        }
    }


    private void showResultDiscounts(List<BenefitsDiscountsResponse.Discount> discountsResult){
        for(BenefitsDiscountsResponse.Discount discount : discountsResult){
            discounts.add(discount);
        }

        discountsRecyclerAdapter.notifyDataSetChanged();
    }

    private void showCompanyDialog(BenefitsCompaniesResponse.Company company) {
        Intent intent = new IntentBuilder(new Intent(getActivity(), DialogAlertActivity.class))
                .putSerializableExtra(KEY_COMPANY, company)
                .build();
        startActivity(intent);
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSCONFIG:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragmentWarning = new DialogFragmentWarning();
                        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                        dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
                            @Override
                            public void onLeftOptionClick() {
                            }

                            @Override
                            public void onRightOptionClick() {
                                dialogFragmentWarning.close();
                                getActivity().finish();
                            }
                        });
                        dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
                        dialogFragmentLoader.close();
                    }
                }, 500);
                break;
            case ServicesRequestType.INVALID_TOKEN:
                // EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
                break;
        }
        hideProgress();
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
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
      /*  if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        } else if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1])) {
                AppUtilities.openAppSettings(parent);
            }
        }*/
        //  WARNING_PERMISSIONS = false;
        dialogFragmentWarning.close();

    }

    private void showWarningDialog(String message){
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }



    private void requestDiscounts(BenefitsCategoriesResponse.Category category){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getBenefits( new BenefitsRequestData(BENEFITS_DISCOUNTS,4,
                benefitsRequestData.getNum_estado(),
                benefitsRequestData.getNum_ciudad(),
                String.valueOf(category.getCve())), token);
    }




    @Override
    public void onCategoryClick(BenefitsDiscountsResponse.Discount discount) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 800){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getBenefits(new BenefitsRequestData(BENEFITS_COMPANY,
                5,benefitsRequestData.getNum_estado(),benefitsRequestData.getNum_ciudad(),benefitsRequestData.getClave_servicio(),discount.getId_empresa()),token);
    }


    private void showEmptyDialog(String content) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setContentText(content);
        dialogFragmentGetDocument.setType(NO_RESULT_BENEFITS, parent);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 800){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        dialogFragmentGetDocument.close();
        getActivity().onBackPressed();

    }
}