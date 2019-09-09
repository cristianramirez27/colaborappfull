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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.Center;
import com.coppel.rhconecta.dev.business.models.ColaboratorControlsMonthResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.Estatus;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.FiltersControlsResponse;
import com.coppel.rhconecta.dev.business.models.RequestsLiquiGteListExpensesResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelColaboratorControlsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelColaboratorMonthsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelMonthsRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.customviews.HeaderTitlesList;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCenter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentEstatus;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_DETAIL_REQUETS_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.capitalizeText;
import static com.coppel.rhconecta.dev.views.utils.TextUtilities.getFormatedERH;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlsLiquidationsFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        ExpensesTravelColaboratorMonthsRecyclerAdapter.OnMonthClickListener,
        ExpensesTravelColaboratorControlsRecyclerAdapter.OnControlSelectedClickListener,
        ExpensesTravelMonthsRequestRecyclerAdapter.OnControlMonthClickListener,
DialogFragmentEstatus.OnButonOptionClick,     DialogFragmentCenter.OnButonOptionClick, DialogFragmentWarning.OnOptionClick{

    public static final String TAG = ControlsLiquidationsFragment.class.getSimpleName();
    private AppCompatActivity parent;

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.seleccionCentroLayout)
    RelativeLayout seleccionCentroLayout;
    @BindView(R.id.centro)
    TextView centro;

    @BindView(R.id.seleccionEstatusLayout)
    RelativeLayout seleccionEstatusLayout;
    @BindView(R.id.estatus)
    TextView estatus;

    @BindView(R.id.rcvControles)
    RecyclerView rcvControles;

    @BindView(R.id.expMonths)
    ExpandableSimpleTitle expMonths;
    @BindView(R.id.layoutMeses)
    LinearLayout layoutMeses;
    @BindView(R.id.rcvMonths)
    RecyclerView rcvMonths;


    @BindView(R.id.titulosControles)
    HeaderTitlesList titulosControles;

    @BindView(R.id.layoutControles)
    LinearLayout layoutControles;
    @BindView(R.id.txtNoControls)
    TextView txtNoControls;

    @BindView(R.id.txtNoMeses)
    TextView txtNoMeses;



    private DialogFragmentCenter dialogFragmentCenter;
    private Center centerSelected;
    private boolean showCenterDialog;

    private DialogFragmentEstatus dialogFragmentEstatus;
    private Estatus estatusSelected;

    private FiltersControlsResponse filtersControlsResponse;


    private ColaboratorRequestsListExpensesResponse.Months monthSelected;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private List<ColaboratorRequestsListExpensesResponse.ControlColaborator> controlColaborators;
    private List<ColaboratorRequestsListExpensesResponse.Months> monthsList;

    private ExpensesTravelColaboratorControlsRecyclerAdapter expensesTravelColaboratorControlsRecyclerAdapter;
    private ExpensesTravelColaboratorMonthsRecyclerAdapter expensesTravelColaboratorMonthsRecyclerAdapter;

    private boolean EXPIRED_SESSION;
    private DialogFragmentWarning dialogFragmentWarning;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static ControlsLiquidationsFragment getInstance(){
        ControlsLiquidationsFragment fragment = new ControlsLiquidationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_controles_liquidaciones, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        seleccionCentroLayout.setOnClickListener(this);
        seleccionEstatusLayout.setOnClickListener(this);

        if(getActivity() instanceof HomeActivity){
            parent = (HomeActivity) getActivity();
            ( (HomeActivity) parent).setToolbarTitle(getString(R.string.title_consult_liqui));
        }else
        if(getActivity() instanceof GastosViajeActivity){
            parent = (GastosViajeActivity) getActivity();
            ( (GastosViajeActivity) parent).setToolbarTitle(getString(R.string.title_consult_liqui));
        }


        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

        rcvControles.setHasFixedSize(true);
        rcvControles.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvMonths.setHasFixedSize(true);
        rcvMonths.setLayoutManager(new LinearLayoutManager(getContext()));
        titulosControles.setTitle1("Control");

        controlColaborators = new ArrayList<>();
        monthsList = new ArrayList<>();

        expMonths.setText("Controles liquidó y no liquidó:");
        expMonths.setsizeText(18);
        expMonths.setColorText(getResources().getColor(R.color.colorTextBlueCoppel));
        expMonths.setVisibilityDivider(View.GONE);
        expMonths.setMarginLeft(false);

        expMonths.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                if (expMonths.isExpanded()) {
                    getMonthsGte();
                    //layoutMeses.setVisibility(View.VISIBLE);
                } else {
                    layoutMeses.setVisibility(View.GONE);
                }
            }
        });

        expensesTravelColaboratorControlsRecyclerAdapter = new ExpensesTravelColaboratorControlsRecyclerAdapter(controlColaborators,false);
        expensesTravelColaboratorControlsRecyclerAdapter.setOnControlSelectedClickListener(this);
        expensesTravelColaboratorMonthsRecyclerAdapter = new ExpensesTravelColaboratorMonthsRecyclerAdapter(monthsList,false);
        expensesTravelColaboratorMonthsRecyclerAdapter.setOnMonthClickListener(this);
        expensesTravelColaboratorMonthsRecyclerAdapter.setOnControlMonthClickListener(this);

        rcvControles.setAdapter(expensesTravelColaboratorControlsRecyclerAdapter);
        rcvMonths.setAdapter(expensesTravelColaboratorMonthsRecyclerAdapter);

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

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length() == 0){
                    getControls();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFilters();
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

    private void getFilters(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTAR_FILTROS_CONTROLES, 6,numEmployer);
        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }


    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.seleccionCentroLayout:
                showCenterDialog = true;
                if(filtersControlsResponse == null){
                    getFilters();
                }else {
                    showCenters(filtersControlsResponse.getData().getResponse().getCentros());
                }
                break;

            case R.id.seleccionEstatusLayout:
                if(filtersControlsResponse == null){
                    getFilters();
                }else {
                    showEstatuss(filtersControlsResponse.getData().getResponse().getEstatus());
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.EXPENSESTRAVEL:

                if(response.getResponse() instanceof FiltersControlsResponse){
                    filtersControlsResponse = (FiltersControlsResponse)response.getResponse();
                    //Centros
                    if(filtersControlsResponse.getData().getResponse().getCentros() != null && !filtersControlsResponse.getData().getResponse().getCentros().isEmpty()){
                        //Se toma el primero por default si solo regresa 1 centro
                        if(filtersControlsResponse.getData().getResponse().getCentros().size() == 1){
                            centerSelected = filtersControlsResponse.getData().getResponse().getCentros().get(0);
                            centro.setText(filtersControlsResponse.getData().getResponse().getCentros().get(0).getNom_centro());
                        }else {
                            if(showCenterDialog) {
                                showCenterDialog = false;
                                showCenters(filtersControlsResponse.getData().getResponse().getCentros());
                            }
                        }
                    }
                    //Estatus
                    if(filtersControlsResponse.getData().getResponse().getEstatus() != null && !filtersControlsResponse.getData().getResponse().getEstatus().isEmpty()){
                        //Se toma el primero por default
                        estatusSelected = filtersControlsResponse.getData().getResponse().getEstatus().get(0);
                        estatus.setText(getFormatedERH(capitalizeText(getContext(), filtersControlsResponse.getData().getResponse().getEstatus().get(0).getNom_estatus_liq())));
                    }

                    //Solicitamos los controles con los filtros seleccionados
                    getControls();

                }else if(response.getResponse() instanceof RequestsLiquiGteListExpensesResponse) {
                    RequestsLiquiGteListExpensesResponse requestsLiquiGte = (RequestsLiquiGteListExpensesResponse)response.getResponse();
                    monthsList.clear();

                    if(requestsLiquiGte.getData().getResponse().getMeses() != null && !requestsLiquiGte.getData().getResponse().getMeses().isEmpty()) {
                        for(ColaboratorRequestsListExpensesResponse.Months month :requestsLiquiGte.getData().getResponse().getMeses()) {
                            monthsList.add(month);
                        }
                        expensesTravelColaboratorMonthsRecyclerAdapter.notifyDataSetChanged();
                        txtNoMeses.setVisibility(View.GONE);
                        layoutMeses.setVisibility(View.VISIBLE);
                    }else{
                        txtNoMeses.setVisibility(View.VISIBLE);
                        layoutMeses.setVisibility(View.GONE);
                    }

                }else if(response.getResponse() instanceof ColaboratorRequestsListExpensesResponse){
                    ColaboratorRequestsListExpensesResponse colaboratorResponse = (ColaboratorRequestsListExpensesResponse)response.getResponse();

                    controlColaborators.clear();

                    if(colaboratorResponse.getData().getResponse().getControles() != null && !colaboratorResponse.getData().getResponse().getControles().isEmpty()){
                        for(ColaboratorRequestsListExpensesResponse.ControlColaborator control :colaboratorResponse.getData().getResponse().getControles()){
                            controlColaborators.add(control);
                        }
                        expensesTravelColaboratorControlsRecyclerAdapter.notifyDataSetChanged();

                        txtNoControls.setVisibility(View.GONE);
                        layoutControles.setVisibility(View.VISIBLE);

                    }else {


                        txtNoControls.setVisibility(View.VISIBLE);
                        layoutControles.setVisibility(View.GONE);
                    }


                }else if(response.getResponse() instanceof ColaboratorControlsMonthResponse){
                    ColaboratorControlsMonthResponse monthResponse = (ColaboratorControlsMonthResponse)response.getResponse();
                    expensesTravelColaboratorMonthsRecyclerAdapter.getMonthSelected(monthSelected.getClv_mes())
                    .setMonthRequest(monthResponse.getData().getResponse().getControles());
                    expensesTravelColaboratorMonthsRecyclerAdapter.getMonthSelected(monthSelected.getClv_mes()).
                            setExpand(true);

                    expensesTravelColaboratorMonthsRecyclerAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.EXPENSESTRAVEL:
                    showWarningDialog(coppelServicesError.getMessage());
                    //showWarningDialog(getString(R.string.error_generic_service));
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
            }

        }
    }


    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        }else {
            dialogFragmentWarning.close();
            getActivity().onBackPressed();
        }
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
    public void onMonthClick(ColaboratorRequestsListExpensesResponse.Months month) {
        monthSelected = month;
        ColaboratorRequestsListExpensesResponse.Months  monthCurrent= expensesTravelColaboratorMonthsRecyclerAdapter.getMonthSelected(monthSelected.getClv_mes());

        if(monthCurrent.isExpand()){
            monthCurrent.setExpand(false);
            expensesTravelColaboratorMonthsRecyclerAdapter.notifyDataSetChanged();
        }else {

            for(ColaboratorRequestsListExpensesResponse.Months monthItem : expensesTravelColaboratorMonthsRecyclerAdapter.getDataItems()){
                monthItem.setExpand(false);
            }

            //String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
            //Se agregan la lista de empleados
            String numEmployer = month.getNum_empleados();
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
            ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_SOLICITUDES_MESES, 3,numEmployer);
            expensesTravelRequestData.setClv_mes(month.getClv_mes());
            coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
        }

    }

    @Override
    public void onControlSelectedClick(ColaboratorRequestsListExpensesResponse.ControlColaborator controlColaborator) {

        DetailExpenseTravelData detailExpenseTravelData = new DetailExpenseTravelData(DetailExpenseTravelType.CONTROLES_GTE,Integer.parseInt(controlColaborator.getControl()));
        detailExpenseTravelData.setData(controlColaborator);
        NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeActivity.class,
                BUNDLE_OPTION_DATA_TRAVEL_EXPENSES,detailExpenseTravelData,
                BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_DETAIL_REQUETS_CONTROLS);
    }

    @Override
    public void onControlMonthClick(ColaboratorControlsMonthResponse.ControlMonth controlMonth) {
        DetailExpenseTravelData detailExpenseTravelData = new DetailExpenseTravelData(DetailExpenseTravelType.CONTROL_LIQUIDO_NOLIQUIDO,Integer.parseInt(controlMonth.getControl()));
        detailExpenseTravelData.setData(controlMonth);

        NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeActivity.class,
                BUNDLE_OPTION_DATA_TRAVEL_EXPENSES,detailExpenseTravelData,
                BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_DETAIL_REQUETS_CONTROLS);
    }

    private void showCenters( List<Center> centers){
        CatalogueData statesData = new CatalogueData();
        statesData.setData(centers);
        dialogFragmentCenter = DialogFragmentCenter.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentCenter.setOnButtonClickListener(this);
        dialogFragmentCenter.show(parent.getSupportFragmentManager(), DialogFragmentCenter.TAG);
    }


    private void showEstatuss( List<Estatus> centers){
        CatalogueData statesData = new CatalogueData();
        statesData.setData(centers);
        dialogFragmentEstatus = DialogFragmentEstatus.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentEstatus.setOnButtonClickListener(this);
        dialogFragmentEstatus.show(parent.getSupportFragmentManager(), DialogFragmentCenter.TAG);
    }

    @Override
    public void onLeftOptionStateClick() {

        if(dialogFragmentCenter != null && dialogFragmentCenter.isVisible())
            dialogFragmentCenter.close();

        else if(dialogFragmentEstatus != null && dialogFragmentEstatus.isVisible())
            dialogFragmentEstatus.close();

    }

    @Override
    public void onRightOptionStateClick(Estatus data) {
        if(data != null) {
            estatusSelected = data;
            dialogFragmentEstatus.close();
            estatus.setText(estatusSelected.getNom_estatus_liq());
            getControls();
        }
    }

    @Override
    public void onRightOptionStateClick(Center data) {
        if(data != null) {
            centerSelected = data;
            dialogFragmentCenter.close();
            centro.setText(centerSelected.getNom_centro());
            getControls();
        }
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
            getControls();
        }
    }

    private void getControls(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTAR_CONTROLES_GTE, 10,numEmployer);
        expensesTravelRequestData.setClv_estatus(1);
        //Agregamos nombre de colaborador en la busqueda
        if(edtSearch.getText() != null && !edtSearch.getText().toString().isEmpty()) {
            expensesTravelRequestData.setNom_empleado(edtSearch.getText().toString());
        }
        //Agregamos centro seleccionado
        if(centerSelected != null){
            expensesTravelRequestData.setNum_centro(centerSelected.getNum_centro());
        }else {
            expensesTravelRequestData.setNum_centro(-1);
        }
        //Agregamos centro seleccionado
        if(estatusSelected != null){
            expensesTravelRequestData.setClv_estatus(estatusSelected.getIdu_estatus_factura());
        }
        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }

    private void getMonthsGte(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTAR_MESES_GTE, 10,numEmployer);
        expensesTravelRequestData.setClv_estatus(99);
        expensesTravelRequestData.setNom_empleado("");
        if(centerSelected != null){
            expensesTravelRequestData.setNum_centro(centerSelected.getNum_centro());
        }else {
            expensesTravelRequestData.setNum_centro(-1);
        }

        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }

}
