package com.coppel.rhconecta.dev.presentation.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 *
 */
public class BannerViewPagerAdapter extends FragmentPagerAdapter {

    /* */
    private List<Fragment> items;


    /**
     *
     * @param fragmentManager
     * @param items
     */
    public BannerViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> items){
        super(fragmentManager);
        this.items = items;
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public Fragment getItem(int i) {
        return items.get(i);
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return items.size();
    }

}
