package ru.vaddya.schedule.core.utils;

import ru.vaddya.schedule.core.exceptions.IllegalTimeFormatException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Вспомогательный класс для представления времени
 *
 * @author vaddya
 */
public class Time {

    public static Time of(String time) {
        return new Time(time);
    }

    public static Time of(int hours, int minutes) {
        return new Time(hours, minutes);
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private LocalTime time;

    private Time(int hours, int minutes) {
        try {
            this.time = LocalTime.of(hours, minutes);
        } catch (DateTimeParseException e) {
            throw new IllegalTimeFormatException("Illegal time FORMATTER: " + hours + ":" + minutes);
        }
    }

    private Time(String time) {
        try {
            this.time = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalTimeFormatException("Illegal time FORMATTER: " + time);
        }
    }

    public int hours() {
        return time.getHour();
    }

    public int minutes() {
        return time.getMinute();
    }

    @Override
    public String toString() {
        return time.format(FORMATTER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;

        Time time1 = (Time) o;

        return time.equals(time1.time);

    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
