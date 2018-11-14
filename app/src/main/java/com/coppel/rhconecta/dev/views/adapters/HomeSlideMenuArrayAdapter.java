package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSlideMenuArrayAdapter extends ArrayAdapter<HomeMenuItem> {

    @BindView(R.id.imgvOptionIcon)
    ImageView imgvOptionIcon;
    @BindView(R.id.txvOptionName)
    TextView txvOptionName;

    public HomeSlideMenuArrayAdapter(@NonNull Context context, int resource, @NonNull List<HomeMenuItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeMenuItem option = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_slider_home_menu, parent, false);
        }
        ButterKnife.bind(this, convertView);
        if (option != null) {
            imgvOptionIcon.setImageDrawable(MenuUtilities.getIconByTag(option.getTAG(), getContext()));
            txvOptionName.setText(option.getName());
        }
        return convertView;
    }
}
