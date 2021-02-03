package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableTitle extends ConstraintLayout implements View.OnClickListener {

    private OnExpadableListener onExpadableListener;
    private OnOptionClickListener onOptionClickListener;
    private boolean isExpanded;
    @BindView(R.id.ctlContainer)
    ConstraintLayout ctlContainer;
    @BindView(R.id.imgvArrow)
    ImageView imgvArrow;
    @BindView(R.id.txvPayroll)
    TextView txvPayroll;
    @BindView(R.id.txvDate)
    TextView txvDate;
    @BindView(R.id.imgvMail)
    ImageView imgvMail;
    @BindView(R.id.imgvDownload)
    ImageView imgvDownload;

    public ExpandableTitle(Context context) {
        super(context);
        initViews();
    }

    public ExpandableTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.expandable_title, this);
        ButterKnife.bind(this);
        ctlContainer.setOnClickListener(this);
        imgvArrow.setOnClickListener(this);
        imgvMail.setOnClickListener(this);
        imgvDownload.setOnClickListener(this);
    }

    public void setSingleText(String text) {
        txvPayroll.setText(text);
        txvDate.setVisibility(View.GONE);
    }

    public void setTexts(String text1, String text2) {
        txvPayroll.setText(text1);
        txvDate.setText(text2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ctlContainer:
            case R.id.imgvArrow:
                if (onExpadableListener != null) {
                    if (isExpanded) {
                        setExpandedFalse();
                    } else {
                        setExpandedTrue();
                    }
                    onExpadableListener.onClick();
                }
                break;
            case R.id.imgvMail:
                if (onOptionClickListener != null) {
                    onOptionClickListener.onMailClick();
                }
                break;
            case R.id.imgvDownload:
                if (onOptionClickListener != null) {
                    onOptionClickListener.onDownloadClick();
                }
                break;
        }
    }

    public interface OnExpadableListener {
        void onClick();
    }

    public interface OnOptionClickListener {
        void onMailClick();

        void onDownloadClick();
    }

    public void setOnExpadableListener(OnExpadableListener onExpadableListener) {
        this.onExpadableListener = onExpadableListener;
    }

    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener) {
        this.onOptionClickListener = onOptionClickListener;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpandedFalse() {
        imgvArrow.setImageResource(R.drawable.ic_down_arrow_blue);
        isExpanded = false;
    }

    public void setExpandedTrue() {
        imgvArrow.setImageResource(R.drawable.ic_up_arrow_blue);
        isExpanded = true;
    }
}
