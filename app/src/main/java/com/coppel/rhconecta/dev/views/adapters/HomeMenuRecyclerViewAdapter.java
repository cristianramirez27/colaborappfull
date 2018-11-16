package com.coppel.rhconecta.dev.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class HomeMenuRecyclerViewAdapter extends RecyclerView.Adapter<HomeMenuRecyclerViewAdapter.ViewHolder> implements View.OnTouchListener,
        View.OnLongClickListener {

    private Context context;
    private List<HomeMenuItem> customMenu;
    private int itemSize;
    private Animation shake;
    private OnItemClick onItemClick;

    public HomeMenuRecyclerViewAdapter(Context context, List<HomeMenuItem> customMenu, int spanCount) {
        this.context = context;
        this.customMenu = customMenu;
        itemSize = getItemSize(spanCount);
        shake = AnimationUtils.loadAnimation(context, R.anim.anim_shake);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_home_menu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.ctlContainer.getLayoutParams().width = itemSize;
        viewHolder.ctlContainer.getLayoutParams().height = itemSize;
        viewHolder.imgvIcon.setImageDrawable(MenuUtilities.getIconByTag(customMenu.get(i).getTAG(), context));
        viewHolder.txvName.setText(customMenu.get(i).getName());
        if(customMenu.get(i).getNotifications() > 0){
            viewHolder.txvNotifications.setText(customMenu.get(i).getNotifications()+"");
            viewHolder.txvNotifications.setVisibility(View.VISIBLE);
        }else{
            viewHolder.txvNotifications.setText("");
            viewHolder.txvNotifications.setVisibility(View.INVISIBLE);
        }
        viewHolder.itemView.setOnLongClickListener(this);
        viewHolder.itemView.setOnTouchListener(this);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClick != null) {
                    onItemClick.onItemClick(customMenu.get(viewHolder.getAdapterPosition()).getTAG());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return customMenu.size();
    }

    public List<HomeMenuItem> getCustomMenu() {
        return customMenu;
    }

    public int getItemSize() {
        return itemSize;
    }

    @Override
    public boolean onLongClick(View view) {
        view.startAnimation(shake);
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_UP
                || motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
            if(view.getAnimation() != null) {
                view.clearAnimation();
            }
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctlContainer)
        ConstraintLayout ctlContainer;
        @BindView(R.id.imgvIcon)
        ImageView imgvIcon;
        @BindView(R.id.txvName)
        TextView txvName;
        @BindView(R.id.txvNotifications)
        TextView txvNotifications;

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

    public  void setNotification(String TAG,int notifications){
        Realm realm = Realm.getDefaultInstance();
         List<HomeMenuItem> customMenuTmp= this.getCustomMenu();
        if (!realm.isInTransaction()) realm.beginTransaction();
        for (int i =0;i<customMenuTmp.size();i++){
            try{
                if(customMenuTmp.get(i).getTAG().equals(TAG)){ // agrega notificaciones a comunicados
                    Log.d("AdapterMenu"," notifications menu "+TAG+": "+notifications);

                    customMenuTmp.get(i).setNotifications(notifications);
                }

            }catch (Exception e){
                Log.d("AdapterMenu","Error add notifications menu adapter: "+e.getMessage());
            }
        }

        realm.commitTransaction();
        this.customMenu=customMenuTmp;
        this.notifyDataSetChanged();

    }

}
