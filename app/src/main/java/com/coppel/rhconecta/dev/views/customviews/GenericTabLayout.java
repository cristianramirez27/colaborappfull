package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IEnumTab;

import java.util.HashMap;
import java.util.Map;

/**
 * @author flima on 22/03/2018.
 */

public class GenericTabLayout<T extends IEnumTab> extends TabLayout implements TabLayout.OnTabSelectedListener {

    @LayoutRes
    private int layout;
    @LayoutRes
    private int selectedLayout;

    private float sizeTitle = 12f;

    private T[] customValues;

    private GenericPagerAdapter<T> mAdapter;
    private Map<Tab, TabHolder> tabView;


    public GenericTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenericTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs){
        tabView = new HashMap<>();
        TypedArray configurationParams =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.GenericTabLayout, 0, 0);
        try {
            this.layout = configurationParams.getResourceId(R.styleable.GenericTabLayout_tabLayout, R.layout.tab_main_menu);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            configurationParams.recycle();
        }
    }

    public void setSizeTitle(float sizeTitle) {
        this.sizeTitle = sizeTitle;
    }

    public void setUpWithoutViewPager(@NonNull T[] values) {
        this.customValues = values;
        for (T value : values) {
            addTab(newTab().setText(value.getName(getContext())), false);
        }
       // setCustomSelectedView(R.layout.tab_ad_emisor_selected);
        getTabAt(0).select();
    }

    public void setCustomSelectedView(@LayoutRes int layout){
        this.selectedLayout = layout;
        addOnTabSelectedListener(this);
    }

    @NonNull
    @Override
    public Tab newTab() {
        TabLayout.Tab tab = super.newTab();
        tab.setCustomView(layout);
        tabView.put(tab, new TabHolder(tab.getCustomView()));
        tabView.get(tab).mText.setTextColor(getTabTextColors());

        return tab;
    }


    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        if (viewPager != null && viewPager.getAdapter() != null && viewPager.getAdapter() instanceof GenericPagerAdapter){
            mAdapter = (GenericPagerAdapter) viewPager.getAdapter();

        }

        super.setupWithViewPager(viewPager);
    }

    @Override
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        Log.e("genric tab ", "addtab");
        int icon;
        if (mAdapter != null) {
            icon = mAdapter.getPageIcon(getTabCount());

        } else if (customValues != null) {
            icon = customValues[getTabCount()].getIconRes();
        } else {
            super.addTab(tab, setSelected);
            return;
        }

        ImageView img = tabView.get(tab).mImage;

        TextView textView = tabView.get(tab).mText;

        textView.setTextSize(sizeTitle);

        if (icon == IEnumTab.NO_ICON){
            img.setVisibility(GONE);
        } else {
            tab.setIcon(icon);
        }
        MarginLayoutParams layoutParams = (MarginLayoutParams)img.getLayoutParams();
        layoutParams.topMargin = 0;
        layoutParams.bottomMargin = 0;

        super.addTab(tab, setSelected);
    }

    public T getCurrentData(int position) {
        if( mAdapter != null) {
            return mAdapter.getCurrentData(position);
        }
        return customValues[position];
    }


    @Override
    public void onTabSelected(final Tab tab) {
        Log.e("genric tab ", "ontabSelect");
        ViewGroup customParen = (ViewGroup) LayoutInflater.from(getContext()).inflate(selectedLayout, null, false);

        ((ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(tab.getPosition())).removeViewAt(2);
        ((ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(tab.getPosition())).addView(customParen, 2);


        tab.setCustomView(customParen);

        tabView.put(tab, new TabHolder(tab.getCustomView()));
        tabView.get(tab).mText.setTextColor(getTabTextColors());

    }

    @Override
    public void onTabUnselected(Tab tab) {
        ViewGroup customParen = (ViewGroup) LayoutInflater.from(getContext()).inflate(layout, null, false);
        ((ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(tab.getPosition())).removeViewAt(2);
        ((ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(tab.getPosition())).addView(customParen, 2);

        tab.setCustomView(customParen);

        tabView.put(tab, new TabHolder(tab.getCustomView()));
        tabView.get(tab).mText.setTextColor(getTabTextColors());
    }

    @Override
    public void onTabReselected(Tab tab) {
        //No-op
    }

    private class TabHolder {
        private TextView mText;
        private ImageView mImage;

        private TabHolder(View view){
            this.mText = (TextView)view.findViewById(android.R.id.text1);
            this.mImage = (ImageView)view.findViewById(android.R.id.icon);
        }
    }
}
