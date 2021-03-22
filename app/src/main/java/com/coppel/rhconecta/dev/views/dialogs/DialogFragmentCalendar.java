package com.coppel.rhconecta.dev.views.dialogs;

import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DateCalendar;
import com.coppel.rhconecta.dev.business.models.DatePrima;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.views.adapters.DateCalendarRecyclerAdapter;

import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_OK;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class DialogFragmentCalendar extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentCalendar.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.textView2)
    TextView month;
    @BindView(R.id.imageView5)
    ImageView previousMonth;
    @BindView(R.id.imageView4)
    ImageView nextMonth;
    @BindView(R.id.button2)
    Button buttonAccept;
    @BindView(R.id.button)
    Button buttonCancel;
    private GridLayoutManager gridLayoutManager;

    private Command acceptAction;
    private DateCalendarRecyclerAdapter dateCalendarRecyclerAdapter = new DateCalendarRecyclerAdapter(new ArrayList<>());
    private List<DatePrima> dateHolidayBonus;
    private int index = -1;
    private String selectDate;
    private StateListDrawable stateList;
    private int length;
    private DialogFragmentGetDocument dialogFragmentGetDocument;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Dialog_Alert);

        stateList = new StateListDrawable();
        stateList.addState(new int[]{-android.R.attr.state_enabled}, getActivity().getDrawable(R.drawable.background_disable_rounded));
        stateList.addState(new int[]{android.R.attr.state_enabled}, getActivity().getDrawable(R.drawable.background_blue_rounded));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_calendar, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        initView();
        return view;
    }

    private void initView() {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setContentText("");
        dialogFragmentGetDocument.setMsgText("");
        dialogFragmentGetDocument.setType(MSG_HOLIDAYS_OK, getActivity());
        dialogFragmentGetDocument.setOnButtonClickListener(new DialogFragmentGetDocument.OnButtonClickListener(){
            @Override
            public void onSend(String email) { }

            @Override
            public void onAccept() {
                dialogFragmentGetDocument.dismiss();
            }
        });
        previousMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        buttonAccept.setOnClickListener(this);
        buttonAccept.setBackground(stateList);
        buttonAccept.setEnabled(false);
        buttonCancel.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dateCalendarRecyclerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        if(!dateHolidayBonus.isEmpty()){
            length = dateHolidayBonus.size()-1;
            index++;
            DatePrima datePrima = dateHolidayBonus.get(index);
            List<DateCalendar> dates = datePrima.getDates();
            month.setText(datePrima.getMonth());
            gridLayoutManager.setSpanCount(dates.size());
            dateCalendarRecyclerAdapter.setList(dates);
            showDialogDescriptionPeriod(datePrima);
            previousMonth.setAlpha(0.6f);
        }

        dateCalendarRecyclerAdapter.setListener(params -> {
           buttonAccept.setEnabled(true);
           Arrays.stream(params)
                   .findFirst()
                   .ifPresent(o -> selectDate = o.toString());
        });

    }

    public void setAcceptAction(Command command) {
        acceptAction = command;
    }

    public void setDateHolidayBonus(List<DatePrima> dateHolidayBonus) {
        index = -1;
        this.dateHolidayBonus = dateHolidayBonus;
    }

    private void showDialogDescriptionPeriod(DatePrima datePrima) {
        if (datePrima.getDescriptionPeriod() !=null){
            dialogFragmentGetDocument.setContentText(datePrima.getDescriptionPeriod());
            dialogFragmentGetDocument.show(getActivity().getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                dismiss();
                break;
            case R.id.button2:
                //click
                if (acceptAction != null) {
                    acceptAction.execute(selectDate);
                }
                dismiss();
                break;
            case R.id.imageView5:
                //previous item
                if ( index > 0) {
                    DatePrima next = dateHolidayBonus.get(--index);
                    month.setText(next.getMonth());
                    List<DateCalendar> dates = next.getDates();
                    gridLayoutManager.setSpanCount(dates.size());
                    dateCalendarRecyclerAdapter.setList(dates);
                    buttonAccept.setEnabled(false);
                    showDialogDescriptionPeriod(next);
                    previousMonth.setAlpha(index == 0 ? 0.6f : 1.0f);
                    nextMonth.setAlpha(index == length ? 0.6f : 1.0f );
                }
                break;
            case R.id.imageView4:
                //next item
                if ( index < length) {
                    DatePrima next = dateHolidayBonus.get(++index);
                    month.setText(next.getMonth());
                    List<DateCalendar> dates = next.getDates();
                    gridLayoutManager.setSpanCount(dates.size());
                    dateCalendarRecyclerAdapter.setList(dates);
                    buttonAccept.setEnabled(false);
                    showDialogDescriptionPeriod(next);
                    nextMonth.setAlpha(index == length ? 0.6f : 1.0f );
                    previousMonth.setAlpha(index == 0 ? 0.6f : 1.0f);
                }
                break;
            default:

        }

    }
}
