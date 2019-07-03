package com.coppel.rhconecta.dev.views.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation;
import com.coppel.rhconecta.dev.business.models.BenefitsCategoriesResponse;
import com.coppel.rhconecta.dev.business.models.BenefitsRequestData;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LoanSavingFundResponse;
import com.coppel.rhconecta.dev.views.fragments.benefits.DiscountsFragment;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by flima on 18/01/2018.
 */

public class BenefitsActivity extends AppCompatActivity  {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;

    private BenefitsRequestData benefitsRequestData;
    private BenefitsCategoriesResponse.Category category;

    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benefits_activity);
        ButterKnife.bind(this);
        setSupportActionBar(tbActionBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        childFragmentManager = getSupportFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();

        category = (BenefitsCategoriesResponse.Category) getIntent().getSerializableExtra(AppConstants.BUNDLE_SELECTED_CATEGORY_BENEFITS);
        benefitsRequestData = (BenefitsRequestData) getIntent().getSerializableExtra(AppConstants.BUNDLE_SELECTED_BENEFIT_DATA);


        DiscountsFragment discountsFragment = new DiscountsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_SELECTED_CATEGORY_BENEFITS, new Gson().toJson(category));
        bundle.putString(AppConstants.BUNDLE_SELECTED_BENEFIT_DATA, new Gson().toJson(benefitsRequestData));
        discountsFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.contentFragment, discountsFragment, DiscountsFragment.TAG).commit();

    }

    public void setToolbarTitle(String title) {
        tbActionBar.setTitle(title);
    }

}