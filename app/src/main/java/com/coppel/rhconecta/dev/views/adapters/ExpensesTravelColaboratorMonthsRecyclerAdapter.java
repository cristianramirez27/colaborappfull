package com.coppel.rhconecta.dev.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.views.customviews.ExpandableSimpleTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class ExpensesTravelColaboratorMonthsRecyclerAdapter extends RecyclerView.Adapter<ExpensesTravelColaboratorMonthsRecyclerAdapter.ViewHolder> {

    private List<ColaboratorRequestsListExpensesResponse.Months> dataItems;
    private OnMonthClickListener OnMonthClickListener;
    private ExpensesTravelMonthsRequestRecyclerAdapter.OnControlMonthClickListener OnControlMonthClickListener;
   // private OnGasVoucherClickListener onGasVoucherClickListener;

    public ExpensesTravelColaboratorMonthsRecyclerAdapter(List<ColaboratorRequestsListExpensesResponse.Months> requestComplementsColaboratorList) {
        this.dataItems = requestComplementsColaboratorList;
    }

    @NonNull
    @Override
    public ExpensesTravelColaboratorMonthsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_month, viewGroup, false);
        return new ExpensesTravelColaboratorMonthsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.itemView.setHasTransientState(true);
        viewHolder.expMonth.setText(dataItems.get(i).getDes_mes());
        viewHolder.expMonth.setText(dataItems.get(i).getDes_mes());
        viewHolder.expMonth.setOnExpadableListener(new ExpandableSimpleTitle.OnExpadableListener() {
            @Override
            public void onClick() {
                    OnMonthClickListener.onMonthClick(dataItems.get(i));

            }
        });

        if(dataItems.get(i).isExpand()){
            viewHolder.layoutMonthsRequest.setVisibility(View.VISIBLE);
            ExpensesTravelMonthsRequestRecyclerAdapter  monthsRequestList = new ExpensesTravelMonthsRequestRecyclerAdapter(dataItems.get(i).getMonthRequest());
            monthsRequestList.setOnControlMonthClickListener(OnControlMonthClickListener);
            viewHolder.rcvControlsPayments.setAdapter(monthsRequestList);
        }else {
            viewHolder.layoutMonthsRequest.setVisibility(View.GONE);
        }
    }


    public ColaboratorRequestsListExpensesResponse.Months getMonthSelected(int clv_month){
        for(ColaboratorRequestsListExpensesResponse.Months month : dataItems){
            if(month.getClv_mes() == clv_month)
                return month;
        }

        return null;
    }



    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public List<ColaboratorRequestsListExpensesResponse.Months> getDataItems() {
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

        @BindView(R.id.expMonth)
        ExpandableSimpleTitle expMonth;
        @BindView(R.id.layoutMonthsRequest)
        LinearLayout layoutMonthsRequest;
        @BindView(R.id.rcvControlsPayments)
        RecyclerView rcvControlsPayments;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            expMonth.setBlackArrow(true);
            expMonth.setMarginLeft(false);

            rcvControlsPayments.setHasFixedSize(true);
            rcvControlsPayments.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    public interface OnMonthClickListener {
        void onMonthClick(ColaboratorRequestsListExpensesResponse.Months month);
    }

    public void setOnMonthClickListener(ExpensesTravelColaboratorMonthsRecyclerAdapter.OnMonthClickListener onMonthClickListener) {
        OnMonthClickListener = onMonthClickListener;
    }

    public void setOnControlMonthClickListener(ExpensesTravelMonthsRequestRecyclerAdapter.OnControlMonthClickListener onControlMonthClickListener) {
        OnControlMonthClickListener = onControlMonthClickListener;
    }
}
