package com.coppel.rhconecta.dev.business.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;
    public static String prefix = "$";
    private static final int MAX_LENGTH = 13;
    private EditText et;

    public NumberTextWatcher(EditText et) {
        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        this.et = et;
        hasFractionalPart = false;
    }

    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    public void afterTextChanged(Editable s) {
        String str = s.toString();


     /*   if(str.length() == 10){
            et.setText(str + ".");
       }*/
      /*  if(str.length() > 3 && str.indexOf(".") > 0){
            String current = s.toString();
            current = current.substring(current.indexOf("."),current.length());

            if(current.length() > 2)
                et.setText(current.substring(0,current.length()-1));
                et.addTextChangedListener(this);
                return;
        }*/

        et.removeTextChangedListener(this);

        if (str.length() > 0 && !str.contains(prefix)) {
            et.setText(prefix + str);
            et.setSelection(et.length());
            et.addTextChangedListener(this);
            return;
        }

        if (!str.startsWith(prefix)) {
            if(str.indexOf(prefix) > 0)
                str =str.substring(str.indexOf(prefix),str.length());
            else
                str = prefix +str;

            et.setText( str);
            et.setSelection(et.length());
            et.addTextChangedListener(this);
            return;
        }

        if (str.equals(prefix)) {
            str = str.replace(prefix, "");
            et.setText(str);
            et.addTextChangedListener(this);
            return;
        }

        try {
            int inilen, endlen;
            inilen = et.getText().length();

            String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            v = v.replace("$","");
            Number n = df.parse(v);
            int cp = et.getSelectionStart();
            if (hasFractionalPart) {

                String decimal = v.substring(v.indexOf("."),v.length());
                if(decimal.length() >3){
                    decimal = decimal.substring(0,3);
                    v = v.substring(0,v.indexOf("."));
                    v = String.format("%s%s",v , decimal);
                    n = df.parse(v);
                }

                String ns = df.format(n);

                  if(decimal.endsWith("0")){
                      v = v.substring(0,v.indexOf("."));
                      n = df.parse(v);
                      ns = df.format(n);
                     // et.setText(prefix +ns);
                      if(ns.endsWith("."))
                          ns = ns.substring(0,ns.length() -1);

                      String current = et.getText().toString();
                      String newText = String.format("%s%s%s",prefix,ns , decimal);
                      /*if(current.equals(newText)) {
                          et.addTextChangedListener(this);
                          return;
                      }*/


                      et.setText(newText);

                     // et.setText(String.format("%s%s%s",prefix,ns , decimal));
                     et.addTextChangedListener(this);

                      endlen = et.getText().length();
                      int sel = (cp + (endlen - inilen));
                      if (sel > 0 && sel <= et.getText().length()) {
                          et.setSelection(sel);
                      } else {
                          // place cursor at the end?
                          et.setSelection(et.getText().length() - 1);
                      }

                    return;
                  }else {


                      if(et.getText().toString().equals(prefix +ns)) {
                          et.addTextChangedListener(this);
                          return;

                      }


                      et.setText(prefix +ns);
                      et.addTextChangedListener(this);


                      endlen = et.getText().length();
                      int sel = (cp + (endlen - inilen));
                      if (sel > 0 && sel <= et.getText().length()) {
                          et.setSelection(sel);
                      } else {
                          // place cursor at the end?
                          et.setSelection(et.getText().length() - 1);
                      }
                    return;
                    }
            } else {
                et.setText(prefix + dfnd.format(n));
            }

            endlen = et.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                // place cursor at the end?
                et.setSelection(et.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            // do nothing?
        } catch (ParseException e) {
            // do nothing?
        }

        et.addTextChangedListener(this);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;


        }
    }

}