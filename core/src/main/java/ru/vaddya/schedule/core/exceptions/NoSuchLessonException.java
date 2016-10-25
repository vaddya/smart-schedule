package ru.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;

/**
 * Created by Vadim on 10/25/2016.
 */
public class NoSuchLessonException extends NoSuchElementException {
    public NoSuchLessonException(String s) {
        super(s);
    }
}
