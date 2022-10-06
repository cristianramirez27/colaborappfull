package com.coppel.rhconecta.dev.views.activities;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_AUTHORIZE_REQUEST;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLABORATOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_CONSULT_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_DETAIL_REQUETS_CONTROLS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.utils.OnEventListener;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.customviews.ZendeskInboxView;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.AuthorizeRequestAndComplementsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ColaboratorControlFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.ControlsLiquidationsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.MyRequestAndControlsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesManagerFragment;
import com.coppel.rhconecta.dev.views.utils.ZendeskStatusCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GastosViajeActivity extends AnalyticsTimeAppCompatActivity implements OnEventListener, ZendeskStatusCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleToolbar)
    AppCompatTextView tvTitleToolbar;
    @BindView(R.id.zendeskInbox)
    ZendeskInboxView zendeskInbox;

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
        setSupportActionBar(toolbar);
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
