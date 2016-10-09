package ru.vaddya.schedule.core.utils;

/**
 * Created by Vadim on 9/25/2016.
 */
public class Timer {

    private int hours;
    private int minutes;
    public static final char DELIMITER = ':';

    public static Timer of(String time) {
        return new Timer(time);
    }

    public static Timer of(int hours, int minutes) {
        return new Timer(hours, minutes);
    }

    private Timer(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    private Timer(String time) {
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
        if (!(o instanceof Timer)) return false;

        Timer timer = (Timer) o;

        return hours == timer.hours && minutes == timer.minutes;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        return result;
    }
}
