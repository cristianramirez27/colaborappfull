package com.coppel.rhconecta.dev.views.fragments.holidays;


import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.data.analytics.AnalyticsRepositoryImpl;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.IconsMenuRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_MENU_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MENU_GTE;

/**
 *
 */
public class HolidaysRolMenuFragment extends Fragment implements IServicesContract.View, IconsMenuRecyclerAdapter.OnItemClick {

    public static final String TAG = HolidaysRolMenuFragment.class.getSimpleName();
    private HomeActivity parent;
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
        parent.setToolbarTitle(getString(R.string.title_holidays));

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
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
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        AnalyticsRepositoryImpl analyticsRepositoryImpl = new AnalyticsRepositoryImpl();
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (tag) {
            case AppConstants.OPTION_MENU_COLABORATOR:
                analyticsRepositoryImpl.sendVisitFlow(10, 1);
                NavigationUtil.openActivityWithStringParam(
                        getActivity(),
                        VacacionesActivity.class,
                        BUNDLE_OPTION_HOLIDAYS,
                        BUNDLE_OPTION_HOLIDAYREQUESTS
                );
                break;
            case OPTION_MENU_GTE:
                analyticsRepositoryImpl.sendVisitFlow(10, 2);
                NavigationUtil.openActivityWithStringParam(
                        getActivity(),
                        VacacionesActivity.class,
                        BUNDLE_OPTION_HOLIDAYS,
                        BUNDLE_OPTION_HOLIDAY_MENU_GTE
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
    public void showResponse(ServicesResponse response) {
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
