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

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdullaer.datetimepickerholiday.HapticFeedbackController;
import com.wdullaer.datetimepickerholiday.R;
import com.wdullaer.datetimepickerholiday.TypefaceHelper;
import com.wdullaer.datetimepickerholiday.Utils;
import com.wdullaer.materialdatepicker.date.AccessibleDateAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * Dialog allowing users to select a date.
 */
public class DatePickerHolidayDialog extends DialogFragment implements
        OnClickListener, DatePickerController {

    public enum Version {
        VERSION_1,
        VERSION_2
    }

    public enum ScrollOrientation {
        HORIZONTAL,
        VERTICAL
    }

    private static final int UNINITIALIZED = -1;
    private static final int MONTH_AND_DAY_VIEW = 0;
    private static final int YEAR_VIEW = 1;

    private boolean isTappedSelected;

    private View view;

    private static final String KEY_SELECTED_YEAR = "year";
    private static final String KEY_SELECTED_MONTH = "month";
    private static final String KEY_SELECTED_DAY = "day";
    private static final String KEY_LIST_POSITION = "list_position";
    private static final String KEY_WEEK_START = "week_start";
    private static final String KEY_CURRENT_VIEW = "current_view";
    private static final String KEY_LIST_POSITION_OFFSET = "list_position_offset";
    private static final String KEY_HIGHLIGHTED_DAYS = "highlighted_days";
    private static final String KEY_THEME_DARK = "theme_dark";
    private static final String KEY_THEME_DARK_CHANGED = "theme_dark_changed";
    private static final String KEY_ACCENT = "accent";
    private static final String KEY_VIBRATE = "vibrate";
    private static final String KEY_DISMISS = "dismiss";
    private static final String KEY_AUTO_DISMISS = "auto_dismiss";
    private static final String KEY_DEFAULT_VIEW = "default_view";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OK_RESID = "ok_resid";
    private static final String KEY_OK_STRING = "ok_string";
    private static final String KEY_OK_COLOR = "ok_color";
    private static final String KEY_CANCEL_RESID = "cancel_resid";
    private static final String KEY_CANCEL_STRING = "cancel_string";
    private static final String KEY_CANCEL_COLOR = "cancel_color";
    private static final String KEY_VERSION = "version";
    private static final String KEY_TIMEZONE = "timezone";
    private static final String KEY_DATERANGELIMITER = "daterangelimiter";
    private static final String KEY_SCROLL_ORIENTATION = "scrollorientation";
    private static final String KEY_LOCALE = "locale";

    private static final int ANIMATION_DURATION = 300;
    private static final int ANIMATION_DELAY = 500;

    private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Locale.getDefault());
    private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM", Locale.getDefault());
    private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd", Locale.getDefault());
    private static SimpleDateFormat VERSION_2_FORMAT;

    private Calendar mCalendar = Utils.trimToMidnight(Calendar.getInstance(getTimeZone()));
    private OnDateSetListener mCallBack;
    private HashSet<OnDateChangedListener> mListeners = new HashSet<>();
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    private AccessibleDateAnimator mAnimator;

    private TextView mDatePickerHeaderView;
    private LinearLayout mMonthAndDayView;
    private TextView mSelectedMonthTextView;
    private TextView mSelectedDayTextView;
    private TextView mYearView;
    private DayPickerGroup mDayPickerView;
    private YearPickerView mYearPickerView;


    private TextView titleCustomCalendar;
    private String titleCustom;

    private int mCurrentView = UNINITIALIZED;

    private int mWeekStart = mCalendar.getFirstDayOfWeek();
    private String mTitle;
    private HashSet<Calendar> highlightedDays = new HashSet<>();
    private boolean mThemeDark = false;
    private boolean mThemeDarkChanged = false;
    private int mAccentColor = -1;
    private boolean mVibrate = true;
    private boolean mDismissOnPause = false;
    private boolean mAutoDismiss = false;
    private int mDefaultView = MONTH_AND_DAY_VIEW;
    private int mOkResid = R.string.mdtp_ok;
    private String mOkString;
    private int mOkColor = -1;
    private int mCancelResid = R.string.mdtp_cancel;
    private String mCancelString;
    private int mCancelColor = -1;
    private Version mVersion;
    private ScrollOrientation mScrollOrientation;
    private TimeZone mTimezone;
    private Locale mLocale = Locale.getDefault();
    private DefaultDateRangeLimiter mDefaultLimiter = new DefaultDateRangeLimiter();
    private DateRangeLimiter mDateRangeLimiter = mDefaultLimiter;

    private HapticFeedbackController mHapticFeedbackController;

    private boolean mDelayAnimation = true;

    // Accessibility strings.
    private String mDayPickerDescription;
    private String mSelectDay;
    private String mYearPickerDescription;
    private String mSelectYear;


    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    View mCardFrontLayout;
    View mCardBackLayout;
    private boolean isEnabledCalendar;

    private TextView daysAvailable;
    private TextView daysSelected;
    private TextView daysSelectedConfig;



    RecyclerView recyclerViewConfig;
    private boolean mIsBackVisible = false;


    private List<DaySelectedHoliday> daySelectedHolidayList;
    private HashMap<String, DaySelectedHoliday> daysSelectedMap = new HashMap<>();
    Map<Long, DaySelectedHoliday> datesSorted;
    private DaysConfigRecyclerAdapter daysConfigRecyclerAdapter;
    private boolean showHalfDaysOption = true;
    private double num_total_vacaciones;
    private double num_diasagendados;
    private double limite_dias;


    private double currentDaysScheduled;

    private String des_mensaje;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        /**
         * @param view        The view associated with this listener.
         * @param year        The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *                    with {@link java.util.Calendar}.
         * @param dayOfMonth  The day of the month that was set.
         */
        void onDateSet(DatePickerHolidayDialog view, int year, int monthOfYear, int dayOfMonth);

        void onDatesSelectedHolidays(Map<String, List<DaySelectedHoliday>> periods, double totalDays);
        void onInvalidMaxSelectedDays(String msg);
    }

    /**
     * The callback used to notify other date picker components of a change in selected date.
     */
    @SuppressWarnings("WeakerAccess")
    protected interface OnDateChangedListener {

        void onDateChanged();
    }


    public DatePickerHolidayDialog() {
        // Empty constructor required for dialog fragment.
    }

    /**
     * Create a new DatePickerHolidayDialog instance with a specific initial selection.
     *
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     * @return a new DatePickerHolidayDialog instance.
     */
    public static DatePickerHolidayDialog newInstance(OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        DatePickerHolidayDialog ret = new DatePickerHolidayDialog();
        ret.initialize(callBack, year, monthOfYear, dayOfMonth);
        return ret;
    }

    /**
     * Create a new DatePickerHolidayDialog instance initialised to the current system date.
     *
     * @param callback How the parent is notified that the date is set.
     * @return a new DatePickerHolidayDialog instance
     */
    @SuppressWarnings("unused")
    public static DatePickerHolidayDialog newInstance(OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        return DatePickerHolidayDialog.newInstance(callback, now);
    }

    /**
     * Create a new DatePickerHolidayDialog instance with a specific initial selection.
     *
     * @param callback         How the parent is notified that the date is set.
     * @param initialSelection A Calendar object containing the original selection of the picker.
     *                         (Time is ignored by trimming the Calendar to midnight in the current
     *                         TimeZone of the Calendar object)
     * @return a new DatePickerHolidayDialog instance
     */
    @SuppressWarnings("unused")
    public static DatePickerHolidayDialog newInstance(OnDateSetListener callback, Calendar initialSelection) {
        DatePickerHolidayDialog ret = new DatePickerHolidayDialog();
        ret.initialize(callback, initialSelection);
        return ret;
    }

    public void initialize(OnDateSetListener callBack, Calendar initialSelection) {
        mCallBack = callBack;
        mCalendar = Utils.trimToMidnight((Calendar) initialSelection.clone());
        mScrollOrientation = null;
        //noinspection deprecation
        setTimeZone(mCalendar.getTimeZone());

       // mVersion = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ? Version.VERSION_1 : Version.VERSION_2;
        mVersion = Version.VERSION_2;
    }

    public void initialize(OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance(getTimeZone());
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.initialize(callBack, cal);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mCurrentView = UNINITIALIZED;
        if (savedInstanceState != null) {
            mCalendar.set(Calendar.YEAR, savedInstanceState.getInt(KEY_SELECTED_YEAR));
            mCalendar.set(Calendar.MONTH, savedInstanceState.getInt(KEY_SELECTED_MONTH));
            mCalendar.set(Calendar.DAY_OF_MONTH, savedInstanceState.getInt(KEY_SELECTED_DAY));
            mDefaultView = savedInstanceState.getInt(KEY_DEFAULT_VIEW);
        }
        if (Build.VERSION.SDK_INT < 18) {
            VERSION_2_FORMAT = new SimpleDateFormat(activity.getResources().getString(R.string.mdtp_date_v2_daymonthyear), mLocale);
        } else {
            VERSION_2_FORMAT = new SimpleDateFormat(DateFormat.getBestDateTimePattern(mLocale, "EEEMMMdd"), mLocale);
        }
        VERSION_2_FORMAT.setTimeZone(getTimeZone());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_YEAR, mCalendar.get(Calendar.YEAR));
        outState.putInt(KEY_SELECTED_MONTH, mCalendar.get(Calendar.MONTH));
        outState.putInt(KEY_SELECTED_DAY, mCalendar.get(Calendar.DAY_OF_MONTH));
        outState.putInt(KEY_WEEK_START, mWeekStart);
        outState.putInt(KEY_CURRENT_VIEW, mCurrentView);
        int listPosition = -1;
        if (mCurrentView == MONTH_AND_DAY_VIEW) {
            listPosition = mDayPickerView.getMostVisiblePosition();
        } else if (mCurrentView == YEAR_VIEW) {
            listPosition = mYearPickerView.getFirstVisiblePosition();
            outState.putInt(KEY_LIST_POSITION_OFFSET, mYearPickerView.getFirstPositionOffset());
        }
        outState.putInt(KEY_LIST_POSITION, listPosition);
        outState.putSerializable(KEY_HIGHLIGHTED_DAYS, highlightedDays);
        outState.putBoolean(KEY_THEME_DARK, mThemeDark);
        outState.putBoolean(KEY_THEME_DARK_CHANGED, mThemeDarkChanged);
        outState.putInt(KEY_ACCENT, mAccentColor);
        outState.putBoolean(KEY_VIBRATE, mVibrate);
        outState.putBoolean(KEY_DISMISS, mDismissOnPause);
        outState.putBoolean(KEY_AUTO_DISMISS, mAutoDismiss);
        outState.putInt(KEY_DEFAULT_VIEW, mDefaultView);
        outState.putString(KEY_TITLE, mTitle);
        outState.putInt(KEY_OK_RESID, mOkResid);
        outState.putString(KEY_OK_STRING, mOkString);
        outState.putInt(KEY_OK_COLOR, mOkColor);
        outState.putInt(KEY_CANCEL_RESID, mCancelResid);
        outState.putString(KEY_CANCEL_STRING, mCancelString);
        outState.putInt(KEY_CANCEL_COLOR, mCancelColor);
        outState.putSerializable(KEY_VERSION, mVersion);
        outState.putSerializable(KEY_SCROLL_ORIENTATION, mScrollOrientation);
        outState.putSerializable(KEY_TIMEZONE, mTimezone);
        outState.putParcelable(KEY_DATERANGELIMITER, mDateRangeLimiter);
        outState.putSerializable(KEY_LOCALE, mLocale);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int listPosition = -1;
        int listPositionOffset = 0;
        int currentView = mDefaultView;
        if (mScrollOrientation == null) {
            mScrollOrientation = mVersion == Version.VERSION_1
                    ? ScrollOrientation.VERTICAL
                    : ScrollOrientation.HORIZONTAL;
        }
        if (savedInstanceState != null) {
            mWeekStart = savedInstanceState.getInt(KEY_WEEK_START);
            currentView = savedInstanceState.getInt(KEY_CURRENT_VIEW);
            listPosition = savedInstanceState.getInt(KEY_LIST_POSITION);
            listPositionOffset = savedInstanceState.getInt(KEY_LIST_POSITION_OFFSET);
            //noinspection unchecked
            highlightedDays = (HashSet<Calendar>) savedInstanceState.getSerializable(KEY_HIGHLIGHTED_DAYS);
            mThemeDark = savedInstanceState.getBoolean(KEY_THEME_DARK);
            mThemeDarkChanged = savedInstanceState.getBoolean(KEY_THEME_DARK_CHANGED);
            mAccentColor = savedInstanceState.getInt(KEY_ACCENT);
            mVibrate = savedInstanceState.getBoolean(KEY_VIBRATE);
            mDismissOnPause = savedInstanceState.getBoolean(KEY_DISMISS);
            mAutoDismiss = savedInstanceState.getBoolean(KEY_AUTO_DISMISS);
            mTitle = savedInstanceState.getString(KEY_TITLE);
            mOkResid = savedInstanceState.getInt(KEY_OK_RESID);
            mOkString = savedInstanceState.getString(KEY_OK_STRING);
            mOkColor = savedInstanceState.getInt(KEY_OK_COLOR);
            mCancelResid = savedInstanceState.getInt(KEY_CANCEL_RESID);
            mCancelString = savedInstanceState.getString(KEY_CANCEL_STRING);
            mCancelColor = savedInstanceState.getInt(KEY_CANCEL_COLOR);
            mVersion = (Version) savedInstanceState.getSerializable(KEY_VERSION);
            mScrollOrientation = (ScrollOrientation) savedInstanceState.getSerializable(KEY_SCROLL_ORIENTATION);
            mTimezone = (TimeZone) savedInstanceState.getSerializable(KEY_TIMEZONE);
            mDateRangeLimiter = savedInstanceState.getParcelable(KEY_DATERANGELIMITER);

            /*
            We need to update some variables when setting the locale, so use the setter rather
            than a plain assignment
             */
            setLocale((Locale) savedInstanceState.getSerializable(KEY_LOCALE));

            /*
            If the user supplied a custom limiter, we need to create a new default one to prevent
            null pointer exceptions on the configuration methods
            If the user did not supply a custom limiter we need to ensure both mDefaultLimiter
            and mDateRangeLimiter are the same reference, so that the config methods actually
            affect the behaviour of the picker (in the unlikely event the user reconfigures
            the picker when it is shown)
             */
            if (mDateRangeLimiter instanceof DefaultDateRangeLimiter) {
                mDefaultLimiter = (DefaultDateRangeLimiter) mDateRangeLimiter;
            } else {
                mDefaultLimiter = new DefaultDateRangeLimiter();
            }
        }

        mDefaultLimiter.setController(this);

