package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ExpenseAuthorizedDetail;
import com.coppel.rhconecta.dev.views.customviews.HeaderTitlesExpensesList;
import com.coppel.rhconecta.dev.views.customviews.HeaderTitlesList;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class ExpenseCheckRecyclerAdapter extends RecyclerView.Adapter<ExpenseCheckRecyclerAdapter.ViewHolder> {

    private List<ExpenseAuthorizedDetail> dataItems;
    private boolean checkedIsNull;
    private boolean missingIsNull;


    public ExpenseCheckRecyclerAdapter(List<ExpenseAuthorizedDetail> itineraryList,boolean checkedIsNull, boolean missingIsNull) {
        this.dataItems = itineraryList;
        this.checkedIsNull = checkedIsNull;
        this.missingIsNull = missingIsNull;
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


        viewHolder.gastoComprobar.setTitle3(checkedIsNull ? "-" : TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(dataItems.get(i).getImp_totalComprobado()))));

        if(checkedIsNull){
            viewHolder.gastoComprobar.setGravityTitle3(Gravity.CENTER);
        }

        viewHolder.gastoComprobar.setTitle4(missingIsNull ? "-" : TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(dataItems.get(i).getImp_totalFaltante()))));

        if(missingIsNull){
            viewHolder.gastoComprobar.setGravityTitle4(Gravity.CENTER);
        }

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
        HeaderTitlesExpensesList gastoComprobar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            gastoComprobar.setColorTitle2(R.color.colorBackgroundCoppelNegro);
            gastoComprobar.setColorTitle3(R.color.colorBackgroundCoppelNegro);
            gastoComprobar.setColorTitle4(R.color.colorBackgroundCoppelNegro);
            gastoComprobar.setVisibilityDivider(View.VISIBLE);

            gastoComprobar.setGravityTitle4(Gravity.LEFT);
            gastoComprobar.setGravityTitle2(Gravity.CENTER);
            gastoComprobar.setGravityTitle3(Gravity.CENTER);
            gastoComprobar.setGravityTitle4(Gravity.CENTER);

            gastoComprobar.setSizeTitle1(10);
            gastoComprobar.setSizeTitle2(10);
            gastoComprobar.setSizeTitle3(10);
            gastoComprobar.setSizeTitle4(10);

            gastoComprobar.setWeightTitle(1,1f);
            gastoComprobar.setWeightTitle(2,1f);
            gastoComprobar.setWeightTitle(3,1f);
            gastoComprobar.setWeightTitle(4,1f);
        }
    }


}
