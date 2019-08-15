package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextViewDetail extends ConstraintLayout {

    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.txvValue)
    TextView txvValue;
    @BindView(R.id.viewDivider)
    View viewDivider;
    @BindView(R.id.guideline73)
    Guideline guideline73;


    public TextViewDetail(Context context) {
        super(context);
        initViews();
    }

    public TextViewDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.text_view_detail, this);
        ButterKnife.bind(this);
    }

    public void setTexts(String startText, String endText) {
        txvTitle.setText(startText);
        txvValue.setText(endText);
    }


    public void setGravityValue(int gravity){
        txvValue.setGravity(gravity);
    }

    public void setPaddingValue(){
        txvValue.setPadding(0,0,140,0);
    }



    public void setTextsSize(float startText, float endText) {
        txvTitle.setTextSize(startText);
        txvValue.setTextSize(endText);
    }


    public void setSingleLine(boolean isSingleLine) {
        txvTitle.setSingleLine(isSingleLine);
        txvValue.setSingleLine(isSingleLine);
    }

    public void setStartText(String startText) {
        txvTitle.setText(startText);
    }

    public void setEndText(String endText) {
        txvValue.setText(endText);
    }

    public void setStartTextColor(int color) {
        txvTitle.setTextColor(color);
    }

    public void setEndTextColor(int color) {
        txvValue.setTextColor(color);
    }

    public void setStartTextSize(float spSize) {
        txvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setEndTextSize(float spSize) {
        txvValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setStartFont(Typeface typeface) {
        txvTitle.setTypeface(typeface);
    }

    public void setEndFont(Typeface typeface) {
        txvValue.setTypeface(typeface);
    }

    public void hideDivider() {
        viewDivider.setVisibility(View.GONE);
    }

    public void setGuideline73(float percent){
        guideline73.setGuidelinePercent(percent);
    }
}
