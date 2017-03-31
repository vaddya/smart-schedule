package com.vaddya.schedule.core.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Calendar.WEEK_OF_YEAR;

/**
 * Класс для представления недельного периода времени
 *
 * @author vaddya
 */
public class WeekTime {

    private final LocalDate firstDay;

    private final int weekNumber;

    public static WeekTime of(LocalDate date) {
        return new WeekTime(date);
    }

    public static WeekTime of(String date) {
        return new WeekTime(LocalDate.from(Dates.FULL_DATE_FORMAT.parse(date)));
    }

    private WeekTime(LocalDate date) {
        firstDay = date.minus(date.getDayOfWeek().ordinal(), DAYS);
        Calendar calendar = new GregorianCalendar(new Locale("ru"));
        calendar.set(firstDay.getYear(),
                firstDay.getMonthValue(),
                firstDay.getDayOfMonth());
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
        if (!(o instanceof WeekTime)) return false;

        WeekTime weekTime = (WeekTime) o;

        return firstDay.equals(weekTime.firstDay);
    }

    @Override
    public int hashCode() {
        return firstDay.getYear() + firstDay.getMonthValue() + firstDay.getDayOfMonth(); // unique for any week
    }

    public static WeekTime current() {
        return of(LocalDate.now());
    }

    public static WeekTime next() {
        return after(current());
    }

    public static WeekTime before(WeekTime current) {
        return new WeekTime(current.getDateOf(MONDAY).minus(7, DAYS));
    }

    public static WeekTime after(WeekTime current) {
        return new WeekTime(current.getDateOf(SUNDAY).plus(1, DAYS));
    }

    public static int between(WeekTime first, WeekTime second) {
        return Math.abs(first.getWeekNumber() - second.getWeekNumber());
    }
}
