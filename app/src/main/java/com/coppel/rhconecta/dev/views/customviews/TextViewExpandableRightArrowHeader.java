package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextViewExpandableRightArrowHeader extends ConstraintLayout implements View.OnClickListener {

    private OnExpandableListener OnExpandableListener;
    private boolean isExpanded;
    @BindView(R.id.ctlContainer)
    ConstraintLayout ctlContainer;
    @BindView(R.id.imgvArrow)
    ImageView imgvArrow;
    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.txvValue)
    TextView txvValue;

    public TextViewExpandableRightArrowHeader(Context context) {
        super(context);
        initViews();
    }

    public TextViewExpandableRightArrowHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.text_view_expandable_header_right_arrow, this);
        ButterKnife.bind(this);
        ctlContainer.setOnClickListener(this);
        imgvArrow.setOnClickListener(this);
    }

    public void setTexts(String startText, String endText) {
        txvTitle.setText(startText);
        txvValue.setText(endText);
    }

    public boolean isExpanded() {
        return isExpanded;
    }


    public void setTitleTextSize(float size){
        txvTitle.setTextSize(size);
    }

    public void setValueTextSize(float size){
        txvValue.setTextSize(size);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ctlContainer:
            case R.id.imgvArrow:
                if (OnExpandableListener != null) {
                    if (isExpanded) {
                        setExpandedFalse();
                    } else {
                        setExpandedTrue();
                    }
                    OnExpandableListener.onClick();
                }
                break;
        }
    }

    public interface OnExpandableListener {
        void onClick();
    }

    public void setOnExpandableListener(OnExpandableListener OnExpandableListener) {
        this.OnExpandableListener = OnExpandableListener;
    }

    public void setExpandedFalse() {
        imgvArrow.setImageResource(R.drawable.ic_down_arrow_black);
        isExpanded = false;
    }

    public void setExpandedTrue() {
        imgvArrow.setImageResource(R.drawable.ic_up_arrow_black);
        isExpanded = true;
    }
}
