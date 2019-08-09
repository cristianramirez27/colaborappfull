package com.coppel.rhconecta.dev.views.fragments.holidays.gte;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.aditionaldays.HolidayAditionalDayListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar.HolidayCalendarListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest.HolidayRequestListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidaysMenuGteFragment extends Fragment implements  View.OnClickListener, IServicesContract.View{

    public static final String TAG = HolidaysMenuGteFragment.class.getSimpleName();
    private VacacionesActivity parent;
    @BindView(R.id.btnRequest)
    Button btnRequest;
    @BindView(R.id.btnAditionals)
    Button btnAditionals;
    @BindView(R.id.btnCalendar)
    Button btnCalendar;

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
        return view;
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
