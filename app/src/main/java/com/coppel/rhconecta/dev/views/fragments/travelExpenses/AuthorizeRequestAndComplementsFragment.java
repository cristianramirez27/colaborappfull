package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.Center;
import com.coppel.rhconecta.dev.business.models.CentersResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelColaboratorRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.HeaderTitlesList;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCenter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_DETAIL_REQUETS_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorizeRequestAndComplementsFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        ExpensesTravelColaboratorRequestRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentCenter.OnButonOptionClick
{

    public static final String TAG = AuthorizeRequestAndComplementsFragment.class.getSimpleName();
    private AppCompatActivity parent;

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.seleccionCentroLayout)
    RelativeLayout seleccionCentroLayout;
    @BindView(R.id.centro)
    TextView centro;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
    @BindView(R.id.titulosSolicitudes)
    HeaderTitlesList titulosSolicitudes;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private List<ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator> requestComplementsColaborators;
    private ExpensesTravelColaboratorRequestRecyclerAdapter expensesTravelColaboratorRequestRecyclerAdapter;

    private OnEventListener OnEventListener;
    private DialogFragmentCenter dialogFragmentCenter;
    private Center centerSelected;
    private boolean showCenterDialog;


    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnEventListener = (OnEventListener)getActivity();
    }

    public static AuthorizeRequestAndComplementsFragment getInstance(){
        AuthorizeRequestAndComplementsFragment fragment = new AuthorizeRequestAndComplementsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_autorizar_solicitudes, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof HomeActivity){
            parent = (HomeActivity) getActivity();
            ( (HomeActivity) parent).setToolbarTitle(getString(R.string.title_authorize_requests_controls));
        }else
        if(getActivity() instanceof GastosViajeActivity){
            parent = (GastosViajeActivity) getActivity();
            ( (GastosViajeActivity) parent).setToolbarTitle(getString(R.string.title_requests_controls));
        }

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        titulosSolicitudes.setTitle1("Control");
        requestComplementsColaborators = new ArrayList<>();
        expensesTravelColaboratorRequestRecyclerAdapter = new ExpensesTravelColaboratorRequestRecyclerAdapter(requestComplementsColaborators,false);
        expensesTravelColaboratorRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(expensesTravelColaboratorRequestRecyclerAdapter);

        seleccionCentroLayout.setOnClickListener(this);

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

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCenters();

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
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.seleccionCentroLayout:
                showCenterDialog = true;
                getCenters();
                break;
        }

    }


    private void getCenters(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_CENTROS, 6,numEmployer);
        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.EXPENSESTRAVEL:
                if(response.getResponse() instanceof CentersResponse){
                    CentersResponse centersResponse = (CentersResponse)response.getResponse();
                    if(centersResponse.getData().getResponse().getCentros() != null && !centersResponse.getData().getResponse().getCentros().isEmpty()){
                        //Se toma el primeropor default si solo regresa 1 centro
                        if(centersResponse.getData().getResponse().getCentros().size() == 1){
                            centerSelected = centersResponse.getData().getResponse().getCentros().get(0);
                            centro.setText(centersResponse.getData().getResponse().getCentros().get(0).getNom_centro());
                        }else {

                            if(showCenterDialog) {
                                showCenterDialog = false;
                                showCenters(centersResponse.getData().getResponse().getCentros());
                            }
                        }
                    }

                } else if(response.getResponse() instanceof ColaboratorRequestsListExpensesResponse){
                    ColaboratorRequestsListExpensesResponse colaboratorResponse = (ColaboratorRequestsListExpensesResponse)response.getResponse();
                    for(ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator request :colaboratorResponse.getData().getResponse().getSolicitudes_Complementos()){
                        requestComplementsColaborators.add(request);
                    }
                    expensesTravelColaboratorRequestRecyclerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {

    }

    private void showCenters( List<Center> centers){
        CatalogueData statesData = new CatalogueData();
        statesData.setData(centers);
        dialogFragmentCenter = DialogFragmentCenter.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentCenter.setOnButtonClickListener(this);
        dialogFragmentCenter.show(parent.getSupportFragmentManager(), DialogFragmentCenter.TAG);
    }


    @Override
    public void onLeftOptionStateClick() {
        dialogFragmentCenter.close();
    }

    @Override
    public void onRightOptionStateClick(Center data) {
        centerSelected = data;
        dialogFragmentCenter.close();
        centro.setText(centerSelected.getNom_centro());

        getRequestExpenses();
    }


    private void getRequestExpenses(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_SOLICITUDES_AUTORIZAR, 7,numEmployer);
        expensesTravelRequestData.setClv_estatus(1);
        //Agregamos nombre de colaborador en la busqueda
        if(edtSearch.getText() != null && !edtSearch.getText().toString().isEmpty()) {
            expensesTravelRequestData.setNom_empleado(edtSearch.getText().toString());
        }
        //Agregamos centro seleccionado
        if(centerSelected != null){
            expensesTravelRequestData.setNum_centro(centerSelected.getNum_centro());
        }

        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }


    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
        if(dialogFragmentLoader != null) dialogFragmentLoader.close();
    }


    @Override
    public void onRequestSelectedClick(ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestComplementsColaborator) {

        DetailExpenseTravelType detailExpenseTravelType = DetailExpenseTravelType.SOLICITUD_A_AUTORIZAR;
        DetailExpenseTravelData detailExpenseTravelData = new DetailExpenseTravelData(detailExpenseTravelType,requestComplementsColaborator.getClv_solicitud());
        detailExpenseTravelData.setData(requestComplementsColaborator);
        NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeActivity.class,
                BUNDLE_OPTION_DATA_TRAVEL_EXPENSES,detailExpenseTravelData,
                BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_DETAIL_REQUETS_CONTROLS);

    }

    private void performSearch(String search) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if(search != null && !search.isEmpty()){
            edtSearch.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
            getRequestExpenses();
        }
    }
}
