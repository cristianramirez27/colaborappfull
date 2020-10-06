package com.coppel.rhconecta.dev.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AbonoFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AditionalSaveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.RemoveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;

public class FondoAhorroActivity extends AppCompatActivity  {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;

    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private  int optionSelected;
    private LoanSavingFundResponse loanSavingFundResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fondo);
        ButterKnife.bind(this);
        setSupportActionBar(tbActionBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        loanSavingFundResponse = (LoanSavingFundResponse) IntentExtension
                .getSerializableExtra(getIntent(), BUNDLE_SAVINFOUND);
        optionSelected = IntentExtension.getIntExtra(getIntent(), BUNDLE_TYPE_SAVING_OPTION);

        childFragmentManager = getSupportFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();

        Fragment fragmentSelected = null;
        if(optionSelected == 1){
            fragmentSelected = new RemoveFragment();
        }else if(optionSelected == 2){
            fragmentSelected = new AbonoFragment();
        }else if(optionSelected == 3){
            fragmentSelected = new AditionalSaveFragment();
        }

        fragmentTransaction.add(R.id.contentFragment, fragmentSelected, RemoveFragment.TAG).commit();
    }

    public void setToolbarTitle(String title) {
        tbActionBar.setTitle(title);
    }


    public LoanSavingFundResponse getLoanSavingFundResponse(){
        return loanSavingFundResponse;
    }

}
