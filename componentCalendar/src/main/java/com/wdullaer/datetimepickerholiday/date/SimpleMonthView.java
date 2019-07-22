/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wdullaer.datetimepickerholiday.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleMonthView extends MonthView {

    HashMap<String,DaySelectedHoliday> daysSelectedlist;

    public SimpleMonthView(Context context, AttributeSet attr, DatePickerController controller) {
        super(context, attr, controller);
        daysSelectedlist = mController.getDaysSelected();

    }

    @Override
    public void drawMonthDay(Canvas canvas, int year, int month, int day,
                             int x, int y, int startX, int stopX, int startY, int stopY) {

        String date = String.format("%s%s%s",String.valueOf(year),month < 10 ? "0"+month : month,day < 10 && day > 0 ? "0"+day : day );
        String dateSelected = String.format("%s%s%s",String.valueOf(year),month < 10 ? "0"+month : month,mSelectedDay < 10 && mSelectedDay > 0 ? "0"+mSelectedDay : mSelectedDay );

        if(dateSelected.equals(date) && mController.isTappedSelected() ){
            if( daysSelectedlist.containsKey(dateSelected)){
                daysSelectedlist.remove(dateSelected);
            }else {
                daysSelectedlist.put(dateSelected,new DaySelectedHoliday(dateSelected,mSelectedDay,month,year,false));
            }
            mController.setIsTappedSelected(false);
        }

        if (daysSelectedlist.containsKey(date)) {
           // Toast.makeText(getContext(),"Seleccionados: " +daysSelectedlist.size(),Toast.LENGTH_SHORT).show();

            if(daysSelectedlist.get(date).isHalfDay()){
                canvas.drawCircle(x, y - (MINI_DAY_NUMBER_TEXT_SIZE / 3), DAY_SELECTED_CIRCLE_SIZE,
                        mSelectedHalfCirclePaint);
            }else {
                canvas.drawCircle(x, y - (MINI_DAY_NUMBER_TEXT_SIZE / 3), DAY_SELECTED_CIRCLE_SIZE,
                        mSelectedCirclePaint);
            }

        }

        if (isHighlighted(year, month, day) && mSelectedDay != day) {
            canvas.drawCircle(x, y + MINI_DAY_NUMBER_TEXT_SIZE - DAY_HIGHLIGHT_CIRCLE_MARGIN,
                    DAY_HIGHLIGHT_CIRCLE_SIZE, mSelectedCirclePaint);
            mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        } else {
            mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        }

        // gray out the day number if it's outside the range.
        if (mController.isOutOfRange(year, month, day)) {
            mMonthNumPaint.setColor(mDisabledDayTextColor);
            //mMonthNumPaint.setColor(mSelectedDayTextColor);
        } else if (daysSelectedlist.containsKey(date)) {

            if(daysSelectedlist.get(date).isHalfDay()){
                mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                mMonthNumPaint.setColor(mTodayNumberColor);
            }else {
                mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                mMonthNumPaint.setColor(mSelectedDayTextColor);
            }
        }
        else if (mHasToday && mToday == day) {
            mMonthNumPaint.setColor(mTodayNumberColor);
        } else {
            mMonthNumPaint.setColor(isHighlighted(year, month, day) ? mHighlightedDayTextColor : mDayTextColor);
        }

        canvas.drawText(String.format(mController.getLocale(), "%d", day), x, y, mMonthNumPaint);

        mController.setDaysSelected(this.daysSelectedlist);
    }
}
