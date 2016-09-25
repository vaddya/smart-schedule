package ru.vaddya.schedule.core;

/**
 * Created by Vadim on 9/25/2016.
 */
public class Timer {

    private int hours;
    private int minutes;
    public static final char DELIMITER = ':';

    public Timer(String time) {
        int indexOfDel = time.indexOf(DELIMITER);
        if (indexOfDel == -1) {
            throw new IllegalArgumentException("Illegal time format");
        }
        hours = Integer.parseInt(time.substring(0, indexOfDel));
        minutes = Integer.parseInt(time.substring(indexOfDel + 1));
    }

    public Timer(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getTime() {
        return String.format("%d:%02d", hours, minutes);
    }
}
