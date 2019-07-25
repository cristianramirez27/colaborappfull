package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.views.customviews.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColaboratorHolidayRecyclerAdapter extends RecyclerView.Adapter<ColaboratorHolidayRecyclerAdapter.ViewHolder> {

    private Context context;

    private OnRequestSelectedClickListener onRequestSelectedClick;
    private List<ColaboratorHoliday> colaborators;
    public ColaboratorHolidayRecyclerAdapter(Context context, List<ColaboratorHoliday> colaborators) {
        this.colaborators = colaborators;
        this.context = context;
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

        viewHolder.txvName.setText(currentItem.getName());

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
