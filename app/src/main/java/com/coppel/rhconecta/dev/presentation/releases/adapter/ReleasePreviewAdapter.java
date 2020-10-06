package com.coppel.rhconecta.dev.presentation.releases.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;

import java.util.List;

/**
 *
 */
public class ReleasePreviewAdapter extends RecyclerView.Adapter<ReleasePreviewAdapter.ViewHolder> {

    /* */
    private List<ReleasePreview> values;
    private OnReleasePreviewClickListener onReleasePreviewClickListener;


    /**
     *
     */
    public ReleasePreviewAdapter(
            List<ReleasePreview> values,
            OnReleasePreviewClickListener onReleasePreviewClickListener
    ) {
        this.values = values;
        this.onReleasePreviewClickListener = onReleasePreviewClickListener;
    }

    /**
     *
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.fila_lista_comunicados, viewGroup, false));
    }

    /**
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Definition
        ReleasePreview releasePreview = values.get(position);
        // Simple values
        viewHolder.tvTitle.setText(releasePreview.getTitle());
        viewHolder.tvDate.setText(releasePreview.getDate());
        // Was read indicator
        int drawableResource =
                releasePreview.wasRead() ? R.drawable.ic_dot_grey_400_36dp : R.drawable.ic_punto_rojo;
        viewHolder.ivWasRead.setImageResource(drawableResource);
        // On click listener
        viewHolder.cvContainer.setOnClickListener(v -> {
            onReleasePreviewClickListener.onClick(releasePreview);
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvContainer;
        ImageView ivWasRead;
        TextView tvTitle;
        TextView tvDate;

        /**
         *
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvContainer =  (CardView) itemView.findViewById(R.id.cardFila);
            ivWasRead= (ImageView) cvContainer.findViewById(R.id.iconoNuevo);
            tvTitle = (TextView) cvContainer.findViewById(R.id.labelTitulo);
            tvDate= (TextView) cvContainer.findViewById(R.id.labelFecha);
        }

    }

    /**
     *
     */
    public interface OnReleasePreviewClickListener {

        /**
         *
         */
        void onClick(ReleasePreview releasePreview);

    }

}
