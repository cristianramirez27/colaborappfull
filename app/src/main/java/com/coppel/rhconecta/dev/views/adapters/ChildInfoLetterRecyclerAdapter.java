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
import com.coppel.rhconecta.dev.business.models.LetterChildrenData;
import com.coppel.rhconecta.dev.business.models.LetterChildrenDataVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildInfoLetterRecyclerAdapter extends RecyclerView.Adapter<ChildInfoLetterRecyclerAdapter.ViewHolder> {

    private Context context;
    private com.coppel.rhconecta.dev.business.interfaces.IChangeVisibilityElements IChangeVisibilityElements;

    private List<LetterChildrenDataVO> letterChildrenData;
    public ChildInfoLetterRecyclerAdapter(Context context, List<LetterChildrenDataVO> letterChildrenData, com.coppel.rhconecta.dev.business.interfaces.IChangeVisibilityElements IChangeVisibilityElements) {
        this.letterChildrenData = letterChildrenData;
        this.IChangeVisibilityElements = IChangeVisibilityElements;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildInfoLetterRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_datachild_letter, viewGroup, false);
        return new ChildInfoLetterRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final LetterChildrenDataVO currentItem = letterChildrenData.get(i);

        viewHolder.txvName.setText(currentItem.getData().getNombre_completo());
        viewHolder.checkboxElement.setChecked(currentItem.isSelected() ? true : false);

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

                IChangeVisibilityElements.changeVisibiliyElement(hasFielsdSelected() ? View.VISIBLE : View.INVISIBLE);

            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public boolean hasFielsdSelected(){

        for(LetterChildrenDataVO dataVO: letterChildrenData){
            if(dataVO.isSelected())
                return true;
        }

        return false;
    }

    public List<LetterChildrenDataVO> getFieldsLetter(){
        return letterChildrenData;
    }

    public List<LetterChildrenData> getChildSelected(){

        List<LetterChildrenData> childrenData = new ArrayList<>();
        for(LetterChildrenDataVO dataVO: letterChildrenData){
            if(dataVO.isSelected())
                childrenData.add(new LetterChildrenData(dataVO.getData().getNombre_completo()));
        }


        return childrenData;
    }

    @Override
    public int getItemCount() {
        return letterChildrenData.size();
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
