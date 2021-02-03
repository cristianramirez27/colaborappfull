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

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.AuthorizeRequestAndComplementsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ColaboratorControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ControlsLiquidationsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.MyRequestAndControlsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesManagerFragment;

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
    private boolean goToAuthorize;

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

        TAG_FRAGMENT = IntentExtension.getStringExtra(getIntent(), BUNDLE_OPTION_TRAVEL_EXPENSES);
        data = IntentExtension.getSerializableExtra(getIntent(), BUNDLE_OPTION_DATA_TRAVEL_EXPENSES);
        if(getIntent().hasExtra( "AUTHORIZE")){
            goToAuthorize = IntentExtension.getBooleanExtra(getIntent(), "AUTHORIZE");
        }

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
                currentFragment = new TravelExpensesManagerFragment();
                replaceFragment(currentFragment, TravelExpensesManagerFragment.TAG);

                if(goToAuthorize){
                    onEvent(OPTION_AUTHORIZE_REQUEST,null);
                }
                break;
            case OPTION_COLABORATOR:
                currentFragment = new MyRequestAndControlsFragment();
                replaceFragment(currentFragment, MyRequestAndControlsFragment.TAG);
                break;
            case OPTION_DETAIL_REQUETS_CONTROLS:
                replaceFragment(ColaboratorControlFragment.getInstance((DetailExpenseTravelData)data) , ColaboratorControlFragment.TAG);

                break;


            case OPTION_AUTHORIZE_REQUEST:

                currentFragment = AuthorizeRequestAndComplementsFragment.getInstance();
                replaceFragment( currentFragment, AuthorizeRequestAndComplementsFragment.TAG);

                break;

            case OPTION_CONSULT_CONTROLS:
                currentFragment = ControlsLiquidationsFragment.getInstance();
                replaceFragment(currentFragment, AuthorizeRequestAndComplementsFragment.TAG);

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
