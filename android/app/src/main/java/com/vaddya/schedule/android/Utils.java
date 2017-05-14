package com.vaddya.schedule.android;

import java.util.Calendar;
import java.util.Date;

/**
 * com.vaddya.schedule.android at android
 *
 * @author vaddya
 */
public class Utils {

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

}