package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.modelview.BannerItem;
import com.coppel.rhconecta.dev.views.utils.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBannerPagerAdapter extends PagerAdapter {

    private Context context;
    private List<BannerItem> banners;
    private LayoutInflater layoutInflater;
    private OnBannerClick onBannerClick;
    @BindView(R.id.imgvBanner)
    ImageView imgvBanner;
    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.txvSubTitle)
    TextView txvSubTitle;

    public HomeBannerPagerAdapter(Context context, List<BannerItem> banners) {
        this.context = context;
        this.banners = banners;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

            View view = layoutInflater.inflate(R.layout.item_banner_home, container, false);
            ButterKnife.bind(this, view);
            GlideApp.with(context)
                    .load(banners.get(position).getImagePath())
                    .error(R.drawable.ic_image_grey_300_48dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgvBanner);
            txvTitle.setText(banners.get(position).getTitle());
            txvSubTitle.setText(banners.get(position).getSubTitle());
            container.addView(view);


            imgvBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onBannerClick != null) {
                        onBannerClick.onBannerClick(position);
                    }
                }
            });
            return view;


    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface OnBannerClick {
        void onBannerClick(int position);
    }

    public void setOnBannerClick(OnBannerClick onBannerClick) {
        this.onBannerClick = onBannerClick;
    }
}
