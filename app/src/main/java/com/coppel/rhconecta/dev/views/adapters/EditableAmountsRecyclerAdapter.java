package com.coppel.rhconecta.dev.views.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ITotalAmounts;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoneyDecimal;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.CoppelApp.getContext;

public class EditableAmountsRecyclerAdapter extends RecyclerView.Adapter<EditableAmountsRecyclerAdapter.ViewHolder> {

    private List<DetailRequest> dataItems;
    private List<DetailRequest> dataItemsColaborator;
    private Activity activity;
    private ITotalAmounts ITotalAmounts;

    public EditableAmountsRecyclerAdapter(Activity activity, List<DetailRequest> itineraryList, List<DetailRequest> dataItemsColaborator,ITotalAmounts ITotalAmounts) {
        this.dataItems = itineraryList;
        this.activity = activity;
        this.ITotalAmounts = ITotalAmounts;
        this.dataItemsColaborator = dataItemsColaborator;
    }

    @NonNull
    @Override
    public EditableAmountsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_amount_edit, viewGroup, false);
        return new EditableAmountsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.itemView.setHasTransientState(true);
    //dataItems.get(i).getControl()
        viewHolder.name.setText(TextUtilities.capitalizeText(getContext(),dataItems.get(i).getDes_tipoGasto()));
        String amountGte = dataItems.get(i).getImp_total().replace(",","");

        String amountColaborator = dataItemsColaborator.get(i).getImp_total().replace(",","");

       /* viewHolder.amount.setHint(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(amount)));*/

        viewHolder.amount.setText(TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble(amountColaborator)));

        viewHolder.edtNewAmount.getEdtQuantity().setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(amountGte)));
        //viewHolder.edtNewAmount.getEdtQuantity().setHint("$0.00");

        setFocusChangeListener(dataItems.get(i), viewHolder.edtNewAmount);

        //.edtNewAmount.getEdtQuantity().setText(String.valueOf(dataItems.get(i).getImp_total()));


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

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.edtNewAmount)
        EditTextMoneyDecimal edtNewAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            edtNewAmount.setVisibilityTitle(View.GONE);
            //edtNewAmount.setPaddingEditText(4,8,4,8);
            edtNewAmount.setSizeQuantity(13);
            edtNewAmount.setPaddingRelative(4,8,4,8);


            /*edtNewAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if(hasFocus){
                        edtNewAmount.getEdtQuantity().setSelection(edtNewAmount.getEdtQuantity().getText().length());
                    }

                }
            });*/

        }
    }


    private void setFocusChangeListener(DetailRequest detailRequest,EditTextMoneyDecimal editTextMoney){

       // editTextMoney.setTextWatcherMoney();

        editTextMoney.getEdtQuantity().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editTextMoney.getQuantity().length() >0){
                    String value =  editTextMoney.getEdtQuantity().getText().toString();
                    value = value.replace("$","");
                    value = value.replace(",","");
                    //value = value.replace(".","");
                    value = value.trim();
                     if(value.equals("."))
                        value = "0.00";

                    detailRequest.setNewAmount(Double.parseDouble(value));
                    ITotalAmounts.setValueGte(detailRequest.getIdu_tipoGasto(),detailRequest.getNewAmount());
                }else{
                    detailRequest.setNewAmount(0.0);
                    ITotalAmounts.setValueGte(detailRequest.getIdu_tipoGasto(),detailRequest.getNewAmount());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextMoney.getEdtQuantity().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    String value = editTextMoney.getEdtQuantity().getText().toString();
                    //if(editTextMoney.getEdtQuantity().getText().length() > 0)
                      //  editTextMoney.getEdtQuantity().setSelection(editTextMoney.getEdtQuantity().getText().length());

                    editTextMoney.setTextWatcherMoney();
                    DeviceManager.showKeyBoard(activity);
                }else {
                    String value = editTextMoney.getEdtQuantity().getText().toString();
                    if(value.isEmpty()){
                        editTextMoney.removeTextWatcher();
                        editTextMoney.getEdtQuantity().setText("$0.00");
                        return;
                    }

                    if(value.contains(".")){
                        if(value.endsWith(".")){
                            editTextMoney.getEdtQuantity().setText(String.format("%s%s",value , "00"));
                        }
                        else if(value.length() -2 > 0 && value.charAt(value.length() -2 ) == '.'){
                            editTextMoney.getEdtQuantity().setText(String.format("%s%s",value , "0"));
                        }
                    }else {
                        editTextMoney.getEdtQuantity().setText(String.format("%s%s",value , ".00"));
                    }

                }
            }
        });
    }
}
