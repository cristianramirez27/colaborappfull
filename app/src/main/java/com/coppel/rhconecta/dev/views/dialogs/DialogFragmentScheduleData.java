package com.coppel.rhconecta.dev.views.dialogs;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterSchedulesDataVO;
import com.coppel.rhconecta.dev.views.adapters.DateTimeLettersRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentScheduleData extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentScheduleData.class.getSimpleName();
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_LAYOUT = "KEY_LAYOUT";
    private OnButonOptionClick onButtonClickListener;
    private Context context;
    private String email;
    @BindView(R.id.rcvFields)
    RecyclerView rcvFields;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;
    private List<LetterConfigResponse.Horario> scheduleData;
    private DateTimeLettersRecyclerAdapter dateTimeLettersRecyclerAdapter;
    private String contentText;
    private int iResLayout;

    private SCHEDULETYPE scheduletypeSelectd;


    public static DialogFragmentScheduleData newInstance(LetterSchedulesDataVO schedules, int iResLayout){
        DialogFragmentScheduleData fragmentScheduleData = new DialogFragmentScheduleData();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATA,schedules);
        args.putInt(KEY_LAYOUT,iResLayout);
        fragmentScheduleData.setArguments(args);
        return fragmentScheduleData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
        iResLayout = getArguments().getInt(KEY_LAYOUT);
        LetterSchedulesDataVO letterSchedulesDataVO = (LetterSchedulesDataVO) getArguments().getSerializable(KEY_DATA);
        scheduleData =  (List<LetterConfigResponse.Horario>)letterSchedulesDataVO.getData();


    }

    public void setScheduletypeSelectd(SCHEDULETYPE scheduletypeSelectd) {
        this.scheduletypeSelectd = scheduletypeSelectd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(iResLayout, container, false);
        ButterKnife.bind(this, view);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        initViews();
        return view;
    }

    private void initViews() {
        rcvFields.setHasFixedSize(true);
        rcvFields.setLayoutManager(new LinearLayoutManager(getContext()));
        dateTimeLettersRecyclerAdapter = new DateTimeLettersRecyclerAdapter(getContext(), scheduleData);
        rcvFields.setAdapter(dateTimeLettersRecyclerAdapter);
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (onButtonClickListener != null) {
            switch (view.getId()) {
                case R.id.btnActionLeft:
                    onButtonClickListener.onLeftOptionClick();
                    break;
                case R.id.btnActionRight:
                    onButtonClickListener.onRightOptionClick(scheduletypeSelectd,getData());
                    break;
                case R.id.imgvClose:
                    close();
                    break;
            }
        }
    }

    private String getData(){
       return dateTimeLettersRecyclerAdapter.getSelectedSchedule();
    }

    public interface OnButonOptionClick {

        void onLeftOptionClick();

        void onRightOptionClick(SCHEDULETYPE scheduletype, String data);
    }

    public void setOnButtonClickListener(OnButonOptionClick onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public enum SCHEDULETYPE{
        START_DAY,
        END_DAY,
        START_HOUR_WORK,
        END_HOUR_WORK,
        TIME_LUNCH
    }
}
