package ru.vaddya.schedule.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Вспомогательный класс для представления даты
 *
 * @author vaddya
 */
public final class Dates {

    public static final DateTimeFormatter EXTEND_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy");

    public static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final DateTimeFormatter BRIEF_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE dd.MM");

    public static final DateTimeFormatter SMALL_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM");

    public static long daysUntil(LocalDate date) {
        return DAYS.between(LocalDate.now(), date);
    }

    public static boolean isAfter(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}
