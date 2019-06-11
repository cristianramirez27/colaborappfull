package com.coppel.rhconecta.dev.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailControlColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.DetailRequestColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.ImportsList;
import com.coppel.rhconecta.dev.views.fragments.PayrollVoucherMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ColaboratorControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.DetailControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.DetailRequestComplementFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.MyRequestAndControlsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesManagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_DETAIL_REQUETS_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_COMPLEMENT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_CONTROL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MORE_DETAIL_REQUEST;

public class GastosViajeDetalleActivity extends AppCompatActivity  {

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

            case OPTION_MORE_DETAIL_REQUEST:
                ImportsList importsListRequest = new ImportsList();
                importsListRequest.setImportes( ((DetailRequestColaboratorResponse)data).getData().getResponse().getVerDetallesSolicitud());
                replaceFragment( DetailRequestComplementFragment.getInstance(importsListRequest), DetailControlFragment.TAG);
                break;
            case OPTION_MORE_DETAIL_COMPLEMENT:
                ImportsList importsListComplement = new ImportsList();
                importsListComplement.setImportes( ((DetailRequestColaboratorResponse)data).getData().getResponse().getVerDetallesComplemento());
                replaceFragment( DetailRequestComplementFragment.getInstance(importsListComplement), DetailControlFragment.TAG);

                break;

            case OPTION_MORE_DETAIL_CONTROL:

                replaceFragment( DetailControlFragment.getInstance((DetailControlColaboratorResponse)data), DetailControlFragment.TAG);

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
