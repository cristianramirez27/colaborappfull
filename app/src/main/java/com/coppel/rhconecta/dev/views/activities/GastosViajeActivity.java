package com.coppel.rhconecta.dev.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.views.fragments.PayrollVoucherMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AbonoFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AditionalSaveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.RemoveFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ColaboratorControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.MyRequestAndControlsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesManagerFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesRolMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_DETAIL_REQUETS_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;

public class GastosViajeActivity extends AppCompatActivity  {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;
    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String TAG_FRAGMENT;
    private Object data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        ButterKnife.bind(this);
        setSupportActionBar(tbActionBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        TAG_FRAGMENT =  getIntent().getStringExtra(BUNDLE_OPTION_TRAVEL_EXPENSES);
        data = getIntent().getSerializableExtra(BUNDLE_OPTION_DATA_TRAVEL_EXPENSES);
        childFragmentManager = getSupportFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();
        onEvent(TAG_FRAGMENT,data);
    }

    public void setToolbarTitle(String title) {
        tbActionBar.setTitle(title);
    }

    public void onEvent(String tag,Object data) {
        switch (tag) {
            case OPTION_MANAGER:
                replaceFragment(new TravelExpensesManagerFragment(), PayrollVoucherMenuFragment.TAG);
                break;
            case OPTION_COLABORATOR:

                replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);

            case OPTION_DETAIL_REQUETS_CONTROLS:

                replaceFragment( ColaboratorControlFragment.getInstance((DetailExpenseTravelData)data), ColaboratorControlFragment.TAG);

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
        finish();
    }

}
