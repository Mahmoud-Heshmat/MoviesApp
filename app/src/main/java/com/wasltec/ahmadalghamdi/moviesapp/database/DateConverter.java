package com.wasltec.ahmadalghamdi.moviesapp.database;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.util.Date;


public class DateConverter {

    //Used when convert read database
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

}
