package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RowFourText extends LinearLayout {

    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.title4)
    TextView title4;

    public RowFourText(Context context) {
        super(context);
        initViews();
    }

    public RowFourText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.header_titles_list, this);
        ButterKnife.bind(this);
    }

    public void setTitle1(String title) {
        title1.setText(title);
    }

    public void setTitle2(String title) {
        title2.setText(title);
    }
    public void setTitle3(String title) {
        title3.setText(title);
    }
    public void setTitle4(String title) {
        title4.setText(title);
    }

}
