package ru.vaddya.schedule.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Вспомогательный класс для различных представлений класса java.util.Date
 *
 * @author vaddya
 */
public final class Dates {

    private static final Locale locale = new Locale("ru");

    private static final SimpleDateFormat EXTEND_DATE_FORMAT = new SimpleDateFormat("EEE dd.MM.yyyy", locale);

    private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", locale);

    private static final SimpleDateFormat BRIEF_DATE_FORMAT = new SimpleDateFormat("EEE dd.MM", locale);

    private static final SimpleDateFormat DAY_OF_WEEK = new SimpleDateFormat("EEEE", locale);

    public static String formatExtend(Date date) {
        return EXTEND_DATE_FORMAT.format(date);
    }

    public static int daysUntil(Date date) {
        long diff = date.getTime() - new Date().getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String dayOfWeek(Date date) {
        return DAY_OF_WEEK.format(date);
    }

    public static String formatShort(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String formatBrief(Date date) {
        return BRIEF_DATE_FORMAT.format(date);
    }

    public static Date parseShort(String string) {
        Date date = new Date();
        try {
            date = SHORT_DATE_FORMAT.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
