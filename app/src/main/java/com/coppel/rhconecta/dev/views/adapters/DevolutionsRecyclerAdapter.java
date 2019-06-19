package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class DevolutionsRecyclerAdapter extends RecyclerView.Adapter<DevolutionsRecyclerAdapter.ViewHolder> {

    private List<Devolution> dataItems;

    public DevolutionsRecyclerAdapter(List<Devolution> itineraryList) {
        this.dataItems = itineraryList;
    }

    @NonNull
    @Override
    public DevolutionsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_devolucion, viewGroup, false);
        return new DevolutionsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.detail.setTexts(String.valueOf(dataItems.get(i).getFec_deposito()),
                String.format("$%s", String.valueOf(dataItems.get(i).getTotal())));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<Devolution> getDataItems() {
        return dataItems;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail)
        TextViewDetail detail;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            detail.setStartTextColor(getContext().getResources().getColor(R.color.colorSavingHeaderText));
            detail.setEndTextColor(getContext().getResources().getColor(R.color.colorBackgroundCoppelNegro));

            detail.setStartFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
            detail.setEndFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));

            detail.setStartTextSize(13);
            detail.setEndTextSize(13);

        }
    }


}
