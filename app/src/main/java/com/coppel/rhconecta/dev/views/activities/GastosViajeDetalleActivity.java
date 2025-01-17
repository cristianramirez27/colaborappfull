package com.coppel.rhconecta.dev.views.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailControlColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.ImportsList;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
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

    @BindView(R.id.toolbar)
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

        TAG_FRAGMENT = IntentExtension.getStringExtra(getIntent(), BUNDLE_OPTION_TRAVEL_EXPENSES);
        data = IntentExtension.getSerializableExtra(getIntent(), BUNDLE_OPTION_DATA_TRAVEL_EXPENSES);
        childFragmentManager = getSupportFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();
        onEvent(TAG_FRAGMENT,data);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Aquí manejas el evento de retroceso.

                    finish(); // Finaliza la actividad.
                }
        });
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
        try {
            fragmentTransaction = childFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.replace(R.id.contentFragment, fragment, tag).commit();
        }catch (Exception e){
            Log.i("prueba",e.toString());
            Log.i("prueba",e.toString());
        }

    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
