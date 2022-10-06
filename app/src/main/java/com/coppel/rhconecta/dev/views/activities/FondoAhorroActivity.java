package com.coppel.rhconecta.dev.views.activities;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_SAVINFOUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.customviews.ZendeskInboxView;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AbonoFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AditionalSaveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.RemoveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.DetailMovementFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.MovementsFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement;
import com.coppel.rhconecta.dev.views.utils.ZendeskStatusCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FondoAhorroActivity extends AnalyticsTimeAppCompatActivity implements MovementsFragment.OnMovementsFragmentListener, ZendeskStatusCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleToolbar)
    AppCompatTextView tvTitleToolbar;
    @BindView(R.id.zendeskInbox)
    ZendeskInboxView zendeskInbox;

    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private  int optionSelected;
    private LoanSavingFundResponse loanSavingFundResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fondo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
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

        zendeskInbox.setOnClickListener(view -> clickZendesk());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCallBackAndRefreshStatus(this);
    }

    public void setToolbarTitle(String title) {
        tvTitleToolbar.setText(title);
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

    /**
     * Callbacks zendesk
     */
    @Override
    public void enableZendeskFeature() {
        zendeskInbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableZendeskFeature() {
        zendeskInbox.setVisibility(View.GONE);
    }

    @Override
    public void zendeskChatOnLine() {
        zendeskInbox.setActive();
    }

    @Override
    public void zendeskChatOutLine() {
        zendeskInbox.setDisable();
    }

    @Override
    public void zendeskSetNotification() {
        zendeskInbox.setNotification();
    }

    @Override
    public void zendeskRemoveNotification() {
        zendeskInbox.removeNotification();
    }

    @Override
    public void zendeskErrorMessage(@NonNull String message) {
        showWarningDialog(message);
    }
}
