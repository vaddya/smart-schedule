package com.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * com.vaddya.schedule.core.exceptions at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class NoSuchChangeException extends NoSuchElementException {

    private static final String TEMPLATE = "Change with ID %s does not exist";

    public NoSuchChangeException(UUID id) {
        super(String.format(TEMPLATE, id));
    }

}