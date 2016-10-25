package ru.vaddya.schedule.core.utils;

import ru.vaddya.schedule.core.exceptions.IllegalTimeFormatException;

/**
 * Класс для представления времени
 *
 * @author vaddya
 */
public class Time {

    private int hours;
    private int minutes;

    private static final char DELIMITER = ':';

    public static Time of(String time) {
        return new Time(time);
    }

    public static Time of(int hours, int minutes) {
        return new Time(hours, minutes);
    }

    private Time(int hours, int minutes) {
        validate(hours, minutes);
        this.hours = hours;
        this.minutes = minutes;
    }

    private Time(String time) {
        int indexOfDel = time.indexOf(DELIMITER);
        if (indexOfDel == -1) {
            throw new IllegalTimeFormatException("Illegal time format: " + time);
        }
        this.hours = Integer.parseInt(time.substring(0, indexOfDel));
        this.minutes = Integer.parseInt(time.substring(indexOfDel + 1));
        validate(hours, minutes);
    }

    public int hours() {
        return hours;
    }

    public int minutes() {
        return minutes;
    }

    private void validate(int hours, int minutes) {
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            throw new IllegalTimeFormatException("Illegal time format: " + hours + ":" + minutes);
        }
    }

    @Override
    public String toString() {
        return String.format("%d:%02d", hours, minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;

        Time time = (Time) o;

        return hours == time.hours && minutes == time.minutes;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        return result;
    }
}
