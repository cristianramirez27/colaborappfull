package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
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


    public void setTextsSize(float startText, float endText) {
        txvTitle.setTextSize(startText);
        txvValue.setTextSize(endText);
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
}
