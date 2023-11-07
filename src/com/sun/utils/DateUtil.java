package com.sun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    public static String getTime(Date date) {
        return sdf.format(date);
    }
}
