package com.chidozie.n.myprojects.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;
import static com.chidozie.n.myprojects.util.Constants.EXTRA_DATE;

/**
 * Created by Chidozie on 10/7/2018.
 */

public class DateDialog extends AppCompatDialogFragment {

    private static final String KEY_DATE = "key_date";
     private DatePicker datePicker;
    private Calendar calendar;

    public static DateDialog newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_DATE, date);

        DateDialog fragment = new DateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date date = (Date) getArguments().getSerializable(KEY_DATE);
        datePicker = new DatePicker(getActivity());
        calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        datePicker.updateDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
            );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new DatePickerDialog.Builder(getActivity())
            .setView(datePicker)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();
                    int day = datePicker.getDayOfMonth();

                    calendar.set(year, month, day);
                    sendResult(calendar.getTime());
                }
            })
            .create();
    }

    private void sendResult(Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment()
            .onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
    }

}
