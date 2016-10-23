package ru.vaddya.schedule.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Вспомогательный класс для различных представлений класса java.util.Date
 *
 * @author vaddya
 */
public class DateFormat {

    private static final Locale locale = new Locale("ru");

    private static final SimpleDateFormat EXTEND_DATE_FORMAT = new SimpleDateFormat("E dd.MM.Y", locale);

    private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", locale);

    public static String formatExtend(Date date) {
        return EXTEND_DATE_FORMAT.format(date);
    }

    public static String formatShort(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static Date parseExtend(String string) {
        Date date = new Date();
        try {
            date = EXTEND_DATE_FORMAT.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
