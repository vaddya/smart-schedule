package ru.vaddya.schedule.core.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

/**
 * Класс для представления недельного периода времени
 *
 * @author vaddya
 */
public final class WeekTime {

    private final LocalDate firstDay;

    public static WeekTime of(LocalDate date) {
        return new WeekTime(date);
    }

    public static WeekTime getNext(WeekTime current) {
        return new WeekTime(current.getDateOf(DayOfWeek.SUNDAY).plus(1, ChronoUnit.DAYS));
    }

    private WeekTime(LocalDate date) {
        firstDay = date.minus(date.getDayOfWeek().ordinal(), ChronoUnit.DAYS);
    }

    public LocalDate getDateOf(DayOfWeek day) {
        return firstDay.plus(day.ordinal(), ChronoUnit.DAYS);
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
        int weekYear = new GregorianCalendar(firstDay.getYear(), firstDay.getMonthValue(),
                firstDay.getDayOfMonth()).getWeekYear();
        return firstDay.getYear() + weekYear; // unique for any week
    }

    public static WeekTime current() {
        return WeekTime.of(LocalDate.now());
    }
}
