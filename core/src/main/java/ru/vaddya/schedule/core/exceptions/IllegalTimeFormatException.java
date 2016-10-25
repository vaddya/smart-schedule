package ru.vaddya.schedule.core.exceptions;

/**
 * Created by Vadim on 10/25/2016.
 */
public class IllegalTimeFormatException extends IllegalArgumentException {
    public IllegalTimeFormatException(String s) {
        super(s);
    }
}
