package com.coppel.rhconecta.dev.views.fragments.benefits;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.Benefits;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCitiesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCompaniesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.BenefitsSearchResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.LetterSchedulesDataVO;
import com.coppel.rhconecta.dev.business.models.LocationEntity;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.models.StatesData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.BenefitsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.FieldLetterRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCompany;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentSelectLocation;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentSelectState;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.PayrollVoucherGasDetailFragment;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_CATEGORIES;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_CITY;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_SEARCH;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_STATES;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_RESULT_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.capitalizeText;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class BenefitsFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,BenefitsRecyclerAdapter.OnBenefitsCategoryClickListener ,
        DialogFragmentSelectState.OnButonOptionClick ,DialogFragmentSelectLocation.OnSelectLocationsButtonsClickListener,
        DialogFragmentGetDocument.OnButtonClickListener{

    public static final String TAG = BenefitsFragment.class.getSimpleName();
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private BenefitsCategoriesResponse benefitsCategoriesResponse;
    private BenefitsRecyclerAdapter benefitsRecyclerAdapter;

    private DialogFragmentWarning dialogFragmentWarning;
    private DialogFragmentSelectState dialogFragmentSelectState;
    private DialogFragmentSelectLocation dialogFragmentSelectLocation;
    private BenefitsRequestData benefitsRequestData;

    private DialogFragmentGetDocument dialogFragmentGetDocument;

    private HomeActivity parent;

    private long mLastClickTime = 0;

    @BindView(R.id.rcvBenefits)
    RecyclerView rcvBenefits;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.titleChangeCity)
    TextView titleChangeCity;
    @BindView(R.id.txtCity)
    TextView txtCity;
    @BindView(R.id.errorMessage)
    TextView errorMessage;

    @BindView(R.id.ctlContent)
    RelativeLayout ctlContent;
    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.imgvRefresh)
    ImageView imgvRefresh;

    private static String stateSelected = "";
    private static String citySelected = "";
    private static String citySelectedName = "";

    private List<BenefitsCategoriesResponse.Category> categories;
    private List<BenefitsStatesResponse.States> statesAvailables;
    private List<BenefitsCitiesResponse.City> citiesAvailables;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static BenefitsFragment getInstance(int tipoCarta){
        BenefitsFragment fragment = new BenefitsFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_benefits, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.benefits));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvBenefits.setHasFixedSize(true);
        rcvBenefits.setLayoutManager(new LinearLayoutManager(getContext()));
        categories = new ArrayList<>();
        benefitsRecyclerAdapter = new BenefitsRecyclerAdapter(getContext(), categories);
        benefitsRecyclerAdapter.setOnBenefitsCategoryClickListener(this);
        rcvBenefits.setAdapter(benefitsRecyclerAdapter);
        rcvBenefits.setOnClickListener(this);
        titleChangeCity.setOnClickListener(this);
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        imgvRefresh.setOnClickListener(this);
        return view;
    }

    private void performSearch(String search) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        if(search != null && !search.isEmpty()){
            errorMessage.setVisibility(View.INVISIBLE);
            BenefitsRequestData benefitsRequestData = new BenefitsRequestData(BENEFITS_SEARCH,6,stateSelected,citySelected);
            benefitsRequestData.setDes_busqueda(search);
            edtSearch.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
            coppelServicesPresenter.getBenefits(benefitsRequestData,token);
        }else {
            errorMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (benefitsCategoriesResponse == null) {
            if(citySelectedName != null && !citySelectedName.isEmpty()){
                txtCity.setText(String.format("%s %s?",getString(R.string.are_you_in_),citySelectedName));
            }else {
                stateSelected = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_STATE_COLABORADOR);;
                citySelected = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_CITY_COLABORADOR);;
                if(stateSelected== null || (stateSelected != null &&  stateSelected.equals("0")))
                    stateSelected = "2";
                if(citySelected== null || (citySelected != null && citySelected.equals("0")))
                    citySelected = "104";
            }

            requestCategories(stateSelected,citySelected);
           }
    }

    private void requestCategories(String state,String city){
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        benefitsRequestData = new BenefitsRequestData(BENEFITS_CATEGORIES,3,state,city);
        coppelServicesPresenter.getBenefits(benefitsRequestData,token);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titleChangeCity:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                showSelectLocation();
                break;

            case R.id.imgvRefresh:
                requestCategories(stateSelected,citySelected);
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ctlConnectionError.setVisibility(View.GONE);
        ctlContent.setVisibility(View.VISIBLE);
        switch (response.getType()) {
            case ServicesRequestType.BENEFITS:
                categories.clear();
                /*Obtenemos categorias*/
                if(response.getResponse() instanceof BenefitsCategoriesResponse){
                    for(BenefitsCategoriesResponse.Category category : ((BenefitsCategoriesResponse)response.getResponse()).getData().getResponse().getCategorias()){
                        categories.add(category);
                    }

                    benefitsRecyclerAdapter.notifyDataSetChanged();
                }else  if(response.getResponse() instanceof BenefitsStatesResponse){
                    if(statesAvailables == null)
                        statesAvailables = new ArrayList<>();

                    statesAvailables.clear();
                    for(BenefitsStatesResponse.States state : ((BenefitsStatesResponse)response.getResponse()).getData().getResponse().getEstados()){
                        statesAvailables.add(state);
                    }

                    showSelectState(statesAvailables);

                }else  if(response.getResponse() instanceof BenefitsCitiesResponse){
                    if(citiesAvailables == null)
                        citiesAvailables = new ArrayList<>();

                    citiesAvailables.clear();
                    for(BenefitsCitiesResponse.City city : ((BenefitsCitiesResponse)response.getResponse()).getData().getResponse().getCiudades()){
                        citiesAvailables.add(city);
                    }

                    showSelectCity(citiesAvailables);

                }else if(response.getResponse() instanceof BenefitsSearchResponse){

                    List<BenefitsCategoriesResponse.Category> listCategory = ((BenefitsSearchResponse)response.getResponse()).getData().getResponse().getCategorias();

                    if(listCategory.size() == 1 && listCategory.get(0).getCve() == 0){
                        showGetVoucherDialog(listCategory.get(0).getDescripcion());
                    }else {

                        for(BenefitsCategoriesResponse.Category category : ((BenefitsSearchResponse)response.getResponse()).getData().getResponse().getCategorias()){
                            categories.add(category);
                        }

                        benefitsRecyclerAdapter.notifyDataSetChanged();
                    }

                }

                break;
        }
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

            default:
                ctlConnectionError.setVisibility(View.VISIBLE);
                ctlContent.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                    }
                }, 800);

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

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void onCategoryClick(BenefitsCategoriesResponse.Category category) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 800){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if(!edtSearch.getText().toString().isEmpty())
            edtSearch.setText("");

        DiscountsFragment discountsFragment = new DiscountsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_SELECTED_CATEGORY_BENEFITS, new Gson().toJson(category));
        bundle.putString(AppConstants.BUNDLE_SELECTED_BENEFIT_DATA, new Gson().toJson(benefitsRequestData));
        discountsFragment.setArguments(bundle);
        parent.replaceFragment(discountsFragment, DiscountsFragment.TAG);


    }

    private void selectState(){
        if(statesAvailables != null && !statesAvailables.isEmpty()){
            showSelectState(statesAvailables);
        }else {
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
            coppelServicesPresenter.getBenefits(new BenefitsRequestData(BENEFITS_STATES,1),token);
        }
    }

    private void showSelectLocation(){
        dialogFragmentSelectLocation = DialogFragmentSelectLocation.getInstance();
        dialogFragmentSelectLocation.setOnSelectLocationsButtonsClickListener(this);
        dialogFragmentSelectLocation.show(parent.getSupportFragmentManager(), DialogFragmentSelectLocation.TAG);
    }

    private void showSelectState( List<BenefitsStatesResponse.States> statesList){
        StatesData statesData = new StatesData();
        statesData.setData(statesList);
        dialogFragmentSelectState = DialogFragmentSelectState.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentSelectState.setOnButtonClickListener(this);
        dialogFragmentSelectState.show(parent.getSupportFragmentManager(), DialogFragmentSelectState.TAG);
    }

    private void showSelectCity(List<BenefitsCitiesResponse.City> cities){
        dialogFragmentSelectState.close();
        StatesData statesData = new StatesData();
        statesData.setData(cities);
        dialogFragmentSelectState = DialogFragmentSelectState.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentSelectState.setOnButtonClickListener(this);
        dialogFragmentSelectState.show(parent.getSupportFragmentManager(), DialogFragmentSelectState.TAG);
    }

    @Override
    public void onLeftOptionStateClick() {
        dialogFragmentSelectState.close();
    }

    @Override
    public void onRightOptionStateClick(LocationEntity data) {
        if(data instanceof BenefitsStatesResponse.States){
            stateSelected = String.valueOf(((BenefitsStatesResponse.States)data).getId_es());
            dialogFragmentSelectLocation.setState((BenefitsStatesResponse.States)data);
            dialogFragmentSelectState.close();
        } else if(data instanceof BenefitsCitiesResponse.City){
            dialogFragmentSelectState.close();
            citySelected =  String.valueOf( ((BenefitsCitiesResponse.City)data).getId_es());
            citySelectedName = capitalizeText(getActivity(),String.valueOf( ((BenefitsCitiesResponse.City)data).getNombre()));
            txtCity.setText(String.format("%s %s?",getString(R.string.are_you_in_),citySelectedName));
            dialogFragmentSelectLocation.setCity((BenefitsCitiesResponse.City)data);
            dialogFragmentSelectState.close();
        }
    }

    /**Location*/
    @Override
    public void onSelectLocation() {
        requestCategories(stateSelected,citySelected);
        dialogFragmentSelectLocation.close();
    }

    @Override
    public void onSelectState() {
        selectState();
    }

    @Override
    public void onSelectCity(BenefitsStatesResponse.States state) {
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getBenefits(new BenefitsRequestData(BENEFITS_CITY,2,stateSelected),token);
    }

    @Override
    public void closeSelectLocationDialog() {
        dialogFragmentSelectLocation.close();
    }

    private void showGetVoucherDialog(String msg) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(NO_RESULT_BENEFITS, parent);
        dialogFragmentGetDocument.setContentText(msg);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {

        dialogFragmentGetDocument.close();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        citySelectedName = "";
    }
}