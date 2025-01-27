package com.shrikanthravi.collapsiblecalendarview.widget;

/**
 * Created by shrikanthravi on 07/03/18.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.shrikanthravi.collapsiblecalendarview.R;
import com.shrikanthravi.collapsiblecalendarview.data.CalendarAdapter;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.data.Event;
import com.shrikanthravi.collapsiblecalendarview.view.ExpandIconView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CollapsibleCalendar extends UICalendar {

    private CalendarAdapter mAdapter;
    private CalendarListener mListener;

    private boolean expanded=false;

    private int mInitHeight = 0;

    private Handler mHandler = new Handler();
    private boolean mIsWaitingForUpdate = false;

    private int mCurrentWeekIndex;
    /**Bandera para dias multiples**/
    private boolean isMultipleDays;
    private boolean isEnable;
    private boolean nameDayGray;

    private boolean isSpliceActionEnable;

    private CommandSplice actionSplice;
    private boolean showCompleteDaysName;


    public CollapsibleCalendar(Context context) {
        super(context);
    }

    public CollapsibleCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsibleCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
            Calendar cal = Calendar.getInstance();
            CalendarAdapter adapter = new CalendarAdapter(context, cal);
            setAdapter(adapter);

        // bind events

        mBtnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevMonth();
            }
        });

        mBtnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonth();
            }
        });

        mBtnPrevWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevWeek();
            }
        });

        mBtnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWeek();
            }
        });

        expandIconView.setState(ExpandIconView.MORE,true);


        expandIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expanded){
                    collapse(400);
                }
                else{
                    expand(400);
                }
            }
        });

        this.post(new Runnable() {
            @Override
            public void run() {
                collapseTo(mCurrentWeekIndex);
            }
        });



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mInitHeight = mTableBody.getMeasuredHeight();

        if (mIsWaitingForUpdate) {
            redraw();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    collapseTo(mCurrentWeekIndex);
                }
            });
            mIsWaitingForUpdate = false;
            if (mListener != null) {
                mListener.onDataUpdate();
            }
        }
    }

    @Override
    protected void redraw() {
        // redraw all views of week
        TableRow rowWeek = (TableRow) mTableHead.getChildAt(0);
       /* if (rowWeek != null) {
            for (int i = 0; i < rowWeek.getChildCount(); i++) {
                ((TextView) rowWeek.getChildAt(i)).setTextColor(getTextColor());
            }
        }*/
        // redraw all views of day
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                Day day = mAdapter.getItem(i);
                View view = mAdapter.getView(i);
                TextView txtDay = (TextView) view.findViewById(R.id.txt_day);
                txtDay.setBackgroundColor(Color.TRANSPARENT);
                txtDay.setTextSize(11);
                txtDay.setTextColor(getTextColor());

                // set today's item
                if (isToady(day)) {
                    txtDay.setBackgroundDrawable(getTodayItemBackgroundDrawable());
                    txtDay.setTextColor(getTodayItemTextColor());
                }

                // set the selected item
                if (isSelectedDay(day) || (isMultipleDays && isInSelectedDayList(day))) {
                    setBackgroundDay(day);
                    txtDay.setBackgroundDrawable(getSelectedItemBackgroundDrawable(day.getHasSplice() == 1 ? true : false));
                    txtDay.setTextColor(getSelectedItemTextColor());
                    txtDay.setPadding(5,5,5,5);
                }
            }
        }
    }

    private void formatMonthNameStyle(String titleMonth){

        try {

            String[] titleParts = titleMonth.split(" ");
            titleParts[0] = titleParts[0].substring(0, 1).toUpperCase() + titleParts[0].substring(1).toLowerCase() + " ";


           /*  final SpannableStringBuilder sb = new SpannableStringBuilder(mTxtTitle.getText().toString());

           final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
            final StyleSpan iss = new StyleSpan(Typeface.NORMAL); //Span to make text italic
            sb.setSpan(bss, 0,  titleParts[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
            sb.setSpan(iss,  titleParts[0].length(), mTxtTitle.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make last 2 characters Italic

            mTxtTitle.setText(sb);*/

            Spannable word = new SpannableString( titleParts[0]);
            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTxtTitle.setText(word);
            Spannable wordTwo = new SpannableString( titleParts[1]);
            wordTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#5f6062")), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTxtTitle.append(wordTwo);

           // mTxtTitle.setText(sbuilder);

        }catch (Exception e){
            //e.printStackTrace();
        }
    }


    @Override
    protected void reload() {
        if (mAdapter != null) {
            mAdapter.refresh();

            // reset UI
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            dateFormat.setTimeZone(mAdapter.getCalendar().getTimeZone());
            formatMonthNameStyle(dateFormat.format(mAdapter.getCalendar().getTime()));
            //mTxtTitle.setText();
            mTableHead.removeAllViews();
            mTableBody.removeAllViews();

            TableRow rowCurrent;

            // set day of week
            int[] dayOfWeekIds = {
                    R.string.sunday,
                    R.string.monday,
                    R.string.tuesday,
                    R.string.wednesday,
                    R.string.thursday,
                    R.string.friday,
                    R.string.saturday
            };

            int[] dayOfWeekComplete = {
                    R.string.sunday_complete,
                    R.string.monday_complete,
                    R.string.tuesday_complete,
                    R.string.wednesday_complete,
                    R.string.thursday_complete,
                    R.string.friday_complete,
                    R.string.saturday_complete
            };
            rowCurrent = new TableRow(mContext);
            rowCurrent.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 7; i++) {
                View view = mInflater.inflate(R.layout.layout_day_of_week, null);
                TextView txtDayOfWeek = (TextView) view.findViewById(R.id.txt_day_of_week);
                if(nameDayGray){
                    txtDayOfWeek.setTypeface(txtDayOfWeek.getTypeface(), Typeface.NORMAL);
                    txtDayOfWeek.setTextColor(Color.parseColor("#9b9b9b"));
                }else {
                    txtDayOfWeek.setTypeface(txtDayOfWeek.getTypeface(), Typeface.BOLD);
                }
                txtDayOfWeek.setText(!showCompleteDaysName ? dayOfWeekIds[(i + getFirstDayOfWeek()) % 7] : dayOfWeekComplete[(i + getFirstDayOfWeek()) % 7] );
                view.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1));
                rowCurrent.addView(view);
            }
            mTableHead.addView(rowCurrent);

            // set day view
            for (int i = 0; i < mAdapter.getCount(); i++) {
                final int position = i;

                if (position % 7 == 0) {
                    rowCurrent = new TableRow(mContext);
                    rowCurrent.setLayoutParams(new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    mTableBody.addView(rowCurrent);
                }
                final View view = mAdapter.getView(position);
                view.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClicked(v, mAdapter.getItem(position));
                    }
                });
                rowCurrent.addView(view);
            }

            redraw();
            mIsWaitingForUpdate = true;
        }
    }

    public void setNameDayGray(boolean nameDayGray) {
        this.nameDayGray = nameDayGray;
    }

    private int getSuitableRowIndex() {
        if (getSelectedItemPosition() != -1) {
            View view = mAdapter.getView(getSelectedItemPosition());
            TableRow row = (TableRow) view.getParent();

            return mTableBody.indexOfChild(row);
        } else if (getTodayItemPosition() != -1) {
            View view = mAdapter.getView(getTodayItemPosition());
            TableRow row = (TableRow) view.getParent();

            return mTableBody.indexOfChild(row);
        } else {
            return 0;
        }
    }

    public void onItemClicked(View view, Day day) {

        if(!isEnable) return;


        //Ejecutamos la accion para los empalmes
        if(isSpliceActionEnable){
            if(day.getHasSplice() != 0){//Se cambia a != 0 para considerar ambos casos
                actionSplice.action(day);
            }

            return;
        }

        select(day);

        Calendar cal = mAdapter.getCalendar();

        int newYear = day.getYear();
        int newMonth = day.getMonth();
        int oldYear = cal.get(Calendar.YEAR);
        int oldMonth = cal.get(Calendar.MONTH);
        if (newMonth != oldMonth) {
            cal.set(day.getYear(), day.getMonth(), 1);

            if (newYear > oldYear || newMonth > oldMonth) {
                mCurrentWeekIndex = 0;
            }
            if (newYear < oldYear || newMonth < oldMonth) {
                mCurrentWeekIndex = -1;
            }
            if (mListener != null) {
                mListener.onMonthChange();
            }
            reload();
        }

        if (mListener != null) {
            mListener.onItemClick(view);
        }
    }

    // public methods
    public void setAdapter(CalendarAdapter adapter) {
        mAdapter = adapter;
        adapter.setFirstDayOfWeek(getFirstDayOfWeek());

        reload();

        // init week
        mCurrentWeekIndex = getSuitableRowIndex();
    }

    public void addEventTag(int numYear, int numMonth, int numDay) {
        mAdapter.addEvent(new Event(numYear, numMonth, numDay,getEventColor()));

        reload();
    }

    public void addEventTag(int numYear, int numMonth, int numDay,int color) {
        mAdapter.addEvent(new Event(numYear, numMonth, numDay,color));


        reload();
    }

    public void prevMonth() {
        Calendar cal = mAdapter.getCalendar();
        if (cal.get(Calendar.MONTH) == cal.getActualMinimum(Calendar.MONTH)) {
            cal.set((cal.get(Calendar.YEAR) - 1), cal.getActualMaximum(Calendar.MONTH), 1);
        } else {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        }
        reload();
        if (mListener != null) {
            mListener.onMonthChange();
        }
    }

    public void nextMonth() {
        Calendar cal = mAdapter.getCalendar();
        if (cal.get(Calendar.MONTH) == cal.getActualMaximum(Calendar.MONTH)) {
            cal.set((cal.get(Calendar.YEAR) + 1), cal.getActualMinimum(Calendar.MONTH), 1);
        } else {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        }
        reload();
        if (mListener != null) {
            mListener.onMonthChange();
        }
    }

    public void prevWeek() {
        if (mCurrentWeekIndex - 1 < 0) {
            mCurrentWeekIndex = -1;
            prevMonth();
        } else {
            mCurrentWeekIndex--;
            collapseTo(mCurrentWeekIndex);
        }
    }

    public void nextWeek() {
        if (mCurrentWeekIndex + 1 >= mTableBody.getChildCount()) {
            mCurrentWeekIndex = 0;
            nextMonth();
        } else {
            mCurrentWeekIndex++;
            collapseTo(mCurrentWeekIndex);
        }
    }

    public int getYear() {
        return mAdapter.getCalendar().get(Calendar.YEAR);
    }

    public int getMonth() {
        return mAdapter.getCalendar().get(Calendar.MONTH);
    }

    public void setMonth(int month) {
         mAdapter.getCalendar().set(Calendar.MONTH,month);
    }


    public Day getSelectedDay() {
        if (getSelectedItem()==null){
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            return new Day(
                    year,
                    month+1,
                    day);
        }
        return new Day(
                getSelectedItem().getYear(),
                getSelectedItem().getMonth(),
                getSelectedItem().getDay());
    }

    public boolean isSelectedDay(Day day) {
        return day != null
                && getSelectedItem() != null
                && day.getYear() == getSelectedItem().getYear()
                && day.getMonth() == getSelectedItem().getMonth()
                && day.getDay() == getSelectedItem().getDay();
    }

    public boolean isInSelectedDayList(Day day) {

        if(getSelectedItems() != null){
            for(Day d : getSelectedItems()){
                if(day.getYear() == d.getYear()
                        && day.getMonth() == d.getMonth()
                        && day.getDay() == d.getDay()){
                    return true;
                }
            }
        }

        return false;
    }

    public void setBackgroundDay(Day day) {

        if(getSelectedItems() != null){
            for(Day d : getSelectedItems()){
                if(day.getYear() == d.getYear()
                        && day.getMonth() == d.getMonth()
                        && day.getDay() == d.getDay()){
                    day.setHasSplice(d.getHasSplice());
                }
            }
        }

    }

    public boolean isToady(Day day) {
        Calendar todayCal = Calendar.getInstance();
        return day != null
                && day.getYear() == todayCal.get(Calendar.YEAR)
                && day.getMonth() == todayCal.get(Calendar.MONTH)
                && day.getDay() == todayCal.get(Calendar.DAY_OF_MONTH);
    }

    public int getSelectedItemPosition() {
        int position = -1;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Day day = mAdapter.getItem(i);

            if (isSelectedDay(day)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodayItemPosition() {
        int position = -1;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Day day = mAdapter.getItem(i);

            if (isToady(day)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public void collapse(int duration) {

        if (getState() == STATE_EXPANDED) {
            setState(STATE_PROCESSING);

            mLayoutBtnGroupMonth.setVisibility(GONE);
            mLayoutBtnGroupWeek.setVisibility(VISIBLE);
            mBtnPrevWeek.setClickable(false);
            mBtnNextWeek.setClickable(false);

            int index = getSuitableRowIndex();
            mCurrentWeekIndex = index;

            final int currentHeight = mInitHeight;
            final int targetHeight = mTableBody.getChildAt(index).getMeasuredHeight();
            int tempHeight = 0;
            for (int i = 0; i < index; i++) {
                tempHeight += mTableBody.getChildAt(i).getMeasuredHeight();
            }
            final int topHeight = tempHeight;

            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    mScrollViewBody.getLayoutParams().height = (interpolatedTime == 1)
                            ? targetHeight
                            : currentHeight - (int) ((currentHeight - targetHeight) * interpolatedTime);
                    mScrollViewBody.requestLayout();

                    if (mScrollViewBody.getMeasuredHeight() < topHeight + targetHeight) {
                        int position = topHeight + targetHeight - mScrollViewBody.getMeasuredHeight();
                        mScrollViewBody.smoothScrollTo(0, position);
                    }

                    if (interpolatedTime == 1) {
                        setState(STATE_COLLAPSED);

                        mBtnPrevWeek.setClickable(true);
                        mBtnNextWeek.setClickable(true);
                    }
                }
            };
            anim.setDuration(duration);
            startAnimation(anim);
        }

        expandIconView.setState(ExpandIconView.MORE,true);
    }

    private void collapseTo(int index) {
        if (getState() == STATE_COLLAPSED) {
            if (index == -1) {
                index = mTableBody.getChildCount() - 1;
            }
            mCurrentWeekIndex = index;

            final int targetHeight = mTableBody.getChildAt(index).getMeasuredHeight();
            int tempHeight = 0;
            for (int i = 0; i < index; i++) {
                tempHeight += mTableBody.getChildAt(i).getMeasuredHeight();
            }
            final int topHeight = tempHeight;

            mScrollViewBody.getLayoutParams().height = targetHeight;
            mScrollViewBody.requestLayout();

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mScrollViewBody.smoothScrollTo(0, topHeight);
                }
            });


            if (mListener != null) {
                mListener.onWeekChange(mCurrentWeekIndex);
            }
        }
    }

    public void expand(int duration) {
        if (getState() == STATE_COLLAPSED) {
            setState(STATE_PROCESSING);

            mLayoutBtnGroupMonth.setVisibility(VISIBLE);
            mLayoutBtnGroupWeek.setVisibility(GONE);
            mBtnPrevMonth.setClickable(false);
            mBtnNextMonth.setClickable(false);

            final int currentHeight = mScrollViewBody.getMeasuredHeight();
            final int targetHeight = mInitHeight;

            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {

                    mScrollViewBody.getLayoutParams().height = (interpolatedTime == 1)
                            ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : currentHeight - (int) ((currentHeight - targetHeight) * interpolatedTime);
                    mScrollViewBody.requestLayout();

                    if (interpolatedTime == 1) {
                        setState(STATE_EXPANDED);

                        mBtnPrevMonth.setClickable(true);
                        mBtnNextMonth.setClickable(true);
                    }
                }
            };
            anim.setDuration(duration);
            startAnimation(anim);
        }

        expandIconView.setState(ExpandIconView.LESS,true);
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        if(state == STATE_COLLAPSED) {
            expanded = false;
        }
        if(state == STATE_EXPANDED) {
            expanded = true;
        }
    }

    public void select(Day day) {
        setSelectedItem(new Day(day.getYear(), day.getMonth(), day.getDay()));

        redraw();

        if (mListener != null) {
            mListener.onDaySelect();
        }
    }

    public void select(List<Day> days) {
        setSelectedItems(days);

        redraw();

        if (mListener != null) {
            mListener.onDaySelect();
        }
    }

    public void setStateWithUpdateUI(int state) {
        setState(state);

        if (getState() != state) {
            mIsWaitingForUpdate = true;
            requestLayout();
        }
    }

    // callback
    public void setCalendarListener(CalendarListener listener) {
        mListener = listener;
    }

    public interface CalendarListener {

        // triggered when a day is selected programmatically or clicked by user.
        void onDaySelect();

        // triggered only when the views of day on calendar are clicked by user.
        void onItemClick(View v);

        // triggered when the data of calendar are updated by changing month or adding events.
        void onDataUpdate();

        // triggered when the month are changed.
        void onMonthChange();

        // triggered when the week position are changed.
        void onWeekChange(int position);
    }

    public void setExpandIconVisible(boolean visible){
        if(visible){
            expandIconView.setVisibility(VISIBLE);
        }else {
            expandIconView.setVisibility(GONE);
        }
    }


    public void setMultipleDays(boolean multipleDays) {
        isMultipleDays = multipleDays;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setActionSplice(CommandSplice actionSplice) {
        this.actionSplice = actionSplice;
    }

    public void setSpliceActionEnable(boolean spliceActionEnable) {
        isSpliceActionEnable = spliceActionEnable;
    }

    public void setShowCompleteDaysName(boolean showCompleteDaysName) {
        this.showCompleteDaysName = showCompleteDaysName;
    }


}

