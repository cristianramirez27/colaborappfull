package com.coppel.rhconecta.dev.views.fragments.holidays;


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
import android.widget.TextView;

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
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableHeader;
import com.coppel.rhconecta.dev.views.customviews.TextViewExpandableRightArrowHeader;
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
public class ColaboratorHolidaysFragment extends Fragment implements  View.OnClickListener, IServicesContract.View
      {

    public static final String TAG = ColaboratorHolidaysFragment.class.getSimpleName();
    private AppCompatActivity parent;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    @BindView(R.id.titleDetail)
    TextViewExpandableRightArrowHeader titleDetail;

    @BindView(R.id.layoutDetail)
    LinearLayout layoutDetail;



    private ColaboratorRequestsListExpensesResponse.Months monthSelected;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    //private List<ColaboratorRequestsListExpensesResponse.ControlColaborator> controlColaborators;



    //private ExpensesTravelColaboratorRequestRecyclerAdapter expensesTravelColaboratorRequestRecyclerAdapter;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacaciones_colaborador, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        if(getActivity() instanceof HomeActivity){
            parent = (HomeActivity) getActivity();
            ( (HomeActivity) parent).setToolbarTitle(getString(R.string.title_my_holidays));
        }


        //btnRequest.setOnClickListener(this);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);

        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));


        titleDetail.setTexts(getString(R.string.title_holidays_days),String.format("%s %s","10",getString(R.string.title_days)));

        titleDetail.setOnExpandableListener(new TextViewExpandableRightArrowHeader.OnExpandableListener() {
            @Override
            public void onClick() {
                if (titleDetail.isExpanded()) {
                    layoutDetail.setVisibility(View.VISIBLE);
                } else {
                    layoutDetail.setVisibility(View.GONE);
                }
            }
        });

       // rcvSolicitudes.setAdapter(expensesTravelColaboratorRequestRecyclerAdapter);




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_COLABORADOR_SOLICITUD, 1,numEmployer);
       // coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
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

                }else if(response.getResponse() instanceof ColaboratorControlsMonthResponse){


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



}
