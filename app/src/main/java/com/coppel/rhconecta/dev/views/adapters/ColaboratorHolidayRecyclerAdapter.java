package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.views.customviews.CircleImageView;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ColaboratorHolidayRecyclerAdapter extends RecyclerView.Adapter<ColaboratorHolidayRecyclerAdapter.ViewHolder> {

    private Context context;
    private boolean showMarker;

    private OnRequestSelectedClickListener onRequestSelectedClick;
    private List<ColaboratorHoliday> colaborators;

    public ColaboratorHolidayRecyclerAdapter(Context context, List<ColaboratorHoliday> colaborators) {
        this.colaborators = colaborators;
        this.context = context;
    }

    public ColaboratorHolidayRecyclerAdapter(Context context, List<ColaboratorHoliday> colaborators,boolean showMarker) {
        this.colaborators = colaborators;
        this.context = context;
        this.showMarker = showMarker;
    }

    @NonNull
    @Override
    public ColaboratorHolidayRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_colaborator_holiday, viewGroup, false);
        return new ColaboratorHolidayRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final ColaboratorHoliday currentItem = colaborators.get(i);

        viewHolder.txvName.setText(TextUtilities.capitalizeText(getContext(),currentItem.getNom_empleado()));

        if(currentItem.getFotoperfil() != null && !currentItem.getFotoperfil().isEmpty()){
            Glide.with(getApplicationContext()).load(currentItem.getFotoperfil())
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .placeholder(R.drawable.ic_account_white)
                    .into(viewHolder.imgColaborador);
        }

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onRequestSelectedClick.onRequestSelectedClick(currentItem);
            }
        });

        if (showMarker) {
            viewHolder.markerSplice.setVisibility(View.VISIBLE);
            if (currentItem.isHasSplice()) {
                viewHolder.markerSplice.setBackgroundResource(R.drawable.backgroud_circle_melon);
            } else {
                viewHolder.markerSplice.setVisibility(View.GONE);
            }
        }
        if (currentItem.getNom_estatus() != null && !currentItem.getNom_estatus().isEmpty()) {
            viewHolder.status.setVisibility(View.VISIBLE);
            viewHolder.status.setText(currentItem.getNom_estatus());
            viewHolder.status.setTextColor(Color.parseColor(currentItem.getColorletra()));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor(currentItem.getColor()));
            gd.setCornerRadius(80);
            viewHolder.status.setBackgroundDrawable(gd);

        }else {
            viewHolder.status.setVisibility(View.GONE);
        }
    }


    public List<ColaboratorHoliday> getColaborators(){
        return colaborators;
    }

    @Override
    public int getItemCount() {
        return colaborators.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.imgColaborador)
        CircleImageView imgColaborador;
        @BindView(R.id.nameElement)
        TextView txvName;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.markerSplice)
        View markerSplice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public interface OnRequestSelectedClickListener {
        void onRequestSelectedClick(ColaboratorHoliday colaboratorHoliday);
    }

    public void setOnRequestSelectedClickListener(OnRequestSelectedClickListener onRequestSelectedClick) {
        this.onRequestSelectedClick = onRequestSelectedClick;
    }

}
