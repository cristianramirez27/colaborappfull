package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTextMoney extends ConstraintLayout {

    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.edtQuantity)
    EditText edtQuantity;
    @BindView(R.id.imgvArrow)
    ImageView imgvArrow;

    public EditTextMoney(Context context) {
        super(context);
        initViews();
    }

    public EditTextMoney(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.edit_text_money, this);
        ButterKnife.bind(this);
    }

    public void setInformativeMode(String concept, String quantity) {
        imgvArrow.setVisibility(View.GONE);
        txvTitle.setText(concept);
        edtQuantity.setEnabled(false);
        edtQuantity.setText(quantity);
    }

    public void setTitleGravity(int gravity){
        txvTitle.setGravity(gravity);
    }
}
