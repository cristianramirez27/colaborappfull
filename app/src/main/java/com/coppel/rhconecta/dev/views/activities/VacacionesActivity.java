package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.models.CalendarProposedData;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.ConfigurationHolidaysData;
import com.coppel.rhconecta.dev.business.models.HolidayPeriod;
import com.coppel.rhconecta.dev.business.models.SpliceSelectedVO;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.aditionaldays.ColaboratorAditionalDaysHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar.ColaboratorCalendarGralHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar.ColaboratorCalendarGralPeriodsHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest.ColaboratorCalendarHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.colaborator.ColaboratorHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.colaborator.ColaboratorHolidaysScheduleFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest.ColaboratorRequestHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.colaborator.DetailPeriodFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar.HolidayCalendarListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.holidaysrequest.HolidayRequestListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.calendar.HolidaySpliceCalendarListFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.gte.HolidaysMenuGteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_COLABORATOR_SCHEDULE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_ADITIONAL_COLABORATOR_REQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_PERIODS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_CALENDAR_PROPOSED;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_COLABORATOR_REQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_MENU_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_REQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_AUTHORIZE_REQUEST;

public class VacacionesActivity extends AppCompatActivity implements OnEventListener, IScheduleOptions {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;
    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String TAG_FRAGMENT;
    private Object data;
    private String titleActivity;
    private boolean isOpenMenuToolbar;
    private Fragment currentFragment;

    @BindView(R.id.eliminateOption)
    TextView eliminateOption;
    @BindView(R.id.authorizeOption)
    TextView authorizeOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacaciones);
        ButterKnife.bind(this);
        setSupportActionBar(tbActionBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        TAG_FRAGMENT = IntentExtension.getStringExtra(getIntent(), BUNDLE_OPTION_HOLIDAYS);
        data = IntentExtension.getSerializableExtra(getIntent(), BUNDLE_OPTION_DATA_HOLIDAYS);

        childFragmentManager = getSupportFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();
        onEvent(TAG_FRAGMENT, data);
    }

    public void setToolbarTitle(String title) {
        titleActivity = title;
        tbActionBar.setTitle(title);
    }

    @Override
    public void onEvent(String tag,Object data) {

        currentFragment = null;
        switch (tag) {
            case BUNDLE_OPTION_COLABORATOR_SCHEDULE:
                currentFragment = ColaboratorHolidaysScheduleFragment.getInstance((ConfigurationHolidaysData)data);
                replaceFragment(currentFragment, ColaboratorHolidaysScheduleFragment.TAG);
                break;
            case BUNDLE_OPTION_HOLIDAYREQUESTS:
                replaceFragment(new ColaboratorHolidaysFragment(), ColaboratorHolidaysScheduleFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAYREQUESTS_DETAIL:
                replaceFragment(DetailPeriodFragment.getInstance((HolidayPeriod) data), DetailPeriodFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAY_MENU_GTE:
                replaceFragment(new HolidaysMenuGteFragment(), HolidaysMenuGteFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAY_REQUESTS:
                replaceFragment(new HolidayRequestListFragment(), HolidayRequestListFragment.TAG);
                break;


            case BUNDLE_OPTION_HOLIDAY_COLABORATOR_REQUESTS:
                replaceFragment(ColaboratorRequestHolidaysFragment.getInstance((ColaboratorHoliday)data), ColaboratorRequestHolidaysFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAY_ADITIONAL_COLABORATOR_REQUESTS:
                replaceFragment(ColaboratorAditionalDaysHolidaysFragment.getInstance((ColaboratorHoliday)data), ColaboratorAditionalDaysHolidaysFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAY_CALENDAR_PROPOSED:
                replaceFragment(ColaboratorCalendarHolidaysFragment.getInstance((CalendarProposedData)data), ColaboratorCalendarHolidaysFragment.TAG);
                break;


            case BUNDLE_OPTION_HOLIDAY_CALENDAR:

                currentFragment = HolidayCalendarListFragment.getInstance();
                replaceFragment(currentFragment, HolidayCalendarListFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAY_SPLICE_CALENDAR:
                replaceFragment(HolidaySpliceCalendarListFragment.getInstance((SpliceSelectedVO)data), HolidaySpliceCalendarListFragment.TAG);
                break;


            case BUNDLE_OPTION_HOLIDAY_CALENDAR_COLABORATOR:

                currentFragment = ColaboratorCalendarGralHolidaysFragment.getInstance((CalendarProposedData) data);
                replaceFragment(currentFragment, ColaboratorCalendarGralHolidaysFragment.TAG);
                break;

            case BUNDLE_OPTION_HOLIDAY_CALENDAR_PERIODS:
                replaceFragment(ColaboratorCalendarGralPeriodsHolidaysFragment.getInstance((CalendarProposedData) data), ColaboratorCalendarGralPeriodsHolidaysFragment.TAG);
                break;


        }
    }

    public void replaceFragment(Fragment fragment, String tag) {
        fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.replace(R.id.contentFragment, fragment, tag).commit();
    }




    @Override
    public void onBackPressed() {

        if(currentFragment != null && currentFragment instanceof ColaboratorHolidaysScheduleFragment){
            ((ColaboratorHolidaysScheduleFragment)currentFragment).cleanSelection();
        }

        if(isOpenMenuToolbar){
            isOpenMenuToolbar = false;
            showTitle(true);
            showEliminatedOption(false,"");
            showAuthorizeOption(false);

        }else {
            showEliminatedOption(false,"");
            int backStackEntryCount = childFragmentManager.getBackStackEntryCount();
            if (backStackEntryCount == 1) {
                if(currentFragment instanceof ColaboratorCalendarGralHolidaysFragment){
                    childFragmentManager.popBackStack();
                   onEvent(BUNDLE_OPTION_HOLIDAY_CALENDAR,null);
                }else{
                    finish();
                }
            } else if (backStackEntryCount > 1) {
                super.onBackPressed();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 888 && resultCode == RESULT_OK){
            onEvent(OPTION_AUTHORIZE_REQUEST,null);
        }

    }

    @Override
    public void showEliminatedOption(boolean show,String name) {
        this.eliminateOption.setVisibility(show ? View.VISIBLE : View.GONE);
        this.eliminateOption.setText(name);
    }

    @Override
    public void showAuthorizeOption(boolean show) {
        this.authorizeOption.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setActionEliminatedOption(Command action) {
            this.eliminateOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    action.execute();
                }
            });
    }


    @Override
    public void setActionAuthorizeOption(Command action) {
        this.authorizeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.execute();
            }
        });
    }

    @Override
    public void showTitle(boolean show) {
        tbActionBar.setTitle(show ? titleActivity : "");
        changeIconToolbar(show ? R.drawable.ic_left_arrow_black : R.drawable.ic_close_black );
        if(!show){
            isOpenMenuToolbar = true;
        }
    }

    @Override
    public void changeIconToolbar(int icon) {
        tbActionBar.setNavigationIcon(icon);
    }
}
