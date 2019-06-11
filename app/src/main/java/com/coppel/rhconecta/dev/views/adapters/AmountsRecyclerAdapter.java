package com.coppel.rhconecta.dev.views.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AmountsRecyclerAdapter extends RecyclerView.Adapter<AmountsRecyclerAdapter.ViewHolder> {

    private List<DetailRequest> dataItems;

    public AmountsRecyclerAdapter(List<DetailRequest> itineraryList) {
        this.dataItems = itineraryList;
    }

    @NonNull
    @Override
    public AmountsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_importe, viewGroup, false);
        return new AmountsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.itemView.setHasTransientState(true);
    //dataItems.get(i).getControl()
        viewHolder.detailAmount.setTexts(dataItems.get(i).getDes_tipoGasto(),
                TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(String.valueOf(dataItems.get(i).getImp_total()))));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<DetailRequest> getDataItems() {
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

        @BindView(R.id.detailAmount)
        TextViewDetail detailAmount;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            Typeface font = ResourcesCompat.getFont(CoppelApp.getContext(), R.font.lineto_circular_pro_book);
            detailAmount.setStartFont(font);
            detailAmount.setEndFont(font);

            detailAmount.setStartTextSize(13);
            detailAmount.setEndTextSize(13);
            detailAmount.setStartTextColor(CoppelApp.getContext().getResources().getColor(R.color.colorSavingHeaderText));
            detailAmount.setEndTextSize(CoppelApp.getContext().getResources().getColor(R.color.colorBackgroundCoppelNegro));

            /*detailAmount.setColorTitle2(R.color.colorBackgroundCoppelNegro);
            detailAmount.setColorTitle3(R.color.colorBackgroundCoppelNegro);
            detailAmount.setColorTitle4(R.color.colorBackgroundCoppelNegro);
            detailAmount.setVisibilityDivider(View.VISIBLE);*/
        }
    }


}
