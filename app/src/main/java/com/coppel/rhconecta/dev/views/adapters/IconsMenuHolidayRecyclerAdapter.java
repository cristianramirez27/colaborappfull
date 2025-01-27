package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.List;

public class IconsMenuHolidayRecyclerAdapter extends RecyclerView.Adapter<IconsMenuHolidayRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<HomeMenuItem> menu;
    private IconsMenuRecyclerAdapter.OnItemClick onItemClick;

    public IconsMenuHolidayRecyclerAdapter(Context context, List<HomeMenuItem> menu) {
        this.context = context;
        this.menu = menu;
    }

    @NonNull
    @Override
    public IconsMenuHolidayRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_menu_vacation, viewGroup, false);
        return new IconsMenuHolidayRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IconsMenuHolidayRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.imgvIcon.setImageDrawable(MenuUtilities.getIconByTag(menu.get(i).getTAG(), context));
        viewHolder.txvName.setText(menu.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClick != null) {
                    onItemClick.onItemClick(menu.get(viewHolder.getAdapterPosition()).getTAG());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.imgvIcon)
        ImageView imgvIcon;
        @BindView(R.id.txvName)
        TextView txvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClick {
        void onItemClick(String tag);
    }

    public void setOnItemClick(IconsMenuRecyclerAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
