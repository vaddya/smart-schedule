package ru.vaddya.schedule.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Вспомогательный класс для представления даты
 *
 * @author vaddya
 */
public class Dates {

    private static final DateTimeFormatter EXTEND_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy");

    private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final DateTimeFormatter BRIEF_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE dd.MM");

    public static long daysUntil(LocalDate date) {
        return DAYS.between(LocalDate.now(), date);
    }

    public static boolean isAfter(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public static String formatExtend(LocalDate date) {
        return EXTEND_DATE_FORMAT.format(date);
    }

    public static String formatShort(LocalDate date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String formatBrief(LocalDate date) {
        return BRIEF_DATE_FORMAT.format(date);
    }

    public static LocalDate parseShort(String s) {
        return LocalDate.from(SHORT_DATE_FORMAT.parse(s));
    }
}
