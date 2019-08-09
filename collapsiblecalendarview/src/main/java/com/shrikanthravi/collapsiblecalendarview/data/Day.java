package com.shrikanthravi.collapsiblecalendarview.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class Day implements Parcelable{

    private int mYear;
    private int mMonth;
    private int mDay;

    private int hasSplice;

    public Day(int year, int month, int day){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }

    public Day(int mYear, int mMonth, int mDay, int hasSplice) {
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        this.hasSplice = hasSplice;
    }

    public int getMonth(){
        return mMonth;
    }

    public int getYear(){
        return mYear;
    }

    public int getDay(){
        return mDay;
    }


    public int getHasSplice() {
        return hasSplice;
    }

    public void setHasSplice(int hasSplice) {
        this.hasSplice = hasSplice;
    }

    public Day(Parcel in){
        int[] data = new int[4];
        in.readIntArray(data);
        this.mYear = data[0];
        this.mMonth = data[1];
        this.mYear = data[2];
        this.hasSplice = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(new int[] {this.mYear,
                this.mMonth,
                this.mDay,this.hasSplice});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };


}
