package com.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Бросается при отсутствии запрашиваемого занятия
 *
 * @author vaddya
 */
public class NoSuchLessonException extends NoSuchElementException {

    private static final String TEMPLATE = "Lesson with ID %s does not exist";

    public NoSuchLessonException(UUID id) {
        super(String.format(TEMPLATE, id));
    }

}