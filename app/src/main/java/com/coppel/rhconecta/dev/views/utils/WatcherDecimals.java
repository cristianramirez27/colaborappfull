package com.coppel.rhconecta.dev.views.utils;

import android.content.res.Configuration;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.widget.EditText;
import android.widget.TextView;

public class WatcherDecimals implements TextWatcher {

    private EditText editText;
    private TextView textView;
    private OnWatcherDecimalTextChanged onWatcherDecimalTextChanged;
    private SpannableStringBuilder spannableString;
    private String regex;
    private String charToInsert;

    public WatcherDecimals(EditText editText, boolean pesosMark) {
        this.editText = editText;
        this.editText.setRawInputType(Configuration.KEYBOARD_12KEY);
        init(pesosMark);
    }

    public WatcherDecimals(TextView textView, boolean pesosMark) {
        this.textView = textView;
        this.textView.setRawInputType(Configuration.KEYBOARD_12KEY);
        init(pesosMark);
    }

    private void init(boolean pesosMark) {
        if (pesosMark) {
            regex = "^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$";
            charToInsert = "$";
        } else {
            regex = "^(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$";
            charToInsert = "";
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().matches(regex)) {
            String userInput = "" + s.toString().replaceAll("[^\\d]", "");
            StringBuilder cashAmountBuilder = new StringBuilder(userInput);

            while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                cashAmountBuilder.deleteCharAt(0);
            }
            while (cashAmountBuilder.length() < 3) {
                cashAmountBuilder.insert(0, '0');
            }
            cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
            cashAmountBuilder.insert(0, charToInsert);

            if (editText != null) {
                editText.setText(cashAmountBuilder.toString());
                // keeps the cursor always to the right
                Selection.setSelection(editText.getText(), cashAmountBuilder.toString().length());
            } else if (textView != null) {
                spannableString = new SpannableStringBuilder(cashAmountBuilder.toString());
                spannableString.setSpan(new SuperscriptSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new AbsoluteSizeSpan(30, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
                // keeps the cursor always to the right
                Selection.setSelection((Spannable) textView.getText(), cashAmountBuilder.toString().length());
            }

            if (onWatcherDecimalTextChanged != null) {
                onWatcherDecimalTextChanged.onWatcherDecimalTextChanged(cashAmountBuilder.toString());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWatcherDecimalTextChanged {
        void onWatcherDecimalTextChanged(String text);
    }

    public void setOnWatcherDecimalTextChanged(OnWatcherDecimalTextChanged onWatcherDecimalTextChanged) {
        this.onWatcherDecimalTextChanged = onWatcherDecimalTextChanged;
    }
}
