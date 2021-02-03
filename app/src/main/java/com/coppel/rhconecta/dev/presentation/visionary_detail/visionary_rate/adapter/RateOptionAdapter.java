package com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.databinding.ViewHolderRateOptionBinding;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryRate;

import java.util.List;

/* */
public class RateOptionAdapter extends RecyclerView.Adapter<RateOptionAdapter.OptionViewHolder> {

    /* */
    private final List<VisionaryRate.Option> options;

    /* */
    private final OnOptionClickListener onOptionClickListener;

    /**
     *
     */
    public RateOptionAdapter(
            List<VisionaryRate.Option> options,
            OnOptionClickListener onOptionClickListener
    ) {
        this.options = options;
        this.onOptionClickListener = onOptionClickListener;
    }

    /**
     *
     */
    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderRateOptionBinding binding = ViewHolderRateOptionBinding
                .inflate(LayoutInflater.from(parent.getContext()));
        return new OptionViewHolder(binding);
    }

    /**
     *
     */
    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        VisionaryRate.Option option = options.get(position);
        holder.binding.getRoot()
                .setOnClickListener(v -> onOptionClickListener.onOptionClick(option));
        holder.binding.tvContent.setText(option.getContent());
        if (option.isSelected)
            setupHolderAsSelected(holder);
        else
            setupHolderAsUnelected(holder);
    }

    /**
     *
     */
    private void setupHolderAsSelected(OptionViewHolder holder) {
        int selectedColor = ContextCompat.getColor(
                holder.itemView.getContext(),
                android.R.color.black
        );
        holder.binding.tvContent.setTextColor(selectedColor);
        holder.binding.ivCheck.setVisibility(View.VISIBLE);
    }

    /**
     *
     */
    private void setupHolderAsUnelected(OptionViewHolder holder) {
        int selectedColor = ContextCompat.getColor(
                holder.itemView.getContext(),
                android.R.color.black
        );
        holder.binding.tvContent.setTextColor(selectedColor);
        holder.binding.ivCheck.setVisibility(View.GONE);
    }

    /**
     *
     */
    @Override
    public int getItemCount() {
        return options.size();
    }

    /**
     *
     */
    public static class OptionViewHolder extends RecyclerView.ViewHolder {

        /* */
        public ViewHolderRateOptionBinding binding;

        /**
         *
         */
        public OptionViewHolder(ViewHolderRateOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
