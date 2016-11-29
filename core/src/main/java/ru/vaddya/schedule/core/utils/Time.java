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
public final class Time implements Comparable<Time> {

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

//    public boolean isAfter(Time time) {
//        return hours() > time.hours() || hours() >= time.hours() && minutes() > time.minutes();
//    }

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

    @Override
    public int compareTo(Time time) {
        int diff = hours() - time.hours();
        if (diff != 0) {
            return diff;
        } else {
            diff = minutes() - time.minutes();
            return diff;
        }
//        return hours() == time.hours() && minutes() == time.minutes()
//                ? 0
//                : hours() > time.hours()
//                    ? 1
//                    :
        //        return hours() > time.hours() || hours() == time.hours() && minutes() > time.minutes()
//                ? 1
//                : minutes() == time.minutes() ? 0 : -1;
    }
}
