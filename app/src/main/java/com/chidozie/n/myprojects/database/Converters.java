package com.chidozie.n.myprojects.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Chidozie on 10/31/2018.
 */
public class Converters {
    @TypeConverter
    public static Long fromDate(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long time) {
        return new Date(time);
    }
}
