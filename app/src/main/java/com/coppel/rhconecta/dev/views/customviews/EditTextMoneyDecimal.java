package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.utils.NumberTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTextMoneyDecimal extends ConstraintLayout {

    @BindView(R.id.txvTitle)
    TextView txvTitle;
    @BindView(R.id.edtQuantity)
   EditText edtQuantity;
    @BindView(R.id.imgvArrow)
    ImageView imgvArrow;

    NumberTextWatcher numberTextWatcher;

    public EditTextMoneyDecimal(Context context) {
        super(context);
        initViews();
    }

    public EditTextMoneyDecimal(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.edit_text_money_v2, this);
        ButterKnife.bind(this);
    }

    public void setInformativeMode(String concept, String quantity) {
        imgvArrow.setVisibility(View.GONE);
        txvTitle.setText(concept);
        edtQuantity.setEnabled(false);
        edtQuantity.setText(quantity);
    }

    public void setEnableQuantity(boolean enable) {
        edtQuantity.setTextSize(22);
        edtQuantity.setEnabled(enable);
    }


    public void setInformativeQuantity(String value) {
        edtQuantity.setTextColor(getResources().getColor(R.color.disable_text_color));
        edtQuantity.setBackground(null);
        edtQuantity.setText(value);
    }

    public void setSizeQuantity(float size) {
        edtQuantity.setTextSize(size);
    }

    public void setHint(String hint) {
        edtQuantity.setHint(hint);
    }

    public void setFont(String font) {
       // edtQuantity.setFon(hint);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),font);
        edtQuantity.setTypeface(type);
    }


    public void setMaxLengh(int lengh){

        edtQuantity.setFilters(new InputFilter[] { new InputFilter.LengthFilter(lengh) });
    }

    public void setPaddinRigthTitle(){

        txvTitle.setPadding(30,8,0,8);
    }

    public void setPaddingEditText(int left, int top,int right,int bottom){

        edtQuantity.setPadding( left, top, right ,bottom);
    }

    public void setTitleGravity(int gravity){
        txvTitle.setGravity(gravity);
    }

    public String getQuantity(){
        if(edtQuantity.getText() != null) {

            String content = edtQuantity.getText().toString().replace(",","");
            content =  content.replace("$","");
            content = content.replace(" ","");

            return content.trim();

        }

        return "";
    }

    public void setTextWatcherMoney(){
       // edtQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtQuantity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(13);
        edtQuantity.setFilters(filterArray);

        //edtQuantity.addTextChangedListener(new MoneyDecimalV2TextWatcher(edtQuantity));*/


       if(numberTextWatcher ==null )
        numberTextWatcher = new NumberTextWatcher(edtQuantity);

        edtQuantity.addTextChangedListener(numberTextWatcher);


    }

    public void removeTextWatcher(){
        edtQuantity.removeTextChangedListener(numberTextWatcher);
    }


    public EditText getEdtQuantity() {
        return edtQuantity;
    }

    public void setVisibilityTitle(int visibility){
        txvTitle.setVisibility(visibility);
    }
}
