package com.chidozie.n.myprojects.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.chidozie.n.myprojects.util.Constants.EXTRA_DATE;

/**
 * Created by Chidozie on 10/7/2018.
 */

public class TimeDialog extends AppCompatDialogFragment {

    private static final String KEY_DATE = "key_date";
    private Date date;
    private TimePicker timePicker;
    private Calendar calendar;

    public static TimeDialog newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_DATE, date);

        TimeDialog fragment = new TimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = (Date) getArguments().getSerializable(KEY_DATE);
        timePicker = new TimePicker(getActivity());
        calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TimePickerDialog.Builder(getActivity())
            .setView(timePicker)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();

                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
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
            .onActivityResult(getTargetRequestCode(), android.app.Activity.RESULT_OK, intent);
    }

}
