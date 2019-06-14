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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ColaboratorControlsMonthResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelColaboratorControlsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelColaboratorMonthsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelColaboratorRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.ExpensesTravelMonthsRequestRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;
import com.coppel.rhconecta.dev.views.customviews.HeaderTitlesList;
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
public class MyRequestAndControlsFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        ExpensesTravelColaboratorMonthsRecyclerAdapter.OnMonthClickListener, ExpensesTravelColaboratorRequestRecyclerAdapter.OnRequestSelectedClickListener,
        ExpensesTravelColaboratorControlsRecyclerAdapter.OnControlSelectedClickListener, ExpensesTravelMonthsRequestRecyclerAdapter.OnControlMonthClickListener {

    public static final String TAG = MyRequestAndControlsFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;
    @BindView(R.id.rcvControles)
    RecyclerView rcvControles;

    @BindView(R.id.expMonths)
    ExpandableSimpleTitle expMonths;
    @BindView(R.id.layoutMeses)
    LinearLayout layoutMeses;
    @BindView(R.id.rcvMonths)
    RecyclerView rcvMonths;

    @BindView(R.id.titulosSolicitudes)
    HeaderTitlesList titulosSolicitudes;
    @BindView(R.id.titulosControles)
    HeaderTitlesList titulosControles;



    private ColaboratorRequestsListExpensesResponse.Months monthSelected;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private List<ColaboratorRequestsListExpensesResponse.ControlColaborator> controlColaborators;
    private List<ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator> requestComplementsColaborators;
    private List<ColaboratorRequestsListExpensesResponse.Months> monthsList;



    private ExpensesTravelColaboratorRequestRecyclerAdapter expensesTravelColaboratorRequestRecyclerAdapter;
    private ExpensesTravelColaboratorControlsRecyclerAdapter expensesTravelColaboratorControlsRecyclerAdapter;
    private ExpensesTravelColaboratorMonthsRecyclerAdapter expensesTravelColaboratorMonthsRecyclerAdapter;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitudes_viaje, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof HomeActivity){
            parent = (HomeActivity) getActivity();
            ( (HomeActivity) parent).setToolbarTitle(getString(R.string.title_requests_controls));
        }else
        if(getActivity() instanceof GastosViajeActivity){
            parent = (GastosViajeActivity) getActivity();
            ( (GastosViajeActivity) parent).setToolbarTitle(getString(R.string.title_requests_controls));
        }


        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvControles.setHasFixedSize(true);
        rcvControles.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvMonths.setHasFixedSize(true);
        rcvMonths.setLayoutManager(new LinearLayoutManager(getContext()));
        titulosControles.setTitle1("Control");

        requestComplementsColaborators = new ArrayList<>();
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
                    layoutMeses.setVisibility(View.VISIBLE);
                } else {
                    layoutMeses.setVisibility(View.GONE);
                }
            }
        });

        expensesTravelColaboratorRequestRecyclerAdapter = new ExpensesTravelColaboratorRequestRecyclerAdapter(requestComplementsColaborators,true);
        expensesTravelColaboratorRequestRecyclerAdapter.setOnRequestSelectedClickListener(this);
        expensesTravelColaboratorControlsRecyclerAdapter = new ExpensesTravelColaboratorControlsRecyclerAdapter(controlColaborators,true);
        expensesTravelColaboratorControlsRecyclerAdapter.setOnControlSelectedClickListener(this);
        expensesTravelColaboratorMonthsRecyclerAdapter = new ExpensesTravelColaboratorMonthsRecyclerAdapter(monthsList,true);
        expensesTravelColaboratorMonthsRecyclerAdapter.setOnMonthClickListener(this);
        expensesTravelColaboratorMonthsRecyclerAdapter.setOnControlMonthClickListener(this);

        rcvSolicitudes.setAdapter(expensesTravelColaboratorRequestRecyclerAdapter);
        rcvControles.setAdapter(expensesTravelColaboratorControlsRecyclerAdapter);
        rcvMonths.setAdapter(expensesTravelColaboratorMonthsRecyclerAdapter);




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_COLABORADOR_SOLICITUD, 1,numEmployer);
        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
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

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnColaborator:

                break;

            case R.id.btnManager:

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
                if(response.getResponse() instanceof ColaboratorRequestsListExpensesResponse){
                    ColaboratorRequestsListExpensesResponse colaboratorResponse = (ColaboratorRequestsListExpensesResponse)response.getResponse();

                    for(ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator request :colaboratorResponse.getData().getResponse().getSolicitudes_Complementos()){
                        requestComplementsColaborators.add(request);
                    }

                    for(ColaboratorRequestsListExpensesResponse.ControlColaborator control :colaboratorResponse.getData().getResponse().getControles()){
                        controlColaborators.add(control);
                    }

                    for(ColaboratorRequestsListExpensesResponse.Months month :colaboratorResponse.getData().getResponse().getMeses()){
                        monthsList.add(month);
                    }

                    expensesTravelColaboratorRequestRecyclerAdapter.notifyDataSetChanged();
                    expensesTravelColaboratorControlsRecyclerAdapter.notifyDataSetChanged();
                    expensesTravelColaboratorMonthsRecyclerAdapter.notifyDataSetChanged();
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

            String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
            ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_SOLICITUDES_MESES, 3,numEmployer);
            expensesTravelRequestData.setClv_mes(month.getClv_mes());
            coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
        }

    }

    @Override
    public void onRequestSelectedClick(ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestComplementsColaborator) {

        DetailExpenseTravelType detailExpenseTravelType = requestComplementsColaborator.getTipo() == 1 ? DetailExpenseTravelType.COMPLEMENTO : DetailExpenseTravelType.SOLICITUD;
        DetailExpenseTravelData detailExpenseTravelData = new DetailExpenseTravelData(detailExpenseTravelType,requestComplementsColaborator.getClv_solicitud());
        detailExpenseTravelData.setData(requestComplementsColaborator);
        NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeActivity.class,
                BUNDLE_OPTION_DATA_TRAVEL_EXPENSES,detailExpenseTravelData,
                BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_DETAIL_REQUETS_CONTROLS);

    }

    @Override
    public void onControlSelectedClick(ColaboratorRequestsListExpensesResponse.ControlColaborator controlColaborator) {

        DetailExpenseTravelData detailExpenseTravelData = new DetailExpenseTravelData(DetailExpenseTravelType.CONTROL,Integer.parseInt(controlColaborator.getControl()));
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
}
