package com.coppel.rhconecta.dev.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.coppel.rhconecta.dev.R;

import java.util.Calendar;

/**
 * Created by faustolima on 05/12/18.
 */

public class MyDatePickerFragment extends DialogFragment {


    DatePickerDialog.OnDateSetListener dateSetListener ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpDialog = new DatePickerDialog(getActivity(), R.style.TimePickerTheme, dateSetListener, year, month, day);
        dpDialog.setTitle("Inicio de Vacaciones");
        dpDialog.getDatePicker().setMinDate(c.getTimeInMillis());


        return dpDialog;
    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }
}