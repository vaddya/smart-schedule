package ru.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;

/**
 * Бросается при отсутствии запрашиваемого занятия
 *
 * @author vaddya
 */
public class NoSuchLessonException extends NoSuchElementException {
    public NoSuchLessonException(String s) {
        super(s);
    }
}
