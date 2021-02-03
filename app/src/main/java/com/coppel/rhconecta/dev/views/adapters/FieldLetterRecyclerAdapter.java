package com.coppel.rhconecta.dev.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FieldLetterRecyclerAdapter extends RecyclerView.Adapter<FieldLetterRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<LetterConfigResponse.Datos> fieldsLetter;
    public FieldLetterRecyclerAdapter(Context context, List<LetterConfigResponse.Datos> fieldsLetter) {
        this.fieldsLetter = fieldsLetter;
        this.context = context;
    }

    @NonNull
    @Override
    public FieldLetterRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_customize_letter, viewGroup, false);
        return new FieldLetterRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final LetterConfigResponse.Datos currentItem = fieldsLetter.get(i);

        viewHolder.txvName.setText(currentItem.getNom_datoscartas());
        viewHolder.checkboxElement.setChecked(currentItem.isSelected() ? true : false);
        viewHolder.checkboxElement.setEnabled(currentItem.getOpc_estatus() == 1 ? true : false);

        viewHolder.checkboxElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentItem.isSelected()) {
                    currentItem.setSelected(false);
                    viewHolder.checkboxElement.setChecked(false);

                } else {
                    currentItem.setSelected(true);
                    viewHolder.checkboxElement.setChecked(true);

                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public boolean hasFielsdSelected(){

        for(LetterConfigResponse.Datos dato : fieldsLetter){
            if(dato.isSelected())
                return true;
        }

        return false;
    }

    public List<LetterConfigResponse.Datos> getFieldsLetter(){
        return fieldsLetter;
    }

    @Override
    public int getItemCount() {
        return fieldsLetter.size();
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

        @BindView(R.id.ctlContainer)
        RelativeLayout ctlContainer;
        @BindView(R.id.checkboxElement)
        CheckBox checkboxElement;
        @BindView(R.id.nameElement)
        TextView txvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
