package com.coppel.rhconecta.dev.presentation.visionaries.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;

import java.util.List;

public class VisionaryPreviewAdapter extends RecyclerView.Adapter<VisionaryPreviewAdapter.ViewHolder> {

    /* */
    private List<VisionaryPreview> values;
    private OnVisionaryPreviewClickListener onVisionaryPreviewClickListener;


    /**
     *
     *
     */
    public VisionaryPreviewAdapter(
            List<VisionaryPreview> values,
            OnVisionaryPreviewClickListener onVisionaryPreviewClickListener
    ) {
        this.values = values;
        this.onVisionaryPreviewClickListener = onVisionaryPreviewClickListener;
    }

    /**
     *
     *
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.fila_lista_videos, viewGroup, false));
    }

    /**
     *
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Definition
        VisionaryPreview visionaryPreview = values.get(position);
        // Simple values
        viewHolder.tvTitle.setText(visionaryPreview.getTitle());
        viewHolder.tvDate.setText(visionaryPreview.getDate());
        String numberOfViews = viewHolder.itemView.getContext()
                .getString(R.string.number_of_views, visionaryPreview.getNumberOfViews());
        viewHolder.tvNumberOfViews.setText(numberOfViews);
        // Images
        Glide.with(viewHolder.itemView)
                .load(visionaryPreview.getPreviewImage())
                .into(viewHolder.ivPreview);
        // On click listener
        viewHolder.itemView.setOnClickListener(v ->
                onVisionaryPreviewClickListener.onClick(visionaryPreview)
        );
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     *
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
        TextView tvNumberOfViews;

        /**
         *
         *
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPreview = (ImageView) itemView.findViewById(R.id.imgVideoPreview);
            tvTitle =  (TextView) itemView.findViewById(R.id.labelTitulo);
            tvDate =  (TextView) itemView.findViewById(R.id.labelFecha);
            tvNumberOfViews =  (TextView) itemView.findViewById(R.id.labelVisitas);
        }

    }

    /**
     *
     *
     */
    public interface OnVisionaryPreviewClickListener {

        /**
         *
         *
         *
         */
        void onClick(VisionaryPreview visionaryPreview);

    }

}

