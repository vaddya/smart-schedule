package com.vaddya.schedule.core.exceptions;

/**
 * Бросается при неверно указанном времени
 *
 * @author vaddya
 */
public class IllegalTimeFormatException extends IllegalArgumentException {

    public IllegalTimeFormatException(String s) {
        super(s);
    }

}