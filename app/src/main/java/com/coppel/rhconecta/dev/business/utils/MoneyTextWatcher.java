package com.coppel.rhconecta.dev.business.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;

    private ICalculatetotal ICalculatetotal;

    public MoneyTextWatcher(EditText editText,ICalculatetotal ICalculatetotal) {
        editTextWeakReference = new WeakReference<EditText>(editText);
        this.ICalculatetotal = ICalculatetotal;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        EditText editText = editTextWeakReference.get();
        editText.removeTextChangedListener(this);

        try {
            String originalString = s.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            editText.setText(formattedString);
            editText.setSelection(editText.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        editText.addTextChangedListener(this);

        this.ICalculatetotal.calculate();
    }
}