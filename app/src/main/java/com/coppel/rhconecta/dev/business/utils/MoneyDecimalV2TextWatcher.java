package com.coppel.rhconecta.dev.business.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyDecimalV2TextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    private static String prefix = "$";
    private static final int MAX_LENGTH = 20;
    private String previousCleanString;
    private   EditText editText;

    public MoneyDecimalV2TextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);
        //setFocus();
        this.editText = editTextWeakReference.get();
    }

    private void setFocus(){
         this.editText = editTextWeakReference.get();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean focused) {
                if (focused) {
                    editText.addTextChangedListener(MoneyDecimalV2TextWatcher.this);
                } else {
                    editText.removeTextChangedListener(MoneyDecimalV2TextWatcher.this);
                }
                handleCaseCurrencyEmpty(focused);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
       /* if (str.length() < prefix.length()) {
            editText.setText(prefix);
            editText.setSelection(prefix.length());
            return;
        }*/
        if (str.equals(prefix)) {
            str = str.replace(prefix, "");
            editText.setText(str);
            return;
        }
        // cleanString this the string which not contain prefix and ,
        String cleanString = str.replace(prefix, "").replaceAll("[,]", "");
        // for prevent afterTextChanged recursive call
        if (cleanString.equals(previousCleanString) || cleanString.isEmpty()) {
            return;
        }
        previousCleanString = cleanString;

        String formattedString;
        if (cleanString.contains(".")) {
            formattedString = formatDecimal(cleanString);
        } else {
            formattedString = formatInteger(cleanString);
        }
        editText.removeTextChangedListener(this); // Remove listener
        editText.setText(formattedString);
        handleSelection();
        editText.addTextChangedListener(this); // Add back the listener

    }

    private String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat(prefix + "#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    private String formatDecimal(String str) {
        if (str.equals(".")) {
            return prefix + ".";
        }
        BigDecimal parsed = new BigDecimal(str);
        // example pattern VND #,###.00
        DecimalFormat formatter = new DecimalFormat(prefix + "#,###." + getDecimalPattern(str),
                new DecimalFormatSymbols(Locale.US));
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(parsed);
    }

    private String getDecimalPattern(String str) {
        int decimalCount = str.length() - str.indexOf(".") - 1;
        StringBuilder decimalPattern = new StringBuilder();
        for (int i = 0; i < decimalCount && i < 2; i++) {
            decimalPattern.append("0");
        }
        return decimalPattern.toString();
    }

    private void handleSelection() {
        if (editTextWeakReference.get().getText().length() <= MAX_LENGTH) {
            editTextWeakReference.get().setSelection(editTextWeakReference.get().getText().length());
        } else {
            editTextWeakReference.get().setSelection(MAX_LENGTH);
        }
    }


    private void handleCaseCurrencyEmpty(boolean focused) {
        if (focused) {
            if (editTextWeakReference.get().getText().toString().isEmpty()) {
                editTextWeakReference.get().setText(prefix);
            }
        } else {
            if (editTextWeakReference.get().getText().toString().equals(prefix)) {
                editTextWeakReference.get().setText("");
            }
        }
    }

}