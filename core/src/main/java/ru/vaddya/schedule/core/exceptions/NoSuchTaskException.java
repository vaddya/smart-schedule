package ru.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;

/**
 * Created by Vadim on 10/25/2016.
 */
public class NoSuchTaskException extends NoSuchElementException {
    public NoSuchTaskException(String s) {
        super(s);
    }
}
