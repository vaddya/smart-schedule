package com.vaddya.schedule.core.exceptions;

import java.util.UUID;

/**
 * com.vaddya.schedule.database.exception at smart-schedule
 *
 * @author vaddya
 * @since April 06, 2017
 */
public class DuplicateIdException extends RuntimeException {

    private static final String TEMPLATE = "Duplicate ID: %s";

    public DuplicateIdException(UUID id) {
        super(String.format(TEMPLATE, id));
    }

}