package com.coppel.rhconecta.dev.views.adapters;

import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DateCalendar;
import com.coppel.rhconecta.dev.business.utils.Command;

import java.util.List;

public class DateCalendarRecyclerAdapter extends RecyclerView.Adapter<DateCalendarRecyclerAdapter.DateCalendarViewHolder> {
    private final List<DateCalendar> list;
    private Command listener;
    static int itemSelected = -1;

    public void setListener(Command listener) {
        this.listener = listener;
    }

    public DateCalendarRecyclerAdapter(List<DateCalendar> list){
        this.list = list;
    }


    public void setList(List<DateCalendar> list) {
        this.list.clear();
        this.list.addAll(list);
        itemSelected = -1;
        notifyDataSetChanged();
    }

    @Override
    public DateCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date_calendar, parent, false);
        return new DateCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateCalendarRecyclerAdapter.DateCalendarViewHolder holder, int position) {
        holder.onBind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DateCalendarViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number_date)
        TextView numberDate;
        @BindView(R.id.description_date)
        TextView descriptionDate;
        private DateCalendar dateCalendar;
        private int numberItem = -1;

        public DateCalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v ->{
                    itemSelected = numberItem;
                    listener.execute(dateCalendar.getDate());
                    notifyDataSetChanged();
            });
            StateListDrawable stateList = new StateListDrawable();
            stateList.addState(new int[]{-android.R.attr.state_activated}, itemView.getContext().getDrawable(R.drawable.backgroud_circle_blue));
            stateList.addState(new int[]{android.R.attr.state_activated}, itemView.getContext().getDrawable(R.drawable.backgroud_circle_green));
            numberDate.setBackground(stateList);
        }

        public void onBind(DateCalendar dateCalendar, int position) {
            numberItem = position;
            this.dateCalendar = dateCalendar;
            numberDate.setText(dateCalendar.getNumberDate());
            descriptionDate.setText(dateCalendar.getDescriptionDate());
            boolean isSelected = itemSelected == numberItem;
            numberDate.setActivated(isSelected);
            descriptionDate.setTypeface(isSelected ? ResourcesCompat.getFont(descriptionDate.getContext(), R.font.lineto_circular_pro_medium) : ResourcesCompat.getFont(descriptionDate.getContext(), R.font.lineto_circular_pro_book));
        }
    }
}
