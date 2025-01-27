package com.coppel.rhconecta.dev.business.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyDecimalTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    private static String prefix = "$";
    private static final int MAX_LENGTH = 20;
    private String previousCleanString;

    public MoneyDecimalTextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);
        //setFocus();
    }

    private void setFocus(){
        EditText editText = editTextWeakReference.get();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean focused) {
                if (focused) {
                    editText.addTextChangedListener(MoneyDecimalTextWatcher.this);
                } else {
                    editText.removeTextChangedListener(MoneyDecimalTextWatcher.this);
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
    public void afterTextChanged(Editable s) {
        EditText editText = editTextWeakReference.get();
        String current = editText.getText().toString();
        if(!s.toString().equals(current)){
               editText.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = formatted;
               editText.setText(formatted);
               editText.setSelection(formatted.length());

               editText.addTextChangedListener(this);
        }

/*
        try {
            String originalString = s.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = new DecimalFormat("$" + "#,###",
                    new DecimalFormatSymbols(Locale.US));

           // DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
           // formatter.applyPattern("#,###,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            editText.setText(TextUtilities.getNumberInCurrencyFormaNoDecimal(Double.parseDouble(formattedString)));
            editText.setSelection(editText.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        editText.addTextChangedListener(this);*/

    }

    private String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat(prefix + "#,###.##", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
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