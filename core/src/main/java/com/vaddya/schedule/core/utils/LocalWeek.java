package com.vaddya.schedule.core.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Calendar.WEEK_OF_YEAR;

/**
 * Класс для представления недельного периода времени
 *
 * @author vaddya
 */
public class LocalWeek {

    private final LocalDate firstDay;

    private final int weekNumber;

    public static LocalWeek of(LocalDate date) {
        return new LocalWeek(date);
    }

    public static LocalWeek of(String date) {
        return new LocalWeek(LocalDate.from(Dates.FULL_DATE_FORMAT.parse(date)));
    }

    private LocalWeek(LocalDate date) {
        firstDay = date.minus(date.getDayOfWeek().ordinal(), DAYS);
        Calendar calendar = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        weekNumber = calendar.get(WEEK_OF_YEAR);
    }

    public LocalDate getDateOf(DayOfWeek day) {
        return firstDay.plus(day.ordinal(), DAYS);
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    @Override
    public String toString() {
        return firstDay.format(Dates.SHORT_DATE_FORMAT) + " - " + firstDay.plus(6, DAYS).format(Dates.SHORT_DATE_FORMAT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalWeek)) return false;

        LocalWeek week = (LocalWeek) o;

        return firstDay.equals(week.firstDay);
    }

    @Override
    public int hashCode() {
        return firstDay.getYear() + firstDay.getMonthValue() + firstDay.getDayOfMonth(); // unique for any week
    }

    public static LocalWeek current() {
        return of(LocalDate.now());
    }

    public static LocalWeek next() {
        return after(current());
    }

    public static LocalWeek before(LocalWeek current) {
        return LocalWeek.of(current.getDateOf(MONDAY).minus(7, DAYS));
    }

    public static LocalWeek after(LocalWeek current) {
        return LocalWeek.of(current.getDateOf(SUNDAY).plus(1, DAYS));
    }

    public static int between(LocalWeek first, LocalWeek second) {
        return Math.abs(first.getWeekNumber() - second.getWeekNumber());
    }

}