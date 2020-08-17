package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.RolExpensesResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.activities.GastosViajeActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.IconsMenuRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MENU_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_BANK_CREDIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_IMSS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_INFONAVIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_VISA_PASSPORT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_WORK_RECORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelExpensesRolMenuFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,IconsMenuRecyclerAdapter.OnItemClick{

    public static final String TAG = TravelExpensesRolMenuFragment.class.getSimpleName();
    private HomeActivity parent;
    @BindView(R.id.btnColaborator)
    Button btnColaborator;
    @BindView(R.id.btnManager)
    Button btnManager;
    @BindView(R.id.rcvOptions)
    RecyclerView rcvOptions;
    private List<HomeMenuItem> menuItems;
    private IconsMenuRecyclerAdapter iconsMenuRecyclerAdapter;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private long mLastClickTime = 0;
    private com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification ISurveyNotification;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rol_select_travel_expenses_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (HomeActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.travel_expenses));

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        btnColaborator.setOnClickListener(this);
        btnManager.setOnClickListener(this);
        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);

        //btnColaborator.setVisibility(View.VISIBLE);
        //btnManager.setVisibility(View.VISIBLE);


        /**Se inicia menu con iconos**/
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvOptions.setLayoutManager(gridLayoutManager);
        menuItems = new ArrayList<>();
        menuItems.addAll(MenuUtilities.getRolUserMenu(parent));
        iconsMenuRecyclerAdapter = new IconsMenuRecyclerAdapter(parent, menuItems, gridLayoutManager.getSpanCount());
        iconsMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(iconsMenuRecyclerAdapter);

        if(getActivity() instanceof  HomeActivity){
            ((HomeActivity) getActivity()).forceHideProgress();
        }

        return view;
    }


    @Override
    public void onItemClick(String tag) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (tag) {

            case AppConstants.OPTION_MENU_COLABORATOR:
                //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_COLABORATOR);
                parent.replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);

                break;
            case OPTION_MENU_GTE:

                NavigationUtil.openActivityWithStringParam(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MANAGER);


                break;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_COLABORATOR);
                parent.replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);

                break;

            case R.id.btnManager:

                NavigationUtil.openActivityWithStringParam(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MANAGER);

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
                if(response.getResponse() instanceof RolExpensesResponse){

                    if(((RolExpensesResponse)response.getResponse()).getData().getResponse().getClv_estatus() == 1){
                        btnColaborator.setVisibility(View.VISIBLE);
                        btnManager.setVisibility(View.VISIBLE);
                    }else {

                        parent.replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                        //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_COLABORATOR);

                    }


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
