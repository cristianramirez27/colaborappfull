package com.coppel.rhconecta.dev.views.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.views.adapters.PagerAdapter;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.ChildInfoLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.ConfigFieldLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.HolidaysLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.PreviewLetterFragment;
import com.coppel.rhconecta.dev.views.fragments.employmentLetters.ScheduleInfoLetterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_RESPONSE_CONFIG_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_BANK_CREDIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_IMSS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_INFONAVIT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_VISA_PASSPORT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_WORK_RECORD;

public class ConfigLetterActivity extends AppCompatActivity implements ILettersNavigation {

    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private  int typeLetter;
    private PreviewDataVO previewDataVO;
    private LetterConfigResponse letterConfigResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_letter);
        ButterKnife.bind(this);
        typeLetter = IntentExtension.getIntExtra(getIntent(), BUNDLE_LETTER);
        letterConfigResponse = (LetterConfigResponse)
                IntentExtension.getSerializableExtra(getIntent(), BUNDLE_RESPONSE_CONFIG_LETTER);
        setSupportActionBar(tbActionBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        initPaging(typeLetter);
    }

    private void initPaging(int typeLetter) {

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        ConfigFieldLetterFragment configFieldLetterFragment = ConfigFieldLetterFragment.getInstance(typeLetter,letterConfigResponse);
        pagerAdapter.addFragment(configFieldLetterFragment);

        if(typeLetter == TYPE_KINDERGARTEN){
            ScheduleInfoLetterFragment scheduleInfoLetterFragment = ScheduleInfoLetterFragment.getInstance(typeLetter);
            HolidaysLetterFragment holidaysLetterFragment = HolidaysLetterFragment.getInstance(typeLetter);
            ChildInfoLetterFragment childInfoLetterFragment = ChildInfoLetterFragment.getInstance(typeLetter);
            pagerAdapter.addFragment(childInfoLetterFragment);
            pagerAdapter.addFragment(scheduleInfoLetterFragment);
            pagerAdapter.addFragment(holidaysLetterFragment);
        }

        PreviewLetterFragment previewLetterFragment = PreviewLetterFragment.getInstance(typeLetter);
        pagerAdapter.addFragment(previewLetterFragment);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void showFragmentAtPosition(int position, Object data) {
        previewDataVO = (PreviewDataVO) data;
        if(position == viewPager.getChildCount() - 1 ){
            PreviewLetterFragment previewLetterFragment = (PreviewLetterFragment) ((PagerAdapter)viewPager.getAdapter()).getFragmentAtPosition(position);
            previewLetterFragment.setDataLetter(previewDataVO);
        }

        viewPager.setCurrentItem(position);
    }

    @Override
    public void setKinderGardenData(Object data) {

        LetterConfigResponse configResponse = (LetterConfigResponse) data;
        ChildInfoLetterFragment childInfoLetterFragment = (ChildInfoLetterFragment) ((PagerAdapter)viewPager.getAdapter()).getFragmentAtPosition(1);
        ScheduleInfoLetterFragment scheduleInfoLetterFragment = (ScheduleInfoLetterFragment) ((PagerAdapter)viewPager.getAdapter()).getFragmentAtPosition(2);
        childInfoLetterFragment.setDataChild(configResponse.getData().getResponse().getDatosGuarderia().getNombre_hijos());
        scheduleInfoLetterFragment.setDataSchedule(configResponse.getData().getResponse().getDatosGuarderia().getDatoshorario());

    }

    public void setToolbarTitle(String title) {
        tbActionBar.setTitle(title);
    }


    public PreviewDataVO getPreviewDataVO() {
        return previewDataVO;
    }

    public String getTitleLetter(){

        switch (typeLetter){
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
