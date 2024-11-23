package com.example.travel_plan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // date time formatter reuse func
    public static SimpleDateFormat getDatetimeFormatter(Boolean isDateTime) {
        String datePattern = "yyyy-MM-dd";
        if(isDateTime) datePattern += " HH:mm:ss";
        return new SimpleDateFormat(datePattern, Locale.KOREA);
    }

    // convert database's date string to Date
    public static Date parseDbDatetime(String date) {
        try {
            return getDatetimeFormatter(true).parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Date parseDbDate(String date) {
        try {
            return getDatetimeFormatter(false).parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // convert Date to database's date string
    public static String formatDbDatetime(Date date) {
        return getDatetimeFormatter(true).format(date);
    }

    public static String formatDbDate(Date date) {
        return formatDbDatetime(date).substring(0, 10);
    }

}
