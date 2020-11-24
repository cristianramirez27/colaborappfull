package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextViewDetailThree extends ConstraintLayout {

    @BindView(R.id.txvStart)
    TextView txvStart;
    @BindView(R.id.txvMiddle)
    TextView txvMiddle;
    @BindView(R.id.txvEnd)
    TextView txvEnd;
    @BindView(R.id.viewDivider)
    View viewDivider;

    public TextViewDetailThree(Context context) {
        super(context);
        initViews();
    }

    public TextViewDetailThree(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.text_view_detail_three_rows, this);
        ButterKnife.bind(this);
    }

    public void setTexts(String startText, String middleText, String endText) {
        txvStart.setText(startText);
        txvMiddle.setText(middleText);
        txvEnd.setText(endText);
    }

    public void setStartText(String startText) {
        txvStart.setText(startText);
    }

    public void setMiddleText(String middleText) {
        txvMiddle.setText(middleText);
    }

    public void setEndText(String endText) {
        txvEnd.setText(endText);
    }

    public void setFontStart(Typeface typeface) {
        txvStart.setTypeface(typeface);
    }

    public void setFontMiddle(Typeface typeface) {
        txvMiddle.setTypeface(typeface);
    }

    public void setFontEnd(Typeface typeface) {
        txvEnd.setTypeface(typeface);
    }

    public void setStartTextColor(int color) {
        txvStart.setTextColor(color);
    }

    public void setMiddleTextColor(int color) {
        txvMiddle.setTextColor(color);
    }

    public void setEndTextColor(int color) {
        txvEnd.setTextColor(color);
    }

    public void setStartTextSize(float spSize) {
        txvStart.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setMiddleTextSize(float spSize) {
        txvMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setEndTextSize(float spSize) {
        txvEnd.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void hideDivider() {
        viewDivider.setVisibility(View.GONE);
    }

}
