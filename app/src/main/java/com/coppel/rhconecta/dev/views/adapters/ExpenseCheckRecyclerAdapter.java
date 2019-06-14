package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ExpenseAuthorizedDetail;
import com.coppel.rhconecta.dev.views.customviews.HeaderTitlesList;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class ExpenseCheckRecyclerAdapter extends RecyclerView.Adapter<ExpenseCheckRecyclerAdapter.ViewHolder> {

    private List<ExpenseAuthorizedDetail> dataItems;

    public ExpenseCheckRecyclerAdapter(List<ExpenseAuthorizedDetail> itineraryList) {
        this.dataItems = itineraryList;
    }

    @NonNull
    @Override
    public ExpenseCheckRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gasto_comprobar, viewGroup, false);
        return new ExpenseCheckRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.gastoComprobar.setTitle1(TextUtilities.capitalizeText(getContext(),dataItems.get(i).getDes_tipoGasto()));
        viewHolder.gastoComprobar.setTitle2(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(dataItems.get(i).getImp_totalAutorizado()))) );
        viewHolder.gastoComprobar.setTitle3(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(dataItems.get(i).getImp_totalComprobado()))));
        viewHolder.gastoComprobar.setTitle4(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(dataItems.get(i).getImp_totalFaltante()))));

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<ExpenseAuthorizedDetail> getDataItems() {
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

        @BindView(R.id.gastoComprobar)
        HeaderTitlesList gastoComprobar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            gastoComprobar.setColorTitle2(R.color.colorBackgroundCoppelNegro);
            gastoComprobar.setColorTitle3(R.color.colorBackgroundCoppelNegro);
            gastoComprobar.setColorTitle4(R.color.colorBackgroundCoppelNegro);
            gastoComprobar.setVisibilityDivider(View.VISIBLE);

            gastoComprobar.setGravityTitle2(Gravity.LEFT);
            gastoComprobar.setGravityTitle3(Gravity.LEFT);
            gastoComprobar.setGravityTitle4(Gravity.LEFT);
            gastoComprobar.setPaddingTitle4();
        }
    }


}
