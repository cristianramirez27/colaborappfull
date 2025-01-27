package com.coppel.rhconecta.dev.presentation.visionaries.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;

import java.util.List;

/** */
public class VisionaryPreviewAdapter extends RecyclerView.Adapter<VisionaryPreviewAdapter.ViewHolder> {

    /* */
    private final List<VisionaryPreview> values;
    /* */
    private final OnVisionaryPreviewClickListener onVisionaryPreviewClickListener;

    /** */
    public VisionaryPreviewAdapter(
            List<VisionaryPreview> values,
            OnVisionaryPreviewClickListener onVisionaryPreviewClickListener
    ) {
        this.values = values;
        this.onVisionaryPreviewClickListener = onVisionaryPreviewClickListener;
    }

    /** */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.fila_lista_videos, viewGroup, false));
    }

    /** */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Definition
        VisionaryPreview visionaryPreview = values.get(position);
        // Content values
        viewHolder.tvTitle.setText(visionaryPreview.getTitle());
        viewHolder.tvDate.setText(visionaryPreview.getDate());
        String numberOfViews = viewHolder.itemView.getContext()
                .getString(R.string.number_of_views, visionaryPreview.getNumberOfViews());
        viewHolder.tvNumberOfViews.setText(numberOfViews);
        Glide.with(viewHolder.itemView)
                .load(visionaryPreview.getPreviewImage())
                .into(viewHolder.ivPreview);
        // Was read indicator
        if (visionaryPreview.isAlreadyBeenSeen()) {
            int readColor = ContextCompat.getColor(
                    viewHolder.itemView.getContext(),
                    R.color.colorTextCoppelNegro
            );
            viewHolder.tvTitle.setTextColor(readColor);
            viewHolder.ivWatchedIcon.setImageResource(R.drawable.ic_punto_gris);
        } else {
            viewHolder.tvTitle.setTextColor(ContextCompat.getColor(
                    viewHolder.itemView.getContext(),
                    R.color.colorPrimaryCoppelAzul));
            viewHolder.ivWatchedIcon.setImageResource(R.drawable.ic_punto_rojo);
        }

        if (visionaryPreview.isUpdated())
            viewHolder.tvIsUpdated.setVisibility(View.VISIBLE);
        else
            viewHolder.tvIsUpdated.setVisibility(View.GONE);

        // On click listener
        viewHolder.itemView.setOnClickListener(v ->
                onVisionaryPreviewClickListener.onClick(visionaryPreview)
        );
    }

    /**
     *
     */
    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /* */
        ImageView ivPreview;
        /* */
        TextView tvTitle;
        /* */
        TextView tvDate;
        /* */
        TextView tvIsUpdated;
        /* */
        ImageView ivWatchedIcon;
        /* */
        TextView tvNumberOfViews;

        /**
         *
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPreview = itemView.findViewById(R.id.imgVideoPreview);
            tvTitle = itemView.findViewById(R.id.labelTitulo);
            tvDate = itemView.findViewById(R.id.labelFecha);
            tvIsUpdated = itemView.findViewById(R.id.labelActualizado);
            ivWatchedIcon = itemView.findViewById(R.id.ivWatchedIcon);
            tvNumberOfViews = itemView.findViewById(R.id.labelVisitas);
        }

    }

    /**
     *
     */
    public interface OnVisionaryPreviewClickListener {

        /* */
        void onClick(VisionaryPreview visionaryPreview);

    }

}

