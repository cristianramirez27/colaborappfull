package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.RolExpensesResponse;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.data.analytics.AnalyticsRepositoryImpl;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelExpensesRolMenuFragment extends Fragment implements View.OnClickListener, IServicesContract.View, IconsMenuRecyclerAdapter.OnItemClick {

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

    private long mLastClickTime = 0;
    private com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification) context;
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

        btnColaborator.setOnClickListener(this);
        btnManager.setOnClickListener(this);
        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);

        /**Se inicia menu con iconos**/
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvOptions.setLayoutManager(gridLayoutManager);
        menuItems = new ArrayList<>();
        menuItems.addAll(MenuUtilities.getRolUserMenu(parent));
        iconsMenuRecyclerAdapter = new IconsMenuRecyclerAdapter(parent, menuItems, gridLayoutManager.getSpanCount());
        iconsMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(iconsMenuRecyclerAdapter);

        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).forceHideProgress();
        }

        return view;
    }

    @Override
    public void onItemClick(String tag) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
            return;

        AnalyticsRepositoryImpl analyticsRepositoryImpl = new AnalyticsRepositoryImpl();
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (tag) {
            case AppConstants.OPTION_MENU_COLABORATOR:
                analyticsRepositoryImpl.sendVisitFlow(11, 1);
                parent.replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                break;
            case OPTION_MENU_GTE:
                analyticsRepositoryImpl.sendVisitFlow(11, 2);
                NavigationUtil.openActivityWithStringParam(
                        getActivity(),
                        GastosViajeActivity.class,
                        BUNDLE_OPTION_TRAVEL_EXPENSES,
                        OPTION_MANAGER
                );
                break;
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
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnColaborator:
                //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_COLABORATOR);
                parent.replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                break;
            case R.id.btnManager:
                NavigationUtil.openActivityWithStringParam(
                        getActivity(),
                        GastosViajeActivity.class,
                        BUNDLE_OPTION_TRAVEL_EXPENSES,
                        OPTION_MANAGER
                );
                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.EXPENSESTRAVEL:
                if (response.getResponse() instanceof RolExpensesResponse) {
                    if (((RolExpensesResponse) response.getResponse()).getData().getResponse().getClv_estatus() == 1) {
                        btnColaborator.setVisibility(View.VISIBLE);
                        btnManager.setVisibility(View.VISIBLE);
                    } else {
                        parent.replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
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
        if (dialogFragmentLoader != null) dialogFragmentLoader.close();
    }

}
