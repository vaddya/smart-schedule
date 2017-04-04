package com.vaddya.schedule.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Вспомогательный класс для представления дат
 *
 * @author vaddya
 */
public final class Dates {

    public static final DateTimeFormatter FULL_DATE_FORMAT = ofPattern("dd.MM.yyyy");

    public static final DateTimeFormatter BRIEF_DATE_FORMAT = ofPattern("EEE dd.MM");

    public static final DateTimeFormatter SHORT_DATE_FORMAT = ofPattern("dd.MM");

    public static long daysUntil(LocalDate date) {
        return DAYS.between(LocalDate.now(), date);
    }

    public static boolean isPast(LocalDate date) {
        return LocalDate.now().isAfter(date);
    }
}
