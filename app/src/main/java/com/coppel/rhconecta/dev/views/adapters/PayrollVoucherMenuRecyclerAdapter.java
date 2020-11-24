package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.graphics.Point;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayrollVoucherMenuRecyclerAdapter extends RecyclerView.Adapter<PayrollVoucherMenuRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<HomeMenuItem> menu;
    private int itemSize;
    private OnItemClick onItemClick;

    public PayrollVoucherMenuRecyclerAdapter(Context context, List<HomeMenuItem> menu, int spanCount) {
        this.context = context;
        this.menu = menu;
        itemSize = getItemSize(spanCount);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payroll_voucher_menu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.ctlContainer.getLayoutParams().width = itemSize;
        viewHolder.ctlContainer.getLayoutParams().height = itemSize;
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

    private int getItemSize(int spanCount) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x / spanCount;
    }

    public interface OnItemClick {
        void onItemClick(String tag);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
