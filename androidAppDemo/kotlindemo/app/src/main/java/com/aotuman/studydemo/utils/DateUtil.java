package com.aotuman.studydemo.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {

    public static final String DATE_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_HOUR_FORMAT = "HH:mm";


    /**
     * 将时间戳转换为时间 5963432554543535
     */
    public static String formatDate(long s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        try {
            Date date = new Date(s);
            res = simpleDateFormat.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            res = "";
        }
        return res;
    }

}
