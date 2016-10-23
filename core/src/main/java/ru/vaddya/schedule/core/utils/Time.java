package ru.vaddya.schedule.core.utils;

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
        this.hours = hours;
        this.minutes = minutes;
    }

    private Time(String time) {
        int indexOfDel = time.indexOf(DELIMITER);
        if (indexOfDel == -1) {
            throw new IllegalArgumentException("Illegal time format");
        }
        hours = Integer.parseInt(time.substring(0, indexOfDel));
        minutes = Integer.parseInt(time.substring(indexOfDel + 1));
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
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
