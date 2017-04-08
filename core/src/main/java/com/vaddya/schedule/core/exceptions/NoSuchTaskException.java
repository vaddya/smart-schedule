package com.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Бросается при отсутствии запрашиваемой задачи
 *
 * @author vaddya
 */
public class NoSuchTaskException extends NoSuchElementException {

    private static final String TEMPLATE = "Task with ID %s does not exist";

    public NoSuchTaskException(UUID id) {
        super(String.format(TEMPLATE, id));
    }

}