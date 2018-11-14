package com.coppel.rhconecta.dev.views.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.coppel.rhconecta.dev.views.adapters.HomeMenuRecyclerViewAdapter;

import java.util.Collections;

public class HomeMenuItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private HomeMenuRecyclerViewAdapter adapter;

    public HomeMenuItemTouchHelperCallback(HomeMenuRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        Collections.swap(adapter.getCustomMenu(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
        adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
    }
}
