package com.coppel.rhconecta.dev.views.activities;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AbonoFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AditionalSaveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.RemoveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.DetailMovementFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.MovementsFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FondoAhorroActivity extends AppCompatActivity implements MovementsFragment.OnMovementsFragmentListener {

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
        } else if (optionSelected == 4) {
            fragmentSelected = MovementsFragment.Companion.newInstance();
        }

        fragmentTransaction.add(R.id.contentFragment, fragmentSelected, RemoveFragment.TAG).commit();
    }

    public void setToolbarTitle(String title) {
        tbActionBar.setTitle(title);
    }


    public LoanSavingFundResponse getLoanSavingFundResponse(){
        return loanSavingFundResponse;
    }

    @Override
    public void openMovementDetail(@NonNull Movement itemMovement) {
        Fragment fragment = DetailMovementFragment.Companion.newInstance(itemMovement);
        String backStackName = fragment.getClass().getCanonicalName();

        fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(backStackName);
        addFadeTransition(fragment);
        fragmentTransaction.replace(R.id.contentFragment, fragment, backStackName).commit();
    }

    private void addFadeTransition(Fragment fragment) {
        fragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
        fragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
    }
}
