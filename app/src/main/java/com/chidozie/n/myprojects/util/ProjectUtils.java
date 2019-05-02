package com.chidozie.n.myprojects.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by Chidozie on 10/1/2018.
 */

public class ProjectUtils {

    /**
     * returns <code> duration </code> in minutes
     *
     * @param position
     * @return
     */
    public static Date duration(int position, int value) {
        long min = 0L;
        Calendar cal = GregorianCalendar.getInstance();
        int field;
        switch (position) {
            case 0: // minutes
                field = Calendar.MINUTE;
                break;
            case 1: // hour
                field = Calendar.HOUR;
                break;
            default:
            case 2: // days
                field = Calendar.DAY_OF_YEAR;
                break;
            case 3: // weeks
                field = Calendar.WEEK_OF_YEAR;
                break;
            case 4: // months
                field = Calendar.MONTH;
                break;
            case 5: // years
                field = Calendar.YEAR;
                break;
        }

        cal.add(field, value);
        return cal.getTime();
    }

    public static String dateFormat(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return String.format(Locale.getDefault(), "%02d/%02d/%02d", day, month, year);
    }

    public static String timeFormat(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        int hr = cal.get(Calendar.HOUR);
        if (hr == 0) hr = 12;
        int min = cal.get(Calendar.MINUTE);
        int am = cal.get(Calendar.AM_PM);

        String am_pm = am == Calendar.AM ? "am" : "pm";

        return String.format(Locale.getDefault(), "%02d:%02d %s", hr, min, am_pm);
    }

}