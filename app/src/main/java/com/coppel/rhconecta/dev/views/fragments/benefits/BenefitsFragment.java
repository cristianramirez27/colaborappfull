package com.coppel.rhconecta.dev.views.fragments.benefits;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.OnGeocoderFinishedListener;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsCitiesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.BenefitsSearchEmptyResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsSearchResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsSearchResultsResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.LocationEntity;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.MyLocation;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.views.activities.BenefitsActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.BenefitsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentSelectLocation;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentSelectState;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.internal.IOException;

import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_CATEGORIES;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_CITY;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_SEARCH;
import static com.coppel.rhconecta.dev.business.Enums.BenefitsType.BENEFITS_STATES;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_RESULT_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.capitalizeText;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/* */
public class BenefitsFragment
        extends Fragment
        implements View.OnClickListener,
        IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,
        BenefitsRecyclerAdapter.OnBenefitsCategoryClickListener,
        DialogFragmentSelectState.OnButonOptionClick,
        DialogFragmentSelectLocation.OnSelectLocationsButtonsClickListener,
        DialogFragmentGetDocument.OnButtonClickListener {


    public static final int REQUEST_MAP_PERMISSION_CODE = 741;
    public static final int REQUEST_ACTIVATE_GPS = 742;
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

    private boolean closeBenefits;

    private List<BenefitsCategoriesResponse.Category> categories;
    private List<BenefitsStatesResponse.States> statesAvailables;
    private List<BenefitsCitiesResponse.City> citiesAvailables;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static BenefitsFragment getInstance(int tipoCarta) {
        BenefitsFragment fragment = new BenefitsFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER, tipoCarta);
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
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        if (search != null && !search.isEmpty()) {
            errorMessage.setVisibility(View.INVISIBLE);
            BenefitsRequestData benefitsRequestData = new BenefitsRequestData(BENEFITS_SEARCH, 6, stateSelected, citySelected);
            benefitsRequestData.setDes_busqueda(search);
            edtSearch.clearFocus();
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN);
            coppelServicesPresenter.getBenefits(benefitsRequestData, token);
        } else {
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
        if (validateGeolocalization()) {
            getGeolocalization();
        } else askPermission();
    }


    private void loadDataBenefitsBasic() {

        if (benefitsCategoriesResponse == null) {
            if (citySelectedName != null && !citySelectedName.isEmpty()) {
                txtCity.setText(String.format("%s %s?", getString(R.string.are_you_in_), citySelectedName));
            } else {
                stateSelected = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_STATE_COLABORADOR);
                ;
                citySelected = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_CITY_COLABORADOR);
                ;
                if (stateSelected == null || (stateSelected != null && stateSelected.equals("0")))
                    stateSelected = "2";
                if (citySelected == null || (citySelected != null && citySelected.equals("0")))
                    citySelected = "104";
            }

            requestCategories(stateSelected, citySelected);
        }
    }

    private void requestCategories(String state, String city) {
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN);
        benefitsRequestData = new BenefitsRequestData(BENEFITS_CATEGORIES, 3, state, city);
        coppelServicesPresenter.getBenefits(benefitsRequestData, token);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleChangeCity:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                showSelectLocation();
                break;

            case R.id.imgvRefresh:
                requestCategories(stateSelected, citySelected);
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ctlConnectionError.setVisibility(View.GONE);
        ctlContent.setVisibility(View.VISIBLE);
        switch (response.getType()) {
            case ServicesRequestType.BENEFITS:
                /*Obtenemos categorias*/
                if (response.getResponse() instanceof BenefitsCategoriesResponse) {
                    categories.clear();

                    BenefitsCategoriesResponse benefitsCategoriesResponse = (BenefitsCategoriesResponse) response.getResponse();

                    if (benefitsCategoriesResponse.getData().getResponse().getClave() == 1) {
                        closeBenefits = true;
                        showGetVoucherDialog(benefitsCategoriesResponse.getData().getResponse().getMensaje());
                    } else {

                        if (benefitsCategoriesResponse.getData().getResponse().getDatosCiudad() != null) {
                            stateSelected = String.valueOf(benefitsCategoriesResponse.getData().getResponse().getDatosCiudad().getNum_estado());
                            citySelected = String.valueOf(benefitsCategoriesResponse.getData().getResponse().getDatosCiudad().getNum_ciudad());
                            txtCity.setText(String.format("%s %s?", getString(R.string.are_you_in_),
                                    TextUtilities.capitalizeText(getActivity(), benefitsCategoriesResponse.getData().getResponse().getDatosCiudad().getNombre_ciudad())));
                        }
                        categories.clear();
                        for (BenefitsCategoriesResponse.Category category : ((BenefitsCategoriesResponse) response.getResponse()).getData().getResponse().getCategorias()) {
                            categories.add(category);
                        }
                        benefitsRecyclerAdapter.notifyDataSetChanged();
                    }

                } else if (response.getResponse() instanceof BenefitsStatesResponse) {
                    if (statesAvailables == null)
                        statesAvailables = new ArrayList<>();

                    statesAvailables.clear();
                    for (BenefitsStatesResponse.States state : ((BenefitsStatesResponse) response.getResponse()).getData().getResponse().getEstados()) {
                        statesAvailables.add(state);
                    }

                    showSelectState(statesAvailables);

                } else if (response.getResponse() instanceof BenefitsCitiesResponse) {
                    if (citiesAvailables == null)
                        citiesAvailables = new ArrayList<>();

                    citiesAvailables.clear();
                    for (BenefitsCitiesResponse.City city : ((BenefitsCitiesResponse) response.getResponse()).getData().getResponse().getCiudades()) {
                        citiesAvailables.add(city);
                    }

                    showSelectCity(citiesAvailables);

                } else if (response.getResponse() instanceof BenefitsSearchResponse) {

                    if (response.getResponse() instanceof BenefitsSearchEmptyResponse) {
                        List<BenefitsCategoriesResponse.CategoryEmpty> listCategory = ((BenefitsSearchEmptyResponse) response.getResponse()).getData().getResponse().getCategorias();

                        showGetVoucherDialog(listCategory.get(0).getDescripcion());
                    } else {

                        for (BenefitsCategoriesResponse.Category category : ((BenefitsSearchResultsResponse) response.getResponse()).getData().getResponse().getCategorias()) {
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
            case ServicesRequestType.INVALID_TOKEN:
                showErrorDialog(getString(R.string.expired_session));
                break;
            default:
                showErrorDialog(coppelServicesError.getMessage());
                break;
        }
        hideProgress();
    }

    /**
     *
     */
    private void showErrorDialog(String message) {
        new Handler().postDelayed(() -> {
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {

                @Override
                public void onLeftOptionClick() { }

                @Override
                public void onRightOptionClick() {
                    dialogFragmentWarning.close();
                    getActivity().onBackPressed();
                }

            });
            dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
            dialogFragmentLoader.close();
        }, 500);
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

    /**
     *
     */
    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();
    }

    /**
     *
     */
    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    /**
     *
     */
    @Override
    public void onCategoryClick(BenefitsCategoriesResponse.Category category) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 800)
            return;
        mLastClickTime = SystemClock.elapsedRealtime();
        if (!edtSearch.getText().toString().isEmpty())
            edtSearch.setText("");

        Intent intent = new IntentBuilder(new Intent(getActivity(), BenefitsActivity.class))
                .putSerializableExtra(AppConstants.BUNDLE_SELECTED_CATEGORY_BENEFITS, category)
                .putSerializableExtra(AppConstants.BUNDLE_SELECTED_BENEFIT_DATA, benefitsRequestData)
                .build();
        benefitsRequestData.setNum_estado(stateSelected);
        benefitsRequestData.setNum_ciudad(citySelected);
        getActivity().startActivityForResult(intent, 231);
    }

    /**
     *
     */
    private void selectState() {
        if (statesAvailables != null && !statesAvailables.isEmpty())
            showSelectState(statesAvailables);
        else {
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN);
            coppelServicesPresenter.getBenefits(new BenefitsRequestData(BENEFITS_STATES, 1), token);
        }
    }

    private void showSelectLocation() {
        dialogFragmentSelectLocation = DialogFragmentSelectLocation.getInstance();
        dialogFragmentSelectLocation.setOnSelectLocationsButtonsClickListener(this);
        dialogFragmentSelectLocation.setCancelable(true);
        dialogFragmentSelectLocation.show(parent.getSupportFragmentManager(), DialogFragmentSelectLocation.TAG);
    }

    private void showSelectState(List<BenefitsStatesResponse.States> statesList) {
        CatalogueData statesData = new CatalogueData();
        statesData.setData(statesList);
        dialogFragmentSelectState = DialogFragmentSelectState.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentSelectState.setOnButtonClickListener(this);
        dialogFragmentSelectState.show(parent.getSupportFragmentManager(), DialogFragmentSelectState.TAG);
    }

    private void showSelectCity(List<BenefitsCitiesResponse.City> cities) {
        dialogFragmentSelectState.close();
        CatalogueData statesData = new CatalogueData();
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
        if (data instanceof BenefitsStatesResponse.States) {
            stateSelected = String.valueOf(((BenefitsStatesResponse.States) data).getId_es());
            dialogFragmentSelectLocation.setState((BenefitsStatesResponse.States) data);
            dialogFragmentSelectState.close();
        } else if (data instanceof BenefitsCitiesResponse.City) {
            dialogFragmentSelectState.close();
            citySelected = String.valueOf(((BenefitsCitiesResponse.City) data).getId_es());
            citySelectedName = capitalizeText(getActivity(), String.valueOf(((BenefitsCitiesResponse.City) data).getNombre()));
            dialogFragmentSelectLocation.setCity((BenefitsCitiesResponse.City) data);
            dialogFragmentSelectState.close();
        }
    }

    /**
     * Location
     */
    @Override
    public void onSelectLocation() {
        requestCategories(stateSelected, citySelected);
        txtCity.setText(String.format("%s %s?", getString(R.string.are_you_in_), citySelectedName));
        dialogFragmentSelectLocation.close();
    }

    @Override
    public void onSelectState() {
        selectState();
    }

    @Override
    public void onSelectCity(BenefitsStatesResponse.States state) {
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getBenefits(new BenefitsRequestData(BENEFITS_CITY, 2, stateSelected), token);
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

        if (closeBenefits) {
            closeBenefits = false;
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        citySelectedName = "";
    }

    /**
     * Geolocalización
     */

    private boolean validateGeolocalization() {
        return ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_MAP_PERMISSION_CODE
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACTIVATE_GPS) {
            if (MyLocation.isGPSEnabled(getActivity())) {
                getGeolocalization();
            } else {
                loadDataBenefitsBasic();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MAP_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getGeolocalization();
                } else {
                    loadDataBenefitsBasic();
                }
                break;
            }
        }
    }


    private void getGeolocalization() {
        if (MyLocation.isGPSEnabled(getActivity())) {
            loadDataBenefitsLocation();
        } else {
            showDialogGPS();
        }
    }

    private void loadDataBenefitsLocation() {

        Location location = MyLocation.find_Location(getActivity());
        if (location != null) {
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN);
            benefitsRequestData = new BenefitsRequestData(BENEFITS_CATEGORIES, 8);
            benefitsRequestData.setLatitud(String.valueOf(location.getLatitude()));
            benefitsRequestData.setLongitud(String.valueOf(location.getLongitude()));
            coppelServicesPresenter.getBenefits(benefitsRequestData, token);


               /* getCityName(location, new OnGeocoderFinishedListener() {
                    @Override
                    public void onFinished(List<Address> results) {
                        // do something with the result

                        if(!results.isEmpty()){
                            txtCity.setText(String.format("%s %s?",getString(R.string.are_you_in_),
                                    results.get(0).getLocality()));
                        }

                    }
                });*/

        } else {
            //Sino se obtiene la ubicación hacemos la petición normal
            loadDataBenefitsBasic();
        }
    }


    private void showDialogGPS() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder
                .setMessage("Activa la Localización para que se puede determinar tu ubicación")
                .setCancelable(false)
                .setPositiveButton("Configuración",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                //startActivity(callGPSSettingIntent);
                                startActivityForResult(callGPSSettingIntent, REQUEST_ACTIVATE_GPS);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadDataBenefitsBasic();
                        dialog.cancel();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }


    public void getCityName(final Location location, final OnGeocoderFinishedListener listener) {
        new AsyncTask<Void, Integer, List<Address>>() {
            @Override
            protected List<Address> doInBackground(Void... arg0) {
                Geocoder coder = new Geocoder(getContext(), Locale.ENGLISH);
                List<Address> results = null;
                try {
                    results = coder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    // nothing
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<Address> results) {
                if (results != null && listener != null) {
                    listener.onFinished(results);
                }
            }
        }.execute();
    }

}