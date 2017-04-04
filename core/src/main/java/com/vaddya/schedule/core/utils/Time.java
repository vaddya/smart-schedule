package com.vaddya.schedule.core.utils;

import com.vaddya.schedule.core.exceptions.IllegalTimeFormatException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Вспомогательный класс для представления времени
 *
 * @author vaddya
 */
public final class Time implements Comparable<Time> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static Time of(String time) {
        return new Time(time);
    }

    public static Time of(int hours, int minutes) {
        return new Time(hours, minutes);
    }

    private LocalTime time;

    private Time(int hours, int minutes) {
        try {
            this.time = LocalTime.of(hours, minutes);
        } catch (DateTimeParseException e) {
            throw new IllegalTimeFormatException("Illegal time format: " + hours + ":" + minutes);
        }
    }

    private Time(String time) {
        try {
            this.time = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalTimeFormatException("Illegal time format: " + time);
        }
    }

    public int hours() {
        return time.getHour();
    }

    public int minutes() {
        return time.getMinute();
    }


    @Override
    public int compareTo(Time time) {
        int diff = hours() - time.hours();
        if (diff != 0) {
            return diff;
        } else {
            diff = minutes() - time.minutes();
            return diff;
        }
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

    @Override
    public String toString() {
        return time.format(FORMATTER);
    }

}