//        int viewRes = mVersion == Version.VERSION_1 ? R.layout.mdtp_date_picker_dialog_library : R.layout.mdtp_date_picker_dialog_gral_v2;
        int viewRes = R.layout.dialog_holiday_calendar;

        view = inflater.inflate(viewRes, container, false);
        // All options have been set at this point: round the initial selection if necessary
        mCalendar = mDateRangeLimiter.setToNearestDate(mCalendar);
        daysAvailable = view.findViewById(R.id.daysAvailable);
        daysSelectedConfig = view.findViewById(R.id.daysSelectedConfig);
        daysSelected = view.findViewById(R.id.daysSelected);
        titleCustomCalendar = view.findViewById(R.id.titleCustomCalendar);
        mDatePickerHeaderView = view.findViewById(R.id.mdtp_date_picker_header);
        mMonthAndDayView = view.findViewById(R.id.mdtp_date_picker_month_and_day);
        mMonthAndDayView.setOnClickListener(this);
        mSelectedMonthTextView = view.findViewById(R.id.mdtp_date_picker_month_v1);
        mSelectedDayTextView = view.findViewById(R.id.mdtp_date_picker_day);
        mYearView = view.findViewById(R.id.mdtp_date_picker_year);
        mYearView.setOnClickListener(this);


        /**Custom holiday*/
        mCardFrontLayout = view.findViewById(R.id.card_front);
        mCardBackLayout = view.findViewById(R.id.card_back);
        recyclerViewConfig = view.findViewById(R.id.rcvFields);
        recyclerViewConfig.setHasFixedSize(true);
        recyclerViewConfig.setLayoutManager(new LinearLayoutManager(getActivity()));
        daySelectedHolidayList = new ArrayList<>();
        daysConfigRecyclerAdapter = new DaysConfigRecyclerAdapter(getActivity(), daySelectedHolidayList);
        daysConfigRecyclerAdapter.setTotalDaysTxt(daysSelectedConfig);
        recyclerViewConfig.setAdapter(daysConfigRecyclerAdapter);

        titleCustomCalendar.setText(titleCustom);

        String totalVacacionesAsString = String.valueOf(num_total_vacaciones);

        if(num_total_vacaciones % 1 == 0){
            totalVacacionesAsString = totalVacacionesAsString.substring(0,totalVacacionesAsString.indexOf("."));
            totalVacacionesAsString = String.valueOf(Integer.parseInt(totalVacacionesAsString));
        }

        daysAvailable.setText(String.format("%s %s",totalVacacionesAsString, num_total_vacaciones > 1 ? "días" :"día"));

        final Activity activity = getActivity();
        mDayPickerView = new DayPickerGroup(activity, this);
        mYearPickerView = new YearPickerView(activity, this);

        // if theme mode has not been set by java code, check if it is specified in Style.xml
        if (!mThemeDarkChanged) {
            mThemeDark = Utils.isDarkTheme(activity, mThemeDark);
        }

        Resources res = getResources();
        mDayPickerDescription = res.getString(R.string.mdtp_day_picker_description);
        mSelectDay = res.getString(R.string.mdtp_select_day);
        mYearPickerDescription = res.getString(R.string.mdtp_year_picker_description);
        mSelectYear = res.getString(R.string.mdtp_select_year);

        int bgColorResource = mThemeDark ? R.color.transparent : R.color.transparent;
        int bgColor = ContextCompat.getColor(activity, bgColorResource);
        view.setBackgroundColor(bgColor);

        mAnimator = view.findViewById(R.id.mdtp_animator_holiday);
        mAnimator.addView(mDayPickerView);
        mAnimator.addView(mYearPickerView);
        mAnimator.setDateMillis(mCalendar.getTimeInMillis());
        // TODO: Replace with animation decided upon by the design team.
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(ANIMATION_DURATION);
        mAnimator.setInAnimation(animation);
        // TODO: Replace with animation decided upon by the design team.
        Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(ANIMATION_DURATION);
        mAnimator.setOutAnimation(animation2);

        String buttonTypeface = activity.getResources().getString(R.string.mdtp_button_typeface);
        Button okButton = view.findViewById(R.id.mdtp_ok);
        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daysSelectedMap != null && daysSelectedMap.size() > 0){
                    tryVibrate();
                    setConfigData();
                    notifyOnDatesHolidaysListener();

                }

            }
        });
        okButton.setTypeface(TypefaceHelper.get(activity, buttonTypeface));
        /*if (mOkString != null) okButton.setText(mOkString);
        else okButton.setText(mOkResid);*/

        Button cancelButton = view.findViewById(R.id.mdtp_cancel);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getDialog() != null) getDialog().cancel();
            }
        });


        cancelButton.setTypeface(TypefaceHelper.get(activity, buttonTypeface));
        //if (mCancelString != null) cancelButton.setText(mCancelString);
        //else cancelButton.setText(mCancelResid);
        cancelButton.setVisibility(isCancelable() ? View.VISIBLE : View.GONE);


        TextView configDaysBtn = view.findViewById(R.id.configDays);

        configDaysBtn.setEnabled(showHalfDaysOption);
        configDaysBtn.setVisibility(showHalfDaysOption ? View.VISIBLE : View.INVISIBLE);
        configDaysBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daysSelectedMap != null && daysSelectedMap.size() > 0){
                    setConfigData();

                    List<DaySelectedHoliday> daySelectedHolidaysSource = new ArrayList<>();
                    for(DaySelectedHoliday daySelectedHoliday :daySelectedHolidayList){
                        daySelectedHolidaysSource.add(new DaySelectedHoliday(daySelectedHoliday));
                    }

                    daysConfigRecyclerAdapter.setDaySelectedHolidayListInitial(daySelectedHolidaysSource);
                    changeView();
                }

            }
        });

        Button okButtonBack = view.findViewById(R.id.mdtp_ok_back);
        okButtonBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDaysSelection();


                changeView();
            }
        });

        Button cancelButtonBack = view.findViewById(R.id.mdtp_cancel_back);
        cancelButtonBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                daysConfigRecyclerAdapter.setDaySelectedHolidaysOriginal();
                changeView();
            }
        });


        // If an accent color has not been set manually, get it from the context
        if (mAccentColor == -1) {
            mAccentColor = Utils.getAccentColorFromThemeIfAvailable(getActivity());
        }
        if (mDatePickerHeaderView != null)
            mDatePickerHeaderView.setBackgroundColor(Utils.darkenColor(mAccentColor));


        //view.findViewById(R.id.mdtp_day_picker_selected_date_layout).setBackgroundColor(Color.parseColor("#0a9dd5"));

        // Buttons can have a different color
        /*if (mOkColor != -1) okButton.setTextColor(mOkColor);
        else okButton.setTextColor(mAccentColor);

        if (mCancelColor != -1) cancelButton.setTextColor(mCancelColor);
        else cancelButton.setTextColor(mAccentColor);*/

        if (getDialog() == null) {
            view.findViewById(R.id.mdtp_done_background).setVisibility(View.GONE);
        }

        updateDisplay(false);
        setCurrentView(currentView);

        if (listPosition != -1) {
            if (currentView == MONTH_AND_DAY_VIEW) {
                mDayPickerView.postSetSelection(listPosition);
            } else if (currentView == YEAR_VIEW) {
                mYearPickerView.postSetSelectionFromTop(listPosition, listPositionOffset);
            }
        }

        mHapticFeedbackController = new HapticFeedbackController(activity);
        return view;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);
            viewGroup.addView(view);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset_alert);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHapticFeedbackController.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHapticFeedbackController.stop();
        if (mDismissOnPause) dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null) mOnCancelListener.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) mOnDismissListener.onDismiss(dialog);
    }

    private void setCurrentView(final int viewIndex) {
        long millis = mCalendar.getTimeInMillis();

        switch (viewIndex) {
            case MONTH_AND_DAY_VIEW:
                if (mVersion == Version.VERSION_1) {
                    ObjectAnimator pulseAnimator = Utils.getPulseAnimator(mMonthAndDayView, 0.9f,
                            1.05f);
                    if (mDelayAnimation) {
                        pulseAnimator.setStartDelay(ANIMATION_DELAY);
                        mDelayAnimation = false;
                    }
                    mDayPickerView.onDateChanged();
                    if (mCurrentView != viewIndex) {
                        mMonthAndDayView.setSelected(true);
                        mYearView.setSelected(false);
                        mAnimator.setDisplayedChild(MONTH_AND_DAY_VIEW);
                        mCurrentView = viewIndex;
                    }
                    pulseAnimator.start();
                } else {
                    mDayPickerView.onDateChanged();
                    if (mCurrentView != viewIndex) {
                        mMonthAndDayView.setSelected(true);
                        mYearView.setSelected(false);
                        mAnimator.setDisplayedChild(MONTH_AND_DAY_VIEW);
                        mCurrentView = viewIndex;
                    }
                }

                int flags = DateUtils.FORMAT_SHOW_DATE;
                String dayString = DateUtils.formatDateTime(getActivity(), millis, flags);
                mAnimator.setContentDescription(mDayPickerDescription + ": " + dayString);
                Utils.tryAccessibilityAnnounce(mAnimator, mSelectDay);
                break;
            case YEAR_VIEW:
                if (mVersion == Version.VERSION_1) {
                    ObjectAnimator pulseAnimator = Utils.getPulseAnimator(mYearView, 0.85f, 1.1f);
                    if (mDelayAnimation) {
                        pulseAnimator.setStartDelay(ANIMATION_DELAY);
                        mDelayAnimation = false;
                    }
                    mYearPickerView.onDateChanged();
                    if (mCurrentView != viewIndex) {
                        mMonthAndDayView.setSelected(false);
                        mYearView.setSelected(true);
                        mAnimator.setDisplayedChild(YEAR_VIEW);
                        mCurrentView = viewIndex;
                    }
                    pulseAnimator.start();
                } else {
                    mYearPickerView.onDateChanged();
                    if (mCurrentView != viewIndex) {
                        mMonthAndDayView.setSelected(false);
                        mYearView.setSelected(true);
                        mAnimator.setDisplayedChild(YEAR_VIEW);
                        mCurrentView = viewIndex;
                    }
                }

                CharSequence yearString = YEAR_FORMAT.format(millis);
                mAnimator.setContentDescription(mYearPickerDescription + ": " + yearString);
                Utils.tryAccessibilityAnnounce(mAnimator, mSelectYear);
                break;
        }
    }

    private void updateDisplay(boolean announce) {
        mYearView.setText(YEAR_FORMAT.format(mCalendar.getTime()));

        if (mVersion == Version.VERSION_1) {
            if (mDatePickerHeaderView != null) {
                if (mTitle != null)
                    mDatePickerHeaderView.setText(mTitle);
                else {
                    mDatePickerHeaderView.setText(mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG,
                            mLocale));
                }
            }
            if (mSelectedMonthTextView == null)
                mSelectedMonthTextView = view.findViewById(R.id.mdtp_date_picker_month_v1);

            if (mSelectedMonthTextView != null)
                mSelectedMonthTextView.setText(MONTH_FORMAT.format(mCalendar.getTime()));

            if (mSelectedDayTextView != null)
                mSelectedDayTextView.setText(DAY_FORMAT.format(mCalendar.getTime()));
        }

        if (mVersion == Version.VERSION_2) {
            mSelectedDayTextView.setText(VERSION_2_FORMAT.format(mCalendar.getTime()));
            if (mTitle != null)
                mDatePickerHeaderView.setText(mTitle.toUpperCase(mLocale));
            else
                mDatePickerHeaderView.setVisibility(View.GONE);
        }

        // Accessibility.
        long millis = mCalendar.getTimeInMillis();
        mAnimator.setDateMillis(millis);
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR;
        String monthAndDayText = DateUtils.formatDateTime(getActivity(), millis, flags);
        mMonthAndDayView.setContentDescription(monthAndDayText);

        if (announce) {
            flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;
            String fullDateText = DateUtils.formatDateTime(getActivity(), millis, flags);
            Utils.tryAccessibilityAnnounce(mAnimator, fullDateText);
        }
    }

    /**
     * Set whether the device should vibrate when touching fields
     *
     * @param vibrate true if the device should vibrate when touching a field
     */
    public void vibrate(boolean vibrate) {
        mVibrate = vibrate;
    }

    /**
     * Set whether the picker should dismiss itself when being paused or whether it should try to survive an orientation change
     *
     * @param dismissOnPause true if the dialog should dismiss itself when it's pausing
     */
    public void dismissOnPause(boolean dismissOnPause) {
        mDismissOnPause = dismissOnPause;
    }

    /**
     * Set whether the picker should dismiss itself when a day is selected
     *
     * @param autoDismiss true if the dialog should dismiss itself when a day is selected
     */
    @SuppressWarnings("unused")
    public void autoDismiss(boolean autoDismiss) {
        mAutoDismiss = autoDismiss;
    }

    /**
     * Set whether the dark theme should be used
     *
     * @param themeDark true if the dark theme should be used, false if the default theme should be used
     */
    public void setThemeDark(boolean themeDark) {
        mThemeDark = themeDark;
        mThemeDarkChanged = true;
    }

    /**
     * Returns true when the dark theme should be used
     *
     * @return true if the dark theme should be used, false if the default theme should be used
     */
    @Override
    public boolean isThemeDark() {
        return mThemeDark;
    }

    /**
     * Set the accent color of this dialog
     *
     * @param color the accent color you want
     */
    @SuppressWarnings("unused")
    public void setAccentColor(String color) {
        mAccentColor = Color.parseColor(color);
    }

    /**
     * Set the accent color of this dialog
     *
     * @param color the accent color you want
     */
    public void setAccentColor(@ColorInt int color) {
        mAccentColor = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Set the text color of the OK button
     *
     * @param color the color you want
     */
    @SuppressWarnings("unused")
    public void setOkColor(String color) {
        mOkColor = Color.parseColor(color);
    }

    /**
     * Set the text color of the OK button
     *
     * @param color the color you want
     */
    @SuppressWarnings("unused")
    public void setOkColor(@ColorInt int color) {
        mOkColor = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Set the text color of the Cancel button
     *
     * @param color the color you want
     */
    @SuppressWarnings("unused")
    public void setCancelColor(String color) {
        mCancelColor = Color.parseColor(color);
    }

    /**
     * Set the text color of the Cancel button
     *
     * @param color the color you want
     */
    @SuppressWarnings("unused")
    public void setCancelColor(@ColorInt int color) {
        mCancelColor = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Get the accent color of this dialog
     *
     * @return accent color
     */
    @Override
    public int getAccentColor() {
        return mAccentColor;
    }

    /**
     * Set whether the year picker of the month and day picker is shown first
     *
     * @param yearPicker boolean
     */
    public void showYearPickerFirst(boolean yearPicker) {
        mDefaultView = yearPicker ? YEAR_VIEW : MONTH_AND_DAY_VIEW;
    }

    @SuppressWarnings("unused")
    public void setFirstDayOfWeek(int startOfWeek) {
        if (startOfWeek < Calendar.SUNDAY || startOfWeek > Calendar.SATURDAY) {
            throw new IllegalArgumentException("Value must be between Calendar.SUNDAY and " +
                    "Calendar.SATURDAY");
        }
        mWeekStart = startOfWeek;
        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    @SuppressWarnings("unused")
    public void setYearRange(int startYear, int endYear) {
        mDefaultLimiter.setYearRange(startYear, endYear);

        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    /**
     * Sets the minimal date supported by this DatePicker. Dates before (but not including) the
     * specified date will be disallowed from being selected.
     *
     * @param calendar a Calendar object set to the year, month, day desired as the mindate.
     */
    @SuppressWarnings("unused")
    public void setMinDate(Calendar calendar) {
        mDefaultLimiter.setMinDate(calendar);

        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    /**
     * @return The minimal date supported by this DatePicker. Null if it has not been set.
     */
    @SuppressWarnings("unused")
    public Calendar getMinDate() {
        return mDefaultLimiter.getMinDate();
    }

    /**
     * Sets the minimal date supported by this DatePicker. Dates after (but not including) the
     * specified date will be disallowed from being selected.
     *
     * @param calendar a Calendar object set to the year, month, day desired as the maxdate.
     */
    @SuppressWarnings("unused")
    public void setMaxDate(Calendar calendar) {
        mDefaultLimiter.setMaxDate(calendar);

        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    /**
     * @return The maximal date supported by this DatePicker. Null if it has not been set.
     */
    @SuppressWarnings("unused")
    public Calendar getMaxDate() {
        return mDefaultLimiter.getMaxDate();
    }

    /**
     * Sets an array of dates which should be highlighted when the picker is drawn
     *
     * @param highlightedDays an Array of Calendar objects containing the dates to be highlighted
     */
    @SuppressWarnings("unused")
    public void setHighlightedDays(Calendar[] highlightedDays) {
        for (Calendar highlightedDay : highlightedDays) {
            this.highlightedDays.add(Utils.trimToMidnight((Calendar) highlightedDay.clone()));
        }
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    /**
     * @return The list of dates, as Calendar Objects, which should be highlighted. null is no dates should be highlighted
     */
    @SuppressWarnings("unused")
    public Calendar[] getHighlightedDays() {
        if (highlightedDays.isEmpty()) return null;
        Calendar[] output = highlightedDays.toArray(new Calendar[0]);
        Arrays.sort(output);
        return output;
    }

    @Override
    public boolean isHighlighted(int year, int month, int day) {
        Calendar date = Calendar.getInstance(getTimeZone());
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        Utils.trimToMidnight(date);
        return highlightedDays.contains(date);
    }

    /**
     * Sets a list of days which are the only valid selections.
     * Setting this value will take precedence over using setMinDate() and setMaxDate()
     *
     * @param selectableDays an Array of Calendar Objects containing the selectable dates
     */
    @SuppressWarnings("unused")
    public void setSelectableDays(Calendar[] selectableDays) {
        mDefaultLimiter.setSelectableDays(selectableDays);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    /**
     * @return an Array of Calendar objects containing the list with selectable items. null if no restriction is set
     */
    @SuppressWarnings("unused")
    public Calendar[] getSelectableDays() {
        return mDefaultLimiter.getSelectableDays();
    }

    /**
     * Sets a list of days that are not selectable in the picker
     * Setting this value will take precedence over using setMinDate() and setMaxDate(), but stacks with setSelectableDays()
     *
     * @param disabledDays an Array of Calendar Objects containing the disabled dates
     */
    @SuppressWarnings("unused")
    public void setDisabledDays(Calendar[] disabledDays) {
        mDefaultLimiter.setDisabledDays(disabledDays);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    /**
     * @return an Array of Calendar objects containing the list of days that are not selectable. null if no restriction is set
     */
    @SuppressWarnings("unused")
    public Calendar[] getDisabledDays() {
        return mDefaultLimiter.getDisabledDays();
    }

    /**
     * Provide a DateRangeLimiter for full control over which dates are enabled and disabled in the picker
     *
     * @param dateRangeLimiter An implementation of the DateRangeLimiter interface
     */
    @SuppressWarnings("unused")
    public void setDateRangeLimiter(DateRangeLimiter dateRangeLimiter) {
        mDateRangeLimiter = dateRangeLimiter;
    }

    /**
     * Set a title to be displayed instead of the weekday
     *
     * @param title String - The title to be displayed
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Set the label for the Ok button (max 12 characters)
     *
     * @param okString A literal String to be used as the Ok button label
     */
    @SuppressWarnings("unused")
    public void setOkText(String okString) {
        mOkString = okString;
    }

    /**
     * Set the label for the Ok button (max 12 characters)
     *
     * @param okResid A resource ID to be used as the Ok button label
     */
    @SuppressWarnings("unused")
    public void setOkText(@StringRes int okResid) {
        mOkString = null;
        mOkResid = okResid;
    }

    /**
     * Set the label for the Cancel button (max 12 characters)
     *
     * @param cancelString A literal String to be used as the Cancel button label
     */
    @SuppressWarnings("unused")
    public void setCancelText(String cancelString) {
        mCancelString = cancelString;
    }

    /**
     * Set the label for the Cancel button (max 12 characters)
     *
     * @param cancelResid A resource ID to be used as the Cancel button label
     */
    @SuppressWarnings("unused")
    public void setCancelText(@StringRes int cancelResid) {
        mCancelString = null;
        mCancelResid = cancelResid;
    }

    /**
     * Set which layout version the picker should use
     *
     * @param version The version to use
     */
    public void setVersion(Version version) {
        mVersion = version;
    }

    /**
     * Get the layout version the Dialog is using
     *
     * @return Version
     */
    public Version getVersion() {
        return mVersion;
    }

    /**
     * Set which way the user needs to swipe to switch months in the MonthView
     *
     * @param orientation The orientation to use
     */
    public void setScrollOrientation(ScrollOrientation orientation) {
        mScrollOrientation = orientation;
    }

    /**
     * Get which way the user needs to swipe to switch months in the MonthView
     *
     * @return SwipeOrientation
     */
    public ScrollOrientation getScrollOrientation() {
        return mScrollOrientation;
    }

    /**
     * Set which timezone the picker should use
     * <p>
     * This has been deprecated in favor of setting the TimeZone using the constructor that
     * takes a Calendar object
     *
     * @param timeZone The timezone to use
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public void setTimeZone(TimeZone timeZone) {
        mTimezone = timeZone;
        mCalendar.setTimeZone(timeZone);
        YEAR_FORMAT.setTimeZone(timeZone);
        MONTH_FORMAT.setTimeZone(timeZone);
        DAY_FORMAT.setTimeZone(timeZone);
    }

    /**
     * Set a custom locale to be used when generating various strings in the picker
     *
     * @param locale Locale
     */
    public void setLocale(Locale locale) {
        mLocale = locale;
        mWeekStart = Calendar.getInstance(mTimezone, mLocale).getFirstDayOfWeek();
        YEAR_FORMAT = new SimpleDateFormat("yyyy", locale);
        MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
        DAY_FORMAT = new SimpleDateFormat("dd", locale);
    }

    /**
     * Return the current locale (default or other)
     *
     * @return Locale
     */
    @Override
    public Locale getLocale() {
        return mLocale;
    }

    @SuppressWarnings("unused")
    public void setOnDateSetListener(OnDateSetListener listener) {
        mCallBack = listener;
    }

    @SuppressWarnings("unused")
    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    @SuppressWarnings("unused")
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    /**
     * Get a reference to the callback
     *
     * @return OnDateSetListener the callback
     */
    @SuppressWarnings("unused")
    public OnDateSetListener getOnDateSetListener() {
        return mCallBack;
    }

    // If the newly selected month / year does not contain the currently selected day number,
    // change the selected day number to the last day of the selected month or year.
    //      e.g. Switching from Mar to Apr when Mar 31 is selected -> Apr 30
    //      e.g. Switching from 2012 to 2013 when Feb 29, 2012 is selected -> Feb 28, 2013
    private Calendar adjustDayInMonthIfNeeded(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day > daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
        }
        return mDateRangeLimiter.setToNearestDate(calendar);
    }

    @Override
    public void onClick(View v) {
        tryVibrate();
        if (v.getId() == R.id.mdtp_date_picker_year) {
            setCurrentView(YEAR_VIEW);
        } else if (v.getId() == R.id.mdtp_date_picker_month_and_day) {
            setCurrentView(MONTH_AND_DAY_VIEW);
        }
    }

    @Override
    public void onYearSelected(int year) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar = adjustDayInMonthIfNeeded(mCalendar);
        updatePickers();
        setCurrentView(MONTH_AND_DAY_VIEW);
        updateDisplay(true);
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        updatePickers();
        updateDisplay(true);
        if (mAutoDismiss) {
            notifyOnDateListener();
            dismiss();
        }
    }

    private void updatePickers() {
        for (OnDateChangedListener listener : mListeners) listener.onDateChanged();
    }


    @Override
    public MonthAdapter.CalendarDay getSelectedDay() {
        return new MonthAdapter.CalendarDay(mCalendar, getTimeZone());
    }

    @Override
    public Calendar getStartDate() {
        return mDateRangeLimiter.getStartDate();
    }

    @Override
    public Calendar getEndDate() {
        return mDateRangeLimiter.getEndDate();
    }

    @Override
    public int getMinYear() {
        return mDateRangeLimiter.getMinYear();
    }

    @Override
    public int getMaxYear() {
        return mDateRangeLimiter.getMaxYear();
    }


    @Override
    public boolean isOutOfRange(int year, int month, int day) {
        return mDateRangeLimiter.isOutOfRange(year, month, day);
    }

    @Override
    public int getFirstDayOfWeek() {
        return mWeekStart;
    }

    @Override
    public void registerOnDateChangedListener(OnDateChangedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterOnDateChangedListener(OnDateChangedListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void tryVibrate() {
        if (mVibrate) mHapticFeedbackController.tryVibrate();
    }

    @Override
    public TimeZone getTimeZone() {
        return mTimezone == null ? TimeZone.getDefault() : mTimezone;
    }

    public void notifyOnDateListener() {
        if (mCallBack != null) {
            mCallBack.onDateSet(DatePickerHolidayDialog.this, mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    public void notifyOnDatesHolidaysListener() {
        if (mCallBack != null && this.daysSelectedMap != null) {
            Map<String, List<DaySelectedHoliday>> periods = getPeriods(datesSorted);
            double totalDays = 0;
            for(String key : periods.keySet()){
                List<DaySelectedHoliday> daysInPeriod = periods.get(key);
                for(DaySelectedHoliday day : daysInPeriod){
                    totalDays+= day.isHalfDay() ? 0.5 : 1;
                }
            }
            //Validar que los días seleccionados sean menor o igual a la variable num_totalvacaciones menos
            //num_diasagendados que se obtiene al entrar a la opción, en caso de que sea mayor mandar el
            //des_mensaje
            if(totalDays <= (limite_dias + currentDaysScheduled)){
                mCallBack.onDatesSelectedHolidays(periods, totalDays);
                dismiss();
            }else {
                mCallBack.onInvalidMaxSelectedDays(this.des_mensaje);
            }
        }
    }

    public void setCustomTitle(String title) {

        titleCustom = title;
    }


    @Override
    public void setIsTappedSelected(boolean isTappedSelected) {
        this.isTappedSelected = isTappedSelected;
    }

    @Override
    public boolean isTappedSelected() {
        return this.isTappedSelected;
    }


    private void changeView() {
        loadAnimations();
        changeCameraDistance();
        flipCard();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!mIsBackVisible) {
                    mCardFrontLayout.setVisibility(View.VISIBLE);
                    mCardBackLayout.setVisibility(View.GONE);
                } else {
                    mCardFrontLayout.setVisibility(View.GONE);
                    mCardBackLayout.setVisibility(View.VISIBLE);
                }


            }
        }, 1800);
    }

    private void changeCameraDistance() {
        int distance = 4000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation);
    }


    public void flipCard() {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    @Override
    public void setDaysSelected(HashMap<String, DaySelectedHoliday> daysSelected) {

        this.daysSelectedMap = daysSelected;
    }

    @Override
    public void validateSelection() {

        String days = String.valueOf(getTotalDaysSelected());

        if(days.contains(".5")){
            daysSelected.setText(String.format("%s %s",days.equals("0.0") ? "0" : days, "días"));
            daysSelectedConfig.setText(String.format("%s %s",days.equals("0.0") ? "0" : days, "días"));
        }else {
            daysSelected.setText(String.format("%s %s",days.equals("0.0") ? "0" :  days.replace(".0","").trim(), "días"));
            daysSelectedConfig.setText(String.format("%s %s",days.equals("0.0") ? "0" :  days.replace(".0","").trim(), "días"));
        }

      /*  if( this.daysSelectedMap != null){

            if(isEnabledCalendar){
                mCallBack.onInvalidMaxSelectedDays(this.des_mensaje);
            }

            if(this.daysSelectedMap.size() == limite_dias){
                isEnabledCalendar = true;
            }else {
                isEnabledCalendar = false;
            }


        }*/
    }

    private double getTotalDaysSelected(){

        double totalDays = 0.0;
        for(String key :this.daysSelectedMap.keySet()){
            totalDays+= !this.daysSelectedMap.get(key).isHalfDay() ? 1 : 0.5 ;
        }
        return totalDays;
    }

    @Override
    public boolean isLimit() {
        return isEnabledCalendar;
    }

    private void setConfigData() {
        daySelectedHolidayList.clear();
        datesSorted = getOrderDates(daysSelectedMap);
        for (Long key : datesSorted.keySet()) {
            daySelectedHolidayList.add(daysSelectedMap.get(String.valueOf(key)));
        }
        daysConfigRecyclerAdapter.notifyDataSetChanged();
    }



    private void updateDaysSelection() {

        this.daysSelectedMap.clear();
        List<DaySelectedHoliday> daysUpdate = daysConfigRecyclerAdapter.getDaySelectedHolidays();
        for (DaySelectedHoliday day : daysUpdate) {
            daysSelectedMap.put(day.getId(), day);
        }
    }

    @Override
    public void setInitDaysSelectedHolidays(Map<String, List<DaySelectedHoliday>> periodsDefault) {

        //HashMap<String, DaySelectedHoliday> daysSelectedHolidays
        //Inicializamos los valores del calendario
        for(String key: periodsDefault.keySet()){
            List<DaySelectedHoliday> daysInPeriod = periodsDefault.get(key);
            for(DaySelectedHoliday selectedHoliday : daysInPeriod){
                this.daysSelectedMap.put(selectedHoliday.getId(),selectedHoliday);
            }
        }

    }

    @Override
    public HashMap<String, DaySelectedHoliday> getDaysSelected() {
        return this.daysSelectedMap;
    }

    public void setShowHalfDaysOption(boolean showHalfDaysOption) {
        this.showHalfDaysOption = showHalfDaysOption;
    }

    public void setNum_total_vacaciones(double num_total_vacaciones) {
        this.num_total_vacaciones = num_total_vacaciones;
    }

    public void setNum_diasagendados(double num_diasagendados) {
        this.num_diasagendados = num_diasagendados;
    }


    public double getLimite_dias() {
        return limite_dias;
    }

    public void setLimite_dias(double limite_dias) {
        this.limite_dias = limite_dias;
    }

    public String getDes_mensaje() {
        return des_mensaje;
    }

    public void setDes_mensaje(String des_mensaje) {
        this.des_mensaje = des_mensaje;
    }

    private Map<Long, DaySelectedHoliday> getOrderDates(HashMap<String, DaySelectedHoliday> datesSource) {
        HashMap<Long, DaySelectedHoliday> datesSorter = new HashMap<>();
        List<Long> listDates = new ArrayList<>();
        for (String key : datesSource.keySet()) {
            listDates.add(Long.parseLong(key));
        }
        Collections.sort(listDates, Collections.reverseOrder());
        Collections.reverse(listDates);
        for (Long date : listDates) {
            datesSorter.put(date, datesSource.get(String.valueOf(date)));
        }

        Map<Long, DaySelectedHoliday> map = new TreeMap<>(datesSorter);

        return map;
    }

    public  Map<String, List<DaySelectedHoliday>> getPeriods(Map<Long, DaySelectedHoliday> selectionSorted) {
        //Map para guardar los periodos encontrados
        Map<String, List<DaySelectedHoliday>> mapPeriods = new TreeMap<>();
        //Lista para guardar las fechas ya procesadas
        List<String> datesProcessed = new ArrayList<>();

        List<Long> halfDays = new ArrayList<>();
        //Buscamos los medios días que son un periodo aparte
        for (Long key : selectionSorted.keySet()) {
            //Si es medio dia lo asignamos como periodo
            if (selectionSorted.get(key).isHalfDay()) {
                List<DaySelectedHoliday> daysInPeriod = new ArrayList<>();
                daysInPeriod.add(selectionSorted.get(key));
                mapPeriods.put(String.valueOf(key), daysInPeriod);
                //Guardamos en lista de procesadas
                datesProcessed.add(String.valueOf(key));
                halfDays.add(key);
            }
        }

        for (Long key : halfDays) {
            //Eliminamos del hashmap original
            selectionSorted.remove(key);
        }


        if(selectionSorted.size() > 0){
            checkConsecutiveDays(selectionSorted,mapPeriods);
        }


        return mapPeriods;

    }


    private void checkConsecutiveDays(Map<Long, DaySelectedHoliday> selectionSortedFilter,
                                      Map<String, List<DaySelectedHoliday>>  periods){
        //Lista para guardar las fechas a checar
        List<Long> daysToCheck = new ArrayList<>();
        //Todas las fechas en tipo Calendar
        HashMap<Long, Calendar>  calendarDatesMapAll = getCalendarDates(selectionSortedFilter);
        HashMap<Long, Calendar>  calendarDatesCurrent =new HashMap<>();
        List<String> calendarCurrent = new ArrayList<>();

        do{

            daysToCheck.clear();
            calendarDatesCurrent.clear();
            calendarCurrent.clear();
            for (Long key : selectionSortedFilter.keySet()) {
                //if(selectionSortedFilter.containsKey(key)) {//Si esta en el map origianl, se agrega
                    calendarDatesCurrent.put(key,calendarDatesMapAll.get(key));
                    calendarCurrent.add(getDateAsString(calendarDatesMapAll.get(key)));
                    daysToCheck.add(key);
                //}
            }


            if(daysToCheck.size() > 1){
                //Obtenemos el primer elemento
                Calendar starDay = calendarDatesMapAll.get(daysToCheck.get(0));
                //Agregamos la primer fecha a u nuevo periodo
                List<DaySelectedHoliday> daysInPeriod = new ArrayList<>();
                daysInPeriod.add(selectionSortedFilter.get(daysToCheck.get(0)));
                //Eliminamos del map ordenado
                selectionSortedFilter.remove(daysToCheck.get(0));
                String keyPeriod = String.valueOf(daysToCheck.get(0));
                periods.put(keyPeriod,daysInPeriod);
                Calendar nextDay = Calendar.getInstance();

                for (int index = 1; index < daysToCheck.size() ; index++){
                    //Calculamos la fecha de mañana
                    nextDay.set(starDay.get(Calendar.YEAR),starDay.get(Calendar.MONTH),starDay.get(Calendar.DAY_OF_MONTH));
                    nextDay.add(Calendar.DATE,index);
                    String nextKey = getDateAsString(nextDay);
                    if(!calendarCurrent.contains(nextKey)){
                        break;
                    }else {
                        long keyDateNextDay = Long.parseLong(getDateAsString(nextDay));
                        //Agregamos la fecha en el periodo actual
                        periods.get(keyPeriod).add(selectionSortedFilter.get(keyDateNextDay));
                        //Eliminamos del map ordenado
                        selectionSortedFilter.remove(keyDateNextDay);
                    }
                }

            }else {

                //Guardamos el ultimo dia como un periodo por separado
                List<DaySelectedHoliday> daysInPeriod = new ArrayList<>();
                daysInPeriod.add(selectionSortedFilter.get(daysToCheck.get(0)));
                //Eliminamos del map ordenado
                selectionSortedFilter.remove(daysToCheck.get(0));
                String keyPeriod = String.valueOf(daysToCheck.get(0));
                periods.put(keyPeriod,daysInPeriod);
            }

        }while (selectionSortedFilter.size() > 0);


    }


    private String getDateAsString( Calendar calendar){

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%s%s%s",year,month < 10 ? "0"+month : month,day < 10 ? "0"+day : day);
// Output "2012-09-26"
    }

    private HashMap<Long, Calendar> getCalendarDates(Map<Long, DaySelectedHoliday> selectionSortedFilter){

        HashMap<Long, Calendar>   ListCalendarAll = new HashMap<>();
        for (Long key : selectionSortedFilter.keySet()) {
            DaySelectedHoliday daySelected = selectionSortedFilter.get(key);
            Calendar dayCalendar = Calendar.getInstance();
            dayCalendar.set(daySelected.getYear(),daySelected.getMonth(),daySelected.getDay());
            ListCalendarAll.put(key,dayCalendar);
        }

        return ListCalendarAll;
    }


    public void setCurrentDaysScheduled(double currentDaysScheduled) {
        this.currentDaysScheduled = currentDaysScheduled;
    }
}
