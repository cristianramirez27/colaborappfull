package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorControlsMonthResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.views.fragments.PayrollVoucherMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.AuthorizeRequestAndComplementsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ColaboratorControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ControlsLiquidationsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.MyRequestAndControlsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesManagerFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_AUTHORIZE_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_CONSULT_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_DETAIL_REQUETS_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;

public class GastosViajeActivity extends AppCompatActivity implements OnEventListener {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;
    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String TAG_FRAGMENT;
    private Object data;

    private Fragment currentFragment;

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

    @Override
    public void onEvent(String tag,Object data) {
        switch (tag) {
            case OPTION_MANAGER:
                replaceFragment(new TravelExpensesManagerFragment(), PayrollVoucherMenuFragment.TAG);
                break;
            case OPTION_COLABORATOR:
                replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                break;
            case OPTION_DETAIL_REQUETS_CONTROLS:
                replaceFragment(ColaboratorControlFragment.getInstance((DetailExpenseTravelData)data) , ColaboratorControlFragment.TAG);

                break;


            case OPTION_AUTHORIZE_REQUEST:

                currentFragment = AuthorizeRequestAndComplementsFragment.getInstance();
                replaceFragment( currentFragment, AuthorizeRequestAndComplementsFragment.TAG);

                break;

            case OPTION_CONSULT_CONTROLS:
                replaceFragment( ControlsLiquidationsFragment.getInstance(), AuthorizeRequestAndComplementsFragment.TAG);

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
        int backStackEntryCount = childFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            finish();
        } else if (backStackEntryCount > 1) {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            //onEvent(OPTION_AUTHORIZE_REQUEST,null);
            if(requestCode == 131 && (currentFragment!= null && currentFragment instanceof AuthorizeRequestAndComplementsFragment)){
                ((AuthorizeRequestAndComplementsFragment)currentFragment).getRequestExpenses();
            }else if((requestCode == 888 || resultCode == RESULT_OK)){

                if(currentFragment != null && currentFragment instanceof  AuthorizeRequestAndComplementsFragment ){
                    ((AuthorizeRequestAndComplementsFragment)currentFragment).getRequestExpenses();
                }else {
                    setResult(RESULT_OK);
                    finish();

                }



            }
        }

    }
}
