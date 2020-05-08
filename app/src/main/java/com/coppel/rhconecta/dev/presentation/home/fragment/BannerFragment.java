package com.coppel.rhconecta.dev.presentation.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

/**
 *
 *
 */
public class BannerFragment extends Fragment {

    /*  */
    public Banner banner;
    private OnBannerClickListener onBannerClickListener;


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    /**
     *
     *
     */
    private void initViews(){
        // Init views
        assert getView() != null;
        View flBanner = getView().findViewById(R.id.flBanner);
        ImageView ivBannerPreview = getView().findViewById(R.id.ivBannerPreview);
        if(banner != null) {
            // Set image
            Glide.with(this)
                    .load(banner.getImage())
                    .error(R.drawable.ic_image_grey_300_48dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivBannerPreview);
            // Set on click listener
            flBanner.setOnClickListener(v -> onBannerClickListener.onClick(banner));
        }
    }

    /**
     *
     * @param banner
     * @return
     */
    public static BannerFragment createInstance(Banner banner, OnBannerClickListener onClickListener) {
        BannerFragment fragment = new BannerFragment();
        fragment.banner = banner;
        fragment.onBannerClickListener = onClickListener;
        return fragment;
    }

    /**
     *
     *
     */
    public interface OnBannerClickListener {

        /**
         *
         * @param banner
         */
        void onClick(Banner banner);

    }

}
