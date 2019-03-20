package com.coppel.rhconecta.dev.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.views.adapters.PagerAdapter;
import com.coppel.rhconecta.dev.views.fragments.LoanSavingFundMainChildFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.ChildInfoLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.ConfigFieldLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.HolidaysLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.PreviewLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.ScheduleInfoLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AbonoFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.AditionalSaveFragment;
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.RemoveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_RESPONSE_CONFIG_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_TYPE_SAVING_OPTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_BANK_CREDIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_IMSS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_INFONAVIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_VISA_PASSPORT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_WORK_RECORD;

public class FondoAhorroActivity extends AppCompatActivity  {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;

    private FragmentManager childFragmentManager;
    private FragmentTransaction fragmentTransaction;

    private  int optionSelected;

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


        optionSelected =  getIntent().getIntExtra(BUNDLE_TYPE_SAVING_OPTION,0);

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


    public String getTitleLetter(){

        switch (optionSelected){
            case TYPE_WORK_RECORD:
                    return getString(R.string.work_record);
                case TYPE_VISA_PASSPORT:
                    return getString(R.string.visa_passport);
                case TYPE_BANK_CREDIT:
                     return getString(R.string.bank_credit);
                case TYPE_IMSS:
                     return getString(R.string.imss);
                case TYPE_INFONAVIT:
                     return getString(R.string.infonavit);
                case TYPE_KINDERGARTEN:
                     return getString(R.string.kindergarten);

                    default: return "";
        }
    }
}
