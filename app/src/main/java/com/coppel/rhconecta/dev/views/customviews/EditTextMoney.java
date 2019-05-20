package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.utils.MoneyTextWatcher;

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

        edtQuantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
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

    public void setPaddinRigthTitle(){

        txvTitle.setPadding(30,8,0,8);
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
        edtQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(12);
        edtQuantity.setFilters(filterArray);

        edtQuantity.addTextChangedListener(new MoneyTextWatcher(edtQuantity));


      /*  edtQuantity.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String current = edtQuantity.getText().toString();
                if(!s.toString().equals(current)){
                    edtQuantity.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = formatted;
                    edtQuantity.setText(formatted);
                    edtQuantity.setSelection(formatted.length());

                    edtQuantity.addTextChangedListener(this);
                }
            }
        });*/


    }


    public EditText getEdtQuantity() {
        return edtQuantity;
    }
}
