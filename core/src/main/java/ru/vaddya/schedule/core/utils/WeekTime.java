package ru.vaddya.schedule.core.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static ru.vaddya.schedule.core.utils.Dates.SHORT_DATE_FORMAT;
import static ru.vaddya.schedule.core.utils.Dates.SMALL_DATE_FORMAT;

/**
 * Класс для представления недельного периода времени
 *
 * @author vaddya
 */
public class WeekTime {

    private final LocalDate firstDay;

    public static WeekTime of(LocalDate date) {
        return new WeekTime(date);
    }

    public static WeekTime of(String date) {
        return new WeekTime(LocalDate.from(SHORT_DATE_FORMAT.parse(date)));
    }

    public static WeekTime getNext(WeekTime current) {
        return new WeekTime(current.getDateOf(DayOfWeek.SUNDAY).plus(1, DAYS));
    }

    private WeekTime(LocalDate date) {
        firstDay = date.minus(date.getDayOfWeek().ordinal(), DAYS);
    }

    public LocalDate getDateOf(DayOfWeek day) {
        return firstDay.plus(day.ordinal(), DAYS);
    }

    @Override
    public String toString() {
        return firstDay.format(SMALL_DATE_FORMAT) + " - " + firstDay.plus(6, DAYS).format(SMALL_DATE_FORMAT);
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
        return WeekTime.of(LocalDate.now());
    }
}
