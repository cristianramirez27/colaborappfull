package com.coppel.rhconecta.dev.presentation.home.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.presentation.home.fragment.BannerFragment;

import java.util.List;

/**
 *
 *
 */
public class BannerViewPagerAdapter extends FragmentStatePagerAdapter {

    /* */
    private List<Banner> banners;
    private BannerFragment.OnBannerClickListener onBannerClickListener;

    /**
     *
     *
     */
    public BannerViewPagerAdapter(
            FragmentManager fragmentManager,
            List<Banner> banners,
            BannerFragment.OnBannerClickListener onBannerClickListener
    ){
        super(fragmentManager);
        this.banners = banners;
        this.onBannerClickListener = onBannerClickListener;
    }

    /**
     *
     *
     */
    @Override
    public Fragment getItem(int i) {
        Banner banner = banners.get(i);
        return BannerFragment.createInstance(banner, onBannerClickListener);
    }

    /**
     *
     *
     */
    @Override
    public int getItemPosition(@NonNull Object item) {
        BannerFragment fragment = (BannerFragment) item;
        String id = fragment.banner.getId();
        Banner banner = new Banner(id, null, null, 1, null);
        int position = banners.indexOf(banner);
        if (position >= 0) return position;
        else return POSITION_NONE;
    }

    /**
     *
     *
     */
    @Override
    public int getCount() {
        return banners.size();
    }

}
