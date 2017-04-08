package com.vaddya.schedule.core.exceptions;

/**
 * Бросается при неверно указанном времени
 *
 * @author vaddya
 */
public class IllegalTimeFormatException extends IllegalArgumentException {

    private static final String TEMPLATE = "Illegal time format: %s";

    public IllegalTimeFormatException(String s) {
        super(s);
    }

}