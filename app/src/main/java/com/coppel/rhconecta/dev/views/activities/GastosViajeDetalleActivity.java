package com.coppel.rhconecta.dev.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailControlColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.ImportsList;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.DetailControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.DetailRequestComplementFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.EditImportsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.RefuseReasonFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EDIT_AMOUNTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_COMPLEMENT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_CONTROL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_REFUSE_REQUEST;

public class GastosViajeDetalleActivity extends AppCompatActivity  {

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

    public void onEvent(String tag,Object data) {
        switch (tag) {


            case OPTION_MORE_DETAIL_REQUEST:
                replaceFragment( DetailRequestComplementFragment.getInstance((ImportsList)data), DetailControlFragment.TAG);
                break;
            case OPTION_MORE_DETAIL_COMPLEMENT:
                replaceFragment( DetailRequestComplementFragment.getInstance((ImportsList)data), DetailControlFragment.TAG);

                break;

            case OPTION_MORE_DETAIL_CONTROL:
                replaceFragment( DetailControlFragment.getInstance((DetailControlColaboratorResponse)data), DetailControlFragment.TAG);
                break;

            case OPTION_REFUSE_REQUEST:
                currentFragment = RefuseReasonFragment.getInstance((DetailExpenseTravelData)data );
                replaceFragment(currentFragment, RefuseReasonFragment.TAG);
                break;

            case OPTION_EDIT_AMOUNTS:
                replaceFragment(EditImportsFragment.getInstance((ImportsList)data), EditImportsFragment.TAG);
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
