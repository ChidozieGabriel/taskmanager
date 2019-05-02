package com.chidozie.n.myprojects.model;

import com.chidozie.n.myprojects.App;
import com.chidozie.n.myprojects.R;

/**
 * Created by Chidozie on 12/22/2018.
 */
public class Duration {
    public static final int SECONDS = 0;
    public static final int MINUTES = 1;
    public static final int HOURS = 2;
    public static final int DAYS = 3;
    public static final int WEEKS = 4;
    public static final int MONTHS = 5;
    public static final int YEARS = 6;
    private int value;
    private int period;
    private boolean isPast;

    public Duration(int value, int period, boolean isPast) {
        this.value = value;
        this.period = period;
        this.isPast = isPast;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPeriod() {
        return App.getRes().getStringArray(R.array.duration_array)[period];
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
