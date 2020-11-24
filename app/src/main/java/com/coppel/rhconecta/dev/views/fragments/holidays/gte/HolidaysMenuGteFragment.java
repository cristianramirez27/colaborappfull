package com.coppel.rhconecta.dev.views.fragments.holidays.gte;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.IconsMenuRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.aditionaldays.HolidayAditionalDayListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar.HolidayCalendarListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest.HolidayRequestListFragment;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_ADITIONAL_DAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_CALENDAR_GRAL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAY_REQUESTS;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidaysMenuGteFragment extends Fragment implements  View.OnClickListener, IServicesContract.View, IconsMenuRecyclerAdapter.OnItemClick {

    public static final String TAG = HolidaysMenuGteFragment.class.getSimpleName();
    private VacacionesActivity parent;
    @BindView(R.id.btnRequest)
    Button btnRequest;
    @BindView(R.id.btnAditionals)
    Button btnAditionals;
    @BindView(R.id.btnCalendar)
    Button btnCalendar;
    @BindView(R.id.rcvOptions)
    RecyclerView rcvOptions;
    private List<HomeMenuItem> menuItems;
    private IconsMenuRecyclerAdapter iconsMenuRecyclerAdapter;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_holidays_gte_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (VacacionesActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.title_admin_holidays));

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        btnRequest.setOnClickListener(this);
        btnAditionals.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);

        /**Se inicia menu con iconos**/
        rcvOptions.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvOptions.setLayoutManager(gridLayoutManager);
        menuItems = new ArrayList<>();
        menuItems.addAll(MenuUtilities.getHolidaysManagerMenu(parent));
        iconsMenuRecyclerAdapter = new IconsMenuRecyclerAdapter(parent, menuItems, gridLayoutManager.getSpanCount());
        iconsMenuRecyclerAdapter.setOnItemClick(this);
        rcvOptions.setAdapter(iconsMenuRecyclerAdapter);

        return view;
    }


    @Override
    public void onItemClick(String tag) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (tag) {

            case OPTION_HOLIDAY_REQUESTS:
                parent.replaceFragment(new HolidayRequestListFragment(), HolidayRequestListFragment.TAG);

                break;
            case OPTION_CALENDAR_GRAL:
                parent.replaceFragment(new HolidayCalendarListFragment(), HolidayCalendarListFragment.TAG);

                break;

            case OPTION_ADITIONAL_DAYS:

                parent.replaceFragment(new HolidayAditionalDayListFragment(), HolidayRequestListFragment.TAG);

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
            case R.id.btnRequest:
                //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_COLABORATOR);
                parent.replaceFragment(new HolidayRequestListFragment(), HolidayRequestListFragment.TAG);

                break;

            case R.id.btnAditionals:

                parent.replaceFragment(new HolidayAditionalDayListFragment(), HolidayRequestListFragment.TAG);

                //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MANAGER);

                break;

            case R.id.btnCalendar:

                parent.replaceFragment(new HolidayCalendarListFragment(), HolidayCalendarListFragment.TAG);

                //NavigationUtil.openActivityClearTask(getActivity(), GastosViajeActivity.class,BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MANAGER);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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
        if(dialogFragmentLoader != null) dialogFragmentLoader.close();
    }
}